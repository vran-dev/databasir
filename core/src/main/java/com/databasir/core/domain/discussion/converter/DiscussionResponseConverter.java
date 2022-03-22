package com.databasir.core.domain.discussion.converter;

import com.databasir.core.domain.discussion.data.DiscussionResponse;
import com.databasir.dao.tables.pojos.DocumentDiscussionPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscussionResponseConverter {

    @Mapping(target = "id", source = "discussion.id")
    @Mapping(target = "createAt", source = "discussion.createAt")
    @Mapping(target = "content", source = "discussion.content")
    DiscussionResponse of(DocumentDiscussionPojo discussion,
                          UserPojo discussBy);
}
