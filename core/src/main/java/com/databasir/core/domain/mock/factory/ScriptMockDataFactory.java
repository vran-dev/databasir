package com.databasir.core.domain.mock.factory;

import com.databasir.core.domain.mock.script.MockScriptEvaluator;
import com.databasir.dao.enums.MockDataType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
@RequiredArgsConstructor
public class ScriptMockDataFactory implements MockDataFactory {

    private final MockScriptEvaluator mockScriptEvaluator;

    @Override
    public boolean accept(MockColumnRule rule) {
        return rule.getMockDataType() == MockDataType.SCRIPT;
    }

    @Override
    public String create(MockColumnRule rule) {
        return mockScriptEvaluator.evaluate(rule.getMockDataScript().get(), new MockScriptEvaluator.ScriptContext());
    }

}
