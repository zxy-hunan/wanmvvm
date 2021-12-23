package com.zyx_hunan.baseutil.net.util;

/**
 * 统一响应
 * @param <T>
 */
public class BaseResponse<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getEntity() {
        return data;
    }
    public void setEntity(T data) {
        this.data = data;
    }
}
