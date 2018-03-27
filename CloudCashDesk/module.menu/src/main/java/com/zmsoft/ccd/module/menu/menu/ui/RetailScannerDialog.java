package com.zmsoft.ccd.module.menu.menu.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerAdapter;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.scan.RetailScannerAdapter;
import com.zmsoft.ccd.module.menu.cart.view.RetailMenuScannerActivity;
import com.zmsoft.ccd.module.menu.menu.bean.BoMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huaixi on 2017/11/6.
 */

public class RetailScannerDialog extends DialogFragment {

    @BindView(R2.id.image_close)
    ImageView mImageClose;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;

    private ArrayList<BoMenu> boMenuList;
    private RetailScannerAdapter retailScannerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.module_menu_dialog_retail_scanner_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            boMenuList = bundle.getParcelableArrayList("bo_menu");
        }

        initRecycler(boMenuList);

        initListener();
    }

    private void initListener() {
        mImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
                ((RetailMenuScannerActivity) getActivity()).continueScanning();
            }
        });
    }

    private void initRecycler(List<BoMenu> boMenuList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        retailScannerAdapter = new RetailScannerAdapter(getContext(), recyclerView, boMenuList);
        recyclerView.setAdapter(retailScannerAdapter);
        retailScannerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (data instanceof BoMenu) {
                    ((RetailMenuScannerActivity) getActivity()).scannerShopClicked((BoMenu) data);
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        ((RetailMenuScannerActivity) getActivity()).continueScanning();
        super.onStop();
    }
}
