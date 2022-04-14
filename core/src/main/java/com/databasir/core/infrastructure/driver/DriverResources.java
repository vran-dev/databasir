package com.databasir.core.infrastructure.driver;

import com.databasir.core.domain.DomainErrors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.jar.JarFile;

@Component
@Slf4j
@RequiredArgsConstructor
public class DriverResources {

    @Value("${databasir.db.driver-directory}")
    private String driverBaseDirectory;

    private final RestTemplate restTemplate;

    public void delete(String databaseType) {
        Path path = Paths.get(driverFilePath(driverBaseDirectory, databaseType));
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("delete driver error " + databaseType, e);
        }
    }

    public String resolveSqlDriverNameFromJar(String driverFileUrl) {
        String tempFilePath = "temp/" + UUID.randomUUID() + ".jar";
        File driverFile = doDownload(driverFileUrl, tempFilePath);
        String className = doResolveSqlDriverNameFromJar(driverFile);
        try {
            Files.deleteIfExists(driverFile.toPath());
        } catch (IOException e) {
            log.error("delete driver error " + tempFilePath, e);
        }
        return className;
    }

    public File loadOrDownload(String databaseType, String driverFileUrl) {
        String filePath = driverFilePath(driverBaseDirectory, databaseType);
        Path path = Path.of(filePath);
        if (Files.exists(path)) {
            // ignore
            log.debug("{} already exists, ignore download from {}", filePath, driverFileUrl);
            return path.toFile();
        }
        return this.doDownload(driverFileUrl, filePath);
    }

    private File doDownload(String driverFileUrl, String filePath) {
        Path path = Path.of(filePath);

        // create parent directory
        if (Files.notExists(path)) {
            path.getParent().toFile().mkdirs();
            try {
                Files.createFile(path);
            } catch (IOException e) {
                log.error("create file error " + filePath, e);
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
                    log.info("{} download success ", filePath);
                    return file;
                } else {
                    log.error("{} download error from {}: {} ", filePath, driverFileUrl, response);
                    throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception("驱动下载失败："
                            + response.getStatusCode()
                            + ", "
                            + response.getStatusText());
                }
            });
        } catch (IllegalArgumentException e) {
            log.error(filePath + " download driver error", e);
            throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception(e.getMessage());
        }
    }

    private String doResolveSqlDriverNameFromJar(File driverFile) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(driverFile);
        } catch (IOException e) {
            log.error("resolve driver class name error", e);
            throw DomainErrors.DRIVER_CLASS_NAME_OBTAIN_ERROR.exception(e.getMessage());
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
                        throw DomainErrors.DRIVER_CLASS_NAME_OBTAIN_ERROR.exception(e.getMessage());
                    } finally {
                        IOUtils.closeQuietly(reader, ex -> log.error("close reader error", ex));
                    }
                })
                .orElseThrow(DomainErrors.DRIVER_CLASS_NAME_OBTAIN_ERROR::exception);
        IOUtils.closeQuietly(jarFile, ex -> log.error("close jar file error", ex));
        return driverClassName;
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
