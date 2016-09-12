package com.tripint.intersight.common;

/**
 * Created by liukun on 16/3/5.
 */
public class BaseResponse<T> {

    private int code;
    private String msg;

    //用来模仿Data
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
