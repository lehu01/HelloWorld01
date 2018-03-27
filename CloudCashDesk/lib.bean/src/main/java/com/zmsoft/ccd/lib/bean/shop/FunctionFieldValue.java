package com.zmsoft.ccd.lib.bean.shop;

import com.zmsoft.ccd.lib.bean.Base;

import java.util.List;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/28 16:38
 */
public class FunctionFieldValue extends Base {

    private int opTime;
    private int createTime;
    private String entityId;
    private String code;
    private String type;
    private int isDisplay;
    private int isLinkageField;
    private String description;
    private String name;
    private String value;
    private int lastVer;
    private String valueType;
    private String example;
    private String defaultValue;
    private int isValid;
    private int isEditable;
    private List<String> optionalValue;

    public int getOpTime() {
        return opTime;
    }

    public void setOpTime(int opTime) {
        this.opTime = opTime;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(int isDisplay) {
        this.isDisplay = isDisplay;
    }

    public int getIsLinkageField() {
        return isLinkageField;
    }

    public void setIsLinkageField(int isLinkageField) {
        this.isLinkageField = isLinkageField;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLastVer() {
        return lastVer;
    }

    public void setLastVer(int lastVer) {
        this.lastVer = lastVer;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public int getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(int isEditable) {
        this.isEditable = isEditable;
    }

    public List<String> getOptionalValue() {
        return optionalValue;
    }

    public void setOptionalValue(List<String> optionalValue) {
        this.optionalValue = optionalValue;
    }
}
