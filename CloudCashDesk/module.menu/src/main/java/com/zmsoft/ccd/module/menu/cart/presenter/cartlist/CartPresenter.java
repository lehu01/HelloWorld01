package com.zmsoft.ccd.module.menu.cart.presenter.cartlist;

import android.text.TextUtils;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.network.ErrorBizHttpCode;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.menu.business.MenuUtils;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartComboFoodRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.model.CartClearVo;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.CustomerVo;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.Item;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderRequest;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderVo;
import com.zmsoft.ccd.module.menu.cart.source.CartRepository;
import com.zmsoft.ccd.module.menu.events.model.ModifyCartParam;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.MenuKind;
import com.zmsoft.ccd.module.menu.menu.bean.vo.CartItemVO;
import com.zmsoft.ccd.module.menu.menu.source.IMenuSource;
import com.zmsoft.ccd.module.menu.menu.source.MenuRemoteSource;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/4/17.
 */

public class CartPresenter implements CartContract.Presenter {
    private CartContract.View mView;
    private CartRepository mRepository;
    private IMenuSource mMenuRepository;

    @Inject
    public CartPresenter(CartContract.View view, CartRepository repository) {
        mView = view;
        mRepository = repository;
        this.mMenuRepository = new MenuRemoteSource();
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void queryCart(String seatCode, String orderId, boolean needJoinCartOnError) {
        mRepository.queryCart(seatCode, orderId, needJoinCartOnError, new Callback<DinningTableVo>() {
            @Override
            public void onSuccess(DinningTableVo data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.showContentView();
                mView.showCart(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.queryCartError(body);
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void clearCart(final String seatCode, final String orderId) {
        mView.showLoading(context.getString(R.string.module_menu_cart_dialog_clearing), false);
        mRepository.clearCart(seatCode, orderId, new Callback<CartClearVo>() {
            @Override
            public void onSuccess(CartClearVo data) {
                if (null == mView) {
                    return;
                }
                //如果清理成功，刷新购物车
                queryCart(seatCode, orderId, true);
                //购物车修改，通知菜单列表
                RouterBaseEvent.CommonEvent modifyCartEvent = RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_CART;
                modifyCartEvent.setObject(null);
                EventBusHelper.post(modifyCartEvent);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void modifyCart(String seatCode, String orderId, ItemVo itemVo) {
//        mView.showLoading(GlobalVars.context.getString(R.string.dialog_waiting), false);
        List<CartItem> cartItemList = getCartItems(itemVo);
        String source = CartHelper.CartSource.CART_LIST;
        mRepository.modifyCart(seatCode, orderId, source, cartItemList, new Callback<DinningTableVo>() {
            @Override
            public void onSuccess(DinningTableVo data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.showCart(data);
                //购物车修改，通知菜单列表
                RouterBaseEvent.CommonEvent modifyCartEvent = RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_CART;
                ModifyCartParam modifyCartParam = new ModifyCartParam(data, null);
                modifyCartEvent.setObject(modifyCartParam);
                EventBusHelper.post(modifyCartEvent);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void submitOrder(SubmitOrderRequest request, List<CartRecyclerItem> cartRecyclerItemList) {
        if (!isCartValid(cartRecyclerItemList)) {
            return;
        }
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        mRepository.submitOrder(request, new Callback<SubmitOrderVo>() {
            @Override
            public void onSuccess(SubmitOrderVo submitOrderVo) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.confimOrder(submitOrderVo);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                if (null != body && !TextUtils.isEmpty(body.getErrorCode())
                        && body.getErrorCode().equals(HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR)) {
                    mView.loadDataError(String.format(context.getResources()
                                    .getString(R.string.alert_permission_deny)
                            , context.getString(R.string.module_menu_cart_open_order)));
                } else if (null != body && !TextUtils.isEmpty(body.getErrorCode())
                        && body.getErrorCode().equals(ErrorBizHttpCode.ERR_FOOD_SOLDOUT)) {
                    mView.alertSoldOut(body.getMessage());
                } else if (null != body && !TextUtils.isEmpty(body.getMessage())) {
                    mView.loadDataError(body.getMessage());
                }
            }
        });
    }

    @Override
    public void saveCustomMenuToCart(String seatCode, ItemVo itemVo, int peopleCount) {
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        String memo = null;
        double num = 0;
        if (null != itemVo) {
            memo = itemVo.getMemo();
            num = itemVo.getNum();
        }
        String itemListJson = getCustomMenuJson(itemVo);

        final double finalNum = num;
        mMenuRepository.saveCustomMenuToCart(UserHelper.getEntityId(),
                UserHelper.getMemberId(), UserHelper.getMemberId(), seatCode,
                peopleCount, itemListJson, new Callback<DinningTableVo>() {
                    @Override
                    public void onSuccess(DinningTableVo data) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        mView.showCart(data);
                        //购物车修改自定义菜，如果菜数量变为0，即删除了该菜，通知菜单列表
                        if (finalNum <= 0) {
                            RouterBaseEvent.CommonEvent modifyCartEvent = RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_CART;
                            ModifyCartParam modifyCartParam = new ModifyCartParam(data, null);
                            modifyCartEvent.setObject(modifyCartParam);
                            EventBusHelper.post(modifyCartEvent);
                        }
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

    @Override
    public void hangUpOrder(String seatCode) {
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        mRepository.hangUpOrder(seatCode, new Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                if (null != result && result) {
                    mView.successHangUpOrder();
                } else {
                    onFailed(new ErrorBody(context.getString(R.string.module_menu_cart_hangup_order_fail)));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                if (null != body && !TextUtils.isEmpty(body.getMessage())) {
                    mView.loadDataError(body.getMessage());
                }
            }
        });
    }

    private boolean isCartValid(List<CartRecyclerItem> cartRecyclerItemList) {
        if (null != cartRecyclerItemList && !cartRecyclerItemList.isEmpty()) {
            if (cartRecyclerItemList.size() == 1 && cartRecyclerItemList.get(0).getItemType() ==
                    CartRecyclerItem.ItemType.TYPE_ORDER_INFO) {
                mView.loadDataError(context.getString(R.string.module_menu_cart_empty));
                return false;
            }
            for (int i = 0; i < cartRecyclerItemList.size(); i++) {
                CartRecyclerItem cartRecyclerItem = cartRecyclerItemList.get(i);
                if (null != cartRecyclerItem) {
                    if (cartRecyclerItem.getItemType() == CartRecyclerItem.ItemType.TYPE_COMBO_FOOD) {
                        CartComboFoodRecyclerItem comboItem = cartRecyclerItem.getCartComboFoodRecyclerItem();
                        if (null != comboItem) {
                            if (comboItem.getFoodType() == CartHelper.CartFoodType.TYPE_MUST_SELECT
                                    && !comboItem.isHasSubMenu()) {
                                mView.loadDataError(context.getString(R.string.module_menu_mustselect_child));
                                return false;
                            }
                        }
                    }
                }
            }
        } else {
            mView.loadDataError(context.getString(R.string.module_menu_cart_empty));
            return false;
        }
        return true;
    }

    private List<CartItem> getCartItems(ItemVo itemVo) {
        List<CartItem> cartItemList = new ArrayList<>();
        if (null == itemVo) {
            return cartItemList;
        }
        CartItem mCartItem = new CartItem();
        CustomerVo customerVo = itemVo.getCustomerVo();
        if (!TextUtils.isEmpty(itemVo.getCustomerRegisterId())) {
            mCartItem.setUid(itemVo.getCustomerRegisterId());
        } else {
            if (null != customerVo && !TextUtils.isEmpty(customerVo.getId())) {
                mCartItem.setUid(customerVo.getId());
            } else {
                mCartItem.setUid(UserHelper.getMemberId());
            }
        }
        if (!TextUtils.isEmpty(itemVo.getIndex())) {
            mCartItem.setIndex(itemVo.getIndex());
        } else {
            mCartItem.setIndex(UserHelper.getEntityId() + StringUtils.getRandomString(24));
        }
        mCartItem.setMenuId(itemVo.getMenuId());
        mCartItem.setMenuName(itemVo.getName());
        mCartItem.setMakeId(itemVo.getMakeId());
        mCartItem.setSpecId(itemVo.getSpecDetailId());
        mCartItem.setNum(itemVo.getNum());
        if (!itemVo.isTwoAccount()) {
            itemVo.setAccountNum(itemVo.getNum());
        }
        mCartItem.setAccountNum(itemVo.getAccountNum());
        mCartItem.setDoubleUnitStatus(mCartItem.getAccountNum() == itemVo.getOriginAccountNum() ?
                CartHelper.DoubleUnitStatus.FALSE : CartHelper.DoubleUnitStatus.TRUE);
        mCartItem.setKindMenuId(itemVo.getKindMenuId());
        String source = CartHelper.CartSource.CART_LIST;
        mCartItem.setAddPrice(itemVo.getAddPrice());
        mCartItem.setAddPriceMode(itemVo.getAddPriceMode());
        mCartItem.setKindType(itemVo.getKind());
        mCartItem.setAddType(0); //云收银默认都是0
        mCartItem.setCompulsory(itemVo.getIsCompulsory());
        mCartItem.setIsWait(itemVo.getIsWait());
        mCartItem.setPresent(itemVo.getPresent());
        mCartItem.setSource(source);
        //普通菜子菜列表
        mCartItem.setChildCartVos(getChildFoodList(itemVo));
        mCartItem.setMemo(itemVo.getMemo());
        mCartItem.setLabels(itemVo.getLabels());
        cartItemList.add(mCartItem);
        return cartItemList;
    }

    /**
     * 获取 普通菜加料菜 / 套餐子菜 列表数据
     */
    private List<CartItem> getChildFoodList(ItemVo itemVo) {
        List<Item> childItems = itemVo.getChildItems();
        if (null == childItems || childItems.isEmpty()) {
            return null;
        }
        List<CartItem> childCartItemList = new ArrayList<>();
        for (int i = 0; i < childItems.size(); i++) {
            Item item = childItems.get(i);
            if (null != item && item.getNum() > 0) {
                CartItem cartItem = new CartItem();
                cartItem.setMenuName(item.getName());
                cartItem.setMenuId(item.getMenuId());
                cartItem.setKindMenuId(item.getKindMenuId());
                if (MenuUtils.isTwoAccount(item.getUnit(), item.getAccountUnit())) {
                    cartItem.setNum(item.getAccountNum());
                } else {
                    cartItem.setNum(item.getNum());
                }
                cartItem.setAccountNum(item.getNum());
                cartItem.setKindType(item.getKind());
                cartItem.setMakeId(item.getMakeId());
                cartItem.setSpecId(item.getSpecDetailId());
                cartItem.setMemo(item.getMemo());
                cartItem.setSuitMenuDetailId(item.getSuitMenuDetailId());
                if (!TextUtils.isEmpty(item.getIndex())) {
                    cartItem.setIndex(item.getIndex());
                } else {
                    cartItem.setIndex(UserHelper.getEntityId() + StringUtils.getRandomString(24));
                }
                //用户选择的备注
                if (item.getLabels() == null || item.getLabels().isEmpty()) {
                    cartItem.setLabels(null);
                } else {
                    cartItem.setLabels(item.getLabels());
                }
                childCartItemList.add(cartItem);
            }
        }
        return childCartItemList;
    }

    /**
     * 获取修改自定义菜时上传的json串
     *
     * @param itemVo
     * @return
     */
    private String getCustomMenuJson(ItemVo itemVo) {
        if (null == itemVo) {
            return null;
        }
        List<Item> list = new ArrayList<>();
        Item item = new Item();
        //这个不用传递
        item.setLabels(null);
        //自定义菜
        item.setKind(MenuKind.CUSTOM);
        //商品名称
        item.setName(itemVo.getName());
        //分类id
        item.setKindMenuId(itemVo.getKindMenuId());
        //分类name
        item.setKindMenuName(itemVo.getKindMenuName());
        //点菜数量
        item.setNum(itemVo.getNum());
        //单价
        item.setPrice(itemVo.getPrice());
        //点菜单位
        item.setUnit(itemVo.getUnit());
        //结账单位
        item.setAccountUnit(itemVo.getAccountUnit());
        //设置index
        if (!TextUtils.isEmpty(itemVo.getIndex())) {
            item.setIndex(itemVo.getIndex());
        } else {
            item.setIndex(CartItemVO.createIndex());
        }
        //数据埋点
        item.setSource(CartHelper.CartSource.CUSTOM_FOOD);
        if (!item.isTwoAccount()) {
            //结账数量
            item.setAccountNum(item.getNum());
            //总价
            item.setFee(itemVo.getNum() * itemVo.getPrice());
        } else {
            item.setAccountNum(itemVo.getAccountNum());
            //总价
            item.setFee(itemVo.getAccountNum() * itemVo.getPrice());
        }
        if (TextUtils.isEmpty(itemVo.getAccountUnit())) {
            //总价
            item.setFee(itemVo.getNum() * itemVo.getPrice());
        }
        item.setDoubleUnitStatus(item.getAccountNum().equals(itemVo.getOriginAccountNum()) ?
                CartHelper.DoubleUnitStatus.FALSE : CartHelper.DoubleUnitStatus.TRUE);
        //可否打折
        item.setIsRatio(itemVo.getIsRatio());
        //备注
        item.setMemo(itemVo.getMemo());
        //传送方案
        item.setTransferPlanIds(itemVo.getTransferPlanIds());
        list.add(item);
        return JsonMapper.toJsonString(list);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
