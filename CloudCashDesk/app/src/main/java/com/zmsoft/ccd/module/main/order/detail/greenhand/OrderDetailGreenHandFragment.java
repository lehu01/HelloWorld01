package com.zmsoft.ccd.module.main.order.detail.greenhand;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.module.main.order.detail.OrderDetailActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/23 16:03.
 */

public class OrderDetailGreenHandFragment extends Fragment {

    private Unbinder mUnbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail_green_hand, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    //================================================================================
    // on click
    //================================================================================
    // 拦截本页面所有点击事件
    @OnClick(R.id.content_order_detail_guide)
    public void onClickContent() {
    }

    @OnClick({R.id.text_order_detail_guide_start_use, R.id.layout_order_detail_guide_food})
    public void onClickStartUse() {
        if (!(getActivity() instanceof OrderDetailActivity)) {
            return;
        }
        OrderDetailActivity activity = (OrderDetailActivity) getActivity();
        activity.finishGreenHand();
    }
}
