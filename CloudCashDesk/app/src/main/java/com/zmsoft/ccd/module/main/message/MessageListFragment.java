package com.zmsoft.ccd.module.main.message;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.DataMapLayer;
import com.zmsoft.ccd.data.source.msgcenter.dagger.DaggerMessageComponent;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.event.message.NotifyDataChangeEvent;
import com.zmsoft.ccd.helper.CommonHelper;
import com.zmsoft.ccd.helper.MessageHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.filter.FilterItem;
import com.zmsoft.ccd.lib.bean.message.DeskMessage;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.widget.couponview.LinearLayoutCouponView;
import com.zmsoft.ccd.module.main.message.adapter.DeskMessageAdapter;
import com.zmsoft.ccd.module.main.message.dagger.DaggerMsgCenterComponent;
import com.zmsoft.ccd.module.main.message.dagger.MsgCenterPresenterModule;
import com.zmsoft.ccd.module.main.message.detail.MessageDetailActivity;
import com.zmsoft.ccd.module.main.message.takeout.TakeoutDetailActivity;
import com.zmsoft.ccd.module.setting.MsgSettingActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * 电子收款明细列表页
 *
 * @author DangGui
 * @create 2017/08/12.
 */
public class MessageListFragment extends BaseListFragment implements MessageListContract.View {
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.linear_title)
    LinearLayoutCouponView mLinearTitle;
    @BindView(R.id.text_batch_handle)
    TextView mTextBatchHandle;
    @BindView(R.id.view_divider)
    View mDivideView;

    @Inject
    MessageListPresenter mPresenter;
    /**
     * 消息种类（新消息、已处理消息、所有消息）
     */
    private int mMsgCategory;

    public static MessageListFragment newInstance() {
        Bundle args = new Bundle();
        MessageListFragment fragment = new MessageListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_list;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        DaggerMsgCenterComponent.builder()
                .messageComponent(DaggerMessageComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .msgCenterPresenterModule(new MsgCenterPresenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mMsgCategory = MessageHelper.MsgCategory.MESSAGE_NEW;
        disableAutoRefresh();
        disableRefresh();
        showLoadingView();
    }

    @Override
    protected void initListener() {
        getAdapter().setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (null != data && data instanceof DeskMessage) {
                    DeskMessage deskMessage = ((DeskMessage) data);
                    boolean isValid = position < getAdapter().getList().size() && !TextUtils.isEmpty(deskMessage.getId())
                            && (deskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER
                            || deskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK
                            || deskMessage.getTy() == MessageHelper.MsgType.TYPE_AUTO_CHECK
                            || deskMessage.getTy() == MessageHelper.MsgType.TYPE_AUTO_CHECK_PRE_PAY
                            || deskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_SCAN_DESK
                            || deskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT);
                    if (isValid) {
                        MRouter.getInstance()
                                .build(MessageDetailActivity.PATH_MESSAGE_DETAIL)
                                .putInt(MessageDetailActivity.EXTRA_MSG_POSITION, position)
                                .putInt(MessageDetailActivity.EXTRA_MSG_TYPE, deskMessage.getTy())
                                .putString(MessageDetailActivity.EXTRA_MSG_ID, deskMessage.getId())
                                .navigation(getActivity());
                    } else {
                        boolean isTakeoutValid = position < getAdapter().getList().size() && !TextUtils.isEmpty(deskMessage.getId())
                                && (deskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK
                                || deskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE
                                || deskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_INDEPENDENT);
                        if (isTakeoutValid) {
                            TakeoutDetailActivity.launchActivity(getActivity(), deskMessage.getB_id()
                                    , deskMessage.getId(), position, deskMessage.getTy());
                        }
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
    }

    @Override
    protected void loadListData() {
        mPresenter.getMessageList(mMsgCategory, getPageIndex());
    }

    @Override
    protected int getAccentColor() {
        return R.color.accentColor;
    }

    @Override
    protected BaseListAdapter createAdapter() {
        return new DeskMessageAdapter(getActivity(), null);
    }


    @Override
    public void showDeskMsgList(List<DeskMessage> deskMessageList) {
        int actualSize = deskMessageList.size();
        renderListData(DataMapLayer.initDeskMsg(deskMessageList), actualSize);
        checkUnReadMsg();
    }

    @Override
    public void checkUnReadMsg() {
        //如果 顾客新消息 获取为空，则需要通知主界面隐藏消息中心的小红点
        boolean isValid = ((null == getAdapter().getList() || getAdapter().getList().isEmpty())
                && (mMsgCategory == MessageHelper.MsgCategory.MESSAGE_NEW || mMsgCategory == MessageHelper.MsgCategory.MESSAGE_ALL));
        if (isValid) {
            BaseEvents.CommonEvent event = BaseEvents.CommonEvent.EVENT_MSG_CENTER_UNREAD;
            event.setObject(false);
            EventBusHelper.post(event);
        }
    }

    /**
     * @param position 如果是只处理单条，position是合法的（>=0）,否则是-1,代表是批量处理
     */
    @Override
    public void notifyDataChanged(int position) {
        if (position >= 0) { //处理单条
            getAdapter().removeItem(position);
        } else { //批量处理
            getAdapter().notifyDataSetChanged();
            startLoad();
        }
        checkUnReadMsg();
    }

    @Override
    public void loadDataSuccess() {
        mLinearTitle.setVisibility(View.VISIBLE);
        mDivideView.setVisibility(View.VISIBLE);
        enableRefresh();
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
        if (getAdapter().getList().isEmpty()) {
            mLinearTitle.setVisibility(View.INVISIBLE);
            mDivideView.setVisibility(View.INVISIBLE);
            showErrorView(errorMessage);
        }
        loadListFailed();
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        resetParameters();
        loadListData();
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(MessageListContract.Presenter presenter) {
        this.mPresenter = (MessageListPresenter) presenter;
    }

    @Override
    protected void registerEventBus() {
        super.registerEventBus();
        EventBusHelper.register(this);
    }

    @Override
    protected void unRegisterEventBus() {
        super.unRegisterEventBus();
        EventBusHelper.unregister(this);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mMessageSettingItem = menu.findItem(R.id.save);
        mMessageSettingItem.setVisible(true);
        mMessageSettingItem.setTitle(R.string.msg_setting);
        mMessageSettingItem.setIcon(R.drawable.icon_menu_setting);
        mMessageSettingItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MRouter.getInstance().build(MsgSettingActivity.PATH_MSG_SETTING).navigation(this);
        return super.onOptionsItemSelected(item);
    }

    /**
     * <p>消息详情页面做了数据操作后，需要列表页item UI进行刷新，通知调用该方法</p>
     *
     * @param event
     */
    @Subscribe
    public void notifyDataChanged(NotifyDataChangeEvent event) {
        removeMsg(getAdapter().getList(), event.getPosition(), event.getMessageId());
    }

    @Subscribe
    public void onIKnowMsgHandled(BaseEvents.CommonEvent event) {
        if (event == BaseEvents.CommonEvent.EVENT_MSG_CENTER_IKNOW) {
            if (null != event.getObject()) {
                preBatchHandleMsg((Integer) event.getObject());
            }
        }
    }

    @Subscribe
    public void onMsgRefresh(BaseEvents.CommonEvent event) {
        if (event == BaseEvents.CommonEvent.EVENT_MSG_CENTER_REFRESH) {
            if (null != mStateView) {
                if (!mStateView.isRetryViewShowing()) {
                    startLoad();
                }
            } else {
                startLoad();
            }
        }
    }

    @OnClick(R.id.text_batch_handle)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_batch_handle:
                preBatchHandleMsg(-1);
                break;
            default:
                break;
        }
    }

    /**
     * <p>根据主界面右侧栏筛选的消息类型，消息中心列表页切换为不同类型的列表</p>
     * 顾客新消息<br />
     * 已处理消息<br />
     * 所有消息<br />
     */
    public void changeMsgList(FilterItem filterItem) {
        if (filterItem != null) {
            String code = filterItem.getCode();
            if (code.equals(FilterItem.MENU_ITEM_MESSAGE_ALL)) { //所有消息
                mMsgCategory = MessageHelper.MsgCategory.MESSAGE_ALL;
                refreshMsgTitle();
            } else if (code.equals(FilterItem.MENU_ITEM_MESSAGE_USER)) { //顾客新消息
                mMsgCategory = MessageHelper.MsgCategory.MESSAGE_NEW;
                refreshMsgTitle();
            } else if (code.equals(FilterItem.MENU_ITEM_MESSAGE_DEAL_WITH)) { // 已处理消息
                mMsgCategory = MessageHelper.MsgCategory.MESSAGE_HANDLED;
                refreshMsgTitle();
            }
        }
    }

    /**
     * 刷新标题文案
     */
    private void refreshMsgTitle() {
        switch (mMsgCategory) {
            case MessageHelper.MsgCategory.MESSAGE_ALL:
                mTextTitle.setText(R.string.msg_msgcenter_all);
                mTextBatchHandle.setVisibility(View.VISIBLE);
                break;
            case MessageHelper.MsgCategory.MESSAGE_HANDLED:
                mTextTitle.setText(R.string.msg_msgcenter_handled);
                mTextBatchHandle.setVisibility(View.INVISIBLE);
                break;
            case MessageHelper.MsgCategory.MESSAGE_NEW:
                mTextTitle.setText(R.string.msg_msgcenter_new);
                mTextBatchHandle.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        getAdapter().removeAll();
        disableRefresh();
        showLoadingView();
        startLoad();
    }

    /**
     * 批量处理不需要审核的消息之前判断需不需要弹框
     */
    private void preBatchHandleMsg(final int position) {
        if (UserHelper.getWorkStatus()) {
            final List batchHandleMsgList = mPresenter.getBatchUpdateMsgList(getAdapter().getList(), position);
            if (null != batchHandleMsgList) {
                if (mPresenter.isNeedBatchUpdateMsg(getAdapter().getList(), position)) {
                    getDialogUtil().showDialog(R.string.material_dialog_title, String.format(getString(R.string.msg_msgcenter_result_all_handled_hint)
                            , batchHandleMsgList.size()), true, new SingleButtonCallback() {
                        @Override
                        public void onClick(DialogUtilAction which) {
                            if (which == DialogUtilAction.POSITIVE) {
                                batchHandleMsg(batchHandleMsgList, position);
                            }
                        }
                    });
                } else {
                    batchHandleMsg(batchHandleMsgList, position);
                }
            }
        } else {
            //如果是已下班状态，弹框提示
            CommonHelper.showOffWorkDialog(getActivity(), getDialogUtil());
        }
    }

    /**
     * 批量处理不需要审核的消息
     */
    private void batchHandleMsg(List batchHandleMsgList, int position) {
        String batchHandleJson = mPresenter.getBatchUpdateMsgIdJson(batchHandleMsgList);
        if (!TextUtils.isEmpty(batchHandleJson)) {
            mPresenter.batchUpdateMessage(batchHandleMsgList, batchHandleJson, MessageHelper.MsgCategory.MESSAGE_HANDLED
                    , getString(R.string.msg_msgcenter_result_all_handled), position);
        }
        //clear通知栏notification
        if ((context.getSystemService(Context.NOTIFICATION_SERVICE)) != null) {
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
        }
    }

    public void refreshByCheckShop() {
        startLoad();
    }

    private void startLoad() {
        startRefresh();
    }

    /**
     * 消息详情审核过消息之后，删除消息列表中对应的message
     *
     * @param deskList
     * @param position
     * @param messageId
     */
    private void removeMsg(List<DeskMessage> deskList, int position, String messageId) {
        boolean isValid = (position >= 0 && position < deskList.size() && deskList.get(position).getId().equals(messageId));
        if (isValid) {
            getAdapter().removeItem(position);
        }
    }
}
