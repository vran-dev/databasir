package com.databasir.core.domain.database.converter;

import com.databasir.core.domain.database.data.DatabaseTypeCreateRequest;
import com.databasir.core.domain.database.data.DatabaseTypeDetailResponse;
import com.databasir.core.domain.database.data.DatabaseTypePageResponse;
import com.databasir.core.domain.database.data.DatabaseTypeUpdateRequest;
import com.databasir.dao.tables.pojos.DatabaseTypePojo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DatabaseTypePojoConverter {

    DatabaseTypePojo of(DatabaseTypeCreateRequest request);

    DatabaseTypePojo of(DatabaseTypeUpdateRequest request);

    DatabaseTypeDetailResponse toDetailResponse(DatabaseTypePojo data);

    DatabaseTypePageResponse toPageResponse(DatabaseTypePojo pojo, Integer projectCount);
}
