package com.databasir.core.domain.remark.converter;

import com.databasir.core.domain.remark.data.RemarkResponse;
import com.databasir.dao.tables.pojos.DocumentRemarkPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RemarkResponseConverter {

    @Mapping(target = "remarkBy.userId", source = "remarkBy.id")
    @Mapping(target = "remarkBy.nickname", source = "remarkBy.nickname")
    @Mapping(target = "remarkBy.email", source = "remarkBy.email")
    @Mapping(target = "id", source = "remark.id")
    @Mapping(target = "createAt", source = "remark.createAt")
    RemarkResponse of(DocumentRemarkPojo remark,
                      UserPojo remarkBy);
}
