package com.zmsoft.ccd.module.menu.cart.adapter.scan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerAdapter;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.utils.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.lib.widget.ShopSpecialityView;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.menu.bean.BoMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huaixi on 2017/11/9.
 */

public class RetailScannerAdapter extends BaseRecyclerAdapter<BoMenu> {

    private LayoutInflater mLayoutInflater;
    private List<BoMenu> boMenuList;
    private Context mContext;

    public RetailScannerAdapter(Context context, RecyclerView v, List<BoMenu> data) {
        super(v, data);
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        boMenuList = data;
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RetailScannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.module_menu_item_retail_scanner_menu, parent, false), this);
    }

    class RetailScannerViewHolder extends BaseRecyclerHolder<BoMenu> {

        @BindView(R2.id.image_menu_icon)
        ImageView imageMenuIcon;
        @BindView(R2.id.view_shade)
        View viewShade;
        @BindView(R2.id.image_menu_icon_sell_out)
        ImageView imageMenuIconSellOut;
        @BindView(R2.id.frame_image_menu_icon)
        RelativeLayout frameImageMenuIcon;
        @BindView(R2.id.text_weigh_food_flag)
        TextView textWeighFoodFlag;
        @BindView(R2.id.text_food_name)
        TextView textFoodName;
        @BindView(R2.id.shop_speciality_view)
        ShopSpecialityView shopSpecialityView;
        @BindView(R2.id.layout_menu_name)
        LinearLayout layoutMenuName;
        @BindView(R2.id.text_bar_code)
        TextView textBarCode;
        @BindView(R2.id.text_food_unit_price)
        TextView textFoodUnitPrice;

        public RetailScannerViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
            super(context, itemView, adapter);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void initView(BaseRecyclerHolder holder, BoMenu item, int position) {
            if (null != item) {
                fillView(item);
            }
        }

        private void fillView(BoMenu item) {
            //商品图片
            if (item.getImagePath() != null) {
                ImageLoaderUtil.getInstance().loadImage(item.getImagePath(), imageMenuIcon
                        , ImageLoaderOptionsHelper.getCcdGoodsRoundCornerOptions());
            }

            //商品名
            textFoodName.setText(StringUtils.getNullStr(item.getName()));

            //称重标识
            if (item.getIsTwoAccount() == 1) {
                textWeighFoodFlag.setVisibility(View.VISIBLE);
            } else {
                textWeighFoodFlag.setVisibility(View.GONE);
            }

            //条形码
            if (item.getCode() != null) {
                textBarCode.setText(StringUtils.getNullStr(item.getCode()));
            } else {
                textBarCode.setText(context.getString(R.string.module_menu_retail_no_barcode_label));
            }

            //价格单位
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(String.format(mContext.getResources().getString(R.string.module_menu_list_placeholder_price_unit)
                    , NumberUtils.getDecimalFee(item.getPrice(), 2)
                    , item.getAccount()));

            int divide = builder.toString().indexOf('/');
            ForegroundColorSpan redSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.module_menu_price_red));
            builder.setSpan(redSpan, 0, divide, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ForegroundColorSpan graySpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.module_menu_black));
            builder.setSpan(graySpan, divide, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textFoodUnitPrice.setText(builder);
        }
    }
}
