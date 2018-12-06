package com.hxd.root.http;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hxd.root.app.Constants;
import com.hxd.root.http.base.BaseResponse;
import com.hxd.root.http.interceptor.HttpCacheInterceptor;
import com.hxd.root.http.interceptor.HttpCookiesInterceptor;
import com.hxd.root.http.interceptor.HttpHeaderInterceptor;
import com.hxd.root.http.interceptor.ReceivedCookiesInterceptor;
import com.hxd.root.http.interceptor.httplogger.Logger;
import com.hxd.root.http.interceptor.httplogger.LoggerInterceptor;
import com.hxd.root.http.interceptor.httpparams.HttpFixedParams;
import com.hxd.root.http.rxutils.HttpsUtils;
import com.hxd.root.http.rxutils.ImplTokenGetListener;
import com.hxd.root.http.rxutils.NullOnEmptyConverterFactory;
import com.hxd.root.http.rxutils.ParamNames;
import com.hxd.root.http.rxutils.ResultDeserialize;
import com.hxd.root.utils.DebugUtil;
import com.hxd.root.utils.SPUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
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
        // 将规范的Gson解析成实体(鉴于后台蛋疼Json,必须特殊处理)
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
        HttpFixedParams.init(context);
    }

    /**
     * 初始化服务器
     *
     * @return API_ROOT
     */
    private String initRootApi() {
        return SPUtils.getString(Constants.CUSTOM_SERVER, DebugUtil.isDebug() ? API_BETA : API_FORMAL);
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
     * 后台Json格式不规范，必须特别处理
     */
    private Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            // 避免后台不规范的蛋疼Json格式
            builder.registerTypeHierarchyAdapter(BaseResponse.class, new ResultDeserialize());
            // 规范Json处理
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
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    // 添加公用参数头文件
                    .addInterceptor(new HttpHeaderInterceptor())
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
        if (DebugUtil.isDebug()) {
            logInterceptor.setLevel(LoggerInterceptor.Level.BODY); // 测试
        } else {
            logInterceptor.setLevel(LoggerInterceptor.Level.NONE); // 打包
        }
        return logInterceptor;
    }

    /**
     * 设置Token监听
     *
     * @param listener
     */
    public void setTokenListener(ImplTokenGetListener listener) {
        this.listener = listener;
    }

}
