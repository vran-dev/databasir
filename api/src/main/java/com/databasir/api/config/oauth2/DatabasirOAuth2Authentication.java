package com.databasir.api.config.oauth2;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class DatabasirOAuth2Authentication extends AbstractAuthenticationToken {

    private Object credentials;

    private Object principal;

    public DatabasirOAuth2Authentication(UserDetails principal) {
        super(principal.getAuthorities());
        this.credentials = null;
        this.principal = principal;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
