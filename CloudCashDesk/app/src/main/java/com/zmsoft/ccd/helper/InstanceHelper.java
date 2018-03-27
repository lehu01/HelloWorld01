package com.zmsoft.ccd.helper;

import android.view.View;
import android.widget.ImageView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.instance.Instance;
import com.zmsoft.ccd.lib.utils.StringUtils;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/22 19:30
 */
public class InstanceHelper {

    public static final Short KIND_NORMAL = (short) 1; // 普通菜
    public static final Short KIND_SUIT = (short) 2; // 套菜
    public static final Short KIND_SELF = (short) 3; // 自定义菜
    public static final Short KIND_SELF_SUIT = (short) 4; // 自定义套菜
    public static final Short KIND_ADDITION = (short) 5; // 加料菜
    public static final double ZERO = 0.005d;

    /**
     * 是否套菜.
     */
    public static boolean isSuit(Short kind) {
        return KIND_SUIT.equals(kind) || KIND_SELF_SUIT.equals(kind);
    }

    /**
     * 判断是否是套菜子菜.
     */
    public static boolean isSuitChild(Short kind, String parentId) {
        return !kind.equals(KIND_ADDITION) && isChild(parentId);
    }

    /**
     * 判断是否是子菜.
     */
    public static boolean isChild(String parentId) {
        return !StringUtils.isEmpty(parentId) && !"0".equals(parentId);
    }

    /**
     * 是否自定义菜.
     */
    public static boolean isSelf(Short kind) {
        return KIND_SELF.equals(kind);
    }

    public final static int INSTANCE_NO_CONFIRM = 1; // 未确认菜肴
    public final static int INSTANCE_NORMAL = 2; // 正常菜肴
    public final static int INSTANCE_EXIT = 3; // 退菜
    public final static int INSTANCE_REJECT = 104; // 拒绝
    public final static int INSTANCE_SEPERATE_ORDER = 8; // 分单
    public final static int INSTANCE_NO_DEAL_WITH = 101; // 未处理
    public final static int INSTANCE_SEND_SHOP = 102; // 下发到店
    public final static int INSTANCE_TIME_OUT = 103; // 下发超时
    public final static int INSTANCE_COOKING = 106; // 已下厨

    /**
     * 设置菜肴的状态icon显示
     *
     * @param instanceState
     * @param imageView
     */
    public static void setInstanceStateImage(int instanceState, ImageView imageView) {
        switch (instanceState) {
            case INSTANCE_NORMAL: // 正常/已审核
                imageView.setImageResource(R.drawable.icon_instance_aduit);
                break;
            case INSTANCE_EXIT: // 退菜
                imageView.setImageResource(R.drawable.icon_instance_cancel);
                break;
            case INSTANCE_REJECT: // 拒绝
            case INSTANCE_TIME_OUT: // 超时
                imageView.setImageResource(R.drawable.icon_instance_rejected);
                break;
            case INSTANCE_COOKING: // 已下厨
                imageView.setImageResource(R.drawable.icon_instance_cooking);
                break;
            default:
                imageView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 菜肴是否被拒绝
     */
    public static boolean isRejectInstance(int status) {
        if (status == INSTANCE_REJECT || status == INSTANCE_EXIT || status == INSTANCE_TIME_OUT) {
            return true;
        }
        return false;
    }

    /**
     * 是否显示菜肴操作弹窗
     *
     * @param status
     * @return
     */
    public static boolean isCanOpInstance(int status) {
        if (status == INSTANCE_REJECT || status == INSTANCE_TIME_OUT || status == INSTANCE_EXIT) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是赠菜(价格为零的主菜).
     */
    public static boolean isGive(Instance instance) {
        if (!isChild(instance.getParentId()) && instance.getPrice() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否能够赠菜操作
     */
    public static boolean isOpInsGive(Instance instance) {
        if (instance != null) {
            if (instance.getIsGive() == Base.INT_FALSE) {
                return false;
            } else if (isGive(instance)) {
                return false;
            }
        }
        return true;
    }
}
