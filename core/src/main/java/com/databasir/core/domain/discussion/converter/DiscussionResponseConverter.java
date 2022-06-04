package com.databasir.core.domain.discussion.converter;

import com.databasir.core.domain.discussion.data.DiscussionResponse;
import com.databasir.dao.tables.pojos.DocumentDiscussion;
import com.databasir.dao.tables.pojos.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscussionResponseConverter {

    @Mapping(target = "id", source = "discussion.id")
    @Mapping(target = "createAt", source = "discussion.createAt")
    @Mapping(target = "content", source = "discussion.content")
    DiscussionResponse of(DocumentDiscussion discussion,
                          User discussBy);
}
