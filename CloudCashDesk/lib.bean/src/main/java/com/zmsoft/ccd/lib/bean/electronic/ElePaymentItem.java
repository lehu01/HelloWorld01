package com.zmsoft.ccd.lib.bean.electronic;

/**
 * @author DangGui
 * @create 2017/8/19.
 */

public class ElePaymentItem {
    /**
     * ITEM类型
     *
     * @see ItemType
     */
    private int type;
    private ElePaymentNormalItem mElePaymentNormalItem;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ElePaymentNormalItem getElePaymentNormalItem() {
        return mElePaymentNormalItem;
    }

    public void setElePaymentNormalItem(ElePaymentNormalItem elePaymentNormalItem) {
        mElePaymentNormalItem = elePaymentNormalItem;
    }

    public class ItemType {
        public static final int TYPE_NORMAL = 1;
        public static final int TYPE_JAG_UP = 2;
        public static final int TYPE_JAG_DOWN = 3;
        public static final int TYPE_JAG_WHOLE = 4;
    }
}
