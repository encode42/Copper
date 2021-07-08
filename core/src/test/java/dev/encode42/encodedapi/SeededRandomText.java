package dev.encode42.encodedapi;

import dev.encode42.encodedapi.util.SeededRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SeededRandomText {
    @Test
    public void testSeededRandom() throws Exception {
        SeededRandom random = new SeededRandom(42);

        Assertions.assertEquals(-1170105035, random.nextInt());
        Assertions.assertEquals(42, random.getSeed());

        random.setSeed(50);
        Assertions.assertEquals(-1160871061, random.nextInt());
        Assertions.assertEquals(50, random.getSeed());
    }
}