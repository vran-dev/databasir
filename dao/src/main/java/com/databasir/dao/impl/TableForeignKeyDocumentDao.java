package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.TableForeignKeyDocument;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.databasir.dao.Tables.TABLE_FOREIGN_KEY_DOCUMENT;

@Repository
public class TableForeignKeyDocumentDao extends BaseDao<TableForeignKeyDocument> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public TableForeignKeyDocumentDao() {
        super(TABLE_FOREIGN_KEY_DOCUMENT, TableForeignKeyDocument.class);
    }

    public List<TableForeignKeyDocument> selectByDatabaseDocumentId(Integer databaseDocumentId) {
        return getDslContext()
                .selectFrom(TABLE_FOREIGN_KEY_DOCUMENT)
                .where(TABLE_FOREIGN_KEY_DOCUMENT.DATABASE_DOCUMENT_ID.eq(databaseDocumentId))
                .fetchInto(TableForeignKeyDocument.class);
    }

    public List<TableForeignKeyDocument> selectByDatabaseDocumentIdAndTableIdIn(Integer databaseDocumentId,
                                                                                    Collection<Integer> tableIdIn) {
        if (tableIdIn == null || tableIdIn.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .selectFrom(TABLE_FOREIGN_KEY_DOCUMENT)
                .where(TABLE_FOREIGN_KEY_DOCUMENT.DATABASE_DOCUMENT_ID.eq(databaseDocumentId)
                        .and(TABLE_FOREIGN_KEY_DOCUMENT.TABLE_DOCUMENT_ID.in(tableIdIn)))
                .fetchInto(TableForeignKeyDocument.class);
    }
}
