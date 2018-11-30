package com.hxd.root.http.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import retrofit2.HttpException;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/29
 * 邮 箱： wistorm@sina.com
 */
public class CustomException {

    /**
     * 服务器产生的Exception
     */
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int SERVICE_UNAVAILABLE = 503;

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    ex.setMessage("操作未授权");
                    break;
                case FORBIDDEN:
                    ex.setMessage("请求被拒绝");
                    break;
                case NOT_FOUND:
                    ex.setMessage("资源不存在");
                    break;
                case REQUEST_TIMEOUT:
                    ex.setMessage("服务器执行超时");
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.setMessage("服务器内部错误");
                    break;
                case SERVICE_UNAVAILABLE:
                    ex.setMessage("服务器不可用");
                    break;
                default:
                    ex.setMessage("网络错误");
                    break;
            }
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            ex.setMessage("数据解析错误");
            return ex;
        } else if (e instanceof ConnectException || e instanceof SocketException) {
            ex = new ResponseThrowable(e, ERROR.NETWORK_ERROR);
            ex.setMessage("网络异常，请检查网络重试");
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.setMessage("请求失败，请稍后重试...");
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.setMessage("请求超时");
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.setMessage("连接超时");
            return ex;
        } else if (e instanceof SSLException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.setMessage("证书验证失败");
            return ex;
        } else {
            ex = new ResponseThrowable(e, ERROR.UNKNOWN);
            ex.setMessage("未知错误");
            return ex;
        }
    }

    /**
     * 约定异常 这个具体规则需要与服务端或者领导商讨定义
     */
    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 2000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 2001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 2002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 2003;
        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 2005;
        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 2006;
    }

}
