package org.onengine.support.rule.reader;

import org.onengine.api.Rule;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Base class for RuleDefinitionReaders.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractRuleDefinitionReader implements RuleDefinitionReader {

    public List<RuleDefinition> read(Reader reader) throws Exception {
        List<RuleDefinition> ruleDefinitions = new ArrayList<>();
        Iterable<Map<String, Object>> rules = loadRules(reader);
        for (Map<String, Object> rule : rules) {
            ruleDefinitions.add(createRuleDefinition(rule));
        }
        return ruleDefinitions;
    }

    /**
     * Load rules from the given reader as an iterable of Maps.
     *
     * @param reader to read rules from
     * @return an iterable of rule Maps
     * @throws Exception if unable to load rules
     */
    protected abstract Iterable<Map<String, Object>> loadRules(Reader reader) throws Exception;

    /**
     * Create a rule definition.
     *
     * @param map of rule properties
     * @return a rule definition
     */
    protected RuleDefinition createRuleDefinition(Map<String, Object> map) {
        RuleDefinition ruleDefinition = new RuleDefinition();

        String name = (String) map.get("name");
        ruleDefinition.setName(name != null ? name : Rule.DEFAULT_NAME);

        String description = (String) map.get("description");
        ruleDefinition.setDescription(description != null ? description : Rule.DEFAULT_DESCRIPTION);

        String condition = (String) map.get("condition");
        if (condition == null) {
            throw new IllegalArgumentException("The rule condition must be specified");
        }
        ruleDefinition.setCondition(condition);

        List<String> actions = (List<String>) map.get("actions");
        if ((actions == null || actions.isEmpty())) {
            throw new IllegalArgumentException("The rule action(s) must be specified");
        }
        ruleDefinition.setActions(actions);

        return ruleDefinition;
    }
}
