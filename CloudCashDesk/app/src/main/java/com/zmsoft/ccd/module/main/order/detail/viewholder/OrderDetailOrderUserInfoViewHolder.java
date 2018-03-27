package com.zmsoft.ccd.module.main.order.detail.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.instance.PersonInfo;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.TimeUtils;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/22 18:10
 */
public class OrderDetailOrderUserInfoViewHolder extends OrderDetailBaseViewHolder {

    @BindView(R.id.image_user_icon)
    ImageView mImageUserIcon;
    @BindView(R.id.text_instance_all_count)
    TextView mTextInstanceAllCount;
    @BindView(R.id.text_user_nice)
    TextView mTextUserNice;
    @BindView(R.id.text_open_time)
    TextView mTextOpenTime;

    public OrderDetailOrderUserInfoViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
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
            String name = "";
            mTextOpenTime.setText(TimeUtils.getTimeStr(userInfo.getCreateTime(), TimeUtils.FORMAT_TIME));
            mTextInstanceAllCount.setText(StringUtils.appendStr(userInfo.getInstanceNum(), getContext().getString(R.string.part)));
            // 必选菜
            if ("consumer_system".equals(userInfo.getCustomerRegisterId())) {
                User user = UserLocalPrefsCacheSource.getUser();
                // 是服务员点菜
                name = StringUtils.appendStr(getContext().getString(R.string.default_waiter_name)
                        , getContext().getString(R.string.risk)
                        , user.getMemberName());
                ImageLoaderUtil.getInstance().loadImage(user.getPicFullPath(), mImageUserIcon,
                        ImageLoaderOptionsHelper.getCcdAvatarOptions());
            } else {
                // 服务员下单：二维火收银机，服务生
                if (StringUtils.isEmpty(userInfo.getCustomerName()) || StringUtils.isEmpty(userInfo.getCustomerRegisterId())) {
                    name = getContext().getString(R.string.default_waiter_name);
                } else {
                    // 判断是否是服务员下单：云收银
                    if (Base.INT_TRUE == userInfo.getFromCustomer()) {
                        name = userInfo.getCustomerName();
                    } else {
                        name = StringUtils.appendStr(getContext().getString(R.string.default_waiter_name)
                                , getContext().getString(R.string.risk)
                                , userInfo.getCustomerName());
                    }
                }
                ImageLoaderUtil.getInstance().loadImage(userInfo.getFileUrl(), mImageUserIcon,
                        ImageLoaderOptionsHelper.getCcdAvatarOptions());
            }
            mTextUserNice.setText(name);
        }
    }
}
