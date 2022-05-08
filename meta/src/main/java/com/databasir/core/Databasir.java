package com.databasir.core;

import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.meta.provider.MetaProviders;
import com.databasir.core.meta.provider.condition.Condition;
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

    public Optional<DatabaseMeta> get(Connection connection, String databaseName, String schemaName) {
        Condition condition = Condition.builder()
                .databaseName(databaseName)
                .schemaName(schemaName)
                .ignoreTableNameRegex(config.getIgnoreTableNameRegex())
                .ignoreTableColumnNameRegex(config.getIgnoreTableColumnNameRegex())
                .build();
        return MetaProviders
                .of(connection)
                .select(connection, condition);
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
        return new Databasir(config);
    }

}
