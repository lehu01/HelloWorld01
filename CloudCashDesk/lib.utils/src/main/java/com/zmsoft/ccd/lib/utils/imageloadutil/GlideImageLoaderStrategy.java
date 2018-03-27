package com.zmsoft.ccd.lib.utils.imageloadutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.zmsoft.ccd.lib.utils.FileUtils;
import com.zmsoft.ccd.lib.utils.imageloadutil.progress.ProgressLoadListener;
import com.zmsoft.ccd.lib.utils.imageloadutil.progress.ProgressModelLoader;
import com.zmsoft.ccd.lib.utils.imageloadutil.progress.ProgressUIListener;

import javax.net.ssl.SSLHandshakeException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Glide加载策略(默认策略)
 *
 * @author DangGui
 * @create 2016/12/28.
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {
    private static final String IMAGE_HTTPS = "https";
    private static final String IMAGE_HTTP = "http";

    @Override
    public void loadImage(final String url, final ImageView imageView, final ImageLoaderOptions options) {
        //装配基本的参数
        DrawableTypeRequest dtr = Glide.with(imageView.getContext()).load(url);
        //装配附加参数
        loadOptions(imageView.getContext(), dtr, options).into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                String httpUrl = ifHandleFailException(url, e);
                if (!TextUtils.isEmpty(httpUrl)) {
                    loadImage(httpUrl, imageView, options);
                } else {
                    super.onLoadFailed(e, errorDrawable);
                }
            }
        });
    }

    @Override
    public void loadImage(int drawable, ImageView imageView, ImageLoaderOptions options) {
        DrawableTypeRequest dtr = Glide.with(imageView.getContext()).load(drawable);
        loadOptions(imageView.getContext(), dtr, options).into(imageView);
    }

    @Override
    public void loadImage(final String url, final int placeholder, final ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).dontAnimate()
                .placeholder(placeholder).into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                String httpUrl = ifHandleFailException(url, e);
                if (!TextUtils.isEmpty(httpUrl)) {
                    loadImage(httpUrl, placeholder, imageView);
                } else {
                    super.onLoadFailed(e, errorDrawable);
                }
            }
        });
    }

    @Override
    public void loadImage(final String url, final int placeholder, final int errorRes, final ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).dontAnimate()
                .placeholder(placeholder).error(errorRes).into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                String httpUrl = ifHandleFailException(url, e);
                if (!TextUtils.isEmpty(httpUrl)) {
                    loadImage(httpUrl, placeholder, errorRes, imageView);
                } else {
                    super.onLoadFailed(e, errorDrawable);
                }
            }
        });
    }

    @Override
    public void loadImage(final Context context, final String url, final int placeholder, final ImageView imageView) {
        Glide.with(context).load(url).dontAnimate()
                .placeholder(placeholder).into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                String httpUrl = ifHandleFailException(url, e);
                if (!TextUtils.isEmpty(httpUrl)) {
                    loadImage(context, httpUrl, placeholder, imageView);
                } else {
                    super.onLoadFailed(e, errorDrawable);
                }
            }
        });
    }

    @Override
    public void loadImage(final Context context, final String url, final int placeholder, final int errorRes, final ImageView imageView) {
        Glide.with(context).load(url).dontAnimate()
                .placeholder(placeholder).error(errorRes).into(new GlideDrawableImageViewTarget(imageView) {
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                String httpUrl = ifHandleFailException(url, e);
                if (!TextUtils.isEmpty(httpUrl)) {
                    loadImage(context, httpUrl, placeholder, errorRes, imageView);
                } else {
                    super.onLoadFailed(e, errorDrawable);
                }
            }
        });
    }

    /**
     * 无holder的image加载
     *
     * @param url
     * @param imageView
     */
    @Override
    public void loadImage(final String url, final ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).dontAnimate()
                .placeholder(imageView.getDrawable())
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        String httpUrl = ifHandleFailException(url, e);
                        if (!TextUtils.isEmpty(httpUrl)) {
                            loadImage(httpUrl, imageView);
                        } else {
                            super.onLoadFailed(e, errorDrawable);
                        }
                    }
                });
    }

    @Override
    public void loadGifImage(String url, int placeholder, ImageView imageView) {
        loadGif(imageView.getContext(), url, placeholder, imageView);
    }

    @Override
    public void loadGifImage(String url, int placeholder, int errorRes, ImageView imageView) {
        loadGif(imageView.getContext(), url, placeholder, errorRes, imageView);
    }

    @Override
    public void loadImageWithProgress(String url, final ImageView imageView, final ProgressLoadListener listener) {
        Glide.with(imageView.getContext()).using(new ProgressModelLoader(new ProgressUIListener() {
            @Override
            public void update(final int bytesRead, final int contentLength) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.update(bytesRead, contentLength);
                    }
                });
            }
        })).load(url).asBitmap().dontAnimate().
                listener(new RequestListener<Object, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        listener.onException();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        listener.onResourceReady();
                        return false;
                    }

                }).into(imageView);
    }

    @Override
    public void loadGifWithProgress(String url, final ImageView imageView, final ProgressLoadListener listener) {
        Glide.with(imageView.getContext()).using(new ProgressModelLoader(new ProgressUIListener() {
            @Override
            public void update(final int bytesRead, final int contentLength) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.update(bytesRead, contentLength);
                    }
                });
            }
        })).load(url).skipMemoryCache(true).dontAnimate().diskCacheStrategy(DiskCacheStrategy.SOURCE).
                listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        listener.onException();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        listener.onResourceReady();
                        return false;
                    }
                }).into(imageView);
    }

    /**
     * 只想知道图片是否准备完毕（包括来自网络或者sdcard），区别于loadImageWithProgress和loadGifWithProgress的进度回调
     *
     * @param url
     * @param imageView
     * @param listener
     */
    @Override
    public void loadGifWithPrepareCall(String url, ImageView imageView, final SourceReadyListener listener) {
        Glide.with(imageView.getContext()).load(url).asGif().dontAnimate()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).
                listener(new RequestListener<String, GifDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        listener.onResourceReady(resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
                        return false;
                    }
                }).into(imageView);
    }

    @Override
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context.getApplicationContext()).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void trimMemory(Context context, int level) {
        Glide.get(context).trimMemory(level);
    }

    @Override
    public String getCacheSize(Context context) {
        try {
            return FileUtils.getFormatSize(FileUtils.getDirLength(Glide.getPhotoCacheDir(context.getApplicationContext())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 为其添加缓存策略,其中缓存策略可以为:Source及None,None及为不缓存,Source缓存原型.
     */
    private void loadNormal(final Context ctx, final String url, int placeholder, ImageView imageView) {
        //去掉动画 解决与CircleImageView冲突的问题 这个只是其中的一个解决方案
        //使用SOURCE 图片load结束再显示而不是先显示缩略图再显示最终的图片（导致图片大小不一致变化）
        Glide.with(ctx).load(url).dontAnimate()
                .placeholder(placeholder).into(imageView);
    }

    /**
     * load image with Glide
     */
    private void loadGif(final Context ctx, String url, int placeholder, ImageView imageView) {
        Glide.with(ctx).load(url).asGif().dontAnimate()
                .placeholder(placeholder).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GifDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
    }

    /**
     * load image with Glide
     */
    private void loadGif(final Context ctx, String url, int placeholder, int errorRes, ImageView imageView) {
        Glide.with(ctx).load(url).asGif().dontAnimate()
                .placeholder(placeholder)
                .error(errorRes)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).listener(new RequestListener<String, GifDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
    }

    /**
     * 这个方法用来装载由外部设置的参数
     *
     * @param dtr     DrawableTypeRequest
     * @param options 参数设置项
     * @return DrawableTypeRequest
     */
    private DrawableTypeRequest loadOptions(Context context, DrawableTypeRequest dtr, ImageLoaderOptions options) {
        if (options == null) {
            return dtr;
        }
        if (options.getPlaceHolder() != -1) {
            dtr.placeholder(options.getPlaceHolder());
        }
        if (options.getErrorDrawable() != -1) {
            dtr.error(options.getErrorDrawable());
        }
        if (options.isCircle()) {
            dtr.bitmapTransform(new CropCircleTransformation(context));
        } else {
            if (options.getRoundCornerRadius() > 0) {
                dtr.bitmapTransform(new RoundedCornersTransformation(context, options.getRoundCornerRadius()
                        , 0, RoundedCornersTransformation.CornerType.ALL));
            }
        }
        if (options.isCrossFade()) {
            dtr.crossFade();
        }
        if (options.isSkipMemoryCache()) {
            dtr.skipMemoryCache(options.isSkipMemoryCache());
        }
        if (options.getAnimator() != null) {
            dtr.animate(options.getAnimator());
        }
        return dtr;
    }

    private String ifHandleFailException(String imageUrl, Exception e) {
        if (!TextUtils.isEmpty(imageUrl) && e instanceof SSLHandshakeException) {
            return imageUrl.replaceFirst(IMAGE_HTTPS, IMAGE_HTTP);
        }
        return null;
    }
}
