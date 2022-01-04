package com.databasir.dao.exception;

public class DataNotExistsException extends RuntimeException {

    public DataNotExistsException() {
        super();
    }

    public DataNotExistsException(String message) {
        super(message);
    }

    public DataNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotExistsException(Throwable cause) {
        super(cause);
    }

    protected DataNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
