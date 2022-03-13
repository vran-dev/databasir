package com.databasir.core.domain.remark.service;

import com.databasir.common.exception.Forbidden;
import com.databasir.core.domain.remark.converter.DiscussionResponseConverter;
import com.databasir.core.domain.remark.data.DiscussionCreateRequest;
import com.databasir.core.domain.remark.data.DiscussionListCondition;
import com.databasir.core.domain.remark.data.DiscussionResponse;
import com.databasir.dao.impl.DocumentDiscussionDao;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.tables.pojos.DocumentDiscussionPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentDiscussionService {

    private final DocumentDiscussionDao documentDiscussionDao;

    private final ProjectDao projectDao;

    private final UserDao userDao;

    private final DiscussionResponseConverter discussionResponseConverter;

    public void deleteById(Integer groupId,
                           Integer projectId,
                           Integer discussionId) {
        if (projectDao.exists(groupId, projectId)) {
            documentDiscussionDao.deleteById(discussionId);
        } else {
            throw new Forbidden();
        }
    }

    public Page<DiscussionResponse> list(Integer groupId,
                                         Integer projectId,
                                         Pageable pageable,
                                         DiscussionListCondition condition) {
        if (projectDao.exists(groupId, projectId)) {
            Page<DocumentDiscussionPojo> data =
                    documentDiscussionDao.selectByPage(pageable, condition.toCondition(projectId));
            Set<Integer> userIdList = data.getContent()
                    .stream()
                    .map(DocumentDiscussionPojo::getUserId)
                    .collect(Collectors.toSet());
            Map<Integer, UserPojo> userMapById = userDao.selectUserIdIn(userIdList)
                    .stream()
                    .collect(Collectors.toMap(UserPojo::getId, Function.identity()));
            return data
                    .map(dicussionPojo -> {
                        UserPojo userPojo = userMapById.get(dicussionPojo.getUserId());
                        return discussionResponseConverter.of(dicussionPojo, userPojo);
                    });
        } else {
            throw new Forbidden();
        }
    }

    public void create(Integer groupId, Integer projectId, Integer userId, DiscussionCreateRequest request) {
        if (projectDao.exists(groupId, projectId)) {
            DocumentDiscussionPojo pojo = new DocumentDiscussionPojo();
            pojo.setUserId(userId);
            pojo.setProjectId(projectId);
            pojo.setContent(request.getContent());
            pojo.setTableName(request.getTableName());
            pojo.setColumnName(request.getColumnName());
            documentDiscussionDao.insertAndReturnId(pojo);
        } else {
            throw new Forbidden();
        }
    }
}
