package com.zmsoft.ccd.login;

import com.zmsoft.ccd.module.login.login.LoginContract;
import com.zmsoft.ccd.module.login.login.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 03/02/2017.
 */
public class loginPresenterTest {

    @Mock
    private LoginContract.View mLoginView;

    private LoginPresenter mLoginPresenter;

    @Before
    public void setupMocksAndView() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loginSuccessUi(){
//        mLoginPresenter = new LoginPresenter(mLoginView);
//        User user = new User();
//        verify(mLoginView).loginSuccess(user);
    }
}
