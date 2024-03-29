package com.databasir.core.domain.app.data;

import com.databasir.dao.enums.OAuthAppType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class OAuthAppCreateRequest {

    @NotNull
    private String registrationId;

    @NotBlank
    private String appName;

    @NotNull
    private OAuthAppType appType;

    private String appIcon;

    private List<OauthAppPropertyData> properties = new ArrayList<>();

}
