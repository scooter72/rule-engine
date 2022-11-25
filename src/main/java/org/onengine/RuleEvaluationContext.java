package org.onengine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.onengine.api.Facts;
import org.onengine.api.Rule;

import java.util.Map;

@Data
@AllArgsConstructor
public class RuleEvaluationContext {
    @Getter
    private Rule rule;
    @Getter
    private Facts facts;
    @Getter
    private Map<Rule, Boolean> results;
}