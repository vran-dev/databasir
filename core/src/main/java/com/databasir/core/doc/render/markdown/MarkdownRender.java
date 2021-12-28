package com.databasir.core.doc.render.markdown;

import com.databasir.core.doc.model.DatabaseDoc;
import com.databasir.core.doc.model.TableDoc;
import com.databasir.core.doc.render.Render;
import com.databasir.core.doc.render.RenderConfiguration;
import lombok.Getter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class MarkdownRender implements Render {

    @Getter
    private final RenderConfiguration config;

    protected MarkdownRender(RenderConfiguration config) {
        this.config = config;
    }

    public static MarkdownRender of(RenderConfiguration config) {
        return new MarkdownRender(config);
    }

    @Override
    public void rendering(DatabaseDoc doc,
                          OutputStream outputStream) throws IOException {
        MarkdownBuilder contentBuilder = MarkdownBuilder.builder();
        contentBuilder.primaryTitle(doc.getDatabaseName());
        if (config.getRenderTables()) {
            for (TableDoc table : doc.getTables()) {
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

    private void buildTableName(MarkdownBuilder contentBuilder, TableDoc table) {
        String tableName;
        if (table.getTableComment() == null || table.getTableComment().trim().isEmpty()) {
            tableName = table.getTableName();
        } else {
            tableName = table.getTableName() + "(" + table.getTableComment() + ")";
        }
        contentBuilder.secondTitle(tableName);
    }

    private void buildColumns(MarkdownBuilder contentBuilder, TableDoc table) {
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

    private void buildIndexes(MarkdownBuilder contentBuilder, TableDoc table) {
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

    private void buildTriggers(MarkdownBuilder contentBuilder, TableDoc table) {
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
