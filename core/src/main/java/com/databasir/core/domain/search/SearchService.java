package com.databasir.core.domain.search;

import com.databasir.core.domain.search.converter.SearchResponseConverter;
import com.databasir.core.domain.search.data.SearchResponse;
import com.databasir.dao.impl.GroupDao;
import com.databasir.dao.impl.ProjectDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProjectDao projectDao;

    private final GroupDao groupDao;

    private final SearchResponseConverter searchResponseConverter;

    public SearchResponse search(String query) {
        var groupPojoList = groupDao.selectByName(query);
        var groupResults = searchResponseConverter.toGroupResults(groupPojoList);
        var projectList = projectDao.selectByProjectNameOrDatabaseOrSchemaOrGroup(query);
        var projectResults = searchResponseConverter.toProjectResults(projectList);

        // build response
        SearchResponse response = new SearchResponse();
        response.setGroups(groupResults);
        response.setProjects(projectResults);
        // TODO support Table search
        return response;
    }
}
