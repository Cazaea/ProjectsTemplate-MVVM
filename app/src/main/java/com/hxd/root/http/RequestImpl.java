package com.hxd.root.http;


import io.reactivex.disposables.Disposable;

/**
 * Created by Cazaea on 2017/1/17.
 * 用于数据请求的回调
 */

public interface RequestImpl {

    void loadFailed();

    void loadSuccess(Object object);

    void addSubscription(Disposable subscription);
}
