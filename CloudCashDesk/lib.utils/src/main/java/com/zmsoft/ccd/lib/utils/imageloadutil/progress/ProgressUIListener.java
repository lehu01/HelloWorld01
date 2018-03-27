package com.zmsoft.ccd.lib.utils.imageloadutil.progress;

/**
 * 通知UI进度
 *
 * @author DangGui
 * @create 2016/12/28.
 */
public interface ProgressUIListener {
    void update(int bytesRead, int contentLength);
}
