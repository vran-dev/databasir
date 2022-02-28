package com.databasir.core.infrastructure.oauth2.data;

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

    private String name;

    private String icon;

    private String registrationId;

    private String clientId;

    private String clientSecret;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

}
