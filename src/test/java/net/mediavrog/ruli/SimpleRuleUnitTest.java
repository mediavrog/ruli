package net.mediavrog.irr;

import net.mediavrog.irr.rule.SimpleRule;
import net.mediavrog.irr.rule.Value;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SimpleRuleUnitTest {
    @Test
    public void string_equals_correct() throws Exception {
        assertEquals(new SimpleRule<>(Value.as("OK"), SimpleRule.Comparator.EQ, Value.as("OK")).evaluate(), true);
    }
}