package com.databasir.core.meta.provider.sqlserver;

import com.databasir.core.meta.provider.AbstractSqlTriggerMetaProvider;
import com.databasir.core.meta.provider.condition.TableCondition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SqlServerTriggerMetaProvider extends AbstractSqlTriggerMetaProvider {
    @Override
    protected String sql(TableCondition condition) {
        String sql = "SELECT SCHEMA_NAME(tab.schema_id) + '.' + tab.name AS table_name,\n"
                + "       trig.name                                   AS TRIGGER_NAME,\n"
                + "       trig.create_date                            AS CREATED,\n"
                + "       CASE\n"
                + "           WHEN is_instead_of_trigger = 1 THEN 'Instead of'\n"
                + "           ELSE 'After' END                        AS ACTION_TIMING,\n"
                + "       (CASE\n"
                + "            WHEN OBJECTPROPERTY(trig.object_id, 'ExecIsUpdateTrigger') = 1\n"
                + "                THEN 'Update '\n"
                + "            ELSE '' END\n"
                + "           + CASE\n"
                + "                 WHEN OBJECTPROPERTY(trig.object_id, 'ExecIsDeleteTrigger') = 1\n"
                + "                     THEN 'Delete '\n"
                + "                 ELSE '' END\n"
                + "           + CASE\n"
                + "                 WHEN OBJECTPROPERTY(trig.object_id, 'ExecIsInsertTrigger') = 1\n"
                + "                     THEN 'Insert '\n"
                + "                 ELSE '' END\n"
                + "           )                                       AS EVENT_MANIPULATION,\n"
                + "       CASE\n"
                + "           WHEN trig.[type] = 'TA' THEN 'Assembly (CLR) trigger'\n"
                + "           WHEN trig.[type] = 'TR' THEN 'SQL trigger'\n"
                + "           ELSE '' END                             AS [TYPE],\n"
                + "       CASE\n"
                + "           WHEN is_disabled = 1 THEN 'Disabled'\n"
                + "           ELSE 'Active' END                       AS [status],\n"
                + "       OBJECT_DEFINITION(trig.object_id)           AS ACTION_STATEMENT\n"
                + "FROM sys.triggers trig\n"
                + "         INNER JOIN sys.objects tab\n"
                + "                    ON trig.parent_id = tab.object_id\n"
                + "WHERE SCHEMA_NAME(tab.schema_id) = '%s' AND tab.name = '%s'";
        return String.format(sql, condition.getSchemaName(), condition.getTableName());
    }

}
