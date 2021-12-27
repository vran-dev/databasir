package com.databasir.core.doc.factory;

import com.databasir.core.doc.factory.jdbc.*;
import lombok.Builder;
import lombok.Getter;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Builder
@Getter
public class DatabaseDocConfiguration {

    private String databaseName;

    private Connection connection;

    @Builder.Default
    private List<String> ignoreTableRegexes = Collections.emptyList();

    @Builder.Default
    private List<String> ignoreColumnRegexes = Collections.emptyList();

    @Builder.Default
    private DatabaseDocFactory databaseDocFactory = new JdbcDatabaseDocFactory();

    @Builder.Default
    private TableDocFactory tableDocFactory = new JdbcTableDocFactory();

    @Builder.Default
    private TableIndexDocFactory tableIndexDocFactory = new JdbcTableIndexDocFactory();

    @Builder.Default
    private TableTriggerDocFactory tableTriggerDocFactory = new JdbcTableTriggerDocFactory();

    @Builder.Default
    private TableColumnDocFactory tableColumnDocFactory = new JdbcTableColumnDocFactory();

    public boolean ignoredTable(String tableName) {
        return ignoreTableRegexes.stream().anyMatch(regex -> Pattern.matches(regex, tableName));
    }

    public boolean ignoredColumn(String column) {
        return ignoreColumnRegexes.stream().anyMatch(regex -> Pattern.matches(regex, column));
    }

}
