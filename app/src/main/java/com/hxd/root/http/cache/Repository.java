package com.hxd.root.http.cache;

import android.arch.lifecycle.MutableLiveData;

import com.hxd.root.bean.home.HomeInfoBean;
import com.hxd.root.http.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/26
 * 邮 箱： wistorm@sina.com
 * <p>
 * 接口数据缓存
 */
public class Repository {

    /**
     * 首页数据
     */
    public MutableLiveData<HomeInfoBean> getHomeData() {
        final MutableLiveData<HomeInfoBean> data = new MutableLiveData<>();
        HttpClient.Builder.getBaseServer()
                .getHomeInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeInfoBean>() {
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
