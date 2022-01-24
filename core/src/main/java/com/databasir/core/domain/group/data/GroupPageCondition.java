package com.databasir.core.domain.group.data;

import com.databasir.dao.Tables;
import lombok.Data;
import org.jooq.Condition;
import org.jooq.impl.DSL;

@Data
public class GroupPageCondition {

    private String groupNameContains;

    public Condition toCondition() {
        if (groupNameContains != null) {
            return Tables.GROUP.NAME.contains(groupNameContains);
        }
        return DSL.trueCondition();
    }
}
