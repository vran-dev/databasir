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
import com.databasir.dao.tables.records.DataSourcePropertyRecord;
import com.databasir.dao.tables.records.DataSourceRecord;
import com.databasir.dao.tables.records.DatabaseDocumentRecord;
import com.databasir.dao.tables.records.DatabaseTypeRecord;
import com.databasir.dao.tables.records.DocumentDescriptionRecord;
import com.databasir.dao.tables.records.DocumentDiscussionRecord;
import com.databasir.dao.tables.records.DocumentFullTextRecord;
import com.databasir.dao.tables.records.DocumentTemplatePropertyRecord;
import com.databasir.dao.tables.records.GroupRecord;
import com.databasir.dao.tables.records.LoginRecord;
import com.databasir.dao.tables.records.MockDataRuleRecord;
import com.databasir.dao.tables.records.OauthAppPropertyRecord;
import com.databasir.dao.tables.records.OauthAppRecord;
import com.databasir.dao.tables.records.OperationLogRecord;
import com.databasir.dao.tables.records.ProjectRecord;
import com.databasir.dao.tables.records.ProjectSyncRuleRecord;
import com.databasir.dao.tables.records.ProjectSyncTaskRecord;
import com.databasir.dao.tables.records.SysKeyRecord;
import com.databasir.dao.tables.records.SysMailRecord;
import com.databasir.dao.tables.records.TableColumnDocumentRecord;
import com.databasir.dao.tables.records.TableDocumentRecord;
import com.databasir.dao.tables.records.TableForeignKeyDocumentRecord;
import com.databasir.dao.tables.records.TableIndexDocumentRecord;
import com.databasir.dao.tables.records.TableTriggerDocumentRecord;
import com.databasir.dao.tables.records.UserFavoriteProjectRecord;
import com.databasir.dao.tables.records.UserRecord;
import com.databasir.dao.tables.records.UserRoleRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * databasir.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<DataSourceRecord> KEY_DATA_SOURCE_PRIMARY = Internal.createUniqueKey(DataSourceTable.DATA_SOURCE, DSL.name("KEY_data_source_PRIMARY"), new TableField[] { DataSourceTable.DATA_SOURCE.ID }, true);
    public static final UniqueKey<DataSourceRecord> KEY_DATA_SOURCE_UK_PROJECT_ID = Internal.createUniqueKey(DataSourceTable.DATA_SOURCE, DSL.name("KEY_data_source_uk_project_id"), new TableField[] { DataSourceTable.DATA_SOURCE.PROJECT_ID }, true);
    public static final UniqueKey<DataSourcePropertyRecord> KEY_DATA_SOURCE_PROPERTY_PRIMARY = Internal.createUniqueKey(DataSourcePropertyTable.DATA_SOURCE_PROPERTY, DSL.name("KEY_data_source_property_PRIMARY"), new TableField[] { DataSourcePropertyTable.DATA_SOURCE_PROPERTY.ID }, true);
    public static final UniqueKey<DatabaseDocumentRecord> KEY_DATABASE_DOCUMENT_PRIMARY = Internal.createUniqueKey(DatabaseDocumentTable.DATABASE_DOCUMENT, DSL.name("KEY_database_document_PRIMARY"), new TableField[] { DatabaseDocumentTable.DATABASE_DOCUMENT.ID }, true);
    public static final UniqueKey<DatabaseDocumentRecord> KEY_DATABASE_DOCUMENT_UK_PROJECT_ID_VERSION_IS_ARCHIVE = Internal.createUniqueKey(DatabaseDocumentTable.DATABASE_DOCUMENT, DSL.name("KEY_database_document_uk_project_id_version_is_archive"), new TableField[] { DatabaseDocumentTable.DATABASE_DOCUMENT.PROJECT_ID, DatabaseDocumentTable.DATABASE_DOCUMENT.VERSION, DatabaseDocumentTable.DATABASE_DOCUMENT.IS_ARCHIVE }, true);
    public static final UniqueKey<DatabaseTypeRecord> KEY_DATABASE_TYPE_PRIMARY = Internal.createUniqueKey(DatabaseTypeTable.DATABASE_TYPE, DSL.name("KEY_database_type_PRIMARY"), new TableField[] { DatabaseTypeTable.DATABASE_TYPE.ID }, true);
    public static final UniqueKey<DatabaseTypeRecord> KEY_DATABASE_TYPE_UK_DATABASE_TYPE_DELETED_DELETED_TOKEN = Internal.createUniqueKey(DatabaseTypeTable.DATABASE_TYPE, DSL.name("KEY_database_type_uk_database_type_deleted_deleted_token"), new TableField[] { DatabaseTypeTable.DATABASE_TYPE.DATABASE_TYPE_, DatabaseTypeTable.DATABASE_TYPE.DELETED, DatabaseTypeTable.DATABASE_TYPE.DELETED_TOKEN }, true);
    public static final UniqueKey<DocumentDescriptionRecord> KEY_DOCUMENT_DESCRIPTION_PRIMARY = Internal.createUniqueKey(DocumentDescriptionTable.DOCUMENT_DESCRIPTION, DSL.name("KEY_document_description_PRIMARY"), new TableField[] { DocumentDescriptionTable.DOCUMENT_DESCRIPTION.ID }, true);
    public static final UniqueKey<DocumentDescriptionRecord> KEY_DOCUMENT_DESCRIPTION_UK_PROJECT_ID_TABLE_NAME_COLUMN_NAME = Internal.createUniqueKey(DocumentDescriptionTable.DOCUMENT_DESCRIPTION, DSL.name("KEY_document_description_uk_project_id_table_name_column_name"), new TableField[] { DocumentDescriptionTable.DOCUMENT_DESCRIPTION.PROJECT_ID, DocumentDescriptionTable.DOCUMENT_DESCRIPTION.TABLE_NAME, DocumentDescriptionTable.DOCUMENT_DESCRIPTION.COLUMN_NAME }, true);
    public static final UniqueKey<DocumentDiscussionRecord> KEY_DOCUMENT_DISCUSSION_PRIMARY = Internal.createUniqueKey(DocumentDiscussionTable.DOCUMENT_DISCUSSION, DSL.name("KEY_document_discussion_PRIMARY"), new TableField[] { DocumentDiscussionTable.DOCUMENT_DISCUSSION.ID }, true);
    public static final UniqueKey<DocumentFullTextRecord> KEY_DOCUMENT_FULL_TEXT_PRIMARY = Internal.createUniqueKey(DocumentFullTextTable.DOCUMENT_FULL_TEXT, DSL.name("KEY_document_full_text_PRIMARY"), new TableField[] { DocumentFullTextTable.DOCUMENT_FULL_TEXT.ID }, true);
    public static final UniqueKey<DocumentTemplatePropertyRecord> KEY_DOCUMENT_TEMPLATE_PROPERTY_PRIMARY = Internal.createUniqueKey(DocumentTemplatePropertyTable.DOCUMENT_TEMPLATE_PROPERTY, DSL.name("KEY_document_template_property_PRIMARY"), new TableField[] { DocumentTemplatePropertyTable.DOCUMENT_TEMPLATE_PROPERTY.ID }, true);
    public static final UniqueKey<DocumentTemplatePropertyRecord> KEY_DOCUMENT_TEMPLATE_PROPERTY_UK_TYPE_KEY = Internal.createUniqueKey(DocumentTemplatePropertyTable.DOCUMENT_TEMPLATE_PROPERTY, DSL.name("KEY_document_template_property_uk_type_key"), new TableField[] { DocumentTemplatePropertyTable.DOCUMENT_TEMPLATE_PROPERTY.TYPE, DocumentTemplatePropertyTable.DOCUMENT_TEMPLATE_PROPERTY.KEY }, true);
    public static final UniqueKey<GroupRecord> KEY_GROUP_PRIMARY = Internal.createUniqueKey(GroupTable.GROUP, DSL.name("KEY_group_PRIMARY"), new TableField[] { GroupTable.GROUP.ID }, true);
    public static final UniqueKey<LoginRecord> KEY_LOGIN_PRIMARY = Internal.createUniqueKey(LoginTable.LOGIN, DSL.name("KEY_login_PRIMARY"), new TableField[] { LoginTable.LOGIN.ID }, true);
    public static final UniqueKey<LoginRecord> KEY_LOGIN_UK_USER_ID = Internal.createUniqueKey(LoginTable.LOGIN, DSL.name("KEY_login_uk_user_id"), new TableField[] { LoginTable.LOGIN.USER_ID }, true);
    public static final UniqueKey<MockDataRuleRecord> KEY_MOCK_DATA_RULE_PRIMARY = Internal.createUniqueKey(MockDataRuleTable.MOCK_DATA_RULE, DSL.name("KEY_mock_data_rule_PRIMARY"), new TableField[] { MockDataRuleTable.MOCK_DATA_RULE.ID }, true);
    public static final UniqueKey<MockDataRuleRecord> KEY_MOCK_DATA_RULE_UK_PROJECT_ID_TABLE_NAME_COLUMN_NAME = Internal.createUniqueKey(MockDataRuleTable.MOCK_DATA_RULE, DSL.name("KEY_mock_data_rule_uk_project_id_table_name_column_name"), new TableField[] { MockDataRuleTable.MOCK_DATA_RULE.PROJECT_ID, MockDataRuleTable.MOCK_DATA_RULE.TABLE_NAME, MockDataRuleTable.MOCK_DATA_RULE.COLUMN_NAME }, true);
    public static final UniqueKey<OauthAppRecord> KEY_OAUTH_APP_PRIMARY = Internal.createUniqueKey(OauthAppTable.OAUTH_APP, DSL.name("KEY_oauth_app_PRIMARY"), new TableField[] { OauthAppTable.OAUTH_APP.ID }, true);
    public static final UniqueKey<OauthAppRecord> KEY_OAUTH_APP_UK_REGISTRATION_ID = Internal.createUniqueKey(OauthAppTable.OAUTH_APP, DSL.name("KEY_oauth_app_uk_registration_id"), new TableField[] { OauthAppTable.OAUTH_APP.REGISTRATION_ID }, true);
    public static final UniqueKey<OauthAppPropertyRecord> KEY_OAUTH_APP_PROPERTY_PRIMARY = Internal.createUniqueKey(OauthAppPropertyTable.OAUTH_APP_PROPERTY, DSL.name("KEY_oauth_app_property_PRIMARY"), new TableField[] { OauthAppPropertyTable.OAUTH_APP_PROPERTY.ID }, true);
    public static final UniqueKey<OperationLogRecord> KEY_OPERATION_LOG_PRIMARY = Internal.createUniqueKey(OperationLogTable.OPERATION_LOG, DSL.name("KEY_operation_log_PRIMARY"), new TableField[] { OperationLogTable.OPERATION_LOG.ID }, true);
    public static final UniqueKey<ProjectRecord> KEY_PROJECT_PRIMARY = Internal.createUniqueKey(ProjectTable.PROJECT, DSL.name("KEY_project_PRIMARY"), new TableField[] { ProjectTable.PROJECT.ID }, true);
    public static final UniqueKey<ProjectRecord> KEY_PROJECT_UK_GROUP_ID_NAME_DELETED_TOKEN = Internal.createUniqueKey(ProjectTable.PROJECT, DSL.name("KEY_project_uk_group_id_name_deleted_token"), new TableField[] { ProjectTable.PROJECT.GROUP_ID, ProjectTable.PROJECT.NAME, ProjectTable.PROJECT.DELETED_TOKEN }, true);
    public static final UniqueKey<ProjectSyncRuleRecord> KEY_PROJECT_SYNC_RULE_PRIMARY = Internal.createUniqueKey(ProjectSyncRuleTable.PROJECT_SYNC_RULE, DSL.name("KEY_project_sync_rule_PRIMARY"), new TableField[] { ProjectSyncRuleTable.PROJECT_SYNC_RULE.ID }, true);
    public static final UniqueKey<ProjectSyncRuleRecord> KEY_PROJECT_SYNC_RULE_UK_PROJECT_ID = Internal.createUniqueKey(ProjectSyncRuleTable.PROJECT_SYNC_RULE, DSL.name("KEY_project_sync_rule_uk_project_id"), new TableField[] { ProjectSyncRuleTable.PROJECT_SYNC_RULE.PROJECT_ID }, true);
    public static final UniqueKey<ProjectSyncTaskRecord> KEY_PROJECT_SYNC_TASK_PRIMARY = Internal.createUniqueKey(ProjectSyncTaskTable.PROJECT_SYNC_TASK, DSL.name("KEY_project_sync_task_PRIMARY"), new TableField[] { ProjectSyncTaskTable.PROJECT_SYNC_TASK.ID }, true);
    public static final UniqueKey<SysKeyRecord> KEY_SYS_KEY_PRIMARY = Internal.createUniqueKey(SysKeyTable.SYS_KEY, DSL.name("KEY_sys_key_PRIMARY"), new TableField[] { SysKeyTable.SYS_KEY.ID }, true);
    public static final UniqueKey<SysMailRecord> KEY_SYS_MAIL_PRIMARY = Internal.createUniqueKey(SysMailTable.SYS_MAIL, DSL.name("KEY_sys_mail_PRIMARY"), new TableField[] { SysMailTable.SYS_MAIL.ID }, true);
    public static final UniqueKey<TableColumnDocumentRecord> KEY_TABLE_COLUMN_DOCUMENT_PRIMARY = Internal.createUniqueKey(TableColumnDocumentTable.TABLE_COLUMN_DOCUMENT, DSL.name("KEY_table_column_document_PRIMARY"), new TableField[] { TableColumnDocumentTable.TABLE_COLUMN_DOCUMENT.ID }, true);
    public static final UniqueKey<TableDocumentRecord> KEY_TABLE_DOCUMENT_PRIMARY = Internal.createUniqueKey(TableDocumentTable.TABLE_DOCUMENT, DSL.name("KEY_table_document_PRIMARY"), new TableField[] { TableDocumentTable.TABLE_DOCUMENT.ID }, true);
    public static final UniqueKey<TableForeignKeyDocumentRecord> KEY_TABLE_FOREIGN_KEY_DOCUMENT_PRIMARY = Internal.createUniqueKey(TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT, DSL.name("KEY_table_foreign_key_document_PRIMARY"), new TableField[] { TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.ID }, true);
    public static final UniqueKey<TableIndexDocumentRecord> KEY_TABLE_INDEX_DOCUMENT_PRIMARY = Internal.createUniqueKey(TableIndexDocumentTable.TABLE_INDEX_DOCUMENT, DSL.name("KEY_table_index_document_PRIMARY"), new TableField[] { TableIndexDocumentTable.TABLE_INDEX_DOCUMENT.ID }, true);
    public static final UniqueKey<TableTriggerDocumentRecord> KEY_TABLE_TRIGGER_DOCUMENT_PRIMARY = Internal.createUniqueKey(TableTriggerDocumentTable.TABLE_TRIGGER_DOCUMENT, DSL.name("KEY_table_trigger_document_PRIMARY"), new TableField[] { TableTriggerDocumentTable.TABLE_TRIGGER_DOCUMENT.ID }, true);
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = Internal.createUniqueKey(UserTable.USER, DSL.name("KEY_user_PRIMARY"), new TableField[] { UserTable.USER.ID }, true);
    public static final UniqueKey<UserRecord> KEY_USER_UK_EMAIL = Internal.createUniqueKey(UserTable.USER, DSL.name("KEY_user_uk_email"), new TableField[] { UserTable.USER.EMAIL, UserTable.USER.DELETED_TOKEN }, true);
    public static final UniqueKey<UserRecord> KEY_USER_UK_USERNAME = Internal.createUniqueKey(UserTable.USER, DSL.name("KEY_user_uk_username"), new TableField[] { UserTable.USER.USERNAME, UserTable.USER.DELETED_TOKEN }, true);
    public static final UniqueKey<UserFavoriteProjectRecord> KEY_USER_FAVORITE_PROJECT_PRIMARY = Internal.createUniqueKey(UserFavoriteProjectTable.USER_FAVORITE_PROJECT, DSL.name("KEY_user_favorite_project_PRIMARY"), new TableField[] { UserFavoriteProjectTable.USER_FAVORITE_PROJECT.ID }, true);
    public static final UniqueKey<UserFavoriteProjectRecord> KEY_USER_FAVORITE_PROJECT_UK_USER_ID_PROJECT_ID = Internal.createUniqueKey(UserFavoriteProjectTable.USER_FAVORITE_PROJECT, DSL.name("KEY_user_favorite_project_uk_user_id_project_id"), new TableField[] { UserFavoriteProjectTable.USER_FAVORITE_PROJECT.USER_ID, UserFavoriteProjectTable.USER_FAVORITE_PROJECT.PROJECT_ID }, true);
    public static final UniqueKey<UserRoleRecord> KEY_USER_ROLE_PRIMARY = Internal.createUniqueKey(UserRoleTable.USER_ROLE, DSL.name("KEY_user_role_PRIMARY"), new TableField[] { UserRoleTable.USER_ROLE.ID }, true);
    public static final UniqueKey<UserRoleRecord> KEY_USER_ROLE_UK_USER_ID_GROUP_ID_ROLE = Internal.createUniqueKey(UserRoleTable.USER_ROLE, DSL.name("KEY_user_role_uk_user_id_group_id_role"), new TableField[] { UserRoleTable.USER_ROLE.USER_ID, UserRoleTable.USER_ROLE.GROUP_ID, UserRoleTable.USER_ROLE.ROLE }, true);
}
