package com.databasir.core.infrastructure.driver;

import com.databasir.core.domain.DomainErrors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
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
            throw DomainErrors.LOAD_DRIVER_FAILED.exception();
        }
    }

    public DriverResult tempLoadFromRemote(String remoteUrl) {
        Path dirPath;
        try {
            dirPath = Files.createTempDirectory("databasir-drivers");
        } catch (IOException e) {
            log.error("load driver error cause create temp dir failed", e);
            throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception();
        }
        File file = download(remoteUrl, dirPath.toString());
        return new DriverResult(file.getAbsolutePath(), file);
    }

    private File download(String driverFileUrl, String parentDir) {
        Path parentDirPath = Paths.get(parentDir);
        try {
            Files.createDirectories(parentDirPath);
        } catch (IOException e) {
            log.error("create directory for driver failed", e);
            throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception(e);
        }

        // download
        try {
            return restTemplate.execute(driverFileUrl, HttpMethod.GET, null, response -> {
                if (response.getStatusCode().is2xxSuccessful()) {
                    String prefix = System.currentTimeMillis() + "";
                    String originFileName = response.getHeaders().getContentDisposition().getFilename();
                    String filename;
                    if (originFileName == null) {
                        URL url = new URL(driverFileUrl);
                        String nameFromUrl = FilenameUtils.getName(url.getPath());
                        if (StringUtils.endsWith(nameFromUrl, ".jar")) {
                            filename = prefix + "-" + nameFromUrl;
                        } else {
                            filename = prefix + ".jar";
                        }
                    } else {
                        filename = prefix + "-" + originFileName;
                    }
                    File targetFile = Paths.get(parentDir, filename).toFile();
                    FileOutputStream out = new FileOutputStream(targetFile);
                    StreamUtils.copy(response.getBody(), out);
                    IOUtils.closeQuietly(out, ex -> log.error("close file error", ex));
                    log.info("{} download success ", targetFile);
                    return targetFile;
                } else {
                    log.error("{} download error from {}: {} ", parentDir, driverFileUrl, response);
                    throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception("驱动下载失败："
                            + response.getStatusCode()
                            + ", "
                            + response.getStatusText());
                }
            });
        } catch (RestClientException e) {
            String msg = String.format("download driver from %s to %s failed", driverFileUrl, parentDir);
            log.error(msg, e);
            throw DomainErrors.DOWNLOAD_DRIVER_ERROR.exception(msg);
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
        DriverResult driverResult = tempLoadFromRemote(driverFileUrl);
        File driverFile = driverResult.getDriverFile();
        String className = resolveDriverClassName(driverFile);
        try {
            Files.deleteIfExists(driverFile.toPath());
        } catch (IOException e) {
            log.error("delete driver error from " + driverResult.getDriverFilePath(), e);
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
        String targetFile = targetDriverFile(databaseType, sourceFile.getName());
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

    private String targetDriverFile(String databaseType, String fileName) {
        return driverBaseDirectory
                + "/" + databaseType
                + "/" + fileName;
    }
}
