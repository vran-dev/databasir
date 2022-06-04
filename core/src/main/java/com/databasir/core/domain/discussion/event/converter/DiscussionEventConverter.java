package com.databasir.core.domain.discussion.event.converter;

import com.databasir.core.domain.discussion.event.DiscussionCreated;
import com.databasir.dao.tables.pojos.DocumentDiscussion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscussionEventConverter {

    @Mapping(target = "createByUserId", source = "pojo.userId")
    DiscussionCreated of(DocumentDiscussion pojo, Integer discussionId);

}
