package com.zmsoft.ccd.module.menubalance;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.menubalance.MenuBalanceRepository;
import com.zmsoft.ccd.lib.bean.menubalance.MenuBalanceVO;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by jihuo on 2016/12/22.
 */

public class MenuBalancePresenter implements MenuBalanceContract.Presenter {

    private MenuBalanceContract.View mView;

    private final MenuBalanceRepository mMenuBalanceRepository;

    private ICommonSource mICommonSource;

    @Inject
    MenuBalancePresenter(MenuBalanceContract.View view, MenuBalanceRepository menuBalanceRepository) {
        this.mView = view;
        mMenuBalanceRepository = menuBalanceRepository;
        mICommonSource = new CommonRemoteSource();
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void getMenuBalanceList(String entityId, Integer pageSize, Integer pageIndex) {
        mMenuBalanceRepository.getMenuBalanceList(entityId, pageSize, pageIndex, new Callback<List<MenuBalanceVO>>() {
            @Override
            public void onSuccess(List<MenuBalanceVO> data) {
                if (null == mView) {
                    return;
                }
                mView.getMenuBalanceListSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.getMenuBalanceListFailure(body.getMessage());
            }
        });
    }

    @Override
    public void clearAllMenuBalance(String entityId, List<String> menuIdList, String opUserId) {
        mMenuBalanceRepository.clearAllMenuBalance(entityId, menuIdList, opUserId, new Callback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                if (null == mView) {
                    return;
                }
                mView.clearAllMenuBalanceSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.clearAllMenuBalanceFailure(body.getMessage());
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
