package com.hxd.root.http.rxhttp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import com.hxd.root.data.UserUtil;
import com.hxd.root.http.rxhttp.utils.RequestUrlUtil;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cazaea on 2017/2/14.
 */
public class HttpHead {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static final String CLIENT_TYPE = "4";
    private static HashMap<String, String> params;

    static void init(Context context) {
        HttpHead.context = context;
        params = UserUtil.getUserInfo();
    }

    static String getHeader(String httpMethod) {
        long currentTime = System.currentTimeMillis() / 1000;
        return getPlatform() + getVersion() + getUniqueId() + CLIENT_TYPE + httpMethod + "0" + currentTime;
    }

    /**
     * 用户user_id
     */
    public static String getId() {
        return params.get("id");
    }

    /**
     * 用户token
     */
    public static String getToken() {
        return params.get("token");
    }

    /**
     * 获取平台信息
     */
    public static String getPlatform() {
        return "Android";
    }

    /**
     * 获取应用版本号
     */
    public static String getVersion() {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        return info.versionName;
    }

    /**
     * 获取设备唯一id
     */
    public static String getUniqueId() {
        @SuppressLint("HardwareIds") String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        @SuppressLint("HardwareIds") String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }

    private static String toMD5(String text) throws NoSuchAlgorithmException {
        // 获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        // 通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte aDigest : digest) {
            // 循环每个字符 将计算结果转化为正整数;
            int digestInt = aDigest & 0xff;
            // 将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            // 转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            // 将循环结果添加到缓冲区
            sb.append(hexString);
        }
        // 返回整个结果
        return sb.toString();
    }

    /**
     * 方式一：通过解析Url
     * 数字签名工具
     * 1qaz2wsx
     */
    public static String getSign(String baseUrl) throws IOException {

        // 解析出url参数中的键值对
        StringBuilder baseString = new StringBuilder();
        // Url中的参数部分
        Map<String, String> valuePart = RequestUrlUtil.getParamMap(baseUrl);
        List<Map.Entry<String, String>> list = new ArrayList<>(valuePart.entrySet());
        // 升序排序
        Collections.sort(list, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));

        for (Map.Entry<String, String> mapping : list) {
            baseString.append(mapping.getKey()).append("=").append(mapping.getValue());
        }

        // 加上签名规则
        baseString.append("1qaz2wsx");

        byte[] bytes;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(baseString.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }

        StringBuilder sign = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

    /**
     * 方式二：通过解析Map
     * 数字签名工具
     * 1qaz2wsx
     *
     * @param postPairs
     * @return
     * @throws IOException
     */
    public static String getSign(HashMap<String, String> postPairs) throws IOException {

        StringBuilder baseString = new StringBuilder();
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(postPairs.entrySet());
        // 升序排序
        Collections.sort(list, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));

        for (Map.Entry<String, String> mapping : list) {
            baseString.append(mapping.getKey()).append("=").append(mapping.getValue());
        }

        // 加上签名规则
        baseString.append("1qaz2wsx");

        byte[] bytes;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(baseString.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }

        StringBuilder sign = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

    /**
     * 获取机型
     */
    public static String getDevice() {
        String s = Build.MODEL;
        return s.replace(" ", "");
    }

}
