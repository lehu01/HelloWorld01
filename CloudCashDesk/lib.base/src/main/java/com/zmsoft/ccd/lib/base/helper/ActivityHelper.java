package com.zmsoft.ccd.lib.base.helper;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zmsoft.ccd.lib.base.R;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/16 18:48
 */
public class ActivityHelper {

    public static void showFragment(@NonNull FragmentManager fm, @NonNull Fragment fragment, int frameId) {
        showFragment(fm, fragment, frameId, false);
    }

    public static void showFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId, String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //if (isAddAnim) {
        //ft.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
        //}
        if (fragment.isAdded()) {
            ft.show(fragment);
        } else {
            ft.add(frameId, fragment, tag);
        }
        ft.commitAllowingStateLoss();
    }

    public static void showFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId, boolean isAddAnim) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (isAddAnim) {
            ft.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
        }
        if (fragment.isAdded()) {
            ft.show(fragment);
        } else {
            ft.add(frameId, fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId, boolean isAddAnim) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isAddAnim) {
            transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit, R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
        }
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        replaceFragment(fragmentManager, fragment, frameId, true);
    }

    public static void replaceFragmentToActivityNoBackStack(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit, R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    public static void hideFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        hideFragment(fragmentManager, fragment, true);
    }

    public static void hideFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, boolean isAnim) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        if (fragment.isAdded()) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (isAnim) {
                ft.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
            }
            ft.hide(fragment).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
    }

    public static void removeFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, boolean isAnim) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        if (fragment.isAdded()) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (isAnim) {
                ft.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);
            }
            ft.remove(fragment).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
    }
}
