package com.hxd.root.app;

/**
 * @author Cazaea
 * @time 2016/11/26 15:24
 * @mail wistorm@sina.com
 * <p>
 * 固定参数
 */
public class Constants {

    /**
     * Router
     */
    public static final String ROUTER_HEAD = "lease";
    public static final String ROUTER_TOTAL = "lease://";
    public static final String ROUTER_WEBSITE = "www.lease.com";

    /**
     * Global Constants
     */
    public static String IS_FIRST = "is_first";
    public static String IS_LOGIN = "is_login";
    public static String IS_DEBUG = "is_debug";

    public static String APP_SERVER = "app_server";

    /**
     * Captcha type
     * 0 注册账户 1修改密码 2找回密码 3付款 4绑定银行卡
     */
    public static final String CODE_TYPE_REGISTER = "0";
    public static final String CODE_TYPE_MODIFY_PSW = "1";
    public static final String CODE_TYPE_RECOVER_PSW = "2";
    public static final String CODE_TYPE_PAY_MONEY = "3";
    public static final String CODE_TYPE_BIND_CARD = "4";

    /**
     * App Skin Theme
     */
    public static final String NIGHT_SKIN = "night.skin";
    public static final String KEY_MODE_NIGHT = "mode-night";

    /**
     * App Cache
     */
    // 首页缓存
    public static String CACHE_HOME_INFO = "CACHE_HOME_INFO";
    // 保存每日推荐轮播图url
    public static String BANNER_PIC = "banner_pic";
    // 保存每日推荐轮播图的跳转数据
    public static String BANNER_PIC_DATA = "banner_pic_data";
    // 保存每日推荐RecyclerView内容
    public static String EVERYDAY_CONTENT = "everyday_content";
    // 干货订制类别
    public static String GANK_CALA = "gank_cala";

}
