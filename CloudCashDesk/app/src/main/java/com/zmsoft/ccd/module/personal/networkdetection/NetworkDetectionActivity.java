package com.zmsoft.ccd.module.personal.networkdetection;

import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.personal.networkdetection.dagger.DaggerNetworkDetectionComponent;
import com.zmsoft.ccd.module.personal.networkdetection.dagger.NetworkDetectionPresenterModule;
import com.zmsoft.ccd.module.personal.networkdetection.fragment.NetworkDetectionFragment;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.personal.networkdetection.NetworkDetectionActivity.PATH_NETWORK_DETECTION;


/**
 * 个人中心--网络检测
 *
 * @author mantianxing
 * @create 2017/12/15.
 */
@Route(path = PATH_NETWORK_DETECTION)
public class NetworkDetectionActivity extends ToolBarActivity {

    public static final String PATH_NETWORK_DETECTION = "/main/networkdetection";

    @Inject
    NetworkDetectionPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);

        NetworkDetectionFragment networkDetectionFragment = (NetworkDetectionFragment)getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if(networkDetectionFragment == null) {
            networkDetectionFragment = NetworkDetectionFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(),networkDetectionFragment,R.id.content);
        }

        DaggerNetworkDetectionComponent.builder()
                .networkDetectionPresenterModule(new NetworkDetectionPresenterModule(networkDetectionFragment))
                .build()
                .inject(this);
    }

}
