package com.hxd.root.http.rxhttp.utils.cache;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.hxd.root.app.RootApplication;
import com.hxd.root.http.rxhttp.utils.NetworkUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作 者： Cazaea
 * 日 期： 2018/10/27
 * 邮 箱： wistorm@sina.com
 */
public class EnhancedCall<T> {

    private Call<T> mCall;
    private Class dataClassName;
    // 是否使用缓存 默认开启
    private boolean mUseCache = true;

    public EnhancedCall(Call<T> call) {
        this.mCall = call;
    }

    /**
     * Gson反序列化缓存时 需要获取到泛型的class类型
     */
    public EnhancedCall<T> dataClassName(Class className) {
        dataClassName = className;
        return this;
    }

    /**
     * 是否使用缓存 默认使用
     */
    public EnhancedCall<T> useCache(boolean useCache) {
        mUseCache = useCache;
        return this;
    }

    public void enqueue(final EnhancedCallback<T> enhancedCallback) {
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                enhancedCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(@NonNull Call<T> call, Throwable t) {
                if (!mUseCache || NetworkUtil.isNetworkConnected(RootApplication.getInstance())) {
                    //不使用缓存 或者网络可用 的情况下直接回调onFailure
                    enhancedCallback.onFailure(call, t);
                    return;
                }

                Request request = call.request();
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

                String cache = EnhancedCacheManager.getInstance().getCache(sb.toString());
                Log.d(EnhancedCacheManager.TAG, "get cache->" + cache);

                if (!TextUtils.isEmpty(cache) && dataClassName != null) {
                    Object obj = new Gson().fromJson(cache, dataClassName);
                    if (obj != null) {
                        enhancedCallback.onGetCache((T) obj);
                        return;
                    }
                }
                enhancedCallback.onFailure(call, t);
                Log.d(EnhancedCacheManager.TAG, "onFailure->" + t.getMessage());
            }
        });
    }

}
