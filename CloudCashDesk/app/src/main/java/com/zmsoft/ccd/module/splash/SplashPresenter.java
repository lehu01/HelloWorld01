package com.zmsoft.ccd.module.splash;

import com.zmsoft.ccd.data.source.splash.SplashSourceRepository;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/19 16:18
 */
public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View mView;

    private final SplashSourceRepository mRepository;

    @Inject
    SplashPresenter(SplashContract.View loginView, SplashSourceRepository splashSourceRepository) {
        mView = loginView;
        mRepository = splashSourceRepository;
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
    public void getConfigSwitchVal(String entityId, String systemTypeId, String code) {
        mRepository.getConfigSwitchVal(entityId, systemTypeId, code)
                .retryWhen(new RetryWithDelay(3, 500))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String data) {
                        if (mView == null) {
                            return;
                        }
                        mView.getTurnOnCloudSuccess(data);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mView == null) {
                            return;
                        }
                        mView.getTurnOnCloudSuccessFailure();
                    }
                });
    }
}
