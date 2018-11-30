package com.hxd.root.http.rxutils;


import io.reactivex.disposables.Disposable;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/29
 * 邮 箱： wistorm@sina.com
 * <p>
 * 用于数据请求的回调
 */

public interface RequestImpl {

    void loadFailed();

    void loadSuccess(Object object);

    void addSubscription(Disposable subscription);
}
