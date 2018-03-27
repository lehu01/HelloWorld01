package com.zmsoft.ccd.module.receipt.vipcard.input.fragment;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.vipcard.result.VipCardListResult;
import com.zmsoft.ccd.module.receipt.vipcard.source.VipCardSourceRepository;

import javax.inject.Inject;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/26 17:27
 */
public class InputVipCardPresenter implements InputVipCardContract.Presenter {

    private InputVipCardContract.View mView;
    private VipCardSourceRepository mVipCardSourceRepository;

    @Inject
    public InputVipCardPresenter(InputVipCardContract.View view, VipCardSourceRepository vipCardSourceRepository) {
        this.mView = view;
        this.mVipCardSourceRepository = vipCardSourceRepository;
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
    public void getVipCardList(String entityId, String keyword) {
        mView.showLoading(true);
        mVipCardSourceRepository.getVipCardList(entityId, keyword, new Callback<VipCardListResult>() {
            @Override
            public void onSuccess(VipCardListResult data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.loadVipCardListSuccess(data.getCardList());
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }
}
