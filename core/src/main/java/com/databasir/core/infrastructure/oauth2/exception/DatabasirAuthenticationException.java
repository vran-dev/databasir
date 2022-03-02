package com.databasir.core.infrastructure.oauth2.exception;

import com.databasir.common.DatabasirException;
import org.springframework.security.core.AuthenticationException;

public class DatabasirAuthenticationException extends AuthenticationException {

    public DatabasirAuthenticationException(DatabasirException databasirException) {
        super(databasirException.getErrMessage(), databasirException);
    }

    public DatabasirAuthenticationException(String msg) {
        super(msg);
    }

    public DatabasirAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
