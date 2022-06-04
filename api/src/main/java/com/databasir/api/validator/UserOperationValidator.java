package com.databasir.api.validator;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.core.domain.DomainErrors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserOperationValidator {

    public void forbiddenIfUpdateSelfRole(Integer userId) {
        DatabasirUserDetails principal = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal.getUser().getId().equals(userId)) {
            throw DomainErrors.CANNOT_UPDATE_SELF_ROLE.exception();
        }
    }

    public boolean isMyself(Integer userId) {
        DatabasirUserDetails principal = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return principal.getUser().getId().equals(userId);
    }

}
