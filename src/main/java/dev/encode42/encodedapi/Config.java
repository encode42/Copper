package dev.encode42.encodedapi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Config {
    private YamlConfiguration config;
    private final String filename;
    private final Plugin plugin;
    private final Logger log;

    public Config(Plugin plugin, String filename) {
        this.filename = filename;
        this.plugin = plugin;
        this.log = plugin.getLogger();
    }

    // Get the config
    public FileConfiguration get() {
        File configFile = getFile();

        // Create the file if not existent
        if (!configFile.exists()) {create();}

        return YamlConfiguration.loadConfiguration(configFile);
    }

    // Get the file
    public File getFile() {
        return new File(plugin.getDataFolder() + File.separator + filename.replaceAll("/\\\\", File.separator) + ".yml");
    }

    // Create the file
    public void create() {
        try {
            // Create the plugin folder
            if (!plugin.getDataFolder().exists()) {plugin.getDataFolder().mkdir();}

            // Create the config file
            File configFile = getFile();
            if (!configFile.exists()) {
                configFile.createNewFile();

                Reader dataInput = new InputStreamReader(plugin.getResource(filename + ".yml"), StandardCharsets.UTF_8);

                config = YamlConfiguration.loadConfiguration(dataInput);
                save();
            }
        } catch(Exception e) {log.severe("Error creating data file: " + e);}
    }

    // Set config values
    public void set(String key, Object value) {
        File configFile = getFile();
        config = YamlConfiguration.loadConfiguration(configFile);

        config.set(key, value);
        save();
    }

    // Save the config
    public void save() {
        try {
            File configFile = getFile();
            config.save(configFile);
        }
        catch (Exception e) {log.severe("Error saving data file: " + e);}
    }
}
