package com.zmsoft.ccd.lib.widget.dialog.permission;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.lib.widget.R;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/29 19:24.
 */

public class PermissionViewHolder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mTextView;

    public PermissionViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.image_permission);
        mTextView = (TextView) itemView.findViewById(R.id.text_permission);
    }
}
