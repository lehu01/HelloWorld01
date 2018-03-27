package com.zmsoft.ccd.module.menu.menu.presenter;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.menu.bean.MenuCategory;
import com.zmsoft.ccd.module.menu.menu.source.MenuRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description：自定义商品Presenter
 * <br/>
 * Created by kumu on 2017/4/17.
 */

public class RetailCustomFoodPresenter implements RetailCustomFoodContract.Presenter {

    private MenuRepository mMenuRepository;
    private RetailCustomFoodContract.View mView;

    @Inject
    RetailCustomFoodPresenter(RetailCustomFoodContract.View view, MenuRepository menuRepository) {
        this.mView = view;
        this.mMenuRepository = menuRepository;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void loadMenuCategories(String entityId, int[] types) {
        mMenuRepository.getMenuCategories(entityId, types, new Callback<List<MenuCategory>>() {
            @Override
            public void onSuccess(List<MenuCategory> data) {
                if (mView == null) {
                    return;
                }
                mView.loadMenuCategoriesSuccess((ArrayList<MenuCategory>) data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.loadMenuCategoriesFailed(body.getErrorCode(), body.getMessage());
            }
        });
    }

    @Override
    public void saveCustomMenuToCart(String entityId, String userId, String ownerCustomerId,
                                     String seatCode, int peopleCount, String itemListJson) {
        mMenuRepository.saveCustomMenuToCart(entityId, userId, ownerCustomerId, seatCode,
                peopleCount, itemListJson, new Callback<DinningTableVo>() {
                    @Override
                    public void onSuccess(DinningTableVo data) {
                        if (mView == null) {
                            return;
                        }
                        mView.saveCustomMenuToCartSuccess(data);
                    }

                    @Override
                    public void onFailed(ErrorBody body) {
                        if (mView == null) {
                            return;
                        }
                        mView.saveCustomMenuToCartFailed(body.getErrorCode(), body.getMessage());
                    }
                });
    }
}
