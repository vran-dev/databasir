package com.databasir.core.domain.app.handler;

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
    private Map<String, String[]> callbackParameters = new HashMap<>();

}
