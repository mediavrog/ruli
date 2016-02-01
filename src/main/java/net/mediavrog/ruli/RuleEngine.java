package net.mediavrog.ruli;

import java.util.List;

/**
 *  A slightly enhanced `RuleSet`, which saves the last result and can notify a listener upon result availability.
 */
public class RuleEngine extends RuleSet {
    public static final String TAG = RuleEngine.class.getSimpleName();

    public static class Builder extends RuleSet.Builder {
        @Override
        public RuleEngine build() {
            return new RuleEngine(rules);
        }
    }

    public interface OnRulesEvaluatedListener {
        void onResult(boolean isValid);
    }

    Boolean mRulesEvaluationResult;

    OnRulesEvaluatedListener mOnRulesEvaluatedListener;

    public RuleEngine(List<Rule> rules) {
        super(rules, Mode.OR);
    }

    public void setOnRulesEvaluatedListener(OnRulesEvaluatedListener o) {
        mOnRulesEvaluatedListener = o;
        notifyListener();  // will immediately notify listener if rules already evaluated
    }

    public boolean isValid() {
        return mRulesEvaluationResult;
    }

    public boolean isReady() {
        return mRulesEvaluationResult != null;
    }

    void notifyListener() {
        if (mOnRulesEvaluatedListener != null && mRulesEvaluationResult != null)
            mOnRulesEvaluatedListener.onResult(mRulesEvaluationResult);
    }

    @Override
    public boolean evaluate() {
        mRulesEvaluationResult = super.evaluate();
        notifyListener();
        return mRulesEvaluationResult;
    }
}
