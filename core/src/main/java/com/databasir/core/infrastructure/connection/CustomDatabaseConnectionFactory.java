package com.databasir.core.infrastructure.connection;

import com.alibaba.excel.util.StringUtils;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.infrastructure.driver.DriverResources;
import com.databasir.dao.impl.DatabaseTypeDao;
import com.databasir.dao.tables.pojos.DatabaseTypePojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

@Component
@RequiredArgsConstructor
@Slf4j
@Order
public class CustomDatabaseConnectionFactory implements DatabaseConnectionFactory {

    private final DatabaseTypeDao databaseTypeDao;

    private final DriverResources driverResources;

    @Override
    public boolean support(String databaseType) {
        return databaseTypeDao.existsByDatabaseType(databaseType);
    }

    @Override
    public Connection getConnection(Context context) throws SQLException {
        String databaseType = context.getDatabaseType();
        DatabaseTypePojo type = databaseTypeDao.selectByDatabaseType(databaseType);

        File driverFile = loadDriver(type);
        URLClassLoader loader = null;
        try {
            loader = new URLClassLoader(
                    new URL[]{
                            driverFile.toURI().toURL()
                    },
                    this.getClass().getClassLoader()
            );
        } catch (MalformedURLException e) {
            log.error("load driver error " + context, e);
            throw DomainErrors.CONNECT_DATABASE_FAILED.exception(e.getMessage());
        }
        // retrieve the driver class
        Class<?> clazz = null;
        Driver driver = null;
        try {
            clazz = Class.forName(type.getJdbcDriverClassName(), false, loader);
            driver = (Driver) clazz.getConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            log.error("init driver error", e);
            throw DomainErrors.CONNECT_DATABASE_FAILED.exception("驱动初始化异常, 请检查驱动类名：" + e.getMessage());
        } catch (InvocationTargetException
                 | InstantiationException
                 | IllegalAccessException
                 | NoSuchMethodException e) {
            log.error("init driver error", e);
            throw DomainErrors.CONNECT_DATABASE_FAILED.exception("驱动初始化异常：" + e.getMessage());
        }

        String urlPattern = type.getUrlPattern();
        String jdbcUrl = urlPattern.replace("{{jdbc.protocol}}", type.getJdbcProtocol())
                .replace("{{db.url}}", context.getUrl())
                .replace("{{db.name}}", context.getDatabaseName())
                .replace("{{db.schema}}", context.getSchemaName());
        Properties info = new Properties();
        info.put("user", context.getUsername());
        info.put("password", context.getPassword());
        return driver.connect(jdbcUrl, info);
    }

    private File loadDriver(DatabaseTypePojo type) {
        if (StringUtils.isNotBlank(type.getJdbcDriverFilePath())) {
            return driverResources.loadFromLocal(type.getJdbcDriverFilePath()).getDriverFile();
        }
        if (StringUtils.isNotBlank(type.getJdbcDriverFileUrl())) {
            File remoteFile = driverResources.loadFromRemote(type.getJdbcDriverFileUrl()).getDriverFile();
            driverResources.validateDriverJar(remoteFile, type.getJdbcDriverClassName());
            String targetFile = driverResources.copyToStandardDirectory(remoteFile, type.getDatabaseType());
            return Paths.get(targetFile).toFile();
        }
        String databaseType = type.getDatabaseType();
        throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception("驱动加载失败, database=" + databaseType);
    }
}
