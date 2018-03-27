package com.zmsoft.ccd.module.main.home.presenter;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.home.HomeRepository;
import com.zmsoft.ccd.data.source.workmodel.WorkModelSource;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.home.HomeCount;

import java.util.List;
import java.util.Map;
import com.zmsoft.ccd.lib.bean.shop.ShopLimitVo;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;
import static com.zmsoft.ccd.lib.bean.Base.STRING_TRUE;

/**
 * @author DangGui
 * @create 2017/8/15.
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private HomeRepository mHomeRepository;
    private WorkModelSource mWorkModelSourceRepository;
    @Inject
    public HomePresenter(HomeContract.View view, HomeRepository homeRepository) {
        this.mView = view;
        this.mHomeRepository = homeRepository;
        this.mWorkModelSourceRepository = new WorkModelSource();
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void getWorkModel(String entityId, List<String> codeList) {
        mWorkModelSourceRepository.getWorkModel(entityId, codeList, new Callback<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> data) {
                if (mView == null) {
                    return;
                }
                if (data != null) {
                    boolean isMixture = STRING_TRUE.equals(data.get(SystemDirCodeConstant.IS_USE_LOCAL_CASH));
                    mView.successGetWorkMode(isMixture);
                    UserHelper.saveWorkModeToSp(isMixture);
                }
                getHomeUnreadCount();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                getHomeUnreadCount();
            }
        });
    }

    public void getShopLimitDay(String entityId) {
        mHomeRepository.getShopLimitDay(entityId, new Callback<ShopLimitVo>() {
            @Override
            public void onSuccess(ShopLimitVo data) {
                if (null == mView) {
                    return;
                }
                mView.getShopLimitDaySuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                getHomeUnreadCount();
                if (null == mView) {
                    return;
                }
                mView.getShopLimitDayFailure();
            }
        });
    }

    @Override
    public void getHomeUnreadCount() {
        mHomeRepository.getHomeUnReadCount(new com.zmsoft.ccd.data.Callback<HomeCount>() {
            @Override
            public void onSuccess(HomeCount homeCount) {
                if (null == mView) {
                    return;
                }
                if (null != homeCount) {
                    mView.successGetHomeUnreadCount(homeCount);
                } else {
                    mView.failGetHomeUnreadCount(context.getString(R.string.server_no_data));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.failGetHomeUnreadCount(body.getMessage());
            }
        });
    }

    @Override
    public boolean isAppInstalled(String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

    @Override
    public String getLauncherActivityClassName(String packageName) {
        PackageInfo packageinfo;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageinfo = null;
        }
        if (packageinfo == null) {
            return null;
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveInfoList.iterator().next();
        if (resolveinfo == null) {
            return null;
        }
        return resolveinfo.activityInfo.name;
    }
}
