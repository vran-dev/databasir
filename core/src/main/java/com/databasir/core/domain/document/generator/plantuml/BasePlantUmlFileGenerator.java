package com.databasir.core.domain.document.generator.plantuml;

import com.databasir.common.SystemException;
import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.core.domain.document.generator.DocumentFileGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public abstract class BasePlantUmlFileGenerator implements DocumentFileGenerator {

    public static final String LINE = "\r\n";

    @Override
    public void generate(DocumentFileGenerateContext context, OutputStream outputStream) {
        String dsl = new ErDsl(context).toDsl();
        try {
            new SourceStringReader(dsl).outputImage(outputStream, fileFormatOption());
        } catch (IOException e) {
            log.error("export plantuml error", e);
            throw new SystemException("System error");
        }
    }

    protected abstract FileFormatOption fileFormatOption();

    @RequiredArgsConstructor
    public class ErDsl {

        private final DocumentFileGenerateContext context;

        private Set<String> foreignKeyRelations = new HashSet<>(16);

        public String toDsl() {
            DatabaseDocumentResponse databaseDocument = context.getDatabaseDocument();
            StringBuilder dslBuilder = new StringBuilder(1024);
            dslBuilder.append("@startuml").append(LINE);

            // configuration
            dslBuilder.append("' hide the spot").append(LINE);
            dslBuilder.append("hide circle").append(LINE);

            // entities
            String entities = databaseDocument.getTables()
                    .stream()
                    .map(table -> toErDsl(table))
                    .collect(Collectors.joining(LINE));
            dslBuilder.append(entities);

            // relation
            dslBuilder.append(LINE);
            String relations = foreignKeyRelations.stream()
                    .collect(Collectors.joining(LINE));
            dslBuilder.append(relations);
            dslBuilder.append(LINE);

            dslBuilder.append("@enduml");
            if (log.isDebugEnabled()) {
                log.debug("------------------------------");
                log.debug(dslBuilder.toString());
                log.debug("------------------------------");
            }
            return dslBuilder.toString();
        }

        private String toErDsl(TableDocumentResponse table) {
            StringBuilder dslBuilder = new StringBuilder(1024);
            dslBuilder.append("entity ").append(table.getName())
                    .append(" {");
            table.getColumns()
                    .stream()
                    .filter(TableDocumentResponse.ColumnDocumentResponse::getIsPrimaryKey)
                    .forEach(primaryCol -> {
                        dslBuilder.append(LINE);
                        dslBuilder.append("*")
                                .append(primaryCol.getName())
                                .append(" : ")
                                .append(primaryCol.getType())
                                .append("(")
                                .append(primaryCol.getSize())
                                .append(")")
                                .append(" <PK> ");
                        dslBuilder.append(LINE);
                        dslBuilder.append("--");
                    });
            table.getColumns()
                    .stream()
                    .filter(col -> !col.getIsPrimaryKey())
                    .forEach(col -> {
                        dslBuilder.append(LINE);
                        if ("NO".equalsIgnoreCase(col.getNullable())) {
                            dslBuilder.append("*");
                        }
                        dslBuilder.append(col.getName())
                                .append(" : ")
                                .append(col.getType())
                                .append("(")
                                .append(col.getSize())
                                .append(")");
                        if (col.getComment() != null && !"".equals(col.getComment().trim())) {
                            dslBuilder.append(" /* ").append(col.getComment()).append(" */");
                        }
                        dslBuilder.append(LINE);
                    });
            dslBuilder.append("}");
            dslBuilder.append(LINE);

            table.getForeignKeys().forEach(fk -> {
                String fkTableName = fk.getFkTableName();
                String fkColumnName = fk.getFkColumnName();
                String pkTableName = fk.getPkTableName();
                String pkColumnName = fk.getPkColumnName();
                StringBuilder relationBuilder = new StringBuilder();
                relationBuilder.append(fkTableName).append("::").append(fkColumnName)
                        .append(" --> ")
                        .append(pkTableName).append("::").append(pkColumnName)
                        .append(" : ")
                        .append(Objects.requireNonNullElse(fk.getFkName(), ""));
                foreignKeyRelations.add(relationBuilder.toString());
            });
            return dslBuilder.toString();
        }
    }
}
