package com.databasir.core;

import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.meta.provider.MetaProviders;
import com.databasir.core.meta.provider.condition.Condition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class Databasir {

    private final DatabasirConfig config;

    public Optional<DatabaseMeta> get(Connection connection, String databaseName, String schemaName) {
        // pre compile regex
        List<Pattern> ignoreTableColumnPatterns = config.getIgnoreTableColumnNameRegex().stream()
                .map(Pattern::compile)
                .collect(Collectors.toList());
        List<Pattern> ignoreTableNamePatterns = config.getIgnoreTableNameRegex().stream()
                .map(Pattern::compile)
                .collect(Collectors.toList());
        Condition condition = Condition.builder()
                .databaseName(databaseName)
                .schemaName(schemaName)
                .ignoreTableNamePatterns(ignoreTableNamePatterns)
                .ignoreTableColumnNamePatterns(ignoreTableColumnPatterns)
                .build();
        return MetaProviders
                .of(connection)
                .select(connection, condition);
    }

    public static Databasir of() {
        return of(new DatabasirConfig());
    }

    public static Databasir of(DatabasirConfig config) {
        return new Databasir(config);
    }

}
