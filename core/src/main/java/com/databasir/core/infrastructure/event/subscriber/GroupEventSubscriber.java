package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.core.domain.group.event.GroupCreated;
import com.databasir.core.domain.group.event.GroupDeleted;
import com.databasir.core.domain.group.event.GroupUpdated;
import com.databasir.dao.Tables;
import com.databasir.dao.impl.DocumentFullTextDao;
import com.databasir.dao.tables.pojos.DocumentFullTextPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Async("fullTextRefreshThreadPoolTaskExecutor")
public class GroupEventSubscriber {

    private final DocumentFullTextDao documentFullTextDao;

    @EventListener(classes = GroupUpdated.class)
    public void refreshFullTextWhenUpdated(GroupUpdated event) {
        if (!documentFullTextDao.exists(Tables.DOCUMENT_FULL_TEXT.GROUP_ID.eq(event.getGroupId())
                .and(Tables.DOCUMENT_FULL_TEXT.PROJECT_ID.isNull()))) {
            DocumentFullTextPojo pojo = new DocumentFullTextPojo();
            pojo.setGroupId(event.getGroupId());
            pojo.setGroupName(event.getGroupName());
            pojo.setGroupDescription(event.getGroupDescription());
            documentFullTextDao.insertAndReturnId(pojo);
            log.info("group not exists, save new full text by event({}) success", event);
        } else {
            int result = documentFullTextDao.updateGroupInfoByGroupId(event.getGroupName(),
                    event.getGroupDescription(),
                    event.getGroupId());
            log.info("update full text group({}) info success, effect rows {}", event.getGroupId(), result);
        }
    }

    @EventListener(classes = GroupDeleted.class)
    public void deleteFullTextWhenDeleted(GroupDeleted event) {
        int result = documentFullTextDao.deleteByGroupId(event.getGroupId());
        log.info("delete full text by group({}) success, effect rows {}", event.getGroupId(), result);
    }

    @EventListener(classes = GroupCreated.class)
    public void addFullTextWhenCreated(GroupCreated event) {
        if (!documentFullTextDao.exists(Tables.DOCUMENT_FULL_TEXT.GROUP_ID.eq(event.getGroupId())
                .and(Tables.DOCUMENT_FULL_TEXT.PROJECT_ID.isNull()))) {
            DocumentFullTextPojo pojo = new DocumentFullTextPojo();
            pojo.setGroupId(event.getGroupId());
            pojo.setGroupName(event.getGroupName());
            pojo.setGroupDescription(event.getGroupDescription());
            documentFullTextDao.insertAndReturnId(pojo);
            log.info("save full text by event({}) success", event);
        } else {
            log.warn("ignore event {} because document already exists", event);
        }
    }
}
