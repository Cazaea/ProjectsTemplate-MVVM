package com.hxd.root.utils;

import com.hxd.root.app.Constants;
import com.orhanobut.logger.Logger;

/**
 * @author Cazaea
 * @Description 在代码中要打印log, 就直接DebugUtil.debug(....).
 * 然后如果发布的时候,就直接把这个类的DEBUG 改成false,这样所有的log就不会再打印在控制台.
 */
public class DebugUtil {

    public static boolean isDebug = false;
    private static final String TAG = "Logger";

    public static void initDebugMode() {
        isDebug = SPUtils.getBoolean(Constants.IS_DEBUG, false);
    }

    public static void debug(String msg) {
        if (isDebug) {
            Logger.t(TAG);
            Logger.d(msg);
        }
    }

    public static void debug(String tag, String msg) {
        if (isDebug) {
            Logger.t(tag);
            Logger.d(msg);
        }
    }

    public static void error(String error) {
        if (isDebug) {
            Logger.t(TAG);
            Logger.e(error);
        }
    }

    public static void error(String tag, String error) {
        if (isDebug) {
            Logger.t(tag);
            Logger.e(error);
        }
    }
}