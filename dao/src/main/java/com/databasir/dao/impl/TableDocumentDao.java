package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.TableColumnDocumentPojo;
import com.databasir.dao.tables.pojos.TableDocumentPojo;
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

    public List<TableDocumentPojo> selectByDatabaseDocumentIdAndIdIn(Integer databaseDocumentId,
                                                                     Collection<Integer> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }
        return getDslContext()
                .selectFrom(TABLE_DOCUMENT)
                .where(TABLE_DOCUMENT.DATABASE_DOCUMENT_ID.eq(databaseDocumentId)
                        .and(TABLE_DOCUMENT.ID.in(idList)))
                .fetchInto(TableDocumentPojo.class);
    }

    public Optional<TableColumnDocumentPojo> selectByDatabaseDocumentIdAndTableName(Integer databaseDocumentId,
                                                                                    String tableName) {
        return getDslContext()
                .select(TABLE_DOCUMENT.fields()).from(TABLE_DOCUMENT)
                .where(TABLE_DOCUMENT.DATABASE_DOCUMENT_ID.eq(databaseDocumentId)
                        .and(TABLE_DOCUMENT.NAME.eq(tableName)))
                .fetchOptionalInto(TableColumnDocumentPojo.class);
    }

    public Optional<TableDocumentPojo> selectByDatabaseDocumentIdAndId(Integer databaseDocumentId,
                                                                       Integer id) {
        return getDslContext()
                .selectFrom(TABLE_DOCUMENT)
                .where(TABLE_DOCUMENT.DATABASE_DOCUMENT_ID.eq(databaseDocumentId)
                        .and(TABLE_DOCUMENT.ID.eq(id)))
                .fetchOptionalInto(TableDocumentPojo.class);
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
