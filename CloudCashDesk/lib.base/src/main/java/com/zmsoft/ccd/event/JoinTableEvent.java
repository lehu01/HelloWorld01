package com.zmsoft.ccd.event;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/28.
 */

public class JoinTableEvent {

    private String avatar;
    private String name;


    public JoinTableEvent(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
