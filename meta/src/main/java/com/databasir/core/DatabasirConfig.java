package com.databasir.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
public class DatabasirConfig {

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
