package com.zmsoft.ccd.module.personal.networkdetection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.source.system.AppSystemSource;
import com.zmsoft.ccd.data.source.system.IAppSystemSource;
import com.zmsoft.ccd.lib.base.constant.NetworkDetectionConstant;
import com.zmsoft.ccd.lib.base.constant.NetworkStateScope;

import javax.inject.Inject;

/**
 * @author mantianxing
 * @create 2017/12/15.
 */

public class NetworkDetectionPresenter implements NetworkDetectionContract.presenter {

    private NetworkDetectionContract.View mView;
    private IAppSystemSource iAppSystemSource;

    @Inject
    NetworkDetectionPresenter(NetworkDetectionContract.View view) {
        mView = view;
        iAppSystemSource = new AppSystemSource();
    }

    /**
     * 加注解确保该方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public int networkDetection(final String entityId, final String appCode, final int version, final int count,final long sumDuration) {
        if(count < NetworkStateScope.NETWORK_REQUEST_COUNT) {
            iAppSystemSource.checkNetworkLatency(entityId, appCode, version, new IAppSystemSource.CheckNetworkLatencyCallback() {
                @Override
                public void onResponse(long duration) {
                    if (mView == null) {
                        return;
                    }
                    networkDetection(entityId, appCode, version, (count + 1),(sumDuration + duration));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    mView.loadDataError(throwable.getMessage());
                }
            });
        }else {
            mView.detectionSuccess((int)(sumDuration/NetworkStateScope.NETWORK_REQUEST_COUNT));
        }
        return 0;
    }

    /**
     * 检测网络是否连接
     */
    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) GlobalVars.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null) {
            return manager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

}
