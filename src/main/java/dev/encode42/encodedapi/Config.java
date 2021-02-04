package dev.encode42.encodedapi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Collectors;

// TODO: Defaults and migrations
public class Config {
	private FileConfiguration config;
	private final File file;
	private final String name;
	private final boolean isCopy;

	/**
	 * Configuration file manager
	 * @param filename Name of the file
	 * @param copy Make a copy
	 * - Does not remove comments
	 * - Disables all set/save methods
	 */
	public Config(String filename, boolean copy) {
		this.file   = Util.toFile(filename + ".yml");
		this.name   = filename;
		this.isCopy = copy;

		// Create and load the config
		create();
		load();
	}

	/**
	 * Configuration file manager
	 * Overload that defaults copy to false
	 * @see Config(String, Boolean)
	 * @param filename Name or path to save to
	 */
	public Config(String filename) {
		this(filename, false);
	}

	/**
	 * Create the configuration file
	 * @param force Force creation of the file
	 */
	private void create(boolean force) {
		if (!force && file.exists()) return;

		try {
			// Create the file
			Util.createFile(file, force);

			// Copy the config
			if (this.isCopy) {
				// Get the resource
				BufferedReader resource = new BufferedReader(Util.getResource(name));

				// Write the file
				Util.writeFile(file.getName(), resource.lines().collect(Collectors.joining()), true);

				resource.close();
				return;
			}

			// Save the config
			save();
		} catch(IOException e) {
			Error.fileReadWriteError(file, e);
		}

		load();
	}

	/**
	 * Create the configuration file
	 * Overload that defaults force to false
	 */
	private void create() {
		create(false);
	}

	/**
	 * Save the configuration
	 * Disabled if the configuration is a copy
	 */
	public void save() {
		try {
			if (!this.isCopy) config.save(file);
		} catch (IOException e) {
			Error.fileReadWriteError(file, e);
		}
	}

	/**
	 * Load the configuration from a reader
	 * @param reader The reader to load
	 */
	private void load(Reader reader) {
		config = YamlConfiguration.loadConfiguration(reader);

		// Close the reader
		try {
			reader.close();
		} catch (IOException e) {
			Error.readerCloseError(e);
		}
	}

	/**
	 * Load the configuration from a file
	 * @param file The file to load
	 */
	private void load(File file) {
		config = YamlConfiguration.loadConfiguration(file);
	}

	/**
	 * Load the configuration from a file
	 * Overload that defaults to global file
	 * @see Config#load(File)
	 */
	private void load() {
		load(file);
	}

	/**
	 * Get an object
	 * @param path Path to get value of
	 * @return Parsed object
	 */
	public Object get(String path) {
		return config.get(path);
	}

	/**
	 * Get a string
	 * @param path Path to get value of
	 * @return Parsed string
	 */
	public String getString(String path) {
		return config.getString(path);
	}

	/**
	 * Get an integer
	 * @param path Path to get value of
	 * @return Parsed integer
	 */
	public int getInt(String path) {
		return config.getInt(path);
	}

	/**
	 * Get an boolean
	 * @param path Path to get value of
	 * @return Parsed boolean
	 */
	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}

	/**
	 * Get the configuration
	 * @return Config file configuration
	 */
	public FileConfiguration getConfig() {
		return config;
	}

	/**
	 * Set configuration values
	 * Disabled if the configuration is a copy
	 * @param key Key (path) to set
	 * @param value Value to set the key to
	 */
	public void set(String key, Object value) {
		// Don't modify copies
		if (this.isCopy) return;

		config.set(key, value);
		save();
	}

	/**
	 * Reload the config
	 * @return The configuration file
	 */
	public Config reload() {
		// Create and load
		create();
		load();

		return this;
	}
}