package com.hxd.root.http.rxhttp.utils.interceptor;

import android.support.annotation.NonNull;

import com.hxd.root.http.rxhttp.HttpHead;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作 者： Cazaea
 * 日 期： 2018/10/26
 * 邮 箱： wistorm@sina.com
 * <p>
 * 添加公用参数
 * 后续所有的网络相关公共参数以及缓存配置可以在该类实现
 */
public class HttpHeaderInterceptor2 implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        HashMap<String, String> params = new HashMap<>();

        // 获取原先的请求
        Request originalRequest = chain.request();
        // 重新构建Url
        HttpUrl.Builder builder = originalRequest.url().newBuilder();
        // 如果是POST请求的话就把参数重新拼接一下，GET请求的话就可以直接加入公共参数了
        if (originalRequest.method().equals("POST")) {
            FormBody body = (FormBody) originalRequest.body();
            int size = Objects.requireNonNull(body).size();
            for (int i = 0; i < size; i++) {
                builder.addQueryParameter(body.name(i), body.value(i));
                params.put(body.name(i), body.value(i));
            }
        }
        // 公共参数
        params.put("user_id", HttpHead.getId());
        params.put("token", HttpHead.getToken());
        params.put("platform", HttpHead.getPlatform());
        params.put("version", HttpHead.getVersion());
        params.put("device_uid", HttpHead.getUniqueId());

        builder.addQueryParameter("user_id", HttpHead.getId())
                .addQueryParameter("token", HttpHead.getToken())
                .addQueryParameter("platform", HttpHead.getPlatform())
                .addQueryParameter("version", HttpHead.getVersion())
                .addQueryParameter("device_uid", HttpHead.getUniqueId())
                .addQueryParameter("sign", HttpHead.getSign(params));

        // 新的Url
        HttpUrl httpUrl = builder.build();
        Request request = originalRequest.newBuilder()
                .method(originalRequest.method(), originalRequest.body())
                .url(httpUrl).build();

        return chain.proceed(request);
    }

}
