package com.databasir.core.domain.remark.converter;

import com.databasir.core.domain.remark.data.DiscussionResponse;
import com.databasir.dao.tables.pojos.DocumentRemarkPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscussionResponseConverter {

    @Mapping(target = "id", source = "remark.id")
    @Mapping(target = "createAt", source = "remark.createAt")
    @Mapping(target = "content", source = "remark.remark")
    DiscussionResponse of(DocumentRemarkPojo remark,
                          UserPojo discussBy);
}
