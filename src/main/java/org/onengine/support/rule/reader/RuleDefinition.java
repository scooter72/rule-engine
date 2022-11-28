package org.onengine.support.rule.reader;

import org.onengine.api.Rule;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule definition as defined in a rule descriptor.
 * This class encapsulates the static definition of a Rule.
 */
public class RuleDefinition {

    private String name = Rule.DEFAULT_NAME;
    private String description = Rule.DEFAULT_DESCRIPTION;
    private String condition;
    private List<String> actions = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }
}
