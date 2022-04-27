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
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.jar.JarFile;

@Component
@Slf4j
@RequiredArgsConstructor
public class DriverResources {

    @Value("${databasir.db.driver-directory}")
    private String driverBaseDirectory;

    private final RestTemplate restTemplate;

    public DriverResult loadFromLocal(String localPath) {
        File driverFile = Paths.get(localPath).toFile();
        if (driverFile.exists()) {
            return new DriverResult(localPath, driverFile);
        } else {
            throw DomainErrors.UPLOAD_DRIVER_FILE_ERROR.exception();
        }
    }

    public DriverResult loadFromRemote(String remoteUrl) {
        String targetFile = "temp/" + System.currentTimeMillis() + ".jar";
        File file = download(remoteUrl, targetFile);
        return new DriverResult(targetFile, file);
    }

    public DriverResult load(String driverFilePath, String driverFileUrl, String databaseType) {
        if (driverFilePath == null) {
            String targetFile = targetDriverFile(databaseType);
            File file = download(driverFileUrl, targetFile);
            return new DriverResult(targetFile, file);
        }
        File driverFile = Paths.get(driverFilePath).toFile();
        if (driverFile.exists()) {
            return new DriverResult(driverFilePath, driverFile);
        } else {
            String targetFile = targetDriverFile(databaseType);
            File file = download(driverFileUrl, targetFile);
            return new DriverResult(targetFile, file);
        }
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

    public void deleteByDatabaseType(String databaseType) {
        String baseDir = driverBaseDirectory + "/" + databaseType;
        Path path = Paths.get(baseDir);
        try {
            if (Files.exists(path)) {
                Files.list(path).forEach(file -> {
                    try {
                        Files.deleteIfExists(file);
                    } catch (IOException e) {
                        log.error("delete file error " + file, e);
                    }
                });
            }
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("delete driver error " + databaseType, e);
        }
    }

    public String resolveDriverClassNameFromRemote(String driverFileUrl) {
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

    public String resolveDriverClassNameFromLocal(String driverFilePath) {
        File driverFile = Paths.get(driverFilePath).toFile();
        if (!driverFile.exists()) {
            throw DomainErrors.DRIVER_CLASS_NOT_FOUND.exception("驱动文件不存在，请重新上传");
        }
        return resolveDriverClassName(driverFile);
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

    public void validateDriverJar(File driverFile, String className) {
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
        }
    }

    public String copyToStandardDirectory(File sourceFile, String databaseType) {
        String targetFile = targetDriverFile(databaseType);
        try {
            Path target = Paths.get(targetFile);
            Files.createDirectories(target.getParent());
            Files.copy(sourceFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
            return targetFile;
        } catch (IOException e) {
            log.error("copy driver file error", e);
            throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception(e.getMessage());
        }
    }

    private String targetDriverFile(String databaseType) {
        return driverBaseDirectory
                + "/" + databaseType
                + "/" + System.currentTimeMillis() + ".jar";
    }
}
