/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao;


import com.databasir.dao.tables.DataSource;
import com.databasir.dao.tables.DataSourceProperty;
import com.databasir.dao.tables.DatabaseDocument;
import com.databasir.dao.tables.DatabaseDocumentHistory;
import com.databasir.dao.tables.Group;
import com.databasir.dao.tables.Login;
import com.databasir.dao.tables.Project;
import com.databasir.dao.tables.ProjectSyncRule;
import com.databasir.dao.tables.SysKey;
import com.databasir.dao.tables.SysMail;
import com.databasir.dao.tables.TableColumnDocument;
import com.databasir.dao.tables.TableDocument;
import com.databasir.dao.tables.TableIndexDocument;
import com.databasir.dao.tables.TableTriggerDocument;
import com.databasir.dao.tables.User;
import com.databasir.dao.tables.UserRole;
import com.databasir.dao.tables.records.DataSourcePropertyRecord;
import com.databasir.dao.tables.records.DataSourceRecord;
import com.databasir.dao.tables.records.DatabaseDocumentHistoryRecord;
import com.databasir.dao.tables.records.DatabaseDocumentRecord;
import com.databasir.dao.tables.records.GroupRecord;
import com.databasir.dao.tables.records.LoginRecord;
import com.databasir.dao.tables.records.ProjectRecord;
import com.databasir.dao.tables.records.ProjectSyncRuleRecord;
import com.databasir.dao.tables.records.SysKeyRecord;
import com.databasir.dao.tables.records.SysMailRecord;
import com.databasir.dao.tables.records.TableColumnDocumentRecord;
import com.databasir.dao.tables.records.TableDocumentRecord;
import com.databasir.dao.tables.records.TableIndexDocumentRecord;
import com.databasir.dao.tables.records.TableTriggerDocumentRecord;
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

    public static final UniqueKey<DataSourceRecord> KEY_DATA_SOURCE_PRIMARY = Internal.createUniqueKey(DataSource.DATA_SOURCE, DSL.name("KEY_data_source_PRIMARY"), new TableField[] { DataSource.DATA_SOURCE.ID }, true);
    public static final UniqueKey<DataSourceRecord> KEY_DATA_SOURCE_UK_PROJECT_ID = Internal.createUniqueKey(DataSource.DATA_SOURCE, DSL.name("KEY_data_source_uk_project_id"), new TableField[] { DataSource.DATA_SOURCE.PROJECT_ID }, true);
    public static final UniqueKey<DataSourcePropertyRecord> KEY_DATA_SOURCE_PROPERTY_PRIMARY = Internal.createUniqueKey(DataSourceProperty.DATA_SOURCE_PROPERTY, DSL.name("KEY_data_source_property_PRIMARY"), new TableField[] { DataSourceProperty.DATA_SOURCE_PROPERTY.ID }, true);
    public static final UniqueKey<DatabaseDocumentRecord> KEY_DATABASE_DOCUMENT_PRIMARY = Internal.createUniqueKey(DatabaseDocument.DATABASE_DOCUMENT, DSL.name("KEY_database_document_PRIMARY"), new TableField[] { DatabaseDocument.DATABASE_DOCUMENT.ID }, true);
    public static final UniqueKey<DatabaseDocumentRecord> KEY_DATABASE_DOCUMENT_UK_PROJECT_ID = Internal.createUniqueKey(DatabaseDocument.DATABASE_DOCUMENT, DSL.name("KEY_database_document_uk_project_id"), new TableField[] { DatabaseDocument.DATABASE_DOCUMENT.PROJECT_ID }, true);
    public static final UniqueKey<DatabaseDocumentHistoryRecord> KEY_DATABASE_DOCUMENT_HISTORY_PRIMARY = Internal.createUniqueKey(DatabaseDocumentHistory.DATABASE_DOCUMENT_HISTORY, DSL.name("KEY_database_document_history_PRIMARY"), new TableField[] { DatabaseDocumentHistory.DATABASE_DOCUMENT_HISTORY.ID }, true);
    public static final UniqueKey<DatabaseDocumentHistoryRecord> KEY_DATABASE_DOCUMENT_HISTORY_UK_CONNECTION_ID_VERSION = Internal.createUniqueKey(DatabaseDocumentHistory.DATABASE_DOCUMENT_HISTORY, DSL.name("KEY_database_document_history_uk_connection_id_version"), new TableField[] { DatabaseDocumentHistory.DATABASE_DOCUMENT_HISTORY.DATABASE_DOCUMENT_ID, DatabaseDocumentHistory.DATABASE_DOCUMENT_HISTORY.VERSION }, true);
    public static final UniqueKey<GroupRecord> KEY_GROUP_PRIMARY = Internal.createUniqueKey(Group.GROUP, DSL.name("KEY_group_PRIMARY"), new TableField[] { Group.GROUP.ID }, true);
    public static final UniqueKey<GroupRecord> KEY_GROUP_UK_NAME = Internal.createUniqueKey(Group.GROUP, DSL.name("KEY_group_uk_name"), new TableField[] { Group.GROUP.NAME }, true);
    public static final UniqueKey<LoginRecord> KEY_LOGIN_PRIMARY = Internal.createUniqueKey(Login.LOGIN, DSL.name("KEY_login_PRIMARY"), new TableField[] { Login.LOGIN.ID }, true);
    public static final UniqueKey<LoginRecord> KEY_LOGIN_UK_USER_ID = Internal.createUniqueKey(Login.LOGIN, DSL.name("KEY_login_uk_user_id"), new TableField[] { Login.LOGIN.USER_ID }, true);
    public static final UniqueKey<ProjectRecord> KEY_PROJECT_PRIMARY = Internal.createUniqueKey(Project.PROJECT, DSL.name("KEY_project_PRIMARY"), new TableField[] { Project.PROJECT.ID }, true);
    public static final UniqueKey<ProjectRecord> KEY_PROJECT_UK_GROUP_ID_NAME = Internal.createUniqueKey(Project.PROJECT, DSL.name("KEY_project_uk_group_id_name"), new TableField[] { Project.PROJECT.GROUP_ID, Project.PROJECT.NAME }, true);
    public static final UniqueKey<ProjectSyncRuleRecord> KEY_PROJECT_SYNC_RULE_PRIMARY = Internal.createUniqueKey(ProjectSyncRule.PROJECT_SYNC_RULE, DSL.name("KEY_project_sync_rule_PRIMARY"), new TableField[] { ProjectSyncRule.PROJECT_SYNC_RULE.ID }, true);
    public static final UniqueKey<ProjectSyncRuleRecord> KEY_PROJECT_SYNC_RULE_UK_PROJECT_ID = Internal.createUniqueKey(ProjectSyncRule.PROJECT_SYNC_RULE, DSL.name("KEY_project_sync_rule_uk_project_id"), new TableField[] { ProjectSyncRule.PROJECT_SYNC_RULE.PROJECT_ID }, true);
    public static final UniqueKey<SysKeyRecord> KEY_SYS_KEY_PRIMARY = Internal.createUniqueKey(SysKey.SYS_KEY, DSL.name("KEY_sys_key_PRIMARY"), new TableField[] { SysKey.SYS_KEY.ID }, true);
    public static final UniqueKey<SysMailRecord> KEY_SYS_MAIL_PRIMARY = Internal.createUniqueKey(SysMail.SYS_MAIL, DSL.name("KEY_sys_mail_PRIMARY"), new TableField[] { SysMail.SYS_MAIL.ID }, true);
    public static final UniqueKey<TableColumnDocumentRecord> KEY_TABLE_COLUMN_DOCUMENT_PRIMARY = Internal.createUniqueKey(TableColumnDocument.TABLE_COLUMN_DOCUMENT, DSL.name("KEY_table_column_document_PRIMARY"), new TableField[] { TableColumnDocument.TABLE_COLUMN_DOCUMENT.ID }, true);
    public static final UniqueKey<TableDocumentRecord> KEY_TABLE_DOCUMENT_PRIMARY = Internal.createUniqueKey(TableDocument.TABLE_DOCUMENT, DSL.name("KEY_table_document_PRIMARY"), new TableField[] { TableDocument.TABLE_DOCUMENT.ID }, true);
    public static final UniqueKey<TableIndexDocumentRecord> KEY_TABLE_INDEX_DOCUMENT_PRIMARY = Internal.createUniqueKey(TableIndexDocument.TABLE_INDEX_DOCUMENT, DSL.name("KEY_table_index_document_PRIMARY"), new TableField[] { TableIndexDocument.TABLE_INDEX_DOCUMENT.ID }, true);
    public static final UniqueKey<TableTriggerDocumentRecord> KEY_TABLE_TRIGGER_DOCUMENT_PRIMARY = Internal.createUniqueKey(TableTriggerDocument.TABLE_TRIGGER_DOCUMENT, DSL.name("KEY_table_trigger_document_PRIMARY"), new TableField[] { TableTriggerDocument.TABLE_TRIGGER_DOCUMENT.ID }, true);
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = Internal.createUniqueKey(User.USER, DSL.name("KEY_user_PRIMARY"), new TableField[] { User.USER.ID }, true);
    public static final UniqueKey<UserRecord> KEY_USER_UK_EMAIL = Internal.createUniqueKey(User.USER, DSL.name("KEY_user_uk_email"), new TableField[] { User.USER.EMAIL }, true);
    public static final UniqueKey<UserRecord> KEY_USER_UK_USERNAME = Internal.createUniqueKey(User.USER, DSL.name("KEY_user_uk_username"), new TableField[] { User.USER.USERNAME }, true);
    public static final UniqueKey<UserRoleRecord> KEY_USER_ROLE_PRIMARY = Internal.createUniqueKey(UserRole.USER_ROLE, DSL.name("KEY_user_role_PRIMARY"), new TableField[] { UserRole.USER_ROLE.ID }, true);
    public static final UniqueKey<UserRoleRecord> KEY_USER_ROLE_UK_USER_ID_GROUP_ID_ROLE = Internal.createUniqueKey(UserRole.USER_ROLE, DSL.name("KEY_user_role_uk_user_id_group_id_role"), new TableField[] { UserRole.USER_ROLE.USER_ID, UserRole.USER_ROLE.GROUP_ID, UserRole.USER_ROLE.ROLE }, true);
}
