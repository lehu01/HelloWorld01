package com.zmsoft.ccd.lib.utils.imageloadutil;

import android.content.Context;
import android.widget.ImageView;

import com.zmsoft.ccd.lib.utils.imageloadutil.progress.ProgressLoadListener;


/**
 * 加载策略
 *
 * @author DangGui
 * @create 2016/12/28.
 */
public interface BaseImageLoaderStrategy {
    void loadImage(String url, ImageView imageView, ImageLoaderOptions options);

    void loadImage(int drawable, ImageView imageView, ImageLoaderOptions options);

    void loadImage(String url, ImageView imageView);

    void loadImage(String url, int placeholder, ImageView imageView);

    void loadImage(String url, int placeholder, int errorRes, ImageView imageView);

    void loadImage(Context context, String url, int placeholder, ImageView imageView);

    void loadImage(Context context, String url, int placeholder, int errorRes, ImageView imageView);

    void loadGifImage(String url, int placeholder, ImageView imageView);

    void loadGifImage(String url, int placeholder, int errorRes, ImageView imageView);

    void loadImageWithProgress(String url, ImageView imageView, ProgressLoadListener listener);

    void loadGifWithProgress(String url, ImageView imageView, ProgressLoadListener listener);

    void loadGifWithPrepareCall(String url, ImageView imageView, SourceReadyListener listener);

    //清除硬盘缓存
    void clearImageDiskCache(final Context context);

    //清除内存缓存
    void clearImageMemoryCache(Context context);

    //根据不同的内存状态，来响应不同的内存释放策略
    void trimMemory(Context context, int level);

    //获取缓存大小
    String getCacheSize(Context context);

}
