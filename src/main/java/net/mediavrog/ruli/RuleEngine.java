package net.mediavrog.ruli;

import java.util.List;

/**
 * Created by maikvlcek on 1/26/16.
 */
public class RuleEngine extends RuleSet {
    public static final String TAG = RuleEngine.class.getSimpleName();

    public interface OnRulesEvaluatedListener {
        void onResult(boolean isValid);
    }

    Boolean mRulesEvaluationResult;

    OnRulesEvaluatedListener mOnRulesEvaluatedListener;

    public RuleEngine() {
        this(null);
    }

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
