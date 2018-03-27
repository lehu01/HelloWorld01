package com.zmsoft.ccd.data.source.shortcutreceipt;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.shortcutreceipt.ShortCutReceiptResponse;

/**
 * @author DangGui
 * @create 2017/8/2.
 */

public interface IShortCutReceiptSource {
    /**
     * 快捷收款
     *
     * @param fee      收款金额
     * @param callback
     */
    void shortCutReceipt(int fee, final Callback<ShortCutReceiptResponse> callback);
}
