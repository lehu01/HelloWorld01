package com.zmsoft.ccd.module.personal.feedback;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心——意见反馈
 *
 * @author DangGui
 * @create 2016/12/15.
 */

public class FeedBackFragment extends BaseFragment implements FeedbackContract.View {

    @BindView(R.id.edit_feedback_input)
    EditText mEditFeedbackInput;
    @BindView(R.id.edit_feedback_email)
    EditText mEditFeedbackEmail;
    @BindView(R.id.button_send_feedback)
    Button mButtonSendFeedback;

    private FeedbackContract.Presenter mPresenter;

    public static FeedBackFragment newInstance() {
        Bundle args = new Bundle();
        FeedBackFragment fragment = new FeedBackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setPresenter(FeedbackContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {

    }

    public void initListener() {
    }

    @OnClick(R.id.button_send_feedback)
    public void onClick() {
        if (TextUtils.isEmpty(mEditFeedbackInput.getText().toString().trim())) {
            toastMsg(getString(R.string.person_feedback_send_feedback_empty_hint));
            return;
        }
        mPresenter.feedBackTask(mEditFeedbackInput.getText().toString().trim(), mEditFeedbackEmail.getText().toString().trim());
    }

    @Override
    public void finishView() {
        getActivity().finish();
    }

    @Override
    public void sendSuccess(String successMessage) {
        toastMsg(successMessage);
    }

    @Override
    public void loadDataError(String errorMessage) {
        toastMsg(errorMessage);
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }
}
