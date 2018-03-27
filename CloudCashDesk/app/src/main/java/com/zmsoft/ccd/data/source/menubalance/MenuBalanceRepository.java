package com.zmsoft.ccd.data.source.menubalance;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.menubalance.MenuBalanceVO;
import com.zmsoft.ccd.lib.bean.menubalance.MenuVO;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/2/22.
 */
@Singleton
public class MenuBalanceRepository implements IMenuBalanceSource {

    private final IMenuBalanceSource remoteSource;

    @Inject
    MenuBalanceRepository(@Remote IMenuBalanceSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    @Override
    public void addMenuBalance(String entityId, String menuId, double num, String opUser,
                               Callback<Object> callback) {
        remoteSource.addMenuBalance(entityId, menuId, num, opUser, callback);
    }

    @Override
    public void getAllMenus(String entityId, Callback<List<MenuVO>> callback) {
        remoteSource.getAllMenus(entityId, callback);
    }

    @Override
    public void cancelMenuBalance(String entityId, String menuId, String opUserId, Callback<Integer> callback) {
        remoteSource.cancelMenuBalance(entityId, menuId, opUserId, callback);
    }

    @Override
    public void updateMenuBalance(String entityId, String menuId, double num, String opUserId, Callback<Integer> callback) {
        remoteSource.updateMenuBalance(entityId, menuId, num, opUserId, callback);
    }

    @Override
    public void getMenuBalanceList(String entityId, Integer pageSize, Integer pageIndex, Callback<List<MenuBalanceVO>> callback) {
        remoteSource.getMenuBalanceList(entityId, pageSize, pageIndex, callback);
    }

    @Override
    public void clearAllMenuBalance(String entityId, List<String> menuIdList, String opUserId, Callback<Integer> callback) {
        remoteSource.clearAllMenuBalance(entityId, menuIdList, opUserId, callback);
    }
}
