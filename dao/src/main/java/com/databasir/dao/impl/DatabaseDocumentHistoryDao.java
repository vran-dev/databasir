package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DatabaseDocumentHistoryPojo;
import com.databasir.dao.tables.records.DatabaseDocumentHistoryRecord;
import com.databasir.dao.value.DatabaseDocumentVersionPojo;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public Page<DatabaseDocumentVersionPojo> selectVersionPageByDatabaseDocumentId(Pageable request, Integer schemaDocumentId) {
        Condition condition = DATABASE_DOCUMENT_HISTORY.DATABASE_DOCUMENT_ID.eq(schemaDocumentId);
        Integer count = getDslContext()
                .selectCount().from(DATABASE_DOCUMENT_HISTORY).where(condition)
                .fetchOne(0, int.class);
        int total = count == null ? 0 : count;
        List<DatabaseDocumentVersionPojo> data = getDslContext()
                .select(
                        DATABASE_DOCUMENT_HISTORY.VERSION,
                        DATABASE_DOCUMENT_HISTORY.DATABASE_DOCUMENT_ID,
                        DATABASE_DOCUMENT_HISTORY.CREATE_AT
                )
                .from(DATABASE_DOCUMENT_HISTORY)
                .where(condition)
                .orderBy(getSortFields(request.getSort()))
                .offset(request.getOffset())
                .limit(request.getPageSize())
                .fetchInto(DatabaseDocumentVersionPojo.class);
        return new PageImpl<>(data, request, total);
    }
}
