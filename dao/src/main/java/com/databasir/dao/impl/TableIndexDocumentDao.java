package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.TableIndexDocument;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.databasir.dao.Tables.TABLE_INDEX_DOCUMENT;

@Repository
public class TableIndexDocumentDao extends BaseDao<TableIndexDocument> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public TableIndexDocumentDao() {
        super(TABLE_INDEX_DOCUMENT, TableIndexDocument.class);
    }

    public List<TableIndexDocument> selectByDatabaseMetaId(Integer documentId) {
        return getDslContext()
                .select(TABLE_INDEX_DOCUMENT.fields()).from(TABLE_INDEX_DOCUMENT)
                .where(TABLE_INDEX_DOCUMENT.DATABASE_DOCUMENT_ID.eq(documentId))
                .fetchInto(TableIndexDocument.class);
    }

    public List<TableIndexDocument> selectByDatabaseDocumentIdAndIdIn(Integer documentId,
                                                                          Collection<Integer> tableIdIn) {
        if (tableIdIn == null || tableIdIn.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .select(TABLE_INDEX_DOCUMENT.fields()).from(TABLE_INDEX_DOCUMENT)
                .where(TABLE_INDEX_DOCUMENT.DATABASE_DOCUMENT_ID.eq(documentId)
                        .and(TABLE_INDEX_DOCUMENT.TABLE_DOCUMENT_ID.in(tableIdIn)))
                .fetchInto(TableIndexDocument.class);
    }
}
