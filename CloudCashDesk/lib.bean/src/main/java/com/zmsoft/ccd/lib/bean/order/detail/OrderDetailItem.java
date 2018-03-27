package com.zmsoft.ccd.lib.bean.order.detail;

import com.zmsoft.ccd.lib.bean.instance.Instance;
import com.zmsoft.ccd.lib.bean.instance.PersonInfo;
import com.zmsoft.ccd.lib.bean.instance.statistics.CategoryInfo;
import com.zmsoft.ccd.lib.bean.pay.Pay;

import java.io.Serializable;

/**
 * Description：组装订单详情列表所需item
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2016/12/22 14:01
 */
public class OrderDetailItem implements Serializable {

    public static final int ITEM_TYPE_ORDER_INFO = 2; // 来源+桌位
    public static final int ITEM_TYPE_USER_INFO = 3; // 用户信息
    public static final int ITEM_TYPE_INSTANCE = 4; // 菜肴列表
    public static final int ITEM_TYPE_PAY_INFO = 5; // 支付信息列表
    public static final int ITEM_TYPE_SUIT_INSTANCE = 6; // 套菜
    public static final int ITEM_TYPE_SUIT_CHILD_INSTANCE = 7; // 套菜子菜
    public static final int ITEM_TYPE_INSTANCE_ALL = 8; // 菜肴统计
    public static final int ITEM_TYPE_TAKEOUT_ADDRESS = 9; // 外卖信息

    private int type; // 封装的item的类型
    private OrderItem orderVo; // 订单来源+金额
    private Pay pay; // 支付记录
    private PersonInfo personInfo; // 点菜人信息
    private Instance instance; // 菜肴列表
    private CategoryInfo categoryInfoVo; // 统计信息
    private boolean isNews; // 是否有消息列表
    private OrderDetailTakeoutInfoVo takeoutInfo; // 外卖配送信息
    private String payOperator; //收银员姓名

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public OrderItem getOrderVo() {
        return orderVo;
    }

    public void setOrderVo(OrderItem orderVo) {
        this.orderVo = orderVo;
    }

    public Pay getPay() {
        return pay;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public CategoryInfo getCategoryInfoVo() {
        return categoryInfoVo;
    }

    public void setCategoryInfoVo(CategoryInfo categoryInfoVo) {
        this.categoryInfoVo = categoryInfoVo;
    }

    public boolean isNews() {
        return isNews;
    }

    public void setNews(boolean news) {
        isNews = news;
    }

    public OrderDetailTakeoutInfoVo getTakeoutInfo() {
        return takeoutInfo;
    }

    public void setTakeoutInfo(OrderDetailTakeoutInfoVo takeoutInfo) {
        this.takeoutInfo = takeoutInfo;
    }

    public String getPayOperator() {
        return payOperator;
    }

    public void setPayOperator(String payOperator) {
        this.payOperator = payOperator;
    }
}
