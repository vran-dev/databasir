package com.databasir.core.domain.document.service;

import com.databasir.common.DatabasirException;
import com.databasir.core.Databasir;
import com.databasir.core.DatabasirConfig;
import com.databasir.core.diff.Diffs;
import com.databasir.core.diff.data.DiffType;
import com.databasir.core.diff.data.RootDiff;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.document.comparator.DiffResult;
import com.databasir.core.domain.document.comparator.DocumentDiffs;
import com.databasir.core.domain.document.comparator.TableDiffResult;
import com.databasir.core.domain.document.converter.*;
import com.databasir.core.domain.document.data.*;
import com.databasir.core.domain.document.data.TableDocumentResponse.ForeignKeyDocumentResponse;
import com.databasir.core.domain.document.event.DocumentUpdated;
import com.databasir.core.domain.document.generator.DocumentFileGenerator;
import com.databasir.core.domain.document.generator.DocumentFileType;
import com.databasir.core.infrastructure.connection.DatabaseConnectionService;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.core.infrastructure.event.EventPublisher;
import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.meta.data.TableMeta;
import com.databasir.dao.impl.*;
import com.databasir.dao.tables.pojos.*;
import com.databasir.dao.value.DocumentDiscussionCountPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.tools.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {

    private final ProjectDao projectDao;

    private final ProjectSyncRuleDao projectSyncRuleDao;

    private final DataSourceDao dataSourceDao;

    private final DataSourcePropertyDao dataSourcePropertyDao;

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

    private final TableResponseConverter tableResponseConverter;

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
            RootDiff diff = null;
            try {
                diff = Diffs.diff(null, current);
            } catch (Exception e) {
                log.error("diff project " + projectId + " error, fallback diff type to NONE", e);
            }
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
        try {
            if (jdbcConnection == null) {
                throw DomainErrors.CONNECT_DATABASE_FAILED.exception();
            }
            DatabaseMeta databaseMeta = Databasir.of(databasirConfig)
                    .get(jdbcConnection, dataSource.getDatabaseName(), dataSource.getSchemaName())
                    .orElseThrow(DomainErrors.DATABASE_META_NOT_FOUND::exception);
            return databaseMeta;
        } finally {
            try {
                if (jdbcConnection != null && !jdbcConnection.isClosed()) {
                    jdbcConnection.close();
                }
            } catch (SQLException e) {
                log.error("close jdbc connection error", e);
            }
        }
    }

    private void saveNewDocument(DatabaseMeta meta,
                                 Long version,
                                 Integer projectId) {

        var pojo = documentPojoConverter.toDatabasePojo(projectId, meta, version);
        final Integer docId;
        try {
            docId = databaseDocumentDao.insertAndReturnId(pojo);
        } catch (DuplicateKeyException e) {
            log.warn("ignore insert database document projectId={} version={}", projectId, version);
            throw new DatabasirException(DomainErrors.DATABASE_DOCUMENT_DUPLICATE_KEY);
        }
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

    public Optional<DatabaseDocumentSimpleResponse> getSimpleOneByProjectId(Integer projectId,
                                                                            Long version,
                                                                            Long originalVersion) {
        String projectName = projectDao.selectOptionalById(projectId)
                .map(ProjectPojo::getName)
                .orElseThrow(DomainErrors.PROJECT_NOT_FOUND::exception);

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
            var descriptionMapByTableName =
                    documentDescriptionDao.selectTableDescriptionByProjectId(projectId)
                            .stream()
                            .collect(Collectors.toMap(d -> d.getTableName(), d -> d.getContent(), (a, b) -> a));
            var tableMetas = documentSimpleResponseConverter.of(
                    tables,
                    discussionCountMapByTableName,
                    descriptionMapByTableName
            );

            // if original version is not null mean version diff enabled
            if (originalVersion != null) {
                var originalDocument =
                        databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, originalVersion)
                                .orElseThrow(DomainErrors.DOCUMENT_VERSION_IS_INVALID::exception);
                var originalTables = tableDocumentDao.selectByDatabaseDocumentId(originalDocument.getId());
                var originalTableMetas = documentSimpleResponseConverter.of(
                        originalTables,
                        discussionCountMapByTableName,
                        descriptionMapByTableName
                );
                var originalMap = originalTableMetas.stream()
                        .collect(Collectors.toMap(t -> t.getName(), Function.identity(), (a, b) -> a));
                var currentMap = tableMetas.stream()
                        .collect(Collectors.toMap(t -> t.getName(), Function.identity(), (a, b) -> a));
                List<TableDiffResult> diffResults = tableDiffs(projectId, originalVersion, version);

                List<DatabaseDocumentSimpleResponse.TableData> result = new ArrayList<>();
                for (TableDiffResult diffResult : diffResults) {
                    var cur = currentMap.get(diffResult.getId());
                    var org = originalMap.get(diffResult.getId());
                    if (diffResult.getDiffType() == DiffType.ADDED) {
                        cur.setDiffType(DiffType.ADDED);
                        result.add(cur);
                    } else if (diffResult.getDiffType() == DiffType.MODIFIED) {
                        cur.setDiffType(DiffType.MODIFIED);
                        cur.setOriginal(org);
                        result.add(cur);
                    } else if (diffResult.getDiffType() == DiffType.REMOVED) {
                        org.setDiffType(DiffType.REMOVED);
                        result.add(org);
                    } else {
                        cur.setDiffType(DiffType.NONE);
                        result.add(cur);
                    }
                }
                result.sort(Comparator.comparing(DatabaseDocumentSimpleResponse.TableData::getName));
                boolean allAdded = result.stream()
                        .filter(item -> !item.getDiffType().isNone())
                        .allMatch(item -> item.getDiffType().isAdded());
                DiffType diffType;
                if (allAdded) {
                    diffType = DiffType.ADDED;
                } else {
                    diffType = result.stream()
                            .anyMatch(t -> t.getDiffType() != DiffType.NONE) ? DiffType.MODIFIED : DiffType.NONE;
                }
                return documentSimpleResponseConverter.of(document, result, diffType, projectName);
            } else {
                tableMetas.sort(Comparator.comparing(DatabaseDocumentSimpleResponse.TableData::getName));
                return documentSimpleResponseConverter.of(document, tableMetas, DiffType.NONE, projectName);
            }
        });
    }

    public List<TableDiffResult> tableDiffs(Integer projectId, Long originalVersion, Long currentVersion) {
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
        return DocumentDiffs.tableDiff(originalMeta, currMeta);
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
                        var subColumns = columnsGroupByTableMetaId.getOrDefault(tableId, emptyList());
                        var subIndexes = indexesGroupByTableMetaId.getOrDefault(tableId, emptyList());
                        var subTriggers = triggersGroupByTableMetaId.getOrDefault(tableId, emptyList());
                        var subForeignKeys =
                                foreignKeysGroupByTableMetaId.getOrDefault(tableId, emptyList());
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
                                                       Collection<Integer> tableIds) {
        // maybe deleted
        if (CollectionUtils.isEmpty(tableIds) || !projectDao.existsById(projectId)) {
            return emptyList();
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
                    var subColumns = columnsGroupByTableMetaId.getOrDefault(tableId, emptyList());
                    var subIndexes = indexesGroupByTableMetaId.getOrDefault(tableId, emptyList());
                    var subForeignKeys = foreignKeysGroupByTableMetaId.getOrDefault(tableId, emptyList());
                    var subTriggers = triggersGroupByTableMetaId.getOrDefault(tableId, emptyList());
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

    public List<TableDocumentResponse> getTableDetails(Integer projectId,
                                                       Integer databaseDocumentId,
                                                       TableDocumentRequest request) {
        // maybe deleted
        if (CollectionUtils.isEmpty(request.getTableIds()) || !projectDao.existsById(projectId)) {
            return emptyList();
        }
        var current = this.getTableDetails(projectId, databaseDocumentId, request.getTableIds());
        if (request.getOriginalVersion() != null) {
            DatabaseDocumentPojo doc =
                    databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, request.getOriginalVersion())
                            .orElseThrow(DomainErrors.DOCUMENT_VERSION_IS_INVALID::exception);
            List<String> tableNames = current.stream().map(t -> t.getName()).distinct().collect(Collectors.toList());
            List<Integer> originalTableIds =
                    tableDocumentDao.selectTableIdsByDatabaseDocumentIdAndTableNameIn(doc.getId(), tableNames);
            var original = this.getTableDetails(projectId, doc.getId(), originalTableIds);
            Map<String, TableDocumentResponse> currentMapByName = current.stream()
                    .collect(Collectors.toMap(TableDocumentResponse::getName, Function.identity(), (a, b) -> a));
            Map<String, TableDocumentResponse> originalMapByName = original.stream()
                    .collect(Collectors.toMap(TableDocumentResponse::getName, Function.identity(), (a, b) -> a));
            List<TableMeta> currentMeta = databaseMetaConverter.of(current);
            List<TableMeta> originalMeta = databaseMetaConverter.of(original);
            List<TableDiffResult> diffs = DocumentDiffs.tableDiff(originalMeta, currentMeta);
            return diffs.stream()
                    .map(diff -> {
                        if (diff.getDiffType() == DiffType.ADDED) {
                            TableDocumentResponse c = currentMapByName.get(diff.getId());
                            c.setDiffType(DiffType.ADDED);
                            var cols =
                                    diff(diff.getColumnDiffResults(), emptyList(), c.getColumns(), i -> i.getName());
                            c.setColumns(cols);
                            var indexes =
                                    diff(diff.getIndexDiffResults(), emptyList(), c.getIndexes(), i -> i.getName());
                            c.setIndexes(indexes);
                            var foreignKeys = foreignKeyDiff(diff.getForeignKeyDiffResults(),
                                    emptyList(), c.getForeignKeys());
                            c.setForeignKeys(foreignKeys);
                            var triggers =
                                    diff(diff.getTriggerDiffResults(), emptyList(), c.getTriggers(), t -> t.getName());
                            c.setTriggers(triggers);
                            return c;
                        }
                        if (diff.getDiffType() == DiffType.REMOVED) {
                            TableDocumentResponse t = originalMapByName.get(diff.getId());
                            t.setDiffType(DiffType.REMOVED);
                            return t;
                        }
                        if (diff.getDiffType() == DiffType.MODIFIED) {
                            TableDocumentResponse c = currentMapByName.get(diff.getId());
                            TableDocumentResponse o = originalMapByName.get(diff.getId());
                            c.setDiffType(DiffType.MODIFIED);
                            c.setOriginal(o);
                            var cols =
                                    diff(diff.getColumnDiffResults(), o.getColumns(), c.getColumns(),
                                            col -> col.getName());
                            c.setColumns(cols);
                            var indexes =
                                    diff(diff.getIndexDiffResults(), o.getIndexes(), c.getIndexes(), i -> i.getName());
                            c.setIndexes(indexes);
                            var foreignKeys = foreignKeyDiff(diff.getForeignKeyDiffResults(),
                                    o.getForeignKeys(), c.getForeignKeys());
                            c.setForeignKeys(foreignKeys);
                            var triggers =
                                    diff(diff.getTriggerDiffResults(), o.getTriggers(), c.getTriggers(),
                                            t -> t.getName());
                            c.setTriggers(triggers);
                            return c;
                        }
                        TableDocumentResponse t = currentMapByName.get(diff.getId());
                        t.setDiffType(DiffType.NONE);
                        return t;
                    })
                    .sorted(Comparator.comparing(TableDocumentResponse::getName))
                    .collect(Collectors.toList());

        } else {
            current.sort(Comparator.comparing(TableDocumentResponse::getName));
            return current;
        }
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

    public List<TableResponse> getTableAndColumns(Integer projectId, Long version) {
        Optional<DatabaseDocumentPojo> documentOption;
        if (version == null) {
            documentOption = databaseDocumentDao.selectNotArchivedByProjectId(projectId);
        } else {
            documentOption = databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, version);
        }
        if (documentOption.isEmpty()) {
            return emptyList();
        } else {
            DatabaseDocumentPojo databaseDoc = documentOption.get();
            var tables = tableDocumentDao.selectByDatabaseDocumentId(databaseDoc.getId());
            var columns = tableColumnDocumentDao.selectByDatabaseDocumentId(databaseDoc.getId());
            var columnMapByTableId = columns.stream()
                    .collect(Collectors.groupingBy(TableColumnDocumentPojo::getTableDocumentId));
            return tableResponseConverter.from(tables, columnMapByTableId);
        }
    }

    private <T extends DiffAble> List<T> diff(Collection<DiffResult> diffs,
                                              Collection<T> original,
                                              Collection<T> current,
                                              Function<T, String> idMapping) {
        var currentMapByName = current.stream()
                .collect(Collectors.toMap(idMapping, Function.identity(), (a, b) -> a));
        var originalMapByName = original.stream()
                .collect(Collectors.toMap(idMapping, Function.identity(), (a, b) -> a));
        return diffs.stream().map(diff -> {
                    if (diff.getDiffType() == DiffType.ADDED) {
                        var t = currentMapByName.get(diff.getId());
                        t.setDiffType(DiffType.ADDED);
                        return t;
                    }
                    if (diff.getDiffType() == DiffType.REMOVED) {
                        var t = originalMapByName.get(diff.getId());
                        t.setDiffType(DiffType.REMOVED);
                        return t;
                    }
                    if (diff.getDiffType() == DiffType.MODIFIED) {
                        var c = currentMapByName.get(diff.getId());
                        var o = originalMapByName.get(diff.getId());
                        c.setDiffType(DiffType.MODIFIED);
                        c.setOriginal(o);
                        return c;
                    }
                    var t = currentMapByName.get(diff.getId());
                    t.setDiffType(DiffType.NONE);
                    return t;
                })
                .collect(Collectors.toList());
    }

    private List<ForeignKeyDocumentResponse> foreignKeyDiff(Collection<DiffResult> diffs,
                                                            Collection<ForeignKeyDocumentResponse> original,
                                                            Collection<ForeignKeyDocumentResponse> current) {
        Function<ForeignKeyDocumentResponse, String> idMapping = fk -> {
            return fk.getFkTableName() + "." + fk.getFkColumnName() + "." + fk.getKeySeq();
        };
        return diff(diffs, original, current, idMapping);
    }
}
