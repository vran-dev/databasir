package com.databasir.core.infrastructure.remote.github;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@SuppressWarnings("checkstyle:all")
@Builder
public class TokenRequest {

    private String client_id;

    private String client_secret;

    private String code;

    private String redirect_uri;

    public Map<String, String> toMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("client_id", client_id);
        map.put("client_secret", client_secret);
        map.put("code", code);
        if (redirect_uri != null) {
            map.put("redirect_uri", redirect_uri);
        }
        return map;
    }
}
