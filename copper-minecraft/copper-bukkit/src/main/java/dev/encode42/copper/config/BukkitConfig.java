package dev.encode42.copper.config;

import org.bukkit.plugin.Plugin;

import java.io.File;

public class BukkitConfig extends Config {

    public BukkitConfig(Plugin plugin, String fileName, String resourceName) {
        super(plugin.getDataFolder().getAbsolutePath() + File.separator + fileName, resourceName);
    }

    public BukkitConfig(Plugin plugin, String filename) {
        this(plugin, filename, filename);
    }
}
