package com.zmsoft.ccd.checkShop;

import com.zmsoft.ccd.lib.bean.shop.Shop;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.module.checkshop.fragment.CheckShopContract;
import com.zmsoft.ccd.module.checkshop.fragment.CheckShopPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 03/02/2017.
 */
public class checkShopPresenterTest {

    private CheckShopPresenter mCheckShopPresenter;

    @Mock
    private CheckShopContract.View mView;

    @Before
    public void setupMocksAndView() {
        MockitoAnnotations.initMocks(this);
//        mCheckShopPresenter = new CheckShopPresenter(mView);
    }

    @Test
    public void onBindShopUi(){
        User user  = new User();
//        verify(mView).bindSuccess(user);
    }

    @Test
    public void searchShopList(){
        ArrayList<Shop> shops = new ArrayList<>();
//        verify(mView).searchSuccess(shops);
    }
}
