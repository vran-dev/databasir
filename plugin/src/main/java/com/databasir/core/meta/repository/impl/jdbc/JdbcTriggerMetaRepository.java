package com.databasir.core.meta.repository.impl.jdbc;

import com.databasir.core.meta.data.TriggerMeta;
import com.databasir.core.meta.repository.TriggerMetaRepository;
import com.databasir.core.meta.repository.condition.TableCondition;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class JdbcTriggerMetaRepository implements TriggerMetaRepository {

    @Override
    public List<TriggerMeta> selectTriggers(Connection connection, TableCondition condition) {
        // note: jdbc not support get triggers
        return Collections.emptyList();
    }
}
