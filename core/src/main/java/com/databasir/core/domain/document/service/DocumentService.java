package com.databasir.core.domain.document.service;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.document.converter.DocumentDiffConverter;
import com.databasir.core.domain.document.converter.DocumentResponseConverter;
import com.databasir.core.domain.document.converter.DocumentSimpleResponseConverter;
import com.databasir.core.domain.document.converter.TableResponseConverter;
import com.databasir.core.domain.document.data.*;
import com.databasir.core.domain.document.data.diff.DatabaseDocDiff;
import com.databasir.core.domain.document.data.diff.TableDocDiff;
import com.databasir.core.domain.document.diff.DiffTypePredictor;
import com.databasir.core.domain.document.diff.DocumentDiffChecker;
import com.databasir.core.domain.document.generator.DocumentFileGenerator;
import com.databasir.core.domain.document.generator.DocumentFileGenerator.DocumentFileGenerateContext;
import com.databasir.core.domain.document.generator.DocumentFileType;
import com.databasir.dao.impl.*;
import com.databasir.dao.tables.pojos.*;
import com.databasir.dao.value.DocumentDiscussionCountPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.tools.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {

    private final ProjectDao projectDao;

    private final DatabaseDocumentDao databaseDocumentDao;

    private final TableDocumentDao tableDocumentDao;

    private final TableColumnDocumentDao tableColumnDocumentDao;

    private final TableIndexDocumentDao tableIndexDocumentDao;

    private final TableTriggerDocumentDao tableTriggerDocumentDao;

    private final TableForeignKeyDocumentDao tableForeignKeyDocumentDao;

    private final DocumentDiscussionDao documentDiscussionDao;

    private final DocumentDescriptionDao documentDescriptionDao;

    private final DocumentResponseConverter documentResponseConverter;

    private final DocumentSimpleResponseConverter documentSimpleResponseConverter;

    private final DocumentDiffConverter documentDiffConverter;

    private final TableResponseConverter tableResponseConverter;

    private final List<DocumentFileGenerator> documentFileGenerators;

    private final DocumentDiffChecker documentDiffChecker;

    public Optional<DatabaseDocumentSimpleResponse> getSimpleOneByProjectId(Integer projectId,
                                                                            Long version,
                                                                            Long originalVersion) {
        String projectName = projectDao.selectOptionalById(projectId)
                .map(Project::getName)
                .orElseThrow(DomainErrors.PROJECT_NOT_FOUND::exception);

        Optional<DatabaseDocument> documentOption;
        if (version == null) {
            documentOption = databaseDocumentDao.selectNotArchivedByProjectId(projectId);
        } else {
            documentOption = databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, version);
        }

        return documentOption.map(document -> {
            var discussionCountMapByTableName =
                    documentDiscussionDao.selectAllDiscussionCount(projectId)
                            .stream()
                            .collect(Collectors.toMap(d -> d.getTableName(), d -> d.getCount(), (a, b) -> a));
            var descriptionMapByTableName =
                    documentDescriptionDao.selectByProjectId(projectId)
                            .stream()
                            .collect(Collectors.toMap(d -> d.getTableName(), d -> d.getContent(), (a, b) -> a));
            if (originalVersion != null) {
                var diffResults = tableDiffs(projectId, originalVersion, version);
                var tableMetas = documentSimpleResponseConverter.ofDiff(
                        diffResults, discussionCountMapByTableName, descriptionMapByTableName);
                DiffType diffType = DiffTypePredictor.predict(tableMetas);
                tableMetas.sort(Comparator.comparing(DatabaseDocumentSimpleResponse.TableData::getName));
                return documentSimpleResponseConverter.of(document, tableMetas, diffType, projectName);
            } else {
                Integer id = document.getId();
                var tables = tableDocumentDao.selectByDatabaseDocumentId(id);
                var tableMetas = documentSimpleResponseConverter.of(
                        tables,
                        discussionCountMapByTableName,
                        descriptionMapByTableName
                );
                return documentSimpleResponseConverter.of(document, tableMetas, DiffType.NONE, projectName);
            }
        });
    }

    public List<TableDocDiff> tableDiffs(Integer projectId, Long originalVersion, Long currentVersion) {
        var original = databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, originalVersion)
                .orElseThrow(DomainErrors.DOCUMENT_VERSION_IS_INVALID::exception);
        DatabaseDocument current;
        if (currentVersion == null) {
            current = databaseDocumentDao.selectNotArchivedByProjectId(projectId)
                    .orElseThrow(DomainErrors.DOCUMENT_VERSION_IS_INVALID::exception);
        } else {
            current = databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, currentVersion)
                    .orElseThrow(DomainErrors.DOCUMENT_VERSION_IS_INVALID::exception);
        }
        DatabaseDocDiff currentDiff = retrieveDatabaseDocumentDiff(current);
        DatabaseDocDiff originalDiff = retrieveDatabaseDocumentDiff(original);
        return documentDiffChecker.diff(originalDiff.getTables(), currentDiff.getTables());
    }

    public Optional<DatabaseDocumentResponse> getOneByProjectId(Integer projectId, Long version) {

        Optional<DatabaseDocument> documentOption;
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
            Map<Integer, List<TableColumnDocument>> columnsGroupByTableMetaId = columns.stream()
                    .collect(Collectors.groupingBy(TableColumnDocument::getTableDocumentId));
            Map<Integer, List<TableIndexDocument>> indexesGroupByTableMetaId = indexes.stream()
                    .collect(Collectors.groupingBy(TableIndexDocument::getTableDocumentId));
            Map<Integer, List<TableTriggerDocument>> triggersGroupByTableMetaId = triggers.stream()
                    .collect(Collectors.groupingBy(TableTriggerDocument::getTableDocumentId));
            Map<Integer, List<TableForeignKeyDocument>> foreignKeysGroupByTableMetaId = foreignKeys.stream()
                    .collect(Collectors.groupingBy(TableForeignKeyDocument::getTableDocumentId));
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
        Map<Integer, List<TableColumnDocument>> columnsGroupByTableMetaId = columns.stream()
                .collect(Collectors.groupingBy(TableColumnDocument::getTableDocumentId));

        // index
        var indexes =
                tableIndexDocumentDao.selectByDatabaseDocumentIdAndIdIn(databaseDocumentId, tableIds);
        Map<Integer, List<TableIndexDocument>> indexesGroupByTableMetaId = indexes.stream()
                .collect(Collectors.groupingBy(TableIndexDocument::getTableDocumentId));

        // foreign keys
        var foreignKeys =
                tableForeignKeyDocumentDao.selectByDatabaseDocumentIdAndTableIdIn(databaseDocumentId, tableIds);
        Map<Integer, List<TableForeignKeyDocument>> foreignKeysGroupByTableMetaId = foreignKeys.stream()
                .collect(Collectors.groupingBy(TableForeignKeyDocument::getTableDocumentId));

        // trigger
        var triggers =
                tableTriggerDocumentDao.selectByDatabaseDocumentIdAndIdIn(databaseDocumentId, tableIds);
        Map<Integer, List<TableTriggerDocument>> triggersGroupByTableMetaId = triggers.stream()
                .collect(Collectors.groupingBy(TableTriggerDocument::getTableDocumentId));

        // discussion
        var discussions = documentDiscussionDao.selectAllDiscussionCount(projectId);
        Map<String, Integer> discussionCountMapByJoinName = discussions.stream()
                .collect(Collectors.toMap(
                        d -> d.getTableName(),
                        DocumentDiscussionCountPojo::getCount,
                        (a, b) -> a));

        // description
        var descriptions = documentDescriptionDao.selectByProjectId(projectId);
        Map<String, String> descriptionMapByJoinName = descriptions.stream()
                .collect(Collectors.toMap(
                        d -> String.join(".",
                                d.getTableName(),
                                StringUtils.defaultIfBlank(d.getColumnName(), "")),
                        DocumentDescription::getContent,
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

        if (request.getOriginalVersion() != null) {
            List<TableDocDiff> diffResults =
                    this.tableDiffs(projectId, request.getOriginalVersion(), request.getCurrentVersion());
            // discussion
            var discussions = documentDiscussionDao.selectAllDiscussionCount(projectId);
            Map<String, Integer> discussionCountMapByJoinName = discussions.stream()
                    .collect(Collectors.toMap(
                            d -> d.getTableName(),
                            DocumentDiscussionCountPojo::getCount,
                            (a, b) -> a));

            // description
            var descriptions = documentDescriptionDao.selectByProjectId(projectId);
            Map<String, String> descriptionMapByJoinName = descriptions.stream()
                    .collect(Collectors.toMap(
                            d -> String.join(".", d.getTableName(), d.getColumnName()),
                            DocumentDescription::getContent,
                            (a, b) -> a));
            return diffResults.stream()
                    .filter(tableDocDiff -> request.getTableIds().contains(tableDocDiff.getId()))
                    .map(diff -> documentResponseConverter.ofDiff(
                            diff, discussionCountMapByJoinName, descriptionMapByJoinName))
                    .sorted(Comparator.comparing(TableDocumentResponse::getName))
                    .collect(Collectors.toList());
        } else {
            var current = this.getTableDetails(projectId, databaseDocumentId, request.getTableIds());
            current.sort(Comparator.comparing(TableDocumentResponse::getName));
            return current;
        }
    }

    public void export(Integer projectId,
                       Long version,
                       List<Integer> tableIds,
                       DocumentFileType type,
                       OutputStream out) {
        DatabaseDocumentResponse doc;
        if (tableIds == null || CollectionUtils.isEmpty(tableIds)) {
            doc = getOneByProjectId(projectId, version)
                    .orElseThrow(DomainErrors.DOCUMENT_VERSION_IS_INVALID::exception);
        } else {
            DatabaseDocument databaseDoc;
            if (version == null) {
                databaseDoc = databaseDocumentDao.selectNotArchivedByProjectId(projectId)
                        .orElseThrow(DomainErrors.DOCUMENT_VERSION_IS_INVALID::exception);
            } else {
                databaseDoc = databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, version)
                        .orElseThrow(DomainErrors.DOCUMENT_VERSION_IS_INVALID::exception);
            }
            Integer databaseDocId = databaseDoc.getId();
            List<TableDocumentResponse> tableDocs = getTableDetails(projectId, databaseDocId, tableIds);
            doc = documentResponseConverter.of(databaseDoc, tableDocs);
        }
        var context = DocumentFileGenerateContext.builder()
                .documentFileType(type)
                .databaseDocument(doc)
                .build();
        documentFileGenerators.stream()
                .filter(g -> g.support(type))
                .findFirst()
                .ifPresent(generator -> generator.generate(context, out));
    }

    public List<TableResponse> getTableAndColumns(Integer projectId, Long version) {
        Optional<DatabaseDocument> documentOption;
        if (version == null) {
            documentOption = databaseDocumentDao.selectNotArchivedByProjectId(projectId);
        } else {
            documentOption = databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, version);
        }
        if (documentOption.isEmpty()) {
            return emptyList();
        } else {
            DatabaseDocument databaseDoc = documentOption.get();
            var tables = tableDocumentDao.selectByDatabaseDocumentId(databaseDoc.getId());
            var columns = tableColumnDocumentDao.selectByDatabaseDocumentId(databaseDoc.getId());
            var columnMapByTableId = columns.stream()
                    .collect(Collectors.groupingBy(TableColumnDocument::getTableDocumentId));
            return tableResponseConverter.from(tables, columnMapByTableId);
        }
    }

    private DatabaseDocDiff retrieveDatabaseDocumentDiff(DatabaseDocument databaseDocument) {
        Integer docId = databaseDocument.getId();
        List<TableDocument> tables = tableDocumentDao.selectByDatabaseDocumentId(docId);
        List<TableColumnDocument> columns = tableColumnDocumentDao.selectByDatabaseDocumentId(docId);
        List<TableIndexDocument> indexes = tableIndexDocumentDao.selectByDatabaseMetaId(docId);
        List<TableTriggerDocument> triggers = tableTriggerDocumentDao.selectByDatabaseDocumentId(docId);
        List<TableForeignKeyDocument> fks = tableForeignKeyDocumentDao.selectByDatabaseDocumentId(docId);
        return documentDiffConverter.of(databaseDocument, tables, columns, indexes, triggers, fks);
    }

}
