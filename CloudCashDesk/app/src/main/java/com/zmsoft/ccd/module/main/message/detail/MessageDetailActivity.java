package com.zmsoft.ccd.module.main.message.detail;

import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.msgcenter.dagger.DaggerMessageComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.message.detail.dagger.DaggerMsgCenterDetailComponent;
import com.zmsoft.ccd.module.main.message.detail.dagger.MsgCenterDetailPresenterModule;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.main.message.detail.MessageDetailActivity.PATH_MESSAGE_DETAIL;

/**
 * 消息详情页
 *
 * @author DangGui
 * @create 2016/12/23.
 */
@Route(path = PATH_MESSAGE_DETAIL)
public class MessageDetailActivity extends ToolBarActivity {

    public static final String PATH_MESSAGE_DETAIL = "/main/msg_detail";
    //在消息列表中的position
    public static final String EXTRA_MSG_POSITION = "extra_msg_position";
    //消息id
    public static final String EXTRA_MSG_ID = "extra_msg_id";
    //消息类型
    public static final String EXTRA_MSG_TYPE = "extra_msg_type";
    @Inject
    MessageDetailPresenter mPresenter;
    //在消息列表中的position
    @Autowired(name = EXTRA_MSG_POSITION)
    int mPosition;
    //消息id
    @Autowired(name = EXTRA_MSG_ID)
    String mMessageId;

    @Autowired(name = EXTRA_MSG_TYPE)
    int mMsgType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);

        MessageDetailFragment fragment = (MessageDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);

        if (fragment == null) {
            fragment = MessageDetailFragment.newInstance(mPosition, mMessageId, mMsgType);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.content);
        }

        DaggerMsgCenterDetailComponent.builder()
                .messageComponent(
                        DaggerMessageComponent.builder().appComponent(CcdApplication.getInstance().getAppComponent())
                                .build())
                .msgCenterDetailPresenterModule(new MsgCenterDetailPresenterModule(fragment))
                .build()
                .inject(this);
    }
}
