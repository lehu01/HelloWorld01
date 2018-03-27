package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.viewholder;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerRemarkItem;
import com.zmsoft.ccd.module.menu.events.BaseEvents;

import java.util.List;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class RetailCartDetailRemarkItemViewHolder extends BaseCartDetailViewHolder {
    @BindView(R2.id.view_remark_divider)
    View mViewRemarkDivider;
    @BindView(R2.id.view_standby_divider)
    View mViewStandbyDivider;
    @BindView(R2.id.text_standby)
    TextView mTextStandby;
    @BindView(R2.id.checkbox_standby)
    SwitchCompat mCheckboxStandby;
    @BindView(R2.id.view_presenter_divider)
    View mViewPresenterDivider;
    @BindView(R2.id.text_presenter)
    TextView mTextPresenter;
    @BindView(R2.id.checkbox_presenter)
    SwitchCompat mCheckboxPresenter;
    @BindView(R2.id.view_bottom_divider)
    View mViewBottomDivider;
    @BindView(R2.id.layout_standby)
    LinearLayout mLayoutStandby;
    @BindView(R2.id.layout_presenter)
    LinearLayout mLayoutPresenter;
    @BindView(R2.id.edit_memo)
    EditText mEditMemo;
    private CartDetailRecyclerRemarkItem mCartDetailRecyclerRemarkItem;

    public RetailCartDetailRemarkItemViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mCartDetailRecyclerItem) {
            mCartDetailRecyclerRemarkItem = mCartDetailRecyclerItem.getCartDetailRecyclerRemarkItem();
            if (null != mCartDetailRecyclerRemarkItem) {
                if (mCartDetailRecyclerRemarkItem.isShowStandBy()) {
                    mViewStandbyDivider.setVisibility(View.VISIBLE);
                    mLayoutStandby.setVisibility(View.VISIBLE);
                    mCheckboxStandby.setChecked(mCartDetailRecyclerRemarkItem.isStandBy());
                } else {
                    mViewStandbyDivider.setVisibility(View.GONE);
                    mLayoutStandby.setVisibility(View.GONE);
                }
                if (mCartDetailRecyclerRemarkItem.isShowPresenterFood()) {
                    mViewPresenterDivider.setVisibility(View.VISIBLE);
                    mLayoutPresenter.setVisibility(View.VISIBLE);
                    mCheckboxPresenter.setChecked(mCartDetailRecyclerRemarkItem.isPresenterFood());
                } else {
                    mViewPresenterDivider.setVisibility(View.GONE);
                    mLayoutPresenter.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(mCartDetailRecyclerRemarkItem.getMemo())) {
                    mEditMemo.setText(mCartDetailRecyclerRemarkItem.getMemo());
                    mEditMemo.setSelection(mEditMemo.getText().length());
                } else {
                    mEditMemo.setText("");
                }
            }
        }
        mEditMemo.setVisibility(View.GONE);
        mLayoutStandby.setVisibility(View.GONE);
        initListener();
    }

    private void initListener() {
        mCheckboxStandby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCartDetailRecyclerRemarkItem.setStandBy(isChecked);
                EventBusHelper.post(BaseEvents.CommonEvent.CART_DETAIL_MODIFY);
            }
        });
        mCheckboxPresenter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (BatchPermissionHelper.getPermission(Permission.PresentFood.ACTION_CODE)) {
                        mCartDetailRecyclerRemarkItem.setPresenterFood(isChecked);
                        EventBusHelper.post(BaseEvents.CommonEvent.CART_DETAIL_MODIFY);
                    } else {
                        ToastUtils.showShortToast(mContext, R.string.module_menu_retail_cart_present_food_not_permission);
                        buttonView.setChecked(!isChecked);
                    }
//                    mCartDetailRecyclerRemarkItem.setPresenterFood(isChecked);
//                    EventBusHelper.post(BaseEvents.CommonEvent.CART_DETAIL_MODIFY);
//                    //通知界面调用检查权限接口，判断用户是否有“赠送这个菜”的权限
//                    EventBusHelper.post(BaseEvents.CommonEvent.CART_DETAIL_SWITCH_PRESENT_FOOD);
                }
            }
        });
        mEditMemo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCartDetailRecyclerRemarkItem.setMemo(s.toString());
                EventBusHelper.post(BaseEvents.CommonEvent.CART_DETAIL_MODIFY);
            }
        });
    }
}
