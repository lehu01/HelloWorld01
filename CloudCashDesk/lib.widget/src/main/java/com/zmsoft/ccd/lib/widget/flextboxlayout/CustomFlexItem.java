package com.zmsoft.ccd.lib.widget.flextboxlayout;

/**
 * @author DangGui
 * @create 2017/4/18.
 */

public class CustomFlexItem {
    /**
     * 唯一标示，代表该item的id，可以是菜肴id等
     */
    private String id;

    /**
     * 名称
     */
    private String name;
    /**
     * 额外属性（比如 “一次性加价10元”)
     */
    private String extras;

    /**
     * 是否选中
     */
    private boolean checked;

    /**
     * 能否修改（有些场景是ITEM默认被选中，且不可修改）<br />
     * 默认是可修改，false
     */
    private boolean unModifyAble;

    private Object data;

    public CustomFlexItem(String id, String name) {
        this(id, name, false);
    }

    public CustomFlexItem(String id, String name, boolean checked) {
        this(id, name, null, checked);
    }

    public CustomFlexItem(String id, String name, String extras, boolean checked) {
        this.id = id;
        this.name = name;
        this.extras = extras;
        this.checked = checked;
    }

    public CustomFlexItem(String id, String name, String extras, Object data, boolean checked) {
        this.id = id;
        this.name = name;
        this.extras = extras;
        this.data = data;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isUnModifyAble() {
        return unModifyAble;
    }

    public void setUnModifyAble(boolean unModifyAble) {
        this.unModifyAble = unModifyAble;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
