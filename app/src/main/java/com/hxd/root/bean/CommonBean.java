package com.hxd.root.bean;

import android.databinding.BaseObservable;

/**
 * 作 者： Cazaea
 * 日 期： 2018/8/24
 * 邮 箱： wistorm@sina.com
 */
public class CommonBean extends BaseObservable {

    /**
     * code : 1000
     * msg : 密码更新成功
     * data : null / "1328" / {}
     */
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
