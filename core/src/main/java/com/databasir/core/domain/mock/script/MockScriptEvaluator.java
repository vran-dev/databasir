package com.databasir.core.domain.mock.script;

import lombok.Data;

public interface MockScriptEvaluator {

    String evaluate(String script, ScriptContext context);

    @Data
    class ScriptContext {

    }

}
