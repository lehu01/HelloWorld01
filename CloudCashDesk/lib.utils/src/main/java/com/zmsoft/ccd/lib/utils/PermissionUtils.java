package com.zmsoft.ccd.lib.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProjectName:CloudCashDesk
 * 权限：Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA
 * Created by Jiaozi
 * on 17/05/2017.
 */
public final class PermissionUtils {

    static public final String[] REQUEST_PERMISSIONS = {Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,     // Android6.0 蓝牙扫描才需要
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    static public final int[] PERMISSION_TEXT_RESOURCE = {R.string.permission_device,
            R.string.permission_camera,
            R.string.permission_location,
            R.string.permission_blue_tooth,
            R.string.permission_storage};

    private String[] mUnGrantedPermissions; // android系统小于6.0时，返回为null

    public PermissionUtils(@NonNull Context context) {
        super();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mUnGrantedPermissions = null;
            return;
        }

        List<String> unGrantedPermissionList = new ArrayList<>();
        for (String requestPermission : REQUEST_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, requestPermission) != PackageManager.PERMISSION_GRANTED) {
                unGrantedPermissionList.add(requestPermission);
            }
        }
        mUnGrantedPermissions = unGrantedPermissionList.toArray(new String[0]);
    }

    public String[] getUnGrantedPermissions() {
        return mUnGrantedPermissions;
    }

    public String getPermissionContent(Context context) {
        Map<String, Integer> textResourceMap = new HashMap<>();
        for (int i = 0; i < PermissionUtils.REQUEST_PERMISSIONS.length; i++) {
            textResourceMap.put(PermissionUtils.REQUEST_PERMISSIONS[i], PermissionUtils.PERMISSION_TEXT_RESOURCE[i]);
        }
        StringBuilder stringBuilderPermission = new StringBuilder();
        for (int i = 0; i < mUnGrantedPermissions.length; i++) {
            String unGrantedPermission = mUnGrantedPermissions[i];
            if (!textResourceMap.containsKey(unGrantedPermission)) {
                continue;
            }
            stringBuilderPermission.append(context.getString(textResourceMap.get(unGrantedPermission)));
            if (i != mUnGrantedPermissions.length - 1) {
                stringBuilderPermission.append(context.getString(R.string.permission_dialog_content_segmentation));
            }
        }

        return context.getString(R.string.permission_dialog_content_0) +
                stringBuilderPermission +
                context.getString(R.string.permission_dialog_content_1);
    }

    public static boolean hasPermissions(@NonNull Context context, @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) ==
                    PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }
}
