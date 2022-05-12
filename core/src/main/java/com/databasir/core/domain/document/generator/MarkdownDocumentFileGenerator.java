package com.databasir.core.domain.document.generator;

import com.alibaba.excel.util.StringUtils;
import com.databasir.common.SystemException;
import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.DocumentTemplatePropertiesResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.core.domain.document.service.DocumentTemplateService;
import com.databasir.core.render.markdown.MarkdownBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class MarkdownDocumentFileGenerator implements DocumentFileGenerator {

    private final DocumentTemplateService documentTemplateService;

    @Override
    public boolean support(DocumentFileType type) {
        return type == DocumentFileType.MARKDOWN;
    }

    @Override
    public void generate(DocumentFileGenerateContext context, OutputStream outputStream) {
        DocumentTemplatePropertiesResponse templateProperties = documentTemplateService.getAllProperties();
        String fileName = context.getDatabaseDocument().getDatabaseName() + "-" + UUID.randomUUID().toString();
        String data = markdownData(context, templateProperties);
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile(fileName, ".md");
            Path path = Files.writeString(tempFile, data, StandardCharsets.UTF_8);
            byte[] bytes = Files.readAllBytes(path);
            outputStream.write(bytes);
        } catch (IOException e) {
            if (tempFile != null) {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException ex) {
                    log.warn("delete temp file error", ex);
                }
            }
            throw new SystemException("System error");
        }
    }

    private String markdownData(DocumentFileGenerateContext context,
                                DocumentTemplatePropertiesResponse properties) {
        DatabaseDocumentResponse doc = context.getDatabaseDocument();
        MarkdownBuilder builder = MarkdownBuilder.builder();
        builder.primaryTitle(doc.getDatabaseName());
        // overview
        overviewBuild(builder, doc);
        // field map by table name
        Map<String, String> columnTitleMap = properties.getColumnFieldNameProperties()
                .stream()
                .collect(Collectors.toMap(d -> d.getKey(),
                        d -> Objects.requireNonNullElse(d.getValue(), d.getDefaultValue())));
        Map<String, String> indexTitleMap = properties.getIndexFieldNameProperties()
                .stream()
                .collect(Collectors.toMap(d -> d.getKey(),
                        d -> Objects.requireNonNullElse(d.getValue(), d.getDefaultValue())));
        Map<String, String> triggerTitleMap = properties.getTriggerFieldNameProperties()
                .stream()
                .collect(Collectors.toMap(d -> d.getKey(),
                        d -> Objects.requireNonNullElse(d.getValue(), d.getDefaultValue())));
        Map<String, String> foreignKeyTitleMap = properties.getForeignKeyFieldNameProperties()
                .stream()
                .collect(Collectors.toMap(d -> d.getKey(),
                        d -> Objects.requireNonNullElse(d.getValue(), d.getDefaultValue())));
        // table document build
        doc.getTables().forEach(table -> {
            if (StringUtils.isNotBlank(table.getComment())) {
                builder.secondTitle(table.getName() + " /\\*" + table.getComment() + "\\*/");
            } else {
                builder.secondTitle(table.getName());
            }
            columnBuild(builder, table, columnTitleMap);
            indexBuild(builder, table, indexTitleMap);
            foreignKeyBuild(builder, table, foreignKeyTitleMap);
            triggerBuild(builder, table, triggerTitleMap);
        });
        return builder.build();
    }

    private void overviewBuild(MarkdownBuilder builder, DatabaseDocumentResponse doc) {
        builder.secondTitle("overview");
        List<List<String>> overviewContent = new ArrayList<>();
        for (int i = 0; i < doc.getTables().size(); i++) {
            TableDocumentResponse table = doc.getTables().get(i);
            List<String> row = List.of((i + 1) + "",
                    Objects.requireNonNullElse(table.getName(), ""),
                    Objects.requireNonNullElse(table.getType(), ""),
                    Objects.requireNonNullElse(table.getComment(), ""));
            overviewContent.add(row);
        }
        builder.table(List.of("", "表名", "类型", "备注"), overviewContent);
    }

    private void columnBuild(MarkdownBuilder builder,
                             TableDocumentResponse table,
                             Map<String, String> titleMap) {
        Function<TableDocumentResponse.ColumnDocumentResponse, String>
                columnDefaultValueMapping = column -> {
            if (Objects.equals(column.getNullable(), "YES")) {
                return Objects.requireNonNullElse(column.getDefaultValue(), "null");
            } else {
                return Objects.requireNonNullElse(column.getDefaultValue(), "");
            }
        };
        builder.thirdTitle("Columns");
        List<List<String>> columnContent = new ArrayList<>();
        for (int i = 0; i < table.getColumns().size(); i++) {
            var column = table.getColumns().get(i);
            String type;
            if (column.getDecimalDigits() == null
                    || Objects.requireNonNullElse(column.getDecimalDigits(), 0) == 0) {
                type = column.getType() + "(" + column.getSize() + ")";
            } else {
                type = column.getType() + "(" + column.getSize() + "," + column.getDecimalDigits() + ")";
            }
            columnContent.add(List.of((i + 1) + "",
                    column.getName(),
                    type,
                    column.getIsPrimaryKey() ? "YES" : "NO",
                    column.getNullable(),
                    column.getAutoIncrement(),
                    columnDefaultValueMapping.apply(column),
                    Objects.requireNonNullElse(column.getComment(), "")));
        }
        builder.table(
                List.of(
                        "",
                        titleMap.getOrDefault("name", "name"),
                        titleMap.getOrDefault("type", "type"),
                        titleMap.getOrDefault("isPrimaryKey", "isPrimaryKey"),
                        titleMap.getOrDefault("nullable", "nullable"),
                        titleMap.getOrDefault("autoIncrement", "autoIncrement"),
                        titleMap.getOrDefault("defaultValue", "defaultValue"),
                        titleMap.getOrDefault("comment", "comment")
                ),
                columnContent
        );
    }

    private void indexBuild(MarkdownBuilder builder,
                            TableDocumentResponse table,
                            Map<String, String> titleMap) {
        builder.thirdTitle("Indexes");
        List<List<String>> indexContent = new ArrayList<>();
        for (int i = 0; i < table.getIndexes().size(); i++) {
            var index = table.getIndexes().get(i);
            String columnNames = String.join(", ", index.getColumnNames());
            String isUnique = index.getIsUnique() ? "YES" : "NO";
            indexContent.add(List.of((i + 1) + "", index.getName(), isUnique, columnNames));
        }
        builder.table(
                List.of(
                        "",
                        titleMap.getOrDefault("name", "name"),
                        titleMap.getOrDefault("isUnique", "isUnique"),
                        titleMap.getOrDefault("columnNames", "columnNames")
                ),
                indexContent
        );
    }

    private void foreignKeyBuild(MarkdownBuilder builder,
                                 TableDocumentResponse table,
                                 Map<String, String> titleMap) {
        if (!table.getForeignKeys().isEmpty()) {
            List<List<String>> foreignKeys = new ArrayList<>();
            builder.thirdTitle("Foreign Keys");
            for (int i = 0; i < table.getForeignKeys().size(); i++) {
                TableDocumentResponse.ForeignKeyDocumentResponse fk = table.getForeignKeys().get(i);
                List<String> item = List.of(
                        (i + 1) + "",
                        Objects.requireNonNullElse(fk.getFkName(), ""), fk.getFkColumnName(),
                        Objects.requireNonNullElse(fk.getPkName(), ""), fk.getPkTableName(),
                        fk.getPkColumnName(),
                        fk.getUpdateRule(), fk.getDeleteRule()
                );
                foreignKeys.add(item);
            }
            builder.table(
                    List.of(
                            "",
                            titleMap.getOrDefault("fkName", "fkName"),
                            titleMap.getOrDefault("fkColumnName", "fkColumnName"),
                            titleMap.getOrDefault("pkName", "pkName"),
                            titleMap.getOrDefault("pkTableName", "pkTableName"),
                            titleMap.getOrDefault("pkColumnName", "pkColumnName"),
                            titleMap.getOrDefault("updateRule", "updateRule"),
                            titleMap.getOrDefault("deleteRule", "deleteRule")
                    ),
                    foreignKeys
            );
        }
    }

    private void triggerBuild(MarkdownBuilder builder,
                              TableDocumentResponse table,
                              Map<String, String> titleMap) {
        if (!table.getTriggers().isEmpty()) {
            List<List<String>> triggerContent = new ArrayList<>();
            for (int i = 0; i < table.getTriggers().size(); i++) {
                var trigger = table.getTriggers().get(i);
                triggerContent.add(
                        List.of(
                                (i + 1) + "",
                                Objects.requireNonNullElse(trigger.getName(), ""),
                                Objects.requireNonNullElse(trigger.getTiming(), ""),
                                Objects.requireNonNullElse(trigger.getManipulation(), ""),
                                Objects.requireNonNullElse(trigger.getStatement(), "")
                        ));
            }
            builder.thirdTitle("Triggers");
            builder.table(
                    List.of(
                            "",
                            titleMap.getOrDefault("name", "name"),
                            titleMap.getOrDefault("timing", "timing"),
                            titleMap.getOrDefault("manipulation", "manipulation"),
                            titleMap.getOrDefault("statement", "statement")
                    ),
                    triggerContent
            );
        }
    }
}
