package com.databasir.api.config.oauth2;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.common.JsonData;
import com.databasir.core.domain.log.service.OperationLogService;
import com.databasir.core.domain.login.data.UserLoginResponse;
import com.databasir.core.domain.login.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginService loginService;

    private final ObjectMapper objectMapper;

    private final OperationLogService operationLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        DatabasirUserDetails details = (DatabasirUserDetails) authentication.getPrincipal();
        loginService.generate(details.getUserPojo().getId());
        UserLoginResponse data = loginService.getUserLoginData(details.getUserPojo().getId())
                .orElseThrow(() -> {
                    operationLogService.saveLoginLog(details.getUserPojo(), false, null);
                    return new CredentialsExpiredException("请重新登陆");
                });
        operationLogService.saveLoginLog(details.getUserPojo(), true, null);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), JsonData.ok(data));
    }


}
