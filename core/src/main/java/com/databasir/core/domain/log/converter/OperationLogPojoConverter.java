package com.databasir.core.domain.log.converter;

import com.databasir.core.domain.log.data.OperationLogPageResponse;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.GroupPojo;
import com.databasir.dao.tables.pojos.OperationLogPojo;
import com.databasir.dao.tables.pojos.ProjectPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring", uses = JsonConverter.class)
public interface OperationLogPojoConverter {

    @Mapping(target = "id", source = "pojo.id")
    @Mapping(target = "createAt", source = "pojo.createAt")
    OperationLogPageResponse to(OperationLogPojo pojo,
                                GroupPojo involvedGroup,
                                UserPojo involvedUser,
                                ProjectPojo involvedProject);

    default OperationLogPageResponse to(OperationLogPojo operationLogPojo,
                                        Map<Integer, GroupPojo> groupMapById,
                                        Map<Integer, UserPojo> userMapById,
                                        Map<Integer, ProjectPojo> projectMapById) {
        GroupPojo group = null;
        if (operationLogPojo.getInvolvedGroupId() != null) {
            group = groupMapById.get(operationLogPojo.getInvolvedGroupId());
        }
        UserPojo user = null;
        if (operationLogPojo.getInvolvedUserId() != null) {
            user = userMapById.get(operationLogPojo.getInvolvedUserId());
        }
        ProjectPojo project = null;
        if (operationLogPojo.getInvolvedProjectId() != null) {
            project = projectMapById.get(operationLogPojo.getInvolvedProjectId());
        }
        return to(operationLogPojo, group, user, project);
    }
}
