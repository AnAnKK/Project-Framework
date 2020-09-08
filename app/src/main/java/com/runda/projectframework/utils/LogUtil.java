package com.runda.projectframework.utils;

import android.util.Log;
import com.runda.projectframework.app.others.Constants;

/**
 * Created by Kongdq
 * @date 2019/11/7
 * Description: Log工具类
 */
public class LogUtil {

    public static boolean DEBUG = Constants.DEBUG;

    public static void d(String TAG, String value) {
        if (DEBUG) {
            Log.d(TAG, value);
        }
    }

    public static void e(String TAG, String value) {
        if (DEBUG) {
            Log.e(TAG, value);
        }
    }

    public static void e(String TAG, Exception value) {
        if (DEBUG && value != null) {
            Log.e(TAG, value.getMessage());
        }
    }

    public static void i(String TAG, String value) {
        if (DEBUG) {
            Log.i(TAG, value);
        }
    }

    public static void w(String TAG, String value) {
        if (DEBUG) {
            Log.w(TAG, value);
        }
    }
}
