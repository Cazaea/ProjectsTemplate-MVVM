package com.hxd.root.http.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作 者： Cazaea
 * 日 期： 2018/10/26
 * 邮 箱： wistorm@sina.com
 */
public class HttpCookiesInterceptor implements Interceptor {

    private Context context;

    public HttpCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String cookie = sharedPreferences.getString("cookie", "");
        builder.addHeader("Cookie", cookie);
        return chain.proceed(builder.build());
    }

}
