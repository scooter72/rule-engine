package org.onengine;

import org.onengine.api.Facts;
import org.onengine.api.Rule;
import org.onengine.exceptions.ActionExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This rue engine iterates over the set of rules, evaluates the condition
 * of each rule and executes its actions if the condition evaluates to true.
 */
public class RulesEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(RulesEngine.class);
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    /***
     * iterates over the set of rules, evaluates the condition
     *  * of each rule and executes its actions if the condition evaluates to true.
     * @param rules set of rules to evaluate
     * @return rules evaluation results
     */
    public Map<Rule, Boolean> fire(Set<Rule> rules, Facts facts) {
        Map<Rule, Boolean> results = new HashMap<>();

        LOGGER.debug("Rules evaluation started");
        rules.forEach(rule -> executor.execute(() -> fire(new RuleEvaluationContext(rule, facts, results))));
        awaitTerminationAfterShutdown(executor);

        return results;
    }

    private void fire(RuleEvaluationContext context) {
        Rule rule = context.getRule();
        Facts facts = context.getFacts();
        Map<Rule, Boolean> results = context.getResults();

            boolean isRuleViolated = false;

            try {
                LOGGER.debug("Evaluating " + rule.getName());
                isRuleViolated = rule.evaluate(facts);
            } catch (RuntimeException ex) {
                LOGGER.error("Rule '" + rule.getName() + "' evaluated with error", ex);
            }

            results.put(rule, isRuleViolated);

            if (isRuleViolated) {
                LOGGER.debug(rule.getName() + " has been violated, executing actions");
                try {
                    rule.execute(facts);
                } catch (ActionExecutionException ex) {
                    LOGGER.error("Rule '" + rule.getName() + "' performed with error", ex);
                }
        }
    }

    public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
