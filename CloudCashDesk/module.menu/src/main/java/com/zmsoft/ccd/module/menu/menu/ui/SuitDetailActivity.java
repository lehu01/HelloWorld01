package com.zmsoft.ccd.module.menu.menu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.lib.base.activity.BaseActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenu;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitMenuVO;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description：套餐详情
 * <br/>
 * Created by kumu on 2017/4/21.
 */

@Route(path = RouterPathConstant.SuitDetail.PATH)
public class SuitDetailActivity extends BaseActivity {


    public static final int REQUEST_CODE_SUIT_SUB_MENU = 1;
    public static final int REQUEST_CODE_SUIT_MENU_NOTE = 2;

    @BindView(R2.id.text_toolbar_title)
    TextView mTextToolbarTitle;
    @BindView(R2.id.text_toolbar_right)
    TextView mTextToolbarRight;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.text_label_number)
    TextView mTextLabelNumber;


    private SuitMenu mSuitMenu;
    private String mSuitMenuId;
    private String mSuitName;

    private SuitDetailFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_menu_activity_suit_detail);
        mSuitName = getIntent().getStringExtra(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_NAME);
        mSuitMenuId = getIntent().getStringExtra(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_ID);
        if (mSuitMenuId == null) {
            ToastUtils.showShortToast(this, "suitMenuId is empty");
            finish();
            return;
        }
        initViews(savedInstanceState);
    }

    public void setTitleText(String title) {
        if (!TextUtils.isEmpty(title) && TextUtils.isEmpty(mTextToolbarTitle.getText().toString())) {
            mTextToolbarTitle.setText(title);
        }
    }

    private void initViews(Bundle savedInstanceState) {
        if (mSuitName != null) {
            mTextToolbarTitle.setText(mSuitName);
        }
        mTextToolbarRight.setText(R.string.module_menu_title_suit_right);
        mTextToolbarRight.setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_menu_suit_note, 0, 0, 0);
        mTextToolbarRight.setVisibility(View.VISIBLE);


        if (savedInstanceState != null) {
            mFragment = (SuitDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        }

        if (mFragment == null) {
            OrderParam createOrderParam = (OrderParam) getIntent()
                    .getSerializableExtra(RouterPathConstant.SuitDetail.PARAM_CREATE_ORDER_PARAM);
            ItemVo itemVo = (ItemVo) getIntent().getSerializableExtra(RouterPathConstant.SuitDetail.PARAM_ITEMVO);
            BaseMenuVo baseMenuVo = (BaseMenuVo) getIntent().getSerializableExtra(RouterPathConstant.SuitDetail.PARAM_SUIT_BASE_MENU_VO);

            mFragment = new SuitDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_ID, mSuitMenuId);
            bundle.putSerializable(RouterPathConstant.SuitDetail.PARAM_CREATE_ORDER_PARAM, createOrderParam);
            bundle.putSerializable(RouterPathConstant.SuitDetail.PARAM_ITEMVO, itemVo);
            bundle.putSerializable(RouterPathConstant.SuitDetail.PARAM_SUIT_BASE_MENU_VO, baseMenuVo);
            mFragment.setArguments(bundle);
        }
        ActivityHelper.showFragment(getSupportFragmentManager(), mFragment, R.id.content, false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_SUIT_SUB_MENU:
                if (data != null && data.getExtras() != null) {
                    //SuitChildVO suitChildVO = data.getParcelableExtra(RouterPathConstant.SuitDetail.RESULT_MENU_CHILD);
                    //ArrayList<SuitChildVO> suitChildVO = data.getParcelableArrayListExtra(RouterPathConstant.SuitDetail.RESULT_MENU_CHILD);
                    //if (suitChildVO != null && suitChildVO.size() > 0 && mFragment != null) {
                    //    mFragment.combineSuitChild(suitChildVO.get(0));
                    //}
                }
                break;
            case REQUEST_CODE_SUIT_MENU_NOTE:
                if (data != null && data.getExtras() != null) {
                    SuitMenuVO suitMenuVO = data.getParcelableExtra(RouterPathConstant.SuitNote.PARAM_SUITMENOVO);
                    if (suitMenuVO != null && mFragment != null) {
                        mFragment.combineSuitNote(suitMenuVO);
                    }
                }
                break;
        }
    }

    @OnClick({R2.id.text_toolbar_left, R2.id.text_toolbar_right})
    void processClick(View view) {
        int i = view.getId();
        if (i == R.id.text_toolbar_left) {
            finish();

        } else if (i == R.id.text_toolbar_right) {
            SuitMenuVO suitMenuVO = mFragment.getSuitMenuVO();
            if (suitMenuVO == null || suitMenuVO.getSuitMenuHitRules() == null) {
                return;
            }
            MRouter.getInstance().build(RouterPathConstant.SuitNote.PATH)
                    .putParcelable(RouterPathConstant.SuitNote.PARAM_SUITMENOVO, suitMenuVO)
                    .navigation(this, REQUEST_CODE_SUIT_MENU_NOTE);
        }
    }
}
