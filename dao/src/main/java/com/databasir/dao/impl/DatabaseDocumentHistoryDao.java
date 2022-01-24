package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DatabaseDocumentHistoryPojo;
import com.databasir.dao.tables.records.DatabaseDocumentHistoryRecord;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.databasir.dao.Tables.DATABASE_DOCUMENT_HISTORY;


@Repository
public class DatabaseDocumentHistoryDao extends BaseDao<DatabaseDocumentHistoryRecord, DatabaseDocumentHistoryPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DatabaseDocumentHistoryDao() {
        super(DATABASE_DOCUMENT_HISTORY, DatabaseDocumentHistoryPojo.class);
    }

    public Optional<DatabaseDocumentHistoryPojo> selectOptionalByProjectIdAndVersion(Integer projectId, Long version) {
        return dslContext
                .selectFrom(DATABASE_DOCUMENT_HISTORY).where(DATABASE_DOCUMENT_HISTORY.PROJECT_ID.eq(projectId).and(DATABASE_DOCUMENT_HISTORY.VERSION.eq(version)))
                .fetchOptionalInto(DatabaseDocumentHistoryPojo.class);
    }

    public Page<DatabaseDocumentHistoryPojo> selectPageByDatabaseDocumentId(Pageable request, Integer schemaDocumentId) {
        return super.selectByPage(request, DATABASE_DOCUMENT_HISTORY.DATABASE_DOCUMENT_ID.eq(schemaDocumentId));
    }

}
