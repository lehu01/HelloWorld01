package com.zmsoft.ccd.module.menu.menu.bean.vo;

import android.text.TextUtils;
import android.util.Log;

import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.menu.cart.model.Item;
import com.zmsoft.ccd.module.menu.cart.model.MemoLabel;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.helper.SpecificationDataMapLayer;
import com.zmsoft.ccd.module.menu.menu.MenuUtils;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;
import com.zmsoft.ccd.module.menu.menu.bean.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/13.
 */

public class MenuVO {

    /**
     * 菜详情
     */
    private Menu menu;

    /**
     * 如果该菜是自己点的，且点的菜没有包含规格、做法或加料 且该购物车记录不是必选记录
     */
    private CartItemVO mySpecification;

    /**
     * 如果该购物车记录是必选记录
     */
    private CartItemVO compulsorySpecification;

    /**
     * MenuVO所属的MenuGroupVO
     */
    private MenuGroupVO menuGroupVO;

    /**
     * 临时保存的num
     */
    private double tmpNum;

    private List<CartItemVO> specificationVOs;

    /**
     * 初始值和num一样
     */
    private double aggregateNum;

    private SuitGroupVO suitGroupVO;

    /**
     * 用于套餐，用户选择的做法，如果没有设置默认第一个
     */
    private Recipe selectedMake;

    /**
     * 套餐的子菜（子菜已经加入了购物车这个属性才会有值）
     */
    private Item item;

    private String suitGroupId;

    private String index;
    /**
     * 规格id
     */
    private String specDetailId;

    private String memo;

    private short isWait;

    private Map<String, List<MemoLabel>> labels;

    private int kindType;

    private int doubleUnitStatus;

    private double accountNum = 1;

    /**
     * 当前点的菜是属于哪个列表显示的菜（用于套餐）
     */
    private MenuVO belongMenu;

    private MenuVO selectedMenuNoSpec;

    /**
     * 这个菜用户点了多少项（套餐子菜）
     */
    private List<MenuVO> selectedMenus;

    private String specification;

    /**
     * 套餐子菜的加价（本身的加价+做法等）
     */
    private double extraPrice;

    private boolean selectedMenuNewLine;

    private double suitSubInputNum;


    public MenuVO(Menu menu, CartItemVO specificationVO, List<CartItemVO> specificationVOs) {
        this.menu = menu;
        this.mySpecification = specificationVO;
        this.specificationVOs = specificationVOs;
    }

    public MenuVO(Menu menu) {
        this.menu = menu;
    }

    public MenuVO(Menu menu, MenuGroupVO menuGroupVO) {
        this.menu = menu;
        this.menuGroupVO = menuGroupVO;
    }

    public MenuVO(Menu menu, SuitGroupVO suitGroupVO) {
        this.menu = menu;
        this.suitGroupVO = suitGroupVO;
    }

    /**
     * @param menu             菜的基本信息
     * @param num              点菜数量
     * @param accountNum       结账数量
     * @param specDetailId     规格id
     * @param selectedMake     做法
     * @param labels           用户选择的备注（口味）
     * @param memo             用户输入的备注
     * @param isWait           是否先不上菜，1表示暂不上菜，0表示立即上菜
     * @param kindType         菜肴类型 1：普通菜 2：套菜 5：加料菜（只能在子child出现）
     * @param doubleUnitStatus 0：未修改 1：修改过
     * @return
     */
    public static MenuVO createForSuit(Menu menu, Item item, SuitGroupVO suitGroupVO, double num, double accountNum,
                                       String specDetailId, Recipe selectedMake,
                                       Map<String, List<MemoLabel>> labels, String memo, short isWait,
                                       int kindType, int doubleUnitStatus, String specification, double extraPrice, String index) {
        MenuVO menuVO = new MenuVO(menu);
        menuVO.tmpNum = num;
        menuVO.selectedMake = selectedMake;
        menuVO.specDetailId = specDetailId;
        menuVO.memo = memo;
        menuVO.labels = labels;
        menuVO.isWait = isWait;
        menuVO.kindType = kindType;
        menuVO.doubleUnitStatus = doubleUnitStatus;
        menuVO.accountNum = accountNum;
        menuVO.specification = specification;
        menuVO.extraPrice = extraPrice;
        menuVO.suitGroupVO = suitGroupVO;
        menuVO.item = item;
        menuVO.index = index;
        return menuVO;
    }

    public static MenuVO createForSuit(MenuVO menuVO, String index) {
        return MenuVO.createForSuit(menuVO.getMenu(), menuVO.getItem(), menuVO.getSuitGroupVO(),
                menuVO.getTmpNum(), menuVO.getAccountNum(), menuVO.getSpecDetailId(), menuVO.getSelectedMake(),
                menuVO.getLabels(), menuVO.getMemo(), menuVO.getIsWait(), menuVO.getKindType(),
                menuVO.getDoubleUnitStatus(), menuVO.getSpecification(), menuVO.getExtraPrice(), index);

    }

    public static MenuVO createForDispart(MenuVO menuVO) {
        return new MenuVO(menuVO.getMenu());
    }

    /**
     * `
     * 是否被用户点了（包含必选菜自动加入购物车）
     *
     * @return
     */
    public boolean isOrdered() {
        return (getSpecificationVOs() != null && !getSpecificationVOs().isEmpty())
                || getMySpecification() != null
                || compulsorySpecification != null;
    }

    /**
     * 是否应该另起一行显示
     * <br/>
     * 如果点了1份，没有点配菜，做法的，不需要换行展示
     * <br/>
     * 如果该菜点了2项，则换行展示；
     *
     * @return
     */
    public boolean shouldNewLine() {
        if (getSpecificationVOs() == null || getSpecificationVOs().isEmpty()) {
            return false;
        }

        if (getSpecificationVOs().size() == 1) {
            if (!getSpecificationVOs().get(0).hasSpecification()) {
                return false;
            }
        }

        return true;
    }

    public boolean showAddToList() {
        if (getSpecificationVOs() == null) {
            return false;
        }
        if ((getSpecificationVOs().size() == 1 && getSpecificationVOs().get(0).hasSpecification())
                || getSpecificationVOs().size() > 1) {
            return true;
        }
        return false;
    }

    public void addSpecification(CartItemVO noSpecification) {
        if (specificationVOs == null) {
            specificationVOs = new ArrayList<>();
        }
        specificationVOs.add(noSpecification);
    }

    public void addToBelongSelectMenus() {
        if (getBelongMenu() == null) {
            Log.e("MenuVO", "getBelongMenu() == null======================");
            return;
        }
        getBelongMenu().addSelectedMenu(this);
    }

    private void addSelectedMenu(MenuVO menuVO) {
        if (selectedMenus == null) {
            selectedMenus = new ArrayList<>();
        }
        if (!selectedMenus.contains(menuVO)) {
            selectedMenus.add(menuVO);
        }
    }

    public int getKindWhenUpdateCart() {
        if (menu == null) {
            return CartHelper.CartFoodKind.KIND_NORMAL_FOOD;
        }
        return MenuUtils.mapKindType(menu.getIsInclude());
    }

    public double getTmpNum() {
        return tmpNum;
    }

    public void setTmpNum(double tmpNum) {
        this.tmpNum = tmpNum;
        extraPrice = SpecificationDataMapLayer.getCombSubFoodAddPrice(menu,
                selectedMake, tmpNum, getAccountNum());
    }

    public Menu getMenu() {
        return menu;
    }

    public boolean isNoLimit() {
        return menu != null && menu.getLimitNum() <= 0;
    }

    public int getLimitNum() {
        if (menu != null) {
            return menu.getLimitNum();
        }
        return 0;
    }

    public double getStartNum() {
        if (menu != null) {
            return menu.getStartNum();
        }
        return 1;
    }

    public String getUnit() {
        if (menu != null) {
            return menu.getBuyAccount();
        }
        return "";
    }


    public String getMenuName() {
        if (menu == null || menu.getName() == null) {
            return "";
        }
        return menu.getName();
    }

    public String getMenuId() {
        if (menu != null) {
            return menu.getId();
        }
        return "";
    }

    public String getKindMenuId() {
        if (menu != null) {
            return menu.getKindMenuId();
        }
        return "";
    }

    public String getSpell() {
        if (menu != null && !TextUtils.isEmpty(menu.getSpell())) {
            return menu.getSpell();
        }
        return "";
    }

    private String getLetter() {
        if (menu == null || TextUtils.isEmpty(menu.getSpell()))
            return "";
        return menu.getSpell().substring(0, 1);
    }

    /**
     * 如果首字母不是a-z，则返回#
     *
     * @return
     */
    public String getFirstLetter() {
        String letter = getLetter();
        if (TextUtils.isEmpty(letter)) {
            return "";
        }
        if (!StringUtils.isLetter(letter)) {
            return "#";
        }
        return letter;
    }

    public double getNumFromSpec() {
        if (mySpecification != null) {
            return mySpecification.getNum();
        }
        return 0;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public CartItemVO getMySpecification() {
        return mySpecification;
    }

    public void setMySpecification(CartItemVO mySpecification) {
        this.mySpecification = mySpecification;
    }

    public List<CartItemVO> getSpecificationVOs() {
        return specificationVOs;
    }

    public void setSpecificationVOs(List<CartItemVO> specificationVOs) {
        this.specificationVOs = specificationVOs;
    }

    public MenuGroupVO getMenuGroupVO() {
        return menuGroupVO;
    }

    public void setMenuGroupVO(MenuGroupVO menuGroupVO) {
        this.menuGroupVO = menuGroupVO;
    }

    public CartItemVO getCompulsorySpecification() {
        return compulsorySpecification;
    }

    public void setCompulsorySpecification(CartItemVO compulsorySpecification) {
        this.compulsorySpecification = compulsorySpecification;
    }

    public CartItemVO getFirstSpec() {
        if (getSpecificationVOs() != null && getSpecificationVOs().size() > 0) {
            return getSpecificationVOs().get(0);
        }
        return null;
    }


    public void setAggregateNum(double aggregateNum) {
        this.aggregateNum = aggregateNum;
    }

    public double getAggregateNum() {
        if (aggregateNum != 0) {
            return aggregateNum;
        }
        if (mySpecification != null) {
            aggregateNum = mySpecification.getNum();
        }
        return aggregateNum;
    }

    public boolean hasOnlyOneSpec() {
        return specificationVOs != null && specificationVOs.size() == 1;
    }

    public SuitGroupVO getSuitGroupVO() {
        return suitGroupVO;
    }

    public void setSuitGroupVO(SuitGroupVO suitGroupVO) {
        this.suitGroupVO = suitGroupVO;
    }

    public Recipe getSelectedMake() {
        return selectedMake;
    }

    public void setSelectedMake(Recipe selectedMake) {
        this.selectedMake = selectedMake;
    }

    /**
     * 是否是全部必选（用于套餐）
     */
    public boolean isRequired() {
        return suitGroupVO != null && suitGroupVO.isRequired();
    }

    public String getSpecDetailName() {
        if (item != null) {
            return item.getSpecDetailName();
        }
        if (menu != null) {
            return menu.getSpecDetailName();
        }
        return "";
    }

    public String getSpecDetailId() {
        if (specDetailId == null) {
            if (item != null) {
                return specDetailId = item.getSpecDetailId();
            }
            if (menu != null) {
                return specDetailId = menu.getSpecDetailId();
            }
        }
        return specDetailId;
    }

    public String getSuitGroupName() {
        if (suitGroupVO != null) {
            return suitGroupVO.getGroupName();
        }
        return "";
    }


    public int getPerNum() {
        if (menu != null) {
            return menu.getPerNum();
        }
        return 0;
    }

    public String getBuyAccount() {
        if (menu != null) {
            return menu.getBuyAccount();
        }
        return "";
    }

    public boolean hasSpecVos() {
        return specificationVOs != null && specificationVOs.size() > 0;
    }

    public boolean hasSelectedMenu() {
        return selectedMenus != null && selectedMenus.size() > 0;
    }

    public int getSelectedMenuSize() {
        if (hasSelectedMenu()) {
            return selectedMenus.size();
        }
        return 0;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getIndex() {
        if (index == null) {
            return index = CartItemVO.createIndex();
        }
        return index;
    }

    public String getMakeId() {
        if (selectedMake != null) {
            return selectedMake.getMakeId();
        }
        return null;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setSpecDetailId(String specDetailId) {
        this.specDetailId = specDetailId;
    }

    public short getIsWait() {
        return isWait;
    }

    public void setIsWait(short isWait) {
        this.isWait = isWait;
    }

    public Map<String, List<MemoLabel>> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, List<MemoLabel>> labels) {
        this.labels = labels;
    }

    public int getKindType() {
        return kindType;
    }

    public double getSuitSubInputNum() {
        return suitSubInputNum;
    }

    public void setSuitSubInputNum(double suitSubInputNum) {
        this.suitSubInputNum = suitSubInputNum;
    }

    public void setKindType(int kindType) {
        this.kindType = kindType;
    }

    public int getDoubleUnitStatus() {
        return doubleUnitStatus;
    }

    public void setDoubleUnitStatus(int doubleUnitStatus) {
        this.doubleUnitStatus = doubleUnitStatus;
    }

    public double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(double accountNum) {
        this.accountNum = accountNum;
    }

    public boolean isTwoAccount() {
        return menu != null && com.zmsoft.ccd.menu.business.MenuUtils.isTwoAccount(menu.getBuyAccount(), menu.getAccount());
    }

    public List<MenuVO> getSelectedMenus() {
        return selectedMenus;
    }

    public void setSelectedMenus(List<MenuVO> selectedMenus) {
        this.selectedMenus = selectedMenus;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public double getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public boolean isSelectedMenuNewLine() {
        return selectedMenuNewLine;
    }

    public void setSelectedMenuNewLine(boolean selectedMenuNewLine) {
        this.selectedMenuNewLine = selectedMenuNewLine;
    }

    public MenuVO getBelongMenu() {
        return belongMenu;
    }

    public int getBelongMenuSelectedNum() {
        if (belongMenu != null && belongMenu.getSelectedMenus() != null) {
            return belongMenu.getSelectedMenus().size();
        } else if (selectedMenus != null) {
            return selectedMenus.size();
        }
        return 0;
    }

    public void setBelongMenu(MenuVO belongMenu) {
        this.belongMenu = belongMenu;
    }

    public void removeSelfFromBelong() {
        if (belongMenu != null && belongMenu.getSelectedMenus() != null) {
            boolean f = belongMenu.getSelectedMenus().remove(this);
            Log.e("MenuVO", "remove " + f);
        }
    }

    public void resetNoSpecMenuFromBelong() {
        if (belongMenu != null) {
            belongMenu.setSelectedMenuNoSpec(null);
        }
    }


    public MenuVO getSelectedMenuNoSpec() {
        return selectedMenuNoSpec;
    }

    public void setSelectedMenuNoSpec(MenuVO selectedMenuNoSpec) {
        this.selectedMenuNoSpec = selectedMenuNoSpec;
    }

    public void setSuitGroupId(String suitGroupId) {
        this.suitGroupId = suitGroupId;
    }

    public String getSuitGroupId() {
        if (!TextUtils.isEmpty(suitGroupId)) {
            return suitGroupId;
        }
        if (item != null) {
            return item.getSuitMenuDetailId();
        }
        if (suitGroupVO != null) {
            return suitGroupVO.getGroupId();
        }
        return "";
    }

}
