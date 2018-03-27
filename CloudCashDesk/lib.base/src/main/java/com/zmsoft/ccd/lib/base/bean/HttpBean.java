package com.zmsoft.ccd.lib.base.bean;

import java.io.Serializable;

/**
 * 网络请求response实体类
 *
 * @author DangGui
 * @create 2017/2/9.
 */

public class HttpBean<T> implements Serializable {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
