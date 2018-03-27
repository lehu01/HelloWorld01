package com.zmsoft.ccd.module.work.fragment;

import com.zmsoft.ccd.data.UserDataRepository;

import javax.inject.Inject;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/28 11:14
 */
public class GotoWorkPresenter implements GotoWorkContract.Presenter {

    private GotoWorkContract.View mView;
    private final UserDataRepository mRepository;

    @Inject
    public GotoWorkPresenter(GotoWorkContract.View view, UserDataRepository repository) {
        this.mView = view;
        this.mRepository = repository;
    }

    @Inject
    void setPresenter() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

}
