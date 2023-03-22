package com.databasir.core.domain.mock.validator;

import com.databasir.core.domain.DomainErrors;
import com.databasir.dao.impl.DatabaseDocumentDao;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.TableDocumentDao;
import com.databasir.dao.tables.pojos.DatabaseDocument;
import com.databasir.dao.tables.pojos.TableDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MockDataValidator {

    private final ProjectDao projectDao;

    private final DatabaseDocumentDao databaseDocumentDao;

    private final TableDocumentDao tableDocumentDao;

    public void validProject(Integer projectId) {
        if (!projectDao.existsById(projectId)) {
            throw DomainErrors.PROJECT_NOT_FOUND.exception();
        }
    }

    public DatabaseDocument validAndGetDatabaseDocument(Integer projectId, Long version) {
        Optional<DatabaseDocument> databaseDoc;
        if (version == null) {
            databaseDoc = databaseDocumentDao.selectNotArchivedByProjectId(projectId);
        } else {
            databaseDoc = databaseDocumentDao.selectOptionalByProjectIdAndVersion(projectId, version);
        }
        if (databaseDoc.isEmpty()) {
            throw DomainErrors.DATABASE_META_NOT_FOUND.exception();
        }
        return databaseDoc.get();
    }

    public TableDocument validAndGetTableDocument(Integer databaseDocId, Integer tableId) {
        Optional<TableDocument> tableOption =
            tableDocumentDao.selectByDatabaseDocumentIdAndId(databaseDocId, tableId);
        if (tableOption.isEmpty()) {
            throw DomainErrors.DATABASE_META_NOT_FOUND.exception();
        }
        return tableOption.get();
    }

}
