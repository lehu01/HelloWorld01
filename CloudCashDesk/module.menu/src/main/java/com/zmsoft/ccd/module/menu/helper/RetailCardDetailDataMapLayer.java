package com.zmsoft.ccd.module.menu.helper;

import android.content.Context;
import android.text.TextUtils;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexItem;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerCategoryItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerChoiceItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerFeedItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerPriceItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerRemarkItem;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerWeightItem;
import com.zmsoft.ccd.module.menu.cart.model.Item;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.MemoLabel;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.AdditionKindMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.AdditionMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.KindAndTasteVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.MakeDataDto;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.Menu;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.NormalMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.SpecDataDto;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.Taste;
import com.zmsoft.ccd.module.menu.menu.bean.ParamSuitSubMenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 原始数据处理<br />
 * <p>
 * 将服务端返回的原始数据处理为界面展示需要的数据模型
 * </p>
 *
 * @author DangGui
 * @create 2017/4/20.
 */
public class RetailCardDetailDataMapLayer extends BaseDataHelper {

    public static NormalMenuVo getNormalMenuVo(BaseMenuVo baseMenuVo) {
        NormalMenuVo normalMenuVo = null;
        try {
            normalMenuVo = JsonMapper.fromJson(baseMenuVo.getMenuJson(), NormalMenuVo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return normalMenuVo;
    }

    /**
     * 获取商品详情
     *
     * @param context
     * @param baseMenuVo 菜详情bean
     * @param itemVo     购物车中该菜的bean （商品修改页该字段才有值，详情页等该值传null即可）
     * @param specId     套餐子菜详情 掌柜上设置的默认规格ID
     * @return
     */
    public static List<CartDetailRecyclerItem> getCartDetailList(Context context, BaseMenuVo baseMenuVo
            , ItemVo itemVo, int detailType, String specId) {
        if (null == baseMenuVo) {
            return null;
        }
        NormalMenuVo normalMenuVo = getNormalMenuVo(baseMenuVo);
        //备注（口味等选项）
        List<KindAndTasteVo> kindAndTasteVoList = baseMenuVo.getKindTasteList();
        if (null == normalMenuVo) {
            return null;
        }
        switch (detailType) {
            case CartHelper.FoodDetailType.FOOD_DETAIL:
            case CartHelper.FoodDetailType.COMBO_CHILD_DETAIL:
                itemVo = null;
                break;
            case CartHelper.FoodDetailType.FOOD_MODIFY:
                break;
            case CartHelper.FoodDetailType.MUST_SELECT_MODIFY:
                break;
            case CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL:
                itemVo = null;
                break;
            default:
                break;
        }
        List<CartDetailRecyclerItem> cartDetailRecyclerItemList = new ArrayList<>();
        Menu menu = normalMenuVo.getMenu();
        if (null != menu) {
            //单价
            CartDetailRecyclerItem cartDetailPriceRecyclerItem = new CartDetailRecyclerItem();
            cartDetailPriceRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_PRICE);
            cartDetailPriceRecyclerItem.setDetailType(detailType);
            CartDetailRecyclerPriceItem priceItem = new CartDetailRecyclerPriceItem();
            cartDetailPriceRecyclerItem.setCartDetailRecyclerPriceItem(priceItem);
            priceItem.setFoodName(menu.getName());
            if (menu.getPrice() > 0) {
                String price = FeeHelper.getDecimalFee(menu.getPrice());
                price = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                        , price);
                priceItem.setPrice(price);
            }
            if (!TextUtils.isEmpty(menu.getAccount())) {
                priceItem.setUnit(menu.getAccount());
            } else {
                priceItem.setUnit(menu.getBuyAccount());
            }
            priceItem.setChili(menu.getAcridLevel());
            priceItem.setRecommendation(menu.getRecommendLevel());
            priceItem.setSpecialty(menu.getSpecialTagString());
            //套餐子菜详情不显示价格
            if ((detailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                    || detailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL)) {
                if (menu.getAcridLevel() > 0 || menu.getRecommendLevel() > 0
                        || !TextUtils.isEmpty(menu.getSpecialTagString())) {
                    cartDetailRecyclerItemList.add(cartDetailPriceRecyclerItem);
                }
            } else {
                cartDetailRecyclerItemList.add(cartDetailPriceRecyclerItem);
            }
            //重量
//            boolean hasAccountUnit = !TextUtils.isEmpty(menu.getBuyAccount()) && !TextUtils.isEmpty(menu.getAccount())
//                    && !menu.getBuyAccount().equals(menu.getAccount());
//            boolean isTwoAccount = ((menu.getIsTwoAccount() == CartHelper.TwoAccountKind.KIND_2Account)
//                    || hasAccountUnit); //是否有双单位
//            if (isTwoAccount) {
//                CartDetailRecyclerItem cartDetailWeightRecyclerItem = new CartDetailRecyclerItem();
//                cartDetailWeightRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_WEIGHT);
//                cartDetailWeightRecyclerItem.setDetailType(detailType);
//                CartDetailRecyclerWeightItem weightItem = new CartDetailRecyclerWeightItem();
//                cartDetailWeightRecyclerItem.setCartDetailRecyclerWeightItem(weightItem);
//                cartDetailRecyclerItemList.add(cartDetailWeightRecyclerItem);
//                //商品重量用的是默认值，如果是商品修改页，需要用顾客已点数量赋值给AccountNum
//                String num = null;
//                if (null != itemVo && itemVo.getAccountNum() > 0) {
//                    num = FeeHelper.getDecimalFee(itemVo.getAccountNum());
//                    weightItem.setAccountNum(itemVo.getAccountNum());
//                    weightItem.setOriginAccountNum(itemVo.getAccountNum());
//                }
//                if (TextUtils.isEmpty(num)) {
//                    if (menu.getDefaultNum() > 0) {
//                        weightItem.setAccountNum(menu.getDefaultNum());
//                        weightItem.setOriginAccountNum(menu.getDefaultNum());
//                    } else {
//                        weightItem.setAccountNum(CartHelper.CART_DETAIL_DOUBLE_UNIT_ACCOUNT_NUM);
//                        weightItem.setOriginAccountNum(CartHelper.CART_DETAIL_DOUBLE_UNIT_ACCOUNT_NUM);
//                    }
//                }
//                if (!TextUtils.isEmpty(menu.getAccount())) {
//                    weightItem.setAccountUnit(menu.getAccount());
//                }
//            }

            //规格
            List<SpecDataDto> specDataList = normalMenuVo.getSpecDataList();
            //套餐子菜详情，规格默认只显示并且选中掌柜上设置套餐时选择的规格，不能修改
            if (detailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                    || detailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                if (null != specDataList && !specDataList.isEmpty()) {
                    Iterator<SpecDataDto> specDataDtoIterator = specDataList.iterator();
                    while (specDataDtoIterator.hasNext()) {
                        SpecDataDto specDataDto = specDataDtoIterator.next();
                        if (!specDataDto.getSpecItemId().equals(specId))
                            specDataDtoIterator.remove();
                    }
                }
            }
            if (null != specDataList && !specDataList.isEmpty()) {
                CartDetailRecyclerItem cartDetailSpecRecyclerItem = new CartDetailRecyclerItem();
                cartDetailSpecRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_CHOICE);
                cartDetailSpecRecyclerItem.setDetailType(detailType);
                CartDetailRecyclerChoiceItem specItem = new CartDetailRecyclerChoiceItem();
                cartDetailSpecRecyclerItem.setCartDetailRecyclerChoiceItem(specItem);
                cartDetailRecyclerItemList.add(cartDetailSpecRecyclerItem);
                specItem.setKey(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_SPEC);
                specItem.setName(context.getResources().getString(R.string.module_menu_cartdetail_spec));
                specItem.setMustSelect(true);
                //供选择的item
                List<CustomFlexItem> customFlexItemList = new ArrayList<>();
                //被选中的item
                List<CustomFlexItem> checkedFlexItemList = new ArrayList<>();
                for (int i = 0; i < specDataList.size(); i++) {
                    SpecDataDto specDataDto = specDataList.get(i);
                    String addPrice = null;
                    if (specDataDto.getPriceScale() > 0) {
                        addPrice = FeeHelper.getDecimalFee(specDataDto.getPriceScale());
                        addPrice = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , addPrice);
                    }
                    CustomFlexItem customFlexItem = new CustomFlexItem(specDataDto.getSpecItemId()
                            , specDataDto.getName(), addPrice, false);
                    //商品修改页，默认选中用户之前选好的规格
                    if (null != itemVo && itemVo.getSpecDetailId().equals(specDataDto.getSpecItemId())) {
                        customFlexItem.setChecked(true);
                        checkedFlexItemList.add(customFlexItem);
                    }
                    //套餐子菜详情，规格默认选中掌柜上设置套餐时选择的规格，不能修改
                    if (detailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                            || detailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                        //套餐子菜详情下，specDataList默认应该只返回一个规格，这里为了容错，判断第0项为默认选中的item
                        if (i == 0) {
                            customFlexItem.setChecked(true);
                            checkedFlexItemList.add(customFlexItem);
                        }
                        customFlexItem.setUnModifyAble(true);
                    }
                    customFlexItemList.add(customFlexItem);
                }
                specItem.setCustomFlexItemList(customFlexItemList);
                specItem.setCheckedFlexItemList(checkedFlexItemList);
            }

            //做法
            List<MakeDataDto> makeDataList = normalMenuVo.getMakeDataList();
            if (null != makeDataList && !makeDataList.isEmpty()) {
                CartDetailRecyclerItem cartDetailMakeRecyclerItem = new CartDetailRecyclerItem();
                cartDetailMakeRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_CHOICE);
                cartDetailMakeRecyclerItem.setDetailType(detailType);
                CartDetailRecyclerChoiceItem makeItem = new CartDetailRecyclerChoiceItem();
                cartDetailMakeRecyclerItem.setCartDetailRecyclerChoiceItem(makeItem);
                cartDetailRecyclerItemList.add(cartDetailMakeRecyclerItem);
                makeItem.setKey(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_MAKE);
                makeItem.setName(context.getResources().getString(R.string.module_menu_cartdetail_makename));
                makeItem.setMustSelect(true);
                //供选择的item
                List<CustomFlexItem> customFlexItemList = new ArrayList<>(makeDataList.size());
                //被选中的item
                List<CustomFlexItem> checkedFlexItemList = new ArrayList<>();
                for (int i = 0; i < makeDataList.size(); i++) {
                    MakeDataDto makeDataDto = makeDataList.get(i);
                    String addPrice = null;
                    if (makeDataDto.getMakePrice() > 0) {
                        addPrice = FeeHelper.getDecimalFee(makeDataDto.getMakePrice());
                        switch (makeDataDto.getMakePriceMode()) {
                            case CartHelper.AddPriceMode.MODE_NONE:
                                addPrice = null;
                                break;
                            case CartHelper.AddPriceMode.MODE_ONCE:
                                addPrice = String.format(context.getResources().getString(R.string.module_menu_cartdetail_make_addprice_once)
                                        , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                                , addPrice));
                                break;
                            case CartHelper.AddPriceMode.MODE_BUY_UNIT:
                                addPrice = String.format(context.getResources().getString(R.string.module_menu_cartdetail_make_addprice_per)
                                        , menu.getBuyAccount(), FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                                , addPrice));
                                break;
                            case CartHelper.AddPriceMode.MODE_ACCOUNT_UNIT:
                                addPrice = String.format(context.getResources().getString(R.string.module_menu_cartdetail_make_addprice_per)
                                        , menu.getAccount(), FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                                , addPrice));
                                break;
                            default:
                                addPrice = null;
                                break;
                        }
                    }
                    CustomFlexItem customFlexItem = new CustomFlexItem(makeDataDto.getMakeId()
                            , makeDataDto.getName(), addPrice, false);
                    //商品修改页，默认选中用户之前选好的做法
                    if (null != itemVo && itemVo.getMakeId().equals(makeDataDto.getMakeId())) {
                        customFlexItem.setChecked(true);
                        checkedFlexItemList.add(customFlexItem);
                    }
                    //套餐必选子菜详情，做法默认选中第一个
                    if (detailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                        if (i == 0) {
                            customFlexItem.setChecked(true);
                            checkedFlexItemList.add(customFlexItem);
                        }
                    }
                    customFlexItemList.add(customFlexItem);
                }
                makeItem.setCustomFlexItemList(customFlexItemList);
                makeItem.setCheckedFlexItemList(checkedFlexItemList);
            }

            //加料(必选商品修改界面、套餐子菜详情、套餐必选子菜详情界面 不展示加料)
            if (detailType != CartHelper.FoodDetailType.MUST_SELECT_MODIFY
                    && detailType != CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                    && detailType != CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                List<AdditionKindMenuVo> additionKindMenuList = normalMenuVo.getAdditionKindMenuList();
                if (null != additionKindMenuList && !additionKindMenuList.isEmpty()) {
                    for (int i = 0; i < additionKindMenuList.size(); i++) {
                        //加料分类
                        AdditionKindMenuVo additionKindMenuVo = additionKindMenuList.get(i);
                        CartDetailRecyclerItem cartDetailCategoryRecyclerItem = new CartDetailRecyclerItem();
                        cartDetailCategoryRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_CATEGORY);
                        cartDetailCategoryRecyclerItem.setDetailType(detailType);
                        CartDetailRecyclerCategoryItem categoryItem = new CartDetailRecyclerCategoryItem();
                        cartDetailCategoryRecyclerItem.setCartDetailRecyclerCategoryItem(categoryItem);
                        cartDetailRecyclerItemList.add(cartDetailCategoryRecyclerItem);
                        if (i == 0) {
                            categoryItem.setCategoryHeaderName(context.getResources()
                                    .getString(R.string.module_menu_cartdetail_feed));
                        }
                        categoryItem.setCategoryName(additionKindMenuVo.getKindMenuName());
                        //加料菜
                        List<AdditionMenuVo> additionMenuList = additionKindMenuVo.getAdditionMenuList();
                        if (null != additionMenuList && !additionMenuList.isEmpty()) {
                            for (int j = 0; j < additionMenuList.size(); j++) {
                                AdditionMenuVo additionMenuVo = additionMenuList.get(j);
                                CartDetailRecyclerItem cartDetailFeedRecyclerItem = new CartDetailRecyclerItem();
                                cartDetailFeedRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_FEED);
                                cartDetailFeedRecyclerItem.setDetailType(detailType);
                                CartDetailRecyclerFeedItem feedItem = new CartDetailRecyclerFeedItem();
                                cartDetailFeedRecyclerItem.setCartDetailRecyclerFeedItem(feedItem);
                                cartDetailRecyclerItemList.add(cartDetailFeedRecyclerItem);
                                feedItem.setFeedName(additionMenuVo.getMenuName());
                                feedItem.setCategoryId(additionKindMenuVo.getKindMenuId());
                                feedItem.setFeedId(additionMenuVo.getMenuId());
                                feedItem.setSoldOut(additionMenuVo.isSoldOut());
                                //如果是修改页，需用将用户相应加料菜已点数量赋值给getNum
                                double num = 0;
                                if (null != itemVo) {
                                    num = getAdditionMenuNum(itemVo, additionMenuVo);
                                }
                                if (num <= 0) {
                                    num = additionMenuVo.getNum();
                                }
                                feedItem.setNum(num);
                                feedItem.setIndex(getAdditionMenuIndex(itemVo, additionMenuVo));
                                double price = additionMenuVo.getMenuPrice();
                                String feedPrice = null;
                                if (price > 0) {
                                    feedPrice = FeeHelper.getDecimalFee(price);
                                    feedPrice = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                            , feedPrice);
                                }
                                feedItem.setPrice(feedPrice);
                            }
                        }
                    }
                }
            }

            //备注(口味等选项)
            if (null != kindAndTasteVoList && !kindAndTasteVoList.isEmpty()) {
                for (int i = 0; i < kindAndTasteVoList.size(); i++) {
                    //口味分类
                    KindAndTasteVo kindAndTasteVo = kindAndTasteVoList.get(i);
                    CartDetailRecyclerItem cartDetailCategoryRecyclerItem = new CartDetailRecyclerItem();
                    cartDetailCategoryRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_CATEGORY);
                    cartDetailCategoryRecyclerItem.setDetailType(detailType);
                    CartDetailRecyclerCategoryItem categoryItem = new CartDetailRecyclerCategoryItem();
                    cartDetailCategoryRecyclerItem.setCartDetailRecyclerCategoryItem(categoryItem);
                    cartDetailRecyclerItemList.add(cartDetailCategoryRecyclerItem);
                    categoryItem.setCategoryType(CartDetailRecyclerItem.ItemType.TYPE_REMARK);
                    if (i == 0) {
                        categoryItem.setCategoryHeaderName(context.getResources().getString(R.string.module_menu_cartdetail_remark));
                    }
                    categoryItem.setCategoryName(kindAndTasteVo.getKindTasteName());

                    //口味
                    List<Taste> tasteList = kindAndTasteVo.getTasteList();
                    if (null != tasteList && !tasteList.isEmpty()) {
                        CartDetailRecyclerItem cartDetailTasteRecyclerItem = new CartDetailRecyclerItem();
                        cartDetailTasteRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_CHOICE);
                        cartDetailTasteRecyclerItem.setDetailType(detailType);
                        CartDetailRecyclerChoiceItem tasteItem = new CartDetailRecyclerChoiceItem();
                        cartDetailTasteRecyclerItem.setCartDetailRecyclerChoiceItem(tasteItem);
                        cartDetailRecyclerItemList.add(cartDetailTasteRecyclerItem);
                        tasteItem.setKey(kindAndTasteVo.getKindTasteId());
                        //供选择的item
                        List<CustomFlexItem> customFlexItemList = new ArrayList<>(tasteList.size());
                        //被选中的item
                        List<CustomFlexItem> checkedFlexItemList = new ArrayList<>();
                        List<MemoLabel> checkedLableList = null;
                        Map<String, List<MemoLabel>> labels = null;
                        if (null != itemVo) {
                            labels = itemVo.getLabels();
                        }
                        if (null != labels && !labels.isEmpty()) {
                            checkedLableList = labels.get(kindAndTasteVo.getKindTasteId());
                        }
                        for (int j = 0; j < tasteList.size(); j++) {
                            Taste taste = tasteList.get(j);
                            CustomFlexItem customFlexItem = new CustomFlexItem(taste.getId()
                                    , taste.getName());
                            if (null != checkedLableList && !checkedLableList.isEmpty()) {
                                for (int k = 0; k < checkedLableList.size(); k++) {
                                    MemoLabel checkedLabel = checkedLableList.get(k);
                                    if (null != checkedLabel && null != taste.getId()
                                            && taste.getId().equals(checkedLabel.getLabelId())) {
                                        customFlexItem.setChecked(true);
                                        checkedFlexItemList.add(customFlexItem);
                                    }
                                }
                            }
                            customFlexItemList.add(customFlexItem);
                        }
                        tasteItem.setCustomFlexItemList(customFlexItemList);
                        tasteItem.setCheckedFlexItemList(checkedFlexItemList);
                    }
                }
            }

            //备注（页面底部）
            CartDetailRecyclerItem cartDetailRemarkRecyclerItem = new CartDetailRecyclerItem();
            cartDetailRemarkRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_REMARK);
            cartDetailRemarkRecyclerItem.setDetailType(detailType);
            CartDetailRecyclerRemarkItem remarkItem = new CartDetailRecyclerRemarkItem();
            cartDetailRemarkRecyclerItem.setCartDetailRecyclerRemarkItem(remarkItem);
            cartDetailRecyclerItemList.add(cartDetailRemarkRecyclerItem);

            //套餐子菜详情 不能选择暂不上菜,不能赠送这个菜
            if (detailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                    || detailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                remarkItem.setShowStandBy(false);
                remarkItem.setShowPresenterFood(false);
            } else {
                remarkItem.setShowStandBy(true);
                remarkItem.setShowPresenterFood(menu.getIsGive() == 1);
                if (null != itemVo) {
                    remarkItem.setPresenterFood(itemVo.getPresent() == CartHelper.PresentFood.YES);
                }
            }
            if (null != itemVo) {
                remarkItem.setStandBy(itemVo.getIsWait() == 1);
                if (!TextUtils.isEmpty(itemVo.getMemo())) {
                    remarkItem.setMemo(itemVo.getMemo());
                }
            }
        }

        return cartDetailRecyclerItemList;
    }

    /**
     * 获取套餐必选子菜商品详情
     *
     * @param context
     * @param baseMenuVo       菜详情bean
     * @param paramSuitSubMenu 套餐必选子菜
     * @param specId           套餐子菜详情 掌柜上设置的默认规格ID
     * @return
     */
    public static List<CartDetailRecyclerItem> getComboDetailList(Context context, BaseMenuVo baseMenuVo
            , ParamSuitSubMenu paramSuitSubMenu, int detailType, String specId) {
        if (null == baseMenuVo) {
            return null;
        }
        NormalMenuVo normalMenuVo = getNormalMenuVo(baseMenuVo);
        //备注（口味等选项）
        List<KindAndTasteVo> kindAndTasteVoList = baseMenuVo.getKindTasteList();
        if (null == normalMenuVo) {
            return null;
        }
        List<CartDetailRecyclerItem> cartDetailRecyclerItemList = new ArrayList<>();
        Menu menu = normalMenuVo.getMenu();
        if (null != menu) {
            //单价
            CartDetailRecyclerItem cartDetailPriceRecyclerItem = new CartDetailRecyclerItem();
            cartDetailPriceRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_PRICE);
            cartDetailPriceRecyclerItem.setDetailType(detailType);
            CartDetailRecyclerPriceItem priceItem = new CartDetailRecyclerPriceItem();
            cartDetailPriceRecyclerItem.setCartDetailRecyclerPriceItem(priceItem);
            priceItem.setFoodName(menu.getName());
            if (menu.getPrice() > 0) {
                String price = FeeHelper.getDecimalFee(menu.getPrice());
                price = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                        , price);
                priceItem.setPrice(price);
            }
            if (!TextUtils.isEmpty(menu.getAccount())) {
                priceItem.setUnit(menu.getAccount());
            } else {
                priceItem.setUnit(menu.getBuyAccount());
            }
            priceItem.setChili(menu.getAcridLevel());
            priceItem.setRecommendation(menu.getRecommendLevel());
            priceItem.setSpecialty(menu.getSpecialTagString());
            //套餐子菜详情不显示价格
            if ((detailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                    || detailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL)) {
                if (menu.getAcridLevel() > 0 || menu.getRecommendLevel() > 0
                        || !TextUtils.isEmpty(menu.getSpecialTagString())) {
                    cartDetailRecyclerItemList.add(cartDetailPriceRecyclerItem);
                }
            } else {
                cartDetailRecyclerItemList.add(cartDetailPriceRecyclerItem);
            }
            //重量
            boolean hasAccountUnit = !TextUtils.isEmpty(menu.getBuyAccount()) && !TextUtils.isEmpty(menu.getAccount())
                    && !menu.getBuyAccount().equals(menu.getAccount());
            boolean isTwoAccount = ((menu.getIsTwoAccount() == CartHelper.TwoAccountKind.KIND_2Account)
                    || hasAccountUnit); //是否有双单位
            if (isTwoAccount) {
                CartDetailRecyclerItem cartDetailWeightRecyclerItem = new CartDetailRecyclerItem();
                cartDetailWeightRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_WEIGHT);
                cartDetailWeightRecyclerItem.setDetailType(detailType);
                CartDetailRecyclerWeightItem weightItem = new CartDetailRecyclerWeightItem();
                cartDetailWeightRecyclerItem.setCartDetailRecyclerWeightItem(weightItem);
                cartDetailRecyclerItemList.add(cartDetailWeightRecyclerItem);
                //商品重量用的是默认值，如果是商品修改页，需要用顾客已点数量赋值给AccountNum
                String num = null;
                if (null != paramSuitSubMenu && paramSuitSubMenu.getAccountNum() > 0) {
                    num = FeeHelper.getDecimalFee(paramSuitSubMenu.getAccountNum());
                    weightItem.setAccountNum(paramSuitSubMenu.getAccountNum());
                    weightItem.setOriginAccountNum(paramSuitSubMenu.getAccountNum());
                }
                if (TextUtils.isEmpty(num)) {
                    if (menu.getDefaultNum() > 0) {
                        weightItem.setAccountNum(menu.getDefaultNum());
                        weightItem.setOriginAccountNum(menu.getDefaultNum());
                    } else {
                        weightItem.setAccountNum(CartHelper.CART_DETAIL_DOUBLE_UNIT_ACCOUNT_NUM);
                        weightItem.setOriginAccountNum(CartHelper.CART_DETAIL_DOUBLE_UNIT_ACCOUNT_NUM);
                    }
                }
                if (!TextUtils.isEmpty(menu.getAccount())) {
                    weightItem.setAccountUnit(menu.getAccount());
                }
            }

            //规格
            List<SpecDataDto> specDataList = normalMenuVo.getSpecDataList();
            //套餐子菜详情，规格默认只显示并且选中掌柜上设置套餐时选择的规格，不能修改
            if (detailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                    || detailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                if (null != specDataList && !specDataList.isEmpty()) {
                    Iterator<SpecDataDto> specDataDtoIterator = specDataList.iterator();
                    while (specDataDtoIterator.hasNext()) {
                        SpecDataDto specDataDto = specDataDtoIterator.next();
                        if (!specDataDto.getSpecItemId().equals(specId))
                            specDataDtoIterator.remove();
                    }
                }
            }
            if (null != specDataList && !specDataList.isEmpty()) {
                CartDetailRecyclerItem cartDetailSpecRecyclerItem = new CartDetailRecyclerItem();
                cartDetailSpecRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_CHOICE);
                cartDetailSpecRecyclerItem.setDetailType(detailType);
                CartDetailRecyclerChoiceItem specItem = new CartDetailRecyclerChoiceItem();
                cartDetailSpecRecyclerItem.setCartDetailRecyclerChoiceItem(specItem);
                cartDetailRecyclerItemList.add(cartDetailSpecRecyclerItem);
                specItem.setKey(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_SPEC);
                specItem.setName(context.getResources().getString(R.string.module_menu_cartdetail_spec));
                specItem.setMustSelect(true);
                //供选择的item
                List<CustomFlexItem> customFlexItemList = new ArrayList<>();
                //被选中的item
                List<CustomFlexItem> checkedFlexItemList = new ArrayList<>();
                for (int i = 0; i < specDataList.size(); i++) {
                    SpecDataDto specDataDto = specDataList.get(i);
                    String addPrice = null;
                    if (specDataDto.getPriceScale() > 0) {
                        addPrice = FeeHelper.getDecimalFee(specDataDto.getPriceScale());
                        addPrice = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , addPrice);
                    }
                    CustomFlexItem customFlexItem = new CustomFlexItem(specDataDto.getSpecItemId()
                            , specDataDto.getName(), addPrice, false);
                    //套餐子菜详情，规格默认选中掌柜上设置套餐时选择的规格，不能修改
                    if (detailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                            || detailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                        //套餐子菜详情下，specDataList默认应该只返回一个规格，这里为了容错，判断第0项为默认选中的item
                        if (i == 0) {
                            customFlexItem.setChecked(true);
                            checkedFlexItemList.add(customFlexItem);
                        }
                        customFlexItem.setUnModifyAble(true);
                    }
                    customFlexItemList.add(customFlexItem);
                }
                specItem.setCustomFlexItemList(customFlexItemList);
                specItem.setCheckedFlexItemList(checkedFlexItemList);
            }

            //做法
            List<MakeDataDto> makeDataList = normalMenuVo.getMakeDataList();
            if (null != makeDataList && !makeDataList.isEmpty()) {
                CartDetailRecyclerItem cartDetailMakeRecyclerItem = new CartDetailRecyclerItem();
                cartDetailMakeRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_CHOICE);
                cartDetailMakeRecyclerItem.setDetailType(detailType);
                CartDetailRecyclerChoiceItem makeItem = new CartDetailRecyclerChoiceItem();
                cartDetailMakeRecyclerItem.setCartDetailRecyclerChoiceItem(makeItem);
                cartDetailRecyclerItemList.add(cartDetailMakeRecyclerItem);
                makeItem.setKey(CartDetailRecyclerChoiceItem.ChoiceItemKey.KEY_MAKE);
                makeItem.setName(context.getResources().getString(R.string.module_menu_cartdetail_makename));
                makeItem.setMustSelect(true);
                //供选择的item
                List<CustomFlexItem> customFlexItemList = new ArrayList<>(makeDataList.size());
                //被选中的item
                List<CustomFlexItem> checkedFlexItemList = new ArrayList<>();
                for (int i = 0; i < makeDataList.size(); i++) {
                    MakeDataDto makeDataDto = makeDataList.get(i);
                    String addPrice = null;
                    if (makeDataDto.getMakePrice() > 0) {
                        addPrice = FeeHelper.getDecimalFee(makeDataDto.getMakePrice());
                        switch (makeDataDto.getMakePriceMode()) {
                            case CartHelper.AddPriceMode.MODE_NONE:
                                addPrice = null;
                                break;
                            case CartHelper.AddPriceMode.MODE_ONCE:
                                addPrice = String.format(context.getResources().getString(R.string.module_menu_cartdetail_make_addprice_once)
                                        , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                                , addPrice));
                                break;
                            case CartHelper.AddPriceMode.MODE_BUY_UNIT:
                                addPrice = String.format(context.getResources().getString(R.string.module_menu_cartdetail_make_addprice_per)
                                        , menu.getBuyAccount(), FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                                , addPrice));
                                break;
                            case CartHelper.AddPriceMode.MODE_ACCOUNT_UNIT:
                                addPrice = String.format(context.getResources().getString(R.string.module_menu_cartdetail_make_addprice_per)
                                        , menu.getAccount(), FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                                , addPrice));
                                break;
                            default:
                                addPrice = null;
                                break;
                        }
                    }
                    CustomFlexItem customFlexItem = new CustomFlexItem(makeDataDto.getMakeId()
                            , makeDataDto.getName(), addPrice, false);
                    //商品修改页，默认选中用户之前选好的做法
                    if (null != paramSuitSubMenu) {
                        if (paramSuitSubMenu.getMakeId().equals(makeDataDto.getMakeId())) {
                            customFlexItem.setChecked(true);
                            checkedFlexItemList.add(customFlexItem);
                        }
                    } else {
                        //套餐必选子菜详情，做法默认选中第一个
                        if (detailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                            if (i == 0) {
                                customFlexItem.setChecked(true);
                                checkedFlexItemList.add(customFlexItem);
                            }
                        }
                    }
                    customFlexItemList.add(customFlexItem);
                }
                makeItem.setCustomFlexItemList(customFlexItemList);
                makeItem.setCheckedFlexItemList(checkedFlexItemList);
            }

            //备注(口味等选项)
            if (null != kindAndTasteVoList && !kindAndTasteVoList.isEmpty()) {
                for (int i = 0; i < kindAndTasteVoList.size(); i++) {
                    //口味分类
                    KindAndTasteVo kindAndTasteVo = kindAndTasteVoList.get(i);
                    CartDetailRecyclerItem cartDetailCategoryRecyclerItem = new CartDetailRecyclerItem();
                    cartDetailCategoryRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_CATEGORY);
                    cartDetailCategoryRecyclerItem.setDetailType(detailType);
                    CartDetailRecyclerCategoryItem categoryItem = new CartDetailRecyclerCategoryItem();
                    cartDetailCategoryRecyclerItem.setCartDetailRecyclerCategoryItem(categoryItem);
                    cartDetailRecyclerItemList.add(cartDetailCategoryRecyclerItem);
                    categoryItem.setCategoryType(CartDetailRecyclerItem.ItemType.TYPE_REMARK);
                    if (i == 0) {
                        categoryItem.setCategoryHeaderName(context.getResources().getString(R.string.module_menu_cartdetail_remark));
                    }
                    categoryItem.setCategoryName(kindAndTasteVo.getKindTasteName());

                    //口味
                    List<Taste> tasteList = kindAndTasteVo.getTasteList();
                    if (null != tasteList && !tasteList.isEmpty()) {
                        CartDetailRecyclerItem cartDetailTasteRecyclerItem = new CartDetailRecyclerItem();
                        cartDetailTasteRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_CHOICE);
                        cartDetailTasteRecyclerItem.setDetailType(detailType);
                        CartDetailRecyclerChoiceItem tasteItem = new CartDetailRecyclerChoiceItem();
                        cartDetailTasteRecyclerItem.setCartDetailRecyclerChoiceItem(tasteItem);
                        cartDetailRecyclerItemList.add(cartDetailTasteRecyclerItem);
                        tasteItem.setKey(kindAndTasteVo.getKindTasteId());
                        //供选择的item
                        List<CustomFlexItem> customFlexItemList = new ArrayList<>(tasteList.size());
                        //被选中的item
                        List<CustomFlexItem> checkedFlexItemList = new ArrayList<>();
                        List<MemoLabel> checkedLableList = null;
                        Map<String, List<MemoLabel>> labels = null;
                        if (null != paramSuitSubMenu) {
                            labels = paramSuitSubMenu.getLabels();
                        }
                        if (null != labels && !labels.isEmpty()) {
                            checkedLableList = labels.get(kindAndTasteVo.getKindTasteId());
                        }
                        for (int j = 0; j < tasteList.size(); j++) {
                            Taste taste = tasteList.get(j);
                            CustomFlexItem customFlexItem = new CustomFlexItem(taste.getId()
                                    , taste.getName());
                            if (null != checkedLableList && !checkedLableList.isEmpty()) {
                                for (int k = 0; k < checkedLableList.size(); k++) {
                                    MemoLabel checkedLabel = checkedLableList.get(k);
                                    if (null != checkedLabel && null != taste.getId()
                                            && taste.getId().equals(checkedLabel.getLabelId())) {
                                        customFlexItem.setChecked(true);
                                        checkedFlexItemList.add(customFlexItem);
                                    }
                                }
                            }
                            customFlexItemList.add(customFlexItem);
                        }
                        tasteItem.setCustomFlexItemList(customFlexItemList);
                        tasteItem.setCheckedFlexItemList(checkedFlexItemList);
                    }
                }
            }

            //备注（页面底部）
            CartDetailRecyclerItem cartDetailRemarkRecyclerItem = new CartDetailRecyclerItem();
            cartDetailRemarkRecyclerItem.setItemType(CartDetailRecyclerItem.ItemType.TYPE_REMARK);
            cartDetailRemarkRecyclerItem.setDetailType(detailType);
            CartDetailRecyclerRemarkItem remarkItem = new CartDetailRecyclerRemarkItem();
            cartDetailRemarkRecyclerItem.setCartDetailRecyclerRemarkItem(remarkItem);
            cartDetailRecyclerItemList.add(cartDetailRemarkRecyclerItem);

            //套餐子菜详情 不能选择暂不上菜,不能赠送这个菜
            if (detailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                    || detailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                remarkItem.setShowStandBy(false);
                remarkItem.setShowPresenterFood(false);
            }
            if (null != paramSuitSubMenu) {
                if (!TextUtils.isEmpty(paramSuitSubMenu.getMemo())) {
                    remarkItem.setMemo(paramSuitSubMenu.getMemo());
                }
            }
        }

        return cartDetailRecyclerItemList;
    }

    /**
     * 商品修改页，根据传进来的itemVo获取相应加料菜的点菜数量
     *
     * @param itemVo
     * @param additionMenuVo
     * @return
     */
    private static double getAdditionMenuNum(ItemVo itemVo, AdditionMenuVo additionMenuVo) {
        double num = 0;
        if (null == itemVo || null == additionMenuVo) {
            return num;
        }
        //普通菜的加料菜
        List<Item> childItems = itemVo.getChildItems();
        if (null != childItems && !childItems.isEmpty()) {
            for (int i = 0; i < childItems.size(); i++) {
                Item item = childItems.get(i);
                if (item.getMenuId().equals(additionMenuVo.getMenuId())) {
                    num = item.getNum();
                }
            }
        }
        return num;
    }

    /**
     * 商品修改页，根据传进来的itemVo获取相应加料菜的index
     *
     * @param itemVo
     * @param additionMenuVo
     * @return
     */
    private static String getAdditionMenuIndex(ItemVo itemVo, AdditionMenuVo additionMenuVo) {
        String index = null;
        if (null == itemVo || null == additionMenuVo) {
            return null;
        }
        //普通菜的加料菜
        List<Item> childItems = itemVo.getChildItems();
        if (null != childItems && !childItems.isEmpty()) {
            for (int i = 0; i < childItems.size(); i++) {
                Item item = childItems.get(i);
                if (item.getMenuId().equals(additionMenuVo.getMenuId())) {
                    index = item.getIndex();
                }
            }
        }
        return index;
    }
}