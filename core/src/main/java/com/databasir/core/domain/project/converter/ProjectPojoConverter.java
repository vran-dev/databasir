package com.databasir.core.domain.project.converter;

import com.databasir.core.domain.project.data.ProjectCreateRequest;
import com.databasir.core.domain.project.data.ProjectDetailResponse;
import com.databasir.core.domain.project.data.ProjectSimpleResponse;
import com.databasir.core.domain.project.data.ProjectUpdateRequest;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.DataSourcePojo;
import com.databasir.dao.tables.pojos.DataSourcePropertyPojo;
import com.databasir.dao.tables.pojos.ProjectPojo;
import com.databasir.dao.tables.pojos.ProjectSyncRulePojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = JsonConverter.class)
public interface ProjectPojoConverter {

    ProjectPojo of(ProjectCreateRequest request);

    ProjectPojo of(ProjectUpdateRequest request);

    @Mapping(target = "ignoreTableNameRegexArray", source = "request.ignoreTableNameRegexes")
    @Mapping(target = "ignoreColumnNameRegexArray", source = "request.ignoreColumnNameRegexes")
    ProjectSyncRulePojo of(ProjectCreateRequest.ProjectSyncRuleCreateRequest request,
                           Integer projectId);

    @Mapping(target = "ignoreTableNameRegexArray", source = "request.ignoreTableNameRegexes")
    @Mapping(target = "ignoreColumnNameRegexArray", source = "request.ignoreColumnNameRegexes")
    ProjectSyncRulePojo of(ProjectUpdateRequest.ProjectSyncRuleUpdateRequest request,
                           Integer projectId);
}
