package com.databasir.core.meta.provider.mysql;

import com.databasir.core.meta.provider.AbstractSqlTriggerMetaProvider;
import com.databasir.core.meta.provider.condition.TableCondition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MysqlTableTriggerMetaProvider extends AbstractSqlTriggerMetaProvider {
    @Override
    protected String sql(TableCondition condition) {
        String sql = "SELECT TRIGGER_CATALOG,\n"
                + "       TRIGGER_SCHEMA,\n"
                + "       TRIGGER_NAME,\n"
                + "       EVENT_MANIPULATION,\n"
                + "       EVENT_OBJECT_CATALOG,\n"
                + "       EVENT_OBJECT_SCHEMA,\n"
                + "       EVENT_OBJECT_TABLE,\n"
                + "       ACTION_ORDER,\n"
                + "       ACTION_CONDITION,\n"
                + "       ACTION_STATEMENT,\n"
                + "       ACTION_ORIENTATION,\n"
                + "       ACTION_TIMING,\n"
                + "       ACTION_REFERENCE_OLD_TABLE,\n"
                + "       ACTION_REFERENCE_NEW_TABLE,\n"
                + "       ACTION_REFERENCE_OLD_ROW,\n"
                + "       ACTION_REFERENCE_NEW_ROW,\n"
                + "       CREATED,\n"
                + "       SQL_MODE,\n"
                + "       DEFINER\n "
                + "FROM information_schema.TRIGGERS WHERE EVENT_OBJECT_SCHEMA = '%s' AND EVENT_OBJECT_TABLE = '%s'";
        return String.format(sql, condition.getDatabaseName(), condition.getTableName());
    }

}
