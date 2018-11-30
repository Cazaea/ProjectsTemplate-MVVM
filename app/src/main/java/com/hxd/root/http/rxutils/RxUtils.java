package com.hxd.root.http.rxutils;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.hxd.root.http.base.BaseResponse;
import com.hxd.root.http.exception.CustomException;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/29
 * 邮 箱： wistorm@sina.com
 * <p>
 * 有关Rx的工具类
 */
public class RxUtils {
    /**
     * 生命周期绑定
     *
     * @param lifecycle Activity
     */
    public static LifecycleTransformer bindToLifecycle(@NonNull Context lifecycle) {
        if (lifecycle instanceof LifecycleProvider) {
            return ((LifecycleProvider) lifecycle).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("Context not the LifecycleProvider type");
        }
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Fragment
     */
    public static <T> LifecycleTransformer bindToLifecycle(@NonNull Fragment lifecycle) {
        if (lifecycle instanceof LifecycleProvider) {
            return ((LifecycleProvider) lifecycle).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("Fragment not the LifecycleProvider type");
        }
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Fragment
     */
    public static <T> LifecycleTransformer bindToLifecycle(@NonNull LifecycleProvider lifecycle) {
        return lifecycle.bindToLifecycle();
    }

    /**
     * 简化线程调度器
     */
    public static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static <T> ObservableTransformer exceptionTransformer() {

        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
                        .map(new ResponseFunction<T>())  //这里可以取出BaseResponse中的Result
                        .onErrorResumeNext(new ErrorResumeFunction());
            }
        };
    }

    private static class ErrorResumeFunction<T> implements Function<Throwable, Observable<? extends BaseResponse<T>>> {
        @Override
        public Observable<? extends BaseResponse<T>> apply(Throwable t) {
            return Observable.error(CustomException.handleException(t));
        }
    }

    /**
     * 服务器返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResponse<T>, T> {

        @Override
        public T apply(BaseResponse<T> tResponse) throws Exception {
            int code = tResponse.getCode();
            String message = tResponse.getMsg();
            if (tResponse.isOk()) {
                return tResponse.getResult();
            } else {
                throw new RuntimeException(!"".equals(tResponse.getCode() + "" + tResponse.getMsg()) ? tResponse.getMsg() : "");
            }
        }
    }

}
