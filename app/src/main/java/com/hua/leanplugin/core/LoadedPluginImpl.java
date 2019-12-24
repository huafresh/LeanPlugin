package com.hua.leanplugin.core;

import java.lang.reflect.Array;
import java.util.Arrays;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

import static com.hua.leanplugin.core.Utils.logD;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-12-23 17:28
 */

class LoadedPluginImpl implements IPluginApk {
    private String mApkFilePath;
    private DexClassLoader mClassLoader;
    private PluginPackageInfo mPackageInfo;

    LoadedPluginImpl(String apkPath, DexClassLoader mClassLoader) {
        this.mApkFilePath = apkPath;
        this.mClassLoader = mClassLoader;
        combinePathList(mClassLoader, (BaseDexClassLoader) getClass().getClassLoader());
        mPackageInfo = PluginPackageInfo.parsePluginApkInfo(apkPath);
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return mClassLoader.loadClass(className);
    }

    private static void combinePathList(BaseDexClassLoader from, BaseDexClassLoader to) {
        ObjectWrap fromProxy = new ObjectWrap(from);
        Object fromPathList = fromProxy.getFieldValue("pathList");
        ObjectWrap fromPathListProxy = new ObjectWrap(fromPathList);
        Object[] fromElements = (Object[]) fromPathListProxy.getFieldValue("dexElements");
        logD("from elements size: " + fromElements.length);

        ObjectWrap toProxy = new ObjectWrap(to);
        Object toPathList = toProxy.getFieldValue("pathList");
        ObjectWrap toPathListProxy = new ObjectWrap(toPathList);
        Object[] toElements = (Object[]) toPathListProxy.getFieldValue("dexElements");
        logD("to elements size: " + toElements.length);

        int totalLength = fromElements.length + toElements.length;
        // Object[] result2 = new Object[totalLength];
        Object[] result = (Object[]) Array.newInstance(toElements.getClass().getComponentType(), totalLength);
        System.arraycopy(fromElements, 0, result, 0, fromElements.length);
        System.arraycopy(toElements, 0, result, fromElements.length, toElements.length);
        logD("result elements size: " + result.length);

        toPathListProxy.setFieldValue("dexElements", result);
    }
}
