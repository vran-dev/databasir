package com.databasir.core.doc.factory.jdbc;

import com.databasir.core.doc.factory.DatabaseDocConfig;
import com.databasir.core.doc.factory.TableTriggerDocFactory;
import com.databasir.core.doc.model.TriggerDoc;

import java.sql.DatabaseMetaData;
import java.util.Collections;
import java.util.List;

public class JdbcTableTriggerDocFactory implements TableTriggerDocFactory {

    @Override
    public List<TriggerDoc> create(String tableName, DatabaseMetaData metaData, DatabaseDocConfig configuration) {
        // note: jdbc not support get triggers
        return Collections.emptyList();
    }
}
