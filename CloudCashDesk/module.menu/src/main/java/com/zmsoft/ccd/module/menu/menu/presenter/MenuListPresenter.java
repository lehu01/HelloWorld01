package com.zmsoft.ccd.module.menu.menu.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.dfire.mobile.util.JsonMapper;
import com.google.gson.reflect.TypeToken;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.source.CartRemoteSource;
import com.zmsoft.ccd.module.menu.cart.source.ICartSource;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;
import com.zmsoft.ccd.module.menu.menu.bean.MenuDB;
import com.zmsoft.ccd.module.menu.menu.bean.ResponseSeatStatus;
import com.zmsoft.ccd.module.menu.menu.source.MenuRepository;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description：菜单列表Presenter
 * <br/>
 * Created by kumu on 2017/4/15.
 */


public class MenuListPresenter implements MenuListContract.Presenter {

    private static final int TYPE_CART_INPUT = 1;
    private static final int TYPE_CART_ADD = 2;
    private static final int TYPE_CART_MINUS = 3;

    private MenuRepository mMenuRepository;
    private MenuListContract.View mView;
    private ICartSource mCartRepository;

    @Inject
    MenuListPresenter(MenuListContract.View view, MenuRepository menuRepository) {
        this.mView = view;
        this.mMenuRepository = menuRepository;
        this.mCartRepository = new CartRemoteSource();
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void loadMenuList(final String entity, final String seatCode, final String orderId, final boolean needCheckLocal) {
        RxUtils
                .fromCallable(new Callable<List<Menu>>() {
                    @Override
                    public List<Menu> call() throws Exception {
                        //work thread
                        Log.e("MenuListPresenter", "check local db " + Thread.currentThread().getName());
                        if (needCheckLocal) {
                            MenuDB menusDB = DataSupport.where("entity = ?", entity).findFirst(MenuDB.class);
                            if (menusDB != null && !TextUtils.isEmpty(menusDB.getJson())) {
                                Type listType = new TypeToken<List<Menu>>() {
                                }.getType();
                                return JsonMapper.fromJson(menusDB.getJson(), listType);
                            }
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<Menu>, Observable<List<Menu>>>() {
                    @Override
                    public Observable<List<Menu>> call(List<Menu> menus) {
                        //main thread
                        if (menus != null) {
                            Log.e("MenuListPresenter", "load local menus success " + Thread.currentThread().getName());
                            loadMenuListSuccess(menus);
                        }
                        return mMenuRepository.getMenuList(entity);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Menu>>() {
                    @Override
                    public void call(List<Menu> menus) {
                        if (menus != null) {
                            Log.e("MenuListPresenter", "load remote menus success " + Thread.currentThread().getName());
                            loadMenuListSuccess(menus);
                            //把网络上的菜单存储到本地
                            saveMenuList(entity, menus);
                            //加载购物车数据
                            queryCart(seatCode, orderId);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            loadMenuListFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });

    }

    @Override
    public void loadMenuListSuccess(List<Menu> menuList) {
        if (mView == null) {
            return;
        }
        mView.loadMenuListSuccess(menuList);
    }

    @Override
    public void loadMenuListFailed(String errorCode, String errorMsg) {
        if (mView == null) {
            return;
        }
        mView.loadMenuListFailed(errorCode, errorMsg);
    }

    /**
     * 暂不支持一个店多个菜单的问题
     *
     * @param entity
     * @param menuList
     */
    private void saveMenuList(final String entity, final List<Menu> menuList) {
        RxUtils.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                //删除和该店相关的本地菜单数据
                try {
                    DataSupport.deleteAll(MenuDB.class, "entity = ?", entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String json = JsonMapper.toJsonString(menuList);
                //保存该店最新的菜单到本地
                if (new MenuDB(entity, json).save()) {
                    Log.e("MenuListFragment", "menu list save success " + Thread.currentThread().getName());
                }
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void queryCart(String seatCode, String orderId) {
        mCartRepository.queryCart(seatCode, orderId, true, new Callback<DinningTableVo>() {
            @Override
            public void onSuccess(DinningTableVo data) {
                if (mView == null) {
                    return;
                }
                mView.queryCartSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.queryCartFailed(body.getErrorCode(), body.getMessage());
            }
        });
    }


    private void modifyCart(final int type, String seatCode, String orderId, String source, List<CartItem> cartItemList) {
        mCartRepository.modifyCart(seatCode, orderId, source, cartItemList, new Callback<DinningTableVo>() {
            @Override
            public void onSuccess(DinningTableVo data) {
                if (mView == null) {
                    return;
                }
                switch (type) {
                    case TYPE_CART_INPUT:
                        mView.modifyInputCartSuccess(data);
                        break;
                    case TYPE_CART_ADD:
                        mView.addMenuToCartSuccess(data);
                        break;
                    case TYPE_CART_MINUS:
                        mView.removeMenuFromCartSuccess(data);
                        break;
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                switch (type) {
                    case TYPE_CART_INPUT:
                        mView.modifyInputCartFailed(body.getErrorCode(), body.getMessage());
                        break;
                    case TYPE_CART_ADD:
                        mView.addMenuToCartFailed(body.getErrorCode(), body.getMessage());
                        break;
                    case TYPE_CART_MINUS:
                        mView.removeMenuFromCartFailed(body.getErrorCode(), body.getMessage());
                        break;
                }

            }
        });
    }


    public void modifyInputCart(String seatCode, String orderId, List<CartItem> cartItemList) {
        modifyCart(TYPE_CART_INPUT, seatCode, orderId, CartHelper.CartSource.MENU_LIST, cartItemList);
    }

    @Override
    public void addMenuToCart(String seatCode, String orderId, List<CartItem> cartItemList) {
        modifyCart(TYPE_CART_ADD, seatCode, orderId, CartHelper.CartSource.MENU_LIST, cartItemList);
    }

    @Override
    public void removeMenuFromCart(String seatCode, String orderId, List<CartItem> cartItemList) {
        modifyCart(TYPE_CART_MINUS, seatCode, orderId, CartHelper.CartSource.MENU_LIST, cartItemList);
    }

    @Override
    public void getSeatStatus(String entityId, String seatCode) {
        mMenuRepository.getSeatStatus(entityId, seatCode, new Callback<ResponseSeatStatus>() {
            @Override
            public void onSuccess(ResponseSeatStatus data) {
                if (mView == null) {
                    return;
                }
                mView.getSeatStatusSuccess(data == null ? ResponseSeatStatus.STATUS_UNUSED : data.getStatus());
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.getSeatStatusFailed(body.getErrorCode(), body.getMessage());
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
