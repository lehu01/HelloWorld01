package com.zmsoft.ccd.module.main.order.find.recyclerview;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.helper.DeskHelper;
import com.zmsoft.ccd.helper.OrderHelper;
import com.zmsoft.ccd.helper.SeatHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.desk.Seat;
import com.zmsoft.ccd.lib.bean.order.Order;
import com.zmsoft.ccd.lib.utils.ConstUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.TimeUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/20 16:52.
 */

public class OrderFindItem implements Serializable {
    private static final int EAT_TIME_MIN = 0;
    private static final int EAT_TIME_MAX = 999;

    interface orderTypeConstant {
        int EMPTY = 0;
        int OPEN_ORDER = 1;
        int PAID_ALL = 2;
    }

    private boolean mIsEmpty;        // 是否为空白栏，放在列表最后

    // 桌位固有信息
    private String seatCode;        // 桌位编码
    private boolean isRetail;       // 零售单，没有木色背景
    private String name;            // 名称，桌位时“A1”；零售单“NO.10001”
    private String desc;            // 桌位类型和人数，例如“（四人散座）”
    private int adviseNum;          // 推荐就餐人数
    // 订单相关信息
    private int orderType;          // 类型
    private String customerNumber;  // 用餐人数，例如“4人”
    private long openTime;          // 开单时间
    private String eatTime;         // 超时时间，例如“60分钟”
    private boolean isTimeOut;      // 是否超时，用餐超时后将已下单时间变成红色
    private boolean isPrint;        // 客户联打印后，在桌位右下角出现打印图标

    // 非UI信息
    private String orderId;         // 订单id
    private boolean isLimitTime;    // 该订单是否限时
    private boolean isOverTime;     // 服务端返回该订单是否用餐超时，如果为true则isTimeOut永远为true


    //================================================================================
    // constructor
    //================================================================================
    public OrderFindItem(boolean isEmpty) {
        this.mIsEmpty = isEmpty;
    }

    // 从关注的桌位生成
    public OrderFindItem(Seat seat) {
        this.mIsEmpty = false;
        this.seatCode = seat.getCode();
        this.isRetail = (seat.getCode() == null);
        if (seat.getCode() != null) {
            this.name = seat.getName();
        }
        this.desc = DeskHelper.getSeatDescString(seat.getSeatKind(), seat.getAdviseNum());
        this.adviseNum = seat.getAdviseNum();

        this.orderType = orderTypeConstant.EMPTY;
        this.customerNumber = "";
        this.openTime = 0L;
        this.isTimeOut = false;
        this.isPrint = false;
        this.orderId = "";
        this.isLimitTime = false;
        this.isOverTime = false;
        updateEatTime();
    }

    // 生成零售单信息
    public OrderFindItem(Order order) {
        this.mIsEmpty = false;
        this.seatCode = order.getSeatCode();
        this.isRetail = (order.getSeatCode() == null);
        this.name = String.format(GlobalVars.context.getResources().getString(R.string.order_find_retail_code), order.getCode());
        this.desc = "";
        this.adviseNum = 0;

        if (order.getPayStatus() == OrderHelper.OrderList.PayStatus.PAY_ALL) {
            this.orderType = orderTypeConstant.PAID_ALL;
        } else {
            this.orderType = orderTypeConstant.OPEN_ORDER;
        }
        this.customerNumber = String.format(GlobalVars.context.getResources().getString(R.string.msg_attention_desk_customer_number), order.getPeopleCount());
        this.openTime = order.getOpenTime();
        this.isTimeOut = false;
        this.isPrint = false;
        this.orderId = order.getOrderId();
        this.isLimitTime = false;
        this.isOverTime = false;
        updateEatTime();
    }

    // 从桌位订单生成
    public OrderFindItem(com.zmsoft.ccd.lib.bean.table.Seat seat) {
        this.mIsEmpty = false;
        this.seatCode = seat.getSeatCode();
        this.isRetail = (seat.getSeatCode() == null);
        if (seat.getSeatCode() != null) {
            this.name = seat.getSeatName();
        }
        this.desc = DeskHelper.getSeatDescString(seat.getSeatKind(), seat.getAdviseNum());
        this.adviseNum = seat.getAdviseNum();

        fillSeatData(seat);
    }

    //================================================================================
    // update retail data
    //================================================================================
    /**
     * 从关注的桌位生成后，填充订单桌位的信息
     * @param seat
     */
    public void fillSeatData(com.zmsoft.ccd.lib.bean.table.Seat seat) {
        if (StringUtils.isEmpty(seat.getOrderId())) {
            this.orderType = orderTypeConstant.EMPTY;
            this.customerNumber = "";
            this.openTime = 0L;
            this.isTimeOut = false;
            this.isPrint = false;
            this.orderId = "";
        } else {
            this.orderId = seat.getOrderId();
            if (SeatHelper.SEAT_PAY_ALL == seat.getPayStatus()) {
                this.orderType = orderTypeConstant.PAID_ALL;
                this.customerNumber = "";
                this.openTime = 0L;
                this.isTimeOut = false;
                this.isPrint = (seat.getPrintCount() > 0);
            } else {
                this.orderType = orderTypeConstant.OPEN_ORDER;
                this.customerNumber = String.format(GlobalVars.context.getResources().getString(R.string.msg_attention_desk_customer_number), seat.getPeopleCount());
                this.openTime = seat.getOpenTime();
                this.isTimeOut = false;
                this.isPrint = (seat.getPrintCount() > 0);
            }
        }
        this.isLimitTime = (seat.getIsLimitTime() == Base.INT_TRUE);
        this.isOverTime = (seat.getOverTime() == Base.INT_TRUE);;
        updateEatTime();
    }




    //================================================================================
    // update eat time
    //================================================================================
    private void updateEatTime() {
        updateEatTime(0);
    }

    public void updateEatTime(int timeOutMinutes) {
        if (orderTypeConstant.OPEN_ORDER == this.orderType) {
            int eatTimeInt = calculateEatTime(this.openTime);
            this.eatTime = String.format(GlobalVars.context.getResources().getString(R.string.order_find_eat_time), eatTimeInt);
            // 服务端未开启限时用餐
            if (!this.isLimitTime) {
                this.isTimeOut = false;
                return;
            }
            // 服务端判定用餐超时
            if (this.isOverTime) {
                this.isTimeOut = true;
                return;
            }
            // 用餐超时时间异常
            if (timeOutMinutes == 0) {
                this.isTimeOut = false;
                return;
            }
            // 必须为大于，否则服务端可能未超时。出现刷新后变白
            this.isTimeOut = (eatTimeInt > timeOutMinutes);
        } else {
            this.eatTime = "";
        }
    }


    private int calculateEatTime(long startTime) {
        int eatTimeInt = (int) TimeUtils.getTimeSpan((new Date().getTime()), startTime, ConstUtils.TimeUnit.MIN);
        if (eatTimeInt < EAT_TIME_MIN) {
            eatTimeInt = EAT_TIME_MIN;
        }
        if (eatTimeInt > EAT_TIME_MAX) {
            eatTimeInt = EAT_TIME_MAX;
        }
        return eatTimeInt;
    }

    //================================================================================
    // setter and getter
    //================================================================================
    public boolean isEmpty() {
        return mIsEmpty;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public boolean isRetail() {
        return isRetail;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getOrderType() {
        return orderType;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getEatTime() {
        return eatTime;
    }

    public boolean isTimeOut() {
        return isTimeOut;
    }

    public boolean isPrint() {
        return isPrint;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getAdviseNum() {
        return adviseNum;
    }
}
