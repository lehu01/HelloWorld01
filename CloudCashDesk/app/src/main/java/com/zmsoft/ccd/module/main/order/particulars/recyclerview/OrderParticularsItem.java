package com.zmsoft.ccd.module.main.order.particulars.recyclerview;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.particulars.DateOrderParticularsListResult;
import com.zmsoft.ccd.lib.bean.order.particulars.OrderParticulars;
import com.zmsoft.ccd.lib.bean.order.particulars.OrderParticularsListResult;
import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterItem;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.TimeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/18 14:11.
 */

public class OrderParticularsItem implements Serializable {

    private String orderId;             // 非显示部分

    private String code;                // 订单code
    private String seatName;            // 来源   “桌号：A01”，“美团”
    private String serialNumber;        // 流水号
    private String timeYYYYMMDD;        // 时间   2017-10-02
    private String timeHHMM;            // 时间   15:00
    private String consumption;         // 消费金额

    //================================================================================
    // transform
    //================================================================================
    // 生成订单明细页面中列表数据
    public static List<OrderParticularsItem> transformResponse(DateOrderParticularsListResult data){
        List<OrderParticularsItem> result = new ArrayList<>();
        if (null == data) {
            return result;
        }
        List<OrderParticularsListResult> orderParticularsListResultList = data.getDateBillDetailList();
        if (null == orderParticularsListResultList) {
            return result;
        }
        for (OrderParticularsListResult orderParticularsListResult : orderParticularsListResultList) {
            if (null == orderParticularsListResult) {
                continue;
            }
            String date = orderParticularsListResult.getDate();
            List<OrderParticulars> orderParticularsList = orderParticularsListResult.getBillDetails();
            if (null == orderParticularsList) {
                continue;
            }
            // 是否为该日期，第一条数据
            boolean isSectionFirstItem = true;
            for (OrderParticulars orderParticulars : orderParticularsList) {
                String orderId = orderParticulars.getOrderId();
                String code = String.format(GlobalVars.context.getString(R.string.order_particulars_order_code_format), orderParticulars.getCode());
                String seatName = OrderParticularsItem.transformSeatCode(orderParticulars.getOrderFrom(), orderParticulars.getSeatName());
                String serialNumber = String.format(GlobalVars.context.getString(R.string.order_particulars_serial_number_format), orderParticulars.getOrderNum());
                String timeYYYYMMDD = date;
                String timeHHMM = TimeUtils.millis2String(orderParticulars.getEndTime(), TimeUtils.HH_mm_PATTERN);

                String consumption = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), GlobalVars.context.getString(R.string.order_particulars_consumption_format), orderParticulars.getFee());
                OrderParticularsItem orderParticularsItem = new OrderParticularsItem(orderId, code, seatName, serialNumber, timeYYYYMMDD, timeHHMM, consumption);
                result.add(orderParticularsItem);

                if (isSectionFirstItem) {
                    isSectionFirstItem = false;
                }
            }
        }
        return result;
    }

    // 显示桌位名称，如果是零售单，显示"-"；如果是外卖单，则显示外卖来源。
    private static String transformSeatCode(Short orderFrom, String seatCode) {
        // 桌位单
        if (!StringUtils.isEmpty(seatCode)) {
            return String.format(GlobalVars.context.getString(R.string.order_particulars_seat_code_desk_format), seatCode);
        }
        // 外卖
        if (orderFrom.equals(Short.valueOf(OrderRightFilterItem.CodeSource.CODE_WAITER_TAKEOUT))) {
            return GlobalVars.context.getString(R.string.filter_item_source_waiter_takeout);
        } else if (orderFrom.equals(Short.valueOf(OrderRightFilterItem.CodeSource.CODE_MEITUAN))) {
            return GlobalVars.context.getString(R.string.filter_item_source_meituan);
        } else if (orderFrom.equals(Short.valueOf(OrderRightFilterItem.CodeSource.CODE_ELME))) {
            return GlobalVars.context.getString(R.string.filter_item_source_elme);
        } else if (orderFrom.equals(Short.valueOf(OrderRightFilterItem.CodeSource.CODE_BAIDU))) {
            return GlobalVars.context.getString(R.string.filter_item_source_baidu);
        }
        // 零售单
        return GlobalVars.context.getString(R.string.order_particulars_seat_code_retail);
    }

    //================================================================================
    // constructor
    //================================================================================
    public OrderParticularsItem(String orderId, String code, String seatName, String serialNumber, String timeYYYYMMDD, String timeHHMM, String consumption) {
        this.orderId = orderId;
        this.code = code;
        this.seatName = seatName;
        this.serialNumber = serialNumber;
        this.timeYYYYMMDD = timeYYYYMMDD;
        this.timeHHMM = timeHHMM;
        this.consumption = consumption;
    }


    //================================================================================
    // setter and getter
    //================================================================================
    public String getOrderId() {
        return orderId;
    }


    public String getCode() {
        return code;
    }

    public String getSeatName() {
        return seatName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getTimeYYYYMMDD() {
        return timeYYYYMMDD;
    }

    public String getTimeHHMM() {
        return timeHHMM;
    }

    public String getConsumption() {
        return consumption;
    }
}
