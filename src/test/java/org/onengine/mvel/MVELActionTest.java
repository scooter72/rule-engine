package org.onengine.mvel;

import org.junit.Test;
import org.onengine.api.Action;
import org.onengine.api.Facts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MVELActionTest {

    @Test
    public void testMVELActionExecution() throws Exception {
        // given
        Action markAsAdult = new MVELAction("person.setAdult(true);");
        Facts facts = new Facts();
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        markAsAdult.execute(facts);

        // then
        assertThat(foo.isAdult()).isTrue();
    }


    @Test
    public void testMVELActionExecutionWithFailure() {
        // given
        Action action = new MVELAction("person.setBlah(true);");
        Facts facts = new Facts();
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        assertThatThrownBy(() -> action.execute(facts))
                // then
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Error: unable to resolve method: org.jeasy.rules.mvel.Person.setBlah(java.lang.Boolean)");
    }
}
