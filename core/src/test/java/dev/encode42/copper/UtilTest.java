package dev.encode42.copper;

import dev.encode42.copper.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class UtilTest {
    @Test
    public void isEqualTest() {
        String testString = "This is a test";
        Assertions.assertTrue(Util.isEqual(testString, "Hello World", "This is a test"));
        Assertions.assertFalse(Util.isEqual(testString, "Hello World"));

        int testNumber = 42;
        Assertions.assertTrue(Util.isEqual(testNumber, "Test", Collections.EMPTY_LIST, 42));
        Assertions.assertFalse(Util.isEqual(testNumber, 7, 8, 9));
    }

    @Test
    public void datedRandomTest() {

    }

    @Test
    public void biasedRandomTest() {

    }
}