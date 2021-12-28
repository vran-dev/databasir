package com.databasir.core.doc.factory.extension.mysql;

import com.databasir.core.doc.factory.DatabaseDocConfig;
import com.databasir.core.doc.factory.TableTriggerDocFactory;
import com.databasir.core.doc.model.TriggerDoc;
import lombok.extern.slf4j.Slf4j;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class MysqlTableTriggerDocFactory implements TableTriggerDocFactory {

    @Override
    public List<TriggerDoc> create(String tableName,
                                   DatabaseMetaData metaData,
                                   DatabaseDocConfig configuration) {
        String sql = "SELECT TRIGGER_CATALOG,\n" +
                "       TRIGGER_SCHEMA,\n" +
                "       TRIGGER_NAME,\n" +
                "       EVENT_MANIPULATION,\n" +
                "       EVENT_OBJECT_CATALOG,\n" +
                "       EVENT_OBJECT_SCHEMA,\n" +
                "       EVENT_OBJECT_TABLE,\n" +
                "       ACTION_ORDER,\n" +
                "       ACTION_CONDITION,\n" +
                "       ACTION_STATEMENT,\n" +
                "       ACTION_ORIENTATION,\n" +
                "       ACTION_TIMING,\n" +
                "       ACTION_REFERENCE_OLD_TABLE,\n" +
                "       ACTION_REFERENCE_NEW_TABLE,\n" +
                "       ACTION_REFERENCE_OLD_ROW,\n" +
                "       ACTION_REFERENCE_NEW_ROW,\n" +
                "       CREATED,\n" +
                "       SQL_MODE,\n" +
                "       DEFINER\n " +
                "FROM information_schema.TRIGGERS WHERE EVENT_OBJECT_SCHEMA = ? AND EVENT_OBJECT_TABLE = ?";
        try {
            PreparedStatement preparedStatement = configuration.getConnection()
                    .prepareStatement(sql);
            preparedStatement.setObject(1, configuration.getDatabaseName());
            preparedStatement.setObject(2, tableName);
            ResultSet results = preparedStatement.executeQuery();
            List<TriggerDoc> triggers = new ArrayList<>();
            while (results.next()) {
                String name = results.getString("TRIGGER_NAME");
                String statement = results.getString("ACTION_STATEMENT");
                String timing = results.getString("ACTION_TIMING");
                String manipulation = results.getString("EVENT_MANIPULATION");
                String created = results.getString("CREATED");
                TriggerDoc doc = TriggerDoc.builder()
                        .name(name)
                        .manipulation(manipulation)
                        .timing(timing)
                        .statement(statement)
                        .createAt(created)
                        .build();
                triggers.add(doc);
            }
            return triggers;
        } catch (SQLException e) {
            log.warn("create trigger doc failed", e);
            return Collections.emptyList();
        }
    }

}
