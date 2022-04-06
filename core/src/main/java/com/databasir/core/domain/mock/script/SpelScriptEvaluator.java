package com.databasir.core.domain.mock.script;

import lombok.RequiredArgsConstructor;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpelScriptEvaluator implements MockScriptEvaluator {

    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    @Override
    public String evaluate(String script, ScriptContext context) {
        Expression expression = spelExpressionParser.parseExpression(script);
        StandardEvaluationContext spelContext = new StandardEvaluationContext(context);
        return expression.getValue(spelContext, String.class);
    }
}
