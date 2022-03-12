/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao;


import com.databasir.dao.tables.DataSourceProperty;
import com.databasir.dao.tables.DatabaseDocument;
import com.databasir.dao.tables.DocumentDiscussion;
import com.databasir.dao.tables.TableColumnDocument;
import com.databasir.dao.tables.TableDocument;
import com.databasir.dao.tables.TableIndexDocument;
import com.databasir.dao.tables.TableTriggerDocument;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in databasir.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index DATA_SOURCE_PROPERTY_IDX_DATA_SOURCE_ID = Internal.createIndex(DSL.name("idx_data_source_id"), DataSourceProperty.DATA_SOURCE_PROPERTY, new OrderField[] { DataSourceProperty.DATA_SOURCE_PROPERTY.DATA_SOURCE_ID }, false);
    public static final Index TABLE_COLUMN_DOCUMENT_IDX_DATABASE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_database_document_id"), TableColumnDocument.TABLE_COLUMN_DOCUMENT, new OrderField[] { TableColumnDocument.TABLE_COLUMN_DOCUMENT.DATABASE_DOCUMENT_ID }, false);
    public static final Index TABLE_DOCUMENT_IDX_DATABASE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_database_document_id"), TableDocument.TABLE_DOCUMENT, new OrderField[] { TableDocument.TABLE_DOCUMENT.DATABASE_DOCUMENT_ID }, false);
    public static final Index TABLE_INDEX_DOCUMENT_IDX_DATABASE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_database_document_id"), TableIndexDocument.TABLE_INDEX_DOCUMENT, new OrderField[] { TableIndexDocument.TABLE_INDEX_DOCUMENT.DATABASE_DOCUMENT_ID }, false);
    public static final Index TABLE_TRIGGER_DOCUMENT_IDX_DATABASE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_database_document_id"), TableTriggerDocument.TABLE_TRIGGER_DOCUMENT, new OrderField[] { TableTriggerDocument.TABLE_TRIGGER_DOCUMENT.DATABASE_DOCUMENT_ID }, false);
    public static final Index DATABASE_DOCUMENT_IDX_PROJECT_ID = Internal.createIndex(DSL.name("idx_project_id"), DatabaseDocument.DATABASE_DOCUMENT, new OrderField[] { DatabaseDocument.DATABASE_DOCUMENT.PROJECT_ID }, false);
    public static final Index DOCUMENT_DISCUSSION_IDX_PROJECT_ID = Internal.createIndex(DSL.name("idx_project_id"), DocumentDiscussion.DOCUMENT_DISCUSSION, new OrderField[] { DocumentDiscussion.DOCUMENT_DISCUSSION.PROJECT_ID }, false);
    public static final Index TABLE_COLUMN_DOCUMENT_IDX_TABLE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_table_document_id"), TableColumnDocument.TABLE_COLUMN_DOCUMENT, new OrderField[] { TableColumnDocument.TABLE_COLUMN_DOCUMENT.TABLE_DOCUMENT_ID }, false);
    public static final Index TABLE_INDEX_DOCUMENT_IDX_TABLE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_table_document_id"), TableIndexDocument.TABLE_INDEX_DOCUMENT, new OrderField[] { TableIndexDocument.TABLE_INDEX_DOCUMENT.TABLE_DOCUMENT_ID }, false);
    public static final Index TABLE_TRIGGER_DOCUMENT_IDX_TABLE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_table_document_id"), TableTriggerDocument.TABLE_TRIGGER_DOCUMENT, new OrderField[] { TableTriggerDocument.TABLE_TRIGGER_DOCUMENT.TABLE_DOCUMENT_ID }, false);
}
