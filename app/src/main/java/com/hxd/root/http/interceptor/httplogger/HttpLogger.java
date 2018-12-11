package com.hxd.root.http.interceptor.httplogger;

import android.support.annotation.NonNull;

import com.hxd.root.utils.DebugUtil;

/**
 * 作 者： Cazaea
 * 日 期： 2018/10/13
 * 邮 箱： wistorm@sina.com
 * <p>
 * 在Get请求时失效，故重新封装HttpLoggingInterceptor,区分处理
 */
public class HttpLogger implements HttpLoggerInterceptor.Logger {

    private static final String TAG = "HttpLogger";
    private StringBuilder mMessage = new StringBuilder();

    @Override
    public void log(@NonNull String message) {

        // 请求或者响应开始
        if (message.startsWith("--> POST")||message.startsWith("--> GET")) {
            mMessage.setLength(0);
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
        }
        mMessage.append(message.concat("\n"));
        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            DebugUtil.debug(TAG, mMessage.toString());
        }
    }

}
