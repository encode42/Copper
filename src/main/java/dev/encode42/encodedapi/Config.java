package dev.encode42.encodedapi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

/*
TODO:
- Seems like non-copy configurations may not be working
- Long term: Defaults and migrations
 */
public class Config {
	private FileConfiguration config;
	private final boolean isCopy;

	private final Plugin plugin     = Util.plugin;
	private final File pluginFolder = plugin.getDataFolder();

	private final String filename;
	private final File file;

	/**
	 * Configuration file manager
	 * @param filename Name of the file
	 * @param copy Make a copy
	 * - Copies do not remove comments
	 * - Copies disable all set/save methods
	 */
	public Config(String filename, boolean copy) {
		this.filename = filename;
		this.file     = Util.toFile(filename + ".yml");
		this.isCopy   = copy;

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
	 */
	private void create() {
		try {
			// Make the plugin directory
			if (!pluginFolder.exists()) pluginFolder.mkdir();

			// Copy the config
			if (this.isCopy) {
				copy();
				return;
			}

			// Create the file
			if (!file.exists()) file.createNewFile();
			else return;

			// Save the config
			load();
			save();
		} catch(IOException e) {
			Error.fileReadWriteError(file, e);
		}
	}

	/**
	 * Create a the configuration as a copy
	 * Disables save/set methods
	 */
	private void copy() {
		try {
			// Get the resource
			Reader resource = Util.getResource(filename);

			// Convert the reader to a string
			BufferedReader reader = new BufferedReader(resource);
			StringBuilder builder = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null)
				builder.append(line)
						.append("\n");

			// Write the file
			Util.writeFile(file.getName(), builder.toString(), false);

			reader.close();
			resource.close();
		} catch (IOException e) {
			Error.fileReadWriteError(file, e);
		}
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
	 * Get the config
	 * @return The configuration file
	 */
	public FileConfiguration get() {
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
	public FileConfiguration reload() {
		// Create and load
		create();
		load();

		return get();
	}
}