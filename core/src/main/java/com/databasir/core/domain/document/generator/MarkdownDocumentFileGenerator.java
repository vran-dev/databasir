package com.databasir.core.domain.document.generator;

import com.databasir.common.SystemException;
import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.core.render.markdown.MarkdownBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Component
@Slf4j
public class MarkdownDocumentFileGenerator implements DocumentFileGenerator {

    @Override
    public boolean support(DocumentFileType type) {
        return type == DocumentFileType.MARKDOWN;
    }

    @Override
    public void generate(DocumentFileGenerateContext context, OutputStream outputStream) {
        String fileName = context.getDatabaseDocument().getDatabaseName() + "-" + UUID.randomUUID().toString();
        String data = markdownData(context);
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

    private String markdownData(DocumentFileGenerateContext context) {
        DatabaseDocumentResponse doc = context.getDatabaseDocument();
        MarkdownBuilder builder = MarkdownBuilder.builder();
        builder.primaryTitle(doc.getDatabaseName());
        // overview
        overviewBuild(builder, doc);
        // tables
        doc.getTables().forEach(table -> tableBuild(builder, table));
        return builder.build();
    }

    private void overviewBuild(MarkdownBuilder builder, DatabaseDocumentResponse doc) {
        builder.secondTitle("overview");
        List<List<String>> overviewContent = new ArrayList<>();
        for (int i = 0; i < doc.getTables().size(); i++) {
            TableDocumentResponse table = doc.getTables().get(i);
            overviewContent.add(List.of((i + 1) + "", table.getName(), table.getType(),
                    table.getComment()));
        }
        builder.table(List.of("", "表名", "类型", "备注"), overviewContent);
    }

    private void tableBuild(MarkdownBuilder builder, TableDocumentResponse table) {
        builder.secondTitle(table.getName());
        columnBuild(builder, table);
        indexBuild(builder, table);
        foreignKeyBuild(builder, table);
        triggerBuild(builder, table);
    }

    private void columnBuild(MarkdownBuilder builder, TableDocumentResponse table) {
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
            if (column.getDecimalDigits() == null || column.getDecimalDigits() == 0) {
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
                    column.getComment()));
        }
        builder.table(List.of("", "名称", "类型", "是否为主键", "可为空", "自增", "默认值", "备注"),
                columnContent);
    }


    private void indexBuild(MarkdownBuilder builder, TableDocumentResponse table) {
        builder.thirdTitle("Indexes");
        List<List<String>> indexContent = new ArrayList<>();
        for (int i = 0; i < table.getIndexes().size(); i++) {
            var index = table.getIndexes().get(i);
            String columnNames = String.join(", ", index.getColumnNames());
            String isUnique = index.getIsUnique() ? "YES" : "NO";
            indexContent.add(List.of((i + 1) + "", index.getName(), isUnique, columnNames));
        }
        builder.table(List.of("", "名称", "是否唯一", "关联列"), indexContent);

    }

    private void foreignKeyBuild(MarkdownBuilder builder, TableDocumentResponse table) {
        if (!table.getForeignKeys().isEmpty()) {
            List<List<String>> foreignKeys = new ArrayList<>();
            builder.thirdTitle("Foreign Keys");
            for (int i = 0; i < table.getForeignKeys().size(); i++) {
                TableDocumentResponse.ForeignKeyDocumentResponse fk = table.getForeignKeys().get(i);
                List<String> item = List.of(
                        (i + 1) + "",
                        fk.getFkName(), fk.getFkColumnName(),
                        fk.getPkName(), fk.getPkTableName(), fk.getPkColumnName(),
                        fk.getUpdateRule(), fk.getDeleteRule()
                );
                foreignKeys.add(item);
            }
            builder.table(
                    List.of("", "FK Name", "FK Column", "PK Name", "PK Table", "PK Column",
                            "Update Rule", "Delete Rule"),
                    foreignKeys
            );
        }
    }

    private void triggerBuild(MarkdownBuilder builder, TableDocumentResponse table) {
        if (!table.getTriggers().isEmpty()) {
            List<List<String>> triggerContent = new ArrayList<>();
            for (int i = 0; i < table.getTriggers().size(); i++) {
                var trigger = table.getTriggers().get(i);
                triggerContent.add(List.of((i + 1) + "",
                        trigger.getName(),
                        trigger.getTiming(),
                        trigger.getManipulation(),
                        trigger.getStatement()));
            }
            builder.thirdTitle("Triggers");
            builder.table(List.of("", "名称", "timing", "manipulation", "statement"), triggerContent);
        }
    }
}
