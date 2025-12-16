package com.databasir.core.domain.discussion.converter;

import com.databasir.core.domain.discussion.data.DiscussionResponse;
import com.databasir.core.domain.discussion.data.DiscussionResponse.DiscussByUser;
import com.databasir.dao.tables.pojos.DocumentDiscussion;
import com.databasir.dao.tables.pojos.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DiscussionResponseConverterImpl implements DiscussionResponseConverter {

    @Override
    public DiscussionResponse of(DocumentDiscussion discussion, User discussBy) {
        if ( discussion == null && discussBy == null ) {
            return null;
        }

        DiscussionResponse discussionResponse = new DiscussionResponse();

        if ( discussion != null ) {
            discussionResponse.setId( discussion.getId() );
            discussionResponse.setCreateAt( discussion.getCreateAt() );
            discussionResponse.setContent( discussion.getContent() );
            discussionResponse.setProjectId( discussion.getProjectId() );
        }
        if ( discussBy != null ) {
            discussionResponse.setDiscussBy( userToDiscussByUser( discussBy ) );
        }

        return discussionResponse;
    }

    protected DiscussByUser userToDiscussByUser(User user) {
        if ( user == null ) {
            return null;
        }

        DiscussByUser discussByUser = new DiscussByUser();

        discussByUser.setEmail( user.getEmail() );
        discussByUser.setNickname( user.getNickname() );

        return discussByUser;
    }
}
