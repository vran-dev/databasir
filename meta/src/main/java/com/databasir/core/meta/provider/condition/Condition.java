package com.databasir.core.meta.provider.condition;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

@SuperBuilder
@Getter
public class Condition {

    @NonNull
    private String databaseName;

    private String schemaName;

    @Builder.Default
    private Collection<String> ignoreTableNameRegex = Collections.emptyList();

    @Builder.Default
    private Collection<String> ignoreTableColumnNameRegex = Collections.emptyList();

    public boolean tableIsIgnored(String tableName) {
        return ignoreTableNameRegex.stream().anyMatch(regex -> Pattern.matches(regex, tableName));
    }

    public boolean columnIsIgnored(String column) {
        return ignoreTableColumnNameRegex.stream().anyMatch(regex -> Pattern.matches(regex, column));
    }
}
