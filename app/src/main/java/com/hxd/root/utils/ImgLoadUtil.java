package com.hxd.root.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hxd.root.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Cazaea on 2016/11/26.
 */

public class ImgLoadUtil {

    private static ImgLoadUtil instance;

    private ImgLoadUtil() {
    }

    public static ImgLoadUtil getInstance() {
        if (instance == null) {
            instance = new ImgLoadUtil();
        }
        return instance;
    }

    /**
     * 显示随机的图片(每日推荐)
     *
     * @param imgNumber 有几张图片要显示,对应默认图
     * @param imageUrl  显示图片的url
     * @param imageView 对应图片控件
     */
    public static void displayRandom(int imgNumber, String imageUrl, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(RequestOptions.placeholderOf(getMusicDefaultPic(imgNumber))
                        .error(getMusicDefaultPic(imgNumber)))
                .transition(new DrawableTransitionOptions().crossFade(1500))
                .into(imageView);
    }

    private static int getMusicDefaultPic(int imgNumber) {
        switch (imgNumber) {
            case 1:
                return R.drawable.img_two_bi_one;
            case 2:
                return R.drawable.img_four_bi_three;
            case 3:
                return R.drawable.img_one_bi_one;
            default:
                break;
        }
        return R.drawable.img_four_bi_three;
    }

//--------------------------------------

    /**
     * 用于干货item，将gif图转换为静态图
     */
    public static void displayGif(String url, ImageView imageView) {

        Glide.with(imageView.getContext())
                .asBitmap()
                .load(url)
                .apply(RequestOptions.placeholderOf(R.drawable.img_one_bi_one)
                        .error(R.drawable.img_one_bi_one)
//                        .skipMemoryCache(true)                              //跳过内存缓存
//                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)      // 缓存图片源文件（解决加载gif内存溢出问题）
                )
//                .transition(new DrawableTransitionOptions().crossFade(1000))
//                .into(new GlideDrawableImageViewTarget(imageView, 1));
                .into(imageView);
    }

    /**
     * 书籍、妹子图、电影列表图
     * 默认图区别
     */
    public static void displayEspImage(String url, ImageView imageView, int type) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(RequestOptions
                        .placeholderOf(getDefaultPic(type))
                        .error(getDefaultPic(type)))
                .into(imageView);
    }

    /**
     * 占位图区别
     */
    private static int getDefaultPic(int type) {
        switch (type) {
            case 0:// 电影
                return R.drawable.img_default_movie;
            case 1:// 妹子
                return R.drawable.img_default_meizi;
            case 2:// 书籍
                return R.drawable.img_default_book;
        }
        return R.drawable.img_default_meizi;
    }

    /**
     * 显示高斯模糊效果（电影详情页）
     */
    private static void displayGaussian(Context context, String url, ImageView imageView) {
        /**
         * "23":模糊度；
         * "4":图片缩放4倍后再进行模糊
         */
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(23, 4))
                        .placeholder(R.drawable.stackblur_default)
                        .error(R.drawable.stackblur_default))
                .transition(new DrawableTransitionOptions().crossFade(500))
                .into(imageView);
    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    @BindingAdapter("android:displayCircle")
    public static void displayCircle(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new GlideCircleTransform())
                        .error(R.drawable.ic_avatar_default))
                .transition(new DrawableTransitionOptions().crossFade(500))
                .into(imageView);
    }

    /**
     * 妹子，电影列表图
     *
     * @param defaultPicType 电影：0；妹子：1； 书籍：2
     */
    @BindingAdapter({"android:displayFadeImage", "android:defaultPicType"})
    public static void displayFadeImage(ImageView imageView, String url, int defaultPicType) {
        displayEspImage(url, imageView, defaultPicType);
    }

    /**
     * 电影详情页显示电影图片(等待被替换)（测试的还在，已可以弃用）
     * 没有加载中的图
     */
    @BindingAdapter("android:showImg")
    public static void showImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.errorOf(getDefaultPic(0)))
                .transition(new DrawableTransitionOptions().crossFade(500))
                .into(imageView);
    }

    /**
     * 电影列表图片
     */
    @BindingAdapter("android:showMovieImg")
    public static void showMovieImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(RequestOptions
                        .overrideOf((int) CommonUtils.getDimens(R.dimen.movie_detail_width), (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
                        .placeholder(getDefaultPic(0)).error(getDefaultPic(0)))
                .into(imageView);
    }

    /**
     * 首页Function列表图片
     */
    @BindingAdapter("android:showFunctionImg")
    public static void showFunctionImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(RequestOptions.overrideOf((int) CommonUtils.getDimens(R.dimen.function_detail_width), (int) CommonUtils.getDimens(R.dimen.function_detail_height))
                        .placeholder(getDefaultPic(2))
                        .error(getDefaultPic(2)))
                .into(imageView);
    }

    /**
     * 首页Information列表图片
     */
    @BindingAdapter("android:showInformationImg")
    public static void showInformationImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(RequestOptions
                        .placeholderOf(getDefaultPic(0))
                        .error(getDefaultPic(0)))
                .into(imageView);
    }

    /**
     * 电影详情页显示高斯背景图
     */
    @BindingAdapter("android:showImgBg")
    public static void showImgBg(ImageView imageView, String url) {
        displayGaussian(imageView.getContext(), url, imageView);
    }


    /**
     * 热门电影头部图片
     */
    @BindingAdapter({"android:displayRandom", "android:imgType"})
    public static void displayRandom(ImageView imageView, String imageUrl, int imgType) {
        displayRandom(imgType, imageUrl, imageView);
    }
}
