package com.databasir.core.domain.log.data;

import com.databasir.dao.Tables;
import lombok.Data;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

@Data
public class OperationLogPageCondition {

    private String module;

    private String code;

    private Integer operatorUserId;

    private Integer involveProjectId;

    private Integer involveGroupId;

    private Integer involveUserId;

    private Boolean isSuccess;

    public Condition toCondition() {
        List<Condition> conditions = new ArrayList<>();
        if (module != null) {
            conditions.add(Tables.OPERATION_LOG.OPERATION_MODULE.eq(module));
        }
        if (code != null) {
            conditions.add(Tables.OPERATION_LOG.OPERATION_CODE.eq(module));
        }
        if (operatorUserId != null) {
            conditions.add(Tables.OPERATION_LOG.OPERATOR_USER_ID.eq(operatorUserId));
        }
        if (involveProjectId != null) {
            conditions.add(Tables.OPERATION_LOG.INVOLVED_PROJECT_ID.eq(involveProjectId));
        }
        if (involveGroupId != null) {
            conditions.add(Tables.OPERATION_LOG.INVOLVED_GROUP_ID.eq(involveGroupId));
        }
        if (involveUserId != null) {
            conditions.add(Tables.OPERATION_LOG.INVOLVED_USER_ID.eq(involveUserId));
        }
        if (isSuccess != null) {
            conditions.add(Tables.OPERATION_LOG.IS_SUCCESS.eq(isSuccess));
        }
        return conditions.stream()
                .reduce(Condition::and)
                .orElse(DSL.trueCondition());
    }
}
