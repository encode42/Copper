package dev.encode42.copper;

import dev.encode42.copper.util.SeededRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SeededRandomTest {
    @Test
    public void testSeededRandom() {
        SeededRandom random = new SeededRandom(42);

        Assertions.assertEquals(-1170105035, random.nextInt());
        Assertions.assertEquals(42, random.getSeed());

        random.setSeed(50);
        Assertions.assertEquals(-1160871061, random.nextInt());
        Assertions.assertEquals(50, random.getSeed());
    }
}