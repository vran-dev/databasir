package com.databasir.core.doc.factory.jdbc;

import com.databasir.core.doc.factory.*;
import com.databasir.core.doc.model.DatabaseDoc;

import java.sql.Connection;
import java.util.Optional;

public class JdbcDatabaseDocFactory implements DatabaseDocFactory {

    private TableDocFactory tableDocFactory;

    private TableColumnDocFactory tableColumnDocFactory;

    private TableTriggerDocFactory tableTriggerDocFactory;

    private TableIndexDocFactory tableIndexDocFactory;

    @Override
    public Optional<DatabaseDoc> create(Connection connection, String database) {
        return Optional.empty();
    }

}
