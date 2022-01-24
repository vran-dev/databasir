package com.databasir.core.domain.project.converter;

import com.databasir.core.domain.project.data.DataSourcePropertyValue;
import com.databasir.core.domain.project.data.ProjectCreateRequest;
import com.databasir.core.domain.project.data.ProjectUpdateRequest;
import com.databasir.dao.tables.pojos.DataSourcePojo;
import com.databasir.dao.tables.pojos.DataSourcePropertyPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DataSourcePojoConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "password", source = "password")
    DataSourcePojo of(ProjectCreateRequest.DataSourceCreateRequest request,
                      String password,
                      Integer projectId);

    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "password", source = "password")
    DataSourcePojo of(ProjectUpdateRequest.DataSourceUpdateRequest request,
                      String password,
                      Integer projectId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    DataSourcePropertyPojo of(DataSourcePropertyValue propertyValues, Integer dataSourceId);

    default List<DataSourcePropertyPojo> of(List<DataSourcePropertyValue> propertyValues,
                                            Integer dataSourceId) {
        return propertyValues.stream().map(value -> of(value, dataSourceId)).collect(Collectors.toList());
    }

}
