package com.hxd.root.vmodel.main.home;

import java.util.ArrayList;
import java.util.List;

import com.hxd.root.bean.home.BannerBean;
import com.hxd.root.bean.home.HeadlineBean;
import com.hxd.root.bean.home.ModuleBean;
import com.hxd.root.bean.home.ArticleBean;

/**
 * @author Cazaea
 * @data 2018/2/8
 * @Description
 */

public interface HomeNavigator {

    /**
     * 首页Banner部分
     */
    interface BannerNavigator {

        /**
         * 显示Banner图片
         *
         * @param bannerTitle  图片对应标题
         * @param bannerImages 图片链接集合
         * @param result       全部数据
         */
        void showBannerView(ArrayList<String> bannerTitle, ArrayList<String> bannerImages, List<BannerBean> result);

        /**
         * 加载banner图失败
         */
        void loadBannerFailure();
    }

    /**
     * 首页HeadLine部分
     */
    interface HeadLineNavigator {
        /**
         * 显示HeadLine
         *
         * @param result 全部数据
         */
        void showHeadLineView(ArrayList<String> headLineTitle, ArrayList<String> headLineUrls, List<HeadlineBean> result);

        /**
         * 加载Function失败
         */
        void loadHeadLineFailure();
    }

    /**
     * 首页Function部分
     */
    interface FunctionNavigator {
        /**
         * 显示Function
         *
         * @param result 全部数据
         */
        void showFunctionView(List<ModuleBean> result);

        /**
         * 加载Function失败
         */
        void loadFunctionFailure();
    }

    /**
     * 首页Information部分
     */
    interface InformationNavigator {
        /**
         * 加载Information列表失败
         */
        void loadInformationFailure();

        /**
         * 显示Information列表
         *
         * @param informationInfo 文章数据
         */
        void showInformationView(List<ArticleBean> informationInfo);

    }

}
