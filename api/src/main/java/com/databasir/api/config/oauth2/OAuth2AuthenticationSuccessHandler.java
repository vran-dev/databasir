package com.databasir.api.config.oauth2;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.common.JsonData;
import com.databasir.core.domain.login.data.LoginKeyResponse;
import com.databasir.core.domain.login.data.UserLoginResponse;
import com.databasir.core.domain.login.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginService loginService;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        DatabasirUserDetails details = (DatabasirUserDetails) authentication.getPrincipal();
        LoginKeyResponse loginKey = loginService.generate(details.getUserPojo().getId());
        UserLoginResponse data = loginService.getUserLoginData(details.getUserPojo().getId())
                .orElseThrow(() -> new CredentialsExpiredException("请重新登陆"));
        objectMapper.writeValue(response.getWriter(), JsonData.ok(data));
    }
}
