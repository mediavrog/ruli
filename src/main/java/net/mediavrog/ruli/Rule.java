package net.mediavrog.ruli;

/**
 * Abstract rule with `evaluate` interface to provide arbitrary results.
 */
public abstract class Rule {
    /**
     * Evaluates this rule.
     * <p/>
     * Note: Depending on the type of rule, calling this method might lead to a long running operation.
     * If you expect network usage similar, call this method from a non-UI blocking thread.
     *
     * @return True, if the rule is valid. False otherwise.
     */
    public abstract boolean evaluate();

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean evaluate) {
        return "A custom rule. " + (evaluate ? " => " + evaluate() : "");
    }
}
