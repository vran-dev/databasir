package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.login.data.LoginKeyResponse;
import com.databasir.core.infrastructure.oauth2.OAuthAppService;
import com.databasir.core.infrastructure.oauth2.OAuthHandler;
import com.databasir.core.infrastructure.oauth2.data.OAuthAppResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OAuth2LoginController {

    private final OAuthHandler oAuthHandler;

    private final OAuthAppService oAuthAppService;

    @GetMapping("/oauth2/authorization/{registrationId}")
    public RedirectView authorization(@PathVariable String registrationId) {
        String authorization = oAuthHandler.authorization(registrationId);
        return new RedirectView(authorization);
    }

    @GetMapping("/oauth2/login/{registrationId}")
    public RedirectView callback(@PathVariable String registrationId,
                                 @RequestParam Map<String, String> params,
                                 HttpServletResponse response) {
        LoginKeyResponse loginKey = oAuthAppService.oauthCallback(registrationId, params);
        // set cookie
        Cookie accessTokenCookie = new Cookie("accessToken", loginKey.getAccessToken());
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);

        long epochSecond = loginKey.getAccessTokenExpireAt()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        Cookie accessTokenExpireAtCookie = new Cookie("accessTokenExpireAt", epochSecond + "");
        accessTokenExpireAtCookie.setPath("/");
        response.addCookie(accessTokenExpireAtCookie);
        return new RedirectView("/");
    }

    @GetMapping("/oauth2/apps")
    public JsonData<List<OAuthAppResponse>> listApps() {
        return JsonData.ok(oAuthAppService.listAll());
    }

}
