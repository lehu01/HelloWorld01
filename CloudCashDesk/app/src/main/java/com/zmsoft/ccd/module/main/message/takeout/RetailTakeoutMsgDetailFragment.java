package com.zmsoft.ccd.module.main.message.takeout;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.model.PrintData;
import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.RetailDataMapLayer;
import com.zmsoft.ccd.helper.CommonHelper;
import com.zmsoft.ccd.helper.MessageDetailHelper;
import com.zmsoft.ccd.helper.MessageHelper;
import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.message.AuditOrderResponse;
import com.zmsoft.ccd.lib.bean.message.TakeoutMsgDetailResponse;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.main.message.takeout.adapter.RetailTakeoutMsgDetailAdapter;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailRecyclerItem;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * @author DangGui
 * @create 2017/3/7.
 */

public class RetailTakeoutMsgDetailFragment extends BaseFragment implements RetailTakeoutMsgDetailContract.View {
    @BindView(R.id.recyclerview_msg_detail)
    RecyclerView mRecyclerviewMsgDetail;
    @BindView(R.id.linear_bottom)
    LinearLayout mLinearBottom;
    @BindView(R.id.image_reject)
    TextView mImageReject;
    @BindView(R.id.image_agree)
    TextView mImageAgree;
    @BindView(R.id.layout_reject)
    FrameLayout mFrameLayoutReject;
    @BindView(R.id.layout_agree)
    FrameLayout mFrameLayoutAgree;

    private RetailTakeoutMsgDetailPresenter mPresenter;
    private RetailTakeoutMsgDetailAdapter mRecyclerAdapter;
    private ArrayList<TakeoutDetailRecyclerItem> mDatas;
    private TakeoutMsgDetailResponse mTakeoutMsgDetailResponse;
    /**
     * 在消息中心列表中的position
     */
    private int mPosition; //在消息列表中的position
    /**
     * 消息ID
     */
    private String mMsgId;
    /**
     * 订单ID
     */
    private String mOrderId;
    /**
     * 消息类型
     */
    private int mMsgType;

    public static RetailTakeoutMsgDetailFragment newInstance(int position, String msgId, String orderId, int msgType) {
        RetailTakeoutMsgDetailFragment fragment = new RetailTakeoutMsgDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RouterPathConstant.TakeOutMsgDetail.EXTRA_MSG_ID, msgId);
        bundle.putString(RouterPathConstant.TakeOutMsgDetail.EXTRA_ORDER_ID, orderId);
        bundle.putInt(RouterPathConstant.TakeOutMsgDetail.EXTRA_POSITION, position);
        bundle.putInt(RouterPathConstant.TakeOutMsgDetail.EXTRA_MSG_TYPE, msgType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_retail_message_detail;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        mRecyclerAdapter = new RetailTakeoutMsgDetailAdapter(getActivity(), mRecyclerviewMsgDetail, mDatas);
        mRecyclerviewMsgDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerviewMsgDetail.setAdapter(mRecyclerAdapter);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        Bundle bundle = getArguments();
        mPosition = bundle.getInt(RouterPathConstant.TakeOutMsgDetail.EXTRA_POSITION, 0);
        mMsgId = bundle.getString(RouterPathConstant.TakeOutMsgDetail.EXTRA_MSG_ID);
        mOrderId = bundle.getString(RouterPathConstant.TakeOutMsgDetail.EXTRA_ORDER_ID);
        mMsgType = bundle.getInt(RouterPathConstant.TakeOutMsgDetail.EXTRA_MSG_TYPE, 0);
        startLoad();
    }

    @Override
    protected void initListener() {
        //防止按钮重复点击
        RxView.clicks(mImageAgree).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (!hasTakeoutPermission()) {
                            return;
                        }
                        if (UserHelper.getWorkStatus()) {
                            mPresenter.handleMessage(mMsgId, mTakeoutMsgDetailResponse, mPosition, true);
                        } else {
                            //如果是已下班状态，弹框提示
                            CommonHelper.showOffWorkDialog(getActivity(), getDialogUtil());
                        }
                    }
                });
        RxView.clicks(mImageReject).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (!hasTakeoutPermission()) {
                            return;
                        }
                        if (UserHelper.getWorkStatus()) {
                            getDialogUtil().showDialog(R.string.material_dialog_title, R.string.takeout_msg_pay_detail_reject_alert, true, new SingleButtonCallback() {
                                @Override
                                public void onClick(DialogUtilAction which) {
                                    if (which == DialogUtilAction.POSITIVE) {
                                        mPresenter.handleMessage(mMsgId, mTakeoutMsgDetailResponse, mPosition, false);
                                    }
                                }
                            });
                        } else {
                            //如果是已下班状态，弹框提示
                            CommonHelper.showOffWorkDialog(getActivity(), getDialogUtil());
                        }
                    }
                });
    }


    @Override
    public void setPresenter(RetailTakeoutMsgDetailContract.Presenter presenter) {
        this.mPresenter = (RetailTakeoutMsgDetailPresenter) presenter;
    }

    @Override
    public void successGetMsgDetail(TakeoutMsgDetailResponse takeoutMsgDetailResponse) {
        mTakeoutMsgDetailResponse = takeoutMsgDetailResponse;
        //如果是自动审核、预付款支付消息 的消息类型，默认状态设置为已审核，因为这个类型的消息只是展示用，不能做操作
        if (mMsgType == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_INDEPENDENT) {
            if (null != takeoutMsgDetailResponse.getTakeoutOrderVo()) {
                takeoutMsgDetailResponse.setMsgState(MessageDetailHelper.OrderState.STATE_AGREED);
            }
        }
        mDatas.addAll(RetailDataMapLayer.getTakeoutDetailRecyclerItemList(takeoutMsgDetailResponse));
        if (takeoutMsgDetailResponse.getMsgState() == MessageDetailHelper.OrderState.STATE_CHECK_PENDING) {
            mFrameLayoutAgree.setVisibility(View.VISIBLE);
            mFrameLayoutReject.setVisibility(View.VISIBLE);
        }
        mRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void failGetMsgDetail(String errorMsg) {
        showErrorView(errorMsg);
    }

    @Override
    public void showBottomView() {
        mLinearBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeView() {
        if (isHostActive()) {
            getActivity().finish();
        }
    }

    @Override
    public void loadDataError(String errorMessage) {
        toastMsg(errorMessage);
    }

    @Override
    public void printInstance(AuditOrderResponse auditOrderResponse, TakeoutMsgDetailResponse takeoutMsgDetailResponse) {
        if (null != auditOrderResponse) {
            //零售不关心预约单,都打印线上订单运送单
            //if (takeoutMsgDetailResponse.getTakeoutOrderVo().getReserveStatus() != TakeoutConstants.ReserveStatus.APPOINTMENT) {
                CcdPrintHelper.manualPrintOrder(getActivity()
                        , PrintData.BIZ_TYPE_PRINT_RETAIL_ORDER
                        , auditOrderResponse.getOrderId());
            //}
        }
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        startLoad();
    }

    private void startLoad() {
        mPresenter.loadMsgDetail(mMsgId);
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    /**
     * 是否有外卖单权限
     *
     * @return
     */
    private boolean hasTakeoutPermission() {
        if (!BatchPermissionHelper.getPermission(Permission.TakeOut.ACTION_CODE)) {
            showToast(String.format(getResources().getString(R.string.alert_permission_deny)
                    , getString(R.string.home_takeout)));
            return false;
        }
        return true;
    }
}
