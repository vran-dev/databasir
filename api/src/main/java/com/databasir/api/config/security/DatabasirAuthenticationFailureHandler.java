package com.databasir.api.config.security;

import com.databasir.core.domain.app.exception.DatabasirAuthenticationException;
import com.databasir.common.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class DatabasirAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (exception instanceof BadCredentialsException) {
            JsonData<Void> data = JsonData.error("-1", "用户名或密码错误");
            String jsonString = objectMapper.writeValueAsString(data);
            response.setStatus(HttpStatus.OK.value());
            response.getOutputStream().write(jsonString.getBytes(StandardCharsets.UTF_8));
        } else if (exception instanceof DisabledException) {
            JsonData<Void> data = JsonData.error("-1", "用户已禁用");
            String jsonString = objectMapper.writeValueAsString(data);
            response.setStatus(HttpStatus.OK.value());
            response.getOutputStream().write(jsonString.getBytes(StandardCharsets.UTF_8));
        } else if (exception instanceof DatabasirAuthenticationException) {
            DatabasirAuthenticationException bizException = (DatabasirAuthenticationException) exception;
            JsonData<Void> data = JsonData.error("-1", bizException.getMessage());
            String jsonString = objectMapper.writeValueAsString(data);
            response.setStatus(HttpStatus.OK.value());
            response.getOutputStream().write(jsonString.getBytes(StandardCharsets.UTF_8));
        } else {
            JsonData<Void> data = JsonData.error("-1", "未登录或未授权用户");
            String jsonString = objectMapper.writeValueAsString(data);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().write(jsonString.getBytes(StandardCharsets.UTF_8));
        }
    }
}
