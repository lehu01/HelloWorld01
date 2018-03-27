package com.zmsoft.ccd.module.receipt.vipcard.detail.fragment;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.vipcard.result.VipCardDetailResult;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.receipt.model.CashPromotionBillRequest;
import com.zmsoft.ccd.receipt.bean.CashPromotionBillResponse;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.Fund;
import com.zmsoft.ccd.module.receipt.receipt.model.NormalCollectPayRequest;
import com.zmsoft.ccd.receipt.bean.Promotion;
import com.zmsoft.ccd.data.receipt.source.IReceiptSource;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRemoteSource;
import com.zmsoft.ccd.module.receipt.vipcard.source.VipCardSourceRepository;

import java.util.List;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 17:27
 */
public class VipCardDetailPresenter implements VipCardDetailContract.Presenter {

    private VipCardDetailContract.View mView;
    private VipCardSourceRepository mVipCardSourceRepository;
    private final IReceiptSource mIReceiptSource;

    @Inject
    public VipCardDetailPresenter(VipCardDetailContract.View view, VipCardSourceRepository vipCardSourceRepository) {
        this.mView = view;
        this.mVipCardSourceRepository = vipCardSourceRepository;
        mIReceiptSource = new ReceiptRemoteSource();
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void promoteBill(String orderId, List<Promotion> promotionList) {
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        CashPromotionBillRequest cashPromotionBillRequest = new CashPromotionBillRequest(orderId,
                UserHelper.getEntityId(), UserHelper.getUserId(), promotionList);
        String requestJson = JsonMapper.toJsonString(cashPromotionBillRequest);
        mIReceiptSource.promoteBill(requestJson, new Callback<CashPromotionBillResponse>() {
            @Override
            public void onSuccess(CashPromotionBillResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.successPromoteBill(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void collectPay(String orderId, List<Fund> fundList, boolean notCheckPromotion) {
        mView.showLoading(context.getString(R.string.module_receipt_cashing), false);
        NormalCollectPayRequest normalCollectPayRequest = new NormalCollectPayRequest(UserHelper.getUserId()
                , orderId, UserHelper.getEntityId(), UserHelper.getUserId(), notCheckPromotion);
        normalCollectPayRequest.setFunds(fundList);
        String requestJson = JsonMapper.toJsonString(normalCollectPayRequest);
        mIReceiptSource.collectPay(requestJson, new Callback<CloudCashCollectPayResponse>() {
            @Override
            public void onSuccess(CloudCashCollectPayResponse data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.successCollectPay(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (body.getErrorCode().equals("20062")) {
                    mView.alreadyOtherDiscount(context.getString(R.string.module_receipt_payed_other_discount));
                } else {
                    mView.failCollectPay(body.getMessage());
                }
            }
        });

    }

    @Override
    public void getVipCardDetail(String entityId, String cardId, String orderId) {
        mVipCardSourceRepository.getVipCardDetail(entityId, cardId, orderId, new Callback<VipCardDetailResult>() {
            @Override
            public void onSuccess(VipCardDetailResult data) {
                if (mView == null) {
                    return;
                }
                mView.loadVipCardDetailSuccess(data, "");
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.loadVipCardDetailSuccess(null, body.getMessage());
            }
        });
    }

    @Override
    public boolean check(double balanceMoney, String needPayMoney) {
        try {
            if (balanceMoney == 0) {
                mView.loadDataError(context.getString(R.string.module_receipt_balance_not_enough));
                return false;
            }
            double needPay = Double.parseDouble(needPayMoney);
            if (needPay == 0) {
                mView.loadDataError(context.getString(R.string.module_receipt_revenue_must_more_than_zero));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
