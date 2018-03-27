package com.zmsoft.ccd.module.personal.feedback;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.UserDataRepository;

import javax.inject.Inject;

/**
 * @author DangGui
 * @create 2016/12/16.
 */

public class FeedbackPresenter implements FeedbackContract.Presenter {

    private FeedbackContract.View mView;
    private final UserDataRepository mUserDataRepository;

    @Inject
    FeedbackPresenter(FeedbackContract.View view, UserDataRepository userDataRepository) {
        this.mView = view;
        this.mUserDataRepository = userDataRepository;
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void feedBackTask(String feedBackContent, String email) {
        mView.showLoading(GlobalVars.context.getString(R.string.dialog_sending), false);
        mUserDataRepository.feedback(feedBackContent, email, new com.zmsoft.ccd.data.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                if (data) {
                    mView.sendSuccess(GlobalVars.context.getString(R.string.person_feedback_send_feedback_success));
                    mView.finishView();
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
