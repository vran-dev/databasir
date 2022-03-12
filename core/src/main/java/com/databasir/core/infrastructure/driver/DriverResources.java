package com.databasir.core.infrastructure.driver;

import com.databasir.core.domain.DomainErrors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
@RequiredArgsConstructor
public class DriverResources {

    @Value("${databasir.db.driver-directory}")
    private String driverBaseDirectory;

    private final RestTemplate restTemplate;

    public File download(String databaseType, String driverFileUrl) throws IOException {
        // create parent directory
        if (Files.notExists(Path.of(driverBaseDirectory))) {
            Files.createDirectory(Path.of(driverBaseDirectory));
        }

        String filePath = driverPath(databaseType);
        Path path = Path.of(filePath);
        if (Files.exists(path)) {
            // ignore
            log.debug("{} already exists, ignore download from {}", filePath, driverFileUrl);
            return path.toFile();
        } else {
            // download
            try {
                return restTemplate.execute(driverFileUrl, HttpMethod.GET, null, response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        File file = path.toFile();
                        StreamUtils.copy(response.getBody(), new FileOutputStream(file));
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
    }

    public void delete(String databaseType) {
        Path path = Paths.get(driverPath(databaseType));
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("delete driver error " + databaseType, e);
        }
    }

    private String driverPath(String databaseType) {
        String fileName = databaseType + ".jar";
        String filePath;
        if (driverBaseDirectory.endsWith(File.separator)) {
            filePath = driverBaseDirectory + fileName;
        } else {
            filePath = driverBaseDirectory + File.separator + fileName;
        }
        return filePath;
    }
}
