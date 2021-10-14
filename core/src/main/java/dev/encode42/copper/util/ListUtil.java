package dev.encode42.copper.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtil {
    /**
     * Parse a string formatted as a list to a list object.
     * @param string String to parse
     * @return List parsed from string
     */
    public static List<String> toList(String string) {
        if (string == null) {
            return Constants.EMPTY_STRING_LIST;
        }

        return Arrays.stream(string.split("\\[|, |]"))
                .filter(entry -> !entry.isEmpty())
                .toList();
    }

    /**
     * Parse a wildcard list to a list comprised of strings.
     * @param list List to parse and convert
     * @return Parsed list
     */
    public static List<String> toString(List<?> list) {
        List<String> parsed = new ArrayList<>();

        for (Object entry : list) {
            parsed.add(String.valueOf(list));
        }

        return parsed;
    }
}
