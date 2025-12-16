package com.databasir.core.domain.discussion.event.converter;

import com.databasir.core.domain.discussion.event.DiscussionCreated;
import com.databasir.dao.tables.pojos.DocumentDiscussion;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DiscussionEventConverterImpl implements DiscussionEventConverter {

    @Override
    public DiscussionCreated of(DocumentDiscussion pojo, Integer discussionId) {
        if ( pojo == null && discussionId == null ) {
            return null;
        }

        DiscussionCreated discussionCreated = new DiscussionCreated();

        if ( pojo != null ) {
            discussionCreated.setCreateByUserId( pojo.getUserId() );
            discussionCreated.setColumnName( pojo.getColumnName() );
            discussionCreated.setContent( pojo.getContent() );
            discussionCreated.setCreateAt( pojo.getCreateAt() );
            discussionCreated.setProjectId( pojo.getProjectId() );
            discussionCreated.setTableName( pojo.getTableName() );
        }
        if ( discussionId != null ) {
            discussionCreated.setDiscussionId( discussionId );
        }

        return discussionCreated;
    }
}
