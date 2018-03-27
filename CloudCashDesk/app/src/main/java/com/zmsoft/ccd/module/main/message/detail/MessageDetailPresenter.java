package com.zmsoft.ccd.module.main.message.detail;

import android.text.TextUtils;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.msgcenter.MsgCenterRepository;
import com.zmsoft.ccd.event.message.NotifyDataChangeEvent;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.message.AuditOrderResponse;
import com.zmsoft.ccd.lib.bean.message.DeskMsgDetail;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailRecyclerItem;
import com.zmsoft.ccd.helper.MessageDetailHelper;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.List;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class MessageDetailPresenter implements MessageDetailContract.Presenter {
    private MessageDetailContract.View mView;
    private MsgCenterRepository mMsgCenterRepository;

    @Inject
    public MessageDetailPresenter(MessageDetailContract.View view, MsgCenterRepository msgCenterRepository) {
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
    public void loadMsgDetail(final List<DeskMsgDetailRecyclerItem> datas, String messageId) {
        mView.showLoadingView();
        mMsgCenterRepository.loadMsgDetail(messageId, new com.zmsoft.ccd.data.Callback<DeskMsgDetail>() {
            @Override
            public void onSuccess(DeskMsgDetail deskMsgDetail) {
                if (null == mView) {
                    return;
                }
                mView.showContentView();
                mView.showMsgDetail(deskMsgDetail);
                if (deskMsgDetail.getStatus() == MessageDetailHelper.OrderState.STATE_CHECK_PENDING) {
                    mView.showBottomView();
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
    public void handleMessage(final String messageId, final DeskMsgDetail deskMsgDetail, final int position
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
                    mView.printInstance(data);
                }
                refreshMessage(deskMsgDetail, position, isAgree, messageId);
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
    public void refreshMessage(DeskMsgDetail deskMsgDetail, int position, boolean isAgree, String messageId) {
        if (null != deskMsgDetail) {
            deskMsgDetail.setStatus(isAgree ? MessageDetailHelper.OrderState.STATE_AGREED
                    : MessageDetailHelper.OrderState.STATE_REJECTED);
            //通知消息中心列表页刷新UI
            NotifyDataChangeEvent event = new NotifyDataChangeEvent();
            event.setAgreed(isAgree);
            event.setPosition(position);
            event.setMessageId(messageId);
            EventBusHelper.post(event);
            //更新当前UI
            mView.showMsgDetail(null);
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
