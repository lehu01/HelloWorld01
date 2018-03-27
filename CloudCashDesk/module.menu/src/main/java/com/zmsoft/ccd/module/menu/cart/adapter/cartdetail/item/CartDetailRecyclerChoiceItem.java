package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item;

import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexItem;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailRecyclerChoiceItem {
    /**
     * 因为CartDetailRecyclerChoiceItem用于多种不同类型的模块，比如规格、做法、口味等，
     * 需要一个key来区分这几个模块，方便调用修改购物车接口时，获取到相应模块下被用户选中的item，
     * 其中规格、做法的key是固定的，口味的key需要根据服务端返回的口味分类ID来充当key
     *
     * @see ChoiceItemKey
     */
    private String key;
    /**
     * 分类名称（规格、做法等）
     */
    private String mName;
    /**
     * 是否必选
     */
    private boolean isMustSelect;
    /**
     * 供选择的ITEM
     */
    private List<CustomFlexItem> mCustomFlexItemList;
    /**
     * 被选中的ITEM
     */
    private List<CustomFlexItem> mCheckedFlexItemList;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isMustSelect() {
        return isMustSelect;
    }

    public void setMustSelect(boolean mustSelect) {
        isMustSelect = mustSelect;
    }

    public List<CustomFlexItem> getCustomFlexItemList() {
        return mCustomFlexItemList;
    }

    public void setCustomFlexItemList(List<CustomFlexItem> customFlexItemList) {
        mCustomFlexItemList = customFlexItemList;
    }

    public List<CustomFlexItem> getCheckedFlexItemList() {
        return mCheckedFlexItemList;
    }

    public void setCheckedFlexItemList(List<CustomFlexItem> checkedFlexItemList) {
        mCheckedFlexItemList = checkedFlexItemList;
    }

    /**
     * 规格、做法的key是固定的
     */
    public static class ChoiceItemKey {
        //规格的key
        public static String KEY_SPEC = "key_spec";
        //做法的key
        public static String KEY_MAKE = "key_make";
    }
}
