package com.zmsoft.ccd.module.personal.attention;


import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.desk.SeatArea;
import com.zmsoft.ccd.lib.bean.desk.ViewHolderSeat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DangGui
 * @create 2016/12/19.
 */

public interface AddAttenDeskContract {
    interface View extends BaseView<Presenter> {
        /**
         * 刷新界面
         */
        void notifyDataChange();

        /**
         * 展示加载到的桌位列表，UI更新
         *
         * @param deskSectionList 数据源
         */
        void showAttentionDeskList(List<SeatArea> data);

        /**
         * 关闭页面
         */
        void finishView();

        /**
         * 数据请求成功
         */
        void loadDataSuccess();

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void bindSeatsError(String errorMessage);

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         * @param showNetErrorView true显示网络错误的界面
         */
        void loadDataError(String errorMessage, boolean showNetErrorView);
    }

    interface Presenter extends BasePresenter {
        /**
         * 分页加载桌位
         */
        void loadAttentionDeskList();

        /**
         * section header被 选中/反选 后，整个section的所有item应该相应做 选中/反选 处理
         *
         * @param sectionId 当前sectionId
         * @param isChecked 是否被选中
         */
        void checkedSectionItems(ArrayList<ViewHolderSeat> mDeskSectionList, String sectionId, boolean isChecked);

        /**
         * section中的某个item被 选中/反选 后，应当检测同sectionId的item是否全部是选中状态，
         * 如果是section header也应该是选中状态，只要有一个不是选中状态，header就应该是 反选 状态
         *
         * @param sectionId 当前sectionId
         */
        void checkIfSectionItemChecked(ArrayList<ViewHolderSeat> mDeskSectionList, String sectionId, boolean isChecked);

        /**
         * 全选/全不选 所有的item
         *
         * @param isChecked 全选/全不选
         */
        void checkallItems(ArrayList<ViewHolderSeat> mDeskSectionList, boolean isChecked);

        /**
         * 获取到所有已选中的桌位，转化为json串
         *
         * @return
         */
        String getAllCheckedSeats(ArrayList<ViewHolderSeat> mDeskSectionList, String takeoutDeskId);

        /**
         * 调用接口传入选中的桌位id json串
         *
         * @param jsonString
         */
        void bindCheckedSeats(String jsonString);
    }
}
