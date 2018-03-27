package com.zmsoft.ccd.module.takeout.order.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.utils.KeyboardUtils;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.R2;

import butterknife.BindView;

/**
 * Description：外卖单搜索
 * <br/>
 * Created by kumu on 2017/8/29.
 */

public class RetailSearchTakeoutActivity extends ToolBarActivity {
    @BindView(R2.id.edit_search)
    EditText mEditSearch;
    private RetailTakeoutListFragment mTakeoutListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_takeout_activity_takeout_search);

        if (savedInstanceState != null) {
            mTakeoutListFragment = (RetailTakeoutListFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        }

        if (mTakeoutListFragment == null) {
            mTakeoutListFragment = RetailTakeoutListFragment.createForSearch();
            Bundle bundle = new Bundle();
            bundle.putBoolean("fromSearch", true);
            mTakeoutListFragment.setArguments(bundle);
            mTakeoutListFragment.disableRefresh();
        }
        ActivityHelper.showFragment(getSupportFragmentManager(), mTakeoutListFragment, R.id.container, false);

        initSearchView();
    }

    private void initSearchView() {
        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String key = mEditSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(key)) {
                        KeyboardUtils.hideSoftInput(RetailSearchTakeoutActivity.this);
                        if (mTakeoutListFragment != null) {
                            mTakeoutListFragment.setParams(key, null);
                            mTakeoutListFragment.startRefresh(false, true);
                        }
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_OK);
    }

    @Override
    protected void handleBack() {
        super.handleBack();
        setResult(Activity.RESULT_OK);
    }

    public static void launchActivity(Fragment fragment, int requestCode) {
        if (null == fragment) return;
        Intent intent = new Intent(fragment.getContext(), RetailSearchTakeoutActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }
}
