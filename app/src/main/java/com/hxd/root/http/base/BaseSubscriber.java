package com.hxd.root.http.base;

import android.content.Context;

import com.hxd.root.http.exception.ApiException;
import com.hxd.root.http.exception.ResponseThrowable;
import com.hxd.root.http.rxutils.NetworkUtil;

import io.reactivex.observers.DisposableObserver;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/29
 * 邮 箱： wistorm@sina.com
 * <p>
 * 该类仅供参考，实际业务Code, 根据需求来定义，
 */
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    private Context context;
    private boolean isNeedCahe;

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        // TODO some common as show loading  and check netWork is NetworkAvailable
        // if NetworkAvailable no ! Must to call onComplete
        if (!NetworkUtil.isNetworkConnected(context)) {
            onComplete();
        }
    }

    @Override
    public void onError(Throwable e) {
        // TODO something error
        if (e instanceof ResponseThrowable) {
            onError((ResponseThrowable) e);
        } else {
            onError(new ResponseThrowable(e, ApiException.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {
        // TODO some common as  dismiss loading
//        ToastUtil.showShort("http is Complete");

    }

    @Override
    public void onNext(T data) {
        onResult(data);
    }

    public abstract void onError(ResponseThrowable e);

    public abstract void onResult(T t);

}
