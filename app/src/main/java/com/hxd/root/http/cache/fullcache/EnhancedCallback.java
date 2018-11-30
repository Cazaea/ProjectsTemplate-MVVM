package com.hxd.root.http.cache.fullcache;

import retrofit2.Call;
import retrofit2.Response;

/**
 * 作 者： Cazaea
 * 日 期： 2018/10/27
 * 邮 箱： wistorm@sina.com
 */
public interface EnhancedCallback<T> {
    void onResponse(Call<T> call, Response<T> response);

    void onFailure(Call<T> call, Throwable t);

    void onGetCache(T t);
}
