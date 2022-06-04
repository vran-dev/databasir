package com.databasir.core.domain.database.converter;

import com.databasir.core.domain.database.data.DatabaseTypeCreateRequest;
import com.databasir.core.domain.database.data.DatabaseTypeDetailResponse;
import com.databasir.core.domain.database.data.DatabaseTypePageResponse;
import com.databasir.core.domain.database.data.DatabaseTypeUpdateRequest;
import com.databasir.dao.tables.pojos.DatabaseType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DatabaseTypeConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "jdbcDriverFilePath", source = "jdbcDriverFilePath")
    DatabaseType of(DatabaseTypeCreateRequest request, String jdbcDriverFilePath);

    @Mapping(target = "jdbcDriverFilePath", source = "jdbcDriverFilePath")
    DatabaseType of(DatabaseTypeUpdateRequest request, String jdbcDriverFilePath);

    DatabaseType of(DatabaseTypeUpdateRequest request);

    DatabaseTypeDetailResponse toDetailResponse(DatabaseType data);

    DatabaseTypePageResponse toPageResponse(DatabaseType pojo, Integer projectCount);
}
