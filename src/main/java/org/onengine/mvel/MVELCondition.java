package org.onengine.mvel;

import lombok.ToString;
import org.onengine.api.Condition;
import org.onengine.api.Facts;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;

import java.io.Serializable;

/**
 * This class is an implementation of Condition that uses
 * <a href="https://github.com/mvel/mvel">MVEL</a> to evaluate the condition.
 *
 */
@ToString
public class MVELCondition implements Condition {

    private final Serializable compiledExpression;

    /**
     * Create a new {@link MVELCondition}.
     *
     * @param expression the condition written in expression language
     */
    public MVELCondition(String expression) {
        compiledExpression = MVEL.compileExpression(expression);
    }

    /**
     * Create a new {@link MVELCondition}.
     *
     * @param expression the condition written in expression language
     * @param parserContext the MVEL parser context
     */
    public MVELCondition(String expression, ParserContext parserContext) {
        compiledExpression = MVEL.compileExpression(expression, parserContext);
    }

    @Override
    public boolean evaluate(Facts facts) {
        return (boolean) MVEL.executeExpression(compiledExpression, facts.asMap());
    }
}
