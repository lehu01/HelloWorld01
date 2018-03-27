package com.zmsoft.ccd.module.main.message.detail.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderOptionsHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.lib.widget.imageloadutil.ImageLoaderUtil;
import com.zmsoft.ccd.module.main.message.detail.adapter.MessageDetailAdapter;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailFoodsTitleRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailRecyclerItem;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class MessageDetailFoodTitleViewholder extends MessageDetailBaseViewholder {
    protected ArrayList<DeskMsgDetailRecyclerItem> datas;
    protected RecyclerView recyclerView;
    protected MessageDetailAdapter recyclerItemAdapter;
    protected DeskMsgDetailFoodsTitleRecyclerItem foodsTitle;
    //item 布局
    @BindView(R.id.image_userhead)
    ImageView mIvUserHead;
    @BindView(R.id.text_username)
    TextView mTvUserName;
    @BindView(R.id.text_foods_count)
    TextView mTvFoodsCount;
    @BindView(R.id.text_time)
    TextView mTvTime;

    public MessageDetailFoodTitleViewholder(Context context, View itemView, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        super(context, itemView, recyclerView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, DeskMsgDetailRecyclerItem msgDetailRecyclerItemObj, int position) {
        super.initView(holder, msgDetailRecyclerItemObj, position);
        foodsTitle = msgDetailRecyclerItemObj.getFoodsTitle();
        if (null == foodsTitle)
            return;
        initInfoView();
        initListener(position);
    }

    protected void initInfoView() {
        if (!TextUtils.isEmpty(foodsTitle.getCustomerAvatarUrl())) {
            ImageLoaderUtil.getInstance().loadImage(foodsTitle.getCustomerAvatarUrl(), mIvUserHead,
                    ImageLoaderOptionsHelper.getCcdAvatarOptions());
        }
        if (!TextUtils.isEmpty(foodsTitle.getCustomerName())) {
            mTvUserName.setText(foodsTitle.getCustomerName());
        } else {
            mTvUserName.setText("");
        }
        mTvFoodsCount.setText(foodsTitle.getMenuNum());
        mTvTime.setText(foodsTitle.getShowTime());
    }

    protected void initListener(final int position) {
    }
}
