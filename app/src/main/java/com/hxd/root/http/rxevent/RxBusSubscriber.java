package com.hxd.root.http.rxevent;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * 作 者： Cazaea
 * 日 期： 2018/12/5
 * 邮 箱： wistorm@sina.com
 */
public abstract class RxBusSubscriber<T> implements Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(T t) {
        receive(t);
    }

    @Override
    public void onError(Throwable e) {
        error(e);
    }

    @Override
    public void onComplete() {
        complete();
    }

    public abstract void receive(T data);

    public void error(Throwable e) {
        e.printStackTrace();
    }

    public void complete() {
    }

}
