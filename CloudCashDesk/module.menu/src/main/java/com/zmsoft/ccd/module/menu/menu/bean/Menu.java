package com.zmsoft.ccd.module.menu.menu.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/13.
 */

public class Menu extends Food implements Parcelable {

    private String id;

    /**
     * 规格项--有规格，没有设置的话,默认选择第一个
     */
    private String specDetailName;

    private String specDetailId;

    /**
     * 图片所在的服务器
     */
    private String server;

    /**
     * 图片路径
     */
    private String path;

    /**
     * 菜类ID
     */
    private String kindMenuId;
    /**
     * 照片附件
     */
    private String attachmentId;

    /**
     * 打包盒价格（兼容本店外卖前版本）
     */
    private double packingBoxPrice;

    /**
     * 拼写（简拼）
     */
    private String spell;
    /**
     * 拼写2
     */
    private String spell2;

    /**
     * 是否指定：默认0
     * 可以根据值从大到小排序
     */
    private int showTop;

    /**
     * 规格ID
     */
    private String specId;

    /**
     * 提成方式
     */
    private int deduct;

    /**
     * 提成比例
     */
    private String deductKind;

    /**
     * 打包
     */
    private Menu packingBox;

    /**
     * 累加步长
     */
    private int stepLength;
    /**
     * 仅套餐中显示
     */
    private boolean mealOnly;

    /**
     * 是否有效
     */
    private int isValid;
    /**
     * 打包盒个数
     */
    private int packingBoxNum;

    /**
     * 是否需要出单
     */
    private int isPrint;

    /**
     * 点菜时是否允许修改单价
     */
    private int isChangePrice;

    /**
     * 记录创建时间对应的属性
     */
    private long createTime;

    /**
     * 操作时间对应的属性
     */
    private long opTime;

    /**
     * 是否是必选商品
     */
    private int isForceMenu;

    /**
     * 图片完整路径
     * use {@link Food#imagePath} instead
     */
    @Deprecated
    private String menuPicUrl;

    public int getIsForceMenu() {
        return isForceMenu;
    }

    public void setIsForceMenu(int isForceMenu) {
        this.isForceMenu = isForceMenu;
    }

    public boolean isForceMenu() {
        return isForceMenu == 1;
    }

    public String getSpecDetailId() {
        return specDetailId;
    }

    public void setSpecDetailId(String specDetailId) {
        this.specDetailId = specDetailId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecDetailName() {
        return specDetailName;
    }

    public void setSpecDetailName(String specDetailName) {
        this.specDetailName = specDetailName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKindMenuId() {
        return kindMenuId;
    }

    public void setKindMenuId(String kindMenuId) {
        this.kindMenuId = kindMenuId;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public double getPackingBoxPrice() {
        return packingBoxPrice;
    }

    public void setPackingBoxPrice(double packingBoxPrice) {
        this.packingBoxPrice = packingBoxPrice;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getSpell2() {
        return spell2;
    }

    public void setSpell2(String spell2) {
        this.spell2 = spell2;
    }

    public int getShowTop() {
        return showTop;
    }

    public void setShowTop(int showTop) {
        this.showTop = showTop;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public int getDeduct() {
        return deduct;
    }

    public void setDeduct(int deduct) {
        this.deduct = deduct;
    }

    public String getDeductKind() {
        return deductKind;
    }

    public void setDeductKind(String deductKind) {
        this.deductKind = deductKind;
    }

    public Menu getPackingBox() {
        return packingBox;
    }

    public void setPackingBox(Menu packingBox) {
        this.packingBox = packingBox;
    }

    public int getStepLength() {
        return stepLength;
    }

    public void setStepLength(int stepLength) {
        this.stepLength = stepLength;
    }

    public boolean isMealOnly() {
        return mealOnly;
    }

    public void setMealOnly(boolean mealOnly) {
        this.mealOnly = mealOnly;
    }

    public int getPackingBoxNum() {
        return packingBoxNum;
    }

    public void setPackingBoxNum(int packingBoxNum) {
        this.packingBoxNum = packingBoxNum;
    }

    public int getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(int isPrint) {
        this.isPrint = isPrint;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getOpTime() {
        return opTime;
    }

    public void setOpTime(long opTime) {
        this.opTime = opTime;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public int getIsChangePrice() {
        return isChangePrice;
    }

    public void setIsChangePrice(int isChangePrice) {
        this.isChangePrice = isChangePrice;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    /**
     * 完整的图片地址
     *
     * @return
     */
    public String getMenuPicUrl() {
        return TextUtils.isEmpty(menuPicUrl)? imagePath : menuPicUrl;
    }

    public Menu() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.specDetailName);
        dest.writeString(this.specDetailId);
        dest.writeString(this.server);
        dest.writeString(this.path);
        dest.writeString(this.kindMenuId);
        dest.writeString(this.attachmentId);
        dest.writeDouble(this.packingBoxPrice);
        dest.writeString(this.spell);
        dest.writeString(this.spell2);
        dest.writeInt(this.showTop);
        dest.writeString(this.specId);
        dest.writeInt(this.deduct);
        dest.writeString(this.deductKind);
        dest.writeParcelable(this.packingBox, flags);
        dest.writeInt(this.stepLength);
        dest.writeByte(this.mealOnly ? (byte) 1 : (byte) 0);
        dest.writeInt(this.isValid);
        dest.writeInt(this.packingBoxNum);
        dest.writeInt(this.isPrint);
        dest.writeInt(this.isChangePrice);
        dest.writeLong(this.createTime);
        dest.writeLong(this.opTime);
        dest.writeInt(this.isForceMenu);
        dest.writeString(this.menuPicUrl);
    }

    protected Menu(Parcel in) {
        super(in);
        this.id = in.readString();
        this.specDetailName = in.readString();
        this.specDetailId = in.readString();
        this.server = in.readString();
        this.path = in.readString();
        this.kindMenuId = in.readString();
        this.attachmentId = in.readString();
        this.packingBoxPrice = in.readDouble();
        this.spell = in.readString();
        this.spell2 = in.readString();
        this.showTop = in.readInt();
        this.specId = in.readString();
        this.deduct = in.readInt();
        this.deductKind = in.readString();
        this.packingBox = in.readParcelable(Menu.class.getClassLoader());
        this.stepLength = in.readInt();
        this.mealOnly = in.readByte() != 0;
        this.isValid = in.readInt();
        this.packingBoxNum = in.readInt();
        this.isPrint = in.readInt();
        this.isChangePrice = in.readInt();
        this.createTime = in.readLong();
        this.opTime = in.readLong();
        this.isForceMenu = in.readInt();
        this.menuPicUrl = in.readString();
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel source) {
            return new Menu(source);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };
}
