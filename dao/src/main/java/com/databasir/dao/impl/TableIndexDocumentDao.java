package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.TableIndexDocumentPojo;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.databasir.dao.Tables.TABLE_INDEX_DOCUMENT;


@Repository
public class TableIndexDocumentDao extends BaseDao<TableIndexDocumentPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public TableIndexDocumentDao() {
        super(TABLE_INDEX_DOCUMENT, TableIndexDocumentPojo.class);
    }

    public List<TableIndexDocumentPojo> selectByDatabaseMetaId(Integer schemaMetaId) {
        return getDslContext()
                .select(TABLE_INDEX_DOCUMENT.fields()).from(TABLE_INDEX_DOCUMENT)
                .where(TABLE_INDEX_DOCUMENT.DATABASE_DOCUMENT_ID.eq(schemaMetaId))
                .fetchInto(TableIndexDocumentPojo.class);
    }

    public void deleteByDatabaseMetaId(Integer schemaMetaId) {
        getDslContext()
                .deleteFrom(TABLE_INDEX_DOCUMENT).where(TABLE_INDEX_DOCUMENT.DATABASE_DOCUMENT_ID.eq(schemaMetaId))
                .execute();
    }
}
