package com.zmsoft.ccd.module.main.order.particulars.model;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.module.main.order.particulars.recyclerview.OrderParticularsAdapter;
import com.zmsoft.ccd.module.main.order.particulars.recyclerview.OrderParticularsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * recycler view中总数据
 *
 * @author : heniu@2dfire.com
 * @time : 2017/11/7 09:53.
 */

public class OrderParticularsModel extends Base {

    private final List<OrderParticularsSectionModel> mOrderParticularsSectionModelList;
    private int itemCount;          // 包含日期和正常内容的条数


    public OrderParticularsModel() {
        mOrderParticularsSectionModelList = new ArrayList<>();
        itemCount = 0;
    }

    public void appendData(List<OrderParticularsItem> orderParticularsItemList, boolean isFirstPage) {
        if (isFirstPage) {
            mOrderParticularsSectionModelList.clear();
            itemCount = 0;
        }
        if (null == orderParticularsItemList) {
            return;
        }
        for (OrderParticularsItem orderParticularsItem : orderParticularsItemList) {
            itemCount ++;
            // 检查是否有相同日期
            String timeYYYYMMDD = orderParticularsItem.getTimeYYYYMMDD();
            boolean isExistTimeYYYYMMDD = false;
            OrderParticularsSectionModel existOrderParticularsSectionModel = null;
            for (OrderParticularsSectionModel orderParticularsSectionModel : mOrderParticularsSectionModelList) {
                if (timeYYYYMMDD.equals(orderParticularsSectionModel.getTimeYYYYMMDD())) {
                    isExistTimeYYYYMMDD = true;
                    existOrderParticularsSectionModel = orderParticularsSectionModel;
                    break;
                }
            }
            // 新开一个日期
            if (!isExistTimeYYYYMMDD) {
                itemCount ++;
                OrderParticularsSectionModel newOrderParticularsSectionModel = new OrderParticularsSectionModel(timeYYYYMMDD);
                newOrderParticularsSectionModel.add(orderParticularsItem);
                mOrderParticularsSectionModelList.add(newOrderParticularsSectionModel);
            } else {    // 已存在相同日期
                existOrderParticularsSectionModel.add(orderParticularsItem);
            }
        }
    }

    /**
     * @param position OrderParticularsAdapter.VIEW_HOLDER_TYPE
     * @return
     */
    public int getViewType(int position) {
        int i = 0;
        for (OrderParticularsSectionModel orderParticularsSectionModel : mOrderParticularsSectionModelList) {
            // section
            if (position == i) {
                return OrderParticularsAdapter.VIEW_HOLDER_TYPE.SECTION;
            }
            i++;
            // normal
            int normalSize = orderParticularsSectionModel.getSize();
            if (position >= i && position < (i + normalSize)) {
                return OrderParticularsAdapter.VIEW_HOLDER_TYPE.NORMAL;
            }
            i += normalSize;
        }
        return OrderParticularsAdapter.VIEW_HOLDER_TYPE.FOOTER;
    }

    /**
     * 获取normal栏中订单信息
     *
     * @param position
     * @return
     */
    public OrderParticularsItem getOrderParticularsItem(int position) {
        int i = 0;
        for (OrderParticularsSectionModel orderParticularsSectionModel : mOrderParticularsSectionModelList) {
            // section
            if (position == i) {
                return null;
            }
            i++;
            // normal
            int normalSize = orderParticularsSectionModel.getSize();
            if (position >= i && position < (i + normalSize)) {
                return orderParticularsSectionModel.get(position - i);
            }
            i += normalSize;
        }
        return null;
    }

    /**
     * 获取section栏中时间信息
     *
     * @param position
     * @return
     */
    public String getTimeYYYYMMDD(int position) {
        int i = 0;
        for (OrderParticularsSectionModel orderParticularsSectionModel : mOrderParticularsSectionModelList) {
            // section
            if (position == i) {
                return orderParticularsSectionModel.getTimeYYYYMMDD();
            }
            i++;
            // normal
            int normalSize = orderParticularsSectionModel.getSize();
            i += normalSize;
        }
        return "";
    }

    public int getItemCount() {
        return itemCount;
    }
}
