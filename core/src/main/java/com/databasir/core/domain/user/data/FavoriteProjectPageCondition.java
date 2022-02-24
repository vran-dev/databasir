package com.databasir.core.domain.user.data;

import com.databasir.dao.Tables;
import lombok.Data;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

@Data
public class FavoriteProjectPageCondition {

    private String projectNameContains;

    public Condition toCondition(Integer userId) {
        List<Condition> conditions = new ArrayList<>();
        conditions.add(Tables.USER_FAVORITE_PROJECT.USER_ID.eq(userId));
        if (projectNameContains != null && !projectNameContains.trim().equals("")) {
            conditions.add(Tables.PROJECT.NAME.contains(projectNameContains));
        }
        return conditions.stream()
                .reduce(Condition::and)
                .orElse(DSL.trueCondition());
    }
}
