package com.databasir.core.domain.log.converter;

import com.databasir.core.domain.log.data.OperationLogPageResponse;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.Group;
import com.databasir.dao.tables.pojos.OperationLog;
import com.databasir.dao.tables.pojos.Project;
import com.databasir.dao.tables.pojos.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring", uses = JsonConverter.class)
public interface OperationLogConverter {

    @Mapping(target = "id", source = "pojo.id")
    @Mapping(target = "createAt", source = "pojo.createAt")
    OperationLogPageResponse to(OperationLog pojo,
                                Group involvedGroup,
                                User involvedUser,
                                Project involvedProject);

    default OperationLogPageResponse to(OperationLog operationLog,
                                        Map<Integer, Group> groupMapById,
                                        Map<Integer, User> userMapById,
                                        Map<Integer, Project> projectMapById) {
        Group group = null;
        if (operationLog.getInvolvedGroupId() != null) {
            group = groupMapById.get(operationLog.getInvolvedGroupId());
        }
        User user = null;
        if (operationLog.getInvolvedUserId() != null) {
            user = userMapById.get(operationLog.getInvolvedUserId());
        }
        Project project = null;
        if (operationLog.getInvolvedProjectId() != null) {
            project = projectMapById.get(operationLog.getInvolvedProjectId());
        }
        return to(operationLog, group, user, project);
    }
}
