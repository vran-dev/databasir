package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DatabaseDocumentPojo;
import com.databasir.dao.tables.records.DatabaseDocumentRecord;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
                .select(DATABASE_DOCUMENT.fields()).from(DATABASE_DOCUMENT).where(DATABASE_DOCUMENT.PROJECT_ID.eq(projectId))
                .fetchOptionalInto(DatabaseDocumentPojo.class);
    }

    public void update(DatabaseDocumentPojo toPojo) {
        DatabaseDocumentRecord record = getDslContext().newRecord(DATABASE_DOCUMENT, toPojo);
        record.changed(DATABASE_DOCUMENT.ID, false);
        record.changed(DATABASE_DOCUMENT.CREATE_AT, false);
        record.update();
    }
}
