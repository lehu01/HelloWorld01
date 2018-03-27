package com.zmsoft.ccd.lib.widget.dialog.permission;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zmsoft.ccd.lib.utils.language.LanguageUtil;
import com.zmsoft.ccd.lib.widget.R;

import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/29 19:44.
 */

public class PermissionAdapter extends RecyclerView.Adapter<PermissionViewHolder> {

    private interface TEXT_EMS {
        int CHINESE = 2;
        int NOT_CHINESE = 10;
    }

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<PermissionItem> mPermissionItemList;

    public PermissionAdapter(Context context, List<PermissionItem> mPermissionItemList) {
        this.mContext = context;
        this.mLayoutInflater =  LayoutInflater.from(context);
        this.mPermissionItemList = mPermissionItemList;
    }

    //================================================================================
    // RecyclerView.Adapter
    //================================================================================
    @Override
    public PermissionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PermissionViewHolder(mLayoutInflater.inflate(R.layout.item_dialog_permission, parent, false));
    }

    @Override
    public void onBindViewHolder(PermissionViewHolder holder, int position) {
        PermissionItem permissionItem = mPermissionItemList.get(position);
        holder.mTextView.setText(mContext.getString(permissionItem.getTextResource()));
        holder.mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, permissionItem.getImageResource()));

        if (LanguageUtil.isChineseGroup()) {
            holder.mTextView.setEms(TEXT_EMS.CHINESE);
        } else {
            holder.mTextView.setEms(TEXT_EMS.NOT_CHINESE);
        }
    }

    @Override
    public int getItemCount() {
        return mPermissionItemList == null? 0: mPermissionItemList.size();
    }
}
