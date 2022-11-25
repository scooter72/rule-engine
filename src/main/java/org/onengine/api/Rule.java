package org.onengine.api;

import org.onengine.exceptions.ActionExecutionException;

/**
 * Abstraction for a rule that can be fired by a rules engine.
 */
public interface Rule {
    /**
     * Default rule name.
     */
    String DEFAULT_NAME = "rule";

    /**
     * Default rule description.
     */
    String DEFAULT_DESCRIPTION = "description";

    /**
     * Getter for rule name.
     * @return the rule name
     */
    default String getName() {
        return DEFAULT_NAME;
    }

    /**
     * Getter for rule description.
     * @return rule description
     */
    default String getDescription() {
        return DEFAULT_DESCRIPTION;
    }
    /**
     * This method implements the rule's condition(s).
     * <strong>Implementations should handle any runtime exception and return true/false accordingly</strong>
     *
     * @return true if the rule should be applied given the provided facts, false otherwise
     */
    boolean evaluate(Facts facts);

    /**
     * This method implements the rule's action(s).
     * @throws Exception thrown if an exception occurs when performing action(s)
     */
    void execute(Facts facts) throws ActionExecutionException;
}
