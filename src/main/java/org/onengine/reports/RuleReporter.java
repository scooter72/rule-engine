package org.onengine.reports;

import org.onengine.api.Rule;

import java.io.PrintStream;
import java.util.Map;

/**
 * Generate reports of rules evaluation.
 */
public class RuleReporter {

    public void writeReport(PrintStream out, Map<Rule, Boolean> results) {

        for (Map.Entry<Rule, Boolean> entry : results.entrySet()) {
            out.printf("Rule '%s' violation -> %s%n", entry.getKey().getName(), entry.getValue());
        }
    }
}
