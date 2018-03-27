package com.zmsoft.ccd.module.main;

import com.dfire.mobile.cashupdate.bean.CashUpdateInfoDO;
import com.zmsoft.ccd.lib.bean.Base;

/**
 * 2017/11/3 只放入需要弹框的设置选项，后续将main activity新增成员变量都放入此类
 * @author : heniu@2dfire.com
 * @time : 2017/11/3 14:17.
 */

public class MainActivityModel extends Base {

    public interface GREEN_HAND_STAGE {
        int INIT = 0;           // 默认不显示
        int SHOW = 1;           // 正在显示
        int FINISH = 2;         // 显示结束
    }


    /**
     * 显示新手引导后，需要弹框的话才开始弹框
     */
    private int mGreenHandStage;
    private boolean needShowForceUpdateDialog;          // 需要弹框提示：当前有强制更新的新版本，提示APP升级
    private boolean needShowUpdateDialog;               // 需要弹框提示：当前有新版本，提示APP升级
    private boolean needShowReloadWorkStatusDialog;     // 需要弹框提示：获取上班状态失败，提示用户重试
    private boolean needShowPermissionRationale;        // 需要弹框提示：请求系统权限
    private boolean needShowGotoWorkingDialog;          // 需要弹框提示：当前未上班，提示是否要上班
    private boolean needShowUpgradeShopDialog;          // 需要弹框提示：试用期已过，提示是否升级店铺

    private CashUpdateInfoDO cashUpdateInfo;            // 版本更新对象


    public MainActivityModel() {
        mGreenHandStage = GREEN_HAND_STAGE.INIT;
        needShowForceUpdateDialog = false;
        needShowUpdateDialog = false;
        needShowReloadWorkStatusDialog = false;
        needShowPermissionRationale = false;
        needShowGotoWorkingDialog = false;
        needShowUpgradeShopDialog = false;
    }

    public int getGreenHandStage() {
        return mGreenHandStage;
    }

    public void setGreenHandStage(int mGreenHandStage) {
        this.mGreenHandStage = mGreenHandStage;
    }

    public boolean isNeedShowForceUpdateDialog() {
        return needShowForceUpdateDialog;
    }

    public void setNeedShowForceUpdateDialog(boolean needShowForceUpdateDialog) {
        this.needShowForceUpdateDialog = needShowForceUpdateDialog;
    }

    public boolean isNeedShowUpdateDialog() {
        return needShowUpdateDialog;
    }

    public void setNeedShowUpdateDialog(boolean needShowUpdateDialog) {
        this.needShowUpdateDialog = needShowUpdateDialog;
    }

    public boolean isNeedShowReloadWorkStatusDialog() {
        return needShowReloadWorkStatusDialog;
    }

    public void setNeedShowReloadWorkStatusDialog(boolean needShowReloadWorkStatusDialog) {
        this.needShowReloadWorkStatusDialog = needShowReloadWorkStatusDialog;
    }

    public boolean isNeedShowPermissionRationale() {
        return needShowPermissionRationale;
    }

    public void setNeedShowPermissionRationale(boolean needShowPermissionRationale) {
        this.needShowPermissionRationale = needShowPermissionRationale;
    }

    public boolean isNeedShowGotoWorkingDialog() {
        return needShowGotoWorkingDialog;
    }

    public void setNeedShowGotoWorkingDialog(boolean needShowGotoWorkingDialog) {
        this.needShowGotoWorkingDialog = needShowGotoWorkingDialog;
    }

    public boolean isNeedShowUpgradeShopDialog() {
        return needShowUpgradeShopDialog;
    }

    public void setNeedShowUpgradeShopDialog(boolean needShowUpgradeShopDialog) {
        this.needShowUpgradeShopDialog = needShowUpgradeShopDialog;
    }

    public CashUpdateInfoDO getCashUpdateInfo() {
        return cashUpdateInfo;
    }

    public void setCashUpdateInfo(CashUpdateInfoDO cashUpdateInfo) {
        this.cashUpdateInfo = cashUpdateInfo;
    }
}
