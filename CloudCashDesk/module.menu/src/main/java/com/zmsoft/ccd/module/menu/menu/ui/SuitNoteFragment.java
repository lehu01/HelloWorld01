package com.zmsoft.ccd.module.menu.menu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenuHitRule;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitMenuVO;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description：套餐介绍Fragment
 * <br/>
 * Created by kumu on 2017/4/19.
 */
public class SuitNoteFragment extends BaseFragment {
    @BindView(R2.id.text_suit_name)
    TextView mTextSuitName;
    @BindView(R2.id.text_suit_price)
    TextView mTextSuitPrice;
    @BindView(R2.id.text_suit_desc)
    TextView mTextSuitDesc;
    @BindView(R2.id.text_valuation_rule_label)
    TextView mTextValuationRuleLabel;
    @BindView(R2.id.linear_suit_rule)
    LinearLayout mLinearSuitRule;

    @BindView(R2.id.edit_suit_note)
    EditText mEditSuitNote;
    @BindView(R2.id.edit_suit_serving_switch)
    SwitchCompat mEditSuitServingSwitch;
    @BindView(R2.id.text_confirm)
    TextView mTextConfirm;
    @BindView(R2.id.layout_rule_and_desc)
    View mLayoutRuleAndDesc;


    private SuitMenuVO mSuitMenuVO;
    private int isWait;

    @Override
    protected int getLayoutId() {
        return R.layout.module_menu_fragment_suite_note;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            mSuitMenuVO = bundle.getParcelable(RouterPathConstant.SuitNote.PARAM_SUITMENOVO);
            if (mSuitMenuVO == null) {
                getActivity().finish();
                return;
            }
            mTextSuitName.setText(mSuitMenuVO.getMenuName());

            mTextSuitPrice.setText(getString(R.string.module_menu_list_suit_detail_price,
                    FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , NumberUtils.getDecimalFee(mSuitMenuVO.getMenuPrice(), 2))));

            if (TextUtils.isEmpty(mSuitMenuVO.getDesc())) {
                mTextSuitDesc.setVisibility(View.GONE);
            } else {
                mTextSuitDesc.setText(mSuitMenuVO.getDesc());
            }
            mEditSuitNote.setText(mSuitMenuVO.getMemo());
            mEditSuitNote.setSelection(mEditSuitNote.getText().length());
            if (mSuitMenuVO.getSuitMenuHitRules() != null && !mSuitMenuVO.getSuitMenuHitRules().isEmpty()) {
                mTextValuationRuleLabel.setVisibility(View.VISIBLE);
                mLinearSuitRule.setVisibility(View.VISIBLE);
                LayoutInflater inflater = LayoutInflater.from(getContext());
                for (SuitMenuHitRule rule : mSuitMenuVO.getSuitMenuHitRules()) {
                    View itemView = inflater.inflate(R.layout.module_menu_item_suit_rule, mLinearSuitRule, false);
                    TextView ruleName = (TextView) itemView.findViewById(R.id.text_valuation_rule_name);
                    TextView ruleValue = (TextView) itemView.findViewById(R.id.text_valuation_rule_value);
                    ruleName.setText(rule.getName());
                    if (rule.getFloatPrice() > 0) {
                        ruleValue.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , NumberUtils.getDecimalFee(rule.getFloatPriceYuan(), 2)));
                    } else {
                        ruleValue.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , NumberUtils.getDecimalFee(-rule.getFloatPriceYuan(), 2), true));
                    }
                    mLinearSuitRule.addView(itemView);
                }
            } else {
                mTextValuationRuleLabel.setVisibility(View.GONE);
                mLinearSuitRule.setVisibility(View.GONE);
            }
            if (mTextSuitDesc.getVisibility() == View.GONE && mTextValuationRuleLabel.getVisibility() == View.GONE) {
                mLayoutRuleAndDesc.setVisibility(View.GONE);
            }

            mEditSuitServingSwitch.setChecked(mSuitMenuVO.getIsWait() == CartHelper.ServeFoodKind.KIND_STANDBY);
            isWait = mSuitMenuVO.getIsWait();
            mEditSuitServingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mSuitMenuVO.setIsWait(isChecked ? (short) CartHelper.ServeFoodKind.KIND_STANDBY
                            : (short) CartHelper.ServeFoodKind.KIND_NOW);
                }
            });
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {

    }

    @OnClick(R2.id.text_confirm)
    void processClick(View view) {
        if (view.getId() == R.id.text_confirm) {
            back();
        }
    }

    private void back() {
        if (mSuitMenuVO != null) {
            if (!mEditSuitNote.getText().toString().trim().equals(mSuitMenuVO.getMemo())) {
                mSuitMenuVO.setHasChanged(1);
            }

            if (isWait != mSuitMenuVO.getIsWait()) {
                mSuitMenuVO.setHasChanged(1);
            }

            mSuitMenuVO.setMemo(mEditSuitNote.getText().toString());
            Intent intent = new Intent();
            intent.putExtra(RouterPathConstant.SuitNote.PARAM_SUITMENOVO, mSuitMenuVO);
            getActivity().setResult(Activity.RESULT_OK, intent);
        }
        getActivity().finish();
    }

}
