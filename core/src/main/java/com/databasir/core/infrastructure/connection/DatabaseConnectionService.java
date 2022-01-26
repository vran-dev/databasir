package com.databasir.core.infrastructure.connection;

import com.databasir.common.codec.Aes;
import com.databasir.core.domain.DomainErrors;
import com.databasir.dao.impl.SysKeyDao;
import com.databasir.dao.tables.pojos.DataSourcePojo;
import com.databasir.dao.tables.pojos.DataSourcePropertyPojo;
import com.databasir.dao.tables.pojos.SysKeyPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class DatabaseConnectionService {

    private final List<DatabaseConnectionFactory> factories;

    private final SysKeyDao sysKeyDao;

    public Connection create(DataSourcePojo dataSource,
                             List<DataSourcePropertyPojo> dataSourceProperties) {
        SysKeyPojo sysKey = sysKeyDao.selectTopOne();
        String username = dataSource.getUsername();
        String password = Aes.decryptFromBase64Data(dataSource.getPassword(), sysKey.getAesKey());
        String url = dataSource.getUrl();

        Properties info = new Properties();
        dataSourceProperties.forEach(prop -> info.put(prop.getKey(), prop.getValue()));
        try {
            return factories.stream()
                    .filter(factory -> factory.support(dataSource.getDatabaseType()))
                    .findFirst()
                    .orElseThrow(DomainErrors.NOT_SUPPORT_DATABASE_TYPE::exception)
                    .getConnection(username, password, url, dataSource.getDatabaseName(), info);
        } catch (SQLException e) {
            throw DomainErrors.CONNECT_DATABASE_FAILED.exception(e.getMessage(), e);
        }
    }

    public void testConnection(String username,
                               String password,
                               String url,
                               String databaseName,
                               String databaseType,
                               Properties properties) {
        try {
            factories.stream()
                    .filter(factory -> factory.support(databaseType))
                    .findFirst()
                    .orElseThrow(DomainErrors.NOT_SUPPORT_DATABASE_TYPE::exception)
                    .getConnection(username, password, url, databaseName, properties);
        } catch (SQLException e) {
            throw DomainErrors.CONNECT_DATABASE_FAILED.exception(e.getMessage(), e);
        }
    }
}
