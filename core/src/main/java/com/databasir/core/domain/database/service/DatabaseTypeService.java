package com.databasir.core.domain.database.service;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.database.converter.DatabaseTypeConverter;
import com.databasir.core.domain.database.data.*;
import com.databasir.core.domain.database.validator.DatabaseTypeUpdateValidator;
import com.databasir.core.infrastructure.connection.DatabaseTypes;
import com.databasir.core.infrastructure.driver.DriverResources;
import com.databasir.core.infrastructure.driver.DriverResult;
import com.databasir.dao.impl.DatabaseTypeDao;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.tables.pojos.DatabaseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseTypeService {

    private final DriverResources driverResources;

    private final DatabaseTypeDao databaseTypeDao;

    private final ProjectDao projectDao;

    private final DatabaseTypeConverter databaseTypeConverter;

    private final DatabaseTypeUpdateValidator databaseTypeUpdateValidator;

    /**
     * 1. validate: filePath, fileUrl
     * 2. validate: databaseType
     * 3. load from remote or local
     * 4. validate driver class name
     * 5. copy to standard directory
     * 6. save to database
     */
    public Integer create(DatabaseTypeCreateRequest request) {
        databaseTypeUpdateValidator.validRequestRequiredParams(request);
        String databaseType = request.getDatabaseType();
        if (databaseTypeDao.existsByDatabaseType(databaseType)) {
            throw DomainErrors.DATABASE_TYPE_NAME_DUPLICATE.exception();
        }
        DriverResult result = loadAndValidate(request.getJdbcDriverFileUrl(),
                request.getJdbcDriverFilePath(), request.getJdbcDriverClassName());
        String targetPath = driverResources.copyToStandardDirectory(result.getDriverFile(), databaseType);
        DatabaseType pojo = databaseTypeConverter.of(request, targetPath);
        // TODO workaround
        pojo.setJdbcDriverFileUrl(StringUtils.defaultIfBlank(request.getJdbcDriverFileUrl(), ""));
        try {
            return databaseTypeDao.insertAndReturnId(pojo);
        } catch (DuplicateKeyException e) {
            throw DomainErrors.DATABASE_TYPE_NAME_DUPLICATE.exception();
        }
    }

    @Transactional
    public void update(DatabaseTypeUpdateRequest request) {
        databaseTypeUpdateValidator.validRequestRequiredParams(request);
        databaseTypeDao.selectOptionalById(request.getId()).ifPresent(data -> {
            databaseTypeUpdateValidator.validDatabaseTypeIfNecessary(request, data);
            String databaseType = request.getDatabaseType();
            DatabaseType pojo;
            if (databaseTypeUpdateValidator.shouldReloadDriver(request, data)) {
                // 名称修改，下载地址修改需要删除原有的 driver 并重新下载验证
                driverResources.deleteByDatabaseType(data.getDatabaseType());
                // download
                DriverResult result = loadAndValidate(request.getJdbcDriverFileUrl(),
                        request.getJdbcDriverFilePath(), request.getJdbcDriverClassName());
                String targetPath = driverResources.copyToStandardDirectory(result.getDriverFile(), databaseType);
                pojo = databaseTypeConverter.of(request, targetPath);
                pojo.setJdbcDriverFileUrl(StringUtils.defaultIfBlank(request.getJdbcDriverFileUrl(), ""));
            } else {
                pojo = databaseTypeConverter.of(request);
                pojo.setJdbcDriverFileUrl(StringUtils.defaultIfBlank(request.getJdbcDriverFileUrl(), ""));
            }

            try {
                databaseTypeDao.updateById(pojo);
            } catch (DuplicateKeyException e) {
                throw DomainErrors.DATABASE_TYPE_NAME_DUPLICATE.exception();
            }
        });
    }

    private DriverResult loadAndValidate(String remoteUrl, String localPath, String className) {
        DriverResult result;
        if (StringUtils.isNoneBlank(localPath)) {
            result = driverResources.loadFromLocal(localPath);
        } else {
            result = driverResources.tempLoadFromRemote(remoteUrl);
        }
        driverResources.validateDriverJar(result.getDriverFile(), className);
        return result;
    }

    public void deleteById(Integer id) {
        databaseTypeDao.selectOptionalById(id).ifPresent(data -> {
            if (DatabaseTypes.has(data.getDatabaseType())) {
                throw DomainErrors.MUST_NOT_MODIFY_SYSTEM_DEFAULT_DATABASE_TYPE.exception();
            }
            databaseTypeDao.deleteById(id);
            driverResources.deleteByDatabaseType(data.getDatabaseType());
        });
    }

    public Page<DatabaseTypePageResponse> findByPage(Pageable page,
                                                     DatabaseTypePageCondition condition) {
        Page<DatabaseType> pageData = databaseTypeDao.selectByPage(page, condition.toCondition());
        List<String> databaseTypes = pageData.map(DatabaseType::getDatabaseType).toList();
        Map<String, Integer> projectCountMapByDatabaseType = projectDao.countByDatabaseTypes(databaseTypes);
        return pageData
                .map(data -> {
                    Integer count = projectCountMapByDatabaseType.getOrDefault(data.getDatabaseType(), 0);
                    return databaseTypeConverter.toPageResponse(data, count);
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
                    response.setIcon(type.getIcon());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public Optional<DatabaseTypeDetailResponse> selectOne(Integer id) {
        return databaseTypeDao.selectOptionalById(id)
                .map(databaseTypeConverter::toDetailResponse);
    }

    public String resolveDriverClassName(DriverClassNameResolveRequest request) {
        databaseTypeUpdateValidator.validRequestRequiredParams(request);
        if (StringUtils.isNotBlank(request.getJdbcDriverFileUrl())) {
            return driverResources.resolveDriverClassNameFromRemote(request.getJdbcDriverFileUrl());
        } else {
            return driverResources.resolveDriverClassNameFromLocal(request.getJdbcDriverFilePath());
        }
    }

    public String uploadDriver(MultipartFile file) {
        String parent = "temp";
        String path = parent + "/" + System.currentTimeMillis() + "-" + file.getOriginalFilename();
        try {
            Files.createDirectories(Paths.get(parent));
            Path targetPath = Paths.get(path);
            Files.copy(file.getInputStream(), targetPath);
            return path;
        } catch (IOException e) {
            log.error("upload driver file error", e);
            throw DomainErrors.UPLOAD_DRIVER_FILE_ERROR.exception();
        }
    }
}
