package com.databasir.core.domain.database.validator;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.database.data.DatabaseTypeCreateRequest;
import com.databasir.core.domain.database.data.DatabaseTypeUpdateRequest;
import com.databasir.core.domain.database.data.DriverClassNameResolveRequest;
import com.databasir.dao.impl.DatabaseTypeDao;
import com.databasir.dao.tables.pojos.DatabaseTypePojo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DatabaseTypeUpdateValidator {

    private final DatabaseTypeDao databaseTypeDao;

    public void validRequestRequiredParams(DatabaseTypeCreateRequest request) {
        if (StringUtils.isAllBlank(request.getJdbcDriverFilePath(), request.getJdbcDriverFileUrl())) {
            throw DomainErrors.DRIVER_URL_AND_PATH_MUST_NOT_BE_ALL_BLANK.exception();
        }
    }

    public void validRequestRequiredParams(DatabaseTypeUpdateRequest request) {
        if (StringUtils.isAllBlank(request.getJdbcDriverFilePath(), request.getJdbcDriverFileUrl())) {
            throw DomainErrors.DRIVER_URL_AND_PATH_MUST_NOT_BE_ALL_BLANK.exception();
        }
    }

    public void validRequestRequiredParams(DriverClassNameResolveRequest request) {
        if (StringUtils.isAllBlank(request.getJdbcDriverFilePath(), request.getJdbcDriverFileUrl())) {
            throw DomainErrors.DRIVER_URL_AND_PATH_MUST_NOT_BE_ALL_BLANK.exception();
        }
    }

    public void validDatabaseTypeIfNecessary(DatabaseTypeUpdateRequest request, DatabaseTypePojo origin) {
        if (!Objects.equals(request.getDatabaseType(), origin.getDatabaseType())) {
            if (databaseTypeDao.existsByDatabaseType(request.getDatabaseType())) {
                throw DomainErrors.DATABASE_TYPE_NAME_DUPLICATE.exception();
            }
        }
    }

    public boolean shouldReloadDriver(DatabaseTypeUpdateRequest request, DatabaseTypePojo origin) {
        if (!Objects.equals(request.getDatabaseType(), origin.getDatabaseType())) {
            return true;
        }
        if (!Objects.equals(request.getJdbcDriverFileUrl(), origin.getJdbcDriverFileUrl())) {
            return true;
        }
        if (!Objects.equals(request.getJdbcDriverFilePath(), origin.getJdbcDriverFilePath())) {
            return true;
        }
        return false;
    }
}
