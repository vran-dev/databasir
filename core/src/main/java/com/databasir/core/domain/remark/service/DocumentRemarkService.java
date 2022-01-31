package com.databasir.core.domain.remark.service;

import com.databasir.common.exception.Forbidden;
import com.databasir.core.domain.remark.converter.RemarkResponseConverter;
import com.databasir.core.domain.remark.data.RemarkCreateRequest;
import com.databasir.core.domain.remark.data.RemarkListCondition;
import com.databasir.core.domain.remark.data.RemarkResponse;
import com.databasir.dao.impl.DocumentRemarkDao;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.tables.pojos.DocumentRemarkPojo;
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
public class DocumentRemarkService {

    private final DocumentRemarkDao documentRemarkDao;

    private final ProjectDao projectDao;

    private final UserDao userDao;

    private final RemarkResponseConverter remarkResponseConverter;

    public void deleteById(Integer groupId,
                           Integer projectId,
                           Integer remarkId) {
        if (projectDao.exists(groupId, projectId)) {
            documentRemarkDao.deleteById(remarkId);
        } else {
            throw new Forbidden();
        }
    }

    public Page<RemarkResponse> list(Integer groupId,
                                     Integer projectId,
                                     Pageable pageable,
                                     RemarkListCondition condition) {
        if (projectDao.exists(groupId, projectId)) {
            Page<DocumentRemarkPojo> data = documentRemarkDao.selectByPage(pageable, condition.toCondition(projectId));
            Set<Integer> userIdList = data.getContent()
                    .stream()
                    .map(DocumentRemarkPojo::getUserId)
                    .collect(Collectors.toSet());
            Map<Integer, UserPojo> userMapById = userDao.selectUserIdIn(userIdList)
                    .stream()
                    .collect(Collectors.toMap(UserPojo::getId, Function.identity()));
            return data
                    .map(remarkPojo -> {
                        UserPojo userPojo = userMapById.get(remarkPojo.getUserId());
                        return remarkResponseConverter.of(remarkPojo, userPojo);
                    });
        } else {
            throw new Forbidden();
        }
    }

    public void create(Integer groupId, Integer projectId, Integer userId, RemarkCreateRequest request) {
        if (projectDao.exists(groupId, projectId)) {
            DocumentRemarkPojo pojo = new DocumentRemarkPojo();
            pojo.setUserId(userId);
            pojo.setProjectId(projectId);
            pojo.setRemark(request.getRemark());
            pojo.setTableName(request.getTableName());
            pojo.setColumnName(request.getColumnName());
            documentRemarkDao.insertAndReturnId(pojo);
        } else {
            throw new Forbidden();
        }
    }
}
