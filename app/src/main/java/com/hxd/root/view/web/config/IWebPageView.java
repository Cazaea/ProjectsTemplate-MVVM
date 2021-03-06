package com.hxd.root.view.web.config;

import android.view.View;

/**
 * Created by Cazaea on 2016/11/17.
 */
public interface IWebPageView {

    /**
     * 隐藏进度条
     */
    void hindProgressBar();

    /**
     * 显示WebView
     */
    void showWebView();

    /**
     * 隐藏WebView
     */
    void hindWebView();

    /**
     * 进度条变化时调用
     */
    void startProgress(int newProgress);

    /**
     * 添加js监听
     */
    void addImageClickListener();

    /**
     * 播放网络视频全屏调用
     */
    void fullViewAddView(View view);

    void showVideoFullView();

    void hindVideoFullView();


}
