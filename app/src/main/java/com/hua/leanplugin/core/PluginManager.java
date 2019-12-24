package com.hua.leanplugin.core;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-12-23 17:15
 */

public class PluginManager {

    Application context;
    private Map<String, LoadedPluginImpl> mLoadedPluginInfoMap = new HashMap<>();

    public static PluginManager get() {
        return Holder.S_INSTANCE;
    }

    private static final class Holder {
        private static final PluginManager S_INSTANCE = new PluginManager();
    }

    private PluginManager() {
    }

    public @Nullable
    IPluginApk loadPlugin(String pluginPath) {
        Utils.checkNonNull(pluginPath, "pluginPath is null");

        LoadedPluginImpl pluginInfo = mLoadedPluginInfoMap.get(pluginPath);
        if (pluginInfo == null) {
            try {
                pluginInfo = loadPluginInternal(pluginPath);
                mLoadedPluginInfoMap.put(pluginPath, pluginInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return pluginInfo;
    }

    private LoadedPluginImpl loadPluginInternal(String pluginPath) throws PackageManager.NameNotFoundException {
        LoadedPluginImpl pluginInfo = null;
        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                PackageManager.GET_SHARED_LIBRARY_FILES);
        DexClassLoader dexClassLoader = new DexClassLoader(
                pluginPath, null,
                appInfo.nativeLibraryDir,
                getClass().getClassLoader());

        pluginInfo = new LoadedPluginImpl(pluginPath, dexClassLoader);
        return pluginInfo;
    }

}
