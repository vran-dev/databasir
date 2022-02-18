package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.TableColumnDocumentPojo;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.databasir.dao.Tables.TABLE_COLUMN_DOCUMENT;


@Repository
public class TableColumnDocumentDao extends BaseDao<TableColumnDocumentPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public TableColumnDocumentDao() {
        super(TABLE_COLUMN_DOCUMENT, TableColumnDocumentPojo.class);
    }

    public List<TableColumnDocumentPojo> selectByDatabaseDocumentId(Integer schemaDocumentId) {
        return getDslContext()
                .select(TABLE_COLUMN_DOCUMENT.fields()).from(TABLE_COLUMN_DOCUMENT)
                .where(TABLE_COLUMN_DOCUMENT.DATABASE_DOCUMENT_ID.eq(schemaDocumentId))
                .fetchInto(TableColumnDocumentPojo.class);
    }

    public void deleteByDatabaseDocumentId(Integer schemaDocumentId) {
        getDslContext()
                .deleteFrom(TABLE_COLUMN_DOCUMENT).where(TABLE_COLUMN_DOCUMENT.DATABASE_DOCUMENT_ID.eq(schemaDocumentId))
                .execute();
    }
}
