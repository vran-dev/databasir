package com.databasir.core.domain.system.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SystemEmailResponse {

    private String username;

    private String mailFrom;

    private String smtpHost;

    private Integer smtpPort;

    private Boolean useSSL;

    private LocalDateTime createAt;
}
