package com.zmsoft.ccd.module.takeout.delivery.adapter.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.R2;
import com.zmsoft.ccd.module.takeout.delivery.adapter.items.DeliveryOrderItem;
import com.zmsoft.ccd.module.takeout.delivery.helper.DeliveryHelper;
import com.zmsoft.ccd.takeout.bean.TakeoutConstants;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 配送订单信息
 *
 * @author DangGui
 * @create 2016/12/21.
 */

public class DeliveryOrderViewholder extends BaseDeliveryViewholder {

    @BindView(R2.id.iamge_takeout_order_address)
    ImageView mIamgeTakeoutOrderAddress;
    @BindView(R2.id.text_takeout_order_taker)
    TextView mTextTakeoutOrderTaker;
    @BindView(R2.id.layout_takeout_order_taker)
    LinearLayout mLayoutTakeoutOrderTaker;
    @BindView(R2.id.text_takeout_order_phone)
    TextView mTextTakeoutOrderPhone;
    @BindView(R2.id.text_takeout_order_address)
    TextView mTextTakeoutOrderAddress;
    @BindView(R2.id.text_takeout_order_original)
    TextView mTextTakeoutOrderOriginal;
    @BindView(R2.id.text_takeout_delivery_order_foods)
    TextView mTextTakeoutDeliveryOrderFoods;
    @BindView(R2.id.text_takeout_delivery_order_foods_total_num)
    TextView mTextTakeoutDeliveryOrderFoodsToalNum;
    @BindView(R2.id.text_takeout_delivery_order_no)
    TextView mTextTakeoutDeliveryOrderNo;
    @BindView(R2.id.text_delivery_method)
    TextView mTextTakeoutDeliveryMethod;

    private DeliveryOrderItem mDeliveryOrderItem;
    private BaseListAdapter.AdapterClick mAdapterClick;

    public DeliveryOrderViewholder(Context context, View itemView, BaseListAdapter.AdapterClick adapterClick) {
        super(context, itemView);
        this.mAdapterClick = adapterClick;
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        mDeliveryOrderItem = mDeliveryOrderListItem.getDeliveryOrderItem();
        if (null != mDeliveryOrderItem) {
            if (!TextUtils.isEmpty(mDeliveryOrderItem.getUserName())) {
                mTextTakeoutOrderTaker.setText(mDeliveryOrderItem.getUserName());
            } else {
                mTextTakeoutOrderTaker.setText("-");
            }
            if (!TextUtils.isEmpty(mDeliveryOrderItem.getUserPhone())) {
                mTextTakeoutOrderPhone.setText(mDeliveryOrderItem.getUserPhone());
                mTextTakeoutOrderPhone.setVisibility(View.VISIBLE);
            } else {
                mTextTakeoutOrderPhone.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mDeliveryOrderItem.getUserAddress())) {
                mTextTakeoutOrderAddress.setText(mDeliveryOrderItem.getUserAddress());
                mTextTakeoutOrderAddress.setVisibility(View.VISIBLE);
            } else {
                mTextTakeoutOrderAddress.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mDeliveryOrderItem.getOrderFrom())) {
                if (mDeliveryOrderItem.getOrderFromType() != TakeoutConstants.OrderFrom.XIAOER
                        && !TextUtils.isEmpty(mDeliveryOrderItem.getOutId())) {
                    String stringBuilder = mDeliveryOrderItem.getOrderFrom() +
                            "-" +
                            mDeliveryOrderItem.getOutId();
                    mTextTakeoutOrderOriginal.setText(stringBuilder);
                } else {
                    mTextTakeoutOrderOriginal.setText(mDeliveryOrderItem.getOrderFrom());
                }
            } else {
                mTextTakeoutOrderOriginal.setText("");
            }
            switch (mDeliveryOrderItem.getOrderFromType()) {
                case TakeoutConstants.OrderFrom.XIAOER:
                case TakeoutConstants.OrderFrom.WEIDIAN:
                    mTextTakeoutOrderOriginal.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_from_xiaoer, 0, 0);
                    break;
                case TakeoutConstants.OrderFrom.MEITUAN:
                    mTextTakeoutOrderOriginal.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_from_meituan, 0, 0);
                    break;
                case TakeoutConstants.OrderFrom.BAIDU:
                    mTextTakeoutOrderOriginal.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_from_baidu, 0, 0);
                    break;
                case TakeoutConstants.OrderFrom.ERLEME:
                    mTextTakeoutOrderOriginal.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_from_eleme, 0, 0);
                    break;
                default:
                    mTextTakeoutOrderOriginal.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    break;
            }
            if (!TextUtils.isEmpty(mDeliveryOrderItem.getFoodsName())) {
                mTextTakeoutDeliveryOrderFoods.setVisibility(View.VISIBLE);
                mTextTakeoutDeliveryOrderFoods.setText(mDeliveryOrderItem.getFoodsName());
            } else {
                mTextTakeoutDeliveryOrderFoods.setVisibility(View.GONE);
            }
            mTextTakeoutDeliveryOrderFoodsToalNum.setText(mDeliveryOrderItem.getFoodsTotalNum());
            if (!TextUtils.isEmpty(mDeliveryOrderItem.getOrderCode())) {
                mTextTakeoutDeliveryOrderNo.setVisibility(View.VISIBLE);
                SpannableStringUtils.Builder builder = SpannableStringUtils.getBuilder(mContext
                        , mContext.getResources().getString(R.string.module_takeout_delivery_code_no));
                builder.append(mDeliveryOrderItem.getOrderCode()).setProportion(2);
                mTextTakeoutDeliveryOrderNo.setText(builder.create());
            } else {
                mTextTakeoutDeliveryOrderNo.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mDeliveryOrderItem.getDeliveryMethod())) {
                mTextTakeoutDeliveryMethod.setText(mDeliveryOrderItem.getDeliveryMethod());
                mTextTakeoutDeliveryMethod.setTextColor(ContextCompat.getColor(mContext, R.color.normal_bule));
            } else {
                mTextTakeoutDeliveryMethod.setText(R.string.module_takeout_delivery_method_default);
                mTextTakeoutDeliveryMethod.setTextColor(ContextCompat.getColor(mContext, R.color.accentColor));
            }
        }
        initListener();
    }

    private void initListener() {
        //防止按钮重复点击
        RxView.clicks(mTextTakeoutDeliveryMethod).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (null != mAdapterClick) {
                            mAdapterClick.onAdapterClick(DeliveryHelper.RecyclerViewHolderClickType.DELIVERY_METHOD
                                    , mTextTakeoutDeliveryMethod, null);
                        }
                    }
                });
    }
}
