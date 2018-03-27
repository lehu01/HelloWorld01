package com.zmsoft.ccd.data.source.electronic;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentListResponse;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentResponse;

/**
 * 获取电子支付明细列表
 *
 * @author DangGui
 * @create 2017/3/4.
 */

public interface IElectronicSource {
    /**
     * 获取电子支付明细列表
     *
     * @param pageSize  单页数量
     * @param pageIndex 分页index
     * @param callback
     */
    void getElePaymentList(final int pageIndex, int pageSize, final Callback<GetElePaymentListResponse> callback);

    /**
     * 获取电子收款凭证
     *
     * @param payId
     * @param callback
     */
    void getElePaymentDetail(String payId, final Callback<GetElePaymentResponse> callback);
}
