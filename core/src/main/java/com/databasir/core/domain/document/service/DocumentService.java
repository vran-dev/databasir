package com.databasir.core.domain.document.service;

import com.databasir.core.Databasir;
import com.databasir.core.DatabasirConfig;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.document.converter.DocumentPojoConverter;
import com.databasir.core.domain.document.converter.DocumentResponseConverter;
import com.databasir.core.domain.document.converter.DocumentSimpleResponseConverter;
import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentSimpleResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentVersionResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.core.infrastructure.connection.DatabaseConnectionService;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.render.markdown.MarkdownBuilder;
import com.databasir.dao.impl.*;
import com.databasir.dao.tables.pojos.*;
import com.databasir.dao.value.DocumentDiscussionCountPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.tools.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    private final TableForeignKeyDocumentDao tableForeignKeyDocumentDao;

    private final DocumentDiscussionDao documentDiscussionDao;

    private final DocumentDescriptionDao documentDescriptionDao;

    private final DocumentPojoConverter documentPojoConverter;

    private final DocumentResponseConverter documentResponseConverter;

    private final DocumentSimpleResponseConverter documentSimpleResponseConverter;

    private final JsonConverter jsonConverter;

    @Transactional
    public void syncByProjectId(Integer projectId) {
        projectDao.selectOptionalById(projectId)
                .orElseThrow(DomainErrors.PROJECT_NOT_FOUND::exception);
        DatabaseMeta meta = retrieveDatabaseMeta(projectId);
        Optional<DatabaseDocumentPojo> latestDocumentOpt = databaseDocumentDao.selectNotArchivedByProjectId(projectId);
        if (latestDocumentOpt.isPresent()) {
            DatabaseDocumentPojo latestDocument = latestDocumentOpt.get();
            Integer previousDocumentId = latestDocument.getId();
            // archive old version
            databaseDocumentDao.updateIsArchiveById(previousDocumentId, true);
            saveNewDocument(meta, latestDocument.getVersion() + 1, latestDocument.getProjectId());
        } else {
            saveNewDocument(meta, 1L, projectId);
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
                .get(jdbcConnection, dataSource.getDatabaseName(), dataSource.getSchemaName())
                .orElseThrow(DomainErrors.DATABASE_META_NOT_FOUND::exception);
    }

    private void saveNewDocument(DatabaseMeta meta,
                                 Long version,
                                 Integer projectId) {

        var pojo = documentPojoConverter.toDatabasePojo(projectId, meta, version);
        final Integer docId = databaseDocumentDao.insertAndReturnId(pojo);
        meta.getTables().forEach(table -> {
            TableDocumentPojo tableMeta =
                    documentPojoConverter.toTablePojo(docId, table);
            Integer tableMetaId = tableDocumentDao.insertAndReturnId(tableMeta);
            // column
            var columns = documentPojoConverter.toColumnPojo(docId, tableMetaId, table.getColumns());
            tableColumnDocumentDao.batchInsert(columns);
            // index
            var indexes = documentPojoConverter.toIndexPojo(docId, tableMetaId, table.getIndexes())
                    .stream()
                    .filter(index -> {
                        if (index.getName() != null) {
                            return true;
                        } else {
                            log.warn("ignore table {} index {}, cause name is null", table.getName(), index);
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
            tableIndexDocumentDao.batchInsert(indexes);
            // foreign key
            var foreignKeys = documentPojoConverter.toForeignKeyPojo(docId, tableMetaId, table.getForeignKeys());
            tableForeignKeyDocumentDao.batchInsert(foreignKeys);

            // trigger
            var triggers = documentPojoConverter.toTriggerPojo(docId, tableMetaId, table.getTriggers());
            tableTriggerDocumentDao.batchInsert(triggers);
        });
        log.info("save new version document success: projectId = {}, name = {}, version =  {}",
                projectId, meta.getDatabaseName(), version);
    }

    public Optional<DatabaseDocumentSimpleResponse> getSimpleOneByProjectId(Integer projectId, Long version) {
        Optional<DatabaseDocumentPojo> documentOption;
        if (version == null) {
            documentOption = databaseDocumentDao.selectNotArchivedByProjectId(projectId);
        } else {
            documentOption = databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, version);
        }
        return documentOption.map(document -> {
            Integer id = document.getId();
            var tables = tableDocumentDao.selectByDatabaseDocumentId(id);
            var discussionCountMapByTableName =
                    documentDiscussionDao.selectTableDiscussionCount(projectId)
                            .stream()
                            .collect(Collectors.toMap(d -> d.getTableName(), d -> d.getCount(), (a, b) -> a));
            Map<String, String> descriptionMapByTableName =
                    documentDescriptionDao.selectTableDescriptionByProjectId(projectId)
                            .stream()
                            .collect(Collectors.toMap(d -> d.getTableName(), d -> d.getContent(), (a, b) -> a));
            var tableMetas = documentSimpleResponseConverter.of(
                    tables,
                    discussionCountMapByTableName,
                    descriptionMapByTableName
            );
            return documentSimpleResponseConverter.of(document, tableMetas);
        });
    }

    public Optional<DatabaseDocumentResponse> getOneByProjectId(Integer projectId, Long version) {

        Optional<DatabaseDocumentPojo> documentOption;
        if (version == null) {
            documentOption = databaseDocumentDao.selectNotArchivedByProjectId(projectId);
        } else {
            documentOption = databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, version);
        }
        return documentOption.map(document -> {
            Integer id = document.getId();
            var tables = tableDocumentDao.selectByDatabaseDocumentId(id);
            var columns = tableColumnDocumentDao.selectByDatabaseDocumentId(id);
            var indexes = tableIndexDocumentDao.selectByDatabaseMetaId(id);
            var triggers = tableTriggerDocumentDao.selectByDatabaseDocumentId(id);
            var foreignKeys = tableForeignKeyDocumentDao.selectByDatabaseDocumentId(id);
            Map<Integer, List<TableColumnDocumentPojo>> columnsGroupByTableMetaId = columns.stream()
                    .collect(Collectors.groupingBy(TableColumnDocumentPojo::getTableDocumentId));
            Map<Integer, List<TableIndexDocumentPojo>> indexesGroupByTableMetaId = indexes.stream()
                    .collect(Collectors.groupingBy(TableIndexDocumentPojo::getTableDocumentId));
            Map<Integer, List<TableTriggerDocumentPojo>> triggersGroupByTableMetaId = triggers.stream()
                    .collect(Collectors.groupingBy(TableTriggerDocumentPojo::getTableDocumentId));
            Map<Integer, List<TableForeignKeyDocumentPojo>> foreignKeysGroupByTableMetaId = foreignKeys.stream()
                    .collect(Collectors.groupingBy(TableForeignKeyDocumentPojo::getTableDocumentId));
            var tableDocumentResponseList = tables.stream()
                    .map(table -> {
                        Integer tableId = table.getId();
                        var subColumns = columnsGroupByTableMetaId.getOrDefault(tableId, Collections.emptyList());
                        var subIndexes = indexesGroupByTableMetaId.getOrDefault(tableId, Collections.emptyList());
                        var subTriggers = triggersGroupByTableMetaId.getOrDefault(tableId, Collections.emptyList());
                        var subForeignKeys = foreignKeysGroupByTableMetaId.getOrDefault(tableId, Collections.emptyList());
                        return documentResponseConverter.of(
                                table,
                                subColumns,
                                subIndexes,
                                subForeignKeys,
                                subTriggers
                        );
                    })
                    .collect(Collectors.toList());
            return documentResponseConverter.of(document, tableDocumentResponseList);
        });

    }

    public Page<DatabaseDocumentVersionResponse> getVersionsByProjectId(Integer projectId, Pageable page) {
        return databaseDocumentDao.selectNotArchivedByProjectId(projectId)
                .map(databaseDocument ->
                        databaseDocumentDao.selectVersionPageByProjectId(page, projectId)
                                .map(history -> DatabaseDocumentVersionResponse.builder()
                                        .databaseDocumentId(history.getId())
                                        .version(history.getVersion())
                                        .createAt(history.getCreateAt())
                                        .build()))
                .orElseGet(Page::empty);
    }

    public List<TableDocumentResponse> getTableDetails(Integer projectId,
                                                       Integer databaseDocumentId,
                                                       List<Integer> tableIds) {
        // maybe deleted
        if (CollectionUtils.isEmpty(tableIds) || !projectDao.existsById(projectId)) {
            return Collections.emptyList();
        }
        var tables =
                tableDocumentDao.selectByDatabaseDocumentIdAndIdIn(databaseDocumentId, tableIds);
        // column
        var columns =
                tableColumnDocumentDao.selectByDatabaseDocumentIdAndTableIdIn(databaseDocumentId, tableIds);
        Map<Integer, List<TableColumnDocumentPojo>> columnsGroupByTableMetaId = columns.stream()
                .collect(Collectors.groupingBy(TableColumnDocumentPojo::getTableDocumentId));

        // index
        var indexes =
                tableIndexDocumentDao.selectByDatabaseDocumentIdAndIdIn(databaseDocumentId, tableIds);
        Map<Integer, List<TableIndexDocumentPojo>> indexesGroupByTableMetaId = indexes.stream()
                .collect(Collectors.groupingBy(TableIndexDocumentPojo::getTableDocumentId));

        // foreign keys
        var foreignKeys =
                tableForeignKeyDocumentDao.selectByDatabaseDocumentIdAndTableIdIn(databaseDocumentId, tableIds);
        Map<Integer, List<TableForeignKeyDocumentPojo>> foreignKeysGroupByTableMetaId = foreignKeys.stream()
                .collect(Collectors.groupingBy(TableForeignKeyDocumentPojo::getTableDocumentId));

        // trigger
        var triggers =
                tableTriggerDocumentDao.selectByDatabaseDocumentIdAndIdIn(databaseDocumentId, tableIds);
        Map<Integer, List<TableTriggerDocumentPojo>> triggersGroupByTableMetaId = triggers.stream()
                .collect(Collectors.groupingBy(TableTriggerDocumentPojo::getTableDocumentId));

        // discussion
        var discussions = documentDiscussionDao.selectAllDiscussionCount(projectId);
        Map<String, Integer> discussionCountMapByJoinName = discussions.stream()
                .collect(Collectors.toMap(
                        d -> String.join(".",
                                d.getTableName(),
                                StringUtils.defaultIfBlank(d.getColumnName(), "")),
                        DocumentDiscussionCountPojo::getCount,
                        (a, b) -> a));

        // description
        var descriptions = documentDescriptionDao.selectByProjectId(projectId);
        Map<String, String> descriptionMapByJoinName = descriptions.stream()
                .collect(Collectors.toMap(
                        d -> String.join(".",
                                d.getTableName(),
                                StringUtils.defaultIfBlank(d.getColumnName(), "")),
                        DocumentDescriptionPojo::getContent,
                        (a, b) -> a));

        return tables.stream()
                .map(table -> {
                    Integer tableId = table.getId();
                    var subColumns = columnsGroupByTableMetaId.getOrDefault(tableId, Collections.emptyList());
                    var subIndexes = indexesGroupByTableMetaId.getOrDefault(tableId, Collections.emptyList());
                    var subForeignKeys = foreignKeysGroupByTableMetaId.getOrDefault(tableId, Collections.emptyList());
                    var subTriggers = triggersGroupByTableMetaId.getOrDefault(tableId, Collections.emptyList());
                    var discussionCount = discussionCountMapByJoinName.get(table.getName());
                    var description = descriptionMapByJoinName.get(table.getName());
                    var columnResponses = documentResponseConverter.of(
                            subColumns,
                            table.getName(),
                            discussionCountMapByJoinName,
                            descriptionMapByJoinName);
                    return documentResponseConverter.of(
                            table,
                            discussionCount,
                            description,
                            columnResponses,
                            subIndexes,
                            subForeignKeys,
                            subTriggers
                    );
                })
                .collect(Collectors.toList());
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
                        TableDocumentResponse table = doc.getTables().get(i);
                        overviewContent.add(List.of((i + 1) + "", table.getName(), table.getType(),
                                table.getComment()));
                    }
                    builder.table(List.of("", "表名", "类型", "备注"), overviewContent);

                    Function<TableDocumentResponse.ColumnDocumentResponse, String>
                            columnDefaultValueMapping = column -> {
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
