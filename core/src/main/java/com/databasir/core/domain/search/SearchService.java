package com.databasir.core.domain.search;

import com.databasir.core.domain.search.converter.SearchResponseConverter;
import com.databasir.core.domain.search.data.SearchResponse;
import com.databasir.dao.impl.DocumentFullTextDao;
import com.databasir.dao.impl.GroupDao;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.tables.pojos.DocumentFullTextPojo;
import com.databasir.dao.tables.pojos.GroupPojo;
import com.databasir.dao.tables.pojos.ProjectPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchResponseConverter searchResponseConverter;

    private final DocumentFullTextDao documentFullTextDao;

    private final GroupDao groupDao;

    private final ProjectDao projectDao;

    public SearchResponse search(Pageable pageable, String query) {
        Page<DocumentFullTextPojo> columnPageData = documentFullTextDao.selectColumnPage(pageable, query);
        Page<DocumentFullTextPojo> tablePageData = documentFullTextDao.selectTablePage(pageable, query);

        // table 和 column 的项目名、组名等信息需要从关联表取
        Set<Integer> projectIds = new HashSet<>();
        projectIds.addAll(columnPageData.getContent()
                .stream().map(o -> o.getProjectId()).collect(Collectors.toList()));
        projectIds.addAll(tablePageData.getContent()
                .stream().map(o -> o.getProjectId()).collect(Collectors.toList()));
        Map<Integer, ProjectPojo> projectMapById = projectDao.selectInIds(projectIds)
                .stream()
                .collect(Collectors.toMap(o -> o.getId(), o -> o));

        Page<DocumentFullTextPojo> projectPageData = documentFullTextDao.selectProjectPage(pageable, query);
        Set<Integer> groupIds = new HashSet<>();
        groupIds.addAll(columnPageData.getContent()
                .stream().map(o -> o.getGroupId()).collect(Collectors.toList()));
        groupIds.addAll(tablePageData.getContent()
                .stream().map(o -> o.getGroupId()).collect(Collectors.toList()));
        groupIds.addAll(projectPageData.getContent()
                .stream().map(o -> o.getGroupId()).collect(Collectors.toList()));
        Map<Integer, GroupPojo> groupMapById = groupDao.selectInIds(groupIds)
                .stream()
                .collect(Collectors.toMap(o -> o.getId(), o -> o));

        // convert
        var columns = columnPageData.map(item -> searchResponseConverter.toItem(item, projectMapById, groupMapById));
        var tables = tablePageData.map(item -> searchResponseConverter.toItem(item, projectMapById, groupMapById));
        var projects = projectPageData.map(item -> searchResponseConverter.toItem(item, groupMapById));
        var groups = documentFullTextDao.selectGroupPage(pageable, query)
                .map(searchResponseConverter::toItem);
        // build response
        SearchResponse response = new SearchResponse();
        response.setColumnPageData(columns);
        response.setProjectPageData(projects);
        response.setTablePageData(tables);
        response.setGroupPageData(groups);
        return response;
    }
}
