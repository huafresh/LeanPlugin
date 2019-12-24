package com.hua.leanplugin.core;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-12-23 20:09
 */

class ReflectUtil {

    static Class<?> fromName(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
