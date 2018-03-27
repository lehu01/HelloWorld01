package com.zmsoft.ccd.module.menu.menu.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.lib.base.activity.BaseActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitMenuVO;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description：套餐介绍Activity
 * <br/>
 * Created by kumu on 2017/4/19.
 */
@Route(path = RouterPathConstant.SuitNote.PATH)
public class SuitNoteActivity extends BaseActivity {

    @BindView(R2.id.text_toolbar_left)
    TextView mTextToolbarLeft;
    @BindView(R2.id.text_toolbar_title)
    TextView mTextToolbarTitle;
    @BindView(R2.id.text_toolbar_right)
    TextView mTextToolbarRight;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;


    private SuitNoteFragment mSuitNoteFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_menu_activity_suit_note);
        initViews(savedInstanceState);

    }

    private void initViews(Bundle savedInstanceState) {
        mTextToolbarTitle.setText(R.string.module_menu_suit_title);

        if (savedInstanceState != null) {
            mSuitNoteFragment = (SuitNoteFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        }

        if (mSuitNoteFragment == null) {
            SuitMenuVO suitMenuVO = getIntent().getParcelableExtra(RouterPathConstant.SuitNote.PARAM_SUITMENOVO);
            mSuitNoteFragment = new SuitNoteFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(RouterPathConstant.SuitNote.PARAM_SUITMENOVO, suitMenuVO);
            mSuitNoteFragment.setArguments(bundle);
        }
        ActivityHelper.showFragment(getSupportFragmentManager(), mSuitNoteFragment, R.id.content, false);
    }


    @OnClick({R2.id.text_toolbar_left})
    void processClick(View view) {
        int i = view.getId();
        if (i == R.id.text_toolbar_left) {
            finish();
        }
    }
}
