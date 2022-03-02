package com.databasir.api.config.oauth2;

import com.databasir.api.config.security.DatabasirUserDetailService;
import com.databasir.core.domain.user.data.UserDetailResponse;
import com.databasir.core.domain.app.OpenAuthAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class DatabasirOauth2LoginFilter extends AbstractAuthenticationProcessingFilter {

    public static final String OAUTH_LOGIN_URI = "/oauth2/login/*";

    @Autowired
    private OpenAuthAppService openAuthAppService;

    @Autowired
    private DatabasirUserDetailService databasirUserDetailService;

    public DatabasirOauth2LoginFilter(AuthenticationManager authenticationManager,
                                      OAuth2AuthenticationSuccessHandler auth2AuthenticationSuccessHandler,
                                      AuthenticationFailureHandler authenticationFailureHandler) {
        super(OAUTH_LOGIN_URI, authenticationManager);
        this.setAuthenticationSuccessHandler(auth2AuthenticationSuccessHandler);
        this.setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        Map<String, String[]> params = request.getParameterMap();
        String registrationId = new AntPathMatcher().extractPathWithinPattern(OAUTH_LOGIN_URI, request.getRequestURI());
        UserDetailResponse userDetailResponse = openAuthAppService.oauthCallback(registrationId, params);
        UserDetails details = databasirUserDetailService.loadUserByUsername(userDetailResponse.getUsername());
        DatabasirOAuth2Authentication authentication = new DatabasirOAuth2Authentication(details);
        if (!userDetailResponse.getEnabled()) {
            throw new DisabledException("账号已禁用");
        }
        authentication.setAuthenticated(true);
        if (log.isDebugEnabled()) {
            log.debug("login {} success", registrationId);
        }
        return authentication;
    }

}
