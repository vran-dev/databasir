package com.databasir.common;

public class SystemException extends RuntimeException{

    private static final String MSG_INTERNAL_SERVER_ERROR = "服务器开小差了，请稍后再试";

    public static final SystemException INTERNAL_SERVER_ERROR = new SystemException(MSG_INTERNAL_SERVER_ERROR);

    /**
     * @param msg the detail message
     */
    public SystemException(String msg) {
        super(msg);
    }

    /**
     * @param msg   the detail message
     * @param cause the nested exception
     */
    public SystemException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public static SystemException internalServerErrorWithCause(Throwable cause) {
        return new SystemException(MSG_INTERNAL_SERVER_ERROR, cause);
    }
}
