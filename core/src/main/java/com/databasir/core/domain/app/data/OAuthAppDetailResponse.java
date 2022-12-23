package com.databasir.core.domain.app.data;

import com.databasir.dao.enums.OAuthAppType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OAuthAppDetailResponse {

    private Integer id;

    private String appName;

    private String appIcon;

    private OAuthAppType appType;

    private String registrationId;

    private List<OauthAppPropertyData> properties = new ArrayList<>();

    private LocalDateTime updateAt;

    private LocalDateTime createAt;
}
