package com.databasir.core.domain.remark.data;

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

    private String columnName;

    public Condition toCondition(Integer projectId) {
        List<Condition> conditions = new ArrayList<>();
        Condition condition = Tables.DOCUMENT_REMARK.TABLE_NAME.eq(tableName);
        conditions.add(condition);

        Condition columnCondition;
        if (columnName != null) {
            columnCondition = Tables.DOCUMENT_REMARK.COLUMN_NAME.eq(columnName);
        } else {
            columnCondition = Tables.DOCUMENT_REMARK.COLUMN_NAME.isNull();
        }
        conditions.add(columnCondition);
        conditions.add(Tables.DOCUMENT_REMARK.PROJECT_ID.eq(projectId));
        return conditions.stream().reduce(Condition::and).orElse(DSL.trueCondition());
    }
}
