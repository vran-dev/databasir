package com.databasir.core.domain.app.data;

import com.databasir.dao.enums.OAuthAppType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthAppResponse {

    private Integer id;

    private String appName;

    private String appIcon;

    private OAuthAppType appType;

    private String registrationId;

    private LocalDateTime createAt;

}
