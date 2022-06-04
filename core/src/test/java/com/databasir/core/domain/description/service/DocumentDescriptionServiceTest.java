package com.databasir.core.domain.description.service;

import com.databasir.core.BaseTest;
import com.databasir.core.domain.description.data.DocumentDescriptionSaveRequest;
import com.databasir.dao.impl.DocumentDescriptionDao;
import com.databasir.dao.tables.pojos.DocumentDescription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
class DocumentDescriptionServiceTest extends BaseTest {

    @Autowired
    private DocumentDescriptionService documentDescriptionService;

    @Autowired
    private DocumentDescriptionDao documentDescriptionDao;

    @Test
    void save() {
        DocumentDescriptionSaveRequest saveRequest = new DocumentDescriptionSaveRequest();
        saveRequest.setTableName("ut");
        saveRequest.setColumnName("ut");
        saveRequest.setContent("hello world");
        int groupId = -1;
        int projectId = -1;
        int userId = -1;
        documentDescriptionService.save(groupId, projectId, userId, saveRequest);

        boolean isExists = documentDescriptionDao.exists(projectId,
                saveRequest.getTableName(), saveRequest.getColumnName());
        Assertions.assertTrue(isExists);

        DocumentDescriptionSaveRequest updateRequest = new DocumentDescriptionSaveRequest();
        updateRequest.setTableName("ut");
        updateRequest.setColumnName("ut");
        updateRequest.setContent("update content");
        documentDescriptionService.save(groupId, projectId, userId, updateRequest);
        var tableData = documentDescriptionDao.selectTableDescriptionByProjectId(projectId);
        Assertions.assertEquals(0, tableData.size());
        List<DocumentDescription> descriptionData = documentDescriptionDao.selectByProjectId(projectId);
        Assertions.assertEquals(1, descriptionData.size());
    }
}