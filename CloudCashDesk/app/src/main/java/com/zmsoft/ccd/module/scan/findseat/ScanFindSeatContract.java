package com.zmsoft.ccd.module.scan.findseat;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/25 17:28
 */
public class ScanFindSeatContract {

    public interface Presenter extends BasePresenter {

        /**
         * 根据座位code获取座位
         */
        void getSeatBySeatCode(String entityId, String seatCode);
    }

    public interface View extends BaseView<Presenter> {

        // 获取数据失败
        void loadDataError(String errorMessage);

        // 获取桌位
        void loadSeatBySeatCodeSuccess(com.zmsoft.ccd.lib.bean.desk.Seat seat);

    }
}
