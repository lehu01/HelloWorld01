package com.zmsoft.ccd.module.personal.networkdetection.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.BuildConfig;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.constant.NetworkStateScope;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.DeviceUtil;
import com.zmsoft.ccd.module.personal.networkdetection.NetworkDetectionContract;
import com.zmsoft.ccd.network.CommonConstant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author mantianxing
 * @create 2017/12/15.
 */

public class NetworkDetectionFragment extends BaseFragment implements NetworkDetectionContract.View {

    @BindView(R.id.button_start_detection)
    Button mButtonStartDetection;
    @BindView(R.id.text_device_name)
    TextView mTextDeviceName;
    @BindView(R.id.text_device_type)
    TextView mTextDeviceType;
    @BindView(R.id.text_device_resolution)
    TextView mTextDeviceResolution;
    @BindView(R.id.text_android_version)
    TextView mTextAndroidVersion;
    @BindView(R.id.image_network_detection_circle_change)
    ImageView mImageNetworkDetectionCircleChange;
    @BindView(R.id.image_network_state)
    ImageView mImageNetworkState;
    @BindView(R.id.network_state)
    TextView mNetworkState;
    @BindView(R.id.network_value)
    TextView mNetworkValue;
    @BindView(R.id.network_ms)
    TextView mNetworkMs;

    NetworkDetectionContract.presenter mPresenter;

    public static NetworkDetectionFragment newInstance() {
        Bundle args = new Bundle();
        NetworkDetectionFragment fragment = new NetworkDetectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(NetworkDetectionContract.presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_network_detection;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mTextDeviceName.setText(Build.BRAND);
        mTextDeviceType.setText(Build.MODEL);
        mTextAndroidVersion.setText(Build.VERSION.RELEASE);
        mTextDeviceResolution.setText(DeviceUtil.getScreenWidAndHeight(GlobalVars.context));
    }

    @Override
    protected void initListener() {
    }

    @OnClick(R.id.button_start_detection)
    public void startDetecting() {
        // 检测网络
        if (!mPresenter.isNetworkAvailable()) {  // 无网络
            showNetworkState((long) NetworkStateScope.NETWORK_NO);
        } else {
            mButtonStartDetection.setText(R.string.detecting);
            mButtonStartDetection.setEnabled(false);
            mImageNetworkDetectionCircleChange.setVisibility(View.VISIBLE);
            startRotate(getContext(), mImageNetworkDetectionCircleChange);
            mPresenter.networkDetection(UserHelper.getEntityId(), CommonConstant.APP_CODE, BuildConfig.VERSION_CODE, 0, 0);
        }
    }

    @Override
    public void detectionSuccess(long duration) {
        resetUi();
        showNetworkState(duration);
    }

    /**
     * 提示错误信息
     *
     * @param errorMessage
     */
    @Override
    public void loadDataError(String errorMessage) {
        resetUi();
        toastMsg(errorMessage);
    }

    /**
     * 重置Ui到初始状态
     */
    private void resetUi(){
        endRotate(mImageNetworkDetectionCircleChange);
        mImageNetworkDetectionCircleChange.setVisibility(View.GONE);
        mButtonStartDetection.setEnabled(true);
        mButtonStartDetection.setText(R.string.start_detecting);
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    /**
     * 开始动画
     *
     * @param context
     * @param imageView
     */
    public void startRotate(Context context, ImageView imageView) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_network_change_circle);
        LinearInterpolator linearLayout = new LinearInterpolator();
        animation.setInterpolator(linearLayout);
        if (animation != null) {
            imageView.startAnimation(animation);
        }
    }

    /**
     * 结束动画
     *
     * @param imageView
     */
    public void endRotate(ImageView imageView) {
        imageView.clearAnimation();
    }

    /**
     * 显示网络状态
     */
    private void showNetworkState(long duration) {
        mImageNetworkState.setVisibility(View.GONE);
        mNetworkState.setVisibility(View.VISIBLE);

        mNetworkState.setTextColor(ContextCompat.getColor(getContext(), setNetworkScope(duration)));
        mNetworkValue.setTextColor(ContextCompat.getColor(getContext(), setNetworkScope(duration)));
        mNetworkMs.setTextColor(ContextCompat.getColor(getContext(), setNetworkScope(duration)));
    }

    /**
     * 划分网络延迟范围
     *
     * @param duration
     */
    private int setNetworkScope(long duration) {
        if (duration == NetworkStateScope.NETWORK_NO) {  //无网络
            mNetworkState.setText(R.string.network_no);
            mNetworkValue.setText(R.string.network_value);
            return R.color.network_state_red;
        } else {
            mNetworkValue.setText(String.valueOf(duration));
            if (duration >= NetworkStateScope.NETWORK_EXCELLENT_LOW && duration < NetworkStateScope.NETWORK_EXCELLENT_high) {  // 优
                mNetworkState.setText(R.string.network_excellent);
                return R.color.network_state_green;
            } else if (duration >= NetworkStateScope.NETWORK_EXCELLENT_high && duration < NetworkStateScope.NETWORK_GOOD_HIGH) {  // 良
                mNetworkState.setText(R.string.network_good);
                return R.color.network_state_yellow;
            } else {  // 差
                mNetworkState.setText(R.string.network_poor);
                return R.color.network_state_red;
            }
        }
    }

}
