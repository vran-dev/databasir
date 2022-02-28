package com.databasir.core.infrastructure.oauth2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthProcessContext {

    private String registrationId;

    @Builder.Default
    private Map<String, String> callbackParameters = new HashMap<>();

}
