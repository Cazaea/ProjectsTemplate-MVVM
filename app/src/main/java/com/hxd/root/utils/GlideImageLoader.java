package com.hxd.root.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

import com.hxd.root.R;

/**
 * Created by Cazaea on 2016/11/30.
 * 首页轮播图
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(RequestOptions.placeholderOf(R.drawable.img_two_bi_one)
                        .error(R.drawable.img_two_bi_one))
                .transition(new DrawableTransitionOptions().crossFade(1000))
                .into(imageView);
    }
}
