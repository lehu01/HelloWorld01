package com.zmsoft.ccd.module.menu.helper;

import android.content.Context;
import android.text.TextUtils;

import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartCategoryRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartComboFoodRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartNormalFoodRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartOrderRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.model.CustomerVo;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.Item;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.KindMenuCount;
import com.zmsoft.ccd.module.menu.cart.model.KindUserCartVo;
import com.zmsoft.ccd.module.menu.cart.model.MemoLabel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 原始数据处理<br />
 * <p>
 * 将服务端返回的原始数据处理为界面展示需要的数据模型
 * </p>
 *
 * @author DangGui
 * @create 2017/4/13.
 */

public class DataMapLayer extends BaseDataHelper {
    /**
     * 购物车列表界面
     *
     * @param dinningTableVo 服务端返回的原始数据
     * @return 界面展示需要的数据模型
     */
    public static List<CartRecyclerItem> getCartList(Context context, DinningTableVo dinningTableVo
            , OrderParam createOrderParam) {
        if (null == dinningTableVo)
            return null;
        List<CartRecyclerItem> cartRecyclerItemList = new ArrayList<>();
        //order
        CartRecyclerItem orderRecyclerItem = new CartRecyclerItem();
        orderRecyclerItem.setItemType(CartRecyclerItem.ItemType.TYPE_ORDER_INFO);

        CartOrderRecyclerItem cartOrderRecyclerItem = new CartOrderRecyclerItem();
        cartOrderRecyclerItem.setSeatCode(createOrderParam == null ? null : createOrderParam.getSeatCode());
        cartOrderRecyclerItem.setSeatName(createOrderParam == null ? null : createOrderParam.getSeatName());
        cartOrderRecyclerItem.setPeopleCount(dinningTableVo.getPeople());
        cartOrderRecyclerItem.setRemark(dinningTableVo.getMemo());
        cartOrderRecyclerItem.setSummaryFoodInfo(getSummaryInfo(context, dinningTableVo));
        boolean canModifyOrder = true;
        if (null != createOrderParam && !TextUtils.isEmpty(createOrderParam.getOrderId())) {
            canModifyOrder = false;
        }
        cartOrderRecyclerItem.setCanModifyOrder(canModifyOrder);
        List<KindUserCartVo> kindUserCarts = dinningTableVo.getKindUserCarts();
        if (null == kindUserCarts || kindUserCarts.isEmpty()) {
            cartOrderRecyclerItem.setCartEmpty(true);
        }
        orderRecyclerItem.setCartOrderRecyclerItem(cartOrderRecyclerItem);
        cartRecyclerItemList.add(orderRecyclerItem);

        //菜详情（必选商品、热菜、冷菜等）
        handleCartFoodList(context, cartRecyclerItemList, dinningTableVo, cartOrderRecyclerItem
                , createOrderParam);

        return cartRecyclerItemList;
    }

    /**
     * 过期购物车列表界面
     *
     * @return 界面展示需要的数据模型
     */
    public static List<CartRecyclerItem> getExpiredCartList() {
        List<CartRecyclerItem> cartRecyclerItemList = new ArrayList<>();
        //order
        CartRecyclerItem orderRecyclerItem = new CartRecyclerItem();
        orderRecyclerItem.setItemType(CartRecyclerItem.ItemType.TYPE_ORDER_INFO);

        CartOrderRecyclerItem cartOrderRecyclerItem = new CartOrderRecyclerItem();
        cartOrderRecyclerItem.setCartExpired(true);
        orderRecyclerItem.setCartOrderRecyclerItem(cartOrderRecyclerItem);
        cartRecyclerItemList.add(orderRecyclerItem);
        return cartRecyclerItemList;
    }

    /**
     * 处理菜详情（必选商品、热菜、冷菜等）
     *
     * @param context
     * @param cartRecyclerItemList
     * @param dinningTableVo
     */
    private static void handleCartFoodList(Context context, List<CartRecyclerItem> cartRecyclerItemList
            , DinningTableVo dinningTableVo, CartOrderRecyclerItem cartOrderRecyclerItem
            , OrderParam createOrderParam) {
        //合计总金额
        double amountFee = 0;
        //菜详情（必选商品、热菜、冷菜等）
        List<KindUserCartVo> kindUserCarts = dinningTableVo.getKindUserCarts();
        if (null != kindUserCarts && !kindUserCarts.isEmpty()) {
            for (int i = 0; i < kindUserCarts.size(); i++) {
                KindUserCartVo kindUserCartVo = kindUserCarts.get(i);
                if (null != kindUserCartVo) {
                    //category
                    CartRecyclerItem categoryRecyclerItem = new CartRecyclerItem();
                    categoryRecyclerItem.setItemType(CartRecyclerItem.ItemType.TYPE_CATEGORY);

                    CartCategoryRecyclerItem cartCategoryRecyclerItem = new CartCategoryRecyclerItem();
                    if (null != kindUserCartVo.getKindVo()) {
                        cartCategoryRecyclerItem.setCategoryName(kindUserCartVo.getKindVo().getName());
                    }

                    categoryRecyclerItem.setCartCategoryRecyclerItem(cartCategoryRecyclerItem);
                    cartRecyclerItemList.add(categoryRecyclerItem);

                    //food
                    amountFee += handleNormalAndComboFood(context, kindUserCartVo, cartCategoryRecyclerItem
                            , cartRecyclerItemList, createOrderParam);
                }
            }
        }
        //给OrderItem的“合计”设置总金额
        cartOrderRecyclerItem.setSummaryAmount(FeeHelper.getDecimalFee(amountFee));
    }

    /**
     * 处理普通菜、套餐菜等
     *
     * @param context
     * @param kindUserCartVo
     * @param cartCategoryRecyclerItem
     * @param cartRecyclerItemList
     */
    private static double handleNormalAndComboFood(Context context, KindUserCartVo kindUserCartVo
            , CartCategoryRecyclerItem cartCategoryRecyclerItem, List<CartRecyclerItem> cartRecyclerItemList
            , OrderParam createOrderParam) {
        //合计总金额
        double amountFee = 0;
        //food
        List<ItemVo> itemVos = kindUserCartVo.getItemVos();
        if (null != itemVos && !itemVos.isEmpty()) {
            for (int j = 0; j < itemVos.size(); j++) {
                ItemVo itemVo = itemVos.get(j);
                if (null != itemVo) {
                    //如果是必选商品，将cartCategoryRecyclerItem的foodtype设置为必选商品
                    if (itemVo.getIsCompulsory()) {
                        cartCategoryRecyclerItem.setFoodType(CartHelper.CartFoodType.TYPE_MUST_SELECT);
                    }
                    //给原始accountNum赋值
                    itemVo.setOriginAccountNum(itemVo.getAccountNum());
                    switch (itemVo.getKind()) {
                        case CartHelper.CartFoodKind.KIND_NORMAL_FOOD:
                        case CartHelper.CartFoodKind.KIND_CUSTOM_FOOD:
                            CartRecyclerItem normalFoodRecyclerItem = new CartRecyclerItem();
                            normalFoodRecyclerItem.setItemType(CartRecyclerItem.ItemType.TYPE_NORMAL_FOOD);

                            CartNormalFoodRecyclerItem cartNormalFoodRecyclerItem = new CartNormalFoodRecyclerItem();
                            //将该菜的原始数据信息保存在recyclerItem内，供跳转到购物车详情等界面时候使用
                            cartNormalFoodRecyclerItem.setItemVo(itemVo);
                            cartNormalFoodRecyclerItem.setCreateOrderParam(createOrderParam);
                            cartNormalFoodRecyclerItem.setSeatCode(createOrderParam == null ?
                                    null : createOrderParam.getSeatCode());
                            cartNormalFoodRecyclerItem.setOrderId(createOrderParam == null ?
                                    null : createOrderParam.getOrderId());
                            //如果是必选商品，将cartNormalFoodRecyclerItem的foodtype设置为必选商品
                            if (itemVo.getIsCompulsory()) {
                                cartNormalFoodRecyclerItem.setFoodType(CartHelper.CartFoodType.TYPE_MUST_SELECT);
                            }
                            CustomerVo customerVo = itemVo.getCustomerVo();
                            if (null != customerVo) {
                                cartNormalFoodRecyclerItem.setCustomerName(customerVo.getName());
                                cartNormalFoodRecyclerItem.setCustomerAvatarUrl(customerVo.getImageUrl());
                            }
                            cartNormalFoodRecyclerItem.setFoodName(itemVo.getName());
                            //单价
                            if (itemVo.getFee() >= 0) {
                                amountFee += itemVo.getFee();
                                String fee = FeeHelper.getDecimalFee(itemVo.getFee());
                                fee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                        , fee);
                                cartNormalFoodRecyclerItem.setFoodPrice(fee);
                            }
                            //做法
                            cartNormalFoodRecyclerItem.setMakeMethod(getNormalFoodMakeName(context, itemVo));
                            //单位
                            cartNormalFoodRecyclerItem.setNum(itemVo.getNum());
                            cartNormalFoodRecyclerItem.setUnit(itemVo.getUnit());
                            cartNormalFoodRecyclerItem.setAccountNum(itemVo.getAccountNum());
                            cartNormalFoodRecyclerItem.setAccountUnit(itemVo.getAccountUnit());

                            //是否“暂不上菜”
                            if (itemVo.getIsWait() == CartHelper.ServeFoodKind.KIND_STANDBY) {
                                cartNormalFoodRecyclerItem.setStandby(true);
                            }

                            normalFoodRecyclerItem.setCartNormalFoodRecyclerItem(cartNormalFoodRecyclerItem);
                            cartRecyclerItemList.add(normalFoodRecyclerItem);
                            break;
                        case CartHelper.CartFoodKind.KIND_COMBO_FOOD:
                            CartRecyclerItem comboFoodRecyclerItem = new CartRecyclerItem();
                            comboFoodRecyclerItem.setItemType(CartRecyclerItem.ItemType.TYPE_COMBO_FOOD);

                            CartComboFoodRecyclerItem cartComboFoodRecyclerItem = new CartComboFoodRecyclerItem();
                            //将该菜的原始数据信息保存在recyclerItem内，供跳转到购物车详情等界面时候使用
                            cartComboFoodRecyclerItem.setItemVo(itemVo);
                            cartComboFoodRecyclerItem.setCreateOrderParam(createOrderParam);
                            cartComboFoodRecyclerItem.setSeatCode(createOrderParam == null ?
                                    null : createOrderParam.getSeatCode());
                            cartComboFoodRecyclerItem.setOrderId(createOrderParam == null ?
                                    null : createOrderParam.getOrderId());
                            //如果是必选商品，将cartComboFoodRecyclerItem的foodtype设置为必选商品
                            if (itemVo.getIsCompulsory()) {
                                cartComboFoodRecyclerItem.setFoodType(CartHelper.CartFoodType.TYPE_MUST_SELECT);
                            }
                            CustomerVo comboCustomerVo = itemVo.getCustomerVo();
                            if (null != comboCustomerVo) {
                                cartComboFoodRecyclerItem.setCustomerName(comboCustomerVo.getName());
                                cartComboFoodRecyclerItem.setCustomerAvatarUrl(comboCustomerVo.getImageUrl());
                            }
                            //
                            cartComboFoodRecyclerItem.setFoodName(itemVo.getName());
                            //单价
                            if (itemVo.getFee() >= 0) {
                                amountFee += itemVo.getFee();
                                String fee = FeeHelper.getDecimalFee(itemVo.getFee());
                                fee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                        , fee);
                                cartComboFoodRecyclerItem.setFoodPrice(fee);
                            }
                            //做法
                            cartComboFoodRecyclerItem.setMakeMethod(getComboFoodMakeName(context, itemVo, cartComboFoodRecyclerItem));
                            //单位
                            cartComboFoodRecyclerItem.setNum(itemVo.getNum());
                            cartComboFoodRecyclerItem.setUnit(itemVo.getUnit());
                            cartComboFoodRecyclerItem.setAccountNum(itemVo.getAccountNum());
                            cartComboFoodRecyclerItem.setAccountUnit(itemVo.getAccountUnit());

                            //是否“暂不上菜”
                            if (itemVo.getIsWait() == CartHelper.ServeFoodKind.KIND_STANDBY) {
                                cartComboFoodRecyclerItem.setStandby(true);
                            }

                            comboFoodRecyclerItem.setCartComboFoodRecyclerItem(cartComboFoodRecyclerItem);
                            cartRecyclerItemList.add(comboFoodRecyclerItem);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return amountFee;
    }

    /**
     * 获取购物车各个菜类数量信息，拼接
     *
     * @param context
     * @param dinningTableVo
     * @return
     */
    private static String getSummaryInfo(Context context, DinningTableVo dinningTableVo) {
        StringBuilder summaryInfoBuilder = new StringBuilder();
        LinkedHashMap<String, KindMenuCount> kindMenuDic = dinningTableVo.getKindMenuDic();
        if (null != kindMenuDic && !kindMenuDic.isEmpty()) {
            for (Map.Entry<String, KindMenuCount> entry : kindMenuDic.entrySet()) {
                KindMenuCount kindMenuCount = entry.getValue();
                if (!TextUtils.isEmpty(kindMenuCount.getKindMenuName()) && kindMenuCount.getCount() > 0) {
                    summaryInfoBuilder.append(String.format(context.getResources().getString(R.string.module_menu_cart_summart_foodnum)
                            , kindMenuCount.getKindMenuName()
                            , kindMenuCount.getCount()));
                    summaryInfoBuilder.append(CartHelper.COMMA_SEPARATOR);
                }
            }
        }
        return summaryInfoBuilder.toString();
    }

    /**
     * 获取普通菜的做法
     * 商品如果有规格、做法、加料、备注，需要按照此顺序依次全部显示，中间用逗号隔开，加料需要显示数量（例如：＋香菜1份）。超过一行时换行显示。
     * 如果是套餐，子菜按照点菜顺序显示，如果子菜有规格、做法、加料、备注，则跟随在相应的子菜后面从左到右依次显示。
     *
     * @param itemVo
     * @return 展示需要的makeName
     */
    private static String getNormalFoodMakeName(Context context, ItemVo itemVo) {
        StringBuilder makeNameBuilder = new StringBuilder();
        //规格
        if (!TextUtils.isEmpty(itemVo.getSpecDetailName())) {
            makeNameBuilder.append(itemVo.getSpecDetailName());
            makeNameBuilder.append(CartHelper.COMMA_SEPARATOR);
        }
        //做法
        if (!TextUtils.isEmpty(itemVo.getMakeName())) {
            makeNameBuilder.append(itemVo.getMakeName());
            makeNameBuilder.append(CartHelper.COMMA_SEPARATOR);
        }
        //加料(如果必选商品有加料菜，不展示加料菜)
        if (!itemVo.getIsCompulsory()) {
            List<Item> feedList = itemVo.getChildItems();
            if (null != feedList && !feedList.isEmpty()) {
                for (int i = 0; i < feedList.size(); i++) {
                    Item foodItem = feedList.get(i);
                    //如果是加料菜
                    if (null != foodItem && foodItem.getKind() == CartHelper.CartFoodKind.KIND_FEED_FOOD) {
                        double foodItemNum = foodItem.getNum();
                        if (!TextUtils.isEmpty(foodItem.getName()) && foodItemNum > 0) {
                            String num = foodItemNum + "";
                            if (doubleIsInteger(foodItemNum)) {
                                num = (int) foodItemNum + "";
                            }
                            makeNameBuilder.append(String.format(context.getResources().getString(R.string.module_menu_cart_feed_foodnum)
                                    , foodItem.getName()
                                    , num
                                    , foodItem.getUnit()));
                            makeNameBuilder.append(CartHelper.COMMA_SEPARATOR);
                        }
                    }
                }
            }
        }
        //备注
        //用户在详情页选择的标签备注
        Map<String, List<MemoLabel>> labels = itemVo.getLabels();
        if (null != labels && !labels.isEmpty()) {
            for (Map.Entry<String, List<MemoLabel>> entry : labels.entrySet()) {
                List<MemoLabel> memoLabelList = entry.getValue();
                if (null != memoLabelList && !memoLabelList.isEmpty()) {
                    for (int i = 0; i < memoLabelList.size(); i++) {
                        MemoLabel memoLabel = memoLabelList.get(i);
                        if (null != memoLabel && !TextUtils.isEmpty(memoLabel.getLabelName())) {
                            makeNameBuilder.append(memoLabel.getLabelName());
                            makeNameBuilder.append(CartHelper.COMMA_SEPARATOR);
                        }
                    }
                }
            }
        }
        if (!TextUtils.isEmpty(itemVo.getMemo())) {
            makeNameBuilder.append(itemVo.getMemo());
        }
        //如果最后是逗号结尾，则切割掉最后一个字符
        boolean isValid = makeNameBuilder.length() > 0 && String.valueOf(makeNameBuilder.charAt(makeNameBuilder.length() - 1))
                .equals(CartHelper.COMMA_SEPARATOR);
        if (isValid) {
            return makeNameBuilder.substring(0, makeNameBuilder.length() - 1);
        }
        return makeNameBuilder.toString();
    }

    /**
     * 获取套餐菜的做法
     * 商品如果有规格、做法、加料、备注，需要按照此顺序依次全部显示，中间用逗号隔开，加料需要显示数量（例如：＋香菜1份）。超过一行时换行显示。
     * 如果是套餐，子菜按照点菜顺序显示，如果子菜有规格、做法、加料、备注，则跟随在相应的子菜后面从左到右依次显示。
     *
     * @param itemVo
     * @return
     */
    private static String getComboFoodMakeName(Context context, ItemVo itemVo, CartComboFoodRecyclerItem cartComboFoodRecyclerItem) {
        StringBuilder makeNameBuilder = new StringBuilder();
        //子菜
        List<Item> feedList = itemVo.getChildItems();
        if (null != feedList && !feedList.isEmpty()) {
            cartComboFoodRecyclerItem.setHasSubMenu(true);
            for (int i = 0; i < feedList.size(); i++) {
                Item foodItem = feedList.get(i);
                //如果是子菜
                if (null != foodItem) {
                    double foodNum = foodItem.getNum();
                    if (!TextUtils.isEmpty(foodItem.getName()) && foodNum > 0) {
                        String foodNumStr;
                        if (doubleIsInteger(foodNum)) {
                            foodNumStr = (int) foodNum + "";
                        } else {
                            foodNumStr = foodNum + "";
                        }
                        makeNameBuilder.append(String.format(context.getResources().getString(R.string.module_menu_cart_submenu_foodnum)
                                , foodItem.getName()
                                , foodNumStr
                                , foodItem.getUnit()));
                        makeNameBuilder.append(CartHelper.COMMA_SEPARATOR);
                    }
                    getComboSubFoodMakeName(context, foodItem, makeNameBuilder);
//                    List<Item> subFeedList = foodItem.getChildItems();
//                    if (null != subFeedList && !subFeedList.isEmpty()) {
//                        for (int j = 0; j < subFeedList.size(); j++) {
//                            Item item = subFeedList.get(j);
//                            getComboSubFoodMakeName(context, item, makeNameBuilder);
//                        }
//                    }
                }
            }
            //套餐整体的备注
            if (!TextUtils.isEmpty(itemVo.getMemo())) {
                makeNameBuilder.append(itemVo.getMemo());
                makeNameBuilder.append(CartHelper.COMMA_SEPARATOR);
            }
            //如果最后是逗号结尾，则切割掉最后一个字符
            boolean isValid = makeNameBuilder.length() > 0 && String.valueOf(makeNameBuilder.charAt(makeNameBuilder.length() - 1))
                    .equals(CartHelper.COMMA_SEPARATOR);
            if (isValid) {
                return makeNameBuilder.substring(0, makeNameBuilder.length() - 1);
            }
        } else {
            cartComboFoodRecyclerItem.setHasSubMenu(false);
        }
        return makeNameBuilder.toString();
    }

    /**
     * 获取套餐子菜 做法
     *
     * @param itemVo
     * @return 展示需要的makeName
     */
    private static void getComboSubFoodMakeName(Context context, Item itemVo, StringBuilder makeNameBuilder) {
        //规格
        if (!TextUtils.isEmpty(itemVo.getSpecDetailName())) {
            makeNameBuilder.append(itemVo.getSpecDetailName());
            makeNameBuilder.append(CartHelper.COMMA_SEPARATOR);
        }
        //做法
        if (!TextUtils.isEmpty(itemVo.getMakeName())) {
            makeNameBuilder.append(itemVo.getMakeName());
            makeNameBuilder.append(CartHelper.COMMA_SEPARATOR);
        }
        //备注
        //用户在详情页选择的标签备注
        Map<String, List<MemoLabel>> labels = itemVo.getLabels();
        if (null != labels && !labels.isEmpty()) {
            for (Map.Entry<String, List<MemoLabel>> entry : labels.entrySet()) {
                List<MemoLabel> memoLabelList = entry.getValue();
                if (null != memoLabelList && !memoLabelList.isEmpty()) {
                    for (int i = 0; i < memoLabelList.size(); i++) {
                        MemoLabel memoLabel = memoLabelList.get(i);
                        if (null != memoLabel && !TextUtils.isEmpty(memoLabel.getLabelName())) {
                            makeNameBuilder.append(memoLabel.getLabelName());
                            makeNameBuilder.append(CartHelper.COMMA_SEPARATOR);
                        }
                    }
                }
            }
        }
        //备注
        if (!TextUtils.isEmpty(itemVo.getMemo())) {
            makeNameBuilder.append(itemVo.getMemo());
            makeNameBuilder.append(CartHelper.COMMA_SEPARATOR);
        }
    }
}
