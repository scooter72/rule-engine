package org.onengine.mvel;

import org.junit.Before;
import org.junit.Test;
import org.onengine.api.Facts;

import static org.assertj.core.api.Assertions.assertThat;

public class MVELRuleTest {

    private Facts facts = new Facts();
    private MVELRule mvelRule = new MVELRule().name("rn").description("rd");

    @Before
    public void setUp() {
        mvelRule.when("person.age > 18");
        mvelRule.then("person.setAdult(true);");
    }

    @Test
    public void whenTheRuleIsTriggered_thenConditionShouldBeEvaluated() {
        // given
        facts.put("person", new Person("foo", 20));

        // when
        boolean evaluationResult = mvelRule.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
    }

    @Test
    public void whenTheConditionIsTrue_thenActionsShouldBeExecuted() throws Exception {
        // given
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        mvelRule.execute(facts);

        // then
        assertThat(foo.isAdult()).isTrue();
    }
}
