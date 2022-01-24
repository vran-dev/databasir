package com.databasir.core;

import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.meta.repository.*;
import com.databasir.core.meta.repository.condition.Condition;
import com.databasir.core.meta.repository.impl.jdbc.*;
import com.databasir.core.render.Render;
import com.databasir.core.render.RenderConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class Databasir {

    private final DatabasirConfig config;

    public Optional<DatabaseMeta> get(Connection connection, String databaseName) {
        Condition condition = Condition.builder()
                .databaseName(databaseName)
                .ignoreTableNameRegex(config.getIgnoreTableNameRegex())
                .ignoreTableColumnNameRegex(config.getIgnoreTableColumnNameRegex())
                .build();
        return config.getDatabaseMetaRepository().select(connection, condition);
    }

    public void renderAsMarkdown(DatabaseMeta meta, OutputStream out) throws IOException {
        renderAsMarkdown(new RenderConfig(), meta, out);
    }

    public void renderAsMarkdown(RenderConfig config, DatabaseMeta meta, OutputStream stream) throws IOException {
        Render.markdownRender(config).rendering(meta, stream);
    }

    public static Databasir of() {
        return of(new DatabasirConfig());
    }

    public static Databasir of(DatabasirConfig config) {
        TriggerMetaRepository triggerMetaRepository = config.getTriggerMetaRepository();
        if (triggerMetaRepository == null) {
            triggerMetaRepository = new JdbcTriggerMetaRepository();
        }
        IndexMetaRepository indexMetaRepository = config.getIndexMetaRepository();
        if (indexMetaRepository == null) {
            indexMetaRepository = new JdbcIndexMetaRepository();
        }
        ColumnMetaRepository columnMetaRepository = config.getColumnMetaRepository();
        if (columnMetaRepository == null) {
            columnMetaRepository = new JdbcColumnMetaRepository();
        }
        TableMetaRepository tableMetaRepository = config.getTableMetaRepository();
        if (tableMetaRepository == null) {
            tableMetaRepository =
                    new JdbcTableMetaRepository(columnMetaRepository, indexMetaRepository, triggerMetaRepository);
        }
        DatabaseMetaRepository databaseMetaRepository = config.getDatabaseMetaRepository();
        if (databaseMetaRepository == null) {
            databaseMetaRepository = new JdbcDatabaseMetaRepository(tableMetaRepository);
        }
        config.setTriggerMetaRepository(triggerMetaRepository);
        config.setIndexMetaRepository(indexMetaRepository);
        config.setColumnMetaRepository(columnMetaRepository);
        config.setTableMetaRepository(tableMetaRepository);
        config.setDatabaseMetaRepository(databaseMetaRepository);
        return new Databasir(config);
    }

}
