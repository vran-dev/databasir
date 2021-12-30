package com.databasir.core;

import com.databasir.core.meta.repository.*;
import com.databasir.core.meta.repository.impl.jdbc.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
public class DatabasirConfig {

    private IndexMetaRepository indexMetaRepository = new JdbcIndexMetaRepository();

    private TriggerMetaRepository triggerMetaRepository = new JdbcTriggerMetaRepository();

    private ColumnMetaRepository columnMetaRepository = new JdbcColumnMetaRepository();

    private TableMetaRepository tableMetaRepository =
            new JdbcTableMetaRepository(columnMetaRepository, indexMetaRepository, triggerMetaRepository);

    private DatabaseMetaRepository databaseMetaRepository = new JdbcDatabaseMetaRepository(tableMetaRepository);

    private Collection<String> ignoreTableNameRegex = new HashSet<>();

    private Collection<String> ignoreTableColumnNameRegex = new HashSet<>();

    public DatabasirConfig ignoreTable(String tableNameRegex) {
        ignoreTableNameRegex.add(tableNameRegex);
        return this;
    }

    public DatabasirConfig ignoreColumn(String columnNameRegex) {
        ignoreTableColumnNameRegex.add(columnNameRegex);
        return this;
    }
}
