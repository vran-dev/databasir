package com.databasir.core.domain.app.handler;

import com.databasir.dao.tables.pojos.OauthApp;
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

    private OauthApp app;

    @Builder.Default
    private Map<String, String[]> parameters = new HashMap<>();

}
