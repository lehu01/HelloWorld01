package com.zmsoft.ccd.module.menu.menu.bean;


import com.zmsoft.ccd.lib.widget.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/17.
 */

public class MenuCategory implements IPickerViewData {


    /**
     * 操作时间
     */
    private long opTime;

    private long createTime;

    private String parentKindMenuName;

    private String groupKindId;

    /**
     * 数据来源标记
     */
    private int ownerType;


    private String entityId;

    /**
     * 提成方式
     */
    private int deduct;


    private String sortCode;

    private String code;

    private int consume;

    private String id;

    private String groupKindMenuName;

    private String parentId;

    private String _id;

    private String name;

    private int lastVer;

    private List<MenuKindTaste> menuKindTastes;

    private String wareHouseId;

    private int deductKind;

    private int isInclude;

    private int isValid;

    private String innerCode;

    public MenuCategory() {
    }

    public MenuCategory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getOpTime() {
        return opTime;
    }

    public void setOpTime(long opTime) {
        this.opTime = opTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getParentKindMenuName() {
        return parentKindMenuName;
    }

    public void setParentKindMenuName(String parentKindMenuName) {
        this.parentKindMenuName = parentKindMenuName;
    }

    public String getGroupKindId() {
        return groupKindId;
    }

    public void setGroupKindId(String groupKindId) {
        this.groupKindId = groupKindId;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getDeduct() {
        return deduct;
    }

    public void setDeduct(int deduct) {
        this.deduct = deduct;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupKindMenuName() {
        return groupKindMenuName;
    }

    public void setGroupKindMenuName(String groupKindMenuName) {
        this.groupKindMenuName = groupKindMenuName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLastVer() {
        return lastVer;
    }

    public void setLastVer(int lastVer) {
        this.lastVer = lastVer;
    }

    public List<MenuKindTaste> getMenuKindTastes() {
        return menuKindTastes;
    }

    public void setMenuKindTastes(List<MenuKindTaste> menuKindTastes) {
        this.menuKindTastes = menuKindTastes;
    }

    public String getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(String wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public int getDeductKind() {
        return deductKind;
    }

    public void setDeductKind(int deductKind) {
        this.deductKind = deductKind;
    }

    public int getIsInclude() {
        return isInclude;
    }

    public void setIsInclude(int isInclude) {
        this.isInclude = isInclude;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }

    class MenuKindTaste {

        private String id;
        private String _id;

        /**
         * 操作时间
         */
        private long opTime;

        private long createTime;

        private int ownerType;

        private int sortCode;

        private String entityId;

        private String tasteId;


        private String tasteName;

        private String kindTasteName;

        private String kindTasteId;

        private int lastVer;

        private String kindMenuId;

        private int isValid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public long getOpTime() {
            return opTime;
        }

        public void setOpTime(long opTime) {
            this.opTime = opTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(int ownerType) {
            this.ownerType = ownerType;
        }

        public int getSortCode() {
            return sortCode;
        }

        public void setSortCode(int sortCode) {
            this.sortCode = sortCode;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getTasteId() {
            return tasteId;
        }

        public void setTasteId(String tasteId) {
            this.tasteId = tasteId;
        }

        public String getTasteName() {
            return tasteName;
        }

        public void setTasteName(String tasteName) {
            this.tasteName = tasteName;
        }

        public String getKindTasteName() {
            return kindTasteName;
        }

        public void setKindTasteName(String kindTasteName) {
            this.kindTasteName = kindTasteName;
        }

        public String getKindTasteId() {
            return kindTasteId;
        }

        public void setKindTasteId(String kindTasteId) {
            this.kindTasteId = kindTasteId;
        }

        public int getLastVer() {
            return lastVer;
        }

        public void setLastVer(int lastVer) {
            this.lastVer = lastVer;
        }

        public String getKindMenuId() {
            return kindMenuId;
        }

        public void setKindMenuId(String kindMenuId) {
            this.kindMenuId = kindMenuId;
        }

        public int getIsValid() {
            return isValid;
        }

        public void setIsValid(int isValid) {
            this.isValid = isValid;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuCategory category = (MenuCategory) o;

        return id.equals(category.id);

    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return id.hashCode();
    }
}
