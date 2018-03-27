package com.zmsoft.ccd.data.source.menubalance;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.menubalance.MenuBalanceVO;
import com.zmsoft.ccd.lib.bean.menubalance.MenuVO;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/2/22.
 */
@Singleton
public class MenuBalanceRemoteSource implements IMenuBalanceSource {

    @Override
    public void addMenuBalance(String entityId, String menuId, double num, String opUserId,
                               final Callback<Object> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.MENUID, menuId);
        paramMap.put(HttpParasKeyConstant.NUM, num);
        paramMap.put(HttpParasKeyConstant.OPUSERID, opUserId);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MENU_BALANCE.RESET_MENU_BALANCE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Object>() {
            @Override
            protected void onData(Object o) {
                callback.onSuccess(o);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }

        });
    }

    @Override
    public void getAllMenus(String entityId, final Callback<List<MenuVO>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MENU_BALANCE.GET_MENU_LIST);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<MenuVO>>() {
            @Override
            protected void onData(List<MenuVO> data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }

        });
    }

    @Override
    public void cancelMenuBalance(String entityId, String menuId, String opUserId, final Callback<Integer> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        List<String> list = new ArrayList<>();
        list.add(menuId);
        paramMap.put(HttpParasKeyConstant.MENUIDLIST, JsonMapper.toJsonString(list));
        paramMap.put(HttpParasKeyConstant.OPUSERID, opUserId);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MENU_BALANCE.CLEAR_MENU_BALANCE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Integer>() {
            @Override
            protected void onData(Integer data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }

        });
    }

    @Override
    public void updateMenuBalance(String entityId, String menuId, double num, String opUserId, final Callback<Integer> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.MENUID, menuId);
        paramMap.put(HttpParasKeyConstant.NUM, num);
        paramMap.put(HttpParasKeyConstant.OPUSERID, opUserId);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MENU_BALANCE.RESET_MENU_BALANCE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Integer>() {
            @Override
            protected void onData(Integer data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getMenuBalanceList(String entityId, Integer pageSize, Integer pageIndex, final Callback<List<MenuBalanceVO>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PAGESIZE, pageSize);
        paramMap.put(HttpParasKeyConstant.PAGEINDEX, pageIndex);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MENU_BALANCE.GET_MENU_BALANCE_LIST);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<MenuBalanceVO>>() {
            @Override
            protected void onData(List<MenuBalanceVO> data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void clearAllMenuBalance(String entityId, List<String> menuIdList, String opUserId, final Callback<Integer> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.MENUIDLIST, JsonMapper.toJsonString(menuIdList));
        paramMap.put(HttpParasKeyConstant.OPUSERID, opUserId);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MENU_BALANCE.CLEAR_MENU_BALANCE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Integer>() {
            @Override
            protected void onData(Integer data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
