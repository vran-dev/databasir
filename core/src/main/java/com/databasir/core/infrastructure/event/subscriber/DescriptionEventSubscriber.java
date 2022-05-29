package com.databasir.core.infrastructure.event.subscriber;

import com.databasir.core.domain.description.event.DescriptionUpdated;
import com.databasir.dao.impl.DocumentFullTextDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DescriptionEventSubscriber {

    private final DocumentFullTextDao documentFullTextDao;

    @EventListener(classes = DescriptionUpdated.class)
    public void refreshDocumentFullText(DescriptionUpdated event) {
        if (event.getColumnName() != null) {
            // update column description
            int result = documentFullTextDao.updateColumnDescription(event.getGroupId(),
                    event.getProjectId(),
                    event.getTableName(),
                    event.getColumnName(),
                    event.getDescription());
            log.info("update column description full text success by event {}, effect rows {}", event, result);
        } else {
            // update table description
            int result = documentFullTextDao.updateTableDescription(event.getGroupId(),
                    event.getProjectId(),
                    event.getTableName(),
                    event.getDescription());
            log.info("update table description full text success by event {}, effect rows {}", event, result);
        }
    }
}
