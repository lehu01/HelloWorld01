package com.zmsoft.ccd.lib.utils.imageloadutil.progress;
/**
 * 通知下载进度
 *
 * @author DangGui
 * @create 2016/12/28.
 */
interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
