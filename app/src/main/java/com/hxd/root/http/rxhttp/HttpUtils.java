package com.hxd.root.http.rxhttp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hxd.root.app.Constants;
import com.hxd.root.http.rxhttp.utils.NetworkUtil;
import com.hxd.root.http.rxhttp.utils.HttpsUtils;
import com.hxd.root.http.rxhttp.utils.httplogger.Logger;
import com.hxd.root.http.rxhttp.utils.httplogger.LoggerInterceptor;
import com.hxd.root.http.rxhttp.utils.interceptor.HttpCacheInterceptor;
import com.hxd.root.http.rxhttp.utils.interceptor.HttpHeaderInterceptor2;
import com.hxd.root.http.rxhttp.utils.interceptor.HttpCookiesInterceptor;
import com.hxd.root.http.rxhttp.utils.interceptor.ReceivedCookiesInterceptor;
import com.hxd.root.utils.DebugUtil;
import com.hxd.root.utils.SPUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作 者： Cazaea
 * 日 期： 2018/10/12
 * 邮 箱： wistorm@sina.com
 * <p>
 * 网络请求工具类 Retrofit2.0 + OkHttp3
 * <p>
 * 使用时请在"Application"下初始化。
 */
public class HttpUtils {

    @SuppressLint("StaticFieldLeak")
    private static HttpUtils instance;
    private Gson gson;
    private Context context;
    private Object baseHttps;
    private Object updateHttps;
    private ImplTokenGetListener listener;

    // 项目默认Server
    public static String API_ROOT;
    // 项目测试Server
    public final static String API_BETA = "http://lease.nfc-hxd.com";
    // 项目正式Server
    public final static String API_FORMAL = "https://www.haoyu.top";
    // 版本更新第三方服务器
    public final static String API_UPDATE = "http://api.fir.im/apps/";

    /**
     * 分页数据，每页的数量
     */
    public static int per_page = 10;
    public static int per_page_more = 20;

    /**
     * 获取实例,方便操作方法
     *
     * @return
     */
    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * Retrofit配置
     * 源码方式, Gson解析自定义, 必须要后台返回Json格式规范
     *
     * @param apiUrl
     * @return
     */
    public Retrofit.Builder getBuilder(String apiUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        // 设置OkHttp3(重点), 避免走默认
        builder.client(getOkClient());
        // 设置远程服务器地址
        builder.baseUrl(apiUrl);
        // 添加Gson空值转换器, 避免转换异常
        builder.addConverterFactory(new NullOnEmptyConverterFactory());
        // 将规范的Gson解析成实体
        builder.addConverterFactory(GsonConverterFactory.create(getGson()));
        // 添加支持RxAdapter回调适配
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder;
    }

    /**
     * 初始化
     *
     * @param context 全局Application
     */
    public void init(Context context) {
        this.context = context;
        API_ROOT = initRootApi();
        HttpHead.init(context);
    }

    /**
     * 初始化服务器
     *
     * @return API_ROOT
     */
    private String initRootApi() {
        return SPUtils.getString(Constants.CUSTOM_SERVER, DebugUtil.isDebug ? API_BETA : API_FORMAL);
    }

    /**
     * 项目服务器获取
     */
    public <T> T getServer(Class<T> a, String type) {

        switch (type) {
            case API_UPDATE:
                if (updateHttps == null) {
                    synchronized (HttpUtils.class) {
                        if (updateHttps == null) {
                            updateHttps = getBuilder(API_UPDATE).build().create(a);
                        }
                    }
                }
                return (T) updateHttps;
            default:
                if (baseHttps == null) {
                    synchronized (HttpUtils.class) {
                        if (baseHttps == null) {
                            baseHttps = getBuilder(API_ROOT).build().create(a);
                        }
                    }
                }
                return (T) baseHttps;
        }
    }

    /**
     * Gson配置
     */
    private Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            gson = builder.create();
        }
        return gson;
    }

    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }

    /**
     * OkHttp配置
     */
    private OkHttpClient getOkClient() {
        OkHttpClient client;
        client = getUnSafeOkHttpClient();
        return client;
    }

    /**
     * OkHttp配置,保证Https安全
     *
     * @return OkHttpClient
     */
    private OkHttpClient getUnSafeOkHttpClient() {
        try {

            // Cache url
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            // Cache size 50 MiB
            int cacheSize = 50 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);

            // 获取目标网站的证书
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();

            // 具体配置，可用链式结构
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                    // 设置网络超时
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    // 添加公用参数头文件
                    .addInterceptor(new HttpHeaderInterceptor2())
                    // 持久化Cookies
                    .addInterceptor(new HttpCookiesInterceptor(context))
                    .addInterceptor(new ReceivedCookiesInterceptor(context))
                    // 公共缓存拦截器,无网络访问时会拿缓存
                    .addInterceptor(new HttpCacheInterceptor(context))
                    .cache(cache)
                    // 设置网络日志拦截器
                    .addInterceptor(getLoggerInterceptor())
                    // 客户端不检查证书
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    // 验证主机名
                    .hostnameVerifier((hostname, session) -> {
                        DebugUtil.debug("HttpUtils", "Host==>" + hostname + "<==>Session=" + session);
                        return true;
                    });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 网络请求Logger日志
     *
     * @return HttpLogger拦截器
     */
    private LoggerInterceptor getLoggerInterceptor() {
        // 自定义网络请求Json日志输出形式
        Logger httpLogger = new Logger();
        LoggerInterceptor logInterceptor = new LoggerInterceptor(httpLogger);
        if (DebugUtil.isDebug) {
            logInterceptor.setLevel(LoggerInterceptor.Level.BODY); // 测试
        } else {
            logInterceptor.setLevel(LoggerInterceptor.Level.NONE); // 打包
        }
        return logInterceptor;
    }

    /**
     * 缓存拦截器，需要有缓存文件
     * <p>
     * 离线读取本地缓存，在线获取最新数据
     */
    private class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            // 获取原先的请求
            Request request = chain.request();
            // 可添加token验证, 可用链式结构
            Request.Builder builder = request.newBuilder();
            builder.addHeader("Accept", "application/json;versions=1");
            if (NetworkUtil.isNetworkConnected(context)) {
                int maxAge = 60;
                builder.addHeader("Cache-Control", "public, max-age=" + maxAge);
            } else {
                int maxStale = 60 * 60 * 24 * 28;
                builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            // 可添加token
//            if (listener != null) {
//                builder.addHeader("token", listener.getToken());
//            }
            // 如有需要，添加请求头
//            builder.addHeader("user_id", HttpHead.getID());
            builder.addHeader("a", HttpHead.getHeader(request.method()));
            return chain.proceed(builder.build());
        }
    }

    public void setTokenListener(ImplTokenGetListener listener) {
        this.listener = listener;
    }

}
