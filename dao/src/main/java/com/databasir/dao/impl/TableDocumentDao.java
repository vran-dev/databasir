package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.TableDocumentPojo;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.databasir.dao.Tables.TABLE_DOCUMENT;

@Repository
public class TableDocumentDao extends BaseDao<TableDocumentPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public TableDocumentDao() {
        super(TABLE_DOCUMENT, TableDocumentPojo.class);
    }

    public List<TableDocumentPojo> selectByDatabaseDocumentId(Integer schemaDocumentId) {
        return getDslContext()
                .select(TABLE_DOCUMENT.fields()).from(TABLE_DOCUMENT)
                .where(TABLE_DOCUMENT.DATABASE_DOCUMENT_ID.eq(schemaDocumentId))
                .fetchInto(TableDocumentPojo.class);
    }

    public void deleteByDatabaseDocumentId(Integer schemaDocumentId) {
        getDslContext()
                .deleteFrom(TABLE_DOCUMENT).where(TABLE_DOCUMENT.DATABASE_DOCUMENT_ID.eq(schemaDocumentId))
                .execute();
    }
}
