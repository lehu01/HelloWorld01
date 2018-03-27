package com.zmsoft.ccd.module.main.message.takeout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.msgcenter.dagger.DaggerMessageComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.message.takeout.dagger.DaggerRetailTakeoutMsgDetailComponent;
import com.zmsoft.ccd.module.main.message.takeout.dagger.RetailTakeoutMsgDetailPresenterModule;

import javax.inject.Inject;

/**
 * 消息详情页
 *
 * @author DangGui
 * @create 2016/12/23.
 */
public class RetailTakeoutMsgDetailActivity extends ToolBarActivity {
    @Inject
    RetailTakeoutMsgDetailPresenter mPresenter;
    //在消息列表中的position
    private int mPosition;
    //消息id
    private String mMessageId;
    //订单id
    private String mOrderId;

    private int mMsgType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        mOrderId = getIntent().getStringExtra(RouterPathConstant.TakeOutMsgDetail.EXTRA_ORDER_ID);
        mMessageId = getIntent().getStringExtra(RouterPathConstant.TakeOutMsgDetail.EXTRA_MSG_ID);
        mPosition = getIntent().getIntExtra(RouterPathConstant.TakeOutMsgDetail.EXTRA_POSITION, -1);
        mMsgType = getIntent().getIntExtra(RouterPathConstant.TakeOutMsgDetail.EXTRA_MSG_TYPE, -1);
        RetailTakeoutMsgDetailFragment fragment = (RetailTakeoutMsgDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);

        if (fragment == null) {
            fragment = RetailTakeoutMsgDetailFragment.newInstance(mPosition, mMessageId, mOrderId, mMsgType);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.content);
        }

        DaggerRetailTakeoutMsgDetailComponent.builder()
                .messageComponent(
                        DaggerMessageComponent.builder()
                                .appComponent(CcdApplication.getInstance().getAppComponent())
                                .build())
                .retailTakeoutMsgDetailPresenterModule(new RetailTakeoutMsgDetailPresenterModule(fragment))
                .build()
                .inject(this);
    }

    public static void launchActivity(Context context, String orderId, String msgId, int position, int msgType) {
        Intent intent = new Intent(context, RetailTakeoutMsgDetailActivity.class);
        intent.putExtra(RouterPathConstant.TakeOutMsgDetail.EXTRA_ORDER_ID, orderId);
        intent.putExtra(RouterPathConstant.TakeOutMsgDetail.EXTRA_MSG_ID, msgId);
        intent.putExtra(RouterPathConstant.TakeOutMsgDetail.EXTRA_POSITION, position);
        intent.putExtra(RouterPathConstant.TakeOutMsgDetail.EXTRA_MSG_TYPE, msgType);
        context.startActivity(intent);
    }
}
