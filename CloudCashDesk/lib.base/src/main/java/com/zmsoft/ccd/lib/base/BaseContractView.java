package com.zmsoft.ccd.lib.base;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/7 18:14
 */
public interface BaseContractView {

    void showLoading(boolean cancelAble);

    void showLoading(String content, boolean cancelAble);

    void hideLoading();

    /**
     * 展示网络正在加载的界面
     */
    void showLoadingView();

    /**
     * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
     *
     * @param errorMessage
     */
    void showErrorView(String errorMessage);

    /**
     * 数据请求成功,但数据源是空的，即没有数据，需要展示空列表交互页
     * 无法对空界面做任何操作，只是展示用
     */
    void showEmptyView();

    /**
     * 数据请求成功,但数据源是空的，即没有数据，需要展示空列表交互页
     * 如果需要对空界面做操作，比如点击空界面中的某个按钮跳转界面，可以调用该方法，传入接收点击事件的控件id
     *
     * @param clickableChildResId 接收点击事件的控件id
     */
    void showEmptyView(int clickableChildResId);

    /**
     * 数据请求成功,数据源获取正常，显示正常页面
     */
    void showContentView();
}
