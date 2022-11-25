package org.onengine.support.rule.reader;

import org.yaml.snakeyaml.Yaml;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Rule definition YAML reader.
 */
@SuppressWarnings("unchecked")
public class YamlRuleDefinitionReader extends AbstractRuleDefinitionReader {

    private final Yaml yaml;

    /**
     * Create a new {@link YamlRuleDefinitionReader}.
     */
    public YamlRuleDefinitionReader() {
        this(new Yaml());
    }

    /**
     * Create a new {@link YamlRuleDefinitionReader}.
     *
     * @param yaml to use to read rule definitions
     */
    public YamlRuleDefinitionReader(Yaml yaml) {
        this.yaml = yaml;
    }

    @Override
    protected Iterable<Map<String, Object>> loadRules(Reader reader) {
        List<Map<String, Object>> rulesList = new ArrayList<>();
        Iterable<Object> rules = yaml.loadAll(reader);
        for (Object rule : rules) {
            rulesList.add((Map<String, Object>) rule);
        }
        return (Iterable<Map<String, Object>>) rulesList.get(0).get("rules");
    }
}
