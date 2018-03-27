package com.zmsoft.ccd.module.personal.feedback;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.DaggerUserComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.personal.feedback.dagger.DaggerFeedbackComponent;
import com.zmsoft.ccd.module.personal.feedback.dagger.FeedbackPresenterModule;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.personal.feedback.FeedBackActivity.PATH_FEEDBACK;

/**
 * 个人中心——意见反馈
 *
 * @author DangGui
 * @create 2016/12/15.
 */
@Route(path = PATH_FEEDBACK)
public class FeedBackActivity extends ToolBarActivity {

    public static final String PATH_FEEDBACK = "/main/feedback";

    private LinearLayout mLinearLayout;
    @Inject
    FeedbackPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_framelayout);

        FeedBackFragment feedBackFragment = (FeedBackFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);

        if (feedBackFragment == null) {
            feedBackFragment = FeedBackFragment.newInstance();
            ActivityHelper.showFragment(getSupportFragmentManager(), feedBackFragment, R.id.content);
        }

        DaggerFeedbackComponent.builder()
                .userComponent(DaggerUserComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .feedbackPresenterModule(new FeedbackPresenterModule(feedBackFragment))
                .build()
                .inject(this);

        mLinearLayout = (LinearLayout) findViewById(R.id.content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mLinearLayout.getWindowToken(), 0);
        super.onBackPressed();
    }
}
