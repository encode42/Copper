package dev.encode42.copper.config;

import dev.encode42.copper.util.IO;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

public class Config {
	private YamlFile config;
	private final File file;
	private final String resourceName;

	/**
	 * Configuration file manager.
	 * @param fileName Name of the file
	 * @param resourceName Name or path to copy from
	 */
	public Config(String fileName, String resourceName) {
		this.file = IO.toFile(fileName);
		this.resourceName = resourceName;

		// Create the config
		create();

		// Load the config
		load();
	}

	/**
	 * Configuration file manager.
	 * @param filename Name or path to save to
	 */
	public Config(String filename) {
		this(filename, filename);
	}

	/**
	 * Create the configuration file.
	 * @param override Force creation of the file
	 * @return Whether or not the file was created
	 */
	private boolean create(boolean override) {
		// Check if the file exists
		if (!override && file.exists()) return false;

		try {
			// Get the resource
			Reader reader = IO.getResource(resourceName);

			if (reader == null) {
				return false;
			}

			BufferedReader resource = new BufferedReader(reader);

			// Write to the file
			IO.writeFile(file, resource.lines().collect(Collectors.joining("\n")), override);

			resource.close();
			reader.close();
		} catch(IOException e) {
			// todo: erroring
			return false;
		}

		return true;
	}

	/**
	 * Create the configuration file.
	 * @return Whether or not the file was created
	 */
	private boolean create() {
		return create(false);
	}

	/**
	 * Save the in-memory configuration changes.
	 * @return Whether or not the file successfully saved
	 */
	public boolean save() {
		try {
			config.save();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Load the configuration from a resource reader.
	 * @param reader Reader to load
	 * @return This config instance
	 */
	public Config load(Reader reader) {
		config = YamlFile.loadConfiguration(reader);

		// Close the reader
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this;
	}

	/**
	 * Load the configuration from a file.
	 * @param file The file to load
	 * @return This config instance
	 */
	public Config load(File file) {
		config = YamlFile.loadConfiguration(file);
		return this;
	}

	/**
	 * Load the configuration from the default file.
	 * @return This config instance
	 */
	public Config load() {
		return load(file);
	}

	/**
	 * Check if the config has a key,
	 * @param key Key to check existence of
	 * @return Whether or not the key exists
	 */
	public boolean has(String key) {
		return config.contains(key);
	}

	/**
	 * Get an object from a key.
	 * @param key Key to get value of
	 * @return Returned object
	 */
	public Object get(String key) {
		return config.get(key);
	}

	/**
	 * Get a string from a key.
	 * @param key Key to get value of
	 * @return Returned string
	 */
	public String getString(String key) {
		return config.getString(key);
	}

	/**
	 * Get an integer from a key.
	 * @param key Key to get value of
	 * @return Returned integer
	 */
	public int getInt(String key) {
		return config.getInt(key);
	}

	/**
	 * Get an boolean from a key.
	 * @param key Key to get value of
	 * @return Returned boolean
	 */
	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	/**
	 * Get a list from a key.
	 * @param key Key to get value of
	 * @return Returned list
	 */
	public List<?> getList(String key) {
		return config.getList(key);
	}

	/**
	 * Get the YamlFile configuration instance.
	 * @return Config file instance
	 */
	public YamlFile getConfig() {
		return config;
	}

	/**
	 * Get the result of a query, return key if non-existent.
	 * @param key Key to get value of
	 * @return Returned string or key
	 */
	public String getReturn(String key) {
		return this.has(key) ? this.getString(key) : key;
	}

	/**
	 * Set a configuration key's value.
	 * @param key Key to set
	 * @param value Value to set the key to
	 */
	public void set(String key, Object value) {
		config.set(key, value);
		save();
	}
}