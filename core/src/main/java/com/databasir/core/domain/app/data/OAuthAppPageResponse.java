package com.databasir.core.domain.app.data;

import com.databasir.dao.enums.OAuthAppType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OAuthAppPageResponse {

    private Integer id;

    private String appName;

    private String appIcon;

    private OAuthAppType appType;

    private String registrationId;

    private String clientId;

    private String authUrl;

    private String resourceUrl;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

}
