package dev.encode42.copper;

import dev.encode42.copper.util.ButtonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ButtonUtilTest {
    @Test
    public void testGetButtonAspects() {
        Map<String, String> testMap = Map.of(
            "name", "684495087534866437",
            "type", "general"
        );
        String testId = "name:" + testMap.get("name") + ",type:" + testMap.get("type");

        Map<String, String> buttonAspects = ButtonUtil.getButtonAspects(testId);
        Assertions.assertEquals(testMap, buttonAspects);
    }
}
