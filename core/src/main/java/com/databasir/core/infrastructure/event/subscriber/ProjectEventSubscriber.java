package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.core.domain.project.event.ProjectDeleted;
import com.databasir.core.domain.project.event.ProjectSaved;
import com.databasir.dao.Tables;
import com.databasir.dao.impl.DocumentFullTextDao;
import com.databasir.dao.tables.pojos.DocumentFullTextPojo;
import com.databasir.dao.value.FullTextProjectInfoUpdatePojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Async("fullTextRefreshThreadPoolTaskExecutor")
public class ProjectEventSubscriber {

    private final DocumentFullTextDao documentFullTextDao;

    @EventListener(classes = ProjectSaved.class)
    @Transactional
    public void refreshFullTextWhenUpdated(ProjectSaved event) {
        if (!documentFullTextDao.exists(Tables.DOCUMENT_FULL_TEXT.PROJECT_ID.eq(event.getProjectId())
                .and(Tables.DOCUMENT_FULL_TEXT.TABLE_DOCUMENT_ID.isNull()))) {
            DocumentFullTextPojo pojo = new DocumentFullTextPojo();
            pojo.setGroupId(event.getGroupId());
            pojo.setProjectId(event.getProjectId());
            pojo.setProjectName(event.getProjectName());
            pojo.setProjectDescription(event.getProjectDescription());
            pojo.setDatabaseName(event.getDatabaseName());
            pojo.setSchemaName(event.getSchemaName());
            pojo.setDatabaseType(event.getDatabaseType());
            documentFullTextDao.insertAndReturnId(pojo);
            log.info("save full text by event ({}) success", event);
        } else {
            FullTextProjectInfoUpdatePojo updatePojo = FullTextProjectInfoUpdatePojo.builder()
                    .projectId(event.getProjectId())
                    .projectName(event.getProjectName())
                    .projectDescription(event.getProjectDescription())
                    .databaseType(event.getDatabaseType())
                    .databaseName(event.getDatabaseName())
                    .schemaName(event.getSchemaName())
                    .build();
            int result = documentFullTextDao.updateProjectInfoByProjectId(updatePojo);
            log.info("update full text project({}) info success, effect rows {}", event, result);
        }
    }

    @EventListener(classes = ProjectDeleted.class)
    public void deleteFullTextWhenDeleted(ProjectDeleted event) {
        int result = documentFullTextDao.deleteByProjectId(event.getProjectId());
        log.info("delete full text by project({}) success, effect rows {}", event.getProjectId(), result);
    }

}
