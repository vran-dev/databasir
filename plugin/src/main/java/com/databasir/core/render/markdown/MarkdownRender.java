package com.databasir.core.render.markdown;

import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.meta.data.TableMeta;
import com.databasir.core.render.Render;
import com.databasir.core.render.RenderConfig;
import lombok.Getter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MarkdownRender implements Render {

    @Getter
    private final RenderConfig config;

    protected MarkdownRender(RenderConfig config) {
        this.config = config;
    }

    public static MarkdownRender of(RenderConfig config) {
        return new MarkdownRender(config);
    }

    @Override
    public void rendering(DatabaseMeta meta,
                          OutputStream outputStream) throws IOException {
        MarkdownBuilder contentBuilder = MarkdownBuilder.builder();
        contentBuilder.primaryTitle(meta.getDatabaseName());
        if (config.getRenderTables()) {
            for (TableMeta table : meta.getTables()) {
                buildTableName(contentBuilder, table);
                if (config.getRenderColumns()) {
                    buildColumns(contentBuilder, table);
                }
                if (config.getRenderIndexes()) {
                    buildIndexes(contentBuilder, table);
                }
                if (config.getRenderTriggers()) {
                    buildTriggers(contentBuilder, table);
                }
            }
        }
        outputStream.write(contentBuilder.build().getBytes(StandardCharsets.UTF_8));
    }

    private void buildTableName(MarkdownBuilder contentBuilder, TableMeta table) {
        String tableName;
        if (table.getComment() == null || table.getComment().trim().isEmpty()) {
            tableName = table.getName();
        } else {
            tableName = table.getName() + "(" + table.getComment() + ")";
        }
        contentBuilder.secondTitle(tableName);
    }

    private void buildColumns(MarkdownBuilder contentBuilder, TableMeta table) {
        contentBuilder.unorderedList(Collections.singletonList("columns"));
        List<List<String>> allColumnRows = table.getColumns()
                .stream()
                .map(column -> config.getColumnTitleAndValueMapping()
                        .values()
                        .stream()
                        .map(mapping -> mapping.apply(column))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        contentBuilder.table(tableTitles(), allColumnRows);
    }

    private void buildIndexes(MarkdownBuilder contentBuilder, TableMeta table) {
        contentBuilder.unorderedList(Collections.singletonList("indexes"));
        List<List<String>> allIndexRows = table.getIndexes().stream()
                .map(index -> config.getIndexTitleAndValueMapping()
                        .values()
                        .stream()
                        .map(mapping -> mapping.apply(index))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        contentBuilder.table(indexTitles(), allIndexRows);
    }

    private void buildTriggers(MarkdownBuilder contentBuilder, TableMeta table) {
        if (table.getTriggers() == null || table.getTriggers().isEmpty()) {
            return;
        }

        contentBuilder.unorderedList(Collections.singletonList("triggers"));
        List<List<String>> allRows = table.getTriggers().stream()
                .map(trigger -> config.getTriggerTitleAndValueMapping()
                        .values()
                        .stream()
                        .map(mapping -> mapping.apply(trigger))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        contentBuilder.table(triggerTitles(), allRows);
    }

    private List<String> tableTitles() {
        return new ArrayList<>(config.getColumnTitleAndValueMapping().keySet());
    }

    private List<String> indexTitles() {
        return new ArrayList<>(config.getIndexTitleAndValueMapping().keySet());
    }

    private List<String> triggerTitles() {
        return new ArrayList<>(config.getTriggerTitleAndValueMapping().keySet());
    }
}
