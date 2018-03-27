package com.zmsoft.ccd.module.printconfig.print.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.module.printconfig.label.LabelPrintConfigActivity;
import com.zmsoft.ccd.module.printconfig.smallticket.SmallTicketPrintConfigActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/18 14:16
 *     desc  : 配置打印机入口
 * </pre>
 */
public class PrintConfigFragment extends BaseFragment {

    @BindView(R.id.relative_small_ticket)
    RelativeLayout mRelativeSmallTicket;
    @BindView(R.id.relative_label_ticket)
    RelativeLayout mRelativeLabelTicket;

    public static PrintConfigFragment newInstance() {
        PrintConfigFragment fragment = new PrintConfigFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_print_config;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {

    }

    @OnClick({R.id.relative_small_ticket, R.id.relative_label_ticket})
    void processClick(View view) {
        switch (view.getId()) {
            case R.id.relative_small_ticket:
                gotoSmallTicketPrintConfigActivity();
                break;
            case R.id.relative_label_ticket:
                gotoLabelPrintConfigActivity();
                break;
        }
    }

    /**
     * 小票配置打印机
     */
    private void gotoSmallTicketPrintConfigActivity() {
        Intent intent = new Intent(getActivity(), SmallTicketPrintConfigActivity.class);
        startActivity(intent);
    }

    /**
     * 标签配置打印机
     */
    private void gotoLabelPrintConfigActivity() {
        Intent intent = new Intent(getActivity(), LabelPrintConfigActivity.class);
        startActivity(intent);
    }
}
