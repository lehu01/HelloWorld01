package com.zmsoft.ccd.module.menubalance;


import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.menubalance.MenuBalanceRepository;

import javax.inject.Inject;

/**
 * Created by jihuo on 2016/12/23.
 */

public class AddMenuBalancePresenter implements AddMenuBalanceContract.Presenter {

    private AddMenuBalanceContract.View mView;

    private final MenuBalanceRepository mMenuBalanceRepository;

    @Inject
    AddMenuBalancePresenter(AddMenuBalanceContract.View view, MenuBalanceRepository mMenuBalanceRepository) {
        this.mView = view;
        this.mMenuBalanceRepository = mMenuBalanceRepository;
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void addMenuBalance(String entityId, String menuId, final double num, String opUser) {
        mMenuBalanceRepository.addMenuBalance(entityId, menuId, num, opUser,
                new com.zmsoft.ccd.data.Callback<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        if (null == mView) {
                            return;
                        }
                        mView.addMenuBalanceSuccess();
                    }

                    @Override
                    public void onFailed(ErrorBody body) {
                        if (null == mView) {
                            return;
                        }
                        mView.addMenuBalanceFailure();
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
