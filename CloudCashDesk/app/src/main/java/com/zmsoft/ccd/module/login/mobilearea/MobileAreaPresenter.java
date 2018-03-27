package com.zmsoft.ccd.module.login.mobilearea;

import com.zmsoft.ccd.data.UserDataRepository;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.bean.login.mobilearea.MobileArea;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/22 16:46.
 */

public class MobileAreaPresenter implements MobileAreaContract.Presenter {

    private MobileAreaContract.View mView;
    private final UserDataRepository mUserDataRepository;


    //================================================================================
    // inject
    //================================================================================
    @Inject
    public MobileAreaPresenter(MobileAreaContract.View mView, UserDataRepository userDataRepository) {
        this.mView = mView;
        this.mUserDataRepository = userDataRepository;
    }

    @Inject
    void setMobileAreaPresenter() {
        mView.setPresenter(this);
    }

    //================================================================================
    // MobileAreaContract.Presenter
    //================================================================================
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void loadMobileArea() {
        mUserDataRepository.getMobileCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MobileArea>>() {
                    @Override
                    public void call(List<MobileArea> mobileAreas) {
                        if (null == mView) {
                            return;
                        }
                        mView.loadDataSuccess(mobileAreas);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (null == mView) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (null != e) {
                            mView.loadDataError(e.getMessage());
                        }
                    }
                });
    }
}
