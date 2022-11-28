package org.onengine.api;

/**
 * This interface represents a rule's condition.
 */
@FunctionalInterface
public interface Condition {

    /**
     * Evaluate the condition according to the known facts.
     *
     * @param facts known when evaluating the rule.
     *
     * @return true if the rule should be triggered, false otherwise
     */
    boolean evaluate(Facts facts);

    /**
     * A NoOp Condition that always returns false.
     */
    Condition FALSE = facts -> false;

    /**
     * A NoOp Condition that always returns true.
     */
    Condition TRUE = facts -> true;
}