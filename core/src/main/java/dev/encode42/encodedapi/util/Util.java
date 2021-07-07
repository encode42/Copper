package dev.encode42.encodedapi.util;

import java.util.Calendar;
import java.util.Random;

public class Util {
	/**
	 * Check variable equality with more than one comparison
	 * @param object Object to check equality for
	 * @param comparisons Comparisons to make
	 * @return Object is equal to one or more comparisons
	 */
	public static boolean isEqual(Object object, Object ...comparisons) {
		if (object == null) return false;

		// Get the instance for specific comparisons
		String type = object.getClass().getSimpleName();

		// Loop through each comparison
		boolean valid = false;
		for (Object comparison : comparisons) {
			if (valid) break;

			if (type.equals("String")) valid = object.equals(comparison);
			else valid = object == comparison;
		}

		return valid;
	}

	/**
	 * Generates a seeded random with date
	 * @param methods Calendar methods to use
	 * @return Seeded random generator
	 */
	public static SeededRandom getSeededRandom(int ...methods) {
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
	 * Generate a biased random number
	 * Lower-end weighted
	 * @param random Random instance to generate with
	 * @param max Maximum number
	 * @param bias Amount of bias
	 * @return Biased random number
	 */
	public static int biasedRandom(Random random, int max, int bias) {
		double value = Math.pow(random.nextDouble(), bias);
		return (int)(value * max);
	}
}