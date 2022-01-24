package com.databasir.common.exception;

import com.databasir.common.DatabasirErrors;
import com.databasir.common.DatabasirException;

public class InvalidTokenException extends DatabasirException {

    public InvalidTokenException(DatabasirErrors errorCodeMessage) {
        super(errorCodeMessage);
    }

    public InvalidTokenException(DatabasirErrors errorCodeMessage, String overrideMessage) {
        super(errorCodeMessage, overrideMessage);
    }

    public InvalidTokenException(DatabasirErrors errorCodeMessage, Throwable cause) {
        super(errorCodeMessage, cause);
    }

    @Override
    public String toString() {
        return getErrCode() + ": " + getErrMessage();
    }
}
