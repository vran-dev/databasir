package com.databasir.api.config.security;

import com.databasir.common.JsonData;
import com.databasir.core.domain.login.data.LoginKeyResponse;
import com.databasir.core.domain.login.service.LoginService;
import com.databasir.core.domain.user.data.UserLoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DatabasirAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    private final LoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        DatabasirUserDetails user = (DatabasirUserDetails) authentication.getPrincipal();
        List<UserLoginResponse.RoleResponse> roles = user.getRoles()
                .stream()
                .map(ur -> {
                    UserLoginResponse.RoleResponse data = new UserLoginResponse.RoleResponse();
                    data.setRole(ur.getRole());
                    data.setGroupId(ur.getGroupId());
                    return data;
                })
                .collect(Collectors.toList());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        UserLoginResponse data = new UserLoginResponse();
        data.setId(user.getUserPojo().getId());
        data.setNickname(user.getUserPojo().getNickname());
        data.setEmail(user.getUserPojo().getEmail());
        data.setUsername(user.getUsername());

        LoginKeyResponse loginKey = loginService.generate(user.getUserPojo().getId());
        data.setAccessToken(loginKey.getAccessToken());
        long expireAt = loginKey.getAccessTokenExpireAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        data.setAccessTokenExpireAt(expireAt);
        data.setRefreshToken(loginKey.getRefreshToken());
        data.setRoles(roles);
        objectMapper.writeValue(response.getWriter(), JsonData.ok(data));
    }
}
