package com.zmsoft.ccd.lib.bean.shop.request;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/26 15:47
 *     desc  : 绑定店铺请求参数
 * </pre>
 */
public class BindShopRequest extends BindShopBaseRequest {

    private int login_type;
    private String member_user_id;

    public int getLogin_type() {
        return login_type;
    }

    public void setLogin_type(int login_type) {
        this.login_type = login_type;
    }

    public String getMember_user_id() {
        return member_user_id;
    }

    public void setMember_user_id(String member_user_id) {
        this.member_user_id = member_user_id;
    }
}
