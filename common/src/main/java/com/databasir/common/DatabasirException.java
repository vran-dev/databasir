package com.databasir.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DatabasirException extends RuntimeException {

    @Getter
    private DatabasirErrors errorCodeMessage;

    @Getter
    private String errCode;

    @Getter
    private Object[] args = new Object[0];

    /**
     * @param errorCodeMessage 错误信息
     */
    public DatabasirException(DatabasirErrors errorCodeMessage) {
        super(errorCodeMessage.getErrCode());
        this.errorCodeMessage = errorCodeMessage;
        this.errCode = errorCodeMessage.getErrCode();
    }

    /**
     * @param errorCodeMessage 错误信息
     * @param cause            root cause
     */
    public DatabasirException(DatabasirErrors errorCodeMessage, Throwable cause) {
        super(errorCodeMessage.getErrCode(), cause);
        this.errorCodeMessage = errorCodeMessage;
        this.errCode = errorCodeMessage.getErrCode();
    }

    public DatabasirException(DatabasirErrors errorCodeMessage, Object... args) {
        super(errorCodeMessage.getErrCode());
        this.errorCodeMessage = errorCodeMessage;
        this.errCode = errorCodeMessage.getErrCode();
        this.args = args;
    }

    public DatabasirException(DatabasirErrors errorCodeMessage, String overrideMessage, Throwable cause) {
        super(overrideMessage, cause);
        this.errorCodeMessage = errorCodeMessage;
        this.errCode = errorCodeMessage.getErrCode();
    }
}
