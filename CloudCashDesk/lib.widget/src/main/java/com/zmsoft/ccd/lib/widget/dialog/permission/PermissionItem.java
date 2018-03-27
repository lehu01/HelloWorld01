package com.zmsoft.ccd.lib.widget.dialog.permission;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.io.Serializable;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/29 19:23.
 */

public class PermissionItem implements Serializable {
    private int mImageResource;
    private int mTextResource;

    public PermissionItem(@DrawableRes int mImageResource, @StringRes int mTextResource) {
        this.mImageResource = mImageResource;
        this.mTextResource = mTextResource;
    }

    int getImageResource() {
        return mImageResource;
    }

    int getTextResource() {
        return mTextResource;
    }
}
