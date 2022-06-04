package com.databasir.core.domain.project.converter;

import com.databasir.core.domain.project.data.ProjectCreateRequest;
import com.databasir.core.domain.project.data.ProjectUpdateRequest;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.Project;
import com.databasir.dao.tables.pojos.ProjectSyncRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = JsonConverter.class)
public interface ProjectConverter {

    Project of(ProjectCreateRequest request);

    Project of(ProjectUpdateRequest request);

    @Mapping(target = "ignoreTableNameRegexArray", source = "request.ignoreTableNameRegexes")
    @Mapping(target = "ignoreColumnNameRegexArray", source = "request.ignoreColumnNameRegexes")
    ProjectSyncRule of(ProjectCreateRequest.ProjectSyncRuleCreateRequest request,
                           Integer projectId);

    @Mapping(target = "ignoreTableNameRegexArray", source = "request.ignoreTableNameRegexes")
    @Mapping(target = "ignoreColumnNameRegexArray", source = "request.ignoreColumnNameRegexes")
    ProjectSyncRule of(ProjectUpdateRequest.ProjectSyncRuleUpdateRequest request,
                           Integer projectId);

    default String optionToEmpty(Optional<String> optional) {
        return optional.orElse("");
    }
}
