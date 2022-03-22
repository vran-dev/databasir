package com.databasir.core.domain.discussion.event.converter;

import com.databasir.core.domain.discussion.event.DiscussionCreated;
import com.databasir.dao.tables.pojos.DocumentDiscussionPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscussionEventConverter {

    @Mapping(target = "createByUserId", source = "pojo.userId")
    DiscussionCreated of(DocumentDiscussionPojo pojo, Integer discussionId);

}
