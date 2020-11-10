package com.jingpai.pos.customer.utils;

import android.util.Log;

import com.jingpai.pos.BuildConfig;

public class LogUtil {
    public static final String LIFE_TAG = "Life";
    public static final String URL_TAG = "OkHttp:";
    public static final String JPUSH_TAG = "JPush:";

    public static void e(String tag, String string) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, string);
        }
    }

    public static void v(String tag, String string) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, string);
        }
    }

    public static void d(String tag, String string) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, string);
        }
    }

    public static void w(String tag, String string) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, string);
        }
    }

    public static void i(String tag, String string) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, string);
        }
    }
}
