package net.mediavrog.ruli;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SimpleRuleTest {
    // Contructors
    @Test
    public void constructor_box_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true).evaluate(), true);
        assertEquals(new SimpleRule<Boolean>(Value.as(true), SimpleRule.Comparator.EQ, true).evaluate(), true);
        assertEquals(new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, Value.as(true)).evaluate(), true);
    }

    // Comparator.EQ
    @Test
    public void comparator_eq_true_for_equal_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(true), SimpleRule.Comparator.EQ, Value.as(true)).evaluate(), true);
        assertEquals(new SimpleRule<String>(Value.as("OK"), SimpleRule.Comparator.EQ, Value.as("OK")).evaluate(), true);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.EQ, Value.as(10)).evaluate(), true);
    }

    @Test
    public void comparator_eq_false_for_unequal_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(true), SimpleRule.Comparator.EQ, Value.as(false)).evaluate(), false);
        assertEquals(new SimpleRule<String>(Value.as("OK"), SimpleRule.Comparator.EQ, Value.as("NOK")).evaluate(), false);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.EQ, Value.as(11)).evaluate(), false);
    }

    // Comparator.NEQ
    @Test
    public void comparator_neq_true_for_unequal_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(true), SimpleRule.Comparator.NEQ, Value.as(false)).evaluate(), true);
        assertEquals(new SimpleRule<String>(Value.as("OK"), SimpleRule.Comparator.NEQ, Value.as("NOK")).evaluate(), true);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.NEQ, Value.as(11)).evaluate(), true);
    }

    @Test
    public void comparator_neq_false_for_equal_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(true), SimpleRule.Comparator.NEQ, Value.as(true)).evaluate(), false);
        assertEquals(new SimpleRule<String>(Value.as("OK"), SimpleRule.Comparator.NEQ, Value.as("OK")).evaluate(), false);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.NEQ, Value.as(10)).evaluate(), false);
    }

    // Comparator.GT
    @Test
    public void comparator_gt_true_for_greater_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(true), SimpleRule.Comparator.GT, Value.as(false)).evaluate(), true);
        assertEquals(new SimpleRule<String>(Value.as("zoom"), SimpleRule.Comparator.GT, Value.as("boom")).evaluate(), true);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.GT, Value.as(9)).evaluate(), true);
    }

    @Test
    public void comparator_gt_false_for_lesser_or_equal_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(false), SimpleRule.Comparator.GT, Value.as(true)).evaluate(), false);

        assertEquals(new SimpleRule<String>(Value.as("boom"), SimpleRule.Comparator.GT, Value.as("zoom")).evaluate(), false);
        assertEquals(new SimpleRule<String>(Value.as("boom"), SimpleRule.Comparator.GT, Value.as("boom")).evaluate(), false);

        assertEquals(new SimpleRule<Integer>(Value.as(9), SimpleRule.Comparator.GT, Value.as(10)).evaluate(), false);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.GT, Value.as(10)).evaluate(), false);
    }

    // Comparator.GT_EQ
    @Test
    public void comparator_gt_eq_true_for_greater_or_equal_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(true), SimpleRule.Comparator.GT_EQ, Value.as(false)).evaluate(), true);
        assertEquals(new SimpleRule<Boolean>(Value.as(true), SimpleRule.Comparator.GT_EQ, Value.as(true)).evaluate(), true);

        assertEquals(new SimpleRule<String>(Value.as("zoom"), SimpleRule.Comparator.GT_EQ, Value.as("boom")).evaluate(), true);
        assertEquals(new SimpleRule<String>(Value.as("boom"), SimpleRule.Comparator.GT_EQ, Value.as("boom")).evaluate(), true);

        assertEquals(new SimpleRule<Integer>(Value.as(11), SimpleRule.Comparator.GT_EQ, Value.as(10)).evaluate(), true);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.GT_EQ, Value.as(10)).evaluate(), true);
    }

    @Test
    public void comparator_gt_eq_false_for_lesser_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(false), SimpleRule.Comparator.GT_EQ, Value.as(true)).evaluate(), false);
        assertEquals(new SimpleRule<String>(Value.as("boom"), SimpleRule.Comparator.GT_EQ, Value.as("zoom")).evaluate(), false);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.GT_EQ, Value.as(11)).evaluate(), false);
    }

    // Comparator.LT
    @Test
    public void comparator_lt_true_for_lesser_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(false), SimpleRule.Comparator.LT, Value.as(true)).evaluate(), true);
        assertEquals(new SimpleRule<String>(Value.as("boom"), SimpleRule.Comparator.LT, Value.as("zoom")).evaluate(), true);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.LT, Value.as(11)).evaluate(), true);
    }

    @Test
    public void comparator_lt_false_for_greater_or_equal_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(true), SimpleRule.Comparator.LT, Value.as(false)).evaluate(), false);
        assertEquals(new SimpleRule<Boolean>(Value.as(true), SimpleRule.Comparator.LT, Value.as(true)).evaluate(), false);

        assertEquals(new SimpleRule<String>(Value.as("zoom"), SimpleRule.Comparator.LT, Value.as("boom")).evaluate(), false);
        assertEquals(new SimpleRule<String>(Value.as("boom"), SimpleRule.Comparator.LT, Value.as("boom")).evaluate(), false);

        assertEquals(new SimpleRule<Integer>(Value.as(11), SimpleRule.Comparator.LT, Value.as(10)).evaluate(), false);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.LT, Value.as(10)).evaluate(), false);
    }

    // Comparator.LT_EQ
    @Test
    public void comparator_lt_eq_true_for_lesser_or_equal_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(false), SimpleRule.Comparator.LT_EQ, Value.as(true)).evaluate(), true);
        assertEquals(new SimpleRule<Boolean>(Value.as(false), SimpleRule.Comparator.LT_EQ, Value.as(false)).evaluate(), true);

        assertEquals(new SimpleRule<String>(Value.as("boom"), SimpleRule.Comparator.LT_EQ, Value.as("zoom")).evaluate(), true);
        assertEquals(new SimpleRule<String>(Value.as("boom"), SimpleRule.Comparator.LT_EQ, Value.as("boom")).evaluate(), true);

        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.LT_EQ, Value.as(11)).evaluate(), true);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.LT_EQ, Value.as(10)).evaluate(), true);
    }

    @Test
    public void comparator_lt_eq_false_for_greater_values() throws Exception {
        assertEquals(new SimpleRule<Boolean>(Value.as(true), SimpleRule.Comparator.LT_EQ, Value.as(false)).evaluate(), false);
        assertEquals(new SimpleRule<String>(Value.as("zoom"), SimpleRule.Comparator.LT_EQ, Value.as("boom")).evaluate(), false);
        assertEquals(new SimpleRule<Integer>(Value.as(10), SimpleRule.Comparator.LT_EQ, Value.as(9)).evaluate(), false);
    }
}