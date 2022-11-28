package org.onengine.mvel;

import org.onengine.api.Action;
import org.onengine.api.Facts;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import lombok.ToString;

/**
 * This class is an implementation of Action that uses
 * <a href="https://github.com/mvel/mvel">MVEL</a> to execute the action.
 */
@ToString
public class MVELAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(MVELAction.class);
    private final String expression;
    private final Serializable compiledExpression;

    /**
     * Create a new VELAction.
     *
     * @param expression the action written in expression language
     */
    public MVELAction(String expression) {
        this.expression = expression;
        compiledExpression = MVEL.compileExpression(expression);
    }

    /**
     * Create a new MVELAction.
     *
     * @param expression the action written in expression language
     * @param parserContext the MVEL parser context
     */
    public MVELAction(String expression, ParserContext parserContext) {
        this.expression = expression;
        compiledExpression = MVEL.compileExpression(expression, parserContext);
    }

    @Override
    public void execute(Facts facts) {
        try {
            MVEL.executeExpression(compiledExpression, facts.asMap());
        } catch (Throwable ex) {
            logger.error("Unable to evaluate expression: '" + expression + "' on facts: " + facts, ex);
            throw ex;
        }
    }
}
