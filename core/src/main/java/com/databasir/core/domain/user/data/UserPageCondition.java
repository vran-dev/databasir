package com.databasir.core.domain.user.data;

import lombok.Data;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

import static com.databasir.dao.Tables.USER;

@Data
public class UserPageCondition {

    private String nicknameContains;

    private String usernameContains;

    private String emailContains;

    private String nicknameOrUsernameOrEmailContains;

    private Boolean enabled;

    public Condition toCondition() {
        List<Condition> conditions = new ArrayList<>();
        if (nicknameContains != null) {
            Condition condition = USER.NICKNAME.contains(nicknameContains);
            conditions.add(condition);
        }
        if (usernameContains != null) {
            Condition condition = USER.USERNAME.contains(usernameContains);
            conditions.add(condition);
        }
        if (emailContains != null) {
            Condition condition = USER.EMAIL.contains(emailContains);
            conditions.add(condition);
        }
        if (enabled != null) {
            Condition condition = USER.ENABLED.eq(enabled);
            conditions.add(condition);
        }
        if (nicknameOrUsernameOrEmailContains != null) {
            Condition condition = USER.EMAIL.contains(nicknameOrUsernameOrEmailContains)
                    .or(USER.USERNAME.contains(nicknameOrUsernameOrEmailContains))
                    .or(USER.EMAIL.contains(nicknameOrUsernameOrEmailContains));
            conditions.add(condition);
        }
        return conditions.stream().reduce(Condition::and).orElse(DSL.trueCondition());
    }
}
