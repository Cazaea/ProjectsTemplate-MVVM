package com.hxd.root.adapter.main.home;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.hxd.root.R;
import com.hxd.root.base.baseadapter.BaseRecyclerViewAdapter;
import com.hxd.root.base.baseadapter.BaseRecyclerViewHolder;
import com.hxd.root.bean.home.ModuleBean;
import com.hxd.root.databinding.ItemHomeFunctionBinding;
import com.hxd.root.utils.PerfectClickListener;
import com.hxd.root.view.web.WebViewActivity;

/**
 * @author Cazaea
 * @time 2017/6/23 17:24
 * @mail wistorm@sina.com
 *
 * 方案二：根据BaseRecyclerViewAdapter实现
 */
public class FunctionAdapter2 extends BaseRecyclerViewAdapter<ModuleBean> {

    private Activity context;

    public FunctionAdapter2(Context context) {
        this.context = (Activity) context;
    }

    @Override
    public int getItemCount() {
        int count;
        if (data == null || data.size() == 0) {
            count = 0;
        } else {
            int length = data.size();
            int num = length % 3;
            if (num != 0) {
                count = (length + (3 - num));
            } else {
                count = length;
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FunctionViewHolder(parent, R.layout.item_home_function);
    }

    /**
     * Content View
     */
    private class FunctionViewHolder extends BaseRecyclerViewHolder<ModuleBean, ItemHomeFunctionBinding> {

        public FunctionViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(ModuleBean bean, int position) {
            binding.setBean(bean);
            binding.executePendingBindings();
            binding.llHomeFunctionItem.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    WebViewActivity.loadUrl(view.getContext(), bean.getLink_url(), bean.getTitle());
                }
            });
        }
    }
}
