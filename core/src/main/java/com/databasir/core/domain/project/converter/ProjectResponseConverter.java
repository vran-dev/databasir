package com.databasir.core.domain.project.converter;

import com.databasir.core.domain.project.data.ProjectDetailResponse;
import com.databasir.core.domain.project.data.ProjectSimpleResponse;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.DataSource;
import com.databasir.dao.tables.pojos.DataSourceProperty;
import com.databasir.dao.tables.pojos.Project;
import com.databasir.dao.tables.pojos.ProjectSyncRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = JsonConverter.class)
public interface ProjectResponseConverter {

    @Mapping(target = "id", source = "database.id")
    @Mapping(target = "createAt", source = "database.createAt")
    ProjectDetailResponse toResponse(Project database,
                                     ProjectDetailResponse.DataSourceResponse dataSource,
                                     ProjectDetailResponse.ProjectSyncRuleResponse projectSyncRule);

    ProjectDetailResponse.DataSourceResponse toResponse(DataSource dataSource,
                                                        List<DataSourceProperty> properties);

    @Mapping(target = "ignoreTableNameRegexes", source = "ignoreTableNameRegexArray")
    @Mapping(target = "ignoreColumnNameRegexes", source = "ignoreColumnNameRegexArray")
    ProjectDetailResponse.ProjectSyncRuleResponse toResponse(ProjectSyncRule rule);

    @Mapping(target = "id", source = "project.id")
    @Mapping(target = "createAt", source = "project.createAt")
    ProjectSimpleResponse toSimple(Project project,
                                   DataSource dataSource,
                                   ProjectSyncRule syncRule,
                                   Boolean isFavorite);
}
