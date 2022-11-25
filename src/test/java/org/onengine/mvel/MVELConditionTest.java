package org.onengine.mvel;

import org.junit.Test;
import org.mvel2.ParserContext;
import org.onengine.api.Condition;
import org.onengine.api.Facts;

import static org.assertj.core.api.Assertions.assertThat;

public class MVELConditionTest {

    @Test
    public void testMVELExpressionEvaluation() {
        // given
        Condition isAdult = new MVELCondition("person.age > 18");
        Facts facts = new Facts();
        facts.put("person", new Person("foo", 20));

        // when
        boolean evaluationResult = isAdult.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
    }

    // Note this behaviour is different in SpEL, where a missing fact is silently ignored and returns false
    @Test(expected = RuntimeException.class)
    public void whenDeclaredFactIsNotPresent_thenShouldThrowRuntimeException() {
        // given
        Condition isHot = new MVELCondition("temperature > 30");
        Facts facts = new Facts();

        // when
        boolean evaluationResult = isHot.evaluate(facts);

        // then
        // expected exception
    }

    @Test
    public void testMVELConditionWithExpressionAndParserContext() {
        // given
        ParserContext context = new ParserContext();
        context.addPackageImport("java.util");
        Condition condition = new MVELCondition("return new java.util.Random(123).nextBoolean();", context);
        Facts facts = new Facts();
        // when
        boolean evaluationResult = condition.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
    }
}
