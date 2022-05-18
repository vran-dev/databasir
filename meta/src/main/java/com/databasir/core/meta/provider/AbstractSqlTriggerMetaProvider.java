package com.databasir.core.meta.provider;

import com.databasir.core.meta.data.TriggerMeta;
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
public abstract class AbstractSqlTriggerMetaProvider implements TriggerMetaProvider {

    @Override
    public List<TriggerMeta> selectTriggers(Connection connection, TableCondition condition) {
        String sql = sql(condition);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet results = preparedStatement.executeQuery();
            List<TriggerMeta> triggers = new ArrayList<>();
            while (results.next()) {
                String name = getTriggerName(results);
                String statement = getStatement(results);
                String timing = getTiming(results);
                String manipulation = getManipulation(results);
                String created = getCreateAt(results);
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
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    protected abstract String sql(TableCondition condition);

    protected String getTriggerName(ResultSet results) throws SQLException {
        return Objects.requireNonNullElse(results.getString("TRIGGER_NAME"), "");
    }

    protected String getStatement(ResultSet results) throws SQLException {
        return results.getString("ACTION_STATEMENT");
    }

    protected String getTiming(ResultSet results) throws SQLException {
        return results.getString("ACTION_TIMING");
    }

    protected String getManipulation(ResultSet results) throws SQLException {
        return results.getString("EVENT_MANIPULATION");
    }

    protected String getCreateAt(ResultSet results) throws SQLException {
        String created = results.getString("CREATED");
        if (created == null) {
            return "unknown";
        }
        return created;
    }
}
