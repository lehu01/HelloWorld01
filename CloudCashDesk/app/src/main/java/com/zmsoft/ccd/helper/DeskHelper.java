package com.zmsoft.ccd.helper;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.bean.desk.Seat;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/21 15:56.
 */

public class DeskHelper {

    /**
     * 获取桌位类型和人数的字符串，例如“（四人散座）”
     * @return
     */
    public static String getSeatDescString(int seatKind, int adviseNum){
        String seatKindString = getSeatKindString(seatKind);
        return String.format(GlobalVars.context.getResources().getString(R.string.msg_attention_desk_kind_bracket), adviseNum, seatKindString);
    }


    /**
     * 获取桌位字符串，例如“散座”
     * @param seatKind
     * @return
     */
    public static String getSeatKindString(int seatKind) {
        String seatKindString = GlobalVars.context.getResources().getString(R.string.msg_attention_desk_kind_random);
        switch (seatKind) {
            case Seat.SeatKind.DESK_KIND_RANDOM:
                seatKindString = GlobalVars.context.getResources().getString(R.string.msg_attention_desk_kind_random);
                break;
            case Seat.SeatKind.DESK_KIND_BOX:
                seatKindString = GlobalVars.context.getResources().getString(R.string.msg_attention_desk_kind_box);
                break;
            case Seat.SeatKind.DESK_KIND_CARD:
                seatKindString = GlobalVars.context.getResources().getString(R.string.msg_attention_desk_kind_card);
                break;
            default:
                break;
        }
        return seatKindString;
    }
}
