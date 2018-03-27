package com.zmsoft.ccd.module.receipt.vipcard.input.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.vipcard.VipCard;
import com.zmsoft.ccd.lib.bean.vipcard.VipCardDetail;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.utils.language.LanguageUtil;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.constant.ExtraConstants;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.module.receipt.vipcard.detail.VipCardDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 17:26
 */
public class InputVipCardFragment extends BaseFragment implements InputVipCardContract.View {

    @BindView(R2.id.edit_vip_card)
    EditText mEditVipCard;
    @BindView(R2.id.button_ok)
    Button mButtonOk;
    @BindView(R2.id.image_clear)
    ImageView mImageClear;

    private InputVipCardPresenter mPresenter;
    private String mKey;
    private DialogUtil dialogUtil;
    private String mOrderId;
    private String mKindPayId;
    private String mName;
    /**
     * 应收金额(单位 元)
     */
    private double mFee;

    public static InputVipCardFragment newInstance(String orderId, String mKindPayId, String name, double fee) {
        InputVipCardFragment fragment = new InputVipCardFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        bundle.putString(RouterPathConstant.InputVipCar.EXTRA_KIND_PAY_ID, mKindPayId);
        bundle.putString(ExtraConstants.NormalReceipt.EXTRA_TITLE, name);
        bundle.putDouble(ExtraConstants.NormalReceipt.EXTRA_FEE, fee);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_receipt_fragment_input_vip_card;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initIntentData();
        updateTitle();
    }

    private void initIntentData() {
        Bundle bundle = getArguments();
        mOrderId = bundle.getString(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
        mKindPayId = bundle.getString(RouterPathConstant.InputVipCar.EXTRA_KIND_PAY_ID);
        mName = bundle.getString(ExtraConstants.NormalReceipt.EXTRA_TITLE);
        mFee = bundle.getDouble(ExtraConstants.NormalReceipt.EXTRA_FEE);
    }

    private void updateTitle() {
        if (StringUtils.isEmpty(mName)) {
            mName = getString(R.string.module_receipt_vip_card);
        }
        getActivity().setTitle(mName);
    }

    @Override
    protected void initListener() {
        mImageClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditVipCard.setText("");
            }
        });
        mEditVipCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String key = s.toString().trim();
                if (key.length() > 0) {
                    mButtonOk.setEnabled(true);
                    mImageClear.setVisibility(View.VISIBLE);
                } else {
                    mButtonOk.setEnabled(false);
                    mImageClear.setVisibility(View.GONE);
                }
            }
        });
        mEditVipCard.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    ok();
                }
                return false;
            }
        });
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(InputVipCardContract.Presenter presenter) {
        mPresenter = (InputVipCardPresenter) presenter;
    }

    /**
     * </p>
     * 点击事件
     */
    @OnClick(R2.id.button_ok)
    void search() {
        ok();
    }


    /**
     * </p>
     * 确认
     */
    private void ok() {
        mKey = mEditVipCard.getText().toString().trim();
        if (check()) {
            getVipCardList();
        }
    }

    /**
     * </p>
     * check
     */
    private boolean check() {
        if (StringUtils.isEmpty(mKey)) {
            showToast(R.string.module_receipt_vip_card_null);
            return false;
        }
        return true;
    }

    /**
     * </p>
     * 获取会员卡列表
     */
    private void getVipCardList() {
        mPresenter.getVipCardList(UserHelper.getEntityId(), mKey);
    }

    @Override
    public void loadVipCardListSuccess(List<VipCard> list) {
        if (list == null || list.size() == 0) {
            showToast(R.string.module_receipt_no_find_vip_card);
        } else if (list.size() == 1) {
            gotoVipCardDetail(list.get(0));
        } else {
            showVipCarListDialog(list);
        }
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    /**
     * </p>
     * 跳转会员卡详情
     */
    private void gotoVipCardDetail(VipCard vipCard) {
        VipCardDetailActivity.launchActivity(this, mOrderId, mKindPayId, vipCard.getCardId(), mFee);
//        MRouter.getInstance().build(RouterPathConstant.VipCardDetail.PATH)
//                .putString(RouterPathConstant.VipCardDetail.ORDER_ID, mOrderId)
//                .putString(RouterPathConstant.VipCardDetail.EXTRA_KIND_PAY_ID, mKindPayId)
//                .putString(RouterPathConstant.VipCardDetail.CARD_ID, vipCard.getCardId())
//                .navigation(getActivity());
    }

    /**
     * </p>
     * 显示会员卡列表弹窗
     */
    private void showVipCarListDialog(List<VipCard> list) {
        // 弹窗view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.module_receipt_layout_vip_card_list_dialog, null);
        LinearLayoutCompat linearLayout = (LinearLayoutCompat) view.findViewById(R.id.linear_vip_card_item);
        linearLayout.removeAllViews();
        ImageView imageView = (ImageView) view.findViewById(R.id.image_clear);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogUtil != null) {
                    dialogUtil.dismissDialog();
                }
            }
        });
        // 会员卡item
        for (VipCard vipCard : list) {
            View item = LayoutInflater.from(getActivity()).inflate(R.layout.module_receipt_item_vip_card, null);
            TextView textVipCarName = (TextView) item.findViewById(R.id.text_vip_card_name);
            TextView textVipCarMoney = (TextView) item.findViewById(R.id.text_vip_card_money);
            textVipCarMoney.setText(String.format(getString(R.string.module_receipt_balance_money_vip), String.valueOf(vipCard.getBalance())));

            updateItemVipDiscountInfo(vipCard, textVipCarName);

            linearLayout.addView(item);
            final VipCard itemVipCard = vipCard;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoVipCardDetail(itemVipCard);
                }
            });
        }
        // dialog
        if (dialogUtil == null) {
            dialogUtil = getDialogUtil();
        }
        dialogUtil.showCustomViewDialog(view);
    }

    /**
     * 更新item显示会员信息
     */
    private void updateItemVipDiscountInfo(VipCard vipCard, TextView textVipCarName) {
        switch (vipCard.getMode()) {
            case VipCardDetail.VIP_PRICE:
                textVipCarName.setText(StringUtils.appendStr(vipCard.getKindCardName()
                        , String.format(getString(R.string.module_receipt_discount_other_name), UserHelper.getCurrencySymbol())));
                break;
            case VipCardDetail.DISCOUNT_WAY:
                textVipCarName.setText(StringUtils.appendStr(vipCard.getKindCardName()
                        , String.format(getString(R.string.module_receipt_discount_other_name), getString(R.string.module_receipt_discount_way))));
                break;
            case VipCardDetail.RATIO:
                String text;
                if (LanguageUtil.isChineseGroup()) {
                    text = StringUtils.appendStr(vipCard.getKindCardName()
                            , String.format(getString(R.string.module_receipt_brackets_discount_vip), NumberUtils.doubleToStr((double) vipCard.getRatio() / 10)));
                } else {
                    text = StringUtils.appendStr(vipCard.getKindCardName()
                            , String.format(getString(R.string.module_receipt_brackets_discount_vip), StringUtils.appendStr(100 - vipCard.getRatio(), "%")));
                }
                textVipCarName.setText(text);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ReceiptHelper.ACTIVITY_REQUEST_CODE.CODE_RECEIPT_WAY:
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                    break;
                default:
                    break;
            }
        }
    }
}

