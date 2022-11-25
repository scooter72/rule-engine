package org.onengine.api;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.onengine.exceptions.ActionExecutionException;

import java.util.Objects;

/**
 * Basic rule implementation class that provides common methods.
 */
@AllArgsConstructor
@ToString
public class BasicRule implements Rule {
    protected String name;
    protected String description;

    @Override
    public String getName() {
        return name == null ? Rule.super.getName() : name;
    }

    @Override
    public String getDescription() {
        return description == null ? Rule.super.getDescription() : description;
    }

    @Override
    public boolean evaluate(Facts facts) {
        return false;
    }

    @Override
    public void execute(Facts facts) throws ActionExecutionException {

    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        BasicRule basicRule = (BasicRule) o;
        if (!name.equals(basicRule.name))
            return false;
        return Objects.equals(description, basicRule.description);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
