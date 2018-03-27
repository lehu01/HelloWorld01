package com.zmsoft.ccd.lib.utils.imageloadutil.progress;

/**
 * 通知图片加载进度
 *
 * @author DangGui
 * @create 2016/12/28.
 */
public interface ProgressLoadListener {

    void update(int bytesRead, int contentLength);

    void onException();

    void onResourceReady();
}
