/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao;


import com.databasir.dao.tables.DataSourcePropertyTable;
import com.databasir.dao.tables.DataSourceTable;
import com.databasir.dao.tables.DatabaseDocumentTable;
import com.databasir.dao.tables.DatabaseTypeTable;
import com.databasir.dao.tables.DocumentDescriptionTable;
import com.databasir.dao.tables.DocumentDiscussionTable;
import com.databasir.dao.tables.DocumentFullTextTable;
import com.databasir.dao.tables.DocumentTemplatePropertyTable;
import com.databasir.dao.tables.GroupTable;
import com.databasir.dao.tables.LoginTable;
import com.databasir.dao.tables.MockDataRuleTable;
import com.databasir.dao.tables.OauthAppPropertyTable;
import com.databasir.dao.tables.OauthAppTable;
import com.databasir.dao.tables.OperationLogTable;
import com.databasir.dao.tables.ProjectSyncRuleTable;
import com.databasir.dao.tables.ProjectSyncTaskTable;
import com.databasir.dao.tables.ProjectTable;
import com.databasir.dao.tables.SysKeyTable;
import com.databasir.dao.tables.SysMailTable;
import com.databasir.dao.tables.TableColumnDocumentTable;
import com.databasir.dao.tables.TableDocumentTable;
import com.databasir.dao.tables.TableForeignKeyDocumentTable;
import com.databasir.dao.tables.TableIndexDocumentTable;
import com.databasir.dao.tables.TableTriggerDocumentTable;
import com.databasir.dao.tables.UserFavoriteProjectTable;
import com.databasir.dao.tables.UserRoleTable;
import com.databasir.dao.tables.UserTable;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Databasir extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>databasir</code>
     */
    public static final Databasir DATABASIR = new Databasir();

    /**
     * The table <code>databasir.data_source</code>.
     */
    public final DataSourceTable DATA_SOURCE = DataSourceTable.DATA_SOURCE;

    /**
     * The table <code>databasir.data_source_property</code>.
     */
    public final DataSourcePropertyTable DATA_SOURCE_PROPERTY = DataSourcePropertyTable.DATA_SOURCE_PROPERTY;

    /**
     * The table <code>databasir.database_document</code>.
     */
    public final DatabaseDocumentTable DATABASE_DOCUMENT = DatabaseDocumentTable.DATABASE_DOCUMENT;

    /**
     * customer database types
     */
    public final DatabaseTypeTable DATABASE_TYPE = DatabaseTypeTable.DATABASE_TYPE;

    /**
     * custom document description
     */
    public final DocumentDescriptionTable DOCUMENT_DESCRIPTION = DocumentDescriptionTable.DOCUMENT_DESCRIPTION;

    /**
     * The table <code>databasir.document_discussion</code>.
     */
    public final DocumentDiscussionTable DOCUMENT_DISCUSSION = DocumentDiscussionTable.DOCUMENT_DISCUSSION;

    /**
     * The table <code>databasir.document_full_text</code>.
     */
    public final DocumentFullTextTable DOCUMENT_FULL_TEXT = DocumentFullTextTable.DOCUMENT_FULL_TEXT;

    /**
     * template property
     */
    public final DocumentTemplatePropertyTable DOCUMENT_TEMPLATE_PROPERTY = DocumentTemplatePropertyTable.DOCUMENT_TEMPLATE_PROPERTY;

    /**
     * The table <code>databasir.group</code>.
     */
    public final GroupTable GROUP = GroupTable.GROUP;

    /**
     * The table <code>databasir.login</code>.
     */
    public final LoginTable LOGIN = LoginTable.LOGIN;

    /**
     * The table <code>databasir.mock_data_rule</code>.
     */
    public final MockDataRuleTable MOCK_DATA_RULE = MockDataRuleTable.MOCK_DATA_RULE;

    /**
     * oauth app info
     */
    public final OauthAppTable OAUTH_APP = OauthAppTable.OAUTH_APP;

    /**
     * The table <code>databasir.oauth_app_property</code>.
     */
    public final OauthAppPropertyTable OAUTH_APP_PROPERTY = OauthAppPropertyTable.OAUTH_APP_PROPERTY;

    /**
     * The table <code>databasir.operation_log</code>.
     */
    public final OperationLogTable OPERATION_LOG = OperationLogTable.OPERATION_LOG;

    /**
     * The table <code>databasir.project</code>.
     */
    public final ProjectTable PROJECT = ProjectTable.PROJECT;

    /**
     * The table <code>databasir.project_sync_rule</code>.
     */
    public final ProjectSyncRuleTable PROJECT_SYNC_RULE = ProjectSyncRuleTable.PROJECT_SYNC_RULE;

    /**
     * The table <code>databasir.project_sync_task</code>.
     */
    public final ProjectSyncTaskTable PROJECT_SYNC_TASK = ProjectSyncTaskTable.PROJECT_SYNC_TASK;

    /**
     * The table <code>databasir.sys_key</code>.
     */
    public final SysKeyTable SYS_KEY = SysKeyTable.SYS_KEY;

    /**
     * The table <code>databasir.sys_mail</code>.
     */
    public final SysMailTable SYS_MAIL = SysMailTable.SYS_MAIL;

    /**
     * The table <code>databasir.table_column_document</code>.
     */
    public final TableColumnDocumentTable TABLE_COLUMN_DOCUMENT = TableColumnDocumentTable.TABLE_COLUMN_DOCUMENT;

    /**
     * The table <code>databasir.table_document</code>.
     */
    public final TableDocumentTable TABLE_DOCUMENT = TableDocumentTable.TABLE_DOCUMENT;

    /**
     * The table <code>databasir.table_foreign_key_document</code>.
     */
    public final TableForeignKeyDocumentTable TABLE_FOREIGN_KEY_DOCUMENT = TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT;

    /**
     * The table <code>databasir.table_index_document</code>.
     */
    public final TableIndexDocumentTable TABLE_INDEX_DOCUMENT = TableIndexDocumentTable.TABLE_INDEX_DOCUMENT;

    /**
     * The table <code>databasir.table_trigger_document</code>.
     */
    public final TableTriggerDocumentTable TABLE_TRIGGER_DOCUMENT = TableTriggerDocumentTable.TABLE_TRIGGER_DOCUMENT;

    /**
     * The table <code>databasir.user</code>.
     */
    public final UserTable USER = UserTable.USER;

    /**
     * The table <code>databasir.user_favorite_project</code>.
     */
    public final UserFavoriteProjectTable USER_FAVORITE_PROJECT = UserFavoriteProjectTable.USER_FAVORITE_PROJECT;

    /**
     * The table <code>databasir.user_role</code>.
     */
    public final UserRoleTable USER_ROLE = UserRoleTable.USER_ROLE;

    /**
     * No further instances allowed
     */
    private Databasir() {
        super("databasir", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            DataSourceTable.DATA_SOURCE,
            DataSourcePropertyTable.DATA_SOURCE_PROPERTY,
            DatabaseDocumentTable.DATABASE_DOCUMENT,
            DatabaseTypeTable.DATABASE_TYPE,
            DocumentDescriptionTable.DOCUMENT_DESCRIPTION,
            DocumentDiscussionTable.DOCUMENT_DISCUSSION,
            DocumentFullTextTable.DOCUMENT_FULL_TEXT,
            DocumentTemplatePropertyTable.DOCUMENT_TEMPLATE_PROPERTY,
            GroupTable.GROUP,
            LoginTable.LOGIN,
            MockDataRuleTable.MOCK_DATA_RULE,
            OauthAppTable.OAUTH_APP,
            OauthAppPropertyTable.OAUTH_APP_PROPERTY,
            OperationLogTable.OPERATION_LOG,
            ProjectTable.PROJECT,
            ProjectSyncRuleTable.PROJECT_SYNC_RULE,
            ProjectSyncTaskTable.PROJECT_SYNC_TASK,
            SysKeyTable.SYS_KEY,
            SysMailTable.SYS_MAIL,
            TableColumnDocumentTable.TABLE_COLUMN_DOCUMENT,
            TableDocumentTable.TABLE_DOCUMENT,
            TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT,
            TableIndexDocumentTable.TABLE_INDEX_DOCUMENT,
            TableTriggerDocumentTable.TABLE_TRIGGER_DOCUMENT,
            UserTable.USER,
            UserFavoriteProjectTable.USER_FAVORITE_PROJECT,
            UserRoleTable.USER_ROLE
        );
    }
}
