package com.hxd.root.http.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hxd.root.http.rxutils.NetworkUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作 者： Cazaea
 * 日 期： 2018/10/26
 * 邮 箱： wistorm@sina.com
 * <p>
 * 缓存拦截器,需要有缓存文件
 * 离线读取本地缓存,在线获取最新数据,只会缓存GET请求
 */
public class HttpCacheInterceptor implements Interceptor {

    private Context context;

    public HttpCacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();

        // 自定义 缓存超时
        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();

        if (NetworkUtil.isNetworkConnected(context)) {
            request = request.newBuilder()
                    // 网络可用,强制从网络获取数据
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        } else {
            request = request.newBuilder()
                    // 网络不可用,从缓存获取数据
                    .cacheControl(cacheControl)
//                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response originalResponse = chain.proceed(request);
        if (NetworkUtil.isNetworkConnected(context)) {
            // Read from cache 在线缓存在60s内可读取
            int maxAge = 60;
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            // Tolerate 1-weeks stale 无网络时,设置超时为1周
            int maxStale = 60 * 60 * 24 * 7;
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }

        return originalResponse;
    }

}
