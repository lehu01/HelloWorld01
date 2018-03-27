package com.zmsoft.ccd.lib.utils.imageloadutil;

import com.bumptech.glide.request.animation.ViewPropertyAnimation;

/**
 * ImageLoaderOptions属性设置
 * 可以把主流图片加载框架（Glide、Picasso、Fresco）所有的共同或相似设置项搬过来，现在仅仅摘取以下几种属性。
 *
 * @author DangGui
 * @create 2017/3/10.
 */

public class ImageLoaderOptions {
    /**
     * 当图片正在加载的时候显示的图片
     */
    private int mPlaceHolder = -1;
    /**
     * 加载错误的时候显示的drawable
     */
    private int mErrorDrawable = -1;
    /**
     * 是否是圆形图片
     */
    private boolean mIsCircle = false;
    /**
     * 圆角图片弧度
     */
    private int mRoundCornerRadius = 0;
    /**
     * 是否渐变平滑的显示图片
     */
    private boolean mIsCrossFade = false;
    /**
     * 是否跳过内存缓存
     */
    private boolean mIsSkipMemoryCache = false;
    /**
     * 图片加载动画
     */
    private ViewPropertyAnimation.Animator mAnimator = null;

    private ImageLoaderOptions(int placeHolder, int errorDrawable, boolean isCircle, int roundCornerRadius
            , boolean isCrossFade, boolean isSkipMemoryCache
            , ViewPropertyAnimation.Animator animator) {
        this.mPlaceHolder = placeHolder;
        this.mErrorDrawable = errorDrawable;
        this.mIsCircle = isCircle;
        this.mRoundCornerRadius = roundCornerRadius;
        this.mIsCrossFade = isCrossFade;
        this.mIsSkipMemoryCache = isSkipMemoryCache;
        this.mAnimator = animator;
    }

    public int getPlaceHolder() {
        return mPlaceHolder;
    }

    public int getErrorDrawable() {
        return mErrorDrawable;
    }

    public boolean isCircle() {
        return mIsCircle;
    }

    public int getRoundCornerRadius() {
        return mRoundCornerRadius;
    }

    public boolean isCrossFade() {
        return mIsCrossFade;
    }

    public boolean isSkipMemoryCache() {
        return mIsSkipMemoryCache;
    }

    public ViewPropertyAnimation.Animator getAnimator() {
        return mAnimator;
    }

    public static final class Builder {
        private int placeHolder = -1;
        private int errorDrawable = -1;
        private boolean isCircle = false;
        private int roundCornerRadius = 0;
        private boolean isCrossFade = false;
        private boolean isSkipMemoryCache = false;
        private ViewPropertyAnimation.Animator animator = null;

        public Builder() {
        }

        public Builder placeHolder(int drawable) {
            this.placeHolder = drawable;
            return this;
        }

        public Builder anmiator(ViewPropertyAnimation.Animator animator) {
            this.animator = animator;
            return this;
        }

        public Builder errorDrawable(int errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        public Builder isCircle(boolean isCircle) {
            this.isCircle = isCircle;
            return this;
        }

        public Builder roundCornerRadius(int roundCornerRadius) {
            this.roundCornerRadius = roundCornerRadius;
            return this;
        }

        public Builder isCrossFade(boolean isCrossFade) {
            this.isCrossFade = isCrossFade;
            return this;
        }

        public Builder isSkipMemoryCache(boolean isSkipMemoryCache) {
            this.isSkipMemoryCache = isSkipMemoryCache;
            return this;
        }

        public ImageLoaderOptions build() {
            return new ImageLoaderOptions(this.placeHolder, this.errorDrawable, this.isCircle,
                    this.roundCornerRadius, this.isCrossFade, this.isSkipMemoryCache, this.animator);
        }
    }
}