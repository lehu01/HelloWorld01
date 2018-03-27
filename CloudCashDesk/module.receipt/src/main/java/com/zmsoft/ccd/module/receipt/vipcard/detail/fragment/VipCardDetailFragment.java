package com.zmsoft.ccd.module.receipt.vipcard.detail.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dfire.sdk.util.MD5Util;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.vipcard.VipCardDetail;
import com.zmsoft.ccd.lib.bean.vipcard.result.VipCardDetailResult;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.lib.utils.language.LanguageUtil;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.lib.widget.EditFoodNumberView;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.constant.ExtraConstants;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptIndustryHelper;
import com.zmsoft.ccd.module.receipt.receipt.model.CardFund;
import com.zmsoft.ccd.receipt.bean.CashPromotionBillResponse;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.Fund;
import com.zmsoft.ccd.receipt.bean.Promotion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description：会员卡详情
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/27 14:11
 */
public class VipCardDetailFragment extends BaseFragment implements VipCardDetailContract.View {


    @BindView(R2.id.text_shop_name)
    TextView mTextShopName;
    @BindView(R2.id.text_card_type_name)
    TextView mTextCardTypeName;
    @BindView(R2.id.text_card_number)
    TextView mTextCardNumber;
    @BindView(R2.id.text_card_discount)
    TextView mTextCardDiscount;
    @BindView(R2.id.text_card_balance)
    TextView mTextCardBalance;
    @BindView(R2.id.text_card_integral)
    TextView mTextCardIntegral;
    @BindView(R2.id.text_card_phone_number)
    TextView mTextCardPhoneNumber;
    @BindView(R2.id.switch_use_balance)
    SwitchCompat mSwitchUseBalance;
    @BindView(R2.id.text_need_pay_money)
    TextView mTextNeedPayMoney;
    @BindView(R2.id.text_receive_money_prompt)
    TextView mTextReceiveMoneyPrompt;
    @BindView(R2.id.text_pass)
    TextView mTextPass;
    @BindView(R2.id.edit_card_pass_word)
    EditText mEditCardPassWord;
    @BindView(R2.id.linear_use_balance)
    LinearLayout mLinearUseBalance;
    @BindView(R2.id.button_ok)
    Button mButtonOk;
    @BindView(R2.id.text_card_account_name)
    TextView mTextCardAccountName;
    @BindView(R2.id.content)
    LinearLayout mContent;
    @BindView(R2.id.image_vip_card_type_icon)
    ImageView mImageVipCardTypeIcon;
    @BindView(R2.id.edit_need_receive_money)
    EditFoodNumberView mEditNeedReceiveMoney;
    @BindView(R2.id.image_vip_card_bg)
    ImageView mImageVipCardBg;
    @BindView(R2.id.linear_vip_card_name)
    LinearLayout mLinearVipCardName;

    private VipCardDetailPresenter mPresenter;
    private String mCardId;
    private String mOrderId;
    private double mNeedPayMoney;
    private double mBalanceMoney;
    private VipCardDetail mVipCardDetail;
    private String mKindPayId;
    /**
     * 应收金额(单位 元)
     */
    private double mFee;

    public static VipCardDetailFragment newInstance(String cardId, String orderId, String kindPayId
            , double fee) {
        VipCardDetailFragment fragment = new VipCardDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RouterPathConstant.VipCardDetail.CARD_ID, cardId);
        bundle.putString(RouterPathConstant.VipCardDetail.ORDER_ID, orderId);
        bundle.putString(RouterPathConstant.VipCardDetail.EXTRA_KIND_PAY_ID, kindPayId);
        bundle.putDouble(ExtraConstants.NormalReceipt.EXTRA_FEE, fee);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_receipt_fragment_vip_card_detail;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initView();
        initIntentData();
    }

    private void initView() {
        mContent.setVisibility(View.GONE);
        mEditNeedReceiveMoney.getEditText().setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        CustomViewUtil.initEditViewFocousAll(mEditNeedReceiveMoney.getEditText());
        mEditNeedReceiveMoney.getEditText().setLayoutParams(ll);
        mEditNeedReceiveMoney.getEditText().setPadding(50, 0, 30, 0);
    }

    private void initIntentData() {
        Bundle bundle = getArguments();
        mCardId = bundle.getString(RouterPathConstant.VipCardDetail.CARD_ID);
        mOrderId = bundle.getString(RouterPathConstant.VipCardDetail.ORDER_ID);
        mKindPayId = bundle.getString(RouterPathConstant.VipCardDetail.EXTRA_KIND_PAY_ID);
        mFee = bundle.getDouble(ExtraConstants.NormalReceipt.EXTRA_FEE);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        showLoadingView();
        getVipCardDetail();
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        getVipCardDetail();
    }

    /**
     * get vip card detail
     */
    private void getVipCardDetail() {
        mPresenter.getVipCardDetail(UserHelper.getEntityId(), mCardId, mOrderId);
    }

    @Override
    protected void initListener() {
        mSwitchUseBalance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    mLinearUseBalance.setVisibility(View.VISIBLE);
                } else {
                    mLinearUseBalance.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(VipCardDetailContract.Presenter presenter) {
        mPresenter = (VipCardDetailPresenter) presenter;
    }

    @OnClick(R2.id.button_ok)
    void ok() {
        if (mSwitchUseBalance.isChecked()) {
            if (mPresenter.check(mBalanceMoney, getNeedPayMoney())) {
                confirmPay(false);
            }
        } else {
            promoteBill();
        }
    }

    private void promoteBill() {
        List<Promotion> promotionList = new ArrayList<>(1);
        Promotion promotion = new Promotion();
        promotion.setType((short) ReceiptHelper.ReceiptPromotion.TYPE_CARD);
        promotion.setClassName(ReceiptHelper.ReceiptPromotion.CLASS_CARD_PROMOTION);
        promotion.setCardId(mVipCardDetail.getCardId());
        promotion.setCardEntityId(mVipCardDetail.getCardEntityId());
        promotionList.add(promotion);
        mPresenter.promoteBill(mOrderId, promotionList);
    }

    private void confirmPay(boolean notCheckPromotion) {
        List<Fund> fundList = new ArrayList<>(1);
        CardFund fund = new CardFund();
        fund.setType((short) ReceiptHelper.PromotionType.VIP_CARD);
        fund.setClassName(ReceiptHelper.ReceiptFund.CLASS_CARD_FUND);
        try {
            fund.setFee((int) Double.parseDouble(FeeHelper.getDecimalFeeByYuan(mEditNeedReceiveMoney.getNumber())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        fund.setCardId(mVipCardDetail.getCardId());
        fund.setCardEntityId(mVipCardDetail.getCardEntityId());
        fund.setCustomerRegisterId(mVipCardDetail.getCustomerRegisterId());
        fund.setKindPayId(mKindPayId);
        fund.setSecret(MD5Util.encode(StringUtils.notNull(getVipCardPassWord())));
        fundList.add(fund);
        mPresenter.collectPay(mOrderId, fundList, notCheckPromotion);
    }

    @Override
    public void alreadyOtherDiscount(String errorMessage) {
        final DialogUtil mDialogUtil = new DialogUtil(getActivity());
        mDialogUtil.showDialog(R.string.module_receipt_prompt, errorMessage
                , R.string.ok
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            confirmPay(true);
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            mDialogUtil.dismissDialog();
                        }
                    }
                });

    }

    @Override
    public void successPromoteBill(CashPromotionBillResponse cashPromotionBillResponse) {
        if (null != cashPromotionBillResponse) {
            List<Promotion> promotions = cashPromotionBillResponse.getPromotions();
            if (null != promotions && !promotions.isEmpty()) {
                Promotion promotion = promotions.get(0);
                if (promotion.getStatus() == ReceiptHelper.PromotionStatus.SUCCESS) {
                    notifyRefresh();
//                    ActivityStackControlUtils.finishSpecActivity(InputVipCardActivity.class);
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                } else {
                    showToast(promotion.getVerifyMessage());
                }
            }
        }
    }

    @Override
    public void successCollectPay(CloudCashCollectPayResponse cloudCashCollectPayResponse) {
        if (null == cloudCashCollectPayResponse) {
            failCollectPay(getString(R.string.module_receipt_fail));
        } else {
            if (cloudCashCollectPayResponse.getStatus() == ReceiptHelper.PayStatus.SUCCESS) {
                //实收 >= 应收 代表已付清,进入收款完成界面；否则回到收款界面
                notifyRefresh();
                if (mEditNeedReceiveMoney.getNumber() - mNeedPayMoney >= 0) {
                    ReceiptIndustryHelper.gotoCompleteReceipt(getActivity(), mOrderId
                            , cloudCashCollectPayResponse.getModifyTime(), mFee);
                } else {
                    notifyRefresh();
//                    ActivityStackControlUtils.finishSpecActivity(InputVipCardActivity.class);
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
            } else if (cloudCashCollectPayResponse.getStatus() == ReceiptHelper.PayStatus.FAILURE) {
                failCollectPay(getString(R.string.module_receipt_fail));
            }
        }
    }

    @Override
    public void failCollectPay(String errorMessage) {
        getDialogUtil().showNoticeDialog(R.string.material_dialog_title
                , errorMessage, R.string.dialog_hint_know, false, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {

                        }
                    }
                });
    }

    @Override
    public String getVipCardPassWord() {
        return mEditCardPassWord.getText().toString().trim();
    }

    @Override
    public String getNeedPayMoney() {
        return mEditNeedReceiveMoney.getEditText().getText().toString().trim();
    }

    @Override
    public void loadVipCardDetailSuccess(VipCardDetailResult vipCardDetail, String errorMessage) {
        if (vipCardDetail != null) {
            mVipCardDetail = vipCardDetail.getCardDetail();
            showContentView();
            mContent.setVisibility(View.VISIBLE);
            updateVipDetailView(vipCardDetail.getCardDetail());
        } else {
            mContent.setVisibility(View.GONE);
            showErrorView(errorMessage);
        }
    }

    /**
     * update view
     */
    private void updateVipDetailView(VipCardDetail vipCardDetail) {
        if (StringUtils.isEmpty(vipCardDetail.getCustomerName()) && StringUtils.isEmpty(vipCardDetail.getMobile())) {
            mLinearVipCardName.setVisibility(View.GONE);
        }
        mTextShopName.setText(vipCardDetail.getShopEntityName());
        mTextCardTypeName.setText(vipCardDetail.getKindCardName());
        mTextCardNumber.setText(vipCardDetail.getCode());

        updateVipCardIcon(vipCardDetail);
        updateDiscount(vipCardDetail);

        mBalanceMoney = vipCardDetail.getBalance() > 0 ? vipCardDetail.getBalance() : 0;
        mTextCardBalance.setText(String.valueOf(vipCardDetail.getBalance()));
        mTextCardIntegral.setText(String.valueOf(vipCardDetail.getDegree()));
        mTextNeedPayMoney.setText(String.valueOf(vipCardDetail.getNeedPayFee()));

        mTextCardAccountName.setText(StringUtils.notNull(vipCardDetail.getCustomerName()));
        mTextCardPhoneNumber.setText(StringUtils.notNull(vipCardDetail.getMobile()));
        mNeedPayMoney = vipCardDetail.getNeedPayFee() > 0 ? vipCardDetail.getNeedPayFee() : 0;
        mEditNeedReceiveMoney.setMaxValue(mNeedPayMoney);//最大值应该是会员卡的余额
        mEditNeedReceiveMoney.setNumberText(mBalanceMoney >= mNeedPayMoney ? mNeedPayMoney : mBalanceMoney);//默认设置会员卡的余额

        ImageLoaderUtil.getInstance().loadImage(StringUtils.notNull(vipCardDetail.getFilePath()), mImageVipCardBg, ImageLoaderOptionsHelper.getCcdVipCardOptions());
    }

    /**
     * 更新会员卡icon：连锁或者单店
     */
    private void updateVipCardIcon(VipCardDetail vipCardDetail) {
        int cardType = vipCardDetail.getCardType();
        switch (cardType) {
            case VipCardDetail.CARD_TYPE_SINGLE:
                mImageVipCardTypeIcon.setImageResource(R.drawable.icon_vip_card_constructions);
                break;
            case VipCardDetail.CARD_TYPE_CHAIN:
                mImageVipCardTypeIcon.setImageResource(R.drawable.icon_vip_card_chain);
                break;
        }
    }

    /**
     * 更新折扣
     */
    private void updateDiscount(VipCardDetail vipCardDetail) {
        int mode = vipCardDetail.getMode();
        switch (mode) {
            case VipCardDetail.VIP_PRICE:
                mTextCardDiscount.setText(UserHelper.getCurrencySymbol());
                break;
            case VipCardDetail.DISCOUNT_WAY:
                mTextCardDiscount.setText(getString(R.string.module_receipt_discount_way));
                break;
            case VipCardDetail.RATIO:
                String text;
                if (LanguageUtil.isChineseGroup()) {
                    text = String.format(getString(R.string.module_receipt_discount_vip), NumberUtils.doubleToStr((double) vipCardDetail.getRatio() / 10));
                } else {
                    text = String.format(getString(R.string.module_receipt_discount_vip), StringUtils.appendStr(100 - vipCardDetail.getRatio(), "%"));
                }
                mTextCardDiscount.setText(text);
                break;
        }
    }

    /**
     * 通知：座位列表，订单列表，订单详情刷新
     */
    private void notifyRefresh() {
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_ORDER_DETAIL);
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }
}
