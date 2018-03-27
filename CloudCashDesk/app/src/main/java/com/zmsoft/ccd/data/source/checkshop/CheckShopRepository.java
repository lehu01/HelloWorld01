package com.zmsoft.ccd.data.source.checkshop;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.user.UserDataSource;
import com.zmsoft.ccd.lib.bean.shop.Shop;
import com.zmsoft.ccd.lib.bean.user.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;


/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/3 17:47
 */
@Singleton
public class CheckShopRepository implements ICheckShopSource {

    public final ICheckShopSource mCheckShopSource;
    public final UserDataSource mUserRemoteSource;
    private final ICommonSource iCommonSource;

    @Inject
    public CheckShopRepository(@Remote ICheckShopSource checkShopSource, @Remote UserDataSource userDataSource) {
        mCheckShopSource = checkShopSource;
        mUserRemoteSource = userDataSource;
        iCommonSource = new CommonRemoteSource();
    }

    @Override
    public void getBindShopList(String memberId, Callback<List<Shop>> callback) {
        mCheckShopSource.getBindShopList(memberId, callback);
    }

//    @Override
//    public void bindShop(BindShopRequest request, Callback<CompositeLoginResultVo> callback) {
//        mCheckShopSource.bindShop(request, callback);
//    }

    @Override
    public void bindShop(String memberId, String userId, String entityId, String originalUserId, Callback<User> callback) {
        mCheckShopSource.bindShop(memberId, userId, entityId, originalUserId, callback);
    }

    public Observable<String> getConfigSwitchVal(String entityId, String systemTypeId, String code) {
        return iCommonSource.getConfigSwitchVal(entityId, systemTypeId, code);
    }
}
