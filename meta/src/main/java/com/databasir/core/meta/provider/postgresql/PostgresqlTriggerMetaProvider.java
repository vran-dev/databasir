package com.databasir.core.meta.provider.postgresql;

import com.databasir.core.meta.provider.AbstractSqlTriggerMetaProvider;
import com.databasir.core.meta.provider.condition.TableCondition;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostgresqlTriggerMetaProvider extends AbstractSqlTriggerMetaProvider {
    @Override
    protected String sql(TableCondition condition) {
        String sql = "SELECT trigger_name         AS TRIGGER_NAME,\n"
                + "       action_timing        AS ACTION_TIMING,\n"
                + "       event_manipulation   AS EVENT_MANIPULATION,\n"
                + "       action_statement     AS ACTION_STATEMENT,\n"
                + "       created              AS CREATED,\n"
                + "       trigger_catalog      AS trigger_catalog,\n"
                + "       TRIGGER_SCHEMA       AS trigger_schema,\n"
                + "       event_object_catalog AS target_catalog,\n"
                + "       event_object_schema  AS target_schema,\n"
                + "       event_object_table   AS target_table_name\n"
                + "FROM information_schema.triggers "
                + "WHERE trigger_catalog = '%s' AND trigger_schema = '%s' AND event_object_table = '%s';";
        return String.format(sql, condition.getDatabaseName(), condition.getSchemaName(), condition.getTableName());
    }
}
