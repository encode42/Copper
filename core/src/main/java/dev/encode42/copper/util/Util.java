package dev.encode42.copper.util;

import java.util.*;

public class Util {
	/**
	 * Check variable equality with more than one comparison.
	 * @param object Object to check equality for
	 * @param comparisons Comparisons to make
	 * @return Object is equal to one or more comparisons
	 */
	public static boolean isEqualSome(Object object, Object ...comparisons) {
		return isEqual(false, object, comparisons);
	}

	public static boolean isEqualAll(Object object, Object ...comparisons) {
		return isEqual(true, object, comparisons);
	}

	public static boolean isEqual(boolean all, Object object, Object ...comparisons) {
		// Get the instance for specific comparisons
		String type = object == null ? "null" : object.getClass().getSimpleName();

		// Loop through each comparison
		boolean valid = false;
		for (Object comparison : comparisons) {
			boolean equal = type.equals("String") && object.equals(comparison) || object == comparison;

			if (!equal && all) {
				break;
			}

			if (equal && !all) {
				valid = true;
				break;
			}
		}

		return all != valid;
	}

	/**
	 * Generates a seeded random with calendar date methods.
	 * @param methods Calendar methods to use
	 * @return Seeded random generator
	 */
	public static SeededRandom getDatedRandom(int ...methods) {
		// Calendar defaults
		Calendar calendar = Calendar.getInstance();
		if (methods.length == 0) {
			methods = new int[]{ Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH };
		}

		// Add the time
		StringBuilder string = new StringBuilder();
		for (int method : methods) {
			string.append(calendar.get(method));
		}

		long seed = Long.parseLong(string.toString());

		// Make the random instance
		return new SeededRandom(seed);
	}

	/**
	 * Generate a lower-end weighted random number.
	 * @param random Random instance to generate with
	 * @param max Maximum number
	 * @param bias Amount of bias
	 * @return Biased random number
	 */
	public static int biasedRandom(Random random, int max, int bias) {
		double value = Math.pow(random.nextDouble(), bias);
		return (int)(value * max);
	}

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
	 * Parse a wildcard map to a map with a known key and value.
	 * @param map Map to parse and convert
	 * @return Parsed map
	 */
	public static Map<String, Object> parseMap(Map<?, ?> map) {
		Map<String, Object> parsed = new HashMap<>();

		for (Map.Entry<?, ?> entry : map.entrySet()) {
			parsed.put(String.valueOf(entry.getKey()), entry.getValue());
		}

		return parsed;
	}

	/**
	 * Get the current epoch time.
	 * @return Current epoch time
	 */
	public static long getEpoch() {
		String ms = String.valueOf(System.currentTimeMillis());
		String epoch = ms.substring(0, ms.length() - 3);

		return Long.parseLong(epoch);
	}
}
