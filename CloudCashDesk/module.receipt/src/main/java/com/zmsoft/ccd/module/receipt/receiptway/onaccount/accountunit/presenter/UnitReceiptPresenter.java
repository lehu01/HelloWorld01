package com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.presenter;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.receipt.bean.GetSignBillSingerResponse;
import com.zmsoft.ccd.module.receipt.receipt.model.GetSignBillUnitRequest;
import com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRepository;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.items.UnitRecyclerItem;

import java.util.List;

import javax.inject.Inject;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class UnitReceiptPresenter implements UnitReceiptContract.Presenter {
    private UnitReceiptContract.View mView;
    private ReceiptRepository mRepository;

    @Inject
    public UnitReceiptPresenter(UnitReceiptContract.View view, ReceiptRepository repository) {
        mView = view;
        this.mRepository = repository;
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
    public void getSignUnit(String kindPayId, String keyword, int pageSize, int pageIndex) {
        GetSignBillUnitRequest getSignBillUnitRequest = new GetSignBillUnitRequest(kindPayId, UserHelper.getEntityId(), keyword, pageIndex, pageSize);
        String requestJson = JsonMapper.toJsonString(getSignBillUnitRequest);
        mRepository.getSignUnit(requestJson, new Callback<GetSignBillSingerResponse>() {
            @Override
            public void onSuccess(GetSignBillSingerResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.successGetSignInfo(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.failGetData(body);
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void checkUnitItem(final List<UnitRecyclerItem> unitRecyclerItemList, final UnitRecyclerItem checkedUnitItem) {
        for (int i = 0; i < unitRecyclerItemList.size(); i++) {
            UnitRecyclerItem unitRecyclerItem = unitRecyclerItemList.get(i);
            if (!unitRecyclerItem.getKindPayDetailOptionId().equals(checkedUnitItem.getKindPayDetailOptionId())) {
                unitRecyclerItem.setChecked(false);
            }
        }
        if (null != mView) {
            mView.notifyDataChange();
        }
    }
}
