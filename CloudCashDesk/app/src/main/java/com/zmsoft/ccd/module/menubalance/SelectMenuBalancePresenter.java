package com.zmsoft.ccd.module.menubalance;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.menubalance.MenuBalanceRepository;
import com.zmsoft.ccd.lib.bean.menubalance.MenuVO;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jihuo on 2016/12/23.
 */

public class SelectMenuBalancePresenter implements SelectMenuBalanceContract.Presenter {

    private final MenuBalanceRepository mMenuBalanceRepository;
    private SelectMenuBalanceContract.View mView;

    @Inject
    public SelectMenuBalancePresenter(SelectMenuBalanceContract.View view, MenuBalanceRepository menuBalanceRepository) {
        mView = view;
        mMenuBalanceRepository = menuBalanceRepository;
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void getAllMenu(String entityId) {
        mMenuBalanceRepository.getAllMenus(entityId, new Callback<List<MenuVO>>() {
            @Override
            public void onSuccess(List<MenuVO> data) {
                if (null == mView) {
                    return;
                }
                mView.getAllMenuSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.getAllMenuFailure(body.getMessage());
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
