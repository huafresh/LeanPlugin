package com.hua.leanplugin.core;

import android.app.Activity;
import android.app.Service;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-12-24 17:55
 */

class PluginPackageInfo {
    ApplicationInfo appInfo;
    final List<ActivityInfo> activities = new ArrayList<>(0);
    final List<ActivityInfo> receivers = new ArrayList<>(0);
    final List<ProviderInfo> providers = new ArrayList<>(0);
    final List<ServiceInfo> services = new ArrayList<>(0);

    static PluginPackageInfo parsePluginApkInfo(String apkPath) {
        PluginPackageInfo packageInfo = new PluginPackageInfo();
        ClassWrap parserClass = new ClassWrap("android.content.pm.PackageParser");
        ObjectWrap parserWrap = new ObjectWrap(parserClass.newInstance());
        Object packageObj = parserWrap.invokeMethod("parsePackage", apkPath, 1);
        ObjectWrap packageWrap  = new ObjectWrap(packageObj);
        packageInfo.appInfo = (ApplicationInfo) packageWrap.getFieldValue("applicationInfo");
        // todo
//        packageInfo.activities = (ApplicationInfo) packageWrap.getFieldValue("applicationInfo");
//        packageInfo.receivers = (ApplicationInfo) packageWrap.getFieldValue("applicationInfo");
//        packageInfo.providers = (ApplicationInfo) packageWrap.getFieldValue("applicationInfo");

        return packageInfo;
    }
}
