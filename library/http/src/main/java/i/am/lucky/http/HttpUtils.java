package i.am.lucky.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import i.am.lucky.http.utils.CheckNetwork;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by Cazaea on 2017/2/14.
 * 网络请求工具类
 * <p>
 * 使用时请在"Application"下初始化。
 */

public class HttpUtils {
    private static HttpUtils instance;
    private Gson gson;
    private Context context;
    private Object baseHttps;
    private ImplTokenGetListener listener;
    private boolean debug;

    // 项目默认Server
    public final static String API_ROOT = "https://www.haoyu.top";

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

    public void init(Context context, boolean debug) {
        this.context = context;
        this.debug = debug;
        HttpHead.init(context);
    }

    public <T> T getBaseServer(Class<T> a) {
        if (baseHttps == null) {
            synchronized (HttpUtils.class) {
                if (baseHttps == null) {
                    baseHttps = getBuilder(API_ROOT).build().create(a);
                }
            }
        }
        return (T) baseHttps;
    }

    public Retrofit.Builder getBuilder(String apiUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkClient());
        builder.baseUrl(apiUrl);// 设置远程地址
        builder.addConverterFactory(new NullOnEmptyConverterFactory());
        builder.addConverterFactory(GsonConverterFactory.create(getGson()));
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return builder;
    }

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

    private OkHttpClient getUnSafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

                @Override
                @SuppressLint("TrustAllX509TrustManager")
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                @SuppressLint("TrustAllX509TrustManager")
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};
            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // cache url
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            // 50 MiB
            int cacheSize = 50 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(30, TimeUnit.SECONDS);
            okBuilder.connectTimeout(30, TimeUnit.SECONDS);
            okBuilder.writeTimeout(30, TimeUnit.SECONDS);
            // 网络缓存
            okBuilder.addInterceptor(new HttpCacheInterceptor());
            // 添加公用参数头
            okBuilder.addInterceptor(new HttpHeadInterceptor());
            // 持久化cookie
            okBuilder.addInterceptor(new ReceivedCookiesInterceptor(context));
            okBuilder.addInterceptor(new AddCookiesInterceptor(context));
            // 添加缓存，无网访问时会拿缓存,只会缓存get请求
            okBuilder.addInterceptor(new AddCacheInterceptor(context));
            okBuilder.cache(cache);
            okBuilder.addInterceptor(getInterceptor());
            okBuilder.sslSocketFactory(sslSocketFactory);
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @SuppressLint("BadHostnameVerifier")
                @Override
                public boolean verify(String hostname, SSLSession session) {
//                    Log.d("HttpUtils", "==come");
                    return true;
                }
            });

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private OkHttpClient getOkClient() {
        OkHttpClient client1;
        client1 = getUnSafeOkHttpClient();
        return client1;
    }

    public void setTokenListener(ImplTokenGetListener listener) {
        this.listener = listener;
    }

    /**
     * 网络缓存头
     */
    private class HttpCacheInterceptor implements Interceptor {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            // 获取原先的请求
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
            builder.addHeader("Accept", "application/json;versions=1");
            if (CheckNetwork.isNetworkConnected(context)) {
                int maxAge = 60;
                builder.addHeader("Cache-Control", "public, max-age=" + maxAge);
            } else {
                int maxStale = 60 * 60 * 24 * 28;
                builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            return chain.proceed(builder.build());
        }
    }

    /**
     * 添加公用参数
     * 后续所有的网络相关公共参数以及缓存配置可以在该类实现
     */
    private class HttpHeadInterceptor implements Interceptor {

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request oldRequest = chain.request();
            Request.Builder newRequestBuild;
            String method = oldRequest.method();
            StringBuilder postBodyString = new StringBuilder();
            if ("POST".equals(method)) {
                RequestBody oldBody = oldRequest.body();
                if (oldBody instanceof FormBody) {
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();
                    formBodyBuilder.add("platform", HttpHead.getPlatform());
                    formBodyBuilder.add("version", HttpHead.getVersion());
                    formBodyBuilder.add("device_uid", HttpHead.getUniqueId());
                    formBodyBuilder.add("sign", HttpHead.getSignature(bodyToString(formBodyBuilder.build())));
                    newRequestBuild = oldRequest.newBuilder();

                    RequestBody formBody = formBodyBuilder.build();
                    postBodyString = new StringBuilder(bodyToString(oldRequest.body()));
                    postBodyString.append((postBodyString.length() > 0) ? "&" : "").append(bodyToString(formBody));
                    newRequestBuild.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString.toString()));
                } else if (oldBody instanceof MultipartBody) {
                    MultipartBody oldBodyMultipart = (MultipartBody) oldBody;
                    List<MultipartBody.Part> oldPartList = oldBodyMultipart.parts();
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    builder.setType(MultipartBody.FORM);

                    RequestBody requestBody1 = RequestBody.create(MediaType.parse("text/plain"), HttpHead.getPlatform());
                    RequestBody requestBody2 = RequestBody.create(MediaType.parse("text/plain"), HttpHead.getVersion());
                    RequestBody requestBody3 = RequestBody.create(MediaType.parse("text/plain"), HttpHead.getUniqueId());

                    for (MultipartBody.Part part : oldPartList) {
                        builder.addPart(part);
                        postBodyString.append(bodyToString(part.body())).append("\n");
                    }
                    postBodyString.append(bodyToString(requestBody1)).append("\n");
                    postBodyString.append(bodyToString(requestBody2)).append("\n");
                    postBodyString.append(bodyToString(requestBody3)).append("\n");

//                    不能用这个方法，因为不知道oldBody的类型，可能是PartMap过来的，也可能是多个Part过来的，所以需要重新逐个加载进去
//                    builder.addPart(oldBody);
                    builder.addPart(requestBody1);
                    builder.addPart(requestBody2);
                    builder.addPart(requestBody3);

                    // 添加参数鉴权(sign)
                    RequestBody requestBody4 = RequestBody.create(MediaType.parse("text/plain"), HttpHead.getSignature(bodyToString(builder.build())));
                    postBodyString.append(bodyToString(requestBody4)).append("\n");
                    builder.addPart(requestBody4);

                    newRequestBuild = oldRequest.newBuilder();
                    newRequestBuild.post(builder.build());
                    Log.e(TAG, "MultipartBody," + oldRequest.url());
                } else {
                    newRequestBuild = oldRequest.newBuilder();
                }
            } else {
                // 添加新的参数
                HttpUrl.Builder commonParamsUrlBuilder = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host());

                commonParamsUrlBuilder
                        .addQueryParameter("platform", HttpHead.getPlatform())
                        .addQueryParameter("version", HttpHead.getVersion())
                        .addQueryParameter("device_uid", HttpHead.getUniqueId())
                        .addQueryParameter("sign", HttpHead.getSignature(commonParamsUrlBuilder.build().toString()));

                newRequestBuild = oldRequest.newBuilder()
                        .method(oldRequest.method(), oldRequest.body())
                        .url(commonParamsUrlBuilder.build());
            }
            Request newRequest = newRequestBuild
                    .addHeader("Accept", "application/json")
                    .addHeader("Accept-Language", "zh")
                    .build();

            Response response = chain.proceed(newRequest);
            MediaType mediaType = Objects.requireNonNull(response.body()).contentType();
            String content = Objects.requireNonNull(response.body()).string();

            return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
        }

        private String bodyToString(final RequestBody request) {
            try {
                final Buffer buffer = new Buffer();
                if (request != null)
                    request.writeTo(buffer);
                else
                    return "";
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "Did not work";
            }
        }
    }

    private HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (debug) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // 测试
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE); // 打包
        }
        return interceptor;
    }

    private class AddCacheInterceptor implements Interceptor {
        private Context context;

        AddCacheInterceptor(Context context) {
            super();
            this.context = context;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {

            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();
            Request request = chain.request();
            if (!CheckNetwork.isNetworkConnected(context)) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (CheckNetwork.isNetworkConnected(context)) {
                // Read from cache
                int maxAge = 0;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                // Tolerate 4-weeks stale
                int maxStale = 60 * 60 * 24 * 28;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }

    private class ReceivedCookiesInterceptor implements Interceptor {
        private Context context;

        ReceivedCookiesInterceptor(Context context) {
            super();
            this.context = context;

        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {

            Response originalResponse = chain.proceed(chain.request());
            // 这里获取请求返回的cookie
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {

                List<String> d = originalResponse.headers("Set-Cookie");
//                Log.e("Cazaea", "------------得到的 cookies:" + d.toString());

                // 返回cookie
                if (!TextUtils.isEmpty(d.toString())) {

                    SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorConfig = sharedPreferences.edit();
                    String oldCookie = sharedPreferences.getString("cookie", "");

                    HashMap<String, String> stringStringHashMap = new HashMap<>();

                    // 之前存过cookie
                    if (!TextUtils.isEmpty(oldCookie)) {
                        String[] substring = oldCookie.split(";");
                        for (String aSubstring : substring) {
                            if (aSubstring.contains("=")) {
                                String[] split = aSubstring.split("=");
                                stringStringHashMap.put(split[0], split[1]);
                            } else {
                                stringStringHashMap.put(aSubstring, "");
                            }
                        }
                    }
                    String join = StringUtils.join(d, ";");
                    String[] split = join.split(";");

                    // 存到Map里
                    for (String aSplit : split) {
                        String[] split1 = aSplit.split("=");
                        if (split1.length == 2) {
                            stringStringHashMap.put(split1[0], split1[1]);
                        } else {
                            stringStringHashMap.put(split1[0], "");
                        }
                    }

                    // 取出来
                    StringBuilder stringBuilder = new StringBuilder();
                    if (stringStringHashMap.size() > 0) {
                        for (String key : stringStringHashMap.keySet()) {
                            stringBuilder.append(key);
                            String value = stringStringHashMap.get(key);
                            if (!TextUtils.isEmpty(value)) {
                                stringBuilder.append("=");
                                stringBuilder.append(value);
                            }
                            stringBuilder.append(";");
                        }
                    }

                    editorConfig.putString("cookie", stringBuilder.toString());
                    editorConfig.apply();
                }
            }

            return originalResponse;
        }
    }

    private class AddCookiesInterceptor implements Interceptor {
        private Context context;

        AddCookiesInterceptor(Context context) {
            super();
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

}
