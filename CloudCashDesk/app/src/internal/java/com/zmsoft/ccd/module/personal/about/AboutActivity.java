package com.zmsoft.ccd.module.personal.about;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.BuildConfig;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zmsoft.ccd.module.personal.about.AboutActivity.PATH_ABOUT;

/**
 * 个人中心——关于二维火
 *
 * @author DangGui
 * @create 2016/12/15.
 */

@Route(path = PATH_ABOUT)
public class AboutActivity extends ToolBarActivity {

    public static final String PATH_ABOUT = "/main/about";

    @BindView(R.id.text_version)
    TextView mTextVersion;
    @BindView(R.id.text_git_hash)
    TextView mTextGitHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTextVersion.setText(BuildConfig.VERSION_NAME);
        mTextGitHash.setVisibility(View.VISIBLE);
        mTextGitHash.setText(BuildConfig.GitHash);
    }
}
