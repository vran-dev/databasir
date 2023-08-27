package com.databasir.common.exception;

import com.databasir.common.DatabasirErrors;
import com.databasir.common.DatabasirException;

public class InvalidTokenException extends DatabasirException {

    public InvalidTokenException(DatabasirErrors errorCodeMessage) {
        super(errorCodeMessage);
    }

    @Override
    public String toString() {
        return "InvalidTokenException:" + getErrCode();
    }
}
