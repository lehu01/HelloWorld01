package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author DangGui
 * @create 2017/6/13.
 */
public class KindPayDetailOptionDto implements Parcelable {

    /**
     * kindPayDetailOptionId
     */
    private String id;

    /**
     * 1/2 额外信息1/额外信息2
     */
    private String kindPayDetailId;

    /**
     * 顺序码
     */
    private int sortCode;

    /**
     * 选项名称
     */
    private String name;

    /**
     * 拼写
     */
    private String spell;

    /**
     * 所属实体
     */
    private String entityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKindPayDetailId() {
        return kindPayDetailId;
    }

    public void setKindPayDetailId(String kindPayDetailId) {
        this.kindPayDetailId = kindPayDetailId;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.kindPayDetailId);
        dest.writeInt(this.sortCode);
        dest.writeString(this.name);
        dest.writeString(this.spell);
        dest.writeString(this.entityId);
    }

    public KindPayDetailOptionDto() {
    }

    protected KindPayDetailOptionDto(Parcel in) {
        this.id = in.readString();
        this.kindPayDetailId = in.readString();
        this.sortCode = in.readInt();
        this.name = in.readString();
        this.spell = in.readString();
        this.entityId = in.readString();
    }

    public static final Creator<KindPayDetailOptionDto> CREATOR = new Creator<KindPayDetailOptionDto>() {
        @Override
        public KindPayDetailOptionDto createFromParcel(Parcel source) {
            return new KindPayDetailOptionDto(source);
        }

        @Override
        public KindPayDetailOptionDto[] newArray(int size) {
            return new KindPayDetailOptionDto[size];
        }
    };
}
