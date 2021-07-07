package dev.encode42.encodedapi.config;

import dev.encode42.encodedapi.util.IO;
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
	 * Configuration file manager
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
	 * Configuration file manager
	 * @param filename Name or path to save to
	 */
	public Config(String filename) {
		this(filename, filename);
	}

	/**
	 * Create the configuration file
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
	 * Create the configuration file
	 * Overload that defaults force to false
	 * @return Whether or not the file was created
	 */
	private boolean create() {
		return create(false);
	}

	/**
	 * Save the configuration
	 * Disabled if the configuration is a copy
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
	 * Load the configuration from a reader
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
	 * Load the configuration from a file
	 * @param file The file to load
	 * @return This config instance
	 */
	public Config load(File file) {
		config = YamlFile.loadConfiguration(file);
		return this;
	}

	/**
	 * Load the configuration from a file
	 * @return This config instance
	 */
	public Config load() {
		return load(file);
	}

	/**
	 * Check if the config has a path
	 * @param path Path to check existence of
	 * @return Whether or not the path exists
	 */
	public boolean has(String path) {
		return config.contains(path);
	}

	/**
	 * Get an object from a path
	 * @param path Path to get value of
	 * @return Returned object
	 */
	public Object get(String path) {
		return config.get(path);
	}

	/**
	 * Get a string from a path
	 * @param path Path to get value of
	 * @return Returned string
	 */
	public String getString(String path) {
		return config.getString(path);
	}

	/**
	 * Get an integer from a path
	 * @param path Path to get value of
	 * @return Returned integer
	 */
	public int getInt(String path) {
		return config.getInt(path);
	}

	/**
	 * Get an boolean from a path
	 * @param path Path to get value of
	 * @return Returned boolean
	 */
	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}

	/**
	 * Get a list from a path
	 * @param path Path to get value of
	 * @return Returned list
	 */
	public List<?> getList(String path) {
		return config.getList(path);
	}

	/**
	 * Get the configuration instance
	 * @return Config file instance
	 */
	public YamlFile getConfig() {
		return config;
	}

	/**
	 * Set configuration values
	 * Disabled if the configuration is a copy
	 * @param key Path to set
	 * @param value Value to set the path to
	 */
	public void set(String key, Object value) {
		config.set(key, value);
		save();
	}
}