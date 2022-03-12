package com.databasir.core.domain.project.data;

import com.databasir.dao.Tables;
import lombok.Data;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectListCondition {

    private String nameContains;

    private String databaseNameContains;

    private String schemaNameContains;

    private String databaseType;

    private Integer groupId;

    public Condition toCondition() {
        List<Condition> conditions = new ArrayList<>();
        if (nameContains != null) {
            Condition condition = Tables.PROJECT.NAME.contains(nameContains);
            conditions.add(condition);
        }
        if (databaseNameContains != null) {
            Condition condition = Tables.DATA_SOURCE.DATABASE_NAME.contains(databaseNameContains);
            conditions.add(condition);
        }
        if (schemaNameContains != null) {
            Condition condition = Tables.DATA_SOURCE.SCHEMA_NAME.contains(schemaNameContains);
            conditions.add(condition);
        }
        if (databaseType != null) {
            Condition condition = Tables.DATA_SOURCE.DATABASE_TYPE.eq(databaseType);
            conditions.add(condition);
        }
        if (groupId != null) {
            Condition condition = Tables.PROJECT.GROUP_ID.eq(groupId);
            conditions.add(condition);
        }
        conditions.add(Tables.PROJECT.DELETED.eq(false));
        return conditions.stream().reduce(Condition::and).orElse(DSL.trueCondition());
    }
}
