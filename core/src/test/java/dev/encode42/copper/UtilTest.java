package dev.encode42.copper;

import dev.encode42.copper.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class UtilTest {
    @Test
    public void isEqualTest() {
        String testString = "This is a test";
        Assertions.assertTrue(Util.isEqualSome(testString, "Hello World", "This is a test"));
        Assertions.assertFalse(Util.isEqualSome(testString, "Hello World"));

        int testNumber = 42;
        Assertions.assertTrue(Util.isEqualSome(testNumber, "Test", Collections.EMPTY_LIST, 42));
        Assertions.assertFalse(Util.isEqualSome(testNumber, 7, 8, 9));

        String nullString = null;
        Assertions.assertTrue(Util.isEqualSome(null, nullString, testString));
        Assertions.assertTrue(Util.isEqualAll(null, nullString, nullString));
    }

    // todo
    @Test
    public void datedRandomTest() {

    }

    // todo
    @Test
    public void biasedRandomTest() {

    }
}