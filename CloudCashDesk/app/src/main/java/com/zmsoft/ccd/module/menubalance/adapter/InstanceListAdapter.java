package com.zmsoft.ccd.module.menubalance.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseExpandableAdapter;
import com.zmsoft.ccd.lib.base.adapter.ViewHolder;
import com.zmsoft.ccd.lib.bean.menubalance.Menu;

import java.util.List;

/**
 * Created by jihuo on 2016/10/27.
 */

public class InstanceListAdapter extends BaseExpandableAdapter{

    Context context;

    public InstanceListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getExGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String instanceSortName = (String)parentList.get(groupPosition);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.instance_list_parent_view, null);
        }
        TextView instanceSort = ViewHolder.get(convertView, R.id.sort_title_text);
        instanceSort.setText(instanceSortName);
        return convertView;
    }

    @Override
    public View getExChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        List<Menu> menuList = (List<Menu>)childList.get(groupPosition);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.instance_list_child_view, null);
        }
        TextView instanceItemName = ViewHolder.get(convertView, R.id.select_instance_item);
        instanceItemName.setText(menuList.get(childPosition).getMenuName());
        return convertView;
    }
}
