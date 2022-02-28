package com.databasir.core.infrastructure.oauth2;

import lombok.Data;

@Data
public class OAuthProcessResult {

    private String email;

    private String username;

    private String nickname;

    private String avatar;

}
