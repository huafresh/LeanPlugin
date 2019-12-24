package com.hua.pluginapp;

import android.app.Application;
import android.util.Log;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-12-24 18:18
 */

public class PluginApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("@@@hua", "plugin app onCreate");
    }
}
