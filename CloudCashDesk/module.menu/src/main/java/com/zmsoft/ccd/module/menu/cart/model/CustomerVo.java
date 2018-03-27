package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;

/**
 * @author DangGui
 * @create 2017/4/13.
 */

public class CustomerVo implements Serializable {
    /**
     * 小二会员Id
     */
    private String id;
    /**
     * 会员名
     */
    private String name;
    /**
     * 头像地址
     */
    private String imageUrl;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 会员等级
     */
    private int level;
    /**
     * 会员等级名称
     */
    private String levelName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
