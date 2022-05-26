package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DatabaseDocumentPojo;
import com.databasir.dao.tables.records.DatabaseDocumentRecord;
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

import static com.databasir.dao.Tables.DATABASE_DOCUMENT;

@Repository
public class DatabaseDocumentDao extends BaseDao<DatabaseDocumentPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DatabaseDocumentDao() {
        super(DATABASE_DOCUMENT, DatabaseDocumentPojo.class);
    }

    public Optional<DatabaseDocumentPojo> selectOptionalByProjectId(Integer projectId) {
        return getDslContext()
                .select(DATABASE_DOCUMENT.fields()).from(DATABASE_DOCUMENT)
                .where(DATABASE_DOCUMENT.PROJECT_ID.eq(projectId))
                .fetchOptionalInto(DatabaseDocumentPojo.class);
    }

    public Optional<DatabaseDocumentPojo> selectOptionalByProjectIdAndVersion(Integer projectId,
                                                                              Long version) {
        return getDslContext()
                .select(DATABASE_DOCUMENT.fields()).from(DATABASE_DOCUMENT)
                .where(DATABASE_DOCUMENT.PROJECT_ID.eq(projectId).and(DATABASE_DOCUMENT.VERSION.eq(version)))
                .fetchOptionalInto(DatabaseDocumentPojo.class);
    }

    public Optional<Integer> selectIdByProjectIdAndVersion(Integer projectId,
                                                           Long version) {
        return getDslContext()
                .select(DATABASE_DOCUMENT.ID).from(DATABASE_DOCUMENT)
                .where(DATABASE_DOCUMENT.PROJECT_ID.eq(projectId).and(DATABASE_DOCUMENT.VERSION.eq(version)))
                .fetchOptionalInto(Integer.class);
    }

    public void update(DatabaseDocumentPojo toPojo) {
        DatabaseDocumentRecord record = getDslContext().newRecord(DATABASE_DOCUMENT, toPojo);
        record.changed(DATABASE_DOCUMENT.ID, false);
        record.changed(DATABASE_DOCUMENT.CREATE_AT, false);
        record.update();
    }

    public Optional<DatabaseDocumentPojo> selectNotArchivedByProjectId(Integer projectId) {
        return getDslContext()
                .select(DATABASE_DOCUMENT.fields()).from(DATABASE_DOCUMENT)
                .where(DATABASE_DOCUMENT.PROJECT_ID.eq(projectId).and(DATABASE_DOCUMENT.IS_ARCHIVE.eq(false)))
                .fetchOptionalInto(DatabaseDocumentPojo.class);
    }

    public void updateIsArchiveById(Integer id, Boolean isArchive) {
        this.getDslContext()
                .update(DATABASE_DOCUMENT).set(DATABASE_DOCUMENT.IS_ARCHIVE, isArchive)
                .where(DATABASE_DOCUMENT.ID.eq(id).and(DATABASE_DOCUMENT.IS_ARCHIVE.eq(!isArchive)))
                .execute();
    }

    public Page<DatabaseDocumentVersionPojo> selectVersionPageByProjectId(Pageable request,
                                                                          Integer projectId) {
        Condition condition = DATABASE_DOCUMENT.PROJECT_ID.eq(projectId);
        Integer count = getDslContext()
                .selectCount().from(DATABASE_DOCUMENT).where(condition)
                .fetchOne(0, int.class);
        int total = count == null ? 0 : count;
        List<DatabaseDocumentVersionPojo> data = getDslContext()
                .select(
                        DATABASE_DOCUMENT.VERSION,
                        DATABASE_DOCUMENT.ID,
                        DATABASE_DOCUMENT.CREATE_AT
                )
                .from(DATABASE_DOCUMENT)
                .where(condition)
                .orderBy(getSortFields(request.getSort()))
                .offset(request.getOffset())
                .limit(request.getPageSize())
                .fetchInto(DatabaseDocumentVersionPojo.class);
        return new PageImpl<>(data, request, total);
    }
}
