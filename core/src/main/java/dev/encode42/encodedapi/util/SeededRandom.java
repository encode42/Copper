package dev.encode42.encodedapi.util;

import java.util.Random;

public class SeededRandom extends Random {
	long seed;
	public SeededRandom(long seed) {
		super(seed);
		this.seed = seed;
	}

	/**
	 * Gets the seed of this random number generator.
	 * @see SeededRandom#setSeed
	 * @return Stored seed of this instance
	 */
	public long getSeed() {
		return this.seed;
	}

	public void setSeed(long seed) {
		super.setSeed(seed);
		this.seed = seed;
	}
}
