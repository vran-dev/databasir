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
    private String errMessage;

    /**
     * @param errorCodeMessage 错误信息
     */
    public DatabasirException(DatabasirErrors errorCodeMessage) {
        super(errorCodeMessage.getErrMessage());
        this.errorCodeMessage = errorCodeMessage;
        this.errCode = errorCodeMessage.getErrCode();
        this.errMessage = errorCodeMessage.getErrMessage();
    }

    /**
     * @param errorCodeMessage 错误信息
     * @param overrideMessage  覆盖 message
     */
    public DatabasirException(DatabasirErrors errorCodeMessage, String overrideMessage) {
        super(overrideMessage);
        this.errorCodeMessage = errorCodeMessage;
        this.errCode = errorCodeMessage.getErrCode();
        this.errMessage = overrideMessage;
    }

    /**
     * @param errorCodeMessage 错误信息
     * @param cause            root cause
     */
    public DatabasirException(DatabasirErrors errorCodeMessage, Throwable cause) {
        super(errorCodeMessage.getErrMessage(), cause);
        this.errorCodeMessage = errorCodeMessage;
        this.errCode = errorCodeMessage.getErrCode();
        this.errMessage = errorCodeMessage.getErrMessage();
    }
}
