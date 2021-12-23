package dev.encode42.copper.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final TypeReference<Map<?, ?>> typeReference = new TypeReference<>(){};

    /**
     * Convert an object into a map.
     * @param object Object to convert
     * @return Converted object
     */
    public static Map<?, ?> fromObject(Object object) {
        return mapper.convertValue(object, typeReference);
    }

    /**
     * Parse a wildcard map to a map with a string key and object value.
     * @param map Map to parse and convert
     * @return Parsed map
     */
    public static Map<String, Object> toStandard(Map<?, ?> map) {
        Map<String, Object> parsed = new HashMap<>();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            parsed.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        return parsed;
    }

    /**
     * Parse a wildcard map to a map with a string key and string value.
     * @param map Map to parse and convert
     * @return Parsed map
     */
    public static Map<String, String> toString(Map<?, ?> map) {
        Map<String, String> parsed = new HashMap<>();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            parsed.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }

        return parsed;
    }

    /**
     * Flattens all nested maps into the root of the map.
     * @param map Map to flatten
     * @return Flattened map
     */
    public static Map<String, Object> flatten(Map<String, Object> map) {
        return map.entrySet().stream()
                .flatMap(MapUtil::flatMap)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Flattens all nested maps into a single entry.
     * @author https://stackoverflow.com/a/62470056
     * @param entry Entry to flatten
     * @return Flattened entry
     */
    private static Stream<Map.Entry<String, Object>> flatMap(Map.Entry<String, Object> entry) {
        if (entry.getValue() instanceof Map) {
            Map<String, Object> parsed = toStandard(fromObject(entry.getValue()));

            return parsed.entrySet().stream()
                    .map(nest -> new AbstractMap.SimpleEntry<>(entry.getKey() + "." + nest.getKey(), nest.getValue()))
                    .flatMap(MapUtil::flatMap);
        }

        return Stream.of(entry);
    }

    /**
     * Merges a set of maps into a single map
     * @author https://blog.knoldus.com/merge-lists-of-map-to-map-java-8-style/
     * @param maps Set of maps to merge
     * @return Merged map
     */
    public static Map<String, Object> merge(Set<Map<String, Object>> maps) {
        Optional<Map<String, Object>> reduce = maps.stream().reduce((firstMap, secondMap) ->
                Stream.concat(firstMap.entrySet().stream(), secondMap.entrySet().stream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (key1, key2) -> key1)));

        return reduce.orElse(null);
    }
}
