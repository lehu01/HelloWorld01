package com.zmsoft.ccd.receipt.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/6/13.
 */
public class KindPayDetailDto implements Parcelable {

    private String id;

    /**
     * 付款类型ID
     */
    private String kindPayId;

    /**
     * 顺序码
     */
    private int sortCode;

    /**
     * 表示付款类型是否需要输入额外信息，如信用卡付款，需要输入卡号；如果字段为空，则不需要输入额外信息
     */
    private String name;

    /**
     * 0自由录入/1从列表中选择(列表来源KindPayExtraInfoOption)/2可选择可录入
     */
    private int inputMode;

    /**
     * 所属实体
     */
    private String entityId;

    private List<KindPayDetailOptionDto> kindPayDetailOptionDtos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKindPayId() {
        return kindPayId;
    }

    public void setKindPayId(String kindPayId) {
        this.kindPayId = kindPayId;
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

    public int getInputMode() {
        return inputMode;
    }

    public void setInputMode(int inputMode) {
        this.inputMode = inputMode;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<KindPayDetailOptionDto> getKindPayDetailOptionDtos() {
        return kindPayDetailOptionDtos;
    }

    public void setKindPayDetailOptionDtos(List<KindPayDetailOptionDto> kindPayDetailOptionDtos) {
        this.kindPayDetailOptionDtos = kindPayDetailOptionDtos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.kindPayId);
        dest.writeInt(this.sortCode);
        dest.writeString(this.name);
        dest.writeInt(this.inputMode);
        dest.writeString(this.entityId);
        dest.writeTypedList(this.kindPayDetailOptionDtos);
    }

    public KindPayDetailDto() {
    }

    protected KindPayDetailDto(Parcel in) {
        this.id = in.readString();
        this.kindPayId = in.readString();
        this.sortCode = in.readInt();
        this.name = in.readString();
        this.inputMode = in.readInt();
        this.entityId = in.readString();
        this.kindPayDetailOptionDtos = in.createTypedArrayList(KindPayDetailOptionDto.CREATOR);
    }

    public static final Creator<KindPayDetailDto> CREATOR = new Creator<KindPayDetailDto>() {
        @Override
        public KindPayDetailDto createFromParcel(Parcel source) {
            return new KindPayDetailDto(source);
        }

        @Override
        public KindPayDetailDto[] newArray(int size) {
            return new KindPayDetailDto[size];
        }
    };
}
