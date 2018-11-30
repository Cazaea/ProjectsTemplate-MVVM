package com.hxd.root.http.rxhttp.utils;

import android.arch.lifecycle.MutableLiveData;

import com.hxd.root.bean.home.HomeInfoBean;
import com.hxd.root.http.HttpClient;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/26
 * 邮 箱： wistorm@sina.com
 * <p>
 * 接口数据缓存
 */
public class Repository {

    public MutableLiveData<HomeInfoBean> getCacheData() {
        final MutableLiveData<HomeInfoBean> data = new MutableLiveData<>();
        HttpClient.Builder.getBaseServer()
                .getHomeInfo()
                .compose(RxUtils.schedulersTransformer())
                .subscribe(new Observer<HomeInfoBean> () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeInfoBean cacheData) {
                        if (cacheData != null) {
                            data.setValue(cacheData);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }

}
