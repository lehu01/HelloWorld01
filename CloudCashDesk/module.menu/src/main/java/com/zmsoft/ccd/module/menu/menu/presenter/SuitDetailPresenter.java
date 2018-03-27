package com.zmsoft.ccd.module.menu.menu.presenter;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.cart.source.CartRemoteSource;
import com.zmsoft.ccd.module.menu.cart.source.ICartSource;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenuHitRule;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenu;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitMenuDetailVO;
import com.zmsoft.ccd.module.menu.menu.source.MenuRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Description：套餐详情Presenter
 * <br/>
 * Created by kumu on 2017/4/21.
 */

public class SuitDetailPresenter implements SuitDetailContract.Presenter {

    private MenuRepository mMenuRepository;
    private SuitDetailContract.View mView;
    private ICartSource mCartRepository;

    @Inject
    SuitDetailPresenter(SuitDetailContract.View view, MenuRepository menuRepository) {
        this.mView = view;
        this.mMenuRepository = menuRepository;
        this.mCartRepository = new CartRemoteSource();
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    /**
     * 先加载计价规则集合，然后加载套餐详情
     */
    @Override
    public void loadSuitDetail(String menuId) {
        loadSuitDetail(mMenuRepository.getRulesBySuitMenuId(menuId), mMenuRepository.queryFoodDetail(menuId));
    }

    public void loadSuitDetail(String menuId, BaseMenuVo baseMenuVo) {
        loadSuitDetail(mMenuRepository.getRulesBySuitMenuId(menuId), Observable.just(baseMenuVo));
    }

    private void loadSuitDetail(Observable<List<SuitMenuHitRule>> hitRuleObservable, Observable<BaseMenuVo> menuVoObservable) {
        Observable.zip(hitRuleObservable, menuVoObservable,
                new Func2<List<SuitMenuHitRule>, BaseMenuVo, SuitMenuDetailVO>() {
                    @Override
                    public SuitMenuDetailVO call(List<SuitMenuHitRule> rules, BaseMenuVo menu) {
                        SuitMenuDetailVO detailVO = new SuitMenuDetailVO(menu, rules);
                        detailVO.setSuitMenuSellOut(menu.getIsSelf() == CartHelper.FoodOffShelves.OFF);
                        SuitMenu suitMenu = JsonMapper.fromJson(menu.getMenuJson(), SuitMenu.class);
                        detailVO.setSuitMenu(suitMenu);
                        return detailVO;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SuitMenuDetailVO>() {
                    @Override
                    public void call(SuitMenuDetailVO o) {
                        if (mView == null) {
                            return;
                        }
                        mView.loadSuitDetailSuccess(o);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        if (mView == null) {
                            return;
                        }
                        if (throwable instanceof ServerException) {
                            ServerException e = (ServerException) throwable;
                            mView.loadSuitDetailFailed(e.getErrorCode(), e.getMessage());
                        } else {
                            mView.loadSuitDetailFailed("error inner", "error inner");
                        }
                    }
                });
    }


    @Override
    public void addSuitToCart(String seatCode, String orderId, String source, List<CartItem> cartItemList) {
        mCartRepository.modifyCart(seatCode, orderId, source, cartItemList, new Callback<DinningTableVo>() {
            @Override
            public void onSuccess(DinningTableVo data) {
                if (mView == null) {
                    return;
                }
                mView.addSuitToCartSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.addSuitToCartFailed(body.getErrorCode(), body.getMessage());
            }
        });
    }

    @Override
    public void loadSuitRule(String menuId) {
        mMenuRepository.getRulesBySuitMenuId(menuId, new Callback<List<SuitMenuHitRule>>() {
            @Override
            public void onSuccess(List<SuitMenuHitRule> data) {
                if (mView == null) {
                    return;
                }
                mView.loadSuitRuleSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.loadSuitRuleFailed(body.getErrorCode(), body.getMessage());
            }
        });
    }


}
