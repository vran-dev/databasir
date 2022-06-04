package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.TableTriggerDocument;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.databasir.dao.Tables.TABLE_TRIGGER_DOCUMENT;

@Repository
public class TableTriggerDocumentDao extends BaseDao<TableTriggerDocument> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public TableTriggerDocumentDao() {
        super(TABLE_TRIGGER_DOCUMENT, TableTriggerDocument.class);
    }

    public List<TableTriggerDocument> selectByDatabaseDocumentId(Integer schemaDocumentId) {
        return getDslContext()
                .select(TABLE_TRIGGER_DOCUMENT.fields()).from(TABLE_TRIGGER_DOCUMENT)
                .where(TABLE_TRIGGER_DOCUMENT.DATABASE_DOCUMENT_ID.eq(schemaDocumentId))
                .fetchInto(TableTriggerDocument.class);
    }

    public List<TableTriggerDocument> selectByDatabaseDocumentIdAndIdIn(Integer documentId,
                                                                            Collection<Integer> tableIdIn) {
        if (tableIdIn == null || tableIdIn.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .select(TABLE_TRIGGER_DOCUMENT.fields()).from(TABLE_TRIGGER_DOCUMENT)
                .where(TABLE_TRIGGER_DOCUMENT.DATABASE_DOCUMENT_ID.eq(documentId)
                        .and(TABLE_TRIGGER_DOCUMENT.TABLE_DOCUMENT_ID.in(tableIdIn)))
                .fetchInto(TableTriggerDocument.class);
    }
}
