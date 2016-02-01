package net.mediavrog.ruli;

import org.junit.Test;

import static org.junit.Assert.*;

public class RuleSetTest {

    // TODO: define always true rule
    // TODO define always false rule

    // Constructors
    @Test
    public void sensible_constructors() throws Exception {

    }

    // add rule
    @Test
    public void add_rule_adds_rules_to_empty() throws Exception {

    }

    @Test
    public void add_rule_adds_rules_to_existing() throws Exception {

    }

    // OR sets
    @Test
    public void or_ruleset_true_if_any_rule_true() throws Exception {
        SimpleRule trueRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
        SimpleRule trueRule2 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);

        RuleSet set = new RuleSet(RuleSet.Mode.OR);
        set.addRule(trueRule1);
        set.addRule(trueRule2);

        assertTrue(set.evaluate());
    }

    @Test
    public void or_ruleset_false_if_all_rules_false() throws Exception {
        SimpleRule falseRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);
        SimpleRule falseRule2 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);

        RuleSet set = new RuleSet(RuleSet.Mode.OR);
        set.addRule(falseRule1);
        set.addRule(falseRule2);

        assertFalse(set.evaluate());
    }

    // AND sets
    @Test
    public void and_ruleset_false_if_any_rule_false() throws Exception {
        SimpleRule trueRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
        SimpleRule trueRule2 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);

        SimpleRule falseRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);

        RuleSet set = new RuleSet(RuleSet.Mode.OR);
        set.addRule(falseRule1);
        set.addRule(falseRule2);

        assertFalse(set.evaluate());
    }

    @Test
    public void and_ruleset_true_if_all_rules_true() throws Exception {

    }
}
