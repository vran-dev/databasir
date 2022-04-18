package com.databasir.core.infrastructure.driver;

import com.databasir.core.domain.DomainErrors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.jar.JarFile;

@Component
@Slf4j
@RequiredArgsConstructor
public class DriverResources {

    @Value("${databasir.db.driver-directory}")
    private String driverBaseDirectory;

    private final RestTemplate restTemplate;

    public void deleteByDatabaseType(String databaseType) {
        Path path = Paths.get(driverFilePath(driverBaseDirectory, databaseType));
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("delete driver error " + databaseType, e);
        }
    }

    public Optional<File> loadByDatabaseType(String databaseType) {
        Path path = Paths.get(driverFilePath(driverBaseDirectory, databaseType));
        if (Files.exists(path)) {
            return Optional.of(path.toFile());
        } else {
            return Optional.empty();
        }
    }

    public File loadOrDownloadByDatabaseType(String databaseType, String driverFileUrl) {
        return loadByDatabaseType(databaseType)
                .orElseGet(() -> download(driverFileUrl, driverFilePath(driverBaseDirectory, databaseType)));
    }

    public String resolveDriverClassName(String driverFileUrl) {
        String tempFilePath = "temp/" + UUID.randomUUID() + ".jar";
        File driverFile = download(driverFileUrl, tempFilePath);
        String className = resolveDriverClassName(driverFile);
        try {
            Files.deleteIfExists(driverFile.toPath());
        } catch (IOException e) {
            log.error("delete driver error " + tempFilePath, e);
        }
        return className;
    }

    public String resolveDriverClassName(File driverFile) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(driverFile);
        } catch (IOException e) {
            log.error("resolve driver class name error", e);
            throw DomainErrors.DRIVER_CLASS_NOT_FOUND.exception(e.getMessage());
        }

        final JarFile driverJar = jarFile;
        String driverClassName = jarFile.stream()
                .filter(entry -> entry.getName().contains("META-INF/services/java.sql.Driver"))
                .findFirst()
                .map(entry -> {
                    InputStream stream = null;
                    BufferedReader reader = null;
                    try {
                        stream = driverJar.getInputStream(entry);
                        reader = new BufferedReader(new InputStreamReader(stream));
                        return reader.readLine();
                    } catch (IOException e) {
                        log.error("resolve driver class name error", e);
                        throw DomainErrors.DRIVER_CLASS_NOT_FOUND.exception(e.getMessage());
                    } finally {
                        IOUtils.closeQuietly(reader, ex -> log.error("close reader error", ex));
                    }
                })
                .orElseThrow(DomainErrors.DRIVER_CLASS_NOT_FOUND::exception);
        IOUtils.closeQuietly(jarFile, ex -> log.error("close jar file error", ex));
        return driverClassName;
    }

    private File download(String driverFileUrl, String targetFile) {
        Path path = Path.of(targetFile);

        // create parent directory
        if (Files.notExists(path)) {
            path.getParent().toFile().mkdirs();
            try {
                Files.createFile(path);
            } catch (IOException e) {
                log.error("create file error " + targetFile, e);
                throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception(e.getMessage());
            }
        }

        // download
        try {
            return restTemplate.execute(driverFileUrl, HttpMethod.GET, null, response -> {
                if (response.getStatusCode().is2xxSuccessful()) {
                    File file = path.toFile();
                    FileOutputStream out = new FileOutputStream(file);
                    StreamUtils.copy(response.getBody(), out);
                    IOUtils.closeQuietly(out, ex -> log.error("close file error", ex));
                    log.info("{} download success ", targetFile);
                    return file;
                } else {
                    log.error("{} download error from {}: {} ", targetFile, driverFileUrl, response);
                    throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception("驱动下载失败："
                            + response.getStatusCode()
                            + ", "
                            + response.getStatusText());
                }
            });
        } catch (RestClientException e) {
            log.error(targetFile + " download driver error", e);
            throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception(e.getMessage());
        }
    }

    public void validateJar(String driverFileUrl, String className) {
        String tempFilePath = "temp/" + UUID.randomUUID() + ".jar";
        File driverFile = download(driverFileUrl, tempFilePath);
        URLClassLoader loader = null;
        try {
            loader = new URLClassLoader(
                    new URL[]{driverFile.toURI().toURL()},
                    this.getClass().getClassLoader()
            );
        } catch (MalformedURLException e) {
            log.error("load driver jar error ", e);
            throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception(e.getMessage());
        }

        try {
            Class clazz = Class.forName(className, false, loader);
            boolean isValid = ClassUtils.getAllInterfaces(clazz)
                    .stream()
                    .anyMatch(cls -> cls.getName().equals("java.sql.Driver"));
            if (!isValid) {
                throw DomainErrors.DRIVER_CLASS_NOT_FOUND.exception("不合法的驱动类，请重新指定");
            }
        } catch (ClassNotFoundException e) {
            log.error("init driver error", e);
            throw DomainErrors.DRIVER_CLASS_NOT_FOUND.exception("驱动初始化异常, 请检查驱动类名：" + e.getMessage());
        } finally {
            IOUtils.closeQuietly(loader);
            try {
                Files.deleteIfExists(driverFile.toPath());
            } catch (IOException e) {
                log.error("delete driver error " + tempFilePath, e);
            }
        }
    }

    private String driverFilePath(String baseDir, String databaseType) {
        String fileName = databaseType + ".jar";
        String filePath;
        if (baseDir.endsWith(File.separator)) {
            filePath = baseDir + fileName;
        } else {
            filePath = baseDir + File.separator + fileName;
        }
        return filePath;
    }
}
