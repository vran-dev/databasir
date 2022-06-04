package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.TableColumnDocument;
import com.databasir.dao.tables.pojos.TableDocument;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.databasir.dao.Tables.TABLE_DOCUMENT;

@Repository
public class TableDocumentDao extends BaseDao<TableDocument> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public TableDocumentDao() {
        super(TABLE_DOCUMENT, TableDocument.class);
    }

    public List<TableDocument> selectByDatabaseDocumentId(Integer schemaDocumentId) {
        return getDslContext()
                .select(TABLE_DOCUMENT.fields()).from(TABLE_DOCUMENT)
                .where(TABLE_DOCUMENT.DATABASE_DOCUMENT_ID.eq(schemaDocumentId))
                .fetchInto(TableDocument.class);
    }

    public List<TableDocument> selectByDatabaseDocumentIdAndIdIn(Integer databaseDocumentId,
                                                                     Collection<Integer> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .selectFrom(TABLE_DOCUMENT)
                .where(TABLE_DOCUMENT.DATABASE_DOCUMENT_ID.eq(databaseDocumentId)
                        .and(TABLE_DOCUMENT.ID.in(idList)))
                .fetchInto(TableDocument.class);
    }

    public Optional<TableColumnDocument> selectByDatabaseDocumentIdAndTableName(Integer databaseDocumentId,
                                                                                    String tableName) {
        return getDslContext()
                .select(TABLE_DOCUMENT.fields()).from(TABLE_DOCUMENT)
                .where(TABLE_DOCUMENT.DATABASE_DOCUMENT_ID.eq(databaseDocumentId)
                        .and(TABLE_DOCUMENT.NAME.eq(tableName)))
                .fetchOptionalInto(TableColumnDocument.class);
    }

    public Optional<TableDocument> selectByDatabaseDocumentIdAndId(Integer databaseDocumentId,
                                                                       Integer id) {
        return getDslContext()
                .selectFrom(TABLE_DOCUMENT)
                .where(TABLE_DOCUMENT.DATABASE_DOCUMENT_ID.eq(databaseDocumentId)
                        .and(TABLE_DOCUMENT.ID.eq(id)))
                .fetchOptionalInto(TableDocument.class);
    }

    public List<Integer> selectTableIdsByDatabaseDocumentIdAndTableNameIn(Integer databaseDocumentId,
                                                                         Collection<String> tableNames) {
        if (tableNames == null || tableNames.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .select(TABLE_DOCUMENT.ID).from(TABLE_DOCUMENT)
                .where(TABLE_DOCUMENT.DATABASE_DOCUMENT_ID.eq(databaseDocumentId)
                        .and(TABLE_DOCUMENT.NAME.in(tableNames)))
                .fetchInto(Integer.class);
    }
}
