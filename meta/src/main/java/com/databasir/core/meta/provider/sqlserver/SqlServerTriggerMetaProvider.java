package com.databasir.core.meta.provider.sqlserver;

import com.databasir.core.meta.data.TriggerMeta;
import com.databasir.core.meta.provider.TriggerMetaProvider;
import com.databasir.core.meta.provider.condition.TableCondition;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SqlServerTriggerMetaProvider implements TriggerMetaProvider {

    private static final Pattern DATE_TIME_PATTERN =
            Pattern.compile("(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})(.*)");

    @Override
    public List<TriggerMeta> selectTriggers(Connection connection, TableCondition condition) {
        String sql = "SELECT SCHEMA_NAME(tab.schema_id) + '.' + tab.name AS table_name,\n" +
                "       trig.name                                   AS trigger_name,\n" +
                "       trig.create_date                            AS create_date,\n" +
                "       CASE\n" +
                "           WHEN is_instead_of_trigger = 1 THEN 'Instead of'\n" +
                "           ELSE 'After' END                        AS timing,\n" +
                "       (CASE\n" +
                "            WHEN OBJECTPROPERTY(trig.object_id, 'ExecIsUpdateTrigger') = 1\n" +
                "                THEN 'Update '\n" +
                "            ELSE '' END\n" +
                "           + CASE\n" +
                "                 WHEN OBJECTPROPERTY(trig.object_id, 'ExecIsDeleteTrigger') = 1\n" +
                "                     THEN 'Delete '\n" +
                "                 ELSE '' END\n" +
                "           + CASE\n" +
                "                 WHEN OBJECTPROPERTY(trig.object_id, 'ExecIsInsertTrigger') = 1\n" +
                "                     THEN 'Insert '\n" +
                "                 ELSE '' END\n" +
                "           )                                       AS manipulation,\n" +
                "       CASE\n" +
                "           WHEN trig.[type] = 'TA' THEN 'Assembly (CLR) trigger'\n" +
                "           WHEN trig.[type] = 'TR' THEN 'SQL trigger'\n" +
                "           ELSE '' END                             AS [TYPE],\n" +
                "       CASE\n" +
                "           WHEN is_disabled = 1 THEN 'Disabled'\n" +
                "           ELSE 'Active' END                       AS [status],\n" +
                "       OBJECT_DEFINITION(trig.object_id)           AS STATEMENT\n" +
                "FROM sys.triggers trig\n" +
                "         INNER JOIN sys.objects tab\n" +
                "                    ON trig.parent_id = tab.object_id\n" +
                "WHERE SCHEMA_NAME(tab.schema_id) = ? AND tab.name = ?";
        PreparedStatement preparedStatement = null;
        List<TriggerMeta> triggerMetas = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, condition.getSchemaName());
            preparedStatement.setString(2, condition.getTableName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String triggerName = resultSet.getString("trigger_name");
                String timing = resultSet.getString("timing");
                String manipulation = resultSet.getString("manipulation");
                String statement = resultSet.getString("statement");
                Matcher matcher = DATE_TIME_PATTERN.matcher(resultSet.getString("create_date"));
                String createAt;
                if (matcher.matches()) {
                    createAt = matcher.group(1);
                } else {
                    createAt = "1970-01-01 00:00:00";
                }
                triggerMetas.add(TriggerMeta.builder()
                        .name(triggerName)
                        .timing(timing)
                        .manipulation(manipulation)
                        .statement(statement)
                        .createAt(createAt)
                        .build());
            }
        } catch (SQLException e) {
            log.warn("ignore trigger meta by error {}", e.getMessage());
            if (log.isDebugEnabled()) {
                log.debug("ignore trigger meta by error ", e);
            }
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
        return triggerMetas;
    }

}
