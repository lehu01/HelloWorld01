package com.zmsoft.ccd.lib.base.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.List;

/**
 * Created by jihuo on 2016/10/26.
 */

public abstract class BaseExpandableAdapter<T> extends BaseExpandableListAdapter {

    private static final String TAG = "ExpandableListAdapter";

    Context context;

    protected LayoutInflater inflater;

    protected ExpandableListView expandableListView;

    protected List<String> parentList;

    protected List<List<T>> childList;

    public BaseExpandableAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<String> parentList, List<List<T>> childList){
        if (parentList == null || childList == null || parentList.size() != childList.size()){
            Log.e(TAG, "分组列表数据为空或父列表与子列表的长度不同");
            return;
        }
        this.parentList = parentList;
        this.childList = childList;
        notifyDataSetChanged();
    }

    public void clear() {
        parentList.clear();
        childList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public int getGroupCount() {
        if (parentList == null || parentList.size() == 0){
            return 0;
        }
        return parentList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (childList == null || childList.size() == 0){
            return 0;
        }
        return childList.get(i).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        if (groupPosition > parentList.size() - 1) {
            return null;
        }
        return parentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (groupPosition < parentList.size() && childPosition < childList.get(groupPosition).size() ){
            return childList.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        expandableListView = (ExpandableListView) parent;
        return getExGroupView(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return getExChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    }

    public abstract View getExGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);

    public abstract View getExChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);
}
