package com.databasir.core.meta.provider.postgresql;

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
public class PostgresqlTriggerMetaProvider implements TriggerMetaProvider {
    @Override
    public List<TriggerMeta> selectTriggers(Connection connection, TableCondition condition) {
        String sql = "SELECT trigger_name         AS name,\n"
                + "       action_timing        AS timing,\n"
                + "       event_manipulation   AS manipulation,\n"
                + "       action_statement     AS statement,\n"
                + "       created              AS trigger_create_at,\n"
                + "       trigger_catalog      AS trigger_catalog,\n"
                + "       TRIGGER_SCHEMA       AS trigger_schema,\n"
                + "       event_object_catalog AS target_catalog,\n"
                + "       event_object_schema  AS target_schema,\n"
                + "       event_object_table   AS target_table_name\n"
                + "FROM information_schema.triggers "
                + "WHERE trigger_catalog = ? AND trigger_schema = ? AND event_object_table = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, condition.getDatabaseName());
            preparedStatement.setObject(2, condition.getSchemaName());
            preparedStatement.setObject(3, condition.getTableName());
            ResultSet results = preparedStatement.executeQuery();
            List<TriggerMeta> triggers = new ArrayList<>();
            while (results.next()) {
                String name = Objects.requireNonNullElse(results.getString("name"), "");
                String statement = results.getString("statement");
                String timing = results.getString("timing");
                String manipulation = results.getString("manipulation");
                // postgresql default is null
                String created = results.getString("trigger_create_at");
                if (created == null) {
                    created = "unknown";
                }
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
            log.warn("postgresql get trigger meta failed", e);
            return Collections.emptyList();
        }
    }
}
