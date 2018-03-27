package com.zmsoft.ccd.module.menu.cart.adapter.cartlist.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartOrderRecyclerItem;
import com.zmsoft.ccd.module.menu.events.BaseEvents;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * @author DangGui
 * @create 2017/4/13.
 */

public class CartOrderItemViewholder extends CartBaseViewholder {
    @BindView(R2.id.view_divider)
    View mViewDivider;
    @BindView(R2.id.text_modify)
    TextView mTextModify;
    @BindView(R2.id.text_desk_id)
    TextView mTextDeskId;
    @BindView(R2.id.text_people_count)
    TextView mTextPeopleCount;
    @BindView(R2.id.text_order_remark)
    TextView mTextOrderRemark;
    @BindView(R2.id.view_summary_divider)
    View mViewSummaryDivider;
    @BindView(R2.id.text_summary)
    TextView mTextSummary;
    @BindView(R2.id.text_clear)
    TextView mTextClear;
    @BindView(R2.id.layout_orderinfo)
    LinearLayout mLayoutOrderinfo;
    @BindView(R2.id.layout_cart_empty)
    LinearLayout mLayoutEmpty;
    @BindView(R2.id.text_empty_title)
    TextView mTextEmptyTitle;
    @BindView(R2.id.text_empty_sub_content)
    TextView mTextEmptySubContent;
    private CartOrderRecyclerItem mCartOrderRecyclerItem;

    public CartOrderItemViewholder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mCartRecyclerItem) {
            mCartOrderRecyclerItem = mCartRecyclerItem.getCartOrderRecyclerItem();
            if (null != mCartOrderRecyclerItem) {
                if (!mCartOrderRecyclerItem.isCartExpired()) {
                    if (!mCartOrderRecyclerItem.isCartEmpty()) {
                        mLayoutOrderinfo.setVisibility(View.VISIBLE);
                        mLayoutEmpty.setVisibility(View.GONE);
                        //桌号
                        if (!TextUtils.isEmpty(mCartOrderRecyclerItem.getSeatName())) {
                            mTextDeskId.setText(String.format(mContext.getResources()
                                    .getString(R.string.seat_code), mCartOrderRecyclerItem.getSeatName()));
                        } else {
                            mTextDeskId.setText(String.format(mContext.getResources()
                                    .getString(R.string.seat_code), mContext.getResources()
                                    .getString(R.string.value_empty)));
                        }
                        //人数
                        if (mCartOrderRecyclerItem.getPeopleCount() >= 0) {
                            mTextPeopleCount.setText(String.format(mContext.getResources()
                                    .getString(R.string.people_count), mCartOrderRecyclerItem.getPeopleCount() + ""));
                        } else {
                            mTextPeopleCount.setText(String.format(mContext.getResources()
                                    .getString(R.string.people_count), mContext.getResources()
                                    .getString(R.string.value_empty)));
                        }
                        mTextModify.setVisibility(mCartOrderRecyclerItem.isCanModifyOrder() ? View.VISIBLE : View.INVISIBLE);
                        //备注
                        if (!TextUtils.isEmpty(mCartOrderRecyclerItem.getRemark())) {
                            SpannableStringUtils.Builder builder = SpannableStringUtils
                                    .getBuilder(mContext, mContext.getResources()
                                            .getString(R.string.remark));
                            builder.append(": ");
                            builder.append(mCartOrderRecyclerItem.getRemark());
                            mTextOrderRemark.setVisibility(View.VISIBLE);
                            mTextOrderRemark.setText(builder.create());
                        } else {
                            mTextOrderRemark.setVisibility(View.GONE);
                        }
                        //合计
                        SpannableStringUtils.Builder builder = SpannableStringUtils
                                .getBuilder(mContext, "");
                        if (!TextUtils.isEmpty(mCartOrderRecyclerItem.getSummaryFoodInfo())) {
                            builder.append(mContext.getString(R.string.module_menu_cart_summary_label))
                                    .setForegroundColor(ContextCompat.getColor(mContext, R.color.light_blue));
                            builder.append(" ");
                            builder.append(mCartOrderRecyclerItem.getSummaryFoodInfo())
                                    .setForegroundColor(ContextCompat.getColor(mContext, R.color.light_blue));
                        }
                        if (!TextUtils.isEmpty(mCartOrderRecyclerItem.getSummaryAmount())) {
                            builder.append(mContext.getString(R.string.module_menu_cart_summary_amount)
                                    + UserHelper.getCurrencySymbol())
                                    .setForegroundColor(ContextCompat.getColor(mContext, R.color.accentColor));
                            builder.append(mCartOrderRecyclerItem.getSummaryAmount())
                                    .setForegroundColor(ContextCompat.getColor(mContext, R.color.accentColor));
                        }
                        SpannableStringBuilder spannableStringBuilder = builder.create();
                        if (spannableStringBuilder.length() > 0) {
                            mTextSummary.setVisibility(View.VISIBLE);
                            mTextSummary.setText(spannableStringBuilder);
                        } else {
                            mTextSummary.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        mLayoutOrderinfo.setVisibility(View.GONE);
                        mLayoutEmpty.setVisibility(View.VISIBLE);
                    }
                } else {
                    mLayoutOrderinfo.setVisibility(View.GONE);
                    mLayoutEmpty.setVisibility(View.VISIBLE);
                    mTextEmptyTitle.setText(R.string.module_menu_cart_expired_title);
                    mTextEmptySubContent.setText(R.string.module_menu_cart_expired_subcontent);
                }
            }
        }
        initListener();
    }

    private void initListener() {
        RxView.clicks(mTextClear).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        getDialogUtil().showDialog(R.string.material_dialog_title,
                                R.string.module_menu_cart_clear_hint,
                                true, new SingleButtonCallback() {
                                    @Override
                                    public void onClick(DialogUtilAction which) {
                                        if (which == DialogUtilAction.POSITIVE) {
                                            EventBusHelper.post(BaseEvents.CommonEvent.CART_CLEAR_EVENT);
                                        }
                                    }
                                });
                    }
                });
    }

    @OnClick({R2.id.text_modify})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.text_modify) {
            EventBusHelper.post(BaseEvents.CommonEvent.CART_TO_MODIFY_ORDER);
        }
    }
}
