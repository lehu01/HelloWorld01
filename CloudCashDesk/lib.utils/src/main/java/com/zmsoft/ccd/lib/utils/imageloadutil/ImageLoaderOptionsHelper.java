package com.zmsoft.ccd.lib.utils.imageloadutil;


import com.zmsoft.ccd.lib.utils.R;

/**
 * ImageLoaderOptions获取配置工具类
 *
 * @author DangGui
 * @create 2017/3/10.
 */

public class ImageLoaderOptionsHelper {

    //云收银 会员卡属性设置
    public static final ImageLoaderOptions getCcdVipCardOptions() {
        return CcdCcdVipCardOptionsHolder.INSTANCE;
    }

    private static class CcdCcdVipCardOptionsHolder {
        private static final ImageLoaderOptions INSTANCE = new ImageLoaderOptions.Builder()
                .placeHolder(R.drawable.icon_default_vip_card)
                .errorDrawable(R.drawable.icon_default_vip_card)
                .roundCornerRadius(10)
                .isCrossFade(true)
                .build();
    }

    //云收银 头像属性设置
    public static final ImageLoaderOptions getCcdAvatarOptions() {
        return CcdAvatarOptionsHolder.INSTANCE;
    }

    private static class CcdAvatarOptionsHolder {
        private static final ImageLoaderOptions INSTANCE = new ImageLoaderOptions.Builder()
                .placeHolder(R.drawable.icon_user_default)
                .errorDrawable(R.drawable.icon_user_default)
                .isCircle(true)
                .isCrossFade(true)
                .build();
    }

    //云收银 店铺圆角logo图片属性设置
    public static final ImageLoaderOptions getCcdShopRoundCornerOptions() {
        return CcdShopRoundCornerOptionsHolder.INSTANCE;
    }

    private static class CcdShopRoundCornerOptionsHolder {
        private static final ImageLoaderOptions INSTANCE = new ImageLoaderOptions.Builder()
                .placeHolder(R.drawable.icon_shop_default)
                .errorDrawable(R.drawable.icon_shop_default)
                .roundCornerRadius(10)
                .isCrossFade(true)
                .build();
    }

    //云收银 商品logo图片属性设置
    public static final ImageLoaderOptions getCcdGoodsRoundCornerOptions() {
        return CcdGoodsRoundCornerOptionsHolder.INSTANCE;
    }

    private static class CcdGoodsRoundCornerOptionsHolder {
        private static final ImageLoaderOptions INSTANCE = new ImageLoaderOptions.Builder()
                .placeHolder(R.drawable.icon_goods_default)
                .errorDrawable(R.drawable.icon_goods_default)
                .roundCornerRadius(6)
                .isCrossFade(true)
                .build();
    }
}
