package com.zmsoft.ccd.helper;

import android.content.Context;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.widget.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;

/**
 * 公共帮助类
 *
 * @author DangGui
 * @create 2017/7/4.
 */

public class CommonHelper {
    /**
     * 已下班状态操作订单/消息的话，弹框提示
     *
     * @param context
     * @param dialogUtil
     */
    public static void showOffWorkDialog(Context context, DialogUtil dialogUtil) {
        dialogUtil.showNoticeDialog(R.string.material_dialog_title
                , context.getResources().getString(R.string.account_off_work_alert), R.string.dialog_hint_know
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                        }
                    }
                });
    }
}
