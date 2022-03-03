package com.databasir.core.domain.app.data;

import com.databasir.dao.Tables;
import com.databasir.dao.enums.OAuthAppType;
import lombok.Data;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

@Data
public class OAuthAppPageCondition {

    private String appNameContains;

    private OAuthAppType appType;

    public Condition toCondition() {
        List<Condition> conditions = new ArrayList<>();
        if (appNameContains != null && !appNameContains.trim().equals("")) {
            conditions.add(Tables.OAUTH_APP.APP_NAME.contains(appNameContains));
        }
        if (appType != null) {
            conditions.add(Tables.OAUTH_APP.APP_TYPE.eq(appType));
        }
        return conditions.stream()
                .reduce(Condition::and)
                .orElse(DSL.trueCondition());
    }
}
