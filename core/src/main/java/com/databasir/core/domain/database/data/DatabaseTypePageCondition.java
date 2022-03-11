package com.databasir.core.domain.database.data;

import com.databasir.dao.Tables;
import lombok.Data;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

@Data
public class DatabaseTypePageCondition {

    private String databaseTypeContains;

    public Condition toCondition() {
        List<Condition> conditions = new ArrayList<>();
        conditions.add(Tables.DATABASE_TYPE.DELETED.eq(false));
        if (databaseTypeContains != null && !databaseTypeContains.trim().equals("")) {
            conditions.add(Tables.DATABASE_TYPE.DATABASE_TYPE_.containsIgnoreCase(databaseTypeContains));
        }
        return conditions.stream()
                .reduce(Condition::and)
                .orElse(DSL.trueCondition());
    }
}
