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
    @Builder.ObtainVia(method = "ignoreTableNameRegexes")
    private Collection<Pattern> ignoreTableNamePatterns = Collections.emptyList();

    @Builder.Default
    @Builder.ObtainVia(method = "ignoreTableColumnNameRegexes")
    private Collection<Pattern> ignoreTableColumnNamePatterns = Collections.emptyList();

    public boolean tableIsIgnored(String tableName) {
        return ignoreTableNamePatterns.stream().anyMatch(regex -> regex.matcher(tableName).matches());
    }

    public boolean columnIsIgnored(String column) {
        return ignoreTableColumnNamePatterns.stream().anyMatch(regex -> regex.matcher(column).matches());
    }
}
