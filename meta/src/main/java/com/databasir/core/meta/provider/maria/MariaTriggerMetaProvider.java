package com.databasir.core.meta.provider.maria;

import com.databasir.core.meta.provider.AbstractSqlTriggerMetaProvider;
import com.databasir.core.meta.provider.condition.TableCondition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MariaTriggerMetaProvider extends AbstractSqlTriggerMetaProvider {
    @Override
    protected String sql(TableCondition condition) {
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
                + "WHERE TRIGGER_SCHEMA = '%s' AND EVENT_OBJECT_TABLE = '%s'";
        return String.format(sql, condition.getDatabaseName(), condition.getTableName());
    }
}
