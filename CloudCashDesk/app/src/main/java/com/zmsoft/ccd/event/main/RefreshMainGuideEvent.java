package com.zmsoft.ccd.event.main;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/30 15:38.
 */

public class RefreshMainGuideEvent {

    public interface VIEW_TYPE {
        int SHOP_LIMIT = 0;             // 商店试用view
        int DELIVERY = 1;               // 外卖消息view
        int ONLINE_TAKEOUT_ITEM = 2;    // “在线外卖”item
    }

    private boolean isShow;
    private int viewType;

    public RefreshMainGuideEvent(boolean isShow, int viewType) {
        this.isShow = isShow;
        this.viewType = viewType;
    }

    public boolean isShow() {
        return isShow;
    }

    public int getViewType() {
        return viewType;
    }
}
