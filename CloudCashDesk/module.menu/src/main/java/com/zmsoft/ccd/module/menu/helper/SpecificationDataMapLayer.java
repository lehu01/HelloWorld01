package com.zmsoft.ccd.module.menu.helper;

import android.content.Context;
import android.text.TextUtils;

import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.CustomerVo;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.Item;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.KindMenuCount;
import com.zmsoft.ccd.module.menu.cart.model.KindUserCartVo;
import com.zmsoft.ccd.module.menu.cart.model.MemoLabel;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.CartItemVO;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;
import com.zmsoft.ccd.module.menu.menu.bean.Recipe;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 原始数据处理<br />
 * <p>
 * 将服务端返回的原始数据处理为菜单界面需要的购物车数据
 * </p>
 *
 * @author DangGui
 * @create 2017/4/20.
 */

public class SpecificationDataMapLayer extends BaseDataHelper {
    /**
     * 菜单列表需要的购物车信息
     *
     * @param dinningTableVo 服务端返回的原始数据
     */
    public static List<CartItemVO> getSpecificationVOList(Context context, DinningTableVo dinningTableVo) {
        if (null == dinningTableVo)
            return null;
        List<CartItemVO> specificationVOList = new ArrayList<>();

        //菜详情（必选商品、热菜、冷菜等）
        handleCartFoodList(context, specificationVOList, dinningTableVo);

        return specificationVOList;
    }

    /**
     * 处理菜详情（必选商品、热菜、冷菜等）
     *
     * @param context
     * @param cartRecyclerItemList
     * @param dinningTableVo
     */
    private static void handleCartFoodList(Context context, List<CartItemVO> cartRecyclerItemList
            , DinningTableVo dinningTableVo) {
        //菜详情（必选商品、热菜、冷菜等）
        List<KindUserCartVo> kindUserCarts = dinningTableVo.getKindUserCarts();
        if (null != kindUserCarts && !kindUserCarts.isEmpty()) {
            for (int i = 0; i < kindUserCarts.size(); i++) {
                KindUserCartVo kindUserCartVo = kindUserCarts.get(i);
                if (null != kindUserCartVo) {
                    handleNormalAndComboFood(context, kindUserCartVo, cartRecyclerItemList);
                }
            }
        }
    }

    /**
     * @param item 套餐子菜、加料菜
     * @return CartItem
     */
    public static CartItem itemConvertToCartItem(Item item) {
        CartItem cartItem = new CartItem();
        cartItem.setMenuName(item.getName());
        cartItem.setMenuId(item.getMenuId());
        cartItem.setKindMenuId(item.getKindMenuId());
        cartItem.setNum(item.getNum());
        //如果套餐子菜、加料菜是单单位的，把accountNum设置和num一样（加料菜没有双单位）
        if (!item.isTwoAccount()) {
            cartItem.setAccountNum(item.getNum());
        }

        //加料菜不需要设置uid，但是要设置index
        //if (item.getKind() != CartHelper.CartFoodKind.KIND_FEED_FOOD) {
        //    cartItem.setUid(item.uid);
        //}
        cartItem.setIndex(item.getIndex());
        //菜类型
        cartItem.setKindType(item.getKind());

        cartItem.setSpecId(item.getSpecDetailId());
        cartItem.setMakeId(item.getMakeId());

        //用户输入的备注
        cartItem.setMemo(item.getMemo());
        //用户选择的备注
        cartItem.setLabels(item.getLabels());

        return cartItem;
    }

    /**
     * @param menuVO 套餐子菜
     * @return CartItem
     */
    public static CartItem suitSubMenuConvertToCartItem(MenuVO menuVO) {
        CartItem cartItem = new CartItem();
        cartItem.setMenuName(menuVO.getMenuName());
        cartItem.setMenuId(menuVO.getMenuId());
        cartItem.setKindMenuId(menuVO.getKindMenuId());
        cartItem.setNum(menuVO.getTmpNum());
        //如果套餐子菜、加料菜是单单位的，把accountNum设置和num一样（加料菜没有双单位）
        if (!menuVO.isTwoAccount()) {
            cartItem.setAccountNum(menuVO.getTmpNum());
        } else {
            cartItem.setAccountNum(menuVO.getAccountNum());
        }
        //所属的分组id
        cartItem.setSuitMenuDetailId(menuVO.getSuitGroupId());
        //加料菜不需要设置uid，但是要设置index
        //if (item.getKind() != CartHelper.CartFoodKind.KIND_FEED_FOOD) {
        //    cartItem.setUid(item.uid);
        //}
        cartItem.setIndex(menuVO.getIndex());
        //菜类型
        cartItem.setKindType(menuVO.getKindWhenUpdateCart());

        cartItem.setSpecId(menuVO.getSpecDetailId());
        cartItem.setMakeId(menuVO.getMakeId());

        cartItem.setSource(CartHelper.CartSource.CART_COMBO_CHILD_DETAIL);

        //用户输入的备注
        cartItem.setMemo(menuVO.getMemo());
        //用户选择的备注
        if (menuVO.getLabels() == null || menuVO.getLabels().isEmpty()) {
            cartItem.setLabels(null);
        } else {
            cartItem.setLabels(menuVO.getLabels());
        }

        return cartItem;
    }

    /**
     * 把套餐子菜或者加料菜List<Item>转成List<CartItem>
     *
     * @param items 套餐子菜或者加料菜
     * @return List<CartItem>
     */
    public static List<CartItem> convertToCartItems(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        List<CartItem> list = new ArrayList<>();
        for (Item item : items) {
            if (item.getNum() > 0) {
                list.add(itemConvertToCartItem(item));
            }
        }
        return list;
    }

    /**
     * 处理普通菜、套餐菜等
     *
     * @param context
     * @param kindUserCartVo
     */
    private static void handleNormalAndComboFood(Context context, KindUserCartVo kindUserCartVo
            , List<CartItemVO> specificationVOList) {
        //food
        List<ItemVo> itemVos = kindUserCartVo.getItemVos();
        if (null != itemVos && !itemVos.isEmpty()) {
            for (int j = 0; j < itemVos.size(); j++) {
                ItemVo itemVo = itemVos.get(j);
                if (null != itemVo) {
                    CartItemVO cartItemVO = new CartItemVO(itemVo);
                    cartItemVO.setAccountNum(itemVo.getAccountNum());
                    switch (itemVo.getKind()) {
                        case CartHelper.CartFoodKind.KIND_NORMAL_FOOD:
                        case CartHelper.CartFoodKind.KIND_CUSTOM_FOOD:
                            CustomerVo customerVo = itemVo.getCustomerVo();
                            if (null != customerVo) {
                                cartItemVO.setMemberId(customerVo.getId());
                            }
                            //做法ID
                            cartItemVO.setMakeId(itemVo.getMakeId());
                            //规格ID
                            cartItemVO.setSpecId(itemVo.getSpecDetailId());
                            //加料列表
                            cartItemVO.setChildCartVos(convertToCartItems(itemVo.getChildItems()));
                            //菜ID
                            cartItemVO.setMenuId(itemVo.getMenuId());
                            //做法
                            cartItemVO.setSpecification(getNormalFoodMakeName(context, itemVo));
                            //用户输入的备注
                            cartItemVO.setMemo(itemVo.getMemo());
                            //用户选择的备注
                            cartItemVO.setLabels(itemVo.getLabels());
                            //点的数量
                            cartItemVO.setNum(itemVo.getNum());
                            //加价
                            double addPrice = getNormalFoodAddPrice(itemVo);
                            cartItemVO.setIndex(itemVo.getIndex());
                            cartItemVO.setExtraPrice(addPrice);
                            cartItemVO.setUnit(itemVo.getUnit());
                            cartItemVO.setAccountUnit(itemVo.getAccountUnit());
                            specificationVOList.add(cartItemVO);
                            break;
                        case CartHelper.CartFoodKind.KIND_COMBO_FOOD:
                            CustomerVo comboCustomerVo = itemVo.getCustomerVo();
                            if (null != comboCustomerVo) {
                                cartItemVO.setMemberId(comboCustomerVo.getId());
                            }
                            cartItemVO.setMenuId(itemVo.getMenuId());
                            //如果是套餐，且是必选菜，不需要展示specification
                            if (!itemVo.getIsCompulsory()) {
                                //做法
                                String specification = context.getResources()
                                        .getString(R.string.module_menu_cart_combo_sub, j + 1)
                                        + " " + getComboFoodMakeName(context, itemVo);
                                cartItemVO.setSpecification(specification);
                            }

                            //点的数量
                            cartItemVO.setNum(itemVo.getNum());
                            //加价
                            //double comboAddPrice = getComboFoodAddPrice(itemVo);
                            cartItemVO.setExtraPrice(getSuitMenuAddPrice(itemVo));
                            cartItemVO.setUnit(itemVo.getUnit());
                            cartItemVO.setAccountUnit(itemVo.getAccountUnit());
                            //套餐子菜
                            cartItemVO.setChildCartVos(convertToCartItems(itemVo.getChildItems()));
                            specificationVOList.add(cartItemVO);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
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
        StringBuilder sb = new StringBuilder();
        //规格
        if (!TextUtils.isEmpty(itemVo.getSpecDetailName())) {
            sb.append(itemVo.getSpecDetailName()).append(CartHelper.COMMA_SEPARATOR);
        }
        //做法
        if (!TextUtils.isEmpty(itemVo.getMakeName())) {
            sb.append(itemVo.getMakeName()).append(CartHelper.COMMA_SEPARATOR);
        }
        //加料(如果必选商品有加料菜，不展示加料菜)
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
                        sb.append(String.format(context.getResources().getString(R.string.module_menu_list_feed_foodnum)
                                , foodItem.getName()
                                , num
                                , foodItem.getUnit())).append(CartHelper.COMMA_SEPARATOR);
                    }
                }
            }
        }
        //用户选的备注
        String labelsString = getLabelsString(itemVo.getLabels());
        if (labelsString != null) {
            sb.append(labelsString).append(CartHelper.COMMA_SEPARATOR);
        }
        //用户输入的备注
        if (!TextUtils.isEmpty(itemVo.getMemo())) {
            sb.append(itemVo.getMemo()).append(CartHelper.COMMA_SEPARATOR);
        }

        if (sb.length() > 0) {
            return sb.deleteCharAt(sb.length() - 1).toString();
        }
        return sb.toString();
    }

    /**
     * 获取套餐菜的做法
     * 商品如果有规格、做法、加料、备注，需要按照此顺序依次全部显示，中间用逗号隔开，加料需要显示数量（例如：＋香菜1份）。超过一行时换行显示。
     * 如果是套餐，子菜按照点菜顺序显示，如果子菜有规格、做法、加料、备注，则跟随在相应的子菜后面从左到右依次显示。
     *
     * @param itemVo
     * @return
     */
    private static String getComboFoodMakeName(Context context, ItemVo itemVo) {
        StringBuilder makeNameBuilder = new StringBuilder();
        //子菜
        List<Item> feedList = itemVo.getChildItems();
        if (null != feedList && !feedList.isEmpty()) {
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
                    List<Item> subFeedList = foodItem.getChildItems();
                    if (null != subFeedList && !subFeedList.isEmpty()) {
                        for (int j = 0; j < subFeedList.size(); j++) {
                            Item item = subFeedList.get(j);
                            getComboSubFoodMakeName(context, item, makeNameBuilder);
                        }
                    }
                }
            }
            //如果最后是逗号结尾，则切割掉最后一个字符
            boolean isValid = makeNameBuilder.length() > 0 && String.valueOf(makeNameBuilder.charAt(makeNameBuilder.length() - 1))
                    .equals(CartHelper.COMMA_SEPARATOR);
            if (isValid) {
                return makeNameBuilder.substring(0, makeNameBuilder.length() - 1);
            }
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
    }

    /**
     * 计算做法的加价
     */
    private static double getMakePrice(int makePriceMode, double makePrice, double num, double accountNum) {
        double scale = 0;
        switch (makePriceMode) {
            case CartHelper.AddPriceMode.MODE_NONE:
                scale = 0;
                break;
            case CartHelper.AddPriceMode.MODE_ONCE:
                scale = 1;
                break;
            case CartHelper.AddPriceMode.MODE_BUY_UNIT:
                if (num >= 0) {
                    scale = num;
                }
                break;
            case CartHelper.AddPriceMode.MODE_ACCOUNT_UNIT:
                if (accountNum >= 0) {
                    scale = accountNum;
                }
                break;
            default:
                break;
        }
        return (makePrice * scale);
    }

    private static double getNormalFoodAddPrice(ItemVo itemVo) {
        double addPrice = itemVo.getAddPrice();// + itemVo.getSpecDetailPrice();规格不算在加价里面
        //做法加价
        addPrice += getMakePrice(itemVo.getMakePriceMode(), itemVo.getMakePrice(),
                itemVo.getNum(), itemVo.getAccountNum());
        //加料
        List<Item> feedList = itemVo.getChildItems();
        if (null != feedList && !feedList.isEmpty()) {
            for (int i = 0; i < feedList.size(); i++) {
                Item foodItem = feedList.get(i);
                //如果是加料菜
                if (null != foodItem && foodItem.getKind() == CartHelper.CartFoodKind.KIND_FEED_FOOD) {
                    if (foodItem.getNum() > 0) {
                        addPrice += (foodItem.getPrice() * foodItem.getNum() * itemVo.getNum());//加料加价（加料价格*加料份数*菜的份数）
                    }
                }
            }
        }
        return addPrice;
    }

    /**
     * @see SpecificationDataMapLayer#getSuitMenuAddPrice(ItemVo)
     */
    @Deprecated
    private static double getComboFoodAddPrice(ItemVo itemVo) {
        double addPrice = itemVo.getAddPrice();
        //子菜
        List<Item> feedList = itemVo.getChildItems();
        if (null != feedList && !feedList.isEmpty()) {
            for (int i = 0; i < feedList.size(); i++) {
                Item foodItem = feedList.get(i);
                //如果是子菜
                if (null != foodItem) {
                    //子菜的规格调价
                    //addPrice += foodItem.getSpecDetailPrice();

                    double foodNum = foodItem.getNum();
                    //子菜本身的加价
                    if (foodNum > 0) {
                        addPrice += (foodItem.getAddPrice() * foodNum);
                    }

                    //做法加价
                    addPrice += getMakePrice(foodItem.getMakePriceMode(), foodItem.getMakePrice(),
                            foodItem.getNum(), foodItem.getAccountNum());

                    //套餐目前没有加料，所以加料的价格没算
                }
            }
        }
        return addPrice * itemVo.getNum();
    }

    /**
     * 计算整个套餐的加价
     *
     * @param itemVo 购物车记录
     * @return addPrice
     */
    public static double getSuitMenuAddPrice(ItemVo itemVo) {
        if (itemVo != null) {
            return itemVo.getPrice() - itemVo.getOriginalPrice();
        }
        return 0;
    }

    /**
     * 获取套餐子菜加价
     */
    public static double getCombSubFoodAddPrice(Menu menu, Recipe recipe, double num, double accountNum) {
        double addPrice = 0;
        if (menu != null) {
            //菜本身加价
            addPrice += (menu.getAddPrice() * num);
        }

        if (recipe != null) {
            //做法加价
            addPrice += getMakePrice(recipe.getMakePriceMode(), recipe.getMakePrice(),
                    num, accountNum);
        }
        return addPrice;//cannot addPrice*num
    }

    private static String getLabelsString(Map<String, List<MemoLabel>> labels) {
        //用户选的备注
        if (labels != null && !labels.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, List<MemoLabel>> entry : labels.entrySet()) {
                List<MemoLabel> list = entry.getValue();
                for (MemoLabel label : list) {
                    sb.append(label.getLabelName()).append(CartHelper.COMMA_SEPARATOR);
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                return sb.toString();
            }
        }
        return null;
    }


    /**
     * 规格+做法+选择的备注（口味）+输入的备注
     */
    public static String getMenuSpecification(String specName, String makeName, Map<String, List<MemoLabel>> labels, String memo) {
        StringBuilder sb = new StringBuilder();
        //规格
        if (!TextUtils.isEmpty(specName)) {
            sb.append(specName).append(CartHelper.COMMA_SEPARATOR);
        }
        //做法
        if (!TextUtils.isEmpty(makeName)) {
            sb.append(makeName).append(CartHelper.COMMA_SEPARATOR);
        }

        //备注
        String labelsString = SpecificationDataMapLayer.getLabelsString(labels);
        if (labelsString != null) {
            sb.append(labelsString).append(CartHelper.COMMA_SEPARATOR);
        }

        if (!TextUtils.isEmpty(memo)) {
            sb.append(memo).append(CartHelper.COMMA_SEPARATOR);
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
