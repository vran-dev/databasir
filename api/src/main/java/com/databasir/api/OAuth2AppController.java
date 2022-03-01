package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.infrastructure.oauth2.OAuthAppService;
import com.databasir.core.infrastructure.oauth2.OAuthHandler;
import com.databasir.core.infrastructure.oauth2.data.OAuthAppResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OAuth2AppController {

    private final OAuthHandler oAuthHandler;

    private final OAuthAppService oAuthAppService;

    @GetMapping("/oauth2/authorization/{registrationId}")
    @ResponseBody
    public JsonData<String> authorization(@PathVariable String registrationId) {
        String authorization = oAuthHandler.authorization(registrationId);
        return JsonData.ok(authorization);
    }

    @GetMapping("/oauth2/apps")
    @ResponseBody
    public JsonData<List<OAuthAppResponse>> listApps() {
        return JsonData.ok(oAuthAppService.listAll());
    }

}
