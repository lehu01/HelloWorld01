package com.zmsoft.ccd.module.takeout.delivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.ListCallbackSingleChoice;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.R2;
import com.zmsoft.ccd.module.takeout.delivery.helper.DeliveryHelper;
import com.zmsoft.ccd.module.takeout.delivery.presenter.DeliveryContract;
import com.zmsoft.ccd.module.takeout.delivery.presenter.DeliveryPresenter;
import com.zmsoft.ccd.shop.bean.IndustryType;
import com.zmsoft.ccd.takeout.bean.DeliverTakeoutResponse;
import com.zmsoft.ccd.takeout.bean.DeliveryTypeVo;
import com.zmsoft.ccd.takeout.bean.GetDeliveryTypeResponse;
import com.zmsoft.ccd.takeout.bean.Takeout;
import com.zmsoft.ccd.takeout.bean.TakeoutCourier;
import com.zmsoft.ccd.takeout.bean.TakeoutDeliveryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * 配送页
 *
 * @author DangGui
 * @create 2017/8/18.
 */
public class DeliveryFragment extends BaseFragment implements DeliveryContract.View {
    @BindView(R2.id.root)
    ScrollView mLayoutRoot;
    @BindView(R2.id.text_takeout_order_taker)
    TextView mTextTakeoutOrderTaker;
    @BindView(R2.id.text_takeout_order_phone)
    TextView mTextTakeoutOrderPhone;
    @BindView(R2.id.text_takeout_order_address)
    TextView mTextTakeoutOrderAddress;
    @BindView(R2.id.text_delivery_method)
    TextView mTextDeliveryMethod;
    @BindView(R2.id.text_delivery_company)
    TextView mTextDeliveryCompany;
    @BindView(R2.id.edit_delivery_no)
    EditText mEditTextDeliveryNo;
    @BindView(R2.id.image_scan)
    ImageView mScanImageView;
    @BindView(R2.id.layout_delivery_logistic)
    LinearLayout mLayoutDeliveryLogistic;
    @BindView(R2.id.text_delivery_platform)
    TextView mTextDeliveryPlatform;
    @BindView(R2.id.text_delivery_fee)
    TextView mTextDeliveryFee;
    @BindView(R2.id.layout_delivery_third)
    LinearLayout mLayoutDeliveryThird;
    @BindView(R2.id.text_delivery_courier)
    TextView mTextDeliveryCourier;
    @BindView(R2.id.layout_delivery_shop)
    LinearLayout mLayoutDeliveryShop;
    @BindView(R2.id.layout_delivery_third_hint)
    LinearLayout mLayoutDeliveryThirdHint;
    @BindView(R2.id.layout_single_order)
    RelativeLayout mLayoutSingleOrder;
    @BindView(R2.id.text_current_order)
    TextView mTextCurrentOrder;
    @BindView(R2.id.flexbox_layout)
    FlexboxLayout mFlexboxLayout;
    @BindView(R2.id.layout_many_order)
    LinearLayout mLayoutManyOrder;
    @BindView(R2.id.text_sure)
    TextView mTextSure;

    private static final int REQUEST_CODE_SCAN = 1;

    private DeliveryPresenter mPresenter;
    /**
     * 外卖订单信息（单独配送）
     */
    private Takeout mTakeout;
    /**
     * 待配送订单（批量配送）
     */
    private List<String> mOrderCodes;
    /**
     * 待配送订单（批量配送）
     */
    private List<String> mOrderIds;

    /**
     * 服务端返回的配送方式集合
     */
    private List<DeliveryTypeVo> mDeliveryTypeVos;
    /**
     * 配送方式列表
     */
    private List<String> mDeliveryTypeList;
    /**
     * 快递公司列表
     */
    private List<String> mExpressCompanyList;
    /**
     * 配送平台列表
     */
    private List<String> mDeliveryPlatformList;
    /**
     * 预估运费列表
     */
    private List<String> mDeliveryFeeList;
    /**
     * 配送员列表
     */
    private List<String> mDeliveryCourierList;
    /**
     * 配送方式列表默认选中index
     */
    private int mSelectedDeliveryTypeIndex = -1;
    /**
     * 快递公司列表默认选中index
     */
    private int mSelectedExpressCompanyIndex = -1;
    /**
     * 配送平台列表默认选中index
     */
    private int mSelectedDeliveryPlatformIndex = -1;
    /**
     * 配送员列表默认选中index
     */
    private int mSelectedDeliveryCourierIndex = -1;

    /**
     * 配送失败的订单ID列表
     */
    private List<String> mFailDeliveryOrderList = null;

    public static DeliveryFragment newInstance(Takeout takeout, ArrayList<String> orderCodes, ArrayList<String> orderIds) {
        Bundle args = new Bundle();
        DeliveryFragment fragment = new DeliveryFragment();
        args.putParcelable(DeliveryHelper.ExtraParams.PARAM_TAKE_OUT, takeout);
        args.putStringArrayList(DeliveryHelper.ExtraParams.PARAM_ORDER_CODES, orderCodes);
        args.putStringArrayList(DeliveryHelper.ExtraParams.PARAM_ORDER_IDS, orderIds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_takeout_delivery_fragment_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            mTakeout = bundle.getParcelable(DeliveryHelper.ExtraParams.PARAM_TAKE_OUT);
            mOrderCodes = bundle.getStringArrayList(DeliveryHelper.ExtraParams.PARAM_ORDER_CODES);
            mOrderIds = bundle.getStringArrayList(DeliveryHelper.ExtraParams.PARAM_ORDER_IDS);
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        showLoadingView();
        initInfoView();
        startLoad();
    }

    @Override
    protected void initListener() {
        RxView.clicks(mTextDeliveryMethod).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        showDeliveryTypeDialog(DeliveryHelper.DeliveryDialogType.DELIVERYTYPE);
                    }
                });
        RxView.clicks(mTextDeliveryCompany).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        showDeliveryTypeDialog(DeliveryHelper.DeliveryDialogType.DELIVERYCOMPANY);
                    }
                });
        RxView.clicks(mTextDeliveryPlatform).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        showDeliveryTypeDialog(DeliveryHelper.DeliveryDialogType.DELIVERYPLATFORM);
                    }
                });
        RxView.clicks(mTextDeliveryCourier).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        showDeliveryTypeDialog(DeliveryHelper.DeliveryDialogType.COURIER);
                    }
                });
        RxView.clicks(mScanImageView).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        ScanDeliveryNoActivity.luanchActivity(DeliveryFragment.this, REQUEST_CODE_SCAN);
                    }
                });
        RxView.clicks(mTextSure).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        deliveryTakeout(false);
                    }
                });
        //运单号输入框设置字符范围
        mEditTextDeliveryNo.setKeyListener(DigitsKeyListener.getInstance(DeliveryHelper.DELIVERY_CODE_DIGISTS));
    }

    @Override
    public void setPresenter(DeliveryContract.Presenter presenter) {
        this.mPresenter = (DeliveryPresenter) presenter;
    }

    @Override
    public void successGetDeliveryType(GetDeliveryTypeResponse response) {
        initDeliveryTypeInfo(response);
        showContentView();
        mLayoutRoot.setVisibility(View.VISIBLE);
        mTextSure.setVisibility(View.VISIBLE);
    }

    @Override
    public void faileGetDeliveryType(String errorMsg) {
        showErrorView(errorMsg);
    }

    @Override
    public void successDeliveryTakeout(DeliverTakeoutResponse response) {
        List<TakeoutDeliveryResult> takeoutDeliveryResultList = response.getTakeoutDeliveryResultList();
        if (null != mFailDeliveryOrderList) {
            mFailDeliveryOrderList.clear();
        }
        String firstErrorMsg = null;
        for (int i = 0; i < takeoutDeliveryResultList.size(); i++) {
            TakeoutDeliveryResult takeoutDeliveryResult = takeoutDeliveryResultList.get(i);
            if (null != takeoutDeliveryResult) {
                if (takeoutDeliveryResult.getResult() == DeliveryHelper.DeliveryStatus.FAIL) {
                    if (null == mFailDeliveryOrderList) {
                        mFailDeliveryOrderList = new ArrayList<>();
                    }
                    mFailDeliveryOrderList.add(takeoutDeliveryResult.getOrderId());
                    if (TextUtils.isEmpty(firstErrorMsg) && !TextUtils.isEmpty(takeoutDeliveryResult.getMsg())) {
                        firstErrorMsg = takeoutDeliveryResult.getMsg();
                    }
                }
            }
        }
        //若所有订单配送成功，则返回外卖列表页，提示：配送成功，若产生配送费，则追加：已扣减配送费：{配送费}
        StringBuilder contentSb = null;
        if (null == mFailDeliveryOrderList || mFailDeliveryOrderList.isEmpty()) {
            if (null == response.getFee()) {
                showToast(getString(R.string.module_takeout_delivery_all_success));
            } else {
                contentSb = new StringBuilder(getString(R.string.module_takeout_delivery_all_success));
                contentSb.append(getString(R.string.comma_separator));
                contentSb.append(String.format(getString(R.string.module_takeout_delivery_fee)
                        , FeeHelper.getDecimalFee(response.getFee())));
                showToast(contentSb.toString());
            }
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        } else {
            //若其中有订单配送失败，则弹窗提示
            //所有订单均配送失败：配送失败，{第一个错误Msg}
            if (mFailDeliveryOrderList.size() == takeoutDeliveryResultList.size()) {
                contentSb = new StringBuilder(getString(R.string.module_takeout_delivery_fail));
                if (!TextUtils.isEmpty(firstErrorMsg)) {
                    contentSb.append(getString(R.string.comma_separator));
                    contentSb.append(firstErrorMsg);
                }
            } else {
                contentSb = new StringBuilder(getString(R.string.module_takeout_delivery_exception));
                if (!TextUtils.isEmpty(firstErrorMsg)) {
                    contentSb.append(getString(R.string.comma_separator));
                    contentSb.append(firstErrorMsg);
                }
                contentSb.append(getString(R.string.full_stop_separator));
                contentSb.append(String.format(getString(R.string.module_takeout_delivery_some_success)
                        , takeoutDeliveryResultList.size() - mFailDeliveryOrderList.size()));
                if (null != response.getFee()) {
                    contentSb.append(String.format(getString(R.string.module_takeout_delivery_fee_some_success)
                            , FeeHelper.getDecimalFee(response.getFee())));
                }
                contentSb.append(getString(R.string.comma_separator));
                contentSb.append(String.format(getString(R.string.module_takeout_delivery_some_fail)
                        , mFailDeliveryOrderList.size()));
            }
            showDeliveryRetryDialog(contentSb.toString());
        }
    }

    @Override
    public void faileDeliveryTakeout(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        startLoad();
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_SCAN) {
            if (null != data.getStringExtra(DeliveryHelper.DELIVERY_RESULT_KEY)) {
                String result = data.getStringExtra(DeliveryHelper.DELIVERY_RESULT_KEY);
                mEditTextDeliveryNo.setText(result);
                mEditTextDeliveryNo.setSelection(result.length());
            }
        }
    }

    private void startLoad() {
        mPresenter.getCourierList(mOrderIds);
    }

    /**
     * 配送订单
     */
    private void deliveryTakeout(boolean retry) {
        if (retry && (mFailDeliveryOrderList == null || mFailDeliveryOrderList.isEmpty())) {
            return;
        }
        if (!checkInfoValid()) {
            return;
        }
        short type = mDeliveryTypeVos.get(mSelectedDeliveryTypeIndex).getType();
        int sexlectIndex = 0;
        String expressCode = null;
        switch (type) {
            case DeliveryHelper.DeliveryType.SHOP:
                sexlectIndex = mSelectedDeliveryCourierIndex;
                break;
            case DeliveryHelper.DeliveryType.LOGISTICS:
                sexlectIndex = mSelectedExpressCompanyIndex;
                expressCode = mEditTextDeliveryNo.getText().toString().trim();
                break;
            case DeliveryHelper.DeliveryType.THIRD:
                sexlectIndex = mSelectedDeliveryPlatformIndex;
                break;
        }
        String platformCode = mDeliveryTypeVos.get(mSelectedDeliveryTypeIndex)
                .getTakeoutDelivererVos().get(sexlectIndex).getPlatformCode();
        String name = mDeliveryTypeVos.get(mSelectedDeliveryTypeIndex)
                .getTakeoutDelivererVos().get(sexlectIndex).getName();
        String phone = mDeliveryTypeVos.get(mSelectedDeliveryTypeIndex)
                .getTakeoutDelivererVos().get(sexlectIndex).getPhone();
        mPresenter.deliveryTakeout(retry ? mFailDeliveryOrderList : mOrderIds, type, platformCode, name, phone, expressCode);
    }

    /**
     * 配送订单前校验填写的信息是否完整
     *
     * @return
     */
    private boolean checkInfoValid() {
        if (mSelectedDeliveryTypeIndex < 0) {
            showToast(R.string.module_takeout_delivery_type_none);
            return false;
        }
        short type = mDeliveryTypeVos.get(mSelectedDeliveryTypeIndex).getType();
        if (type == DeliveryHelper.DeliveryType.LOGISTICS) {
            if (mSelectedExpressCompanyIndex < 0) {
                showToast(R.string.module_takeout_delivery_company_none);
                return false;
            }
            if (mEditTextDeliveryNo.getText().toString().trim().isEmpty()) {
                showToast(R.string.module_takeout_delivery_order_no_none);
                return false;
            }
        }
        if (type == DeliveryHelper.DeliveryType.THIRD) {
            if (mSelectedDeliveryPlatformIndex < 0) {
                showToast(R.string.module_takeout_delivery_platform_none);
                return false;
            }
        }
        if (type == DeliveryHelper.DeliveryType.SHOP) {
            if (mSelectedDeliveryCourierIndex < 0) {
                showToast(R.string.module_takeout_delivery_courier_none);
                return false;
            }
        }
        return true;
    }

    private void initInfoView() {
        if (null != mTakeout) {
            mOrderIds = new ArrayList<>(1);
            mOrderIds.add(mTakeout.getOrderId());
            mLayoutSingleOrder.setVisibility(View.VISIBLE);
            mLayoutManyOrder.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(mTakeout.getName())) {
                mTextTakeoutOrderTaker.setText(mTakeout.getName());
            } else {
                mTextTakeoutOrderTaker.setText("-");
            }
            if (!TextUtils.isEmpty(mTakeout.getMobile())) {
                mTextTakeoutOrderPhone.setText(mTakeout.getMobile());
                mTextTakeoutOrderPhone.setVisibility(View.VISIBLE);
            } else {
                mTextTakeoutOrderPhone.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mTakeout.getAddress())) {
                mTextTakeoutOrderAddress.setText(mTakeout.getAddress());
                mTextTakeoutOrderAddress.setVisibility(View.VISIBLE);
            } else {
                mTextTakeoutOrderAddress.setVisibility(View.GONE);
            }
        } else if (null != mOrderCodes && !mOrderCodes.isEmpty()) {
            mLayoutSingleOrder.setVisibility(View.GONE);
            mLayoutManyOrder.setVisibility(View.VISIBLE);
            mTextCurrentOrder.setText(String.format(context.getResources().getString(R.string.module_takeout_delivery_order_current)
                    , mOrderIds.size()));
            for (String order : mOrderCodes) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.module_takeout_no_item_layout, null);
                TextView textView = (TextView) view.findViewById(R.id.text_item);
                textView.setText(String.format(context.getResources().getString(R.string.module_takeout_order_no)
                        , order));
                mFlexboxLayout.addView(view);
            }
        }
    }

    /**
     * 初始化配送方式信息
     *
     * @param response
     */
    private void initDeliveryTypeInfo(GetDeliveryTypeResponse response) {
        mDeliveryTypeVos = response.getDeliveryTypeVos();
        mDeliveryTypeList = new ArrayList<>(mDeliveryTypeVos.size());
        for (int i = 0; i < mDeliveryTypeVos.size(); i++) {
            DeliveryTypeVo deliveryTypeVo = mDeliveryTypeVos.get(i);
            List<TakeoutCourier> takeoutDelivererVos = deliveryTypeVo.getTakeoutDelivererVos();
            //初始化配送方式
            mDeliveryTypeList.add(deliveryTypeVo.getDesc());
            switch (deliveryTypeVo.getType()) {
                case DeliveryHelper.DeliveryType.SHOP:
                    if (UserHelper.getIndustry() == IndustryType.CATERING) {
                        mSelectedDeliveryTypeIndex = i;
                        setSelectedContent(mTextDeliveryMethod, deliveryTypeVo.getDesc());
                    }
                    //初始化配送员
                    if (null != takeoutDelivererVos && !takeoutDelivererVos.isEmpty()) {
                        mDeliveryCourierList = new ArrayList<>(takeoutDelivererVos.size());
                        for (int j = 0; j < takeoutDelivererVos.size(); j++) {
                            TakeoutCourier takeoutCourier = takeoutDelivererVos.get(j);
                            if (!TextUtils.isEmpty(takeoutCourier.getName())) {
                                mDeliveryCourierList.add(takeoutCourier.getName() + " " + takeoutCourier.getPhone());
                            }
                        }
                    }
                    break;
                case DeliveryHelper.DeliveryType.LOGISTICS:
                    if (UserHelper.getIndustry() == IndustryType.RETAIL) {
                        mSelectedDeliveryTypeIndex = i;
                        setSelectedContent(mTextDeliveryMethod, deliveryTypeVo.getDesc());
                    }
                    //初始化快递公司
                    if (null != takeoutDelivererVos && !takeoutDelivererVos.isEmpty()) {
                        mExpressCompanyList = new ArrayList<>(takeoutDelivererVos.size());
                        for (int j = 0; j < takeoutDelivererVos.size(); j++) {
                            TakeoutCourier takeoutCourier = takeoutDelivererVos.get(j);
                            if (!TextUtils.isEmpty(takeoutCourier.getName())) {
                                mExpressCompanyList.add(takeoutCourier.getName());
                            }
                        }
                    }
                    break;
                case DeliveryHelper.DeliveryType.THIRD:
                    //初始化配送平台
                    if (null != takeoutDelivererVos && !takeoutDelivererVos.isEmpty()) {
                        mDeliveryPlatformList = new ArrayList<>(takeoutDelivererVos.size());
                        mDeliveryFeeList = new ArrayList<>(takeoutDelivererVos.size());
                        for (int j = 0; j < takeoutDelivererVos.size(); j++) {
                            TakeoutCourier takeoutCourier = takeoutDelivererVos.get(j);
                            if (!TextUtils.isEmpty(takeoutCourier.getName())) {
                                mDeliveryPlatformList.add(takeoutCourier.getName());
                            }
                            if (null == takeoutCourier.getFee()) {
                                mDeliveryFeeList.add(getString(R.string.module_takeout_delivery_fee_none));
                            } else {
                                mDeliveryFeeList.add(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                        , String.valueOf(takeoutCourier.getFee())));
                            }
                        }
                    }
                    break;
            }
        }
        //如果只有一种配送方式，则直接默认展示这种方式
        if (mDeliveryTypeList.size() == 1) {
            mSelectedDeliveryTypeIndex = 0;
            setSelectedContent(mTextDeliveryMethod, mDeliveryTypeList.get(0));
        }
        //如果只有一种快递公司，则直接默认展示这种方式
        if (null != mExpressCompanyList && mExpressCompanyList.size() == 1) {
            mSelectedExpressCompanyIndex = 0;
            setSelectedContent(mTextDeliveryCompany, mExpressCompanyList.get(0));
        }
        //如果只有一种配送平台，则直接默认展示这种方式
        if (null != mDeliveryPlatformList && mDeliveryPlatformList.size() == 1) {
            mSelectedDeliveryPlatformIndex = 0;
            setSelectedContent(mTextDeliveryPlatform, mDeliveryPlatformList.get(0));
        }
        //如果只有一个配送员，则直接默认展示这种方式
        if (null != mDeliveryCourierList && mDeliveryCourierList.size() == 1) {
            mSelectedDeliveryCourierIndex = 0;
            setSelectedContent(mTextDeliveryCourier, mDeliveryCourierList.get(0));
        }
        //根据已选择的配送平台显示预估运费
        if (mSelectedDeliveryPlatformIndex >= 0) {
            setSelectedContent(mTextDeliveryFee, mDeliveryFeeList.get(mSelectedDeliveryPlatformIndex));
            if (mDeliveryFeeList.get(mSelectedDeliveryPlatformIndex).equals(getString(R.string.module_takeout_delivery_fee_none))) {
                mTextDeliveryFee.setTextColor(ContextCompat.getColor(getActivity(), R.color.accentColor));
            }
        }
        initDeliveryInfoByType();
    }

    /**
     * 根据配送方式显示界面
     */
    private void initDeliveryInfoByType() {
        if (null == mDeliveryTypeVos || mDeliveryTypeVos.isEmpty()) {
            return;
        }
        if (mSelectedDeliveryTypeIndex >= 0) {
            int type = mDeliveryTypeVos.get(mSelectedDeliveryTypeIndex).getType();
            mLayoutDeliveryShop.setVisibility(type == DeliveryHelper.DeliveryType.SHOP ? View.VISIBLE : View.GONE);
            mLayoutDeliveryLogistic.setVisibility(type == DeliveryHelper.DeliveryType.LOGISTICS ? View.VISIBLE : View.GONE);
            mLayoutDeliveryThird.setVisibility(type == DeliveryHelper.DeliveryType.THIRD ? View.VISIBLE : View.GONE);
            mLayoutDeliveryThirdHint.setVisibility(type == DeliveryHelper.DeliveryType.THIRD ? View.VISIBLE : View.GONE);
            if (type == DeliveryHelper.DeliveryType.THIRD) {
                showDeliveryFeeErrorMsg();
            }
        }
    }

    /**
     * 选择配送方式弹框
     */
    private void showDeliveryTypeDialog(final int dialogType) {
        if (null == mDeliveryTypeList || mDeliveryTypeList.isEmpty()) {
            return;
        }
        int titleResId;
        List<String> list;
        int selectedIndex;
        switch (dialogType) {
            case DeliveryHelper.DeliveryDialogType.DELIVERYTYPE:
                titleResId = R.string.module_takeout_delivery_courier;
                list = mDeliveryTypeList;
                selectedIndex = mSelectedDeliveryTypeIndex;
                break;
            case DeliveryHelper.DeliveryDialogType.DELIVERYCOMPANY:
                titleResId = R.string.module_takeout_delivery_company;
                list = mExpressCompanyList;
                selectedIndex = mSelectedExpressCompanyIndex;
                break;
            case DeliveryHelper.DeliveryDialogType.DELIVERYPLATFORM:
                titleResId = R.string.module_takeout_delivery_platform;
                list = mDeliveryPlatformList;
                selectedIndex = mSelectedDeliveryPlatformIndex;
                break;
            case DeliveryHelper.DeliveryDialogType.COURIER:
                titleResId = R.string.module_takeout_delivery_shop_courier;
                list = mDeliveryCourierList;
                selectedIndex = mSelectedDeliveryCourierIndex;
                break;
            default:
                titleResId = R.string.module_takeout_delivery_courier;
                list = mDeliveryTypeList;
                selectedIndex = mSelectedDeliveryTypeIndex;
                break;
        }
        getDialogUtil().showSingleChoiceDialog(titleResId, list, selectedIndex
                , new ListCallbackSingleChoice() {
                    @Override
                    public void onSelection(int which, CharSequence text) {
                        switch (dialogType) {
                            case DeliveryHelper.DeliveryDialogType.DELIVERYTYPE:
                                mSelectedDeliveryTypeIndex = which;
                                setSelectedContent(mTextDeliveryMethod, mDeliveryTypeList.get(mSelectedDeliveryTypeIndex));
                                initDeliveryInfoByType();
                                break;
                            case DeliveryHelper.DeliveryDialogType.DELIVERYCOMPANY:
                                mSelectedExpressCompanyIndex = which;
                                setSelectedContent(mTextDeliveryCompany, mExpressCompanyList.get(mSelectedExpressCompanyIndex));
                                break;
                            case DeliveryHelper.DeliveryDialogType.DELIVERYPLATFORM:
                                mSelectedDeliveryPlatformIndex = which;
                                setSelectedContent(mTextDeliveryPlatform, mDeliveryPlatformList.get(mSelectedDeliveryPlatformIndex));
                                setSelectedContent(mTextDeliveryFee, mDeliveryFeeList.get(mSelectedDeliveryPlatformIndex));
                                showDeliveryFeeErrorMsg();
                                break;
                            case DeliveryHelper.DeliveryDialogType.COURIER:
                                mSelectedDeliveryCourierIndex = which;
                                setSelectedContent(mTextDeliveryCourier, mDeliveryCourierList.get(mSelectedDeliveryCourierIndex));
                                break;
                            default:
                                break;
                        }
                    }
                }, true, null);
    }

    /**
     * 配送失败重试弹框
     *
     * @param content
     */
    private void showDeliveryRetryDialog(String content) {
        getDialogUtil().showDialog(R.string.material_dialog_title,
                content, R.string.module_takeout_delivery_fail_dialog_sure, R.string.module_takeout_delivery_fail_dialog_close,
                false, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            deliveryTakeout(true);
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        }
                    }
                });
    }

    private void setSelectedContent(TextView textView, String text) {
        textView.setText(text);
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.normal_bule));
    }

    /**
     * 如果无法获取预估运费，提示错误信息
     */
    private void showDeliveryFeeErrorMsg() {
        if (mSelectedDeliveryTypeIndex < 0 || mSelectedDeliveryPlatformIndex < 0) {
            return;
        }
        short type = mDeliveryTypeVos.get(mSelectedDeliveryTypeIndex).getType();
        if (type == DeliveryHelper.DeliveryType.THIRD) {
            if (mDeliveryFeeList.get(mSelectedDeliveryPlatformIndex).equals(getString(R.string.module_takeout_delivery_fee_none))) {
                String errorMsg = mDeliveryTypeVos.get(mSelectedDeliveryTypeIndex)
                        .getTakeoutDelivererVos().get(mSelectedDeliveryPlatformIndex).getErrorMsg();
                if (!TextUtils.isEmpty(errorMsg)) {
                    showToast(errorMsg);
                }
            }
        }
    }
}
