package com.zmsoft.ccd.module.receipt.receipt.helper;

import android.content.Context;

import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.module.receipt.complete.view.CompleteReceiptActivity;
import com.zmsoft.ccd.module.receipt.complete.view.RetailCompleteReceiptActivity;
import com.zmsoft.ccd.shop.bean.IndustryType;

/**
 * 不同类型付款方式根据行业不同跳转到不同的收款完成页面
 * Created by huaixi on 2017/11/13.
 */

public class ReceiptIndustryHelper {

    public static void gotoCompleteReceipt(Context context, String orderId, long modifyTime, double receiveableFee) {

        switch (UserHelper.getIndustry()) {
            case IndustryType.RETAIL:
                RetailCompleteReceiptActivity.launchActivity(context, orderId, modifyTime, receiveableFee);
                break;
            default:
                CompleteReceiptActivity.launchActivity(context, orderId, modifyTime, receiveableFee);
        }
    }
}
