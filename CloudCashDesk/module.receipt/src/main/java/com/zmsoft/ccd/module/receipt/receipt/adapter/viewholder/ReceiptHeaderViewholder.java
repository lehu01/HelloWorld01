package com.zmsoft.ccd.module.receipt.receipt.adapter.viewholder;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.widget.EditFoodNumberView;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.events.BaseEvents;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptDiscountItem;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptHeadReceivedItem;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptHeadRecyclerItem;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.receipt.bean.CloudCashBill;
import com.zmsoft.ccd.receipt.bean.Pay;
import com.zmsoft.ccd.receipt.bean.Refund;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class ReceiptHeaderViewholder extends ReceiptBaseViewholder {
    @BindView(R2.id.text_consume_fee)
    TextView mTextConsumeFee;
    @BindView(R2.id.layout_consume_fee)
    RelativeLayout mLayoutConsumeFee;
    @BindView(R2.id.text_service_fee)
    TextView mTextServiceFee;
    @BindView(R2.id.layout_service_fee)
    RelativeLayout mLayoutServiceFee;
    @BindView(R2.id.text_delivery_fee)
    TextView mTextDeliveryFee;
    @BindView(R2.id.layout_delivery_fee)
    RelativeLayout mLayoutDeliveryFee;
    @BindView(R2.id.text_minimum_fee)
    TextView mTextMinimumFee;
    @BindView(R2.id.layout_minimum_fee)
    RelativeLayout mLayoutMinimumFee;
    @BindView(R2.id.text_discount_fee)
    TextView mTextDiscountFee;
    @BindView(R2.id.layout_discount_items)
    LinearLayout mLayoutDiscountItems;
    @BindView(R2.id.layout_discount_fee)
    RelativeLayout mLayoutDiscountFee;
    @BindView(R2.id.text_receivable_fee)
    TextView mTextReceivableFee;
    @BindView(R2.id.layout_receivable_fee)
    RelativeLayout mLayoutReceivableFee;
    @BindView(R2.id.layout_received_items)
    LinearLayout mLayoutReceivedItems;
    @BindView(R2.id.layout_received_fee)
    RelativeLayout mLayoutReceivedFee;
    @BindView(R2.id.text_need_fee)
    TextView mTextNeedFee;
    @BindView(R2.id.layout_need_fee)
    RelativeLayout mLayoutNeedFee;
    @BindView(R2.id.text_whole_discount)
    TextView mTextWholdDiscount;
    @BindView(R2.id.text_verification)
    TextView mTextVerification;


    private EditFoodNumberView mEditFoodNumberView;

    private ReceiptHeadRecyclerItem mHeadRecyclerItem;
    private BaseListAdapter.AdapterClick mAdapterClick;

    public ReceiptHeaderViewholder(Context context, View itemView, BaseListAdapter.AdapterClick adapterClick) {
        super(context, itemView);
        this.mAdapterClick = adapterClick;
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mReceiptRecyclerItem) {
            mHeadRecyclerItem = mReceiptRecyclerItem.getHeadRecyclerItem();
            if (null != mHeadRecyclerItem) {
                initItems();
                initSubItems();
            }
        }
        initListener();
    }

    private void initListener() {
        RxView.clicks(mTextWholdDiscount).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (null != mAdapterClick) {
                            mAdapterClick.onAdapterClick(ReceiptHelper.RecyclerViewHolderClickType.DISCOUNT
                                    , mTextWholdDiscount, mHeadRecyclerItem);
                        }
                    }
                });
        RxView.clicks(mTextVerification).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (null != mAdapterClick) {
                            mAdapterClick.onAdapterClick(ReceiptHelper.RecyclerViewHolderClickType.VERIFICATION
                                    , mTextVerification, mHeadRecyclerItem);
                        }
                    }
                });
        RxView.clicks(mTextDiscountFee).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (BatchPermissionHelper.getPermission(Permission.EmptyDiscount.ACTION_CODE)) {
                            getDialogUtil().showDialog(R.string.material_dialog_title,
                                    R.string.module_receipt_clear_discount_alert,
                                    true, new SingleButtonCallback() {
                                        @Override
                                        public void onClick(DialogUtilAction which) {
                                            if (which == DialogUtilAction.POSITIVE) {
                                                EventBusHelper.post(BaseEvents.CommonEvent.RECEIPT_CLEAR_DISCOUNT_EVENT);
                                            }
                                        }
                                    });
                        } else {
                            ToastUtils.showShortToast(mContext, String.format(mContext.getResources().getString(R.string.alert_permission_deny)
                                    , mContext.getResources().getString(R.string.module_receipt_clear_discount)));
                        }
                    }
                });
    }

    private void initItems() {
        CloudCashBill cloudCashBill = mHeadRecyclerItem.getCloudCashBill();
        if (null == cloudCashBill) {
            mLayoutServiceFee.setVisibility(View.GONE);
            mLayoutDeliveryFee.setVisibility(View.GONE);
            mLayoutMinimumFee.setVisibility(View.GONE);
        } else {
            if (cloudCashBill.getServiceFee() == 0) {
                mLayoutServiceFee.setVisibility(View.GONE);
            } else {
                mLayoutServiceFee.setVisibility(View.VISIBLE);
            }
            if (cloudCashBill.getOutFee() == 0) {
                mLayoutDeliveryFee.setVisibility(View.GONE);
            } else {
                mLayoutDeliveryFee.setVisibility(View.VISIBLE);
            }
            if (cloudCashBill.getLeastFee() == 0) {
                mLayoutMinimumFee.setVisibility(View.GONE);
            } else {
                mLayoutMinimumFee.setVisibility(View.VISIBLE);
            }
        }
        mTextConsumeFee.setText(mHeadRecyclerItem.getConsumeFee());
        mTextServiceFee.setText(mHeadRecyclerItem.getServiceFee());
        mTextDeliveryFee.setText(mHeadRecyclerItem.getDeliveryFee());
        mTextMinimumFee.setText(mHeadRecyclerItem.getMinimumFee());
        mTextReceivableFee.setText(mHeadRecyclerItem.getReceivableFee());
        mTextNeedFee.setText(mHeadRecyclerItem.getNeedReceiptFee());
        mTextDiscountFee.setText(mHeadRecyclerItem.getDiscountFee());
        mTextDiscountFee.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_delete, 0);
    }

    private void initSubItems() {
        mLayoutDiscountItems.removeAllViews();
        mLayoutReceivedItems.removeAllViews();
        List<ReceiptDiscountItem> discountItemList = mHeadRecyclerItem.getReceiptDiscountItemList();
        List<ReceiptHeadReceivedItem> receivedItemsList = mHeadRecyclerItem.getReceivedItemList();
        //如果使用了优惠，就算discountItemList是空、优惠金额是0，也要展示优惠金额，如果没使用，隐藏该条目以及优惠金额列表
        CloudCashBill cloudCashBill = mHeadRecyclerItem.getCloudCashBill();
        if (null == cloudCashBill) {
            mLayoutDiscountFee.setVisibility(View.GONE);
            mLayoutDiscountItems.setVisibility(View.GONE);
        } else {
            if (cloudCashBill.isUsePromotion()) {
                mLayoutDiscountFee.setVisibility(View.VISIBLE);
                if (null != discountItemList && !discountItemList.isEmpty()) {
                    mLayoutDiscountItems.setVisibility(View.VISIBLE);
                    for (int i = 0; i < discountItemList.size(); i++) {
                        ReceiptDiscountItem discountItem = discountItemList.get(i);
                        View itemView = LayoutInflater.from(mContext).inflate(R.layout.module_receipt_item_recyclerview_head_subitem
                                , null);
                        TextView nameView = (TextView) itemView.findViewById(R.id.text_name);
                        TextView subNameView = (TextView) itemView.findViewById(R.id.text_sub_name);
                        nameView.setText(discountItem.getDiscountName());
                        switch (discountItem.getDiscountType()) {
                            case ReceiptHelper.PromotionType.VIP_CARD:
                                nameView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_card, 0, 0, 0);
                                break;
                            default:
                                nameView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                break;
                        }
                        subNameView.setText(discountItem.getDiscountRate());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            subNameView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                        } else {
                            subNameView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        }
//                        subNameView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        mLayoutDiscountItems.addView(itemView);
                    }
                } else {
                    mLayoutDiscountItems.setVisibility(View.GONE);
                }
            } else {
                mLayoutDiscountFee.setVisibility(View.GONE);
            }
        }

        if (null != receivedItemsList && !receivedItemsList.isEmpty()) {
            mLayoutReceivedFee.setVisibility(View.VISIBLE);
            mLayoutReceivedItems.setVisibility(View.VISIBLE);
            mLayoutNeedFee.setVisibility(View.VISIBLE);
            for (int i = 0; i < receivedItemsList.size(); i++) {
                ReceiptHeadReceivedItem receivedItem = receivedItemsList.get(i);
                View itemView = LayoutInflater.from(mContext).inflate(R.layout.module_receipt_item_recyclerview_head_subitem, null);
                TextView nameView = (TextView) itemView.findViewById(R.id.text_name);
                TextView subNameView = (TextView) itemView.findViewById(R.id.text_sub_name);
                nameView.setText(receivedItem.getPayName());
                nameView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                subNameView.setText(receivedItem.getPayFee());
                subNameView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_delete, 0);
                final Pay pay = receivedItem.getPay();
                String dialogContent = null;
                if (null != pay) {
                    dialogContent = BusinessHelper.alertDeletePayItem(mContext, pay.getType());
                }
                if (TextUtils.isEmpty(dialogContent)) {
                    dialogContent = mContext.getString(R.string.module_receipt_delete_received_item_alert);
                }
                final String dialogContentFinal = dialogContent;
                RxView.clicks(subNameView).throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                short payType = 0;
                                if (null != pay) {
                                    payType = pay.getType();
                                }
                                if (BusinessHelper.isElectronicPay(payType)) {
                                    if (!BatchPermissionHelper.getPermission(Permission.EPayRefund.ACTION_CODE)) {
                                        ToastUtils.showShortToast(mContext, String.format(mContext.getResources()
                                                        .getString(R.string.alert_permission_deny)
                                                , mContext.getString(R.string.module_receipt_permission_online_pay_refund)));
                                        return;
                                    }
                                } else {
                                    if (!BatchPermissionHelper.getPermission(Permission.ClearPay.ACTION_CODE)) {
                                        ToastUtils.showShortToast(mContext, String.format(mContext.getResources()
                                                        .getString(R.string.alert_permission_deny)
                                                , mContext.getString(R.string.module_receipt_permission_clear_pay)));
                                        return;
                                    }
                                }

                                getDialogUtil().showDialog(R.string.material_dialog_title,
                                        dialogContentFinal,
                                        true, new SingleButtonCallback() {
                                            @Override
                                            public void onClick(DialogUtilAction which) {
                                                if (which == DialogUtilAction.POSITIVE) {
                                                    BaseEvents.CommonEvent event = BaseEvents.CommonEvent.RECEIPT_DELETE_PAY_EVENT;
                                                    List<Refund> refundList = new ArrayList<>(1);
                                                    Refund refund = new Refund();
                                                    refund.setType(pay.getType());
                                                    refund.setPayId(pay.getId());
                                                    refundList.add(refund);
                                                    event.setObject(refundList);
                                                    EventBusHelper.post(event);
                                                }
                                            }
                                        });
                            }
                        });
                mLayoutReceivedItems.addView(itemView);
            }
        } else {
            mLayoutReceivedFee.setVisibility(View.GONE);
            mLayoutReceivedItems.setVisibility(View.GONE);
            mLayoutNeedFee.setVisibility(View.GONE);
        }
    }
}
