package com.hxd.root.http.rxutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/29
 * 邮 箱： wistorm@sina.com
 * <p>
 * 用于判断是不是联网状态
 */
public class NetworkUtil {

    /**
     * 判断网络是否连通
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = Objects.requireNonNull(cm).getActiveNetworkInfo();
            return info != null && info.isConnected();
        } else {
            /**如果context为空，就返回false，表示网络未连接*/
            return false;
        }
    }

    /**
     * 判断当前网络是否WIFI连通
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = Objects.requireNonNull(cm).getActiveNetworkInfo();
            return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
        } else {
            /**如果context为null就表示为未连接*/
            return false;
        }
    }

}
