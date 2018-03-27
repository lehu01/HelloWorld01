package com.zmsoft.ccd.app;

import android.content.Context;

import com.zmsoft.ccd.data.UserDataRepository;
import com.zmsoft.ccd.data.source.checkshop.CheckShopRepository;
import com.zmsoft.ccd.data.source.desk.DeskRepository;
import com.zmsoft.ccd.data.source.electronic.ElectronicRepository;
import com.zmsoft.ccd.data.source.filter.FilterSourceRepository;
import com.zmsoft.ccd.data.source.home.HomeRepository;
import com.zmsoft.ccd.data.source.instance.InstanceSourceRepository;
import com.zmsoft.ccd.data.source.menubalance.MenuBalanceRepository;
import com.zmsoft.ccd.data.source.msgcenter.MsgCenterRepository;
import com.zmsoft.ccd.data.source.order.OrderSourceRepository;
import com.zmsoft.ccd.data.source.ordercancel.CancelOrderSourceRepository;
import com.zmsoft.ccd.data.source.ordercomplete.OrderCompleteSourceRepository;
import com.zmsoft.ccd.data.source.ordercreateorupdate.CreateOrUpdateSourceRepository;
import com.zmsoft.ccd.data.source.orderdetail.OrderDetailSourceRepository;
import com.zmsoft.ccd.data.source.orderparticulars.OrderParticularsSourceRepository;
import com.zmsoft.ccd.data.source.ordersummary.OrderSummarySourceRepository;
import com.zmsoft.ccd.data.source.print.PrintConfigSourceRepository;
import com.zmsoft.ccd.data.source.scan.ScanRepository;
import com.zmsoft.ccd.data.source.seat.SeatSourceRepository;
import com.zmsoft.ccd.data.source.setting.SettingSourceRepository;
import com.zmsoft.ccd.data.source.shortcutreceipt.ShortCutReceiptRepository;
import com.zmsoft.ccd.data.source.splash.SplashSourceRepository;
import com.zmsoft.ccd.data.source.system.AppSystemSourceRepository;
import com.zmsoft.ccd.data.source.systemdircode.SystemDirCodeSourceRepository;
import com.zmsoft.ccd.data.source.workmodel.WorkModelSourceRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 08/03/2017.
 * <p>
 * 提供全局公共类注入
 */
@Singleton
@Component(modules = {RepoModule.class, ApplicationModule.class})
public interface AppComponent {

    Context getContext();

    UserDataRepository getUserDataRepository();

    MsgCenterRepository getMsgCenterRepository();

    DeskRepository getDeskRepository();

    MenuBalanceRepository getMenuRepository();

    CheckShopRepository getCheckShopRepository();

    FilterSourceRepository getFilterSourceRepository();

    SeatSourceRepository getSeatSourceRepository();

    OrderSourceRepository getOrderSourceRepository();

    OrderParticularsSourceRepository getOrderParticularsSourceRepository();

    ScanRepository getScanRepository();

    SystemDirCodeSourceRepository getSystemDirCodeSourceRepository();

    InstanceSourceRepository getInstanceSourceRepository();

    OrderDetailSourceRepository getOrderDetailSourceRepository();

    OrderSummarySourceRepository getOrderSummarySourceRepository();

    OrderCompleteSourceRepository getOrderCompleteSourceRepository();

    CancelOrderSourceRepository getCancelOrderSourceRepository();

    CreateOrUpdateSourceRepository getCreateOrUpdateSourceRepository();

    WorkModelSourceRepository getWorkModelSourceRepository();

    PrintConfigSourceRepository getPrintConfigSourceRepository();

    SplashSourceRepository getSplashSourceRepository();

    AppSystemSourceRepository getAppSystemSourceRepository();

    ShortCutReceiptRepository getShortCutReceiptRepository();

    HomeRepository getHomeRepository();

    ElectronicRepository getElectronicRepository();

    SettingSourceRepository getSettingSourceRepository();
}
