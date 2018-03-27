package com.zmsoft.ccd.module.shortcutreceipt;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chiclaim.modularization.router.annotation.Route;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.shortcutreceipt.dagger.DaggerShortCutReceiptDataComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.shortcutreceipt.dagger.DaggerRetailShortCutReceiptComponent;
import com.zmsoft.ccd.module.shortcutreceipt.dagger.RetailShortCutReceiptPresenterModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 快捷收款
 *
 * @author DangGui
 * @create 2017/8/2.
 */
@Route(path = RouterPathConstant.RetailShortCutReceipt.PATH)
public class RetailShortCutReceiptActivity extends ToolBarActivity {
    @Inject
    RetailShortCutReceiptPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_framelayout);

        RetailShortCutReceiptFragment shortCutReceiptFragment = (RetailShortCutReceiptFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);

        if (shortCutReceiptFragment == null) {
            shortCutReceiptFragment = RetailShortCutReceiptFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), shortCutReceiptFragment, R.id.content);
        }

        clearOtherActivity();
        DaggerRetailShortCutReceiptComponent.builder()
                .shortCutReceiptDataComponent(DaggerShortCutReceiptDataComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .retailShortCutReceiptPresenterModule(new RetailShortCutReceiptPresenterModule(shortCutReceiptFragment))
                .build()
                .inject(this);
    }

    public static void launchActivity(Context context) {
        Intent intent = new Intent(context, RetailShortCutReceiptActivity.class);
        context.startActivity(intent);
    }

    /**
     * 继续收款，进入快捷收款界面，关闭之前的页面
     */
    private void clearOtherActivity() {
        List<String> closeClassList = new ArrayList<>(2);
        closeClassList.add(RouterPathConstant.PATH_RETAIL_MAIN_ACTIVITY);
        closeClassList.add(RouterPathConstant.RetailShortCutReceipt.PATH);
        RouterActivityManager.get().finishAllActivityExcept(closeClassList);
    }
}
