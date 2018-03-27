package com.zmsoft.ccd.app;

import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.data.source.checkshop.dagger.CheckShopRepoModule;
import com.zmsoft.ccd.data.source.desk.dagger.DeskRepoModule;
import com.zmsoft.ccd.data.source.electronic.dagger.ElectronicRepoModule;
import com.zmsoft.ccd.data.source.filter.dagger.FilterRepoModule;
import com.zmsoft.ccd.data.source.home.dagger.HomeRepoModule;
import com.zmsoft.ccd.data.source.instance.dagger.InstanceSourceModule;
import com.zmsoft.ccd.data.source.menubalance.dagger.MenuBalanceRepoModule;
import com.zmsoft.ccd.data.source.msgcenter.dagger.MsgCenterRepoModule;
import com.zmsoft.ccd.data.source.order.dagger.OrderSourceModule;
import com.zmsoft.ccd.data.source.ordercancel.dagger.CancelOrderSourceModule;
import com.zmsoft.ccd.data.source.ordercomplete.dagger.OrderCompleteSourceModule;
import com.zmsoft.ccd.data.source.ordercreateorupdate.dagger.CreateOrUpdateSourceModule;
import com.zmsoft.ccd.data.source.orderdetail.dagger.OrderDetailSourceModule;
import com.zmsoft.ccd.data.source.orderparticulars.dagger.OrderParticularsSourceModule;
import com.zmsoft.ccd.data.source.ordersummary.dagger.OrderSummarySourceModule;
import com.zmsoft.ccd.data.source.print.dagger.PrintConfigSourceModule;
import com.zmsoft.ccd.data.source.scan.dagger.ScanSourceModule;
import com.zmsoft.ccd.data.source.seat.dagger.SeatSourceModule;
import com.zmsoft.ccd.data.source.setting.dagger.SettingSourceModule;
import com.zmsoft.ccd.data.source.shortcutreceipt.dagger.ShortCutReceiptRepoModule;
import com.zmsoft.ccd.data.source.splash.dagger.SplashRepoModule;
import com.zmsoft.ccd.data.source.system.dagger.AppSystemRepoModule;
import com.zmsoft.ccd.data.source.systemdircode.dagger.SystemDirCodeRepoModule;
import com.zmsoft.ccd.data.source.user.UserRepoModule;
import com.zmsoft.ccd.data.source.workmodel.dagger.WorkModelSourceModule;

import dagger.Module;
import dagger.Provides;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 08/03/2017.
 * <p>
 * 提供各个数据仓库的module合集
 */
@Module(includes = {
        UserRepoModule.class,
        MsgCenterRepoModule.class,
        MenuBalanceRepoModule.class,
        CheckShopRepoModule.class,
        DeskRepoModule.class,
        FilterRepoModule.class,
        SeatSourceModule.class,
        OrderSourceModule.class,
        ScanSourceModule.class,
        SystemDirCodeRepoModule.class,
        InstanceSourceModule.class,
        OrderDetailSourceModule.class,
        OrderSummarySourceModule.class,
        OrderCompleteSourceModule.class,
        OrderParticularsSourceModule.class,
        CancelOrderSourceModule.class,
        CreateOrUpdateSourceModule.class,
        WorkModelSourceModule.class,
        PrintConfigSourceModule.class,
        SplashRepoModule.class,
        AppSystemRepoModule.class,
        ShortCutReceiptRepoModule.class,
        HomeRepoModule.class,
        ElectronicRepoModule.class,
        SettingSourceModule.class
})
public final class RepoModule {

    @Provides
    NetworkService getNetwork() {
        return NetworkService.getDefault();
    }
}
