package com.databasir.api.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabasirAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(javax.servlet.http.HttpServletRequest request,
                         javax.servlet.http.HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.warn("验证未通过. 提示信息 - {} - {}", request.getRequestURI(), authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }

}
