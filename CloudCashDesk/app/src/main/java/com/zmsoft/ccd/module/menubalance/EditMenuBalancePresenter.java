package com.zmsoft.ccd.module.menubalance;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.menubalance.MenuBalanceRepository;

import javax.inject.Inject;

/**
 * Created by jihuo on 2016/12/23.
 */

public class EditMenuBalancePresenter implements EditMenuBalanceContract.Present {

    private EditMenuBalanceContract.View mView;
    private final MenuBalanceRepository menuBalanceRepository;

    @Inject
    EditMenuBalancePresenter(EditMenuBalanceContract.View view, MenuBalanceRepository menuBalanceRepository) {
        this.mView = view;
        this.menuBalanceRepository = menuBalanceRepository;
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void cancelMenuBalance(String entityId, String menuId, String opUserId) {
        menuBalanceRepository.cancelMenuBalance(entityId, menuId, opUserId, new Callback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                if (null == mView) {
                    return;
                }
                mView.cancelMenuBalanceSuccess();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.cancelMenuBalanceFailure();
            }
        });
    }

    @Override
    public void updateMenuBalance(String entityId, String menuId, double num, String opUser) {
        menuBalanceRepository.updateMenuBalance(entityId, menuId, num, opUser, new Callback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                if (null == mView) {
                    return;
                }
                mView.updateMenuBalanceSuccess();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.updateMenuBalanceFailure();
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
