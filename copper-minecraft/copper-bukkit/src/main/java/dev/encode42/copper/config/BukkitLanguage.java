package dev.encode42.copper.config;

import org.bukkit.plugin.Plugin;

import java.io.File;

public class BukkitLanguage extends Language {
    public BukkitLanguage(Plugin plugin, String fileName, String resourceName) {
        super(plugin.getDataFolder().getAbsolutePath() + File.separator + fileName, resourceName);
    }

    public BukkitLanguage(Plugin plugin, String fileName) {
        this(plugin, fileName, fileName);
    }
}
