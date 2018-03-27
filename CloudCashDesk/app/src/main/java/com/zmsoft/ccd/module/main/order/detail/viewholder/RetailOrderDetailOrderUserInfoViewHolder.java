package com.zmsoft.ccd.module.main.order.detail.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.bean.instance.PersonInfo;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/22 18:10
 */
public class RetailOrderDetailOrderUserInfoViewHolder extends RetailOrderDetailBaseViewHolder {

    @BindView(R.id.image_user_icon)
    ImageView mImageUserIcon;
    @BindView(R.id.text_user_nice)
    TextView mTextUserNice;
    @BindView(R.id.view_line)
    View mViewLine;

    public RetailOrderDetailOrderUserInfoViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (bean != null && bean instanceof OrderDetailItem) {
            OrderDetailItem item = (OrderDetailItem) bean;
            fillView(item.getPersonInfo());
        }
    }

    private void fillView(PersonInfo userInfo) {
        if (userInfo != null) {
            if (null != userInfo.getCustomerName()) {
                showUserInfo();
                String name = StringUtils.appendStr(getContext().getString(R.string.home_electronic_detail_vip)
                        , getContext().getString(R.string.risk)
                        , userInfo.getCustomerName(), " " + getContext().getString(R.string.left_brackets) + userInfo.getMobile() + getContext().getString(R.string.right_brackets));
                ImageLoaderUtil.getInstance().loadImage(userInfo.getFileUrl(), mImageUserIcon,
                        ImageLoaderOptionsHelper.getCcdAvatarOptions());
                mTextUserNice.setText(name);
            } else {
                dismissUserInfo();
            }
        } else {
            if (userInfo.getCustomerName() == null) {
                dismissUserInfo();
            }
        }
    }

    private void dismissUserInfo() {
        mImageUserIcon.setVisibility(View.GONE);
        mTextUserNice.setVisibility(View.GONE);
        mViewLine.setVisibility(View.GONE);
    }

    private void showUserInfo() {
        mImageUserIcon.setVisibility(View.VISIBLE);
        mTextUserNice.setVisibility(View.VISIBLE);
        mViewLine.setVisibility(View.VISIBLE);
    }
}
