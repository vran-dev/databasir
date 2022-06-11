package com.databasir.core.domain.discussion.data;

import com.databasir.dao.Tables;
import lombok.Data;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class DiscussionListCondition {

    @NotBlank
    private String tableName;

    public Condition toCondition(Integer projectId) {
        List<Condition> conditions = new ArrayList<>();
        Condition condition = Tables.DOCUMENT_DISCUSSION.TABLE_NAME.eq(tableName);
        conditions.add(condition);
        conditions.add(Tables.DOCUMENT_DISCUSSION.PROJECT_ID.eq(projectId));
        return conditions.stream().reduce(Condition::and).orElse(DSL.trueCondition());
    }
}
