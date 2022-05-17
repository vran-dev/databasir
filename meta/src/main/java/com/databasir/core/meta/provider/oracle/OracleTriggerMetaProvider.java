package com.databasir.core.meta.provider.oracle;

import com.databasir.core.meta.data.TriggerMeta;
import com.databasir.core.meta.provider.TriggerMetaProvider;
import com.databasir.core.meta.provider.condition.TableCondition;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class OracleTriggerMetaProvider implements TriggerMetaProvider {
    @Override
    public List<TriggerMeta> selectTriggers(Connection connection, TableCondition condition) {
        String sql = "SELECT trig.table_owner AS schema_name,\n"
                + "       trig.table_name,\n"
                + "       trig.owner AS trigger_schema_name,\n"
                + "       trig.trigger_name,\n"
                + "       trig.trigger_type,\n"
                + "       trig.triggering_event,\n"
                + "       trig.status,\n"
                + "       trig.trigger_body AS script\n"
                + "FROM sys.all_triggers trig\n"
                + "         INNER JOIN sys.all_tables tab ON trig.table_owner = tab.owner\n"
                + "    AND trig.table_name = tab.table_name\n"
                + "WHERE trig.base_object_type = 'TABLE' AND trig.owner = ? AND trig.TABLE_NAME = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, condition.getSchemaName());
            preparedStatement.setObject(2, condition.getTableName());
            ResultSet results = preparedStatement.executeQuery();
            List<TriggerMeta> triggers = new ArrayList<>();
            while (results.next()) {
                String status = results.getString("status");
                String name = Objects.requireNonNullElse(results.getString("trigger_name"), "")
                        + " ("
                        + status
                        + ")";
                String statement = results.getString("script");
                String timing = results.getString("trigger_type");
                String manipulation = results.getString("triggering_event");
                String created = "unknown";
                TriggerMeta meta = TriggerMeta.builder()
                        .name(name)
                        .manipulation(manipulation)
                        .timing(timing)
                        .statement(statement)
                        .createAt(created)
                        .build();
                triggers.add(meta);
            }
            return triggers;
        } catch (SQLException e) {
            log.warn("get trigger meta failed", e);
            return Collections.emptyList();
        }
    }
}
