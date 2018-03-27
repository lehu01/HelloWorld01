package com.zmsoft.ccd.module.menu.menu.source;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.ResponseModel;
import com.dfire.mobile.network.service.NetworkService;
import com.dfire.mobile.util.JsonMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zmsoft.ccd.constant.HttpMethodConstantsMenu;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.bean.HttpBean;
import com.zmsoft.ccd.lib.base.bean.HttpResult;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.menu.bean.BoMenu;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;
import com.zmsoft.ccd.module.menu.menu.bean.MenuCategory;
import com.zmsoft.ccd.module.menu.menu.bean.MenuRetailQuery;
import com.zmsoft.ccd.module.menu.menu.bean.MenuUnit;
import com.zmsoft.ccd.module.menu.menu.bean.PassThrough;
import com.zmsoft.ccd.module.menu.menu.bean.ResponseSeatStatus;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenu;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenuHitRule;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

import static com.zmsoft.ccd.network.CommonConstant.PARA_ENTITY_ID;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/13.
 */

public class MenuRemoteSource implements IMenuSource {

    @Override
    public void getMenuList(String entityId, final Callback<List<Menu>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(MenuHttpConstant.MenuList.ENTITY_ID, entityId);
        //paramMap.put(MenuHttpConstant.MenuList.KEYWORD, keyword);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, MenuHttpConstant.MenuList.METHOD);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<Menu>>() {
            @Override
            protected void onData(List<Menu> user) {
                callback.onSuccess(user);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public Observable<List<Menu>> getMenuList(final String entityId) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<List<Menu>>>>() {
            @Override
            public HttpResult<HttpBean<List<Menu>>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(MenuHttpConstant.MenuList.ENTITY_ID, entityId);
                Type listType = new TypeToken<HttpResult<HttpBean<List<Menu>>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, MenuHttpConstant.MenuList.METHOD)
                        .newBuilder().responseType(listType).build();
                ResponseModel<HttpResult<HttpBean<List<Menu>>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public void getMenuCategories(String entityId, int[] types, final Callback<List<MenuCategory>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(MenuHttpConstant.MenuCategory.ENTITY_ID, entityId);
        paramMap.put(MenuHttpConstant.MenuCategory.TYPES, new Gson().toJson(types));

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, MenuHttpConstant.MenuCategory.METHOD);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<MenuCategory>>() {
            @Override
            protected void onData(List<MenuCategory> list) {
                callback.onSuccess(list);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getMenuUnits(String entityId, final Callback<List<MenuUnit>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(MenuHttpConstant.MenuUnit.ENTITY_ID, entityId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, MenuHttpConstant.MenuUnit.METHOD);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<MenuUnit>>() {
            @Override
            protected void onData(List<MenuUnit> list) {
                callback.onSuccess(list);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }


    @Override
    public void getMenuPassThrough(String entityId, final Callback<List<PassThrough>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(MenuHttpConstant.PassThroughWay.ENTITY_ID, entityId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, MenuHttpConstant.PassThroughWay.METHOD);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<PassThrough>>() {
            @Override
            protected void onData(List<PassThrough> list) {
                callback.onSuccess(list);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void saveCustomMenuToCart(String entityId, String userId,
                                     String ownerCustomerId, String seatCode,
                                     int peopleCount, String itemListJson, final Callback<DinningTableVo> callback) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(MenuHttpConstant.CustomToCart.ENTITY_ID, entityId);
        paramMap.put(MenuHttpConstant.CustomToCart.USER_ID, userId);
        paramMap.put(MenuHttpConstant.CustomToCart.OWNER_CUSTOMER_ID, ownerCustomerId);
        paramMap.put(MenuHttpConstant.CustomToCart.SEAT_CODE, seatCode);
        paramMap.put(MenuHttpConstant.CustomToCart.PEOPLE_COUNT, peopleCount);
        paramMap.put(MenuHttpConstant.CustomToCart.ITEM_LIST, itemListJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, MenuHttpConstant.CustomToCart.METHOD);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<DinningTableVo>() {
            @Override
            protected void onData(DinningTableVo list) {
                callback.onSuccess(list);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });


    }

    @Override
    public void getSeatStatus(String entityId, String seatCode, final Callback<ResponseSeatStatus> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(MenuHttpConstant.SeatStatus.ENTITY_ID, entityId);
        paramMap.put(MenuHttpConstant.SeatStatus.SEAT_CODE, seatCode);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, MenuHttpConstant.SeatStatus.METHOD);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<ResponseSeatStatus>() {
            @Override
            protected void onData(ResponseSeatStatus seatStatus) {
                callback.onSuccess(seatStatus);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getSuitDetail(String entityId, String suitMenuId, String source, final Callback<SuitMenu> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(MenuHttpConstant.SuitDetail.ENTITY_ID, entityId);
        paramMap.put(MenuHttpConstant.SuitDetail.SUIT_MENU_ID, suitMenuId);
        paramMap.put(MenuHttpConstant.SuitDetail.SOURCE, source);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, MenuHttpConstant.SuitDetail.METHOD);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<SuitMenu>() {
            @Override
            protected void onData(SuitMenu seatStatus) {
                callback.onSuccess(seatStatus);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void queryFoodDetail(String menuId, final Callback<BaseMenuVo> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(PARA_ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(HttpMethodConstantsMenu.FoodDetail.Paras.MENU_ID, menuId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstantsMenu.FoodDetail.QUERY_DETAIL);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<BaseMenuVo>() {
            @Override
            protected void onData(BaseMenuVo baseMenuVo) {
                callback.onSuccess(baseMenuVo);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getRulesBySuitMenuId(String suitMenuId, final Callback<List<SuitMenuHitRule>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(MenuHttpConstant.SuitHitRule.ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(MenuHttpConstant.SuitHitRule.SUIT_MENU_ID, suitMenuId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, MenuHttpConstant.SuitHitRule.METHOD);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<SuitMenuHitRule>>() {
            @Override
            protected void onData(List<SuitMenuHitRule> rules) {
                callback.onSuccess(rules);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public Observable<List<BoMenu>> queryMenuListByEntityId(final MenuRetailQuery menuRetailQuery) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<List<BoMenu>>>>() {
            @Override
            public HttpResult<HttpBean<List<BoMenu>>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("param", JsonMapper.toJsonString(menuRetailQuery));
                Type listType = new TypeToken<HttpResult<HttpBean<List<BoMenu>>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, MenuHttpConstant.MenuListByCode.METHOD)
                        .newBuilder().responseType(listType).build();
                ResponseModel<HttpResult<HttpBean<List<BoMenu>>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<List<SuitMenuHitRule>> getRulesBySuitMenuId(final String suitMenuId) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<List<SuitMenuHitRule>>>>() {
            @Override
            public HttpResult<HttpBean<List<SuitMenuHitRule>>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(MenuHttpConstant.SuitHitRule.ENTITY_ID, UserHelper.getEntityId());
                paramMap.put(MenuHttpConstant.SuitHitRule.SUIT_MENU_ID, suitMenuId);
                Type listType = new TypeToken<HttpResult<HttpBean<List<SuitMenuHitRule>>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, MenuHttpConstant.SuitHitRule.METHOD)
                        .newBuilder().responseType(listType).build();
                ResponseModel<HttpResult<HttpBean<List<SuitMenuHitRule>>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<BaseMenuVo> queryFoodDetail(final String menuId) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<BaseMenuVo>>>() {
            @Override
            public HttpResult<HttpBean<BaseMenuVo>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(PARA_ENTITY_ID, UserHelper.getEntityId());
                paramMap.put(HttpMethodConstantsMenu.FoodDetail.Paras.MENU_ID, menuId);
                Type listType = new TypeToken<HttpResult<HttpBean<BaseMenuVo>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstantsMenu.FoodDetail.QUERY_DETAIL)
                        .newBuilder().responseType(listType).build();
                ResponseModel<HttpResult<HttpBean<BaseMenuVo>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

}
