package com.zmsoft.ccd.module.menu.cart.presenter.cartdetail;

import android.text.TextUtils;

import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexItem;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerChoiceItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerFeedItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerRemarkItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerWeightItem;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.CustomerVo;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.MemoLabel;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.KindAndTasteVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.MakeDataDto;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.Menu;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.NormalMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.Taste;
import com.zmsoft.ccd.module.menu.cart.source.CartRepository;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.Recipe;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitChildVO;
import com.zmsoft.ccd.module.menu.menu.source.IMenuSource;
import com.zmsoft.ccd.module.menu.menu.source.MenuRemoteSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 购物车菜品详情
 *
 * @author DangGui
 * @create 2017/4/18.
 */

public class CartDetailPresenter implements CartDetailContract.Presenter {
    private CartDetailContract.View mView;
    private CartRepository mRepository;
    private IMenuSource mMenuRepository;
    private ICommonSource mBaseSource;

    @Inject
    public CartDetailPresenter(CartDetailContract.View view, CartRepository repository) {
        mView = view;
        mRepository = repository;
        mMenuRepository = new MenuRemoteSource();
        mBaseSource = new CommonRemoteSource();
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
    public void queryCartFoodDetail(String menuId) {
        mMenuRepository.queryFoodDetail(menuId, new Callback<BaseMenuVo>() {
            @Override
            public void onSuccess(BaseMenuVo data) {
                if (null == mView) {
                    return;
                }
                mView.showContentView();
                if (null != data) {
                    if (data.getIsSetMenu() == CartHelper.BaseMenuFoodType.NORMAL) {
                        mView.showCartFoodDetail(data);
                    } else {
                        //普通菜详情无法解析套餐菜
                        mView.showErrorView(GlobalVars.context.getString(R.string.json_parse_exception));
                    }
                } else {
                    mView.showErrorView(GlobalVars.context.getString(R.string.list_loading_more_failed));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.showErrorView(body.getMessage());
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void modifyCart(String seatCode, String orderId, String menuId, List<CartDetailRecyclerItem> cartRecyclerItems
            , ItemVo itemVo, NormalMenuVo normalMenuVo, List<KindAndTasteVo> mKindTasteList, double num, int detailType) {
        if (null == normalMenuVo || null == cartRecyclerItems || cartRecyclerItems.isEmpty()) {
            return;
        }
        //如果有规格做法的菜，未选择了规格/做法，给予提示
        if (isSpecOrMakeEmpty(cartRecyclerItems)) {
            return;
        }
//        mView.showLoading(GlobalVars.context.getString(R.string.dialog_waiting), false);
        List<CartItem> cartItemList = getCartItems(menuId, cartRecyclerItems, itemVo, normalMenuVo
                , mKindTasteList, num, detailType);
        String source = null;
        switch (detailType) {
            case CartHelper.FoodDetailType.FOOD_DETAIL:
                source = CartHelper.CartSource.CART_DETAIL;
                break;
            case CartHelper.FoodDetailType.FOOD_MODIFY:
                source = CartHelper.CartSource.CART_MODIFY;
                break;
            case CartHelper.FoodDetailType.MUST_SELECT_MODIFY:
                source = CartHelper.CartSource.CART_MUST_SELECT_MODIFY;
                break;
            case CartHelper.FoodDetailType.COMBO_CHILD_DETAIL:
            case CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL:
                source = CartHelper.CartSource.CART_COMBO_CHILD_DETAIL;
                break;
            default:
                break;
        }
        mRepository.modifyCart(seatCode, orderId, source, cartItemList, new Callback<DinningTableVo>() {
            @Override
            public void onSuccess(DinningTableVo data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.showCart(data);
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
    public void checkPermission(int systemType, String actionCode, final List<CartDetailRecyclerItem> cartRecyclerItems) {
//        mView.showLoading(GlobalVars.context.getString(R.string.dialog_waiting), false);
        mBaseSource.checkPermission(systemType, actionCode, new Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                if (null == mView) {
                    return;
                }
                checkPresentFoodPermission(cartRecyclerItems, data);
                mView.hideLoading();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.loadDataError(body.getMessage());
                checkPresentFoodPermission(cartRecyclerItems, false);
                mView.hideLoading();
            }
        });
    }

    /**
     * 判断有规格做法的菜，是否选择了规格/做法
     *
     * @param mCartRecyclerItems
     * @return
     */
    private boolean isSpecOrMakeEmpty(List<CartDetailRecyclerItem> mCartRecyclerItems) {
        if (null != mCartRecyclerItems && !mCartRecyclerItems.isEmpty()) {
            for (int i = 0; i < mCartRecyclerItems.size(); i++) {
                CartDetailRecyclerItem cartDetailRecyclerItem = mCartRecyclerItems.get(i);
                if (null != cartDetailRecyclerItem) {
                    CartDetailRecyclerChoiceItem choiceItem = cartDetailRecyclerItem.getCartDetailRecyclerChoiceItem();
                    if (null != choiceItem && !TextUtils.isEmpty(choiceItem.getKey())) {
                        String key = choiceItem.getKey();
                        boolean isValid = (!TextUtils.isEmpty(key) && (key.equals(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_SPEC)
                                || key.equals(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_MAKE)));
                        if (isValid) {
                            List<CustomFlexItem> customFlexItemList = choiceItem.getCheckedFlexItemList();
                            if (null == customFlexItemList || customFlexItemList.isEmpty()) {
                                if (key.equals(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_SPEC)) {
                                    mView.toastCartMsg(GlobalVars.context.getString(R.string.module_menu_cart_spec_empty));
                                    return true;
                                } else {
                                    mView.toastCartMsg(GlobalVars.context.getString(R.string.module_menu_cart_make_empty));
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private List<CartItem> getCartItems(String menuId, List<CartDetailRecyclerItem> cartRecyclerItems
            , ItemVo itemVo, NormalMenuVo normalMenuVo, List<KindAndTasteVo> mKindTasteList, double num, int detailType) {
        List<CartItem> cartItemList = new ArrayList<>();
        CartItem mCartItem = new CartItem();
        if (null != normalMenuVo.getMenu()) {
            Menu menu = normalMenuVo.getMenu();
            if (null != itemVo) {
                if (!TextUtils.isEmpty(itemVo.getIndex())) {
                    mCartItem.setIndex(itemVo.getIndex());
                }
                if (!TextUtils.isEmpty(itemVo.getCustomerRegisterId())) {
                    mCartItem.setUid(itemVo.getCustomerRegisterId());
                } else {
                    CustomerVo customerVo = itemVo.getCustomerVo();
                    if (null != customerVo && !TextUtils.isEmpty(customerVo.getId())) {
                        mCartItem.setUid(customerVo.getId());
                    } else {
                        mCartItem.setUid(UserHelper.getMemberId());
                    }
                }
            } else {
                mCartItem.setUid(UserHelper.getMemberId());
            }
            if (TextUtils.isEmpty(mCartItem.getIndex())) {
                mCartItem.setIndex(UserHelper.getEntityId() + StringUtils.getRandomString(24));
            }
            mCartItem.setMenuId(menuId);
            mCartItem.setMenuName(menu.getName());
            mCartItem.setMakeId(getChoiceCheckedId(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_MAKE, cartRecyclerItems));
            mCartItem.setSpecId(getChoiceCheckedId(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_SPEC, cartRecyclerItems));
            mCartItem.setNum(num);
            mCartItem.setKindMenuId(menu.getKindMenuId());
            mCartItem.setAccountNum(getAccountNum(cartRecyclerItems));
            mCartItem.setDoubleUnitStatus(mCartItem.getAccountNum() == getOriginAccountNum(cartRecyclerItems) ?
                    CartHelper.DoubleUnitStatus.FALSE : CartHelper.DoubleUnitStatus.TRUE);
            mCartItem.setAccountUnit(menu.getAccount());
            int kindType = 0;
            String source = null;
            switch (detailType) {
                case CartHelper.FoodDetailType.FOOD_DETAIL:
                    kindType = CartHelper.CartFoodKind.KIND_NORMAL_FOOD;
                    source = CartHelper.CartSource.CART_DETAIL;
                    break;
                case CartHelper.FoodDetailType.FOOD_MODIFY:
                    kindType = CartHelper.CartFoodKind.KIND_NORMAL_FOOD;
                    source = CartHelper.CartSource.CART_MODIFY;
                    break;
                case CartHelper.FoodDetailType.MUST_SELECT_MODIFY:
                    kindType = CartHelper.CartFoodKind.KIND_NORMAL_FOOD;
                    source = CartHelper.CartSource.CART_MUST_SELECT_MODIFY;
                    break;
                case CartHelper.FoodDetailType.COMBO_CHILD_DETAIL:
                case CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL:
                    mCartItem.setAddPriceMode(menu.getAddPriceMode());
                    mCartItem.setAddPrice(menu.getAddPrice());
                    kindType = CartHelper.CartFoodKind.KIND_FEED_FOOD;
                    source = CartHelper.CartSource.CART_COMBO_CHILD_DETAIL;
                    break;
                default:
                    break;
            }
            mCartItem.setKindType(kindType);
            mCartItem.setAddType(0); //云收银默认都是0
            if (detailType == CartHelper.FoodDetailType.MUST_SELECT_MODIFY) {
                mCartItem.setCompulsory(true);
            }
            short standby = CartHelper.StandBy.NOT;
            if ((getFoodIfPresent(cartRecyclerItems, 1))) {
                standby = CartHelper.StandBy.YES;
            }
            mCartItem.setIsWait(standby);
            short present = CartHelper.PresentFood.NOT;
            if (getFoodIfPresent(cartRecyclerItems, 2)) {
                present = CartHelper.PresentFood.YES;
            }
            mCartItem.setPresent(present);
            mCartItem.setSource(source);
            //普通菜子菜列表
            mCartItem.setChildCartVos(getChildFoodList(cartRecyclerItems));
            //口味
            mCartItem.setLabels(getLabels(cartRecyclerItems, mKindTasteList));
            mCartItem.setMemo(getMemo(cartRecyclerItems));
        }
        cartItemList.add(mCartItem);
        return cartItemList;
    }

    /**
     * 获取供选择项中被选中的item
     *
     * @param choiceKey
     * @param cartRecyclerItems
     * @return
     */
    private String getChoiceCheckedId(String choiceKey, List<CartDetailRecyclerItem> cartRecyclerItems) {
        String id = null;
        for (int i = 0; i < cartRecyclerItems.size(); i++) {
            CartDetailRecyclerItem cartDetailRecyclerItem = cartRecyclerItems.get(i);
            if (cartDetailRecyclerItem.getItemType() == CartDetailRecyclerItem.ItemType.TYPE_CHOICE) {
                CartDetailRecyclerChoiceItem choiceItem = cartDetailRecyclerItem.getCartDetailRecyclerChoiceItem();
                if (null != choiceItem) {
                    if (!TextUtils.isEmpty(choiceItem.getKey()) && choiceItem.getKey().equals(choiceKey)) {
                        List<CustomFlexItem> checkedFlexItemList = choiceItem.getCheckedFlexItemList();
                        if (null != checkedFlexItemList && !checkedFlexItemList.isEmpty()) {
                            id = checkedFlexItemList.get(0).getId();
                        }
                    }
                }
            }
        }
        return id;
    }

    /**
     * 获取供选择项中被选中的item
     *
     * @param choiceKey
     * @param cartRecyclerItems
     * @return
     */
    private void getTasteChoiceCheckedId(String choiceKey, List<CartDetailRecyclerItem> cartRecyclerItems
            , List<MemoLabel> memoLabelList, KindAndTasteVo kindAndTasteVo) {
        for (int i = 0; i < cartRecyclerItems.size(); i++) {
            CartDetailRecyclerItem cartDetailRecyclerItem = cartRecyclerItems.get(i);
            if (cartDetailRecyclerItem.getItemType() == CartDetailRecyclerItem.ItemType.TYPE_CHOICE) {
                CartDetailRecyclerChoiceItem choiceItem = cartDetailRecyclerItem.getCartDetailRecyclerChoiceItem();
                if (null != choiceItem) {
                    if (!TextUtils.isEmpty(choiceItem.getKey()) && choiceItem.getKey().equals(choiceKey)) {
                        List<CustomFlexItem> checkedFlexItemList = choiceItem.getCheckedFlexItemList();
                        if (null != checkedFlexItemList && !checkedFlexItemList.isEmpty()) {
                            for (int j = 0; j < checkedFlexItemList.size(); j++) {
                                CustomFlexItem checkItem = checkedFlexItemList.get(j);
                                String id = checkItem.getId();
                                if (!TextUtils.isEmpty(id)) {
                                    String name = getLabelName(kindAndTasteVo, id);
                                    MemoLabel memoLabel = new MemoLabel();
                                    memoLabel.setLabelId(id);
                                    memoLabel.setLabelName(name);
                                    memoLabelList.add(memoLabel);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取是否暂不上菜、是否赠送这个菜
     *
     * @param cartRecyclerItems
     * @param type              1：是否暂不上菜 2：是否赠送这个菜
     * @return
     */
    private boolean getFoodIfPresent(List<CartDetailRecyclerItem> cartRecyclerItems, int type) {
        for (int i = 0; i < cartRecyclerItems.size(); i++) {
            CartDetailRecyclerItem cartDetailRecyclerItem = cartRecyclerItems.get(i);
            if (cartDetailRecyclerItem.getItemType() == CartDetailRecyclerItem.ItemType.TYPE_REMARK) {
                CartDetailRecyclerRemarkItem remarkItem = cartDetailRecyclerItem.getCartDetailRecyclerRemarkItem();
                if (null != remarkItem) {
                    if (type == 1) {
                        return remarkItem.isStandBy();
                    } else {
                        return remarkItem.isPresenterFood();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取手动输入的备注
     *
     * @param cartRecyclerItems
     * @return
     */
    private String getMemo(List<CartDetailRecyclerItem> cartRecyclerItems) {
        String memo = null;
        for (int i = 0; i < cartRecyclerItems.size(); i++) {
            CartDetailRecyclerItem cartDetailRecyclerItem = cartRecyclerItems.get(i);
            if (cartDetailRecyclerItem.getItemType() == CartDetailRecyclerItem.ItemType.TYPE_REMARK) {
                CartDetailRecyclerRemarkItem remarkItem = cartDetailRecyclerItem.getCartDetailRecyclerRemarkItem();
                if (null != remarkItem) {
                    memo = remarkItem.getMemo();
                }
            }
        }
        return memo;
    }

    /**
     * 获取普通菜子菜列表数据
     */
    private List<CartItem> getChildFoodList(List<CartDetailRecyclerItem> cartRecyclerItems) {
        List<CartItem> childCartItemList = new ArrayList<>();
        for (int i = 0; i < cartRecyclerItems.size(); i++) {
            CartDetailRecyclerItem cartDetailRecyclerItem = cartRecyclerItems.get(i);
            if (cartDetailRecyclerItem.getItemType() == CartDetailRecyclerItem.ItemType.TYPE_FEED) {
                CartDetailRecyclerFeedItem feedItem = cartDetailRecyclerItem.getCartDetailRecyclerFeedItem();
                if (null != feedItem && feedItem.getNum() > 0) {
                    CartItem cartItem = new CartItem();
                    cartItem.setMenuName(feedItem.getFeedName());
                    cartItem.setMenuId(feedItem.getFeedId());
                    cartItem.setKindMenuId(feedItem.getCategoryId());
                    cartItem.setNum(feedItem.getNum());
                    cartItem.setAccountNum(feedItem.getNum());
                    cartItem.setKindType(CartHelper.CartFoodKind.KIND_FEED_FOOD);
                    if (!TextUtils.isEmpty(feedItem.getIndex())) {
                        cartItem.setIndex(feedItem.getIndex());
                    } else {
                        cartItem.setIndex(UserHelper.getEntityId() + StringUtils.getRandomString(24));
                    }
                    childCartItemList.add(cartItem);
                }
            }
        }
        return childCartItemList;
    }

    private Map<String, List<MemoLabel>> getLabels(List<CartDetailRecyclerItem> cartRecyclerItems
            , List<KindAndTasteVo> mKindTasteList) {
        Map<String, List<MemoLabel>> labels = null;
        if (null != mKindTasteList && !mKindTasteList.isEmpty()) {
            for (int i = 0; i < mKindTasteList.size(); i++) {
                KindAndTasteVo kindAndTasteVo = mKindTasteList.get(i);
                if (null != kindAndTasteVo) {
                    List<MemoLabel> memoLabelList = new ArrayList<>();
                    if (!TextUtils.isEmpty(kindAndTasteVo.getKindTasteId())) {
                        getTasteChoiceCheckedId(kindAndTasteVo.getKindTasteId(), cartRecyclerItems
                                , memoLabelList, kindAndTasteVo);
                        if (!memoLabelList.isEmpty()) {
                            if (null == labels) {
                                labels = new HashMap<>();
                            }
                            labels.put(kindAndTasteVo.getKindTasteId(), memoLabelList);
                        }
                    }
                }
            }
        }
        return labels;
    }

    private String getLabelName(KindAndTasteVo kindAndTasteVo, String id) {
        String name = null;
        List<Taste> tasteList = kindAndTasteVo.getTasteList();
        if (null != tasteList && !tasteList.isEmpty()) {
            for (int j = 0; j < tasteList.size(); j++) {
                Taste taste = tasteList.get(j);
                if (null != taste && !TextUtils.isEmpty(taste.getId()) && id.equals(taste.getId())) {
                    name = taste.getName();
                }
            }
        }
        return name;
    }

    /**
     * 获取结账数量
     *
     * @param cartRecyclerItems
     * @return
     */
    private double getAccountNum(List<CartDetailRecyclerItem> cartRecyclerItems) {
        double accountNum = 0;
        for (int i = 0; i < cartRecyclerItems.size(); i++) {
            CartDetailRecyclerItem cartDetailRecyclerItem = cartRecyclerItems.get(i);
            if (cartDetailRecyclerItem.getItemType() == CartDetailRecyclerItem.ItemType.TYPE_WEIGHT) {
                CartDetailRecyclerWeightItem weightItem = cartDetailRecyclerItem.getCartDetailRecyclerWeightItem();
                if (null != weightItem) {
                    accountNum = weightItem.getAccountNum();
                }
            }
        }
        return accountNum;
    }

    /**
     * 获取原始结账数量
     *
     * @param cartRecyclerItems
     * @return
     */
    private double getOriginAccountNum(List<CartDetailRecyclerItem> cartRecyclerItems) {
        double originAccountNum = 0;
        for (int i = 0; i < cartRecyclerItems.size(); i++) {
            CartDetailRecyclerItem cartDetailRecyclerItem = cartRecyclerItems.get(i);
            if (cartDetailRecyclerItem.getItemType() == CartDetailRecyclerItem.ItemType.TYPE_WEIGHT) {
                CartDetailRecyclerWeightItem weightItem = cartDetailRecyclerItem.getCartDetailRecyclerWeightItem();
                if (null != weightItem) {
                    originAccountNum = weightItem.getOriginAccountNum();
                }
            }
        }
        return originAccountNum;
    }

    /**
     * 根据有没有“赠送这个菜”权限，来打开/关闭 赠送菜的开关
     *
     * @param cartRecyclerItems
     * @param hasPermission
     */
    private void checkPresentFoodPermission(List<CartDetailRecyclerItem> cartRecyclerItems, boolean hasPermission) {
        if (null == mView) {
            return;
        }
        //如果没有权限，需要回退开关状态
        if (!hasPermission) {
            mView.toastCartMsg(GlobalVars.context.getString(R.string.module_menu_cart_present_food_not_permission));
            if (null != cartRecyclerItems && !cartRecyclerItems.isEmpty()) {
                for (int i = 0; i < cartRecyclerItems.size(); i++) {
                    CartDetailRecyclerItem cartDetailRecyclerItem = cartRecyclerItems.get(i);
                    if (cartDetailRecyclerItem.getItemType() == CartDetailRecyclerItem.ItemType.TYPE_REMARK) {
                        CartDetailRecyclerRemarkItem remarkItem = cartDetailRecyclerItem.getCartDetailRecyclerRemarkItem();
                        if (null != remarkItem) {
                            remarkItem.setPresenterFood(!remarkItem.isPresenterFood());
                            if (null != mView) {
                                mView.backPresentFoodPermission();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取套餐子菜修改后的信息，返回给套餐列表界面
     *
     * @param menuId
     * @param normalMenuVo
     * @param cartRecyclerItems
     * @param num
     * @param mKindTasteList
     * @return
     */
    @Override
    public SuitChildVO getComboMenu(String menuId, NormalMenuVo normalMenuVo, List<CartDetailRecyclerItem> cartRecyclerItems
            , double num, List<KindAndTasteVo> mKindTasteList) {
        //如果有规格做法的菜，未选择了规格/做法，给予提示
        if (isSpecOrMakeEmpty(cartRecyclerItems)) {
            return null;
        }
        SuitChildVO suitChildVO = new SuitChildVO();
        Menu normalMenu = normalMenuVo.getMenu();
        if (null != normalMenu) {
            suitChildVO.setId(menuId);
            suitChildVO.setName(normalMenu.getName());
            Recipe recipe = null;
            String checkedMakeId = getChoiceCheckedId(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_MAKE, cartRecyclerItems);
            List<MakeDataDto> makeDataList = normalMenuVo.getMakeDataList();
            if (null != makeDataList && !TextUtils.isEmpty(checkedMakeId)) {
                recipe = new Recipe();
                for (int i = 0; i < makeDataList.size(); i++) {
                    MakeDataDto makeDto = makeDataList.get(i);
                    if (makeDto.getMakeId().equals(checkedMakeId)) {
                        recipe.setName(makeDto.getName());
                        recipe.setMakeId(makeDto.getMakeId());
                        recipe.setMakePrice(makeDto.getMakePrice());
                        recipe.setMakePriceMode(makeDto.getMakePriceMode());
                        break;
                    }
                }
            }
            if (recipe != null) {
                List<Recipe> makeDataDtos = new ArrayList<>(1);
                makeDataDtos.add(recipe);
                suitChildVO.setMakeDataDtos(makeDataDtos);
            }
            suitChildVO.setSpecDetailId(getChoiceCheckedId(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_SPEC, cartRecyclerItems));

            suitChildVO.setPrice(normalMenu.getPrice());
            suitChildVO.setAccountNum(getAccountNum(cartRecyclerItems));
            suitChildVO.setAccount(normalMenu.getAccount());
            suitChildVO.setNum(num);
            suitChildVO.setBuyAccount(normalMenu.getBuyAccount());
            suitChildVO.setLabels(getLabels(cartRecyclerItems, mKindTasteList));
            suitChildVO.setMemo(getMemo(cartRecyclerItems));
            suitChildVO.setAcridLevel(normalMenu.getAcridLevel());
            suitChildVO.setRecommendLevel(normalMenu.getRecommendLevel());
            suitChildVO.setAcridLevelString(normalMenu.getAcridLevelString());
            suitChildVO.setSpecialTagString(normalMenu.getSpecialTagString());
            suitChildVO.setAddPrice(normalMenu.getAddPrice());
            suitChildVO.setAddPriceMode(normalMenu.getAddPriceMode());
            suitChildVO.setAttachmentId(normalMenu.getAttachmentId());
            suitChildVO.setBasePrice(normalMenu.getBasePrice());
            suitChildVO.setBasePriceMode(normalMenu.getBasePriceMode());
            suitChildVO.setConsume(normalMenu.getConsume());
            short present = CartHelper.PresentFood.NOT;
            if (getFoodIfPresent(cartRecyclerItems, 2)) {
                present = CartHelper.PresentFood.YES;
            }
            suitChildVO.setIsGive(present);
            short standby = CartHelper.StandBy.NOT;
            if ((getFoodIfPresent(cartRecyclerItems, 1))) {
                standby = CartHelper.StandBy.YES;
            }
            suitChildVO.setIsWait(standby);
            suitChildVO.setKindMenuId(normalMenu.getKindMenuId());
            suitChildVO.setKindMenuName(normalMenu.getKindMenuName());
            suitChildVO.setStartNum(normalMenu.getStartNum());
            suitChildVO.setDoubleUnitStatus(suitChildVO.getAccountNum() == getOriginAccountNum(cartRecyclerItems) ?
                    CartHelper.DoubleUnitStatus.FALSE : CartHelper.DoubleUnitStatus.TRUE);
        }
        return suitChildVO;
    }
}
