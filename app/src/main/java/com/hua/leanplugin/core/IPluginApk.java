package com.hua.leanplugin.core;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-12-23 19:52
 */

public interface IPluginApk {

    Class<?> loadClass(String className) throws ClassNotFoundException;

}
