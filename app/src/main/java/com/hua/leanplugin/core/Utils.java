package com.hua.leanplugin.core;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import dalvik.system.BaseDexClassLoader;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-12-23 17:01
 */

public class Utils {
    private static final String TAG = "@@@hua";

    public static void copyStream(InputStream input, OutputStream output) {
        try {
            byte[] buf = new byte[2048];
            int len = -1;
            while ((len = input.read(buf)) != -1) {
                output.write(buf, 0, len);
            }
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void checkNonNull(Object obj, String msg) {
        if (obj == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    static void logD(String msg) {
        Log.d(TAG, msg);
    }

    static void logE(String msg, Exception e) {
        Log.e(TAG, msg, e);
    }

    static void logE(String msg) {
        Log.e(TAG, msg);
    }
}
