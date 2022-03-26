package com.databasir.core.domain.discussion.service;

import com.databasir.common.exception.Forbidden;
import com.databasir.core.BaseTest;
import com.databasir.core.domain.discussion.data.DiscussionCreateRequest;
import com.databasir.core.infrastructure.event.subscriber.DiscussionEventSubscriber;
import com.databasir.dao.Tables;
import com.databasir.dao.impl.DocumentDiscussionDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;

@Transactional
class DocumentDiscussionServiceTest extends BaseTest {

    @Autowired
    private DocumentDiscussionService documentDiscussionService;

    @Autowired
    private DocumentDiscussionDao documentDiscussionDao;

    @MockBean
    private DiscussionEventSubscriber discussionEventSubscriber;

    @Test
    @Sql("classpath:sql/domain/discussion/DeleteById.sql")
    void deleteById() {
        int groupId = -999;
        int projectId = -999;
        int discussionId = -999;
        documentDiscussionService.deleteById(groupId, projectId, discussionId);

        Assertions.assertFalse(documentDiscussionDao.existsById(discussionId));
    }

    @Test
    void deleteByIdWhenProjectNotExists() {
        int groupId = -1;
        int projectId = -2;
        Assertions.assertThrows(Forbidden.class,
                () -> documentDiscussionService.deleteById(groupId, projectId, -1));
    }

    @Test
    @Sql("classpath:sql/domain/discussion/Create.sql")
    void create() {
        doNothing().when(discussionEventSubscriber).onDiscussionCreated(any());

        DiscussionCreateRequest request = new DiscussionCreateRequest();
        request.setContent("test");
        request.setColumnName("test");
        request.setTableName("test");
        int groupId = -999;
        int projectId = -999;
        int userId = -999;
        documentDiscussionService.create(groupId, projectId, userId, request);

        var data = documentDiscussionDao.selectList(Tables.DOCUMENT_DISCUSSION.PROJECT_ID.eq(projectId));
        Assertions.assertEquals(1, data.size());
        verify(discussionEventSubscriber, times(1)).onDiscussionCreated(any());
    }

    @Test
    void createWhenProjectNotExists() {
        DiscussionCreateRequest request = new DiscussionCreateRequest();
        request.setContent("test");
        request.setColumnName("test");
        request.setTableName("test");
        int groupId = -1;
        int projectId = -1;
        int userId = -1;
        Assertions.assertThrows(Forbidden.class,
                () -> documentDiscussionService.create(groupId, projectId, userId, request));
    }
}