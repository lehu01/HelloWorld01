package com.zmsoft.ccd.module.carryout;

import com.zmsoft.ccd.data.source.carryout.TakeoutSettingRepository;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.bean.carryout.TakeoutMobile;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/18.
 */

public class CarryoutSettingPresenter implements CarryoutSettingContract.Presenter {


    private CarryoutSettingContract.View mCarryoutView;
    private final TakeoutSettingRepository mCarryoutSettingRepository;

    @Inject
    public CarryoutSettingPresenter(CarryoutSettingContract.View view, TakeoutSettingRepository carryoutSettingRepository) {
        mCarryoutView = view;
        mCarryoutSettingRepository = carryoutSettingRepository;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCarryoutView = null;
    }

    @Override
    public void loadCarryoutPhoneList(String entityId) {
        mCarryoutSettingRepository.getTakeoutMobileList(entityId)
                .retryWhen(new RetryWithDelay(3, 300))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<TakeoutMobile>>() {
                    @Override
                    public void call(List<TakeoutMobile> carryoutMobiles) {
                        if (mCarryoutView == null) {
                            return;
                        }
                        mCarryoutView.loadCarryoutPhoneListSuccess(carryoutMobiles);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mCarryoutView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mCarryoutView.loadCarryoutPhoneListFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void updateCarryoutPhoneSetting(String entityId, String mobile, boolean openFlag) {
        mCarryoutSettingRepository.saveTakeoutMobileConfig(entityId, mobile, openFlag)
                .retryWhen(new RetryWithDelay(3, 300))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (mCarryoutView == null) {
                            return;
                        }
                        mCarryoutView.updateCarryoutPhoneSettingSuccess(aBoolean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mCarryoutView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mCarryoutView.updateCarryoutPhoneSettingFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });
    }
}
