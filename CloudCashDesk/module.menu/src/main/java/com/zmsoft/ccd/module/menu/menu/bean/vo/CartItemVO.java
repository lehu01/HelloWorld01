package com.zmsoft.ccd.module.menu.menu.bean.vo;

import android.text.TextUtils;

import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.MemoLabel;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.helper.SpecificationDataMapLayer;

import java.util.List;
import java.util.Map;

/**
 * Description：购物车数据（规格、做法或加料，或者是套餐则新增一行展示）
 * <br/>
 * Created by kumu on 2017/4/13.
 */

public class CartItemVO {

    private MenuVO menuVO;

    private ItemVo itemVo;

    /**
     * 哪个用户点的
     */
    private String memberId;

    /**
     * 所属于的菜，如果必要可以把Menu包含进来
     */
    private String menuId;


    /**
     * 用户点的规格、做法、加料、备注
     */
    private String specification;

    /**
     * 加价
     */
    private double extraPrice;

    /**
     * 点的份数
     */
    private double num;

    /**
     * 结账份数
     */
    private double accountNum;

    /**
     * 保存用户临时输入的数量，加入购物车成功后设置给num
     */
    private double tmpNum;

    /**
     * 点该菜的时间（用来排序）
     */
    private long orderTime;


    /**
     * 加料列表
     */
    private List<CartItem> childCartVos;

    /**
     * 做法Id
     */
    private String makeId;
    /**
     * 规格Id
     */
    private String specId;
    /**
     * 点菜单位
     */
    private String unit;
    /**
     * 结账单位
     */
    private String accountUnit;

    private String index;

    /**
     * 用户输入的备注（用于修改的时候传参）
     */
    private String memo;
    /**
     * 用户选择的备注（用于修改的时候传参）
     */
    private Map<String, List<MemoLabel>> labels;


    /**
     * 展示用的num
     */
    private String _showNum;
    /**
     * 展示用的extraPrice
     */
    private String _showExtraPrice;


    public String getMemo() {
        if (itemVo != null) {
            return itemVo.getMemo();
        }
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Map<String, List<MemoLabel>> getLabels() {
        if (itemVo != null) {
            return itemVo.getLabels();
        }
        return labels;
    }

    public void setLabels(Map<String, List<MemoLabel>> labels) {
        this.labels = labels;
    }

    public String getShowExtraPrice() {
        if (!TextUtils.isEmpty(_showExtraPrice)) {
            return _showExtraPrice;
        }
        return _showExtraPrice = NumberUtils.trimPointIfZero(extraPrice);
    }

    public void setShowExtraPrice(String _showExtraPrice) {
        this._showExtraPrice = _showExtraPrice;
    }

    public String getShowNum() {

        if (!TextUtils.isEmpty(_showNum)) {
            return _showNum;
        }
        return _showNum = NumberUtils.trimPointIfZero(num);
    }

    public void setShowNum(String _num) {
        this._showNum = _num;
    }

    public CartItemVO(ItemVo itemVo) {
        this.itemVo = itemVo;
    }

    /**
     * @see CartItemVO#createEmpty(String, String, int, long)
     */
    public CartItemVO(String userId, String menuId, String specification, double extraPrice, int orderCount, long orderTime) {
        this.memberId = userId;
        this.menuId = menuId;
        this.specification = specification;
        this.extraPrice = extraPrice;
        this.num = orderCount;
        this.orderTime = orderTime;
    }

    /**
     * 没有<b>规格、做法或加料</b>
     */
    public static CartItemVO createEmpty(String userId, String menuId, int orderCount, long orderTime) {
        return new CartItemVO(userId, menuId, null, 0, orderCount, orderTime);
    }

    public String getMenuKindId() {
        if (itemVo != null && !TextUtils.isEmpty(itemVo.getKindMenuId())) {
            return itemVo.getKindMenuId();
        }
        return "";
    }

    /**
     * 该购物车记录是必选商品（这个不能修改）
     *
     * @return boolean
     */
    public boolean isCompulsory() {
        return (itemVo != null && itemVo.getIsCompulsory());

    }

    public boolean isCustomFood() {
        return itemVo != null && itemVo.getKind() == CartHelper.CartFoodKind.KIND_CUSTOM_FOOD;
    }

    public String getMenuName() {
        if (menuVO != null) {
            return menuVO.getMenuName();
        }
        if (itemVo != null) {
            return itemVo.getName();
        }
        return "";
    }

    public boolean isTwoAccount() {
        return itemVo != null && itemVo.isTwoAccount();
    }

    public boolean hasSpecification() {
        return !TextUtils.isEmpty(getSpecification());
    }

    public boolean isSelf() {
        return UserHelper.getMemberId().equals(getMemberId());
    }

    public double getTmpNum() {
        return tmpNum;
    }

    public void setTmpNum(double tmpNum) {
        this.tmpNum = tmpNum;
    }

    public MenuVO getMenuVO() {
        return menuVO;
    }

    public void setMenuVO(MenuVO menuVO) {
        this.menuVO = menuVO;
    }

    public int getPerNum() {
        if (menuVO != null) {
            return menuVO.getPerNum();
        }
        return 0;
    }

    public List<CartItem> getChildCartVos() {
        if (childCartVos == null && itemVo != null) {
            return childCartVos = SpecificationDataMapLayer.convertToCartItems(itemVo.getChildItems());
        }
        return childCartVos;
    }

    public void setChildCartVos(List<CartItem> childCartVos) {
        this.childCartVos = childCartVos;
    }

    public String getMakeId() {
        if (itemVo != null) {
            return itemVo.getMakeId();
        }
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public String getSpecId() {
        if (itemVo != null) {
            return itemVo.getSpecDetailId();
        }
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public CartItemVO(MenuVO menuVO) {
        this.menuVO = menuVO;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setExtraPrice(double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public String getMenuId() {
        if (itemVo != null) {
            return itemVo.getMenuId();
        }
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     * 用户点的规格、做法、加料、备注
     */
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public double getExtraPrice() {
        return extraPrice;
    }

    /**
     * 起点分数
     *
     * @return int default is 1
     */
    public int getStartNum() {
        if (itemVo != null) {
            return itemVo.getStartNum();
        }
        return 1;
    }

    public boolean hasStartNum() {
        return itemVo != null && itemVo.getStartNum() > 1;
    }

    public double getNum() {
        if (itemVo != null) {
            return itemVo.getNum();
        }
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public String getUnit() {
        if (itemVo != null) {
            return itemVo.getUnit();
        } else if (menuVO != null) {
            return menuVO.getUnit();
        }
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAccountUnit() {
        if (itemVo != null) {
            return itemVo.getAccountUnit();
        }
        return accountUnit;
    }

    public void setAccountUnit(String accountUnit) {
        this.accountUnit = accountUnit;
    }

    public String getIndex() {
        if (itemVo != null) {
            return itemVo.getIndex();
        }
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }


    public static String createIndex() {
        return UserHelper.getEntityId() + StringUtils.getRandomString(24);
    }

    public ItemVo getItemVo() {
        return itemVo;
    }

    public void setItemVo(ItemVo itemVo) {
        this.itemVo = itemVo;
    }


    public double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(double accountNum) {
        this.accountNum = accountNum;
    }
}
