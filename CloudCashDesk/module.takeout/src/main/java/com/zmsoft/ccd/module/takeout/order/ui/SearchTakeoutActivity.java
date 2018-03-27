package com.zmsoft.ccd.module.takeout.order.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class SearchTakeoutActivity extends ToolBarActivity {
    @BindView(R2.id.edit_search)
    EditText mEditSearch;
    private TakeoutListFragment mTakeoutListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_takeout_activity_takeout_search);

        if (savedInstanceState != null) {
            mTakeoutListFragment = (TakeoutListFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        }

        if (mTakeoutListFragment == null) {
            mTakeoutListFragment = TakeoutListFragment.createForSearch();
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
                        KeyboardUtils.hideSoftInput(SearchTakeoutActivity.this);
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
}
