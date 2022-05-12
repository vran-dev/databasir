package com.databasir.core.meta.provider.condition;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class TableCondition extends Condition {

    @NonNull
    private String tableName;

    public static TableCondition of(Condition condition, String tableName) {
        return TableCondition.builder()
                .databaseName(condition.getDatabaseName())
                .tableName(tableName)
                .schemaName(condition.getSchemaName())
                .ignoreTableNamePatterns(condition.getIgnoreTableNamePatterns())
                .ignoreTableColumnNamePatterns(condition.getIgnoreTableColumnNamePatterns())
                .build();
    }

}
