package com.zmsoft.ccd.lib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.lib.utils.PermissionUtils;
import com.zmsoft.ccd.lib.widget.R;
import com.zmsoft.ccd.lib.widget.dialog.permission.PermissionAdapter;
import com.zmsoft.ccd.lib.widget.dialog.permission.PermissionItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/29 17:19.
 */

public class AppPermissionDialog extends Dialog {
    static private final int[] PERMISSION_DRAWABLE_RESOURCE = {com.zmsoft.ccd.lib.widget.R.drawable.icon_dialog_permission_phone_state,
            com.zmsoft.ccd.lib.widget.R.drawable.icon_dialog_permission_camera,
            com.zmsoft.ccd.lib.widget.R.drawable.icon_dialog_permission_location,
            com.zmsoft.ccd.lib.widget.R.drawable.icon_dialog_permission_blue_tooth,
            com.zmsoft.ccd.lib.widget.R.drawable.icon_dialog_permission_phone_storage};


    public interface OnClickBottomListener{
        void onPositiveClick();
    }

    private OnClickBottomListener onClickBottomListener;

    private List<PermissionItem> mPermissionItemList = new ArrayList<>();


    public AppPermissionDialog(Context context) {
        super(context, R.style.AppPermissionDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_permission);
        initView();
    }

    //================================================================================
    // init
    //================================================================================
    private void initView() {
        setCanceledOnTouchOutside(false);
        TextView mTextNext = (TextView) findViewById(R.id.text_next);
        mTextNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onPositiveClick();
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_permission);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), mPermissionItemList.size()));

        PermissionAdapter permissionAdapter = new PermissionAdapter(this.getContext(), mPermissionItemList);
        recyclerView.setAdapter(permissionAdapter);
    }

    //================================================================================
    // setter
    //================================================================================
    public AppPermissionDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    /**
     *
     * @param unGrantedPermissions Manifest.permission类型数组
     * @return
     */
    public AppPermissionDialog setUnGrantPermissions(String[] unGrantedPermissions) {
        Map<String, Integer> textResourceMap = new HashMap<>();
        Map<String, Integer> imageResourceMap = new HashMap<>();
        for (int i = 0; i<PermissionUtils.REQUEST_PERMISSIONS.length; i++) {
            textResourceMap.put(PermissionUtils.REQUEST_PERMISSIONS[i], PermissionUtils.PERMISSION_TEXT_RESOURCE[i]);
            imageResourceMap.put(PermissionUtils.REQUEST_PERMISSIONS[i], PERMISSION_DRAWABLE_RESOURCE[i]);
        }

        for (String unGrantedPermission : unGrantedPermissions) {
            if (textResourceMap.containsKey(unGrantedPermission) && imageResourceMap.containsKey(unGrantedPermission)) {
                mPermissionItemList.add(new PermissionItem(imageResourceMap.get(unGrantedPermission), textResourceMap.get(unGrantedPermission)));
            }
        }
        return this;
    }
}
