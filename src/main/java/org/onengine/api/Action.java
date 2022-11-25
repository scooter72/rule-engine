package org.onengine.api;

import org.onengine.exceptions.ActionExecutionException;

/**
 * This interface represents a rule's action.
 */
@FunctionalInterface
public interface Action {

    /**
     * Execute the action when the rule's condition evaluates to true.
     *
     * @param facts known at the time of execution of the action
     * @throws ActionExecutionException when unable to execute the action
     */
    void execute(Facts facts) throws ActionExecutionException;
}
