/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao;


import com.databasir.dao.tables.DataSourcePropertyTable;
import com.databasir.dao.tables.DocumentDiscussionTable;
import com.databasir.dao.tables.DocumentFullTextTable;
import com.databasir.dao.tables.ProjectSyncTaskTable;
import com.databasir.dao.tables.TableColumnDocumentTable;
import com.databasir.dao.tables.TableDocumentTable;
import com.databasir.dao.tables.TableForeignKeyDocumentTable;
import com.databasir.dao.tables.TableIndexDocumentTable;
import com.databasir.dao.tables.TableTriggerDocumentTable;

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

    public static final Index DOCUMENT_FULL_TEXT_FIDX_COLUMN = Internal.createIndex(DSL.name("FIDX_COLUMN"), DocumentFullTextTable.DOCUMENT_FULL_TEXT, new OrderField[] { DocumentFullTextTable.DOCUMENT_FULL_TEXT.COL_NAME, DocumentFullTextTable.DOCUMENT_FULL_TEXT.COL_COMMENT, DocumentFullTextTable.DOCUMENT_FULL_TEXT.COL_DESCRIPTION, DocumentFullTextTable.DOCUMENT_FULL_TEXT.DATABASE_PRODUCT_NAME }, false);
    public static final Index DOCUMENT_FULL_TEXT_FIDX_GROUP = Internal.createIndex(DSL.name("FIDX_GROUP"), DocumentFullTextTable.DOCUMENT_FULL_TEXT, new OrderField[] { DocumentFullTextTable.DOCUMENT_FULL_TEXT.GROUP_NAME, DocumentFullTextTable.DOCUMENT_FULL_TEXT.GROUP_DESCRIPTION }, false);
    public static final Index DOCUMENT_FULL_TEXT_FIDX_PROJECT = Internal.createIndex(DSL.name("FIDX_PROJECT"), DocumentFullTextTable.DOCUMENT_FULL_TEXT, new OrderField[] { DocumentFullTextTable.DOCUMENT_FULL_TEXT.PROJECT_NAME, DocumentFullTextTable.DOCUMENT_FULL_TEXT.PROJECT_DESCRIPTION, DocumentFullTextTable.DOCUMENT_FULL_TEXT.SCHEMA_NAME, DocumentFullTextTable.DOCUMENT_FULL_TEXT.DATABASE_NAME, DocumentFullTextTable.DOCUMENT_FULL_TEXT.DATABASE_TYPE }, false);
    public static final Index DOCUMENT_FULL_TEXT_FIDX_TABLE = Internal.createIndex(DSL.name("FIDX_TABLE"), DocumentFullTextTable.DOCUMENT_FULL_TEXT, new OrderField[] { DocumentFullTextTable.DOCUMENT_FULL_TEXT.TABLE_NAME, DocumentFullTextTable.DOCUMENT_FULL_TEXT.TABLE_COMMENT, DocumentFullTextTable.DOCUMENT_FULL_TEXT.TABLE_DESCRIPTION, DocumentFullTextTable.DOCUMENT_FULL_TEXT.DATABASE_PRODUCT_NAME }, false);
    public static final Index DATA_SOURCE_PROPERTY_IDX_DATA_SOURCE_ID = Internal.createIndex(DSL.name("idx_data_source_id"), DataSourcePropertyTable.DATA_SOURCE_PROPERTY, new OrderField[] { DataSourcePropertyTable.DATA_SOURCE_PROPERTY.DATA_SOURCE_ID }, false);
    public static final Index TABLE_COLUMN_DOCUMENT_IDX_DATABASE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_database_document_id"), TableColumnDocumentTable.TABLE_COLUMN_DOCUMENT, new OrderField[] { TableColumnDocumentTable.TABLE_COLUMN_DOCUMENT.DATABASE_DOCUMENT_ID }, false);
    public static final Index TABLE_DOCUMENT_IDX_DATABASE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_database_document_id"), TableDocumentTable.TABLE_DOCUMENT, new OrderField[] { TableDocumentTable.TABLE_DOCUMENT.DATABASE_DOCUMENT_ID }, false);
    public static final Index TABLE_FOREIGN_KEY_DOCUMENT_IDX_DATABASE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_database_document_id"), TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT, new OrderField[] { TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.DATABASE_DOCUMENT_ID }, false);
    public static final Index TABLE_INDEX_DOCUMENT_IDX_DATABASE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_database_document_id"), TableIndexDocumentTable.TABLE_INDEX_DOCUMENT, new OrderField[] { TableIndexDocumentTable.TABLE_INDEX_DOCUMENT.DATABASE_DOCUMENT_ID }, false);
    public static final Index TABLE_TRIGGER_DOCUMENT_IDX_DATABASE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_database_document_id"), TableTriggerDocumentTable.TABLE_TRIGGER_DOCUMENT, new OrderField[] { TableTriggerDocumentTable.TABLE_TRIGGER_DOCUMENT.DATABASE_DOCUMENT_ID }, false);
    public static final Index DOCUMENT_FULL_TEXT_IDX_GROUP_ID = Internal.createIndex(DSL.name("IDX_GROUP_ID"), DocumentFullTextTable.DOCUMENT_FULL_TEXT, new OrderField[] { DocumentFullTextTable.DOCUMENT_FULL_TEXT.GROUP_ID }, false);
    public static final Index DOCUMENT_DISCUSSION_IDX_PROJECT_ID = Internal.createIndex(DSL.name("idx_project_id"), DocumentDiscussionTable.DOCUMENT_DISCUSSION, new OrderField[] { DocumentDiscussionTable.DOCUMENT_DISCUSSION.PROJECT_ID }, false);
    public static final Index DOCUMENT_FULL_TEXT_IDX_PROJECT_ID = Internal.createIndex(DSL.name("IDX_PROJECT_ID"), DocumentFullTextTable.DOCUMENT_FULL_TEXT, new OrderField[] { DocumentFullTextTable.DOCUMENT_FULL_TEXT.PROJECT_ID }, false);
    public static final Index PROJECT_SYNC_TASK_IDX_PROJECT_ID = Internal.createIndex(DSL.name("idx_project_id"), ProjectSyncTaskTable.PROJECT_SYNC_TASK, new OrderField[] { ProjectSyncTaskTable.PROJECT_SYNC_TASK.PROJECT_ID }, false);
    public static final Index DOCUMENT_FULL_TEXT_IDX_TABLE_DOCUMENT_ID = Internal.createIndex(DSL.name("IDX_TABLE_DOCUMENT_ID"), DocumentFullTextTable.DOCUMENT_FULL_TEXT, new OrderField[] { DocumentFullTextTable.DOCUMENT_FULL_TEXT.TABLE_DOCUMENT_ID }, false);
    public static final Index TABLE_COLUMN_DOCUMENT_IDX_TABLE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_table_document_id"), TableColumnDocumentTable.TABLE_COLUMN_DOCUMENT, new OrderField[] { TableColumnDocumentTable.TABLE_COLUMN_DOCUMENT.TABLE_DOCUMENT_ID }, false);
    public static final Index TABLE_FOREIGN_KEY_DOCUMENT_IDX_TABLE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_table_document_id"), TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT, new OrderField[] { TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.TABLE_DOCUMENT_ID }, false);
    public static final Index TABLE_INDEX_DOCUMENT_IDX_TABLE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_table_document_id"), TableIndexDocumentTable.TABLE_INDEX_DOCUMENT, new OrderField[] { TableIndexDocumentTable.TABLE_INDEX_DOCUMENT.TABLE_DOCUMENT_ID }, false);
    public static final Index TABLE_TRIGGER_DOCUMENT_IDX_TABLE_DOCUMENT_ID = Internal.createIndex(DSL.name("idx_table_document_id"), TableTriggerDocumentTable.TABLE_TRIGGER_DOCUMENT, new OrderField[] { TableTriggerDocumentTable.TABLE_TRIGGER_DOCUMENT.TABLE_DOCUMENT_ID }, false);
    public static final Index PROJECT_SYNC_TASK_IDX_USER_ID = Internal.createIndex(DSL.name("idx_user_id"), ProjectSyncTaskTable.PROJECT_SYNC_TASK, new OrderField[] { ProjectSyncTaskTable.PROJECT_SYNC_TASK.USER_ID }, false);
}
