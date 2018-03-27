package com.zmsoft.ccd.module.mistake;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.spdata.MistakeDataManager;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.bean.mistakes.Mistake;
import com.zmsoft.ccd.module.mistake.adapter.MistakeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zmsoft.ccd.module.mistake.MistakeMsgActivity.PATH_MISTAKE_ACTIVITY;

@Route(path = PATH_MISTAKE_ACTIVITY)
public class MistakeMsgActivity extends ToolBarActivity implements MistakeMsgContract.View{

    public static final String PATH_MISTAKE_ACTIVITY = "/main/mistake_msg_activity";

    MistakeMsgContract.Presenter presenter;

    @BindView(R.id.recyclerview_mistake)
    RecyclerView mRecyclerView;

    MistakeAdapter mMistakeAdapter;

    List<Mistake> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistake_msg);

        mMistakeAdapter = new MistakeAdapter(this, mRecyclerView, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMistakeAdapter);
        init();
    }

    private void init(){
        mList.addAll(MistakeDataManager.getMistakeData(this));
        mMistakeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMistakeMsgSuccess() {

    }

    public void setPresenter(MistakeMsgContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
