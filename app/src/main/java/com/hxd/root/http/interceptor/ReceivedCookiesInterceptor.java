package com.hxd.root.http.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hxd.root.utils.DebugUtil;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 作 者： Cazaea
 * 日 期： 2018/10/26
 * 邮 箱： wistorm@sina.com
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        // 这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            List<String> d = originalResponse.headers("Set-Cookie");
            DebugUtil.error("------------得到的Cookies:" + d.toString());

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
