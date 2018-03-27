package com.zmsoft.ccd.helper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/26 10:34
 */
public class SeatHelper {

    // 支付状态
    public final static int SEAT_NO_PAY = 0; // 未付款
    public final static int SEAT_NO_PAY_ALL = 1; // 未付请
    public final static int SEAT_PAY_ALL = 2; // 已付清

    // 桌位类型
    public static final int SEAT_KIND_NODESK = -1;  // 无桌位单
    public static final int SEAT_KIND_RANDOM = 1;   // 散座
    public static final int SEAT_KIND_BOX = 2;  // 包厢
    public static final int SEAT_KIND_CARD = 3;  // 卡座

    /**
     * 设置桌子状态(列表)
     *
     * @param fromType
     * @param imageView
     */
    public static void setSeatListSateImage(int fromType, ImageView imageView) {
        switch (fromType) {
            case SEAT_NO_PAY:
                imageView.setImageResource(R.drawable.icon_seat_and_order_list_no_pay);
                break;
            case SEAT_NO_PAY_ALL:
                imageView.setImageResource(R.drawable.icon_seat_and_order_list_no_pay_all);
                break;
            case SEAT_PAY_ALL:
                imageView.setImageResource(R.drawable.icon_seat_and_order_list_pay_all);
                break;
            default:
                imageView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 设置桌子状态(列表)
     *
     * @param fromType
     * @param textView
     */
    public static void setSeatListStateText(int fromType, TextView textView) {
        switch (fromType) {
            case SEAT_NO_PAY:
                textView.setText(R.string.no_pay);
                textView.setTextColor(GlobalVars.context.getResources().getColor(R.color.order_list_red));
                break;
            case SEAT_NO_PAY_ALL:
                textView.setText(R.string.no_pay_all);
                textView.setTextColor(GlobalVars.context.getResources().getColor(R.color.order_list_orange));
                break;
            case SEAT_PAY_ALL:
                textView.setText(R.string.pay_all);
                textView.setTextColor(GlobalVars.context.getResources().getColor(R.color.order_list_green));
                break;
            default:
                textView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 根据桌位类型获取桌位名称
     *
     * @param seatKind 座位类型
     * @return 座位名称
     */
    public static String getSeatListKindText(int seatKind) {
        String result = "";
        switch (seatKind) {
            case SEAT_KIND_NODESK:
                result = "";
                break;
            case SEAT_KIND_RANDOM:
                result = GlobalVars.context.getString(R.string.msg_attention_desk_kind_random);
                break;
            case SEAT_KIND_BOX:
                result = GlobalVars.context.getString(R.string.msg_attention_desk_kind_box);
                break;
            case SEAT_KIND_CARD:
                result = GlobalVars.context.getString(R.string.msg_attention_desk_kind_card);
                break;
        }
        return result;
    }

}
