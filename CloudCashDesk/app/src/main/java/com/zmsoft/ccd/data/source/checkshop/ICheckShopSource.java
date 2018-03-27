package com.zmsoft.ccd.data.source.checkshop;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.shop.Shop;
import com.zmsoft.ccd.lib.bean.user.User;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/3 17:42
 */
public interface ICheckShopSource {

    /**
     * 获取绑定的店铺列表
     *
     * @param memberId 用户id
     * @param callback 回调
     */
    void getBindShopList(String memberId, Callback<List<Shop>> callback);

//    /**
//     * 绑定店铺（切店）
//     *
//     * @param callback 回调
//     */
//    void bindShop(BindShopRequest request, Callback<CompositeLoginResultVo> callback);

    /**
     * 绑定店铺（切店）
     *
     * @param memberId 会员id
     * @param userId   用户id
     * @param entityId 实体id
     * @param callback 回调
     */
    void bindShop(String memberId, String userId, String entityId, String originalUserId, Callback<User> callback);

}
