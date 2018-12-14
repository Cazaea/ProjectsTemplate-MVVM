package com.hxd.root.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.hxd.root.R;
import com.hxd.root.base.baseadapter.BaseRecyclerViewAdapter;
import com.hxd.root.base.baseadapter.BaseRecyclerViewHolder;
import com.hxd.root.databinding.ItemListBinding;

/**
 * @author Cazaea
 * @time 2017/6/23 17:24
 * @mail wistorm@sina.com
 * <p>
 * Template Adapter
 */
public class Adapter extends BaseRecyclerViewAdapter<String> {

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_list);
    }

    class ViewHolder extends BaseRecyclerViewHolder<String, ItemListBinding> {

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(String object, int position) {
            binding.tvText.setText("测试:  " + object);
        }
    }
}
