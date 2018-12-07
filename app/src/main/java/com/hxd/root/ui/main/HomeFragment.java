package com.hxd.root.ui.main;

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
import com.hxd.root.adapter.main.home.FunctionAdapter;
import com.hxd.root.adapter.main.home.InformationAdapter;
import com.hxd.root.base.BaseFragment;
import com.hxd.root.bean.home.ArticleBean;
import com.hxd.root.bean.home.BannerBean;
import com.hxd.root.bean.home.HeadlineBean;
import com.hxd.root.bean.home.ModuleBean;
import com.hxd.root.databinding.FooterItemHomeBinding;
import com.hxd.root.databinding.FragmentHomeBinding;
import com.hxd.root.databinding.HeaderItemHomeBinding;
import com.hxd.root.utils.CommonUtils;
import com.hxd.root.utils.DensityUtil;
import com.hxd.root.utils.GlideImageLoader;
import com.hxd.root.view.web.WebViewActivity;
import com.hxd.root.vmodel.main.home.HomeNavigator.BannerNavigator;
import com.hxd.root.vmodel.main.home.HomeNavigator.FunctionNavigator;
import com.hxd.root.vmodel.main.home.HomeNavigator.HeadLineNavigator;
import com.hxd.root.vmodel.main.home.HomeNavigator.InformationNavigator;
import com.hxd.root.vmodel.main.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cazaea
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements BannerNavigator, HeadLineNavigator, FunctionNavigator, InformationNavigator {


    private MainActivity activity;
    private HomeViewModel mHomeViewModel;

    private HeaderItemHomeBinding mHeaderBinding;
    private FooterItemHomeBinding mFooterBinding;

    private FunctionAdapter mFunctionAdapter;
    private InformationAdapter mInformationAdapter;

    // 初始化完成后加载数据
    private boolean isPrepared = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();

        mHomeViewModel = new HomeViewModel(this);
        mHomeViewModel.setBannerNavigator(this);
        mHomeViewModel.setHeadLineNavigator(this);
        mHomeViewModel.setFunctionNavigator(this);
        mHomeViewModel.setInformationNavigator(this);

        initGridRecyclerView();

        // 准备就绪
        isPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_home;
    }

    /**
     * 初始化Function数据列表
     */
    private void initGridRecyclerView() {
        // 下拉刷新
        bindingView.srlHome.setColorSchemeColors(CommonUtils.getColor(R.color.colorDefaultTheme));
        bindingView.srlHome.setOnRefreshListener(this::loadData);
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
        mFunctionAdapter = new FunctionAdapter(activity);
        bindingView.xrvFunction.setAdapter(mFunctionAdapter);
    }

    /**
     * 资讯列表初始化
     */
    private void initRecyclerView() {
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
        postDelayLoad();
    }

    @Override
    protected void onRefresh() {
        bindingView.srlHome.setRefreshing(true);
        postDelayLoad();
    }

    /**
     * 延迟执行，避免卡顿
     * 加同步锁，避免重复加载
     */
    private void postDelayLoad(){
        synchronized (this) {
                bindingView.srlHome.postDelayed(() -> mHomeViewModel.getHomeData(bindingView.srlHome), 1000);
        }
    }

    /**
     * 显示Banner图片
     *
     * @param bannerTitle  图片标题
     * @param bannerImages 图片链接集合
     * @param result       全部数据
     */
    @Override
    public void showBannerView(ArrayList<String> bannerTitle, ArrayList<String> bannerImages, List<BannerBean> result) {
        // 格式化控件宽高
        DensityUtil.formatViewHeight(mHeaderBinding.homeBanner, DensityUtil.getDisplayWidth(), (32 / 75f));
        // 外层布局格式化，添加固定高度
        DensityUtil.formatViewHeight(mHeaderBinding.rlBannerParent, DensityUtil.getDisplayWidth(), (32 / 75f), 20f);

        mHeaderBinding.homeBanner.setVisibility(View.VISIBLE);
        mHeaderBinding.homeBanner.setBannerTitles(bannerTitle);
        mHeaderBinding.homeBanner.setImages(bannerImages).setImageLoader(new GlideImageLoader()).start();
        mHeaderBinding.homeBanner.setOnBannerListener(position -> {
            if (result.get(position) != null && !TextUtils.isEmpty(result.get(position).getLink_url())) {
                WebViewActivity.loadUrl(getContext(), result.get(position).getLink_url(), result.get(position).getTitle());
            }
        });

    }

    /**
     * 加载banner图失败
     */
    @Override
    public void loadBannerFailure() {
        mHeaderBinding.homeBanner.setVisibility(View.GONE);
    }

    /**
     * 显示HeadLine
     *
     * @param headLineTitle 头条标题
     * @param headLineUrls  头条链接
     * @param result        全部数据
     */
    @Override
    public void showHeadLineView(ArrayList<String> headLineTitle, ArrayList<String> headLineUrls, List<HeadlineBean> result) {
        // 头条数据获取
        mHeaderBinding.homeFlipper.startMarqueeWithList(headLineTitle);
        // 点击跳转事件
        mHeaderBinding.homeFlipper.setMarqueeViewItemClickListener(position -> {
            if (result.get(position) != null && !TextUtils.isEmpty(result.get(position).getLink_url())) {
                WebViewActivity.loadUrl(getContext(), headLineUrls.get(position), result.get(position).getTitle());
            }
        });
    }

    /**
     * 加载HeadLine失败
     */
    @Override
    public void loadHeadLineFailure() {
        mHeaderBinding.homeFlipper.setVisibility(View.GONE);
    }

    /**
     * 显示Function
     *
     * @param result 全部数据
     */
    @Override
    public void showFunctionView(List<ModuleBean> result) {
        mFunctionAdapter.setList(result);
        mFunctionAdapter.notifyDataSetChanged();
    }

    /**
     * 加载Function失败
     */
    @Override
    public void loadFunctionFailure() {
        bindingView.xrvFunction.setVisibility(View.GONE);
    }

    /**
     * 显示Information列表
     *
     * @param informationInfo 文章数据
     */
    @Override
    public void showInformationView(List<ArticleBean> informationInfo) {
        initRecyclerView();
        mInformationAdapter.clear();
        mInformationAdapter.addAll(informationInfo);
        mInformationAdapter.notifyDataSetChanged();
    }

    /**
     * 加载Information列表失败
     */
    @Override
    public void loadInformationFailure() {
        mFooterBinding.xrvInformation.setVisibility(View.GONE);
    }

}