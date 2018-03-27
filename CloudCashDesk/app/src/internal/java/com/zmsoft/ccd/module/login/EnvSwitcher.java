package com.zmsoft.ccd.module.login;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.AppEnv;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.helper.EnvSpHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.widget.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 06/06/2017.
 */
public final class EnvSwitcher {

    private View mView;
    private EditText mEditInputEnv;
    private RecyclerView mRecyclerEnv;
    private EnvAdapter mAdapter;
    private List<EnvBeanModel> mList = new ArrayList<>();
    private Context mContext;

    public void show(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        initView();
        initAdapter();
        initData();
        showSwitcherDialog();
    }

    private void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.layout_coustom_env, null);
        mEditInputEnv = (EditText) mView.findViewById(R.id.edit_input_env);
        mRecyclerEnv = (RecyclerView) mView.findViewById(R.id.recycler_env);
    }

    private void initAdapter() {
        // 设置布局类型
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerEnv.setLayoutManager(manager);

        mAdapter = new EnvAdapter(mContext, null);
        mRecyclerEnv.setAdapter(mAdapter);
        mAdapter.setRadioClick(new IRadioClick() {
            @Override
            public void radioClick(EnvBeanModel envBeanModel) {
                if (envBeanModel.isCheck()) {
                    return;
                }
                doItemClick(envBeanModel);
            }
        });
        mAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (data instanceof EnvBeanModel) {
                    EnvBeanModel envBean = (EnvBeanModel) data;
                    if (envBean.isCheck()) {
                        return;
                    }
                    doItemClick(envBean);
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
    }

    private void initData() {
        mEditInputEnv.setText(StringUtils.notNull(EnvSpHelper.getCustomEnv(mContext)));
        mEditInputEnv.setSelection(StringUtils.notNull(EnvSpHelper.getCustomEnv(mContext)).length());
        update(getResultList(AppEnv.getEnv()));
    }

    private void showSwitcherDialog() {
        final DialogUtil dialogUtil = new DialogUtil(mContext);
        dialogUtil.showCustomViewDialog(R.string.env_title
                , mView
                , R.string.at_once_change
                , true
                , new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            if (saveApiEnv()) {
                                dialogUtil.dismissDialog();
                            }
                        }
                    }
                });
    }

    private boolean saveApiEnv() {
        for (EnvBeanModel model : mList) {
            if (model.isCheck()) {
                if (model.getType() == AppEnv.CUSTOM) {
                    if (StringUtils.isEmpty(getCustomEnv())) {
                        ToastUtils.showShortToastSafe(mContext, "请输入自定义的env环境");
                        return false;
                    } else {
                        EnvSpHelper.saveCustomEnv(mContext, getCustomEnv());
                        AppEnv.switchTo(mContext, model.getType());
                        return true;
                    }
                } else {
                    AppEnv.switchTo(mContext, model.getType());
                    return true;
                }
            }
        }
        return false;
    }

    private String getCustomEnv() {
        return mEditInputEnv.getText().toString().trim();
    }

    private void update(List<EnvBeanModel> data) {
        mList.clear();
        mList.addAll(data);
        mAdapter.setList(mList);
        mAdapter.notifyDataSetChanged();
    }

    private void doItemClick(EnvBeanModel model) {
        if (model != null) {
            update(getResultList(model.getType()));
        }
    }

    private List<EnvBeanModel> getEnvModelList() {
        List<EnvBeanModel> list = new ArrayList<>();

        EnvBeanModel pre = new EnvBeanModel();
        pre.setName("预发环境");
        pre.setType(AppEnv.PRE);

        EnvBeanModel pub = new EnvBeanModel();
        pub.setName("生产环境");
        pub.setType(AppEnv.PUB);

        EnvBeanModel dev = new EnvBeanModel();
        dev.setName("开发环境");
        dev.setType(AppEnv.DEV);

        EnvBeanModel daily = new EnvBeanModel();
        daily.setName("日常环境");
        daily.setType(AppEnv.DAILY);

        EnvBeanModel custom = new EnvBeanModel();
        custom.setName("自定义环境");
        custom.setType(AppEnv.CUSTOM);

        list.add(pre);
        list.add(pub);
        list.add(dev);
        list.add(daily);
        list.add(custom);
        return list;
    }

    private List<EnvBeanModel> getResultList(int envType) {
        List<EnvBeanModel> list = getEnvModelList();
        for (int i = 0; i < list.size(); i++) {
            EnvBeanModel model = list.get(i);
            if (model.getType() == envType) {
                list.get(i).setCheck(true);
            } else {
                list.get(i).setCheck(false);
            }
        }
        return list;
    }
}
