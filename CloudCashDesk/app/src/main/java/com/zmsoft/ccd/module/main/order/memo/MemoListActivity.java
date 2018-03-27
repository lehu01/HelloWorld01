package com.zmsoft.ccd.module.main.order.memo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.systemdircode.dagger.DaggerSystemDirCodeComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.main.order.memo.dagger.DaggerMemoListPresenterComponent;
import com.zmsoft.ccd.module.main.order.memo.dagger.MemoListPresenterModule;
import com.zmsoft.ccd.module.main.order.memo.fragment.MemoListFragment;
import com.zmsoft.ccd.module.main.order.memo.fragment.MemoListPresenter;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.main.order.memo.MemoListActivity.PATH_MEMO;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 15:52
 */
@Route(path = PATH_MEMO)
public class MemoListActivity extends ToolBarActivity {

    public static final String PATH_MEMO = "/main/memo";
    public static final String EXTRA_MEMO = "memo";
    public static final int MENU_ITEM_ONE = 1;

    @Autowired(name = EXTRA_MEMO)
    String mMemo;

    @Inject
    MemoListPresenter mPresenter;

    private MemoListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark);

        fragment = (MemoListFragment) getSupportFragmentManager().findFragmentById(R.id.linear_remark);
        if (fragment == null) {
            fragment = MemoListFragment.newInstance(mMemo);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.linear_remark);
        }

        DaggerMemoListPresenterComponent.builder()
                .systemDirCodeComponent(DaggerSystemDirCodeComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .memoListPresenterModule(new MemoListPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, MENU_ITEM_ONE, MENU_ITEM_ONE, getString(R.string.save));
        item.setIcon(R.drawable.icon_save);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ITEM_ONE) {
            fragment.save();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
