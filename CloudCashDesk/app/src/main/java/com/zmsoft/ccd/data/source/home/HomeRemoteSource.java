package com.zmsoft.ccd.data.source.home;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.home.HomeCount;
import com.zmsoft.ccd.lib.bean.shop.ShopLimitVo;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;


/**
 * 主页
 *
 * @author DangGui
 * @create 2017/8/15.
 */
@Singleton
public class HomeRemoteSource implements IHomeSource {

    @Override
    public void getShopLimitDay(String entityId, final Callback<ShopLimitVo> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Home.METHOD_GET_SHOP_LIMIT_DAY);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<ShopLimitVo>() {
            @Override
            protected void onData(ShopLimitVo shopLimitVo) {
                callback.onSuccess(shopLimitVo);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getHomeUnReadCount(final Callback<HomeCount> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, UserHelper.getUserId());
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Home.METHOD_GET_HOME_UNREAD_COUNT);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<HomeCount>() {
            @Override
            protected void onData(HomeCount homeCount) {
                callback.onSuccess(homeCount);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
