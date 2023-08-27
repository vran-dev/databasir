package com.databasir.core.domain.app.exception;

import com.databasir.common.DatabasirException;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class DatabasirAuthenticationException extends AuthenticationException {

    private final DatabasirException databasirException;

    public DatabasirAuthenticationException(DatabasirException databasirException) {
        super(databasirException.getErrCode(), databasirException);
        this.databasirException = databasirException;
    }

    public String getErrCode() {
        return databasirException.getErrCode();
    }

    public Object[] getArgs() {
        return databasirException.getArgs();
    }

}
