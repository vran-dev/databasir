package com.databasir.core.meta.provider.oracle;

import com.databasir.core.meta.provider.AbstractSqlTriggerMetaProvider;
import com.databasir.core.meta.provider.condition.TableCondition;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
public class OracleTriggerMetaProvider extends AbstractSqlTriggerMetaProvider {
    @Override
    protected String sql(TableCondition condition) {
        String sql = "SELECT trig.table_owner AS schema_name,\n"
                + "       trig.table_name,\n"
                + "       trig.owner AS trigger_schema_name,\n"
                + "       trig.trigger_name as TRIGGER_NAME,\n"
                + "       trig.trigger_type AS ACTION_TIMING,\n"
                + "       trig.triggering_event as EVENT_MANIPULATION,\n"
                + "       trig.status,\n"
                + "       trig.trigger_body AS ACTION_STATEMENT \n"
                + "FROM sys.all_triggers trig\n"
                + "         INNER JOIN sys.all_tables tab ON trig.table_owner = tab.owner\n"
                + "    AND trig.table_name = tab.table_name\n"
                + "WHERE trig.base_object_type = 'TABLE' AND trig.owner = '%s' AND trig.TABLE_NAME = '%s'";
        return String.format(sql, condition.getSchemaName(), condition.getTableName());
    }

    @Override
    protected String getTriggerName(ResultSet results) throws SQLException {
        String status = results.getString("status");
        String name = Objects.requireNonNullElse(results.getString("trigger_name"), "")
                + " ("
                + status
                + ")";
        return name;
    }

    @Override
    protected String getCreateAt(ResultSet results) throws SQLException {
        return "unknown";
    }
}
