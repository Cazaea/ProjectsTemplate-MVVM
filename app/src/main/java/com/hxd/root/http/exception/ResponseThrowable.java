package com.hxd.root.http.exception;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/29
 * 邮 箱： wistorm@sina.com
 */
public class ResponseThrowable extends Exception {

    private  int code;
    private String message;

    public ResponseThrowable(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ResponseThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String pMessage) {
        message = pMessage;
    }

}
