package com.zmsoft.ccd.lib.utils.imageloadutil;

import android.content.Context;
import android.widget.ImageView;

import com.zmsoft.ccd.lib.utils.imageloadutil.progress.ProgressLoadListener;

/**
 * 图片加载的二次封装类，方便之后切换图片加载引擎
 * 默认加载策略是GlideImageLoaderStrategy
 *
 * @author DangGui
 * @create 2016/12/28.
 */

public class ImageLoaderUtil {
    private static ImageLoaderUtil mInstance;
    private BaseImageLoaderStrategy mStrategy;

    public ImageLoaderUtil() {
        mStrategy = new GlideImageLoaderStrategy();
    }

    //单例模式，节省资源
    public static ImageLoaderUtil getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderUtil.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public void loadImage(String url, ImageView imageView, ImageLoaderOptions options) {
        mStrategy.loadImage(url, imageView, options);
    }

    public void loadImage(int drawable, ImageView imageView, ImageLoaderOptions options) {
        mStrategy.loadImage(drawable, imageView, options);
    }

    /**
     * 统一使用App context可能带来的问题：http://stackoverflow.com/questions/31964737/glide-image-loading-with-application-context
     *
     * @param url
     * @param placeholder
     * @param imageView
     */
    public void loadImage(String url, int placeholder, ImageView imageView) {
        mStrategy.loadImage(imageView.getContext(), url, placeholder, imageView);
    }

    public void loadImage(String url, int placeholder, int errorRes, ImageView imageView) {
        mStrategy.loadImage(imageView.getContext(), url, placeholder, errorRes, imageView);
    }

    public void loadGifImage(String url, int placeholder, ImageView imageView) {
        mStrategy.loadGifImage(url, placeholder, imageView);
    }

    public void loadGifImage(String url, int placeholder, int errorRes, ImageView imageView) {
        mStrategy.loadGifImage(url, placeholder, errorRes, imageView);
    }

    public void loadImage(String url, ImageView imageView) {
        mStrategy.loadImage(url, imageView);
    }

    public void loadImageWithProgress(String url, ImageView imageView, ProgressLoadListener listener) {
        mStrategy.loadImageWithProgress(url, imageView, listener);
    }

    public void loadGifWithProgress(String url, ImageView imageView, ProgressLoadListener listener) {
        mStrategy.loadGifWithProgress(url, imageView, listener);
    }

    public void loadGifWithPrepareCall(String url, ImageView imageView, SourceReadyListener listener) {
        mStrategy.loadGifWithPrepareCall(url, imageView, listener);
    }

    /**
     * 策略模式的注入操作
     *
     * @param strategy
     */
    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        mStrategy = strategy;
    }

    /**
     * 清除图片磁盘缓存
     */
    public void clearImageDiskCache(final Context context) {
        mStrategy.clearImageDiskCache(context);
    }

    /**
     * 清除图片内存缓存
     */
    public void clearImageMemoryCache(Context context) {
        mStrategy.clearImageMemoryCache(context);
    }

    /**
     * 根据不同的内存状态，来响应不同的内存释放策略
     *
     * @param context
     * @param level
     */
    public void trimMemory(Context context, int level) {
        mStrategy.trimMemory(context, level);
    }

    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache(Context context) {
        clearImageDiskCache(context.getApplicationContext());
        clearImageMemoryCache(context.getApplicationContext());
    }

    /**
     * 获取缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        return mStrategy.getCacheSize(context);
    }


}
