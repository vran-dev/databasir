package com.databasir.core.domain.mock.validator;

import com.databasir.core.domain.DomainErrors;
import com.databasir.dao.impl.DatabaseDocumentDao;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.TableDocumentDao;
import com.databasir.dao.tables.pojos.DatabaseDocumentPojo;
import com.databasir.dao.tables.pojos.TableDocumentPojo;
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

    public DatabaseDocumentPojo validAndGetDatabaseDocumentPojo(Integer projectId, Long version) {
        Optional<DatabaseDocumentPojo> databaseDoc;
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

    public TableDocumentPojo validAndGetTableDocumentPojo(Integer databaseDocId, Integer tableId) {
        Optional<TableDocumentPojo> tableOption =
                tableDocumentDao.selectByDatabaseDocumentIdAndId(databaseDocId, tableId);
        if (tableOption.isEmpty()) {
            throw DomainErrors.DATABASE_META_NOT_FOUND.exception("表数据不存在");
        }
        return tableOption.get();
    }

}
