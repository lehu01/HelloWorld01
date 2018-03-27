package com.zmsoft.ccd.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.chiclaim.modularization.router.annotation.Route;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.constant.WeChatConstant;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.lang.ref.WeakReference;


/**
 *  使用方式：
 *  打开该Activity即发起微信授权请求，最后结果保存在sp中
 *
 *  主要流程：
 *  1.onCreate()时，向微信发起登录授权
 *  2.onResp()接受微信登录回调，获取code
 *  3.存入sp中，并且finish本页面
 *  4.LoginActivity.OnResume()中提取
 *
 * @author : heniu@2dfire.com
 * @time : 2017/11/15
 */

@Route(path = RouterPathConstant.WxEntry.PATH)
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI mWechatApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseSpHelper.saveWechatLoginSuccess(GlobalVars.context, false);
        BaseSpHelper.saveWechatLoginCode(GlobalVars.context, "");

        mWechatApi = WXAPIFactory.createWXAPI(this.getApplicationContext(), WeChatConstant.APP_WECHAT_ID, true);
        mWechatApi.registerApp(WeChatConstant.APP_WECHAT_ID);
        mWechatApi.handleIntent(getIntent(), this);

        wechatLogin();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWechatApi.handleIntent(intent, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWechatApi != null) {
            mWechatApi.unregisterApp();
            mWechatApi.detach();
        }
    }

    //================================================================================
    // weChat login
    //================================================================================
    private void wechatLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = WeChatConstant.REQ_SCOPE;
        req.state = WeChatConstant.REQ_STATE;
        mWechatApi.sendReq(req);
    }

    //================================================================================
    // IWXAPIEventHandler
    //================================================================================
    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:   // error_ok=0(用户同意)，err_auth_denied=-4(用户拒绝授权)，err_user_cancel=-2(用户取消)
                String code = ((SendAuth.Resp) baseResp).code;
                BaseSpHelper.saveWechatLoginSuccess(GlobalVars.context, true);
                BaseSpHelper.saveWechatLoginCode(GlobalVars.context, code);
                break;
            default:
                if (!StringUtils.isEmpty(baseResp.errStr)) {
                    Toast.makeText(this, baseResp.errStr, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        this.finish();
    }
}
