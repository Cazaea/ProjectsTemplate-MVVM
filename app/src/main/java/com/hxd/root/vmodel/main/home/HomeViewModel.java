package com.hxd.root.vmodel.main.home;

import android.support.v4.widget.SwipeRefreshLayout;

import com.hxd.root.base.BaseFragment;
import com.hxd.root.base.BaseListViewModel;
import com.hxd.root.bean.home.ArticleBean;
import com.hxd.root.bean.home.BannerBean;
import com.hxd.root.bean.home.HeadlineBean;
import com.hxd.root.bean.home.HomeInfoBean;
import com.hxd.root.bean.home.ModuleBean;
import com.hxd.root.http.HttpClient;
import com.hxd.root.http.rxutils.RxUtils;
import com.hxd.root.utils.ToastUtil;
import com.hxd.root.vmodel.main.home.HomeNavigator.BannerNavigator;
import com.hxd.root.vmodel.main.home.HomeNavigator.FunctionNavigator;
import com.hxd.root.vmodel.main.home.HomeNavigator.HeadLineNavigator;
import com.hxd.root.vmodel.main.home.HomeNavigator.InformationNavigator;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Cazaea
 * @data 2018/5/7
 * @Description 首页ViewModel
 */

public class HomeViewModel extends BaseListViewModel {

    private BaseFragment activity;

    private BannerNavigator mBannerNavigator;
    private HeadLineNavigator mHeadLineNavigator;
    private FunctionNavigator mFunctionNavigator;
    private InformationNavigator mInformationNavigator;

    private ArrayList<String> mBannerTitle;
    private ArrayList<String> mBannerImages;

    private ArrayList<String> mHeadLineTitle;
    private ArrayList<String> mHeadLineUrls;

    public HomeViewModel(BaseFragment activity) {
        this.activity = activity;
    }

    public void setBannerNavigator(BannerNavigator mBannerNavigator) {
        this.mBannerNavigator = mBannerNavigator;
    }

    public void setHeadLineNavigator(HeadLineNavigator mHeadLineNavigator) {
        this.mHeadLineNavigator = mHeadLineNavigator;
    }

    public void setFunctionNavigator(FunctionNavigator mFunctionNavigator) {
        this.mFunctionNavigator = mFunctionNavigator;
    }

    public void setInformationNavigator(InformationNavigator mInformationNavigator) {
        this.mInformationNavigator = mInformationNavigator;
    }

    /**
     * 获取数据
     */
    public void getHomeData(SwipeRefreshLayout srl) {

        HttpClient.Builder.getBaseServer()
                .getHomeInfo()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(pDisposable -> {
                    ToastUtil.showShort("正在加载");
                })
                .subscribe(new Observer<HomeInfoBean>() {
                    @Override
                    public void onComplete() {
                        closeRefresh(srl);
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeRefresh(srl);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeInfoBean homeInfo) {
                        if (homeInfo != null && homeInfo.getData() != null) {
                            HomeInfoBean.DataBean dataBean = homeInfo.getData();
                            // 轮播图
                            initBanner(dataBean.getBanner());
                            // 头条数据
                            initHeadLine(dataBean.getHeadline());
                            // 导航模块
                            initFunction(dataBean.getModule());
                            // 咨询信息
                            initInformation(dataBean.getArticle());
                        }
                    }
                });

    }

    private void closeRefresh(SwipeRefreshLayout srl) {
        if (srl != null && srl.isRefreshing())
            new android.os.Handler().postDelayed(() -> srl.setRefreshing(false), 1000);
    }

    private void initBanner(List<BannerBean> bannerInfo) {
        if (mBannerTitle == null) {
            mBannerTitle = new ArrayList<String>();
        } else {
            mBannerTitle.clear();
        }
        if (mBannerImages == null) {
            mBannerImages = new ArrayList<String>();
        } else {
            mBannerImages.clear();
        }

        if (bannerInfo != null && bannerInfo.size() > 0) {
            for (int i = 0; i < bannerInfo.size(); i++) {
                mBannerTitle.add(bannerInfo.get(i).getTitle());
                mBannerImages.add(bannerInfo.get(i).getPic_path());
            }
            mBannerNavigator.showBannerView(mBannerTitle, mBannerImages, bannerInfo);
        } else {
            mBannerNavigator.loadBannerFailure();
        }

    }

    private void initHeadLine(List<HeadlineBean> headLineInfo) {
        if (mHeadLineTitle == null) {
            mHeadLineTitle = new ArrayList<>();
        } else {
            mHeadLineTitle.clear();
        }
        if (mHeadLineUrls == null) {
            mHeadLineUrls = new ArrayList<String>();
        } else {
            mHeadLineUrls.clear();
        }

        if (headLineInfo != null && headLineInfo.size() > 0) {
            for (int i = 0; i < headLineInfo.size(); i++) {
                mHeadLineTitle.add(headLineInfo.get(i).getTitle());
                mHeadLineUrls.add(headLineInfo.get(i).getLink_url());
            }
            mHeadLineNavigator.showHeadLineView(mHeadLineTitle, mHeadLineUrls, headLineInfo);
        } else {
            mHeadLineNavigator.loadHeadLineFailure();
        }
    }

    private void initFunction(List<ModuleBean> functionInfo) {
        if (functionInfo != null && functionInfo.size() > 0) {
            mFunctionNavigator.showFunctionView(functionInfo);
        } else {
            mFunctionNavigator.loadFunctionFailure();
        }

    }

    private void initInformation(List<ArticleBean> informationInfo) {
        if (informationInfo != null && informationInfo.size() > 0) {
            mInformationNavigator.showInformationView(informationInfo);
        } else {
            mInformationNavigator.loadInformationFailure();
        }
    }

}
