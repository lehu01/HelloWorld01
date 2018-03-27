package com.zmsoft.ccd.module.menu.menu.bean.vo;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenuHitRule;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenu;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/6/7.
 */

public class SuitMenuVO implements Parcelable {

    private SuitMenu suitMenu;

    /**
     * 该套餐份数
     */
    private double num;

    private String index;

    /**
     * 用户手动输入的备注
     */
    private String memo;

    private ItemVo itemVo;

    private short isWait;

    private int hasChanged;

    /**
     * 加价规则（需要单独请求接口）
     */
    private List<SuitMenuHitRule> suitMenuHitRules;

    public SuitMenuVO(SuitMenu suitMenu, ItemVo itemVo) {
        this.suitMenu = suitMenu;
        this.itemVo = itemVo;
        if (itemVo != null) {
            isWait = itemVo.getIsWait();
            memo = itemVo.getMemo();
        }
    }

    public String getMenuId() {
        if (suitMenu != null) {
            return suitMenu.getMenuId();
        }
        return "";
    }

    public String getMenuName() {
        if (suitMenu != null) {
            return suitMenu.getName();
        }
        return "";
    }

    public double getMenuPrice() {
        if (suitMenu != null) {
            return suitMenu.getPrice();
        }
        return 0;
    }

    public String getDesc() {
        if (suitMenu != null) {
            return suitMenu.getDesc();
        }
        return "";
    }

    /**
     * 如果没有则创建新的index
     */
    public String getIndex() {
        if (index == null) {
            if (itemVo != null) {
                return index = itemVo.getIndex();
            }
            return index = CartItemVO.createIndex();
        }
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public SuitMenu getSuitMenu() {
        return suitMenu;
    }

    public void setSuitMenu(SuitMenu suitMenu) {
        this.suitMenu = suitMenu;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getMemo() {
        if (TextUtils.isEmpty(memo) && itemVo != null) {
            memo = itemVo.getMemo();
        }
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public ItemVo getItemVo() {
        return itemVo;
    }

    public void setItemVo(ItemVo itemVo) {
        this.itemVo = itemVo;
    }

    public short getIsWait() {
        return isWait;
    }

    public void setIsWait(short isWait) {
        this.isWait = isWait;
    }


    public List<SuitMenuHitRule> getSuitMenuHitRules() {
        return suitMenuHitRules;
    }

    public void setSuitMenuHitRules(List<SuitMenuHitRule> suitMenuHitRules) {
        this.suitMenuHitRules = suitMenuHitRules;
    }

    public int getHasChanged() {
        return hasChanged;
    }

    public void setHasChanged(int hasChanged) {
        this.hasChanged = hasChanged;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.suitMenu, flags);
        dest.writeDouble(this.num);
        dest.writeString(this.index);
        dest.writeString(this.memo);
        dest.writeSerializable(this.itemVo);
        dest.writeInt(this.isWait);
        dest.writeTypedList(this.suitMenuHitRules);
        dest.writeInt(this.hasChanged);
    }

    protected SuitMenuVO(Parcel in) {
        this.suitMenu = in.readParcelable(SuitMenu.class.getClassLoader());
        this.num = in.readDouble();
        this.index = in.readString();
        this.memo = in.readString();
        this.itemVo = (ItemVo) in.readSerializable();
        this.isWait = (short) in.readInt();
        this.suitMenuHitRules = in.createTypedArrayList(SuitMenuHitRule.CREATOR);
        this.hasChanged = in.readInt();
    }

    public static final Parcelable.Creator<SuitMenuVO> CREATOR = new Parcelable.Creator<SuitMenuVO>() {
        @Override
        public SuitMenuVO createFromParcel(Parcel source) {
            return new SuitMenuVO(source);
        }

        @Override
        public SuitMenuVO[] newArray(int size) {
            return new SuitMenuVO[size];
        }
    };
}
