package dev.encode42.copper.config;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Language extends Config {
    private Logger logger;
    private Map<String, Object> globals;

    /**
     * Language configuration file manager.
     * @param fileName Name of the file
     * @param resourceName Name or path to copy from
     */
    public Language(String fileName, String resourceName, Logger logger) {
        super(fileName, resourceName);
        this.logger = logger;
    }

    /**
     * Language configuration file manager.
     * @param fileName Name of the file
     */
    public Language(String fileName, Logger logger) {
        this(fileName, fileName, logger);
    }

    /**
     * Set the logger utilized by {@link Language#log(String)}.
     * @see Language#log(String)
     * @param logger Logger to set
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Set the global placeholders utilized by {@link Language#read(String, Object...)}.
     * @see Language#read(String, Object...)
     * @param placeholders Placeholder map to set
     */
    public void setGlobals(Map<String, Object> placeholders) {
        this.globals = placeholders;
    }

    /**
     * Add a value to the global placeholders map.
     * @param key Key to add
     * @param value Value to set
     */
    public void addGlobal(String key, Object value) {
        this.globals.put(key, value);
    }

    /**
     * Log a message to the logger.
     * @param level Level to log at
     * @param key Message or key to read and process
     * @param placeholders Placeholders to replace
     */
    public void log(Level level, String key, Object ...placeholders) {
        String message = this.read(key, placeholders);
        this.logger.log(level, message);
    }

    /**
     * Log a message to the logger.
     * @param key Message or key to read and process
     * @param placeholders Placeholders to replace
     */
    public void log(String key, Object ...placeholders) {
        this.log(Level.INFO, key, placeholders);
    }

    /**
     * Log a message to the logger.
     * @param level Level to log at
     * @param key Message or key to read and process
     */
    public void log(Level level, String key) {
        this.log(level, key, Collections.EMPTY_MAP);
    }

    /**
     * Log a message to the logger.
     * @param key Message or key to read and process
     */
    public void log(String key) {
        this.log(key, Collections.EMPTY_MAP);
    }

    /**
     * Read and parse a language key value.
     * @param key Path to get language value of
     * @param placeholders Replacement strings (in order)
     * @return Parsed language value
     */
    public String read(String key, Object ...placeholders) {
        String value = this.getReturn(key);
        String prefix = "%";

        if (value.contains(prefix)) {
            // Replace global placeholders
            for (Map.Entry<String, Object> entry : globals.entrySet()) {
                value = value.replace(prefix + entry.getKey(), String.valueOf(entry.getValue()));
            }

            // Iterate placeholders
            for (int i = 0; i < placeholders.length; i++) {
                value = value.replace(prefix + (i + 1), String.valueOf(placeholders[i]));
            }
        }

        return value;
    }
}