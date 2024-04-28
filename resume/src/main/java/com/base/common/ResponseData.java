package com.base.common;

import lombok.Data;

import java.util.HashMap;


@Data
/**
 * 通用响应
 */
public class ResponseData extends HashMap<String, Object> {
    static final String DATA = "data";
    static final String STATUS = "status";
    static final String MSG = "msg";
    static final String FAIL = "fail";
    static final String SUCCESS = "success";

    private static ResponseData ResponseData(String status, String msg, Object data) {
        ResponseData responseData = new ResponseData();
        responseData.put(STATUS, status);
        responseData.put(MSG, msg);
        responseData.put(DATA, data);
        return responseData;
    }

    public static ResponseData success(Object data) {
        return ResponseData(SUCCESS, "操作成功", data);
    }

    public static ResponseData success(String msg, Object data) {
        return ResponseData(SUCCESS, msg, data);
    }

    public static ResponseData fail(Object data) {
        return ResponseData(FAIL, "操作失败", data);
    }

    public static ResponseData fail(String msg, Object data) {
        return ResponseData(FAIL, msg, data);
    }

    @Override
    public ResponseData put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
