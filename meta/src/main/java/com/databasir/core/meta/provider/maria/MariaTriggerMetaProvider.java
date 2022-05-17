package com.databasir.core.meta.provider.maria;

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
public class MariaTriggerMetaProvider implements TriggerMetaProvider {
    @Override
    public List<TriggerMeta> selectTriggers(Connection connection, TableCondition condition) {
        String sql = "SELECT \n"
                + "    TRIGGER_NAME,\n"
                + "    TRIGGER_SCHEMA,\n"
                + "    TRIGGER_CATALOG,\n"
                + "    EVENT_OBJECT_CATALOG,\n"
                + "    EVENT_OBJECT_SCHEMA,\n"
                + "    EVENT_OBJECT_TABLE,\n"
                + "    ACTION_STATEMENT,\n"
                + "    ACTION_TIMING,\n"
                + "    EVENT_MANIPULATION,\n"
                + "     CREATED\n"
                + "FROM information_schema.triggers "
                + "WHERE TRIGGER_SCHEMA = ? AND EVENT_OBJECT_TABLE = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // TODO : mariaDB's default database is 'def', the schema is equal to the user's input db name
            preparedStatement.setObject(1, condition.getDatabaseName());
            preparedStatement.setObject(2, condition.getTableName());
            ResultSet results = preparedStatement.executeQuery();
            List<TriggerMeta> triggers = new ArrayList<>();
            while (results.next()) {
                String name = Objects.requireNonNullElse(results.getString("TRIGGER_NAME"), "");
                String statement = results.getString("ACTION_STATEMENT");
                String timing = results.getString("ACTION_TIMING");
                String manipulation = results.getString("EVENT_MANIPULATION");
                String created = results.getString("CREATED");
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
            log.warn("get trigger meta failed", e);
            return Collections.emptyList();
        }
    }
}
