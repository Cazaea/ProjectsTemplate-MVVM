package com.hxd.root.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.hxd.root.MainActivity;
import com.hxd.root.R;
import com.hxd.root.adapter.main.home.FunctionAdapter2;
import com.hxd.root.adapter.main.home.InformationAdapter;
import com.hxd.root.app.Constants;
import com.hxd.root.base.BaseFragment;
import com.hxd.root.bean.home.BannerBean;
import com.hxd.root.bean.home.HeadlineBean;
import com.hxd.root.bean.home.HomeInfoBean;
import com.hxd.root.databinding.FooterItemHomeBinding;
import com.hxd.root.databinding.FragmentHome2Binding;
import com.hxd.root.databinding.HeaderItemHomeBinding;
import com.hxd.root.http.cache.ACache;
import com.hxd.root.utils.DensityUtil;
import com.hxd.root.utils.GlideImageLoader;
import com.hxd.root.utils.SPUtils;
import com.hxd.root.utils.TimeUtil;
import com.hxd.root.view.web.WebViewActivity;
import com.hxd.root.vmodel.main.home.HomeViewModel2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cazaea
 */
public class HomeFragment2 extends BaseFragment<FragmentHome2Binding> {

    private ACache aCache;
    private MainActivity activity;
    private HomeInfoBean mHomeInfoBean;
    private HomeViewModel2 mHomeViewModel2;

    // 初始化完成后加载数据
    private boolean isPrepared = false;
    // 第一次显示时加载数据，第二次不显示
    private boolean isFirst = true;
    // 是否正在刷新（用于刷新数据时返回页面不再刷新）
    private boolean mIsLoading = false;

    private HeaderItemHomeBinding mHeaderBinding;
    private FooterItemHomeBinding mFooterBinding;

    private FunctionAdapter2 mFunctionAdapter;
    private InformationAdapter mInformationAdapter;

    @Override
    public int setContent() {
        return R.layout.fragment_home2;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();

        initGridRecyclerView();
        initFooterRecyclerView();

        // 获取缓存
        aCache = ACache.get(getActivity());
        mHomeInfoBean = (HomeInfoBean) aCache.getAsObject(Constants.CACHE_HOME_INFO);
        mHomeViewModel2 = ViewModelProviders.of(this).get(HomeViewModel2.class);

        // 准备就绪
        isPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    /**
     * 懒加载
     * 从此页面新开Activity界面返回此页面 不会走这里
     */
    @Override
    protected void loadData() {
        if (!isPrepared || !mIsVisible) {
            return;
        }

        // 显示，准备完毕，不是当天，则请求数据（正在请求时避免再次请求）
        String dataDate = SPUtils.getString("home_data", "2018-11-25");
        if (!dataDate.equals(TimeUtil.getCurrentDate()) && !mIsLoading) {
            showLoading();
            // 延迟执行防止卡顿
            postDelayLoad();
        } else {
            // 正在刷新时不执行这部分
            if (mIsLoading || !isFirst) {
                return;
            }
            // 显示加载中
            showLoading();
            // 请求数据/数据缓存
            if (mHomeInfoBean == null && !mIsLoading) {
                postDelayLoad();
            } else {
                bindingView.xrvFunction.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (this) {
                            setAdapter(mHomeInfoBean);
                            showContentView();
                        }
                    }
                }, 150);
            }
        }

    }

    private void loadHomeData() {
        mHomeViewModel2.getHomeData().observe(this, pHomeInfoBean -> {
            showContentView();
            if (pHomeInfoBean != null && pHomeInfoBean.getData() != null) {
                setAdapter(pHomeInfoBean);
                // 刷新缓存数据
                aCache.remove(Constants.CACHE_HOME_INFO);
                aCache.put(Constants.CACHE_HOME_INFO, pHomeInfoBean);
                // 保存请求的日期
                SPUtils.putString("home_data", TimeUtil.getCurrentDate());
                // 刷新结束
                mIsLoading = false;
            } else {
                if (pHomeInfoBean != null) {
                    setAdapter(pHomeInfoBean);
                } else {
                    if (mFunctionAdapter.getItemCount() == 0) {
                        showError();
                    }
                }
            }
        });
    }

    /**
     * 初始化Function数据列表
     */
    private void initGridRecyclerView() {
        // 设置类型为GridView
        bindingView.xrvFunction.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        // 加上这两行代码，下拉出提示才不会产生出现刷新头的bug，不加拉不下来
        bindingView.xrvFunction.setPullRefreshEnabled(false);
        bindingView.xrvFunction.clearHeader();
        bindingView.xrvFunction.setLoadingMoreEnabled(false);
        // 需加，不然滑动不流畅
        bindingView.xrvFunction.setNestedScrollingEnabled(false);
        bindingView.xrvFunction.setHasFixedSize(false);
        // 添加头文件
        mHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_item_home, null, false);
        bindingView.xrvFunction.addHeaderView(mHeaderBinding.getRoot());
        // 添加尾文件
        mFooterBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.footer_item_home, null, false);
        bindingView.xrvFunction.addFootView(mFooterBinding.getRoot(), true);
        bindingView.xrvFunction.noMoreLoading();
        // 适配数据
        mFunctionAdapter = new FunctionAdapter2(activity);
        bindingView.xrvFunction.setAdapter(mFunctionAdapter);
    }

    /**
     * 资讯列表初始化
     */
    private void initFooterRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFooterBinding.xrvInformation.setLayoutManager(mLayoutManager);
        // 加上这两行代码，下拉出提示才不会产生出现刷新头的bug，不加拉不下来
        mFooterBinding.xrvInformation.setPullRefreshEnabled(false);
        mFooterBinding.xrvInformation.clearHeader();
        mFooterBinding.xrvInformation.setLoadingMoreEnabled(false);
        // 需加，不然滑动不流畅
        mFooterBinding.xrvInformation.setNestedScrollingEnabled(false);
        mFooterBinding.xrvInformation.setHasFixedSize(false);
        // 适配数据
        mInformationAdapter = new InformationAdapter(activity);
        mFooterBinding.xrvInformation.setAdapter(mInformationAdapter);
        // 禁止抢占焦点
        mFooterBinding.xrvInformation.setFocusable(false);
    }


    @Override
    protected void onRefresh() {
        loadHomeData();
    }

    /**
     * 延迟执行，避免卡顿
     * 加同步锁，避免重复加载
     */
    private void postDelayLoad() {
        synchronized (this) {
            if (!mIsLoading) {
                mIsLoading = true;
                bindingView.xrvFunction.postDelayed(this::loadHomeData, 150);
            }
        }
    }

    /**
     * 通过缓存数据设置适配器
     */
    private void setAdapter(HomeInfoBean pHomeInfoBean) {
        // 直接传递首页数据
        mFunctionAdapter.clear();
        mFunctionAdapter.addAll(pHomeInfoBean.getData().getModule());
        mFunctionAdapter.notifyDataSetChanged();

        mInformationAdapter.clear();
        mInformationAdapter.addAll(pHomeInfoBean.getData().getArticle());
        mInformationAdapter.notifyDataSetChanged();

        showBannerView(mHeaderBinding, pHomeInfoBean.getData().getBanner());
        showHeaderLineView(mHeaderBinding, pHomeInfoBean.getData().getHeadline());

        isFirst = false;
    }

    /**
     * 展示轮播图
     */
    private void showBannerView(HeaderItemHomeBinding binding, List<BannerBean> banner) {
        // 格式化控件宽高
        DensityUtil.formatHeight(binding.homeBanner, DensityUtil.getDisplayWidth(), (32 / 75f));
        // 外层布局格式化，添加固定高度
        DensityUtil.formatHeight(binding.rlBannerParent, DensityUtil.getDisplayWidth(), (32 / 75f), 20f);

        List<String> mBannerTitle = new ArrayList<>();
        List<String> mBannerImages = new ArrayList<>();
        List<String> mBannerLinks = new ArrayList<>();

        if (banner != null && banner.size() != 0) {
            mBannerTitle.clear();
            mBannerImages.clear();
            mBannerLinks.clear();
            for (BannerBean bannerBean : banner) {
                mBannerTitle.add(bannerBean.getTitle());
                mBannerImages.add(bannerBean.getPic_path());
                mBannerLinks.add(bannerBean.getLink_url());
            }
        }

        binding.homeBanner.setVisibility(View.VISIBLE);
        binding.homeBanner.setBannerTitles(mBannerTitle);
        binding.homeBanner.setImages(mBannerImages).setImageLoader(new GlideImageLoader()).start();
        binding.homeBanner.setOnBannerListener(position -> {
            if (!TextUtils.isEmpty(mBannerLinks.get(position))) {
                WebViewActivity.loadUrl(activity, mBannerLinks.get(position), mBannerTitle.get(position));
            }
        });
    }

    /**
     * 展示头条数据
     */
    private void showHeaderLineView(HeaderItemHomeBinding binding, List<HeadlineBean> headLine) {

        List<String> mHeadLineTitle = new ArrayList<>();
        List<String> mHeadLineUrls = new ArrayList<>();

        if (headLine != null && headLine.size() != 0) {
            mHeadLineTitle.clear();
            mHeadLineUrls.clear();
            for (HeadlineBean headLineBean : headLine) {
                mHeadLineTitle.add(headLineBean.getTitle());
                mHeadLineUrls.add(headLineBean.getLink_url());
            }
        }
        // 头条数据获取
        binding.homeFlipper.startMarqueeWithList(mHeadLineTitle);
        // 点击跳转事件
        binding.homeFlipper.setMarqueeViewItemClickListener(position -> {
            if (!TextUtils.isEmpty(mHeadLineUrls.get(position))) {
                WebViewActivity.loadUrl(activity, mHeadLineUrls.get(position), mHeadLineTitle.get(position));
            }
        });
    }

}