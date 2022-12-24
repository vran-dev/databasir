package com.databasir.core.domain.app.data;

import com.databasir.dao.enums.OAuthAppType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthAppPlatformResponse {

    private OAuthAppType authAppType;

    private String authAppName;

    @Builder.Default
    private List<OAuthAppPlatformProperty> properties = new ArrayList<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OAuthAppPlatformProperty {

        private String name;

        private String label;

        private String description;

        private Boolean required;

        private String defaultValue;

    }
}
