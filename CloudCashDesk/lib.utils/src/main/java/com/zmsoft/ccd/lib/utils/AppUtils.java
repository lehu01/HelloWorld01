package com.zmsoft.ccd.lib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/29 11:50
 *     desc  : app相关的
 * </pre>
 */
public class AppUtils {

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context     上下文
     * @param packageName 应用包名
     * @return true or false
     */
    public static boolean isAppExist(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 换起另一个app
     *
     * @param context     上下文
     * @param packageName 应用包名
     */
    public static void schemeApp(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            context.startActivity(intent);
        } else {
            // 没有安装要跳转的app应用，提醒一下
            Toast.makeText(context, context.getString(R.string.go_install_app), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 打开url地址
     *
     * @param context 上下文
     * @param url     地址
     */
    public static void openUrl(Context context, String url) {
        try {
            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            context.startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
