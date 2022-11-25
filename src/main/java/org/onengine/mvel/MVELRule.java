package org.onengine.mvel;

import lombok.ToString;
import org.onengine.api.BasicRule;
import org.onengine.exceptions.ActionExecutionException;
import org.onengine.support.rule.reader.RuleDefinition;
import org.onengine.api.Action;
import org.onengine.api.Condition;
import org.onengine.api.Facts;
import org.onengine.api.Rule;
import org.mvel2.ParserContext;

import java.util.ArrayList;
import java.util.List;

/**
 * A Rule implementation that uses
 * <a href="https://github.com/mvel/mvel">MVEL</a> to evaluate and execute the rule.
 * MVEL is an expression language based on Java-syntax.
 */
@ToString
public class MVELRule extends BasicRule {
    private Condition condition = Condition.FALSE;
    private final List<Action> actions = new ArrayList<>();

    private final ParserContext parserContext;

    public MVELRule(ParserContext parserContext) {
        super(Rule.DEFAULT_NAME, Rule.DEFAULT_DESCRIPTION);
        this.parserContext = parserContext;
    }

    public MVELRule() {
        this(new ParserContext());
    }

    @Override
    public boolean evaluate(Facts facts) {
        return condition.evaluate(facts);
    }

    @Override
    public void execute(Facts facts) throws ActionExecutionException {
        for (Action action : actions) {
            action.execute(facts);
        }
    }

    /**
     * Set rule name.
     *
     * @param name of the rule
     * @return this rule
     */
    public MVELRule name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set rule description.
     *
     * @param description of the rule
     * @return this rule
     */
    public MVELRule description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Specify the rule's condition as MVEL expression.
     * @param condition of the rule
     * @return this rule
     */
    public MVELRule when(String condition) {
        this.condition = new MVELCondition(condition, parserContext);
        return this;
    }

    /**
     * Add an action specified as an MVEL expression to the rule.
     * @param action to add to the rule
     * @return this rule
     */
    public MVELRule then(String action) {
        this.actions.add(new MVELAction(action, parserContext));
        return this;
    }

    public static Rule createSimpleRule(RuleDefinition ruleDefinition, ParserContext parserContext) {
        MVELRule mvelRule = new MVELRule(parserContext)
                .name(ruleDefinition.getName())
                .description(ruleDefinition.getDescription())
                .when(ruleDefinition.getCondition());
        for (String action : ruleDefinition.getActions()) {
            mvelRule.then(action);
        }
        return mvelRule;
    }
}
