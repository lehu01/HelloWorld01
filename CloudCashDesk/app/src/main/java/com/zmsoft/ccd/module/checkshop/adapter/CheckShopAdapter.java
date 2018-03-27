package com.zmsoft.ccd.module.checkshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.shop.Shop;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/14 19:48
 */
public class CheckShopAdapter extends BaseListAdapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public CheckShopAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_list_check_shop, parent, false));
    }

    class ViewHolder extends BaseHolder {

        @BindView(R.id.image_shop_icon)
        ImageView mImageShopIcon;
        @BindView(R.id.text_shop_name)
        TextView mTextShopName;
        @BindView(R.id.text_shop_code)
        TextView mTextShopCode;
        @BindView(R.id.text_working)
        TextView mTextWorking;
        @BindView(R.id.text_shop_role_name)
        TextView mTextShopRoleName;

        public ViewHolder(Context context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }

        public void fillView(Object obj) {
            if (obj instanceof Shop) {
                Shop shop = (Shop) obj;
                mTextShopName.setText(shop.getShopName());
                mTextShopCode.setText(StringUtils.appendStr(GlobalVars.context.getString(R.string.shop_code), shop.getShopCode()));
                mTextShopRoleName.setText(String.format(GlobalVars.context.getString(R.string.shop_role_name), shop.getRoleName()));
                ImageLoaderUtil.getInstance().loadImage(shop.getShopPicture(), mImageShopIcon
                        , ImageLoaderOptionsHelper.getCcdShopRoundCornerOptions());

                if (shop.getWorkStatus() == Base.INT_TRUE) {
                    mTextWorking.setVisibility(View.VISIBLE);
                } else {
                    mTextWorking.setVisibility(View.GONE);
                }
            }
        }

        @Override
        protected void bindView(List source, Object bean) {
            fillView(bean);
        }
    }
}
