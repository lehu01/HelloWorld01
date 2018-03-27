package com.zmsoft.ccd.lib.utils;

import android.app.Activity;
import android.text.TextUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Activity 栈控制
 */
public class ActivityStackControlUtils {

    private static Stack<Activity> activityStack;

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        if (activityStack.size() == 0) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束除了retainClass之外的所有Activity
     */
    public static void finishAllActivity(Class retainClass) {
        Iterator<Activity> it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (null != activity && !activity.getClass().equals(retainClass)) {
                it.remove();
                activity.finish();
            }
        }
    }

    /**
     * 结束除了retainClass之外的所有Activity
     */
    public static void finishAllActivity(List<Class> retainClassList) {
        if (null == retainClassList || retainClassList.isEmpty()) {
            return;
        }
        Iterator<Activity> it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (null != activity) {
                if (!retainClassList.contains(activity.getClass())) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束除了retainClassName之外的所有Activity
     */
    public static void finishAllExceptRetain(List<String> retainClassNameList) {
        if (null == retainClassNameList || retainClassNameList.isEmpty()) {
            return;
        }
        Iterator<Activity> it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (null != activity) {
                if (!retainClassNameList.contains(activity.getLocalClassName())) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 清掉栈内指定的activity
     *
     * @param className 要删除的activity的名称
     */
    public static void finishSpecActivity(String className) {
        if (TextUtils.isEmpty(className)) {
            return;
        }
        Iterator<Activity> it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            if (activity.getLocalClassName().equals(className)) {
                it.remove();
                activity.finish();
            }
        }
    }

    /**
     * 清掉栈内指定的activity
     *
     * @param finishClass 要删除的activity的class
     */
    public static void finishSpecActivity(Class finishClass) {
        if (null != activityStack && !activityStack.isEmpty()) {
            Iterator<Activity> it = activityStack.iterator();
            while (it.hasNext()) {
                Activity activity = it.next();
                if (activity.getClass().equals(finishClass)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 退出应用程序
     */
    public static void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAppExit() {
        return activityStack == null || activityStack.isEmpty();
    }

    /**
     * 检测占中是否有指定的activity
     *
     * @param className 指定className
     * @return true or false
     */
    public static boolean isActivity(Class className) {
        boolean result = false;
        for (Activity act : activityStack) {
            if (act.getClass().equals(className)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 检测是否只有一个界面
     */
    public static boolean isOnlyOne() {
        return activityStack != null && activityStack.size() <= 1;
    }

    /**
     * 保留栈内指定的activity list
     *
     * @param className 保留的activity
     */
    public static void notFinishListActivity(List<String> className) {
        if (className == null) {
            return;
        }
        Iterator<Activity> it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = it.next();
            boolean isSave = false;
            for (String name : className) {
                if (activity.getLocalClassName().equals(name)) {
                    isSave = true;
                }
            }
            if (!isSave) {
                it.remove();
                activity.finish();
            }
        }
    }
}


