package com.zmsoft.ccd.module.main.message.takeout;

import android.text.TextUtils;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.msgcenter.MsgCenterRepository;
import com.zmsoft.ccd.event.message.NotifyDataChangeEvent;
import com.zmsoft.ccd.helper.MessageDetailHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.message.AuditOrderResponse;
import com.zmsoft.ccd.lib.bean.message.TakeoutMsgDetailResponse;
import com.zmsoft.ccd.network.HttpHelper;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class RetailTakeoutMsgDetailPresenter implements RetailTakeoutMsgDetailContract.Presenter {
    private RetailTakeoutMsgDetailContract.View mView;
    private MsgCenterRepository mMsgCenterRepository;

    @Inject
    public RetailTakeoutMsgDetailPresenter(RetailTakeoutMsgDetailContract.View view, MsgCenterRepository msgCenterRepository) {
        this.mView = view;
        this.mMsgCenterRepository = msgCenterRepository;
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void loadMsgDetail(String msgId) {
        mView.showLoadingView();
        mMsgCenterRepository.loadTakeoutMsgDetail(msgId, new com.zmsoft.ccd.data.Callback<TakeoutMsgDetailResponse>() {
            @Override
            public void onSuccess(TakeoutMsgDetailResponse takeoutMsgDetailResponse) {
                if (null == mView) {
                    return;
                }
                mView.showContentView();
                if (null != takeoutMsgDetailResponse && null != takeoutMsgDetailResponse.getTakeoutOrderVo()) {
                    mView.successGetMsgDetail(takeoutMsgDetailResponse);
                    if (takeoutMsgDetailResponse.getMsgState() == MessageDetailHelper.OrderState.STATE_CHECK_PENDING) {
                        mView.showBottomView();
                    }
                } else {
                    mView.failGetMsgDetail(context.getString(R.string.server_no_data));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.showErrorView(body.getMessage());
            }
        });
    }

    @Override
    public void handleMessage(final String messageId, final TakeoutMsgDetailResponse takeoutMsgDetailResponse, final int position
            , final boolean isAgree) {
        mView.showLoading(context.getString(R.string.dialog_handling), false);
        mMsgCenterRepository.handleMessage(messageId, isAgree, new com.zmsoft.ccd.data.Callback<AuditOrderResponse>() {
            @Override
            public void onSuccess(AuditOrderResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                //打印小票
                if (isAgree) {
                    mView.printInstance(data, takeoutMsgDetailResponse);
                }
                refreshMessage(takeoutMsgDetailResponse, position, isAgree, messageId);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                if (null != body && !TextUtils.isEmpty(body.getErrorCode())
                        && body.getErrorCode().equals(HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR)) {
                    mView.loadDataError(String.format(context.getResources()
                                    .getString(R.string.alert_permission_deny)
                            , context.getString(R.string.module_menu_cart_open_order)));
                } else if (null != body && !TextUtils.isEmpty(body.getMessage())) {
                    mView.loadDataError(body.getMessage());
                }
            }
        });
    }

    @Override
    public void refreshMessage(TakeoutMsgDetailResponse takeoutMsgDetailResponse, int position, boolean isAgree, String messageId) {
        if (null != takeoutMsgDetailResponse) {
            takeoutMsgDetailResponse.setMsgState(isAgree ? MessageDetailHelper.OrderState.STATE_AGREED
                    : MessageDetailHelper.OrderState.STATE_REJECTED);
            //通知消息中心列表页刷新UI
            NotifyDataChangeEvent event = new NotifyDataChangeEvent();
            event.setAgreed(isAgree);
            event.setPosition(position);
            event.setMessageId(messageId);
            EventBusHelper.post(event);
            //更新当前UI
//            mView.successGetMsgDetail(null);
            //关闭页面
            mView.closeView();
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
