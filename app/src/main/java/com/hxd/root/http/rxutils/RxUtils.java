package com.hxd.root.http.rxutils;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.hxd.root.http.base.BaseResponse;
import com.hxd.root.http.exception.ApiException;
import com.hxd.root.http.exception.ResponseThrowable;
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

    //=====================================================================================
    //======================================生命周期绑定=======================================
    //=====================================================================================

    /**
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
     * @param lifecycle Fragment
     */
    public static LifecycleTransformer bindToLifecycle(@NonNull Fragment lifecycle) {
        if (lifecycle instanceof LifecycleProvider) {
            return ((LifecycleProvider) lifecycle).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("Fragment not the LifecycleProvider type");
        }
    }

    /**
     * @param lifecycle LifecycleProvider
     */
    public static LifecycleTransformer bindToLifecycle(@NonNull LifecycleProvider lifecycle) {
        return lifecycle.bindToLifecycle();
    }

    //=====================================================================================
    //======================================线程调度==========================================
    //=====================================================================================

    /**
     * 简化线程调度器
     */
    public static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //=====================================================================================
    //======================================异常捕获==========================================
    //=====================================================================================

    /**
     * 网络错误的异常转换
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> exceptionTransformer() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<T>());
    }

    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends BaseResponse<T>>> {

        @Override
        public ObservableSource<? extends BaseResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(ApiException.handleException(throwable));
        }
    }

    /**
     * 服务器返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     */
    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseResponse<T> tResponse) throws Exception {
            int code = tResponse.getCode();
            String message = tResponse.getMsg();
            if (tResponse.isSuccess()) {
                return Observable.just(tResponse.getData());
            } else {
                return Observable.error(new ResponseThrowable(code, message));
            }
        }
    }

}
