package com.zmsoft.ccd.module.menubalance.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.bean.menubalance.MenuBalanceVO;

import java.util.List;

/**
 * Created by jihuo on 2016/10/21.
 */

public class MenuBalanceAdapter extends BaseListAdapter {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public MenuBalanceAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_recycleview_menu_balance, null));
    }

    private class ViewHolder extends BaseHolder {
        TextView instanceName;
        TextView instanceSoldOutStatus;
        RelativeLayout menuItemContainer;
        View holderPlace;
        Context mContext;

        public ViewHolder(Context context, View itemView) {
            super(context, itemView);
            this.mContext = context;
            instanceName = (TextView) itemView.findViewById(R.id.instance_name);
            instanceSoldOutStatus = (TextView) itemView.findViewById(R.id.instance_sold_out_status);
            holderPlace = (View) itemView.findViewById(R.id.holder_place);
            menuItemContainer = (RelativeLayout) itemView.findViewById(R.id.menu_item_container);
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (null == bean) {
                return;
            }
            MenuBalanceVO menuBalanceVO = (MenuBalanceVO) bean;
            menuItemContainer.setVisibility(View.VISIBLE);
            holderPlace.setVisibility(View.GONE);
            instanceName.setText(menuBalanceVO.getMenuName());
            if (menuBalanceVO.getBalanceNum() == MenuBalanceVO.BALANCE_NUM_ZERO) {
                instanceSoldOutStatus.setText(mContext.getString(R.string.already_sold_out));
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(mContext.getString(R.string.left));
                stringBuilder.append(Html.fromHtml(String.format("<font color=\"#0088CC\">%.2f</font>", menuBalanceVO.getBalanceNum())));
                stringBuilder.append(mContext.getString(R.string.part));
                instanceSoldOutStatus.setText(stringBuilder.toString());
            }
        }
    }
}
