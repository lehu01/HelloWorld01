package com.zmsoft.ccd.module.main.order.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/19 20:14
 */
public class RetailOrderStatusAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public RetailOrderStatusAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_filter_order, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mTextFilter.setText(list.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.mTextFilter.setTextColor(context.getResources().getColor(R.color.filter_button_color));
                viewHolder.mTextFilter.setBackgroundResource(R.drawable.shape_filter_check);
                viewHolder.mImageFilterCheck.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mTextFilter.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                viewHolder.mTextFilter.setBackgroundResource(R.drawable.shape_filter_uncheck);
                viewHolder.mImageFilterCheck.setVisibility(View.GONE);
            }
        }
    }

    class ViewHolder {
        @BindView(R.id.text_filter)
        TextView mTextFilter;
        @BindView(R.id.image_filter_check)
        ImageView mImageFilterCheck;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
