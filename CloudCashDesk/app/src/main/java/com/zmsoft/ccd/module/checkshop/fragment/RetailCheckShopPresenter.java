package com.zmsoft.ccd.module.checkshop.fragment;

import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.checkshop.CheckShopRepository;
import com.zmsoft.ccd.data.source.user.UserDataSource;
import com.zmsoft.ccd.data.source.user.UserRemoteSource;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.bean.shop.Shop;
import com.zmsoft.ccd.lib.bean.shop.request.BindShopListRequest;
import com.zmsoft.ccd.lib.bean.shop.request.BindShopRequest;
import com.zmsoft.ccd.lib.bean.user.User;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/17 10:54
 */
public class RetailCheckShopPresenter implements RetailCheckShopContract.Presenter {

    private RetailCheckShopContract.View mCheckShopView;
    private final CheckShopRepository mCheckShopRepository;
    private final UserDataSource mUserRemoteSource;

    @Inject
    public RetailCheckShopPresenter(RetailCheckShopContract.View view, CheckShopRepository checkShopRepository) {
        mCheckShopView = view;
        mCheckShopRepository = checkShopRepository;
        mUserRemoteSource = new UserRemoteSource(NetworkService.getDefault());
    }

    @Inject
    void setCheckShopPresenter() {
        mCheckShopView.setPresenter(this);
    }

    @Override
    public void setWorkingStatus(String entityId, int type, String userId, int status) {
        mUserRemoteSource.setWorking(entityId, type, userId, status, new Callback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                if (mCheckShopView == null) {
                    return;
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mCheckShopView == null) {
                    return;
                }
            }
        });
    }

    @Override
    public void getConfigSwitchVal(String entityId, String systemTypeId, String code) {
        mCheckShopView.showLoading(true);
        mCheckShopRepository.getConfigSwitchVal(entityId, systemTypeId, code)
                .retryWhen(new RetryWithDelay(3, 500))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String data) {
                        if (mCheckShopView == null) {
                            return;
                        }
                        mCheckShopView.hideLoading();
                        mCheckShopView.workModelSuccess(data);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mCheckShopView == null) {
                            return;
                        }
                        mCheckShopView.hideLoading();
                        mCheckShopView.workModelSuccess(null);
                    }
                });
    }


    /**
     * use {@link RetailCheckShopPresenter#getBindShopList(BindShopListRequest)} instead
     * @param memberId
     */
    @Override
    @Deprecated
    public void getBindShopList(String memberId) {
        mCheckShopRepository.getBindShopList(memberId, new Callback<List<Shop>>() {
            @Override
            public void onSuccess(List<Shop> data) {
                if (mCheckShopView == null) {
                    return;
                }
                if (data != null && data.size() > 0) {
                    mCheckShopView.refreshBindShopList(data);
                } else {
                    mCheckShopView.refreshBindShopList(data);
                    mCheckShopView.loadDataError(GlobalVars.context.getString(R.string.no_bind_shop));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mCheckShopView == null) {
                    return;
                }
                mCheckShopView.refreshBindShopList(null);
                mCheckShopView.showLoadErrorView(body.getMessage());
            }
        });
    }

    @Override
    public void bindShop(String memberId, String userId, String entityId, String originalUserId) {
        mCheckShopRepository.bindShop(memberId, userId, entityId, originalUserId, new Callback<User>() {
            @Override
            public void onSuccess(User data) {
                if (mCheckShopView == null) {
                    return;
                }
                if (data != null) {
                    mCheckShopView.bindShop(data);
                } else {
                    mCheckShopView.loadDataError(GlobalVars.context.getString(R.string.bind_shop_failure));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mCheckShopView == null) {
                    return;
                }
                mCheckShopView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCheckShopView = null;
    }
}
