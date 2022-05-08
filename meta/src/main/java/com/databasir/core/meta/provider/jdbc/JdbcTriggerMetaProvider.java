package com.databasir.core.meta.provider.jdbc;

import com.databasir.core.meta.data.TriggerMeta;
import com.databasir.core.meta.provider.TriggerMetaProvider;
import com.databasir.core.meta.provider.condition.TableCondition;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class JdbcTriggerMetaProvider implements TriggerMetaProvider {

    @Override
    public List<TriggerMeta> selectTriggers(Connection connection, TableCondition condition) {
        // note: jdbc not support get triggers
        return Collections.emptyList();
    }
}
