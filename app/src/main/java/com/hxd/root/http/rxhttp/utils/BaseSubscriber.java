package com.hxd.root.http.rxhttp.utils;

import android.content.Context;
import android.widget.Toast;

import com.hxd.root.utils.DebugUtil;
import com.hxd.root.utils.ToastUtil;

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
    public void onError(Throwable e) {
        // TODO something error
//        DebugUtil.error(e.getMessage());
        if (e instanceof ResponseThrowable) {
            onError((ResponseThrowable) e);
        } else {
            onError(new ResponseThrowable(e, CustomException.ERROR.UNKNOWN));
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // TODO some common as show loading  and check netWork is NetworkAvailable
        // if NetworkAvailable no ! Must to call onComplete
        if (!NetworkUtil.isNetworkConnected(context)) {
            onError(new ResponseThrowable(CustomException.ERROR.NETWORK_ERROR,"网络连接异常，请检查你的网络！"));
//            onComplete();
        }
    }

    @Override
    public void onComplete() {
        // TODO some common as  dismiss loading
//        ToastUtil.showShort("http is Complete");

    }

    public abstract void onError(ResponseThrowable e);

    @Override
    public void onNext(Object o) {
        BaseResponse baseResponse = (BaseResponse) o;
        if (baseResponse.getCode() == 1000) {
            onResult((T) baseResponse.getResult());
        }/* else if (baseResponse.getCode() == 330) {
            DebugUtil.error(baseResponse.getMsg());
        } else if (baseResponse.getCode() == 503) {
            DebugUtil.error(baseResponse.getMsg());
        } else {
            ToastUtil.showShort("操作失败！错误代码:" + baseResponse.getCode());
        }*/
    }

    public abstract void onResult(T t);

}
