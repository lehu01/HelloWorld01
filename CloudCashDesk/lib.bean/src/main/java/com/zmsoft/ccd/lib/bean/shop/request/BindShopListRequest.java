package com.zmsoft.ccd.lib.bean.shop.request;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/26 14:19
 *     desc  : 绑店列表请求参数
 * </pre>
 */
public class BindShopListRequest extends BindShopBaseRequest {

    private String token;
    private int is_show_brand;
    private int is_show_branch;
    private int is_show_invalid;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIs_show_brand() {
        return is_show_brand;
    }

    public void setIs_show_brand(int is_show_brand) {
        this.is_show_brand = is_show_brand;
    }

    public int getIs_show_branch() {
        return is_show_branch;
    }

    public void setIs_show_branch(int is_show_branch) {
        this.is_show_branch = is_show_branch;
    }

    public int getIs_show_invalid() {
        return is_show_invalid;
    }

    public void setIs_show_invalid(int is_show_invalid) {
        this.is_show_invalid = is_show_invalid;
    }
}
