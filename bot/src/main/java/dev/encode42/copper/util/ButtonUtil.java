package dev.encode42.copper.util;

import java.util.HashMap;
import java.util.Map;

public class ButtonUtil {
    /**
     * Get aspects of a button from its ID.
     * Format: <code>key1:value,key2:value</code>
     * @param id ID to get aspects from
     * @return Map of button aspects
     */
    public static Map<String, String> getButtonAspects(String id) {
        Map<String, String> aspects = new HashMap<>();
        for (String aspect : id.split("(?<!\\\\),")) {
            String[] values = aspect.split("(?<!\\\\):" );
            aspects.put(values[0], values[1]);
        }

        return aspects;
    }
}
