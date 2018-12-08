package com.hxd.root.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.Toast;

import com.hxd.root.app.RootApplication;

/**
 * Created by Cazaea on 2016/12/14.
 * 单例Toast
 */

public class ToastUtil {

    private static Toast mToast;

    @SuppressLint("ShowToast")
    public static void showShort(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(RootApplication.getInstance(), text, Toast.LENGTH_SHORT);
            } else {
                mToast.cancel();
                mToast = Toast.makeText(RootApplication.getInstance(), text, Toast.LENGTH_SHORT);
            }
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
            mToast.show();
        }
    }

    @SuppressLint("ShowToast")
    public static void showLong(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(RootApplication.getInstance(), text, Toast.LENGTH_LONG);
            } else {
                mToast.cancel();
                mToast = Toast.makeText(RootApplication.getInstance(), text, Toast.LENGTH_LONG);
            }
            mToast.setDuration(Toast.LENGTH_LONG);
            mToast.setText(text);
            mToast.show();
        }
    }

}
