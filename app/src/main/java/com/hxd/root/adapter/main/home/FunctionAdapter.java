package com.hxd.root.adapter.main.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxd.root.MainActivity;
import com.hxd.root.R;
import com.hxd.root.bean.home.ModuleBean;
import com.hxd.root.databinding.FooterItemHomeBinding;
import com.hxd.root.databinding.HeaderItemHomeBinding;
import com.hxd.root.databinding.ItemHomeFunctionBinding;
import com.hxd.root.utils.PerfectClickListener;
import com.hxd.root.view.web.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cazaea
 * @time 2017/6/23 17:24
 * @mail wistorm@sina.com
 *
 * 方案一：已弃用
 */
public class FunctionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MainActivity context;

    private static final int TYPE_HEADER_HOME = -1;
    private static final int TYPE_CONTENT_HOME = -2;
    private static final int TYPE_FOOTER_HOME = -3;

    private List<ModuleBean> function;

    public FunctionAdapter(Context context) {
        this.context = (MainActivity) context;
        function = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        int count;
        if (function == null) {
            count = 0;
        } else {
            int length = function.size();
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
                FooterItemHomeBinding mBindFooter = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.footer_item_home, parent, false);
                return new FooterViewHolder(mBindFooter.getRoot());
            default:
                ItemHomeFunctionBinding mBindFunction = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_home_function, parent, false);
                return new FunctionViewHolder(mBindFunction.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.bindItem();
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.bindItem();
        } else if (holder instanceof FunctionViewHolder) {
            FunctionViewHolder functionViewHolder = (FunctionViewHolder) holder;
            if (function != null && function.size() > 0) {
                functionViewHolder.bindItem(function.get(position));
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

        private void bindItem() {
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

        FooterItemHomeBinding mBindHomeFooter;

        FooterViewHolder(View itemView) {
            super(itemView);
            mBindHomeFooter = DataBindingUtil.getBinding(itemView);
//            Objects.requireNonNull(mBindHomeFooter).xrvInformation.setGravity(Gravity.CENTER);
//            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dipToPx(context, 40));
//            itemView.setLayoutParams(params);
        }

        private void bindItem() {
        }
    }

    public void setList(List<ModuleBean> function) {
        this.function.clear();
        this.function = function;
    }

    public void addAll(List<ModuleBean> data) {
        this.function.addAll(data);
    }

    public void add(ModuleBean object) {
        function.add(object);
    }

    public void clear() {
        function.clear();
    }

}
