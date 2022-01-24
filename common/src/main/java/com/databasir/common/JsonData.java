package com.databasir.common;

import lombok.Data;

@Data
public class JsonData<T> {

    /**
     * maybe null
     */
    private T data;

    /**
     * only exists when error happened
     */
    private String errCode;

    /**
     * only exists when error happened
     */
    private String errMessage;

    public static <T> JsonData<T> ok() {
        return ok(null);
    }

    public static <T> JsonData<T> ok(T data) {
        JsonData<T> jsonData = new JsonData<>();
        jsonData.setData(data);
        return jsonData;
    }

    public static <T> JsonData<T> error(String errorCode, String errMessage) {
        JsonData<T> jsonData = new JsonData<>();
        jsonData.setErrCode(errorCode);
        jsonData.setErrMessage(errMessage);
        return jsonData;
    }
}
