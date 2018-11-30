package com.hxd.root.http.rxhttp.utils.interceptor;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hxd.root.http.rxhttp.HttpHead;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import static android.content.ContentValues.TAG;

/**
 * 作 者： Cazaea
 * 日 期： 2018/10/26
 * 邮 箱： wistorm@sina.com
 * <p>
 * 添加公用参数
 * 后续所有的网络相关公共参数以及缓存配置可以在该类实现
 */
public class HttpHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        // 方法名：POST | GET
        Request oldRequest = chain.request();
        String method = oldRequest.method();

        Request.Builder newRequestBuild = null;
        StringBuilder postBodyString = new StringBuilder();

        if ("POST".equals(method)) {
            RequestBody oldBody = oldRequest.body();
            if (oldBody instanceof FormBody) {

                // 添加接口参数
                postBodyString = new StringBuilder(bodyToString(oldBody));
                postBodyString.append((postBodyString.length() > 0) ? "&" : "");

                // 添加三个固定参数及User数据
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                formBodyBuilder.add("user_id", HttpHead.getId());
                formBodyBuilder.add("token", HttpHead.getToken());
                formBodyBuilder.add("platform", HttpHead.getPlatform());
                formBodyBuilder.add("version", HttpHead.getVersion());
                formBodyBuilder.add("device_uid", HttpHead.getUniqueId());

                postBodyString.append(bodyToString(formBodyBuilder.build()));

                // 根据之前参数，添加鉴权参数
                formBodyBuilder.add("sign", HttpHead.getSign(postBodyString.toString()));
                postBodyString.append(bodyToString(formBodyBuilder.build()));

                newRequestBuild = oldRequest.newBuilder();
                newRequestBuild.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString.toString()));

            } else if (oldBody instanceof MultipartBody) {

                MultipartBody oldBodyMultipart = (MultipartBody) oldBody;
                List<MultipartBody.Part> oldPartList = oldBodyMultipart.parts();
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);

                RequestBody requestBody1 = RequestBody.create(MediaType.parse("text/plain"), HttpHead.getId());
                RequestBody requestBody2 = RequestBody.create(MediaType.parse("text/plain"), HttpHead.getToken());
                RequestBody requestBody3 = RequestBody.create(MediaType.parse("text/plain"), HttpHead.getPlatform());
                RequestBody requestBody4 = RequestBody.create(MediaType.parse("text/plain"), HttpHead.getVersion());
                RequestBody requestBody5 = RequestBody.create(MediaType.parse("text/plain"), HttpHead.getUniqueId());

                for (MultipartBody.Part part : oldPartList) {
                    builder.addPart(part);
                    postBodyString.append(bodyToString(part.body())).append("\n");
                }
                postBodyString.append(bodyToString(requestBody1)).append("\n");
                postBodyString.append(bodyToString(requestBody2)).append("\n");
                postBodyString.append(bodyToString(requestBody3)).append("\n");
                postBodyString.append(bodyToString(requestBody4)).append("\n");
                postBodyString.append(bodyToString(requestBody5)).append("\n");

                // 不能用这个方法，因为不知道oldBody的类型，可能是PartMap过来的，也可能是多个Part过来的，所以需要重新逐个加载进去
                //  builder.addPart(oldBody);
                builder.addPart(requestBody1);
                builder.addPart(requestBody2);
                builder.addPart(requestBody3);
                builder.addPart(requestBody4);
                builder.addPart(requestBody5);

                // 添加参数鉴权(sign)
                RequestBody requestBody6 = RequestBody.create(MediaType.parse("text/plain"), HttpHead.getSign(bodyToString(builder.build())));
                postBodyString.append(bodyToString(requestBody4)).append("\n");
                builder.addPart(requestBody6);

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
                    .addQueryParameter("user_id", HttpHead.getId())
                    .addQueryParameter("token", HttpHead.getToken())
                    .addQueryParameter("platform", HttpHead.getPlatform())
                    .addQueryParameter("version", HttpHead.getVersion())
                    .addQueryParameter("device_uid", HttpHead.getUniqueId());

            commonParamsUrlBuilder.addQueryParameter("sign", HttpHead.getSign(commonParamsUrlBuilder.build().toString()));

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
