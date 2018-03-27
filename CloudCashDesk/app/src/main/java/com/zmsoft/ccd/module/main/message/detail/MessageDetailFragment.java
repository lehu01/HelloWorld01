package com.zmsoft.ccd.module.main.message.detail;

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
import com.zmsoft.ccd.data.DataMapLayer;
import com.zmsoft.ccd.helper.CommonHelper;
import com.zmsoft.ccd.helper.MessageDetailHelper;
import com.zmsoft.ccd.helper.MessageHelper;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.message.AuditOrderResponse;
import com.zmsoft.ccd.lib.bean.message.DeskMsgDetail;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.main.message.detail.adapter.MessageDetailAdapter;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailRecyclerItem;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * @author DangGui
 * @create 2017/3/7.
 */

public class MessageDetailFragment extends BaseFragment implements MessageDetailContract.View {
    public static final String EXTRA_MSG_POSITION = "extra_msg_position"; //在消息列表中的position
    public static final String EXTRA_MSG_ID = "extra_msg_id"; //消息id
    public static final String EXTRA_MSG_TYPE = "extra_msg_type"; //消息类型
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
    private MessageDetailAdapter mRecyclerAdapter;
    private ArrayList<DeskMsgDetailRecyclerItem> mDatas;
    private DeskMsgDetail mDeskMsgDetail;
    private int mPosition; //在消息列表中的position
    private String mMessageId; //消息id
    private int mMsgType; //消息类型
    private MessageDetailPresenter mPresenter;

    public static MessageDetailFragment newInstance(int position, String messageId, int msgType) {
        MessageDetailFragment fragment = new MessageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_MSG_POSITION, position);
        bundle.putString(EXTRA_MSG_ID, messageId);
        bundle.putInt(EXTRA_MSG_TYPE, msgType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_detail;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        mDatas = new ArrayList<>();
        mRecyclerAdapter = new MessageDetailAdapter(getActivity(), mRecyclerviewMsgDetail, mDatas);
        mRecyclerviewMsgDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerviewMsgDetail.setAdapter(mRecyclerAdapter);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        Bundle bundle = getArguments();
        mPosition = bundle.getInt(EXTRA_MSG_POSITION, 0);
        mMessageId = bundle.getString(EXTRA_MSG_ID);
        mMsgType = bundle.getInt(EXTRA_MSG_TYPE, 0);
        startLoad();
    }

    @Override
    protected void initListener() {
        //防止按钮重复点击
        RxView.clicks(mImageAgree).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (UserHelper.getWorkStatus()) {
                            mPresenter.handleMessage(mMessageId, mDeskMsgDetail, mPosition, true);
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
                        if (UserHelper.getWorkStatus()) {
                            getDialogUtil().showDialog(R.string.material_dialog_title, R.string.msg_detail_order_reject_hint, true, new SingleButtonCallback() {
                                @Override
                                public void onClick(DialogUtilAction which) {
                                    if (which == DialogUtilAction.POSITIVE) {
                                        mPresenter.handleMessage(mMessageId, mDeskMsgDetail, mPosition, false);
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
    public void setPresenter(MessageDetailContract.Presenter presenter) {
        this.mPresenter = (MessageDetailPresenter) presenter;
    }


    @Override
    public void showMsgDetail(DeskMsgDetail deskMsgDetail) {
        if (null != deskMsgDetail) {
            mDeskMsgDetail = deskMsgDetail;
            //如果是自动审核、预付款支付消息 的消息类型，默认状态设置为已审核，因为这个类型的消息只是展示用，不能做操作
            if (mMsgType == MessageHelper.MsgType.TYPE_AUTO_CHECK
                    || mMsgType == MessageHelper.MsgType.TYPE_AUTO_CHECK_PRE_PAY
                    || mMsgType == MessageHelper.MsgType.TYPE_PREPAY_SCAN_DESK) {
                mDeskMsgDetail.setStatus(MessageDetailHelper.OrderState.STATE_AGREED);
            }
        }
        mDatas.addAll(DataMapLayer.getDeskMsgDetailRecyclerItemList(deskMsgDetail));
        if (mDeskMsgDetail.getStatus() == MessageDetailHelper.OrderState.STATE_CHECK_PENDING) {
            mFrameLayoutAgree.setVisibility(View.VISIBLE);
            if (mDeskMsgDetail.getMessageType() == MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT) {
                mFrameLayoutReject.setVisibility(View.GONE);
            } else {
                mFrameLayoutReject.setVisibility(View.VISIBLE);
            }
        }
        mRecyclerAdapter.notifyDataSetChanged();
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
    public void printInstance(AuditOrderResponse auditOrderResponse) {
        if (null != mDeskMsgDetail && null != auditOrderResponse) {
            int messageType = mDeskMsgDetail.getMessageType();
            switch (messageType) {
                //点菜
                case MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK:
                case MessageHelper.MsgType.TYPE_AUTO_CHECK_PRE_PAY:
                case MessageHelper.MsgType.TYPE_PREPAY_SCAN_DESK:
                case MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT:
                    // 打印小票
                    CcdPrintHelper.printOrder(getActivity()
                            , PrintData.TYPE_SELF_ORDER
                            , PrintData.BIZ_TYPE_PRINT_DISHES_ORDER
                            , auditOrderResponse.getOrderId()
                            , mDeskMsgDetail.getSeatCode());
                    // 打印标签
                    CcdPrintHelper.printLabelOrderInstance(getActivity()
                            , PrintData.BIZ_TYPE_PRINT_LABEL
                            , auditOrderResponse.getOrderId());
                    break;
                //加菜
                case MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER:
                case MessageHelper.MsgType.TYPE_AUTO_CHECK:
                    if (null != auditOrderResponse.getInstanceIds() && !auditOrderResponse.getInstanceIds().isEmpty()) {
                        // 打印小票
                        CcdPrintHelper.printInstance(getActivity()
                                , PrintData.TYPE_SELF_ORDER
                                , PrintData.BIZ_TYPE_PRINT_ADD_INSTANCE
                                , auditOrderResponse.getInstanceIds()
                                , mDeskMsgDetail.getSeatCode()
                                , auditOrderResponse.getOrderId());
                        // 打印标签
                        CcdPrintHelper.printLabelInstance(getActivity()
                                , PrintData.BIZ_TYPE_PRINT_LABEL
                                , auditOrderResponse.getInstanceIds());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        startLoad();
    }

    private void startLoad() {
        mPresenter.loadMsgDetail(mDatas, mMessageId);
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }
}
