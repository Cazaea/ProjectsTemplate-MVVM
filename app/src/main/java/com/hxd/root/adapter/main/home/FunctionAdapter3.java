package com.hxd.root.adapter.main.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxd.root.MainActivity;
import com.hxd.root.R;
import com.hxd.root.bean.home.ArticleBean;
import com.hxd.root.bean.home.BannerBean;
import com.hxd.root.bean.home.HeadlineBean;
import com.hxd.root.bean.home.HomeInfoBean.DataBean;
import com.hxd.root.bean.home.ModuleBean;
import com.hxd.root.databinding.HeaderItemHomeBinding;
import com.hxd.root.databinding.ItemHomeFunctionBinding;
import com.hxd.root.databinding.ItemHomeInformationBinding;
import com.hxd.root.utils.DensityUtil;
import com.hxd.root.utils.DialogBuild;
import com.hxd.root.utils.GlideImageLoader;
import com.hxd.root.utils.PerfectClickListener;
import com.hxd.root.utils.ToastUtil;
import com.hxd.root.view.web.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cazaea
 * @time 2017/6/23 17:24
 * @mail wistorm@sina.com
 */
public class FunctionAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MainActivity context;

    private static final int TYPE_HEADER_HOME = -1;
    private static final int TYPE_CONTENT_HOME = -2;
    private static final int TYPE_FOOTER_HOME = -3;

    private DataBean mDataBean;

    public FunctionAdapter3(Context context) {
        this.context = (MainActivity) context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER_HOME;
        } else if (position + 1 == getItemCount()) {
            return TYPE_FOOTER_HOME;
        } else {
            return TYPE_CONTENT_HOME;
        }
    }

    @Override
    public int getItemCount() {
        int count;
        if (mDataBean == null
                || mDataBean.getModule() == null
                || mDataBean.getModule().size() <= 0) {
            count = 0;
        } else {
            int length = mDataBean.getModule().size();
            int num = length % 3;
            if (num != 0) {
                count = (length + (3 - num));
            } else {
                count = length;
            }
        }
        return count;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER_HOME:
                HeaderItemHomeBinding mBindHeader = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.header_item_home, parent, false);
                return new HeaderViewHolder(mBindHeader.getRoot());
            case TYPE_FOOTER_HOME:
                ItemHomeInformationBinding mBindInformation = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_home_information, parent, false);
                return new FooterViewHolder(mBindInformation.getRoot());
            default:
                ItemHomeFunctionBinding mBindFunction = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_home_function, parent, false);
                return new FunctionViewHolder(mBindFunction.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            if (mDataBean != null) {
                headerViewHolder.bindItem(mDataBean);
            }
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            if (mDataBean != null && mDataBean.getArticle() != null && mDataBean.getArticle().size() > 0) {
                footerViewHolder.bindItem(mDataBean.getArticle().get(position));
            }
        } else if (holder instanceof FunctionViewHolder) {
            FunctionViewHolder functionViewHolder = (FunctionViewHolder) holder;
            if (mDataBean != null && mDataBean.getModule() != null && mDataBean.getModule().size() > 0) {
                functionViewHolder.bindItem(mDataBean.getModule().get(position));
            }
        }
    }

    /**
     * 处理 GridLayoutManager 添加头尾布局占满屏幕宽的情况
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeader(position) || isFooter(position)) ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 处理 StaggeredGridLayoutManager 添加头尾布局占满屏幕宽的情况
     */
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && (isHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    /**
     * 这里规定 position = 0 时
     * 就为头布局，设置为占满整屏幕宽
     */
    private boolean isHeader(int position) {
        return position >= 0 && position < 1;
    }

    /**
     * 这里规定 position =  getItemCount() - 1时
     * 就为尾布局，设置为占满整屏幕宽
     * getItemCount() 改了 ，这里就不用改
     */
    private boolean isFooter(int position) {
        return position < getItemCount() && position >= getItemCount() - 1;
    }

    /**
     * HttpFixedParams View
     */
    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderItemHomeBinding mBindHomeHeader;

        HeaderViewHolder(View view) {
            super(view);
            mBindHomeHeader = DataBindingUtil.getBinding(view);
        }

        private void bindItem(DataBean pBean) {
            showBannerView(mBindHomeHeader, pBean.getBanner());
            showHeaderLineView(mBindHomeHeader, pBean.getHeadline());
        }
    }

    /**
     * Content View
     */
    private class FunctionViewHolder extends RecyclerView.ViewHolder {

        ItemHomeFunctionBinding mBindHomeFunction;

        FunctionViewHolder(View view) {
            super(view);
            mBindHomeFunction = DataBindingUtil.getBinding(view);
        }

        private void bindItem(final ModuleBean function) {
            mBindHomeFunction.setBean(function);
            mBindHomeFunction.executePendingBindings();

            mBindHomeFunction.llHomeFunctionItem.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    WebViewActivity.loadUrl(view.getContext(), function.getLink_url(), function.getTitle());
                }
            });

        }
    }

    /**
     * Footer View
     */
    private class FooterViewHolder extends RecyclerView.ViewHolder {

        ItemHomeInformationBinding mBindHomeInformation;

        FooterViewHolder(View itemView) {
            super(itemView);
            mBindHomeInformation = DataBindingUtil.getBinding(itemView);
        }

        private void bindItem(ArticleBean bean) {
            mBindHomeInformation.setBean(bean);
            mBindHomeInformation.executePendingBindings();
            mBindHomeInformation.llItem.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    WebViewActivity.loadUrl(view.getContext(), bean.getLink_url(), bean.getTitle());
                }
            });
            mBindHomeInformation.llItem.setOnLongClickListener(v -> {
                String title = "Top: " + bean.getTitle();
                DialogBuild.show(v, title, (dialog, which) -> ToastUtil.showShort("Hello!"));
                return false;
            });
        }
    }


    private void showBannerView(HeaderItemHomeBinding binding, List<BannerBean> banner) {
        // 格式化控件宽高
        DensityUtil.formatViewHeight(binding.homeBanner, DensityUtil.getDisplayWidth(), (32 / 75f));
        // 外层布局格式化，添加固定高度
        DensityUtil.formatViewHeight(binding.rlBannerParent, DensityUtil.getDisplayWidth(), (32 / 75f), 20f);

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
                WebViewActivity.loadUrl(context, mBannerLinks.get(position), mBannerTitle.get(position));
            }
        });
    }

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
                WebViewActivity.loadUrl(context, mHeadLineUrls.get(position), mHeadLineTitle.get(position));
            }
        });
    }

    // 将首页数据传递过来
    public void setData(DataBean pData) {
        this.mDataBean = pData;
    }

    public void clear() {
        this.mDataBean = null;
    }

}
