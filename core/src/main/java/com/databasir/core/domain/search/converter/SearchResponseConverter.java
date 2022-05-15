package com.databasir.core.domain.search.converter;

import com.databasir.core.domain.search.data.SearchResponse;
import com.databasir.dao.tables.pojos.GroupPojo;
import com.databasir.dao.value.ProjectQueryPojo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SearchResponseConverter {

    List<SearchResponse.GroupSearchResult> toGroupResults(List<GroupPojo> groups);

    List<SearchResponse.ProjectSearchResult> toProjectResults(List<ProjectQueryPojo> projects);
}
