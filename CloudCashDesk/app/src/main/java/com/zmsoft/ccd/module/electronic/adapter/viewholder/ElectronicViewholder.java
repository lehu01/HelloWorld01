package com.zmsoft.ccd.module.electronic.adapter.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.electronic.ElePaymentNormalItem;
import com.zmsoft.ccd.module.electronic.helper.ElectronicHelper;

import java.util.List;

import butterknife.BindView;

/**
 * 电子收款明细
 *
 * @author DangGui
 * @create 2017/08/12.
 */

public class ElectronicViewholder extends BaseElectronicViewholder {

    @BindView(R.id.text_order_id)
    TextView mTextOrderId;
    @BindView(R.id.text_desk_id)
    TextView mTextDeskId;
    @BindView(R.id.image_logo)
    ImageView mImageLogo;
    @BindView(R.id.text_receipt_source)
    TextView mTextReceiptSource;
    @BindView(R.id.text_receipt_fee)
    TextView mTextReceiptFee;
    @BindView(R.id.text_time)
    TextView mTextTime;
    @BindView(R.id.text_status)
    TextView mTextStatus;
    @BindView(R.id.text_unit)
    TextView mTextUnit;

    private ElePaymentNormalItem mNormalItem;

    public ElectronicViewholder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        mNormalItem = mElePaymentItem.getElePaymentNormalItem();
        if (null == mNormalItem) {
            return;
        }
        initInfoView();
    }

    private void initInfoView() {
        if (!TextUtils.isEmpty(mNormalItem.getOrderCode())) {
            mTextOrderId.setText(mNormalItem.getOrderCode());
        } else {
            mTextOrderId.setText("-");
        }
        if (!TextUtils.isEmpty(mNormalItem.getSeatCode())) {
            mTextDeskId.setVisibility(View.VISIBLE);
            mTextDeskId.setText(String.format(mContext.getResources().getString(R.string.desk_msg_deskid)
                    , mNormalItem.getSeatCode()));
        } else {
            mTextDeskId.setVisibility(View.GONE);
        }
        switch (mNormalItem.getPayType()) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_WEIXIN:
                mImageLogo.setImageResource(R.drawable.icon_wechat);
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALIPAY:
                mImageLogo.setImageResource(R.drawable.icon_alipay);
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_QQ:
                mImageLogo.setImageResource(R.drawable.icon_pay_from_qq);
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VIP:
                mImageLogo.setImageResource(R.drawable.icon_pay_from_vip_car);
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_PROMOTION_MARKET_CENTER:
                mImageLogo.setImageResource(R.drawable.ic_local_promotion);
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_PROMOTION_MARKET_EXCHANGE_SINGLE_COUPON:
                mImageLogo.setImageResource(R.drawable.ic_single_coupon);
                break;
            default:
                mImageLogo.setImageResource(R.drawable.icon_pay_from_default);
                break;
        }
        if (!TextUtils.isEmpty(mNormalItem.getReceiptName())) {
            mTextReceiptSource.setText(mNormalItem.getReceiptName());
        } else {
            mTextReceiptSource.setText("-");
        }
        mTextUnit.setText(UserHelper.getCurrencySymbol());
        if (!TextUtils.isEmpty(mNormalItem.getReceiptFee())) {
            mTextReceiptFee.setText(mNormalItem.getReceiptFee());
        } else {
            mTextReceiptFee.setText("-");
        }
        if (!TextUtils.isEmpty(mNormalItem.getTime())) {
            mTextTime.setText(mNormalItem.getTime());
        } else {
            mTextTime.setText("-");
        }

        if (mNormalItem.getPayStatus() == ElectronicHelper.ReceiptStatus.PAY_SUCCESS
                || mNormalItem.getPayStatus() == ElectronicHelper.ReceiptStatus.PAY_SUCCESS_BUT_REFUNDED) {
            mTextStatus.setText(R.string.electronic_receipt_pay_success);
            mTextStatus.setTextColor(ContextCompat.getColor(mContext, R.color.common_front_green));
            mTextStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_green_sure, 0, 0, 0);
        } else if (mNormalItem.getPayStatus() == ElectronicHelper.ReceiptStatus.REFUND_SUCCESS) {
            mTextStatus.setText(R.string.electronic_receipt_refund_success);
            mTextStatus.setTextColor(ContextCompat.getColor(mContext, R.color.accentColor));
            mTextStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_checked, 0, 0, 0);
        } else {
            mTextStatus.setText("");
        }
    }
}
