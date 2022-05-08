package com.databasir.core;

import com.databasir.core.meta.repository.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
public class DatabasirConfig {

    private IndexMetaRepository indexMetaRepository;

    private TriggerMetaRepository triggerMetaRepository;

    private ColumnMetaRepository columnMetaRepository;

    private ForeignKeyMetaRepository foreignKeyMetaRepository;

    private TableMetaRepository tableMetaRepository;

    private DatabaseMetaRepository databaseMetaRepository;

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
