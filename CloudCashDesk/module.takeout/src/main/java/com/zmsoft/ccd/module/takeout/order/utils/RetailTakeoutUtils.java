package com.zmsoft.ccd.module.takeout.order.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.takeout.bean.OrderListRequest;
import com.zmsoft.ccd.takeout.bean.Takeout;
import com.zmsoft.ccd.takeout.bean.TakeoutConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.zmsoft.ccd.takeout.bean.TakeoutConstants.OrderStatus.DELIVERING;
import static com.zmsoft.ccd.takeout.bean.TakeoutConstants.OrderStatus.WAITING_DELIVERY;
import static com.zmsoft.ccd.takeout.bean.TakeoutConstants.OrderStatus.WAITING_DISPATCH;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/23.
 */

public class RetailTakeoutUtils {


    @StringRes
    public static int getStatusShowName(Takeout takeout) {
        switch (takeout.getStatus()) {
            case TakeoutConstants.OrderStatus.UN_COOK:
                return R.string.module_takeout_un_cook;
            case WAITING_DISPATCH:
                return takeout.getSendType() == TakeoutConstants.SendType.SELF_TAKE ?
                        R.string.module_takeout_retail_waiting_take_by_self :
                        R.string.module_takeout_waiting_dispatch;
            case WAITING_DELIVERY:
                return R.string.module_takeout_waiting_delivery;
            case DELIVERING:
                return R.string.module_takeout_waiting_delivering;
            case TakeoutConstants.OrderStatus.ARRIVED:
                return R.string.module_takeout_arrived;
            case TakeoutConstants.OrderStatus.DELIVERY_EXCEPTION:
                return R.string.module_takeout_delivery_exception;
        }
        return R.string.module_takeout_status_unknown;
    }

    @ColorRes
    public static int getStatusNameColor(Takeout takeout) {
        switch (takeout.getStatus()) {
            case TakeoutConstants.OrderStatus.ARRIVED://已送达
                return R.color.module_takeout_status_name_arrived;
            case TakeoutConstants.OrderStatus.DELIVERING://配送中
                return R.color.module_takeout_status_name_delivering;
            default:
                return R.color.module_takeout_status_name_other;
        }
    }

    @StringRes
    public static int getOrderFromShowName(Takeout takeout) {
        switch (takeout.getOrderFrom()) {
            case TakeoutConstants.OrderFrom.XIAOER:
                return R.string.module_takeout_order_from_name_xiaoer;
            case TakeoutConstants.OrderFrom.MEITUAN:
                return R.string.module_takeout_order_from_name_meituan;
            case TakeoutConstants.OrderFrom.BAIDU:
                return R.string.module_takeout_order_from_name_baidu;
            case TakeoutConstants.OrderFrom.ERLEME:
                return R.string.module_takeout_order_from_name_eleme;
            case TakeoutConstants.OrderFrom.WEIDIAN:
                return R.string.module_takeout_order_from_name_weidian;
        }
        return R.string.module_takeout_status_unknown;
    }


    @StringRes
    public static int getHorseManTip(Takeout takeout, String courierName) {
        switch (takeout.getStatus()) {
            case TakeoutConstants.OrderStatus.WAITING_DISPATCH:
                if (takeout.getSendType() != TakeoutConstants.SendType.SELF_TAKE) {
                    return R.string.module_takeout_horse_man_received;
                }
            case TakeoutConstants.OrderStatus.WAITING_DELIVERY:
                return TextUtils.isEmpty(courierName) ? R.string.module_takeout_horse_man_waiting_receive
                        : R.string.module_takeout_horse_man_received;
            case DELIVERING:
                return R.string.module_takeout_horse_man_took;
            case TakeoutConstants.OrderStatus.ARRIVED:
                return R.string.module_takeout_horse_man_arrived;
            case TakeoutConstants.OrderStatus.DELIVERY_EXCEPTION:
                return R.string.module_takeout_horse_man_exception;
        }
        return R.string.module_takeout_horse_man_unknown;
    }

    @StringRes
    public static int getOrderNextMenu(Takeout takeout) {
        switch (takeout.getStatus()) {
            case TakeoutConstants.OrderStatus.UN_COOK:
                return R.string.module_takeout_order_send_kitchen;
            case TakeoutConstants.OrderStatus.WAITING_DISPATCH:
                return takeout.getSendType() == TakeoutConstants.SendType.SELF_TAKE
                        ? R.string.module_takeout_retail_order_self_took
                        : R.string.module_takeout_order_dispatch;
            case TakeoutConstants.OrderStatus.WAITING_DELIVERY:
                return R.string.module_takeout_order_dispatch_cancel;
            case DELIVERING:
                return R.string.module_takeout_order_arrived;
            case TakeoutConstants.OrderStatus.ARRIVED:
            case TakeoutConstants.OrderStatus.DELIVERY_EXCEPTION:
                return R.string.module_takeout_order_checkout;
        }
        return R.string.module_takeout_status_unknown;
    }


    @DrawableRes
    public static int getOrderFromImage(Takeout takeout) {
        switch (takeout.getOrderFrom()) {
            case TakeoutConstants.OrderFrom.XIAOER:
                return R.drawable.ic_order_from_xiaoer;
            case TakeoutConstants.OrderFrom.MEITUAN:
                return R.drawable.ic_order_from_meituan;
            case TakeoutConstants.OrderFrom.BAIDU:
                return R.drawable.ic_order_from_baidu;
            case TakeoutConstants.OrderFrom.ERLEME:
                return R.drawable.ic_order_from_eleme;
        }
        return R.drawable.ic_order_from_xiaoer;
    }


    public static void copyFilterToRequest(OrderListRequest source, OrderListRequest target) {
        if (source == null || target == null) {
            return;
        }
        target.setReserveStatus(source.getReserveStatus());
        target.setOrderFrom(source.getOrderFrom());
        target.setBeginDate(source.getBeginDate());
        target.setEndDate(source.getEndDate());
    }

    public static String formatDistance(double distance) {
        if (distance < 1) {
            return NumberUtils.trimPointIfZero(distance * 1000) + "m";
        }
        return NumberUtils.trimPointIfZero(distance) + "km";
    }

    public static short getOperationType(Takeout takeout) {
        switch (takeout.getStatus()) {
            case TakeoutConstants.OrderStatus.UN_COOK:
                return TakeoutConstants.OperationType.ORDER_TO_KITCHEN;
            case TakeoutConstants.OrderStatus.WAITING_DISPATCH:
                return takeout.getSendType() == TakeoutConstants.SendType.SELF_TAKE
                        ? TakeoutConstants.OperationType.ORDER_SELF_TAKE
                        : TakeoutConstants.OperationType.ORDER_DISPATCH;
            case TakeoutConstants.OrderStatus.WAITING_DELIVERY:
                return TakeoutConstants.OperationType.ORDER_CANCEL_DISPATCH;
            case DELIVERING:
                return TakeoutConstants.OperationType.ORDER_ARRIVED;
            case TakeoutConstants.OrderStatus.ARRIVED:
            case TakeoutConstants.OrderStatus.DELIVERY_EXCEPTION:
                return TakeoutConstants.OperationType.ORDER_CHECK_OUT;
        }
        return -1;
    }

    public static String getGroupIdentify(Takeout takeout) {
        if (takeout.getTakeoutOrderDetailVo() == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            Date date = sdf.parse(takeout.getTakeoutOrderDetailVo().getCreateTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            StringBuilder builder = new StringBuilder(8);
            return builder.append(year).append(month).append(day).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getGroupTitle(Context context, Takeout takeout) {
        if (takeout.getTakeoutOrderDetailVo() == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        try {
            Date date = sdf.parse(takeout.getTakeoutOrderDetailVo().getCreateTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            //是否是今天
            calendar.setTimeInMillis(System.currentTimeMillis());
            int year2 = calendar.get(Calendar.YEAR);
            int month2 = calendar.get(Calendar.MONTH);
            int day2 = calendar.get(Calendar.DAY_OF_MONTH);
            if (year == year2 && month == month2 && day == day2) {
                return context.getString(R.string.module_takeout_today_order_num, takeout.getGroupTotalNum());
            }

            //是否是昨天
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            int year3 = calendar.get(Calendar.YEAR);
            int month3 = calendar.get(Calendar.MONTH);
            int day3 = calendar.get(Calendar.DAY_OF_MONTH);
            if (year == year3 && month == month3 && day == day3) {
                return context.getString(R.string.module_takeout_yesterday_order_num, takeout.getGroupTotalNum());
            }

            StringBuilder builder = new StringBuilder(10);
            if (month + 1 < 10) {
                builder.append('0').append(month + 1);
            } else {
                builder.append(month + 1);
            }
            builder.append('-');
            if (day < 10) {
                builder.append('0').append(day);
            } else {
                builder.append(day);
            }
            return context.getString(R.string.module_takeout_day_order_num,
                    builder.toString(), takeout.getGroupTotalNum());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @StringRes
    public static int operateTakeoutTip(Takeout takeout) {
        switch (takeout.getStatus()) {
            case TakeoutConstants.OrderStatus.UN_COOK:
                return R.string.module_takeout_operate_success_un_cook;
            case TakeoutConstants.OrderStatus.WAITING_DISPATCH:
                if (takeout.getSendType() == TakeoutConstants.SendType.SELF_TAKE) {
                    return R.string.module_takeout_operate_success_self_take;
                }
            case TakeoutConstants.OrderStatus.WAITING_DELIVERY:
                return R.string.module_takeout_operate_success_cancel_delivery;
            case DELIVERING:
                return R.string.module_takeout_operate_success_order_arrive;
            case TakeoutConstants.OrderStatus.ARRIVED:
            case TakeoutConstants.OrderStatus.DELIVERY_EXCEPTION:
                return R.string.module_takeout_operate_success_checkout;
            default:
                return R.string.module_takeout_operate_success;
        }
    }

    /**
     * 是否是第三方外卖
     *
     * @param takeout
     * @return
     */
    public static boolean isThirdTakeout(Takeout takeout) {
        return (takeout.getOrderFrom() != TakeoutConstants.OrderFrom.WEIDIAN);
    }

}
