package com.zmsoft.ccd.module.main.greenhand;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.event.main.RefreshMainGuideEvent;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.module.main.MainActivity;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/20 10:52.
 */

public class MainGreenHandFragment extends Fragment {

    interface Constants {
        int STEP_1 = 1;
        int STEP_2 = 2;
        int STEP_3 = 3;
        int STEP_4 = 4;
        int STEP_5 = 5;
        int STEP_6 = 6;
        int STEP_FINISH = 7;
    }

    @BindView(R.id.header_main_guide_shop_limit)
    View mViewHeaderShopLimit;
    @BindView(R.id.holder_place_main_guide_delivery)
    View mViewHolderPlaceDelivery;

    @BindView(R.id.text_main_guide_step_1)
    TextView mTextStep1;
    @BindView(R.id.text_main_guide_step_1_hint)
    TextView mTextStep1hint;
    @BindView(R.id.view_main_guide_step_2)
    View mViewStep2;
    @BindView(R.id.view_main_guide_step_2_hint)
    TextView mTextStep2hint;
    @BindView(R.id.view_main_guide_step_3)
    View mViewStep3;
    @BindView(R.id.view_main_guide_step_3_hint)
    TextView mTextStep3hint;
    @BindView(R.id.view_main_guide_step_4)
    View mViewStep4;
    @BindView(R.id.text_main_guide_step_4_hint)
    TextView mTextStep4hint;
    @BindView(R.id.view_main_guide_step_5)
    View mViewStep5;
    @BindView(R.id.text_main_guide_step_5_hint)
    TextView mTextStep5hint;
    @BindView(R.id.view_main_guide_step_6)
    View mViewStep6;
    @BindView(R.id.text_main_guide_step_6_hint)
    TextView mTextStep6hint;
    @BindView(R.id.text_main_guide_next)
    TextView mTextNext;

    private Unbinder mUnbinder;
    private int mStep;
    private boolean mIsShowOnlineTakeoutItem;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_guide, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mStep = Constants.STEP_1;
        mIsShowOnlineTakeoutItem = false;
        updateView(mStep);
        EventBusHelper.register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusHelper.unregister(this);
        mUnbinder.unbind();
    }

    //================================================================================
    // update view
    //================================================================================
    private void updateView(int step) {
        mTextStep1.setVisibility(View.INVISIBLE);
        mTextStep1hint.setVisibility(View.INVISIBLE);
        mViewStep2.setBackgroundColor(ContextCompat.getColor(GlobalVars.context, R.color.transparent70));
        mTextStep2hint.setVisibility(View.INVISIBLE);
        mViewStep3.setBackgroundColor(ContextCompat.getColor(GlobalVars.context, R.color.transparent70));
        mTextStep3hint.setVisibility(View.INVISIBLE);
        mViewStep4.setBackgroundColor(ContextCompat.getColor(GlobalVars.context, R.color.transparent70));
        mTextStep4hint.setVisibility(View.INVISIBLE);
        mViewStep5.setBackgroundColor(ContextCompat.getColor(GlobalVars.context, R.color.transparent70));
        mTextStep5hint.setVisibility(View.INVISIBLE);
        mViewStep6.setBackgroundColor(ContextCompat.getColor(GlobalVars.context, R.color.transparent70));
        mTextStep6hint.setVisibility(View.INVISIBLE);

        switch (step) {
            case Constants.STEP_1:
                mTextStep1.setVisibility(View.VISIBLE);
                mTextStep1hint.setVisibility(View.VISIBLE);
                break;
            case Constants.STEP_2:
                mViewStep2.setBackgroundColor(ContextCompat.getColor(GlobalVars.context, R.color.transparent));
                mTextStep2hint.setVisibility(View.VISIBLE);
                break;
            case Constants.STEP_3:
                mViewStep3.setBackgroundColor(ContextCompat.getColor(GlobalVars.context, R.color.transparent));
                mTextStep3hint.setVisibility(View.VISIBLE);
                break;
            case Constants.STEP_4:
                mViewStep4.setBackground(ContextCompat.getDrawable(GlobalVars.context, R.drawable.shape_guide_stoke));
                mTextStep4hint.setVisibility(View.VISIBLE);
                updateMainActivityTabItem(step);
                break;
            case Constants.STEP_5:
                mViewStep5.setBackground(ContextCompat.getDrawable(GlobalVars.context, R.drawable.shape_guide_stoke));
                mTextStep5hint.setVisibility(View.VISIBLE);
                updateMainActivityTabItem(step);
                break;
            case Constants.STEP_6:
                mViewStep6.setBackground(ContextCompat.getDrawable(GlobalVars.context, R.drawable.shape_guide_stoke));
                mTextStep6hint.setVisibility(View.VISIBLE);
                mTextNext.setText(getString(R.string.main_activity_green_hand_start_use));
                updateMainActivityTabItem(step);
                break;
            case Constants.STEP_FINISH:
                updateMainActivityTabItem(step);
                break;
            default:
                break;
        }
    }

    private void updateMainActivityTabItem(int step) {
        if (!(getActivity() instanceof MainActivity)) {
            return;
        }
        MainActivity activity = (MainActivity) getActivity();
        switch (step) {
            case Constants.STEP_4:
                activity.updateTabItem(MainActivity.TAB_ORDER_FIND);
                break;
            case Constants.STEP_5:
                activity.updateTabItem(MainActivity.TAB_MSG_CENTER);
                break;
            case Constants.STEP_6:
                activity.updateTabItem(MainActivity.TAB_SET);
                break;
            case Constants.STEP_FINISH:
                activity.updateTabItem(MainActivity.TAB_MAIN);
                activity.finishGreenHand();
                break;
        }
    }

    //================================================================================
    // on click
    //================================================================================
    // 拦截本页面所有点击事件
    @OnClick(R.id.content_main_guide)
    public void onClickContent() {
    }

    @OnClick(R.id.text_main_guide_next)
    public void onClickNextText() {
        // 在线外卖
        if (Constants.STEP_2 == mStep) {
            if (mIsShowOnlineTakeoutItem) {
                mStep++;
            } else {
                // 当前设置没有“在线外卖”item，直接跳过，到下一步
                mStep += 2;
            }
        } else {
            mStep++;
        }
        updateView(mStep);
    }

    //================================================================================
    // event bus
    //================================================================================
    @Subscribe
    public void onEvent(RefreshMainGuideEvent refreshMainGuideEvent) {
        if (RefreshMainGuideEvent.VIEW_TYPE.DELIVERY == refreshMainGuideEvent.getViewType()) {
            mViewHolderPlaceDelivery.setVisibility(refreshMainGuideEvent.isShow() ? View.VISIBLE : View.GONE);
        } else if (RefreshMainGuideEvent.VIEW_TYPE.SHOP_LIMIT == refreshMainGuideEvent.getViewType()) {
            mViewHeaderShopLimit.setVisibility(refreshMainGuideEvent.isShow() ? View.VISIBLE : View.GONE);
        } else if (RefreshMainGuideEvent.VIEW_TYPE.ONLINE_TAKEOUT_ITEM == refreshMainGuideEvent.getViewType()) {
            mIsShowOnlineTakeoutItem = refreshMainGuideEvent.isShow();
        }
    }
}
