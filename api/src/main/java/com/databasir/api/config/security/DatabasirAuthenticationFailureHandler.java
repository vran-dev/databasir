package com.databasir.api.config.security;

import com.databasir.common.JsonData;
import com.databasir.core.domain.app.exception.DatabasirAuthenticationException;
import com.databasir.core.domain.log.service.OperationLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabasirAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    private final OperationLogService operationLogService;

    private final MessageSource messageSource;

    private final LocaleResolver localeResolver;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (exception instanceof BadCredentialsException) {
            JsonData<Void> data = JsonData.error("-1", "用户名或密码错误");
            saveLoginFailedLog(username, data.getErrMessage());
            String jsonString = objectMapper.writeValueAsString(data);
            response.setStatus(HttpStatus.OK.value());
            response.getOutputStream().write(jsonString.getBytes(StandardCharsets.UTF_8));
        } else if (exception instanceof DisabledException) {
            JsonData<Void> data = JsonData.error("-1", "用户已禁用");
            saveLoginFailedLog(username, data.getErrMessage());
            String jsonString = objectMapper.writeValueAsString(data);
            response.setStatus(HttpStatus.OK.value());
            response.getOutputStream().write(jsonString.getBytes(StandardCharsets.UTF_8));
        } else if (exception instanceof DatabasirAuthenticationException) {
            DatabasirAuthenticationException bizException = (DatabasirAuthenticationException) exception;
            Locale locale = localeResolver.resolveLocale(request);
            String msg = messageSource.getMessage(bizException.getErrCode(), bizException.getArgs(), locale);
            JsonData<Void> data = JsonData.error(bizException.getErrCode(), msg);
            saveLoginFailedLog(username, data.getErrMessage());
            String jsonString = objectMapper.writeValueAsString(data);
            response.setStatus(HttpStatus.OK.value());
            response.getOutputStream().write(jsonString.getBytes(StandardCharsets.UTF_8));
        } else {
            JsonData<Void> data = JsonData.error("-1", "未登录或未授权用户");
            saveLoginFailedLog(username, data.getErrMessage());
            String jsonString = objectMapper.writeValueAsString(data);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().write(jsonString.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void saveLoginFailedLog(String username, String message) {
        if (username != null) {
            operationLogService.saveLoginFailedLog(username, message);
        }
    }
}
