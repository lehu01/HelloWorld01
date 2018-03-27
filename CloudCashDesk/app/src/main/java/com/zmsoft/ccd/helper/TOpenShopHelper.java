package com.zmsoft.ccd.helper;

import com.zmsoft.ccd.app.AppEnv;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.utils.StringUtils;

import phone.rest.zmsoft.tdfopenshopmodule.exposed.ExternalParams;
import phone.rest.zmsoft.tdfopenshopmodule.exposed.JumpUtil;


/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/6 14:01
 *     desc  : 开店环境管理
 * </pre>
 */
public class TOpenShopHelper {

    /**
     * 获取开店环境的env
     *
     * @return
     */
    private static int getEnvType() {
        if (AppEnv.isProduction()) {
            return ExternalParams.TYPE_RELEASE;
        }
        int env = AppEnv.getEnv();
        if (env == AppEnv.PUB) {
            return ExternalParams.TYPE_RELEASE;
        } else if (env == AppEnv.PRE) {
            return ExternalParams.TYPE_RELEASEPRE;
        } else if (env == AppEnv.DEV || env == AppEnv.DAILY || env == AppEnv.CUSTOM) {
            return ExternalParams.TYPE_DEBUGDAILY;
        }
        return ExternalParams.TYPE_RELEASE;
    }

    /**
     * 初始化开店参数
     */
    public static void initOpenShopSDK() {
        ExternalParams params = new ExternalParams();
        //开店接口所需参数
        params.setToken(UserHelper.getToken());
        params.setEntityId(UserHelper.getEntityId());
        params.setUserId(UserHelper.getUserId());
        params.setMemberId(UserHelper.getMemberId());

        //是否走网关，false不走网关
        params.setGatewayEnable(false);
        // 行业版本
        params.setIndustry(UserHelper.getIndustry());
        // 是否有工作中的店铺
        params.setWorkStatus(StringUtils.isEmpty(UserHelper.getEntityId()) ? Base.INT_TRUE : Base.INT_FALSE);
        // 界面展示所需字段
        params.setShopName(UserHelper.getShopName());
        // 是否隐藏连锁
        params.setHideChainShop(UserHelper.isHideChainShop());
        // 设置环境，内网，日常，预发，正式，如果是内网环境用以下设置
        params.setBuildConfig(TOpenShopHelper.getEnvType());
        JumpUtil.initOpenShopParams(params, CcdApplication.getInstance().getApplication());
    }
}
