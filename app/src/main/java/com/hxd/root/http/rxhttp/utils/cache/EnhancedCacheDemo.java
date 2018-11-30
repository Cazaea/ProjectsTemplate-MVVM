//package com.hxd.root.http.rxhttp.utils.cache;
//
//import android.annotation.SuppressLint;
//import android.util.Log;
//import android.view.View;
//
//import com.hxd.root.bean.home.HomeInfoBean;
//import com.hxd.root.http.HttpClient;
//import com.hxd.root.http.rxhttp.HttpUtils;
//
//import java.util.Objects;
//
//import retrofit2.Call;
//import retrofit2.BaseResponse;
//
///**
// * 作 者： Cazaea
// * 日 期： 2018/10/27
// * 邮 箱： wistorm@sina.com
// */
//public class EnhancedCacheDemo {
//
//    @SuppressLint("StaticFieldLeak")
//    private static EnhancedCacheDemo instance;
//    private static final String TAG = "EnhancedCacheDemo";
//
//    public static EnhancedCacheDemo getInstance() {
//        if (instance == null) {
//            synchronized (HttpUtils.class) {
//                if (instance == null) {
//                    instance = new EnhancedCacheDemo();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public void getRequest(View view) {
//        Call<HomeInfoBean> call = HttpClient.Builder.getBaseServer().getHomeInfo();
//        //使用我们自己的EnhancedCall 替换Retrofit的Call
//        EnhancedCall<HomeInfoBean> enhancedCall = new EnhancedCall<>(call);
//        enhancedCall
//                // 默认支持缓存 可不设置
//                .useCache(true)
//                .dataClassName(HomeInfoBean.class)
//                .enqueue(new EnhancedCallback<HomeInfoBean>() {
//                    @Override
//                    public void onResponse(Call<HomeInfoBean> call, BaseResponse<HomeInfoBean> response) {
//                        HomeInfoBean homeInfo = response.body();
//                        if (homeInfo != null) {
//                            Log.d(TAG, "onResponse->" + homeInfo.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<HomeInfoBean> call, Throwable t) {
//                        Log.d(TAG, "onFailure->" + t.getMessage());
//                    }
//
//                    @Override
//                    public void onGetCache(HomeInfoBean homeInfo) {
//                        Log.d(TAG, "onGetCache" + homeInfo.toString());
//                    }
//                });
//    }
//
//}
