package com.zmsoft.ccd.module.menu.menu.bean;

import org.litepal.crud.DataSupport;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/10/10.
 */

public class MenuDB extends DataSupport {
    private int id;
    private String entity;
    private String json;

    public MenuDB() {
    }

    public MenuDB(String entity, String json) {
        this.entity = entity;
        this.json = json;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}
