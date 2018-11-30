package com.hxd.root.adapter.main.home;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.hxd.root.R;
import com.hxd.root.base.baseadapter.BaseRecyclerViewAdapter;
import com.hxd.root.base.baseadapter.BaseRecyclerViewHolder;
import com.hxd.root.bean.home.ArticleBean;
import com.hxd.root.databinding.ItemHomeInformationBinding;
import com.hxd.root.utils.DialogBuild;
import com.hxd.root.utils.PerfectClickListener;
import com.hxd.root.utils.ToastUtil;
import com.hxd.root.view.web.WebViewActivity;

/**
 * @author Cazaea
 * @time 2017/6/23 17:24
 * @mail wistorm@sina.com
 */

public class InformationAdapter extends BaseRecyclerViewAdapter<ArticleBean> {

    private Activity activity;

    public InformationAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_home_information);
    }

    class ViewHolder extends BaseRecyclerViewHolder<ArticleBean, ItemHomeInformationBinding> {

        ViewHolder(ViewGroup parent, int layout) {
            super(parent, layout);
        }

        @Override
        public void onBindViewHolder(final ArticleBean bean, final int position) {
            binding.setBean(bean);
            // 当数据改变时，Binding会在下一帧去改变数据
            // 如果我们需要立即刷新，调用方法立即改变数据。
            binding.executePendingBindings();
            binding.llItem.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    WebViewActivity.loadUrl(activity, bean.getLink_url(), bean.getTitle());
                }
            });
            binding.llItem.setOnLongClickListener(v -> {
                String title = "Top" + (position + 1) + ": " + bean.getTitle();
                DialogBuild.show(v, title, (dialog, which) -> ToastUtil.showShort("Hello!"));
                return false;
            });
        }
    }

}
