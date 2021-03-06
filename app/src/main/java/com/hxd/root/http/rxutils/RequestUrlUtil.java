package com.hxd.root.http.rxutils;

import java.util.LinkedHashMap;

/**
 * 作 者： Cazaea
 * 日 期： 2018/11/26
 * 邮 箱： wistorm@sina.com
 * <p>
 * 路由Url相关操作类
 */
public class RequestUrlUtil {

    /**
     * 解析出url请求的路径，包括页面
     *
     * @param strURL url地址
     * @return url路径
     */
    public static String getPageInfo(String strURL) {
        String strPage = null;
        strURL = strURL.trim();
        String[] arrSplit = strURL.split("[?]");
        if (strURL.length() > 0) {
            if (arrSplit.length > 0) {
                if (arrSplit[0] != null) {
                    strPage = arrSplit[0];
                }
            }
        }
        return strPage;
    }

    /**
     * 去掉Url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String getParamInfo(String strURL) {
        String strAllParam = null;
        strURL = strURL.trim();

        String[] arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            } else {
                strAllParam = strURL;
            }
        }
        return strAllParam;
    }

    /**
     * 解析出Url参数中的键值对
     * <p>
     * 如: "index.jsp?Action=del&id=123"
     * 解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static LinkedHashMap<String, String> getParamMap(String URL) {
        LinkedHashMap<String, String> mapRequest = new LinkedHashMap<String, String>();
        String strUrlParam = getParamInfo(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        // 每个键值为一组
        String[] arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = strSplit.split("[=]");
            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (!arrSplitEqual[0].equals("")) {
                    // 只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 拼接Url链接部分和参数部分
     */
    public static String stitchingUrl(String urlWithoutValue, LinkedHashMap<String, String> params) {
        StringBuilder url = new StringBuilder(urlWithoutValue + "?");
        boolean firstFlag = true;
        for (LinkedHashMap.Entry<String, String> entry : params.entrySet()) {
            if (firstFlag) {
                url.append(entry.getKey()).append("=").append(entry.getValue());
                firstFlag = false;
            } else {
                url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return url.toString();
    }
}
