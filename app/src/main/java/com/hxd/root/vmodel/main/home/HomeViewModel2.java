package com.hxd.root.vmodel.main.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.hxd.root.base.BaseListViewModel;
import com.hxd.root.bean.home.HomeInfoBean;
import com.hxd.root.http.cache.Repository;

/**
 * @author Cazaea
 * @data 2018/5/7
 * @Description 首页ViewModel
 */

public class HomeViewModel2 extends BaseListViewModel {

    private Repository mRepository;
    private MutableLiveData<HomeInfoBean> mHomeLiveData;

    public HomeViewModel2() {
        this.mRepository = new Repository();
    }

    private void setHomeBean(MutableLiveData<HomeInfoBean> pHomeLiveData) {
        this.mHomeLiveData = pHomeLiveData;
    }

    public LiveData<HomeInfoBean> getHomeData() {
        if (mHomeLiveData == null
                || mHomeLiveData.getValue() == null
                || mHomeLiveData.getValue().getData() == null) {
            mHomeLiveData = new MutableLiveData<>();
            return loadHomeData();
        } else {
            return mHomeLiveData;
        }
    }

    private MutableLiveData<HomeInfoBean> loadHomeData() {
        MutableLiveData<HomeInfoBean> _homeData = mRepository.getHomeData();
        setHomeBean(_homeData);
        return _homeData;
    }

}
