package com.databasir.core.domain.document.service;

import com.databasir.core.Databasir;
import com.databasir.core.DatabasirConfig;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.document.converter.DocumentHistoryPojoConverter;
import com.databasir.core.domain.document.converter.DocumentPojoConverter;
import com.databasir.core.domain.document.converter.DocumentResponseConverter;
import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentVersionResponse;
import com.databasir.core.infrastructure.connection.DatabaseConnectionService;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.render.markdown.MarkdownBuilder;
import com.databasir.dao.impl.*;
import com.databasir.dao.tables.pojos.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {

    private final ProjectDao projectDao;

    private final ProjectSyncRuleDao projectSyncRuleDao;

    private final DataSourceDao dataSourceDao;

    private final DataSourcePropertyDao dataSourcePropertyDao;

    private final SysKeyDao sysKeyDao;

    private final DatabaseConnectionService databaseConnectionService;

    private final DatabaseDocumentDao databaseDocumentDao;

    private final TableDocumentDao tableDocumentDao;

    private final TableColumnDocumentDao tableColumnDocumentDao;

    private final TableIndexDocumentDao tableIndexDocumentDao;

    private final TableTriggerDocumentDao tableTriggerDocumentDao;

    private final DatabaseDocumentHistoryDao databaseDocumentHistoryDao;

    private final DocumentPojoConverter documentPojoConverter;

    private final DocumentResponseConverter documentResponseConverter;

    private final DocumentHistoryPojoConverter documentHistoryPojoConverter;

    private final JsonConverter jsonConverter;

    @Transactional
    public void syncByProjectId(Integer projectId) {
        ProjectPojo project = projectDao.selectOptionalById(projectId)
                .orElseThrow(DomainErrors.PROJECT_NOT_FOUND::exception);
        DatabaseMeta meta = retrieveDatabaseMeta(projectId);
        Optional<DatabaseDocumentPojo> historyDocumentOpt = databaseDocumentDao.selectOptionalByProjectId(projectId);
        if (historyDocumentOpt.isPresent()) {
            DatabaseDocumentPojo historyDocument = historyDocumentOpt.get();
            Integer previousDocumentId = historyDocument.getId();
            saveAsHistory(historyDocument);
            deleteDeprecatedDocument(previousDocumentId);
            saveNewDocument(meta, historyDocument.getVersion() + 1, historyDocument.getProjectId(), previousDocumentId);
        } else {
            saveNewDocument(meta, 1L, projectId, null);
        }
    }

    private DatabaseMeta retrieveDatabaseMeta(Integer projectId) {
        ProjectSyncRulePojo rule = projectSyncRuleDao.selectByProjectId(projectId);
        DataSourcePojo dataSource = dataSourceDao.selectByProjectId(projectId);
        List<DataSourcePropertyPojo> properties = dataSourcePropertyDao.selectByDataSourceId(dataSource.getId());
        Connection jdbcConnection = databaseConnectionService.create(dataSource, properties);
        DatabasirConfig databasirConfig = new DatabasirConfig();
        databasirConfig.setIgnoreTableNameRegex(jsonConverter.fromJson(rule.getIgnoreTableNameRegexArray()));
        databasirConfig.setIgnoreTableColumnNameRegex(jsonConverter.fromJson(rule.getIgnoreColumnNameRegexArray()));
        return Databasir.of(databasirConfig)
                .get(jdbcConnection, dataSource.getDatabaseName())
                .orElseThrow(DomainErrors.DATABASE_META_NOT_FOUND::exception);
    }

    private void saveAsHistory(DatabaseDocumentPojo databaseDocument) {
        // save history
        Integer projectId = databaseDocument.getProjectId();
        Integer databaseMetaId = databaseDocument.getId();
        DatabaseDocumentResponse databaseDocumentResponse = getOneByProjectId(projectId, null).orElse(null);
        Long currVersion = databaseDocument.getVersion();
        DatabaseDocumentHistoryPojo documentHistoryPojo =
                documentHistoryPojoConverter.of(databaseDocumentResponse, projectId, databaseMetaId, currVersion);
        databaseDocumentHistoryDao.insertAndReturnId(documentHistoryPojo);
        log.info("save old meta info to history success");
    }

    private void deleteDeprecatedDocument(Integer databaseDocumentId) {
        // delete old meta info
        tableDocumentDao.deleteByDatabaseDocumentId(databaseDocumentId);
        tableColumnDocumentDao.deleteByDatabaseDocumentId(databaseDocumentId);
        tableIndexDocumentDao.deleteByDatabaseMetaId(databaseDocumentId);
        tableTriggerDocumentDao.deleteByDatabaseDocumentId(databaseDocumentId);
        log.info("delete old meta info success");
    }

    private void saveNewDocument(DatabaseMeta meta,
                                 Long version,
                                 Integer projectId,
                                 Integer databaseDocumentId) {

        Integer currentDatabaseDocumentId = databaseDocumentId;
        if (databaseDocumentId == null) {
            currentDatabaseDocumentId =
                    databaseDocumentDao.insertAndReturnId(documentPojoConverter.toDatabasePojo(projectId, meta, 1L));
        } else {
            databaseDocumentDao.update(documentPojoConverter.toDatabasePojo(projectId, meta, databaseDocumentId, version));
        }

        final Integer docId = currentDatabaseDocumentId;
        meta.getTables().forEach(table -> {
            TableDocumentPojo tableMeta =
                    documentPojoConverter.toTablePojo(docId, table);
            Integer tableMetaId = tableDocumentDao.insertAndReturnId(tableMeta);
            List<TableColumnDocumentPojo> tableColumnMetas = documentPojoConverter.toColumnPojo(docId, tableMetaId, table.getColumns());
            tableColumnDocumentDao.batchInsert(tableColumnMetas);
            List<TableIndexDocumentPojo> tableIndexMetas = documentPojoConverter.toIndexPojo(docId, tableMetaId, table.getIndexes());
            tableIndexDocumentDao.batchInsert(tableIndexMetas);
            List<TableTriggerDocumentPojo> tableTriggerMetas = documentPojoConverter.toTriggerPojo(docId, tableMetaId, table.getTriggers());
            tableTriggerDocumentDao.batchInsert(tableTriggerMetas);
        });
        log.info("save new meta info success");
    }

    public Optional<DatabaseDocumentResponse> getOneByProjectId(Integer projectId, Long version) {
        if (version == null) {
            return databaseDocumentDao.selectOptionalByProjectId(projectId)
                    .map(document -> {
                        Integer id = document.getId();
                        List<TableDocumentPojo> tables = tableDocumentDao.selectByDatabaseDocumentId(id);
                        List<TableColumnDocumentPojo> columns = tableColumnDocumentDao.selectByDatabaseDocumentId(id);
                        List<TableIndexDocumentPojo> indexes = tableIndexDocumentDao.selectByDatabaseMetaId(id);
                        List<TableTriggerDocumentPojo> triggers = tableTriggerDocumentDao.selectByDatabaseDocumentId(id);
                        Map<Integer, List<TableColumnDocumentPojo>> columnsGroupByTableMetaId = columns.stream()
                                .collect(Collectors.groupingBy(TableColumnDocumentPojo::getTableDocumentId));
                        Map<Integer, List<TableIndexDocumentPojo>> indexesGroupByTableMetaId = indexes.stream()
                                .collect(Collectors.groupingBy(TableIndexDocumentPojo::getTableDocumentId));
                        Map<Integer, List<TableTriggerDocumentPojo>> triggersGroupByTableMetaId = triggers.stream()
                                .collect(Collectors.groupingBy(TableTriggerDocumentPojo::getTableDocumentId));
                        List<DatabaseDocumentResponse.TableDocumentResponse> tableDocumentResponseList = tables.stream()
                                .map(table -> {
                                    List<TableColumnDocumentPojo> subColumns = columnsGroupByTableMetaId.getOrDefault(table.getId(), Collections.emptyList());
                                    List<TableIndexDocumentPojo> subIndexes = indexesGroupByTableMetaId.getOrDefault(table.getId(), Collections.emptyList());
                                    List<TableTriggerDocumentPojo> subTriggers = triggersGroupByTableMetaId.getOrDefault(table.getId(), Collections.emptyList());
                                    return documentResponseConverter.of(table, subColumns, subIndexes, subTriggers);
                                })
                                .collect(Collectors.toList());
                        return documentResponseConverter.of(document, tableDocumentResponseList);
                    });
        } else {
            return databaseDocumentHistoryDao.selectOptionalByProjectIdAndVersion(projectId, version)
                    .map(obj -> jsonConverter.of(obj.getDatabaseDocumentObject()));
        }
    }

    public Page<DatabaseDocumentVersionResponse> getVersionsBySchemaSourceId(Integer projectId, Pageable page) {
        return databaseDocumentDao.selectOptionalByProjectId(projectId)
                .map(schemaMeta -> databaseDocumentHistoryDao.selectVersionPageByDatabaseDocumentId(page, schemaMeta.getId())
                        .map(history -> DatabaseDocumentVersionResponse.builder()
                                .version(history.getVersion())
                                .createAt(history.getCreateAt())
                                .build()))
                .orElseGet(Page::empty);
    }

    public Optional<String> toMarkdown(Integer projectId, Long version) {
        return getOneByProjectId(projectId, version)
                .map(doc -> {
                    MarkdownBuilder builder = MarkdownBuilder.builder();
                    builder.primaryTitle(doc.getDatabaseName());
                    // overview
                    builder.secondTitle("overview");
                    List<List<String>> overviewContent = new ArrayList<>();
                    for (int i = 0; i < doc.getTables().size(); i++) {
                        DatabaseDocumentResponse.TableDocumentResponse table = doc.getTables().get(i);
                        overviewContent.add(List.of((i + 1) + "", table.getName(), table.getType(), table.getComment()));
                    }
                    builder.table(List.of("", "表名", "类型", "备注"), overviewContent);

                    Function<DatabaseDocumentResponse.TableDocumentResponse.ColumnDocumentResponse, String> columnDefaultValueMapping = column -> {
                        if (Objects.equals(column.getNullable(), "YES")) {
                            return Objects.requireNonNullElse(column.getDefaultValue(), "null");
                        } else {
                            return Objects.requireNonNullElse(column.getDefaultValue(), "");
                        }
                    };
                    // tables
                    doc.getTables().forEach(table -> {
                        builder.secondTitle(table.getName());

                        // columns
                        List<List<String>> columnContent = new ArrayList<>();
                        for (int i = 0; i < table.getColumns().size(); i++) {
                            var column = table.getColumns().get(i);
                            String type;
                            if (column.getDecimalDigits() == null || column.getDecimalDigits() == 0) {
                                type = table.getType() + "(" + column.getSize() + ")";
                            } else {
                                type = table.getType() + "(" + column.getSize() + "," + column.getDecimalDigits() + ")";
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
                        builder.thirdTitle("columns");
                        builder.table(List.of("", "名称", "类型", "是否为主键", "可为空", "自增", "默认值", "备注"),
                                columnContent);

                        // indexes
                        List<List<String>> indexContent = new ArrayList<>();
                        for (int i = 0; i < table.getIndexes().size(); i++) {
                            var index = table.getIndexes().get(i);
                            String columnNames = String.join(", ", index.getColumnNames());
                            String isUnique = index.getIsUnique() ? "YES" : "NO";
                            indexContent.add(List.of((i + 1) + "", index.getName(), isUnique, columnNames));
                        }
                        builder.thirdTitle("indexes");
                        builder.table(List.of("", "名称", "是否唯一", "关联列"), indexContent);

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
                            builder.thirdTitle("triggers");
                            builder.table(List.of("", "名称", "timing", "manipulation", "statement"), triggerContent);
                        }
                    });
                    return builder.build();
                });
    }
}
