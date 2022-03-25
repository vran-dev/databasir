package com.databasir.core.domain.document.service;

import com.databasir.core.Databasir;
import com.databasir.core.DatabasirConfig;
import com.databasir.core.diff.Diffs;
import com.databasir.core.diff.data.DiffType;
import com.databasir.core.diff.data.RootDiff;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.document.converter.DatabaseMetaConverter;
import com.databasir.core.domain.document.converter.DocumentPojoConverter;
import com.databasir.core.domain.document.converter.DocumentResponseConverter;
import com.databasir.core.domain.document.converter.DocumentSimpleResponseConverter;
import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentSimpleResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentVersionResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.core.domain.document.event.DocumentUpdated;
import com.databasir.core.domain.document.generator.DocumentFileGenerator;
import com.databasir.core.domain.document.generator.DocumentFileType;
import com.databasir.core.infrastructure.connection.DatabaseConnectionService;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.core.infrastructure.event.EventPublisher;
import com.databasir.core.meta.data.DatabaseMeta;
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

import java.io.OutputStream;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private final DatabaseMetaConverter databaseMetaConverter;

    private final JsonConverter jsonConverter;

    private final List<DocumentFileGenerator> documentFileGenerators;

    private final EventPublisher eventPublisher;

    @Transactional
    public void syncByProjectId(Integer projectId) {
        projectDao.selectOptionalById(projectId)
                .orElseThrow(DomainErrors.PROJECT_NOT_FOUND::exception);
        DatabaseMeta current = retrieveDatabaseMeta(projectId);
        Optional<DatabaseDocumentPojo> originalOption = databaseDocumentDao.selectNotArchivedByProjectId(projectId);
        if (originalOption.isPresent()) {
            DatabaseDocumentPojo original = originalOption.get();
            DatabaseMeta originalMeta = retrieveOriginalDatabaseMeta(original);
            RootDiff diff = Diffs.diff(originalMeta, current);
            if (diff.getDiffType() == DiffType.NONE) {
                log.info("ignore project {} {} sync data, because without change",
                        projectId,
                        original.getDatabaseName());
                return;
            }
            Integer previousDocumentId = original.getId();
            // archive old version
            databaseDocumentDao.updateIsArchiveById(previousDocumentId, true);
            Long version = original.getVersion();
            saveNewDocument(current, version + 1, original.getProjectId());
            eventPublisher.publish(new DocumentUpdated(diff, version + 1, version, projectId));
        } else {
            saveNewDocument(current, 1L, projectId);
            RootDiff diff = Diffs.diff(null, current);
            eventPublisher.publish(new DocumentUpdated(diff, 1L, null, projectId));
        }
    }

    private DatabaseMeta retrieveOriginalDatabaseMeta(DatabaseDocumentPojo original) {
        Integer docId = original.getId();
        List<TableDocumentPojo> tables = tableDocumentDao.selectByDatabaseDocumentId(docId);
        List<TableColumnDocumentPojo> columns = tableColumnDocumentDao.selectByDatabaseDocumentId(docId);
        List<TableIndexDocumentPojo> indexes = tableIndexDocumentDao.selectByDatabaseMetaId(docId);
        List<TableTriggerDocumentPojo> triggers = tableTriggerDocumentDao.selectByDatabaseDocumentId(docId);
        List<TableForeignKeyDocumentPojo> fks = tableForeignKeyDocumentDao.selectByDatabaseDocumentId(docId);
        return databaseMetaConverter.of(original, tables, columns, indexes, triggers, fks);
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
                        var subForeignKeys =
                                foreignKeysGroupByTableMetaId.getOrDefault(tableId, Collections.emptyList());
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

    public void export(Integer projectId,
                       Long version,
                       DocumentFileType type,
                       OutputStream out) {
        getOneByProjectId(projectId, version)
                .ifPresent(doc -> {
                    var context = DocumentFileGenerator.DocumentFileGenerateContext.builder()
                            .documentFileType(type)
                            .databaseDocument(doc)
                            .build();
                    documentFileGenerators.stream()
                            .filter(g -> g.support(type))
                            .findFirst()
                            .ifPresent(generator -> generator.generate(context, out));
                });
    }

    public RootDiff diff(Integer projectId, Long originalVersion, Long currentVersion) {
        var original = databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, originalVersion)
                .orElseThrow(DomainErrors.DOCUMENT_VERSION_IS_INVALID::exception);
        DatabaseDocumentPojo current;
        if (currentVersion == null) {
            current = databaseDocumentDao.selectNotArchivedByProjectId(projectId)
                    .orElseThrow(DomainErrors.DOCUMENT_VERSION_IS_INVALID::exception);
        } else {
            current = databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, currentVersion)
                    .orElseThrow(DomainErrors.DOCUMENT_VERSION_IS_INVALID::exception);
        }
        DatabaseMeta currMeta = retrieveOriginalDatabaseMeta(current);
        DatabaseMeta originalMeta = retrieveOriginalDatabaseMeta(original);
        return Diffs.diff(originalMeta, currMeta);
    }
}
