package net.mediavrog.ruli;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SimpleRuleUnitTest {
    @Test
    public void string_equals_correct() throws Exception {
        assertEquals(new SimpleRule<String>(Value.as("OK"), SimpleRule.Comparator.EQ, Value.as("OK")).evaluate(), true);
    }
}