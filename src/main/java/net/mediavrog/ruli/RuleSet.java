package net.mediavrog.ruli;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maikvlcek on 1/27/16.
 */
public class RuleSet extends Rule {
    public static final String TAG = RuleSet.class.getSimpleName();

    public static class Builder {
        RuleSet set;

        public Builder() {
            set = new RuleSet();
        }

        public Builder addRule(Rule r) {
            set.addRule(r);
            return this;
        }

        public RuleSet build() {
            return set;
        }
    }

    public enum Mode {
        OR,
        AND
    }

    List<Rule> mRules;
    Mode mMode;

    public RuleSet() {
        this(null, Mode.AND);
    }

    public RuleSet(List<Rule> rules) {
        this(rules, Mode.AND);
    }

    public RuleSet(Mode mode) {
        this(null, mode);
    }

    public RuleSet(List<Rule> rules, Mode mode) {
        setRules(rules);
        mMode = mode;
    }

    public List<Rule> getRules() {
        return mRules;
    }

    public void setRules(List<Rule> rules) {
        mRules = (rules == null ? new ArrayList<Rule>() : rules);
    }

    public void addRule(Rule rule) {
        mRules.add(rule);
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
     * @return True, if all rules evaluated to true. False otherwise.
     */
    boolean evaluateUsingAnd() {
        for (Rule r : mRules) if (!r.evaluate()) return false;
        return !mRules.isEmpty();
    }

    @Override
    public String toString(boolean evaluateResult) {
        StringBuilder s = new StringBuilder();

        s.append(mRules.size()).append(" Rules (").append(mMode).append(")\n");
        for (Rule r : mRules) {
            s.append("  ").append(r.toString(evaluateResult)).append("\n");
        }

        if (evaluateResult) s.append("=> ").append(evaluate());

        return s.toString();
    }
}
