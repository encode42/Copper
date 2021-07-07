package dev.encode42.encodedapi.config;

import java.util.Map;

public class Language extends Config {
    private final Map<String, String> placeholders;
    private final String prefix = "%";

    /**
     * Language configuration file manager
     * @param fileName Name of the file
     * @param resourceName Name or path to copy from
     */
    public Language(String fileName, String resourceName) {
        super(fileName, resourceName);
        //Config wrenchConfig = Tixits.getConfig();

        // TODO: get wrench out of here
        // todo: redo this entire class?
        //placeholders = Map.of(
        //    "bot.name", wrenchConfig.getString("bot.name"),
        //    "bot.version", "1.0"
        //);
        placeholders = Map.of("test", "true");
    }

    /**
     * Language configuration file manager
     * @param fileName Name of the file
     */
    public Language(String fileName) {
        this(fileName, fileName);
    }

    /**
     * Read and parse a language value
     * @param path Path to get language value of
     * @param args Replacement strings (in order)
     * @return Parsed language value
     */
    public String read(String path, Object ...args) {
        String value = this.has(path) ? this.getString(path) : path;

        if (value.contains(prefix)) {
            // Replace global placeholders
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                value = value.replace(prefix + entry.getKey(), entry.getValue());
            }

            // Iterate placeholders
            for (int i = 0; i < args.length; i++) {
                value = value.replace(prefix + (i + 1), String.valueOf(args[i]));
            }
        }

        return value;
    }
}