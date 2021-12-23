package dev.encode42.copper.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetUtil {
    /**
     * Convert a list into a set.
     * @param list List to convert
     * @return Converted set
     */
    public static Set<?> fromList(List<?> list) {
        return new HashSet<>(list);
    }

    /**
     * Parse a raw set to a string set.
     * @param set Set to parse and convert
     * @return Parsed set
     */
    public static Set<String> toStandard(Set<?> set) {
        Set<String> parsed = new HashSet<>();

        for (Object object : set) {
            parsed.add(String.valueOf(object));
        }

        return parsed;
    }

    /**
     * Check if a set contains at least one element from another list.
     * @param set Set to compare to
     * @param comparison Set to compare with
     * @return Whether the set contains at least one similar element
     */
    public static boolean containsSome(Set<?> set, Set<?> comparison) {
        boolean contains = false;

        for (Object object : comparison) {
            if (set.contains(object)) {
                contains = true;
                break;
            }
        }

        return contains;
    }
}
