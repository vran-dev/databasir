package com.databasir.core.domain.database.service;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.database.converter.DatabaseTypePojoConverter;
import com.databasir.core.domain.database.data.*;
import com.databasir.core.infrastructure.connection.DatabaseTypes;
import com.databasir.core.infrastructure.driver.DriverResources;
import com.databasir.dao.impl.DatabaseTypeDao;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.tables.pojos.DatabaseTypePojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseTypeService {

    private final DriverResources driverResources;

    private final DatabaseTypeDao databaseTypeDao;

    private final ProjectDao projectDao;

    private final DatabaseTypePojoConverter databaseTypePojoConverter;

    public Integer create(DatabaseTypeCreateRequest request) {
        DatabaseTypePojo pojo = databaseTypePojoConverter.of(request);
        try {
            return databaseTypeDao.insertAndReturnId(pojo);
        } catch (DuplicateKeyException e) {
            throw DomainErrors.DATABASE_TYPE_NAME_DUPLICATE.exception();
        }
    }

    @Transactional
    public void update(DatabaseTypeUpdateRequest request) {
        databaseTypeDao.selectOptionalById(request.getId()).ifPresent(data -> {
            if (DatabaseTypes.has(data.getDatabaseType())) {
                throw DomainErrors.MUST_NOT_MODIFY_SYSTEM_DEFAULT_DATABASE_TYPE.exception();
            }

            DatabaseTypePojo pojo = databaseTypePojoConverter.of(request);
            try {
                databaseTypeDao.updateById(pojo);
            } catch (DuplicateKeyException e) {
                throw DomainErrors.DATABASE_TYPE_NAME_DUPLICATE.exception();
            }

            // 名称修改，下载地址修改需要删除原有的 driver
            if (!Objects.equals(request.getDatabaseType(), data.getDatabaseType())
                    || !Objects.equals(request.getJdbcDriverFileUrl(), data.getJdbcDriverFileUrl())) {
                driverResources.delete(data.getDatabaseType());
            }
        });

    }

    public void deleteById(Integer id) {
        databaseTypeDao.selectOptionalById(id).ifPresent(data -> {
            if (DatabaseTypes.has(data.getDatabaseType())) {
                throw DomainErrors.MUST_NOT_MODIFY_SYSTEM_DEFAULT_DATABASE_TYPE.exception();
            }
            databaseTypeDao.deleteById(id);
            driverResources.delete(data.getDatabaseType());
        });
    }

    public Page<DatabaseTypePageResponse> findByPage(Pageable page,
                                                     DatabaseTypePageCondition condition) {
        Page<DatabaseTypePojo> pageData = databaseTypeDao.selectByPage(page, condition.toCondition());
        List<String> databaseTypes = pageData.map(DatabaseTypePojo::getDatabaseType).toList();
        Map<String, Integer> projectCountMapByDatabaseType = projectDao.countByDatabaseTypes(databaseTypes);
        return pageData
                .map(data -> {
                    Integer count = projectCountMapByDatabaseType.getOrDefault(data.getDatabaseType(), 0);
                    return databaseTypePojoConverter.toPageResponse(data, count);
                });
    }

    public List<DatabaseTypeSimpleResponse> listSimpleDatabaseTypes() {
        return databaseTypeDao.selectAll()
                .stream()
                .map(type -> {
                    DatabaseTypeSimpleResponse response = new DatabaseTypeSimpleResponse();
                    response.setDatabaseType(type.getDatabaseType());
                    response.setUrlPattern(type.getUrlPattern());
                    response.setDescription(type.getDescription());
                    response.setJdbcProtocol(type.getJdbcProtocol());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public Optional<DatabaseTypeDetailResponse> selectOne(Integer id) {
        return databaseTypeDao.selectOptionalById(id)
                .map(databaseTypePojoConverter::toDetailResponse);
    }

    public String resolveDriverClassName(DriverClassNameResolveRequest request) {
        return driverResources.resolveSqlDriverNameFromJar(request.getJdbcDriverFileUrl());
    }

}
