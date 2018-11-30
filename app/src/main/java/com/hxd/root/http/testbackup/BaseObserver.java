package com.hxd.root.http.testbackup;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/29
 * 邮 箱： wistorm@sina.com
 * <p>
 * 封装的BaseObserver
 */
public abstract class BaseObserver<T> implements Observer<T> {


    @Override
    public void onSubscribe(Disposable d) {
        //这里可以显示进度框

    }

    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable e) {

//        //这里用来隐藏进度框，还可以提示错误消息
//        if (Ne.isConnected()) {
//            if (e instanceof TimeoutException) {
//                ToastUtils.showShort("连接服务器超时");
//            }
//
//        } else {
//            ToastUtils.showShort("无网络，请检查");
//        }
    }


    @Override
    public void onComplete() {
//        LogUtils.i("onComplete");
        //这里用来隐藏进度框
    }


}
