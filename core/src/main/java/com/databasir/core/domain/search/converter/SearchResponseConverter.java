package com.databasir.core.domain.search.converter;

import com.databasir.core.domain.search.data.SearchResponse;
import com.databasir.dao.tables.pojos.DocumentFullTextPojo;
import com.databasir.dao.tables.pojos.GroupPojo;
import com.databasir.dao.tables.pojos.ProjectPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface SearchResponseConverter {

    default SearchResponse.Item toItem(DocumentFullTextPojo pojo,
                                       Map<Integer, ProjectPojo> projectMapById,
                                       Map<Integer, GroupPojo> groupMapById) {
        ProjectPojo project = projectMapById.get(pojo.getProjectId());
        GroupPojo group = groupMapById.get(pojo.getGroupId());
        return toItem(pojo, group.getName(), group.getDescription(), project.getName(), project.getDescription());
    }

    default SearchResponse.Item toItem(DocumentFullTextPojo pojo,
                                       Map<Integer, GroupPojo> groupMapById) {
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
    SearchResponse.Item toItem(DocumentFullTextPojo item,
                               String groupName,
                               String groupDescription,
                               String projectName,
                               String projectDescription);

    SearchResponse.Item toItem(DocumentFullTextPojo pojo);
}
