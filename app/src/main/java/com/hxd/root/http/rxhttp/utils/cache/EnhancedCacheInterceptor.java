package com.hxd.root.http.rxhttp.utils.cache;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 作 者： Cazaea
 * 日 期： 2018/10/27
 * 邮 箱： wistorm@sina.com
 * <p>
 * 增强的缓存拦截器,支持缓存POST请求
 */
public class EnhancedCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        String url = request.url().toString();
        RequestBody requestBody = request.body();
        Charset charset = Charset.forName("UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (request.method().equals("POST")) {
            MediaType contentType = Objects.requireNonNull(requestBody).contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(buffer.readString(Objects.requireNonNull(charset)));
            buffer.close();
        }

        Log.d(EnhancedCacheManager.TAG, "EnhancedCacheInterceptor -> key:" + sb.toString());

        ResponseBody responseBody = response.body();
        MediaType contentType = Objects.requireNonNull(responseBody).contentType();

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }
        String key = sb.toString();
        // 服务器返回的json原始数据
        String json = buffer.clone().readString(Objects.requireNonNull(charset));

        EnhancedCacheManager.getInstance().putCache(key, json);
        Log.d(EnhancedCacheManager.TAG, "put cache-> key:" + key + "-> json:" + json);
        return chain.proceed(request);
    }

}
