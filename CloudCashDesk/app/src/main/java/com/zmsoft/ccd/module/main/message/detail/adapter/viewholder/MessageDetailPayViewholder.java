package com.zmsoft.ccd.module.main.message.detail.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.MessageDetailHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailPayInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailRecyclerItem;

import butterknife.BindView;

/**
 * 支付消息item
 *
 * @author DangGui
 * @create 2016/4/8.
 */

public class MessageDetailPayViewholder extends MessageDetailBaseViewholder {
    @BindView(R.id.image_pay_info_icon)
    ImageView mImagePayInfoIcon;
    @BindView(R.id.text_pay_info)
    TextView mTextPayInfo;
    private DeskMsgDetailPayInfoRecyclerItem mPayItem;

    public MessageDetailPayViewholder(Context context, View itemView, RecyclerView recyclerView
            , RecyclerView.Adapter adapter) {
        super(context, itemView, recyclerView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, DeskMsgDetailRecyclerItem msgDetailRecyclerItemObj
            , int position) {
        super.initView(holder, msgDetailRecyclerItemObj, position);
        mPayItem = msgDetailRecyclerItemObj.getPayInfoRecyclerItem();
        if (null == mPayItem)
            return;
        initInfoView();
        initListener(position);
    }

    protected void initInfoView() {
        if (!TextUtils.isEmpty(mPayItem.getPayInfoContent())) {
            mTextPayInfo.setText(mPayItem.getPayInfoContent());
            mTextPayInfo.setVisibility(View.VISIBLE);
        } else {
            mTextPayInfo.setVisibility(View.GONE);
        }
        switch (mPayItem.getPaymentMethod()) {
            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_ALIPAY:
                mImagePayInfoIcon.setImageResource(R.drawable.icon_alipay);
                break;
            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_VIP_CARD:
                mImagePayInfoIcon.setImageResource(R.drawable.icon_pay_from_vip_car);
                break;
            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_YIN_LIAN:
                mImagePayInfoIcon.setImageResource(R.drawable.icon_pay_from_bank);
                break;
            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_CASH_MONEY:
                mImagePayInfoIcon.setImageResource(R.drawable.icon_pay_from_cash_money);
                break;
            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_WEIXIN:
                mImagePayInfoIcon.setImageResource(R.drawable.icon_wechat);
                break;
            case MessageDetailHelper.PaymentMethod.PAYMENT_METHOD_QQ:
                mImagePayInfoIcon.setImageResource(R.drawable.icon_pay_from_qq);
                break;
            default:
                mImagePayInfoIcon.setImageResource(R.drawable.icon_pay_from_default);
                break;
        }
    }
}
