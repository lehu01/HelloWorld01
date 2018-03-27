package com.zmsoft.ccd.lib.utils.dialogutil;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.ListCallbackSingleChoice;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/2/6.
 */

public class DialogUtil {
    private BaseDialogStrategy mStrategy;
    private Dialog mDialog;

    public DialogUtil(Context context) {
        mStrategy = new MaterialDialogStrategy(context);
    }

    /**
     * 隐藏dialog
     */
    public void dismissDialog() {
        if (null != mDialog) {
            mDialog.dismiss();
        }
    }

    public void showDialog(int contentTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showDialog(contentTextRes, cancelable, singleButtonCallback);
    }

    public void showDialog(String contentText, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showDialog(contentText, cancelable, singleButtonCallback);
    }


    public void showDialog(int titleTextRes, int contentTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showDialog(titleTextRes, contentTextRes, cancelable, singleButtonCallback);
    }


    public void showDialog(int titleTextRes, String contentText, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showDialog(titleTextRes, contentText, cancelable, singleButtonCallback);
    }

    public void showDialog(String titleText, String contentText, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showDialog(titleText, contentText, cancelable, singleButtonCallback);
    }

    public void showDialog(int contentTextRes, int positiveTextRes, int negativeTextRes,
                           boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showDialog(contentTextRes, positiveTextRes, negativeTextRes, cancelable, singleButtonCallback);
    }


    public void showDialog(int titleTextRes, int contentTextRes, int positiveTextRes, int negativeTextRes,
                           boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showDialog(titleTextRes, contentTextRes, positiveTextRes, negativeTextRes, cancelable, singleButtonCallback);
    }


    public void showDialog(int titleTextRes, String contentText, int positiveTextRes, int negativeTextRes,
                           boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showDialog(titleTextRes, contentText, positiveTextRes, negativeTextRes, cancelable, singleButtonCallback);
    }


    public void showDialog(int titleTextRes, int contentTextRes, int positiveTextRes, int negativeTextRes,
                           int neutralTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showDialog(titleTextRes, contentTextRes, positiveTextRes, negativeTextRes, neutralTextRes, cancelable, singleButtonCallback);
    }


    public void showDialog(int titleTextRes, String contentText, int positiveTextRes, int negativeTextRes,
                           int neutralTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showDialog(titleTextRes, contentText, positiveTextRes, negativeTextRes, neutralTextRes, cancelable, singleButtonCallback);
    }


    public void showIndeterminateProgressDialog(boolean cancelable) {
        mDialog = mStrategy.showIndeterminateProgressDialog(cancelable);
    }


    public void showIndeterminateProgressDialog(int contentTextRes, boolean cancelable) {
        mDialog = mStrategy.showIndeterminateProgressDialog(contentTextRes, cancelable);
    }


    public void showIndeterminateProgressDialog(String contentTextRes, boolean cancelable) {
        mDialog = mStrategy.showIndeterminateProgressDialog(contentTextRes, cancelable);
    }


    public void showIndeterminateProgressDialog(boolean horizontal, int contentTextRes, boolean cancelable) {
        mDialog = mStrategy.showIndeterminateProgressDialog(horizontal, contentTextRes, cancelable);
    }


    public void showNoticeDialog(int titleTextRes, String contentText, int positiveTextRes, boolean cancelable,
                                 SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showNoticeDialog(titleTextRes, contentText, positiveTextRes, cancelable, singleButtonCallback);
    }


    public void showSingleChoiceDialog(int titleTextRes, String[] items, int selectedIndex, ListCallbackSingleChoice listCallbackSingleChoice,
                                       boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showSingleChoiceDialog(titleTextRes, items, selectedIndex, listCallbackSingleChoice, cancelable, singleButtonCallback);
    }

    public void showSingleChoiceDialog(int titleTextRes, List<String> items, int selectedIndex, ListCallbackSingleChoice listCallbackSingleChoice,
                                       boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showSingleChoiceDialog(titleTextRes, items, selectedIndex, listCallbackSingleChoice, cancelable, singleButtonCallback);
    }


    public void showSelectListDialog(int itemsRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showSelectListDialog(itemsRes, cancelable, singleButtonCallback);
    }


    public void showSelectListDialog(int titleTextRes, int itemsRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showSelectListDialog(titleTextRes, itemsRes, cancelable, singleButtonCallback);
    }


    public void showStackedDialog(int titleTextRes, int contentTextRes, int positiveTextRes, int negativeTextRes,
                                  boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showStackedDialog(titleTextRes, contentTextRes, positiveTextRes, negativeTextRes, cancelable, singleButtonCallback);
    }

    public void showCustomViewDialog(int customViewLayoutRes, boolean cancelable) {
        mDialog = mStrategy.showCustomViewDialog(customViewLayoutRes, cancelable);
    }

    public void showCustomViewDialog(int titleTextRes, int customViewLayoutRes, boolean cancelable) {
        mDialog = mStrategy.showCustomViewDialog(titleTextRes, customViewLayoutRes, cancelable);
    }

    public MaterialDialog showCustomViewDialog(int titleTextRes, int customViewLayoutRes, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        return mStrategy.showCustomViewDialog(titleTextRes, customViewLayoutRes, positiveTextRes, negativeTextRes, cancelable, singleButtonCallback);
    }

    public MaterialDialog showCustomViewDialog(int titleTextRes, View view, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        return mStrategy.showCustomViewDialog(titleTextRes, view, positiveTextRes, negativeTextRes, cancelable, singleButtonCallback);
    }

    public void showCustomViewDialog(int titleTextRes, View view, int positiveTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback) {
        mDialog = mStrategy.showCustomViewDialog(titleTextRes, view, positiveTextRes, cancelable, singleButtonCallback);
    }

    public void showCustomViewDialog(View view, String title) {
        mDialog = mStrategy.showCustomViewDialog(view, title, true);
    }

    public void showCustomViewDialog(View view) {
        mDialog = mStrategy.showCustomViewDialog(view, true);
    }
//    public View getCustomViewDialog(int customViewLayoutRes, int positiveTextRes, int negativeTextRes,
//                                    boolean cancelable, SingleButtonCallback singleButtonCallback) {
//        return mStrategy.getCustomViewDialog(customViewLayoutRes, positiveTextRes, negativeTextRes, cancelable, singleButtonCallback);
//    }
//
//
//    public View getCustomViewBuilderByView(View customViewLayoutView, int positiveTextRes, int negativeTextRes,
//                                           boolean cancelable, SingleButtonCallback singleButtonCallback) {
//        return mStrategy.getCustomViewBuilderByView(customViewLayoutView, positiveTextRes, negativeTextRes, cancelable, singleButtonCallback);
//    }
}
