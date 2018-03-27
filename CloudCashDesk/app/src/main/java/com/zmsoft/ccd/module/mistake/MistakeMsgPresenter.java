package com.zmsoft.ccd.module.mistake;

/**
 * Created by jihuo on 2016/12/26.
 */

public class MistakeMsgPresenter implements MistakeMsgContract.Presenter {

    MistakeMsgContract.View view;

    public MistakeMsgPresenter(MistakeMsgContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void getMistakeMsg() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
