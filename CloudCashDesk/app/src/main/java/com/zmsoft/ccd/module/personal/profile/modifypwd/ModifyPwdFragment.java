package com.zmsoft.ccd.module.personal.profile.modifypwd;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.helper.RegularlyHelper;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.functions.Func3;

/**
 * 个人中心——修改密码
 *
 * @author DangGui
 * @create 2016/12/16.
 */

public class ModifyPwdFragment extends BaseFragment implements ModifyPwdContract.View {

    @BindView(R.id.edit_old_pwd)
    EditText mEditOldPwd;
    @BindView(R.id.edit_new_pwd)
    EditText mEditNewPwd;
    @BindView(R.id.edit_confirm_pwd)
    EditText mEditConfirmPwd;
    @BindView(R.id.button_sure)
    Button mButtonSure;

    private ModifyPwdContract.Presenter mPresenter;

    public static ModifyPwdFragment newInstance() {
        Bundle args = new Bundle();
        ModifyPwdFragment fragment = new ModifyPwdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modifypwd;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    public void initListener() {
        Observable<CharSequence> etOldPwdObservable = RxTextView.textChanges(mEditOldPwd).skip(1);
        Observable<CharSequence> etNewPwdObservable = RxTextView.textChanges(mEditNewPwd).skip(1);
        Observable<CharSequence> etConfirmPwdObservable = RxTextView.textChanges(mEditConfirmPwd).skip(1);
        Observable.combineLatest(etOldPwdObservable, etNewPwdObservable, etConfirmPwdObservable, new Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
                boolean oldPwdValid = !TextUtils.isEmpty(charSequence);
                boolean newPwdValid = !TextUtils.isEmpty(charSequence2);
                boolean confirmPwdValid = !TextUtils.isEmpty(charSequence3);
                return oldPwdValid && newPwdValid && confirmPwdValid;
            }
        }).subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {
                Logger.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("onError-->" + e.toString());
            }

            @Override
            public void onNext(Boolean aBoolean) {
                mButtonSure.setEnabled(aBoolean);
            }
        });
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @OnClick(R.id.button_sure)
    public void onClick() {
        String oldPwd = mEditOldPwd.getText().toString().trim();
        String newPwd = mEditNewPwd.getText().toString().trim();
        String confirmPwd = mEditConfirmPwd.getText().toString().trim();
        if (!RegularlyHelper.isPwdValid(newPwd)) {
            showToast(R.string.pass_word_length);
            return;
        }
        if (!newPwd.equals(confirmPwd)) {
            showToast(R.string.person_profile_modifypwd_newpwd_not_equals_confirm);
        } else {
            mPresenter.modifyPwdTask(oldPwd, newPwd);
        }
    }

    public void setPresenter(@NonNull ModifyPwdContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void finishView() {
        getActivity().finish();
    }

    @Override
    public void modifySuccess(String successMessage) {
        toastMsg(successMessage);
    }

    @Override
    public void loadDataError(String errorMessage) {
        toastMsg(errorMessage);
    }
}
