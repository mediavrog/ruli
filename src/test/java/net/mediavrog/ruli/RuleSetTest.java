package net.mediavrog.ruli;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        SimpleRule falseRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);

        RuleSet set = new RuleSet.Builder(RuleSet.Mode.OR)
                .addRule(falseRule1)
                .addRule(trueRule1)
                .addRule(trueRule2)
                .build();

        assertTrue(set.evaluate());
    }

    @Test
    public void or_ruleset_false_if_all_rules_false() throws Exception {
        SimpleRule falseRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);
        SimpleRule falseRule2 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);

        RuleSet set = new RuleSet.Builder(RuleSet.Mode.AND)
                .addRule(falseRule1)
                .addRule(falseRule2)
                .build();

        assertFalse(set.evaluate());
    }

    // AND sets
    @Test
    public void and_ruleset_false_if_any_rule_false() throws Exception {
        SimpleRule trueRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
        SimpleRule trueRule2 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
        SimpleRule falseRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);

        RuleSet set = new RuleSet.Builder(RuleSet.Mode.AND)
                .addRule(trueRule1)
                .addRule(falseRule1)
                .addRule(trueRule2)
                .build();

        assertFalse(set.evaluate());
    }

    @Test
    public void and_ruleset_true_if_all_rules_true() throws Exception {

    }

    // nesting
    @Test
    public void ruleset_nestable() throws Exception {
        SimpleRule trueRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
        SimpleRule trueRule2 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, true);
        SimpleRule falseRule1 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);

        RuleSet trueSet = new RuleSet.Builder(RuleSet.Mode.OR)
                .addRule(falseRule1)
                .addRule(trueRule1)
                .addRule(trueRule2)
                .build();

        SimpleRule falseRule2 = new SimpleRule<Boolean>(true, SimpleRule.Comparator.EQ, false);

        RuleSet set = new RuleSet.Builder(RuleSet.Mode.AND)
                .addRule(trueSet)
                .addRule(falseRule2)
                .build();

        assertFalse(set.evaluate());
    }

    // readme custom example
    @Test
    public void ice_time_correct() throws Exception {
        // data setup
        class WeatherApi {
            public float getTemp() {
                return 30.4f;
            }
        }

        Integer iceEatenToday = 2;

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

        // the rules
        RuleSet eatingIceTime = new RuleSet.Builder()
                // simple int comparison
                .addRule(new SimpleRule<Integer>(iceEatenToday, SimpleRule.Comparator.LT, 5))
                // float compared with return value of other object
                .addRule(new SimpleRule<Float>(new Value<Float>() {
                    @Override
                    public Float get() {
                        return new WeatherApi().getTemp();
                    }
                }, SimpleRule.Comparator.GT_EQ, 30f))
                // custom evaluation logic
                .addRule(new Rule() {
                    @Override
                    public boolean evaluate() {
                        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
                    }
                })
                .build();

        assertTrue("Eh? But I'm still hungry!", eatingIceTime.evaluate()); // *yum* moar!
    }
}
