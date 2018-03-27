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
import com.zmsoft.ccd.module.main.MainActivity;
import com.zmsoft.ccd.module.shortcutreceipt.dagger.DaggerShortCutReceiptComponent;
import com.zmsoft.ccd.module.shortcutreceipt.dagger.ShortCutReceiptPresenterModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 快捷收款
 *
 * @author DangGui
 * @create 2017/8/2.
 */
@Route(path = RouterPathConstant.ShortCutReceipt.PATH)
public class ShortCutReceiptActivity extends ToolBarActivity {
    @Inject
    ShortCutReceiptPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_framelayout);

        ShortCutReceiptFragment shortCutReceiptFragment = (ShortCutReceiptFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);

        if (shortCutReceiptFragment == null) {
            shortCutReceiptFragment = ShortCutReceiptFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), shortCutReceiptFragment, R.id.content);
        }

        clearOtherActivity();

        DaggerShortCutReceiptComponent.builder()
                .shortCutReceiptDataComponent(DaggerShortCutReceiptDataComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .shortCutReceiptPresenterModule(new ShortCutReceiptPresenterModule(shortCutReceiptFragment))
                .build()
                .inject(this);
    }

    public static void launchActivity(Context context) {
        Intent intent = new Intent(context, ShortCutReceiptActivity.class);
        context.startActivity(intent);
    }

    /**
     * 继续收款，进入快捷收款界面，关闭之前的页面
     */
    private void clearOtherActivity() {
        List<Class> closeClassList = new ArrayList<>(2);
        closeClassList.add(MainActivity.class);
        closeClassList.add(ShortCutReceiptActivity.class);
        RouterActivityManager.get().finishAllActivityExcept(closeClassList);
    }
}
