package com.hua.leanplugin.core;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-12-01 11:40
 */

class ObjectWrap {

    Object object;

    ObjectWrap(Object obj) {
        object = obj;
    }

    Object invokeMethod(String name, Object... params) {
        Class cls = object.getClass();
        Method method = null;
        while (cls != null) {
            try {
                method = new ClassWrap(cls).getMethod(name, params);
            } catch (Exception e) {
                cls = cls.getSuperclass();
            }
            if (method != null) {
                break;
            }
        }
        if (method != null) {
            try {
                return method.invoke(this.object, params);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    Object getFieldValue(String name) {
        Field field = findField(name);
        if (field != null) {
            try {
                return field.get(this.object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    void setFieldValue(String name, Object value) {
        Field field = findField(name);
        if (field != null) {
            try {
                field.set(this.object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private @Nullable
    Field findField(String name) {
        Class cls = object.getClass();
        Field field = null;
        while (cls != null) {
            try {
                field = cls.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                // ignore
            }
            if (field != null) {
                field.setAccessible(true);
                break;
            } else {
                cls = cls.getSuperclass();
            }
        }
        return field;
    }
}
