package com.zmsoft.ccd.lib.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 反射帮助类
 *
 * @author DangGui
 * @create 2017/7/20.
 */

public class ReflectUtil {
    public static Object getInstance(String className) {
        try {
            Class<?> cls = Class.forName(className);
            //构造函数
            Constructor<?> constructor = cls.getDeclaredConstructor();
            //根据构造函数，生成实例
            return constructor.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
