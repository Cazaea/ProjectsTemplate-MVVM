package com.hxd.root.http.base;

/**
 * Created by Cazaea on 2017/5/10.
 * 该类仅供参考，实际业务返回的固定字段, 根据需求来定义，
 */
public class BaseResponse<T> {

    /**
     * code : 1000
     * msg : XX成功
     * data : {}/[]
     */
    private T data;     // 具体的数据结果
    private int code;   // 返回的code
    private String msg; // 返回接口的说明

    public boolean isOk() {
        return code == 1000;
    }

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

    public void setData( T data) {
        this.data = data;
    }

}
