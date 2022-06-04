package com.databasir.core.domain.search.converter;

import com.databasir.core.domain.search.data.SearchResponse;
import com.databasir.dao.tables.pojos.DocumentFullText;
import com.databasir.dao.tables.pojos.Group;
import com.databasir.dao.tables.pojos.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface SearchResponseConverter {

    default SearchResponse.Item toItem(DocumentFullText pojo,
                                       Map<Integer, Project> projectMapById,
                                       Map<Integer, Group> groupMapById) {
        Project project = projectMapById.get(pojo.getProjectId());
        Group group = groupMapById.get(pojo.getGroupId());
        return toItem(pojo, group.getName(), group.getDescription(), project.getName(), project.getDescription());
    }

    default SearchResponse.Item toItem(DocumentFullText pojo,
                                       Map<Integer, Group> groupMapById) {
        var group = groupMapById.get(pojo.getGroupId());
        return toItem(pojo,
                group.getName(),
                group.getDescription(),
                pojo.getProjectName(),
                pojo.getProjectDescription());
    }

    @Mapping(target = "groupName", source = "groupName")
    @Mapping(target = "groupDescription", source = "groupDescription")
    @Mapping(target = "projectName", source = "projectName")
    @Mapping(target = "projectDescription", source = "projectDescription")
    SearchResponse.Item toItem(DocumentFullText item,
                               String groupName,
                               String groupDescription,
                               String projectName,
                               String projectDescription);

    SearchResponse.Item toItem(DocumentFullText pojo);
}
