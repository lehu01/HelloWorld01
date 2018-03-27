package com.zmsoft.ccd.module.main.seat.selectseat.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.desk.SeatArea;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/7 10:04
 */
public class SelectSeatContract {

    public interface Presenter extends BasePresenter {

        /**
         * 只需要下载一次
         * 下载完成后会触发页面刷新
         */
        void downloadSeatData();

        /**
         * 之后每次获取presenter中缓存数据
         */
        List<SeatArea> getDownloadData();
    }

    public interface View extends BaseView<SelectSeatContract.Presenter> {

        void loadStateErrorView(String message);

        void loadDataSuccess(List<SeatArea> list, boolean isWatch);         // 下载数据成功，会动画展现recycler view

        void loadDataError(String errorMessage);
    }
}
