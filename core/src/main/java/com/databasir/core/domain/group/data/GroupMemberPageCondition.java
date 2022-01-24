package com.databasir.core.domain.group.data;

import lombok.Data;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

import static com.databasir.dao.Tables.USER;
import static com.databasir.dao.Tables.USER_ROLE;

@Data
public class GroupMemberPageCondition {

    private String role;

    private String nicknameOrUsernameOrEmailContains;

    public Condition toCondition() {
        List<Condition> conditions = new ArrayList<>();
        if (role != null) {
            conditions.add(USER_ROLE.ROLE.eq(role));
        }
        if (nicknameOrUsernameOrEmailContains != null) {
            conditions.add(USER.USERNAME.contains(nicknameOrUsernameOrEmailContains)
                    .or(USER.NICKNAME.contains(nicknameOrUsernameOrEmailContains))
                    .or(USER.EMAIL.contains(nicknameOrUsernameOrEmailContains)));
        }
        return conditions.stream().reduce(Condition::and).orElse(DSL.trueCondition());
    }
}
