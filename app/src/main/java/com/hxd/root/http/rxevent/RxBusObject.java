package com.hxd.root.http.rxevent;

/**
 * Created by Cazaea on 16/5/17.
 */
public class RxBusObject {
    private int code;
    private Object object;

    private RxBusObject(int code, Object object) {
        this.code = code;
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int pCode) {
        code = pCode;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object pObject) {
        object = pObject;
    }

    static RxBusObject newInstance(int code, Object object) {
        return new RxBusObject(code, object);
    }
}
