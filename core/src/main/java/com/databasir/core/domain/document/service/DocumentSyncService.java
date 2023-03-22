package com.databasir.core.domain.document.service;

import com.databasir.common.DatabasirException;
import com.databasir.core.Databasir;
import com.databasir.core.DatabasirConfig;
import com.databasir.core.diff.Diffs;
import com.databasir.core.diff.data.DiffType;
import com.databasir.core.diff.data.RootDiff;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.document.converter.DatabaseMetaConverter;
import com.databasir.core.domain.document.converter.DocumentFullTextConverter;
import com.databasir.core.domain.document.converter.DocumentPojoConverter;
import com.databasir.core.domain.document.event.DocumentUpdated;
import com.databasir.core.infrastructure.connection.DatabaseConnectionService;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.core.infrastructure.event.EventPublisher;
import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.dao.impl.*;
import com.databasir.dao.tables.pojos.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentSyncService {

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

    private final DocumentFullTextDao documentFullTextDao;

    private final DocumentPojoConverter documentPojoConverter;

    private final DocumentFullTextConverter documentFullTextConverter;

    private final JsonConverter jsonConverter;

    private final DatabaseMetaConverter databaseMetaConverter;

    private final EventPublisher eventPublisher;

    @Transactional
    public void syncByProjectId(Integer projectId) {
        projectDao.selectOptionalById(projectId)
                .orElseThrow(DomainErrors.PROJECT_NOT_FOUND::exception);
        DatabaseMeta current = retrieveDatabaseMeta(projectId);
        Optional<DatabaseDocument> originalOption = databaseDocumentDao.selectNotArchivedByProjectId(projectId);
        if (originalOption.isPresent()) {
            DatabaseDocument original = originalOption.get();
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
            Integer docId = saveNewDocument(current, version + 1, original.getProjectId());
            eventPublisher.publish(new DocumentUpdated(diff, version + 1, version, projectId, docId));
        } else {
            Integer docId = saveNewDocument(current, 1L, projectId);
            RootDiff diff = null;
            try {
                diff = Diffs.diff(null, current);
            } catch (Exception e) {
                log.error("diff project " + projectId + " error, fallback diff type to NONE", e);
            }
            eventPublisher.publish(new DocumentUpdated(diff, 1L, null, projectId, docId));
        }
    }

    private DatabaseMeta retrieveDatabaseMeta(Integer projectId) {
        ProjectSyncRule rule = projectSyncRuleDao.selectByProjectId(projectId);
        DataSource dataSource = dataSourceDao.selectByProjectId(projectId);
        List<DataSourceProperty> properties = dataSourcePropertyDao.selectByDataSourceId(dataSource.getId());
        Connection jdbcConnection = databaseConnectionService.create(dataSource, properties);
        DatabasirConfig databasirConfig = new DatabasirConfig();
        databasirConfig.setIgnoreTableNameRegex(jsonConverter.fromJson(rule.getIgnoreTableNameRegexArray()));
        databasirConfig.setIgnoreTableColumnNameRegex(jsonConverter.fromJson(rule.getIgnoreColumnNameRegexArray()));
        try {
            if (jdbcConnection == null) {
                throw DomainErrors.DATABASE_CONNECT_FAILED.exception();
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

    private DatabaseMeta retrieveOriginalDatabaseMeta(DatabaseDocument original) {
        Integer docId = original.getId();
        List<TableDocument> tables = tableDocumentDao.selectByDatabaseDocumentId(docId);
        List<TableColumnDocument> columns = tableColumnDocumentDao.selectByDatabaseDocumentId(docId);
        List<TableIndexDocument> indexes = tableIndexDocumentDao.selectByDatabaseMetaId(docId);
        List<TableTriggerDocument> triggers = tableTriggerDocumentDao.selectByDatabaseDocumentId(docId);
        List<TableForeignKeyDocument> fks = tableForeignKeyDocumentDao.selectByDatabaseDocumentId(docId);
        return databaseMetaConverter.of(original, tables, columns, indexes, triggers, fks);
    }

    private Integer saveNewDocument(DatabaseMeta meta,
                                    Long version,
                                    Integer projectId) {

        var dbDocPojo = documentPojoConverter.toDatabasePojo(projectId, meta, version);
        final Integer docId;
        try {
            docId = databaseDocumentDao.insertAndReturnId(dbDocPojo);
            dbDocPojo.setId(docId);
        } catch (DuplicateKeyException e) {
            log.warn("ignore insert database document projectId={} version={}", projectId, version);
            throw new DatabasirException(DomainErrors.DATABASE_DOCUMENT_DUPLICATE_KEY);
        }
        meta.getTables().forEach(table -> {
            TableDocument tableMeta =
                    documentPojoConverter.toTablePojo(docId, table);
            Integer tableMetaId = tableDocumentDao.insertAndReturnId(tableMeta);
            tableMeta.setId(tableMetaId);
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
        return docId;
    }

}
