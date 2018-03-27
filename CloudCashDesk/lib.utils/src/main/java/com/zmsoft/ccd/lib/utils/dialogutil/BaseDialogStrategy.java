package com.zmsoft.ccd.lib.utils.dialogutil;

import android.app.Dialog;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.ListCallbackSingleChoice;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;

import java.util.List;

/**
 * Dialog显示策略
 *
 * @author DangGui
 * @create 2017/2/6.
 */

public interface BaseDialogStrategy {
    /**
     * @param contentTextRes 正文内容
     * @param cancelable     dialog能否点击隐藏
     */
    Dialog showDialog(int contentTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * @param contentText 正文内容
     */
    Dialog showDialog(String contentText, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * @param titleTextRes   标题内容
     * @param contentTextRes 正文内容
     */
    Dialog showDialog(int titleTextRes, int contentTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * @param titleTextRes 标题内容
     * @param contentText  正文内容
     */
    Dialog showDialog(int titleTextRes, String contentText, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * @param titleText   标题内容
     * @param contentText 正文内容
     */
    Dialog showDialog(String titleText, String contentText, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * @param contentTextRes  正文内容
     * @param positiveTextRes 确定按钮文本
     * @param negativeTextRes 取消按钮文本
     */
    Dialog showDialog(int contentTextRes, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * @param titleTextRes    标题内容
     * @param contentTextRes  正文内容
     * @param positiveTextRes 确定按钮文本
     * @param negativeTextRes 取消按钮文本
     */
    Dialog showDialog(int titleTextRes, int contentTextRes, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * @param titleTextRes    标题内容
     * @param contentText     正文内容
     * @param positiveTextRes 确定按钮文本
     * @param negativeTextRes 取消按钮文本
     */
    Dialog showDialog(int titleTextRes, String contentText, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * @param titleTextRes    标题内容
     * @param contentTextRes  正文内容
     * @param positiveTextRes 确定按钮文本
     * @param negativeTextRes 取消按钮文本
     * @param neutralTextRes  放弃按钮文本
     */
    Dialog showDialog(int titleTextRes, int contentTextRes, int positiveTextRes, int negativeTextRes,
                      int neutralTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * @param titleTextRes    标题内容
     * @param contentText     正文内容
     * @param positiveTextRes 确定按钮文本
     * @param negativeTextRes 取消按钮文本
     * @param neutralTextRes  放弃按钮文本
     */
    Dialog showDialog(int titleTextRes, String contentText, int positiveTextRes, int negativeTextRes,
                      int neutralTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * 加载进度弹出框
     */
    Dialog showIndeterminateProgressDialog(boolean cancelable);

    /**
     * 加载进度弹出框
     *
     * @param contentTextRes 正文内容
     */
    Dialog showIndeterminateProgressDialog(int contentTextRes, boolean cancelable);

    /**
     * 加载进度弹出框
     *
     * @param contentTextRes 正文内容
     */
    Dialog showIndeterminateProgressDialog(String contentTextRes, boolean cancelable);

    /**
     * 加载进度弹出框
     *
     * @param horizontal     true：横向加载进度条  false：圆圈加载进度条
     * @param contentTextRes 正文内容
     */
    Dialog showIndeterminateProgressDialog(boolean horizontal, int contentTextRes, boolean cancelable);


    /**
     * “使用帮助类”弹出框,一般只有一个按钮
     *
     * @param titleTextRes    标题内容
     * @param contentText     正文内容
     * @param positiveTextRes 确定按钮文本
     */
    Dialog showNoticeDialog(int titleTextRes, String contentText, int positiveTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * 单选对话框
     *
     * @param titleTextRes  标题
     * @param selectedIndex 默认选中项
     */
    Dialog showSingleChoiceDialog(int titleTextRes, String[] items, int selectedIndex, ListCallbackSingleChoice listCallbackSingleChoice, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * 单选对话框
     *
     * @param titleTextRes  标题
     * @param selectedIndex 默认选中项
     */
    Dialog showSingleChoiceDialog(int titleTextRes, List<String> items, int selectedIndex, ListCallbackSingleChoice listCallbackSingleChoice, boolean cancelable, SingleButtonCallback singleButtonCallback);


    /**
     * <b>不带标题</b>
     * 选择对话框（eg：删除、复制等情景下的提示框）
     *
     * @param itemsRes 需要显示在对话框里边的item,需在values目录下的arrays.xml配置 string-array
     */
    Dialog showSelectListDialog(int itemsRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * <b>带标题</b>
     * 选择对话框（eg：删除、复制等情景下的提示框）
     *
     * @param titleTextRes 标题
     * @param itemsRes     需要显示在对话框里边的item,需在values目录下的arrays.xml配置 string-array
     */
    Dialog showSelectListDialog(int titleTextRes, int itemsRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * 代标题的提示设置的对话框，（eg：检查到应用没有获取通讯录的权限，提示去设置页设置）
     *
     * @param titleTextRes    标题内容
     * @param contentTextRes  正文内容
     * @param positiveTextRes 确定按钮文本
     * @param negativeTextRes 取消按钮文本
     */
    Dialog showStackedDialog(int titleTextRes, int contentTextRes, int positiveTextRes,
                             int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * 自定义主体内容
     *
     * @param customViewLayoutRes 自定义view layout资源ID
     */
    Dialog showCustomViewDialog(int customViewLayoutRes, boolean cancelable);

    Dialog showCustomViewDialog(View view, String title, boolean cancelable);

    Dialog showCustomViewDialog(View view, boolean cancelable);

    /**
     * 自定义主体内容
     *
     * @param titleTextRes        标题内容
     * @param customViewLayoutRes 自定义view layout资源ID
     */
    Dialog showCustomViewDialog(int titleTextRes, int customViewLayoutRes, boolean cancelable);

    /**
     * 自定义主体内容
     *
     * @param customViewLayoutRes 自定义view layout资源ID
     * @param negativeTextRes     取消按钮文本
     * @param positiveTextRes     确定按钮文本
     */
    MaterialDialog showCustomViewDialog(int titleTextRes, int customViewLayoutRes, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * 自定义主体内容
     *
     * @param view            自定义view
     * @param negativeTextRes 取消按钮文本
     * @param positiveTextRes 确定按钮文本
     */
    MaterialDialog showCustomViewDialog(int titleTextRes, View view, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);

    /**
     * 自定义主体内容
     *
     * @param titleTextRes         标题
     * @param view                 自定义view
     * @param positiveTextRes      确定按钮
     * @param cancelable           是否能取消
     * @param singleButtonCallback 回调
     * @return
     */
    MaterialDialog showCustomViewDialog(int titleTextRes, View view, int positiveTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);
//    /**
//     * 自定义主体内容
//     *
//     * @param customViewLayoutRes 自定义view layout资源ID
//     * @param negativeTextRes     取消按钮文本
//     * @param positiveTextRes     确定按钮文本
//     */
//    View getCustomViewDialog(int customViewLayoutRes, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);
//
//    /**
//     * 自定义主体内容
//     *
//     * @param customViewLayoutView 自定义view
//     * @param negativeTextRes      取消按钮文本
//     * @param positiveTextRes      确定按钮文本
//     */
//    View getCustomViewBuilderByView(View customViewLayoutView, int positiveTextRes, int negativeTextRes, boolean cancelable, SingleButtonCallback singleButtonCallback);
}
