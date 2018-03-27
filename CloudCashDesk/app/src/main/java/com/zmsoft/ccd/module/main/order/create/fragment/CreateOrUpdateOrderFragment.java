package com.zmsoft.ccd.module.main.order.create.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccd.lib.print.helper.CcdPrintHelper;
import com.chiclaim.modularization.router.MRouter;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.helper.CommonHelper;
import com.zmsoft.ccd.helper.OpOrderTypeHelper;
import com.zmsoft.ccd.lib.base.constant.AnswerEventConstant;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.AnswerEventLogger;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.ConfigHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.OpenOrderVo;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.bean.order.op.ChangeOrderByTrade;
import com.zmsoft.ccd.lib.bean.pay.CashLimit;
import com.zmsoft.ccd.lib.bean.table.Seat;
import com.zmsoft.ccd.lib.bean.table.SeatStatus;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.lib.widget.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.main.order.create.CreateOrUpdateOrderActivity;
import com.zmsoft.ccd.module.main.order.memo.MemoListActivity;
import com.zmsoft.ccd.module.main.seat.selectseat.SelectSeatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/1 14:10
 */
public class CreateOrUpdateOrderFragment extends BaseFragment implements CreateOrUpdateOrderContract.View {

    public static final int MAX_NUMBER = 999;

    @BindView(R.id.text_seat_name)
    TextView mTextSeatName;
    @BindView(R.id.edit_order_number)
    EditText mEditOrderNumber;
    @BindView(R.id.text_remark)
    TextView mTextRemark;
    @BindView(R.id.button_ok)
    Button mButtonOk;
    @BindView(R.id.relative_select_seat_name)
    RelativeLayout mRelativeSelectSeatName;
    @BindView(R.id.relative_select_remark)
    RelativeLayout mRelativeSelectRemark;
    @BindView(R.id.text_remark_display)
    TextView mTextRemarkDisplay;
    @BindView(R.id.switch_temp_served)
    SwitchCompat mSwitchTempServed;

    /**
     * 数据检测
     */
    private boolean mIsWait; // 是否待菜
    private String mMemo;    // 备注
    private int mPeopleCount; // 就餐人数
    /**
     * 数据源
     */
    private String mFrom;    // 来源
    private OrderParam mOrderParam;
    private DialogUtil dialogUtil;
    private CreateOrUpdateOrderPresenter mPresenter;

    public static CreateOrUpdateOrderFragment newInstance(String from, OrderParam createOrderParam) {
        CreateOrUpdateOrderFragment fragment = new CreateOrUpdateOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM, from);
        bundle.putSerializable(RouterPathConstant.CreateOrUpdateOrder.EXTRA_ORDER_PARAM, createOrderParam);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_or_update_oreder;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initBundle();
        updateView();
        CustomViewUtil.initEditViewFocousAll(mEditOrderNumber);
    }

    /**
     * 初始化传参
     */
    private void initBundle() {
        Bundle bundle = getArguments();
        mFrom = bundle.getString(RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM);
        mOrderParam = (OrderParam) bundle.getSerializable(RouterPathConstant.CreateOrUpdateOrder.EXTRA_ORDER_PARAM);
    }

    /**
     * 更新view
     */
    private void updateView() {
        if (RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_CREATE.equals(mFrom)) { // 开单
            getActivity().setTitle(getString(R.string.create_order));
            mButtonOk.setText(getString(R.string.start_dishes));
            if (mOrderParam != null) {
                mTextSeatName.setText(StringUtils.notNull(mOrderParam.getSeatName()));
            }
        } else if (RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_UPDATE_BY_ORDER_DETAIL.equals(mFrom)
                || RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_UPDATE_BY_SHOP_CAR.equals(mFrom)) { // 改单
            getActivity().setTitle(getString(R.string.update_order));
            mButtonOk.setText(getString(R.string.ok));
            if (mOrderParam != null) {
                mTextSeatName.setText(mOrderParam.getSeatName());
                mTextSeatName.setTextColor(getResources().getColor(R.color.common_blue));

                mEditOrderNumber.setText(String.valueOf(mOrderParam.getNumber()));
                mEditOrderNumber.setSelection(String.valueOf(mOrderParam.getNumber()).length());
                mTextRemark.setText(StringUtils.notNull(mOrderParam.getMemo()));
                mMemo = mOrderParam.getMemo();
                mPeopleCount = mOrderParam.getNumber();
                mIsWait = mOrderParam.isWait();
                mSwitchTempServed.setChecked(mIsWait);
            }
        }
    }

    @Override
    protected void initListener() {
        mEditOrderNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String key = editable.toString().trim();
                try {
                    if (key.length() > 0) {
                        int number = Integer.parseInt(key);
                        if (number > MAX_NUMBER) {
                            mEditOrderNumber.setText(String.valueOf(MAX_NUMBER));
                            mEditOrderNumber.setSelection(String.valueOf(MAX_NUMBER).length());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mSwitchTempServed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                mIsWait = check;
            }
        });
    }

    @Override
    public void unBindPresenterFromView() {
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @OnClick({R.id.relative_select_seat_name, R.id.relative_select_remark, R.id.button_ok,})
    void processClick(View view) {
        if (!UserHelper.getWorkStatus()) {
            CommonHelper.showOffWorkDialog(getActivity(), getDialogUtil());
            return;
        }
        switch (view.getId()) {
            case R.id.relative_select_seat_name:
                gotoSelectSeatActivity();
                break;
            case R.id.relative_select_remark:
                selectRemark();
                break;
            case R.id.button_ok:
                createOrUpdateOrder();
                break;
        }
    }

    /**
     * 选择备注
     */
    private void selectRemark() {
        MRouter.getInstance().build(MemoListActivity.PATH_MEMO)
                .putString(MemoListActivity.EXTRA_MEMO, mMemo)
                .navigation(getActivity(), CreateOrUpdateOrderActivity.RESULT_REMARK_CODE);
    }

    /**
     * 开单、改单
     */
    private void createOrUpdateOrder() {
        if (check()) {
            if (RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_UPDATE_BY_ORDER_DETAIL.equals(mFrom)) {
                // 订单详情 - 改单
                if (mOrderParam != null) {
                    changeOrderByOrderId(mIsWait ? 1 : -1);
                }
            } else if (RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_UPDATE_BY_SHOP_CAR.equals(mFrom)) {
                // 购物车 - 改单
                if (mOrderParam != null) {
                    // 没有开过单
                    if (!StringUtils.isEmpty(mOrderParam.getSeatCode()) && !mOrderParam.getSeatCode().equals(mOrderParam.getOriginSeatCode())) {
                        mPresenter.getSeatStatusBySeatCode(UserHelper.getEntityId(), mOrderParam.getSeatCode()); // 先判断座位状态
                    } else {
                        changeOrderByShopCar();
                    }
                }
            } else if (RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_CREATE.equals(mFrom)) {
                // 开单必须选择桌位
                if (BaseSpHelper.isOpenOrderMustSeat(getActivity())) {
                    if (StringUtils.isEmpty(getSeatName())) {
                        showToast(getString(R.string.seat_must_be_not_null));
                        return;
                    }
                }
                // 开单
                if (ConfigHelper.hasOpenCashClean(getActivity())) { // 现金收款
                    mPresenter.getCashLimit(UserHelper.getEntityId(), UserHelper.getUserId());
                } else {
                    if (!StringUtils.isEmpty(getSeatName())) {
                        mPresenter.getSeatStatusBySeatCode(UserHelper.getEntityId(), mOrderParam.getSeatCode()); // 先判断座位状态
                    } else {
                        openShopCar();
                    }
                }
            }
        }
    }

    /**
     * check param
     */
    private boolean check() {
        String inputNumber = getPeople();
        if (StringUtils.isEmpty(inputNumber)) {
            showToast(getString(R.string.number_not_null));
            return false;
        }
        try {
            mPeopleCount = Integer.parseInt(inputNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mPeopleCount <= 0) {
            showToast(getString(R.string.input_number_than_zero));
            return false;
        }
        return true;
    }


    private void gotoSelectSeatActivity() {
        AnswerEventLogger.log(
                mFrom.equalsIgnoreCase(RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_CREATE)
                        ? AnswerEventConstant.Order.ANSWER_EVENT_FROM_CREATE_ORDER : AnswerEventConstant.Order.ANSWER_EVENT_FROM_CHANGE_ORDER
                , AnswerEventConstant.Order.ANSWER_EVENT_SELECT_DESK_BY_LIST);
        MRouter.getInstance().build(SelectSeatActivity.PATH_SELECT_SEAT)
                .putString(SelectSeatActivity.EXTRA_SEAT_CODE, mOrderParam.getSeatCode())
                .putString(SelectSeatActivity.EXTRA_FROM, mFrom)
                .navigation(getActivity(), CreateOrUpdateOrderActivity.RESULT_SELECT_SEAT_CODE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 更新备注
     */
    public void updateRemark(String memo) {
        mMemo = StringUtils.notNull(memo);
        mTextRemark.setText(mMemo);
        mOrderParam.setMemo(mMemo);
    }

    /**
     * 选座换桌
     */
    public void updateSeatName(Seat seat) {
        mTextSeatName.setText(seat.getSeatName());
        if (RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_UPDATE_BY_ORDER_DETAIL.equals(mFrom)
                || RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_UPDATE_BY_SHOP_CAR.equals(mFrom)) {  // 改单
            mOrderParam.setSeatCode(seat.getSeatCode());
            mOrderParam.setSeatName(seat.getSeatName());
        } else { // 开单
            mOrderParam.setNumber(mPeopleCount);
            mOrderParam.setMemo(mMemo);
            mOrderParam.setWait(mIsWait);
            mOrderParam.setSeatCode(seat.getSeatCode());
            mOrderParam.setSeatName(seat.getSeatName());
        }
    }

    @Override
    public void setPresenter(CreateOrUpdateOrderContract.Presenter presenter) {
        mPresenter = (CreateOrUpdateOrderPresenter) presenter;
    }

    @Override
    public String getSeatName() {
        return mTextSeatName.getText().toString().trim();
    }

    @Override
    public void getCashLimitFailed(String errorCode, String errorMsg) {
        Logger.d(errorCode + ',' + errorMsg);
        showToast(errorMsg);
    }

    @Override
    public void getCashLimitSuccess(CashLimit cashLimit) {
        if (cashLimit == null) {
            return;
        }
        Logger.d(cashLimit.getCurrAmount() + ":" + cashLimit.getCollectLimit());

        if (cashLimit.hasExceedCashLimit()) {
            String content = getString(R.string.dialog_content_reach_cash_limit,
                    NumberUtils.trimPointIfZero(cashLimit.getCollectLimit()),
                    NumberUtils.trimPointIfZero(cashLimit.getCurrAmount()));
            getDialogUtil().showNoticeDialog(R.string.dialog_title, content, R.string.dialog_positive_reach_cash_limit, true, null);
        } else {
            if (!StringUtils.isEmpty(getSeatName())) {
                mPresenter.getSeatStatusBySeatCode(UserHelper.getEntityId(), mOrderParam.getSeatCode()); // 先判断座位状态
            } else {
                openShopCar();
            }
        }
    }

    @Override
    public String getPeople() {
        return mEditOrderNumber.getText().toString().trim();
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void changeOrderSuccess(ChangeOrderByTrade changeOrder) {
        showToast(getString(R.string.change_order_success));
        if (RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_UPDATE_BY_SHOP_CAR.equals(mFrom)) {
            mOrderParam.setOrderId(changeOrder.getOrderId());
            mOrderParam.setModifyTime(changeOrder.getModifyTime());
            gotoShopCarActivity();
        } else {
            // 如果有必选菜 && 修改用餐人数
            if (mOrderParam.isMustMenu() && mOrderParam.getNumber() != mPeopleCount) {
                showMustInstanceDialog(changeOrder);
            } else {
                gotoOrderDetailActivity(changeOrder);
            }
        }
    }

    @Override
    public void changeOrderSuccessByShopCar() {
        showToast(getString(R.string.change_order_success));
        gotoShopCarActivity();
    }


    @Override
    public void getSeatStatusSuccess(SeatStatus seatStatus) {
        // 没有开桌
        if (seatStatus == null || seatStatus.getStatus() == SeatStatus.NO_OPEN_ORDER) {
            if (RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_CREATE.equals(mFrom)) {
                openShopCar();
            } else if (RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_UPDATE_BY_SHOP_CAR.equals(mFrom)) {
                changeOrderByShopCar();
            }
        } else { // 已开桌
            if (RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_CREATE.equals(mFrom)) {
                EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
                showDialogByCreate();
            } else if (RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_UPDATE_BY_SHOP_CAR.equals(mFrom)) {
                showChangeOrderDialog();
            }
        }
    }

    @Override
    public void createOrderSuccess(OpenOrderVo data) {
        if (data != null) {
            if (data.getStatus() == OpenOrderVo.STATUS_SUCCESS) {
                gotoMenuListActivity();
            } else if (data.getStatus() == OpenOrderVo.STATUS_MORE_THAN_50) {
                showToast(data.getMessage());
                gotoMenuListActivity();
            } else if (data.getStatus() == OpenOrderVo.STATUS_MORE_THAN_100) {
                // 是否有关注的零售单
                if (BaseSpHelper.isWatchedRetail(getActivity())) {
                    showRetailOrderDialog(data.getMessage());
                } else {
                    showRetailOrderDialogByNoWatched();
                }
            }
        }
    }

    //=========================================================================================
    //  show other dialog
    //=========================================================================================

    /**
     * 零售单弹窗提示：没有关注
     */
    private void showRetailOrderDialogByNoWatched() {
        if (dialogUtil == null) {
            dialogUtil = new DialogUtil(getActivity());
        }
        dialogUtil.showNoticeDialog(R.string.material_dialog_title
                , getString(R.string.prompt_no_watched_retail)
                , R.string.dialog_hint_know
                , false
                , new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            dialogUtil.dismissDialog();
                        }
                    }
                });
    }

    /**
     * 零售单弹窗提示
     */
    private void showRetailOrderDialog(String message) {
        final DialogUtil dialogUtil = new DialogUtil(getActivity());
        dialogUtil.showDialog(R.string.prompt
                , message
                , R.string.go_deal_with
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            EventBusHelper.post(BaseEvents.CommonEvent.EVENT_SWITCH_WATCHED_RETAIL);
                            getActivity().finish();
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            dialogUtil.dismissDialog();
                        }
                    }
                });
    }

    /**
     * 1.has must be instance
     * 2.update people number
     * show must instance dialog
     */
    private void showMustInstanceDialog(final ChangeOrderByTrade changeOrder) {
        final DialogUtil dialogUtil = new DialogUtil(getActivity());
        String contentId;
        if (mOrderParam.getNumber() < mPeopleCount) {
            contentId = getString(R.string.must_people_add_prompt);
        } else {
            contentId = getString(R.string.must_people_sub_prompt);
        }
        dialogUtil.showNoticeDialog(R.string.material_dialog_title
                , contentId, R.string.dialog_hint_know, false, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            gotoOrderDetailActivity(changeOrder);
                        }
                    }
                });
    }

    /**
     * 开单，已开桌，弹窗提示
     */
    private void showDialogByCreate() {
        if (dialogUtil == null) {
            dialogUtil = new DialogUtil(getActivity());
        }
        dialogUtil.showDialog(R.string.prompt
                , R.string.open_car_prompt
                , R.string.my_add_instance
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            gotoMenuListActivity();
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            dialogUtil.dismissDialog();
                        }
                    }
                });
    }

    /**
     * 修改购物车
     */
    private void showChangeOrderDialog() {
        if (dialogUtil == null) {
            dialogUtil = new DialogUtil(getActivity());
        }
        dialogUtil.showDialog(R.string.prompt
                , R.string.open_seat_prompt
                , R.string.my_add_instance
                , R.string.change_seat_number
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            changeOrderByShopCar();
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            dialogUtil.dismissDialog();
                        }
                    }
                });
    }

    //=========================================================================================
    //  go to other activity
    //=========================================================================================

    /**
     * 跳转订单详情
     */
    private void gotoOrderDetailActivity(ChangeOrderByTrade changeOrder) {
        // 转桌打印转桌单
        if (!StringUtils.notNull(mOrderParam.getOriginSeatCode()).equals(StringUtils.notNull(mOrderParam.getSeatCode()))) {
            CcdPrintHelper.printChangeSeat(getActivity(), mOrderParam.getOrderId(), mOrderParam.getOriginSeatCode());
        }

        // 回传数据
        Intent intent = new Intent();
        if (changeOrder != null) {
            intent.putExtra(RouterPathConstant.OrderDetail.EXTRA_ORDER_ID, changeOrder.getOrderId());
        }
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    /**
     * 跳转到购物车界面
     */
    private void gotoShopCarActivity() {
        RouterBaseEvent.CommonEvent event = RouterBaseEvent.CommonEvent.EVENT_REFRESH_SHOP_CAR;
        event.setObject(mOrderParam);
        EventBusHelper.post(event);
        getActivity().finish();
    }

    /**
     * 跳转菜肴列表
     */
    private void gotoMenuListActivity() {
        if (mOrderParam != null) {
            mOrderParam.setNumber(mPeopleCount);
            mOrderParam.setMemo(mMemo);
            mOrderParam.setWait(mIsWait);
            MRouter.getInstance().build(RouterPathConstant.MenuList.PATH)
                    .putSerializable(RouterPathConstant.MenuList.EXTRA_CREATE_ORDER_PARAM, mOrderParam)
                    .navigation(getActivity());
        }
    }


    //=========================================================================================
    //  do some request
    //=========================================================================================

    /**
     * 修改订单：购物车
     */
    private void changeOrderByShopCar() {
        mOrderParam.setNumber(mPeopleCount);
        mOrderParam.setMemo(mMemo);
        mOrderParam.setWait(mIsWait);

        mPresenter.changeOrderByShopCar(UserHelper.getEntityId()
                , UserHelper.getUserId()
                , mOrderParam.getOriginSeatCode()
                , mOrderParam.getSeatCode()
                , mPeopleCount
                , mMemo
                , mIsWait);
    }

    /**
     * 修改订单[交易中心]
     *
     * @param wait
     */
    private void changeOrderByOrderId(int wait) {
        mPresenter.changeOrderByTrade(UserHelper.getEntityId()
                , mMemo
                , mPeopleCount
                , mOrderParam.getSeatCode()
                , UserHelper.getUserId()
                , mOrderParam.getOrderId()
                , wait
                , OpOrderTypeHelper.OP_ORDER_CHANGE_SEAT + OpOrderTypeHelper.OP_ORDER_CHANGE_MEMO_PEOPLE + OpOrderTypeHelper.OP_ORDER_CHANGE_IS_WAIT
                , mOrderParam.getModifyTime());
    }

    /**
     * 开购物车
     */
    private void openShopCar() {
        AnswerEventLogger.log(AnswerEventConstant.Order.ANSWER_EVENT_OPEN_ORDER);
        mPresenter.createOrder(UserHelper.getEntityId()
                , UserHelper.getMemberId()
                , mOrderParam.getSeatCode()
                , mPeopleCount
                , mMemo
                , mIsWait);
    }
}
