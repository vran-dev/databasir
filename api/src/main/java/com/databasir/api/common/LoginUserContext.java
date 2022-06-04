package com.databasir.api.common;

import com.databasir.api.config.security.DatabasirUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUserContext {

    public static Integer getLoginUserId() {
        DatabasirUserDetails principal = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return principal.getUser().getId();
    }
}
