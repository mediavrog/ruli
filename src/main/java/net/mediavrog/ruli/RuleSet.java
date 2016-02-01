package net.mediavrog.ruli;

import java.util.ArrayList;
import java.util.List;

/**
 * Evaluate groups of rules with logical AND or OR. Implements the `Rule` interface to allow nesting.
 */
public class RuleSet extends Rule {
    public static final String TAG = RuleSet.class.getSimpleName();

    public static Mode DEFAULT_MODE = Mode.AND;

    public static class Builder {
        Mode mode;
        ArrayList<Rule> rules;

        public Builder() {
            this(DEFAULT_MODE);
        }

        public Builder(Mode m) {
            mode = m;
            rules = new ArrayList<Rule>();
        }

        /**
         * Adds a rule to the collection.
         *
         * Pass clones of custom, mutable rules for thread-safety.
         *
         * @param rule The rule to add.
         * @return The Builder object.
         */
        public Builder addRule(Rule rule) {
            rules.add(rule);
            return this;
        }

        public RuleSet build() {
            return new RuleSet(rules, mode);
        }
    }

    public enum Mode {
        OR,
        AND
    }

    final private Rule[] mRules;
    final private Mode mMode;

    public RuleSet(List<Rule> rules) {
        this(rules, DEFAULT_MODE);
    }

    public RuleSet(Mode mode) {
        this(new Rule[0], mode);
    }

    public RuleSet(List<Rule> rules, Mode mode) {
        this(rules.toArray(new Rule[rules.size()]), mode);
    }

    public RuleSet(Rule[] rules, Mode mode) {
        mRules = rules;
        mMode = mode;
    }

    @Override
    public boolean evaluate() {
        switch (mMode) {
            default:
            case AND:
                return evaluateUsingAnd();
            case OR:
                return evaluateUsingOr();
        }
    }

    /**
     * Evaluates the set of rules using logical OR.
     * Returns immediately if any rule evaluates to true, so evaluation of each rule is not guaranteed.
     *
     * @return True, if any rule evaluated to true. False otherwise.
     */
    boolean evaluateUsingOr() {
        for (Rule r : mRules) if (r.evaluate()) return true;
        return false;
    }

    /**
     * Evaluates the set of rules using logical AND.
     * Returns immediately if any rule evaluates to false, so evaluation of each rule is not guaranteed.
     *
     * @return True, if all rules evaluated to true. False if no rules given or one rule was false.
     */
    boolean evaluateUsingAnd() {
        for (Rule r : mRules) if (!r.evaluate()) return false;
        return mRules.length > 0; // result is false for empty lists
    }

    @Override
    public String toString(boolean evaluateResult) {
        StringBuilder s = new StringBuilder();

        s.append(mRules.length).append(" Rules (").append(mMode).append(")\n");
        for (Rule r : mRules) {
            s.append("  ").append(r.toString(evaluateResult)).append("\n");
        }

        if (evaluateResult) s.append("=> ").append(evaluate());

        return s.toString();
    }
}
