package com.zmsoft.ccd.data.source.menubalance;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.menubalance.MenuBalanceVO;
import com.zmsoft.ccd.lib.bean.menubalance.MenuVO;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/2/22.
 */

public interface IMenuBalanceSource {


    void addMenuBalance(String entityId, String menuId, double num, String opUserId,
                        Callback<Object> callback);

    void getAllMenus(String entityId, Callback<List<MenuVO>> callback);

    void cancelMenuBalance(String entityId, String menuId, String opUserId, Callback<Integer> callback);

    void updateMenuBalance(String entityId, String menuId, double num, String opUserId, Callback<Integer> callback);

    void getMenuBalanceList(String entityId, Integer pageSize, Integer pageIndex, Callback<List<MenuBalanceVO>> callback);

    void clearAllMenuBalance(String entityId, List<String> menuIdList, String opUserId,Callback<Integer> callback);

}
