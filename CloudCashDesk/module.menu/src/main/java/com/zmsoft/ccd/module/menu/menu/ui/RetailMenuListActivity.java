package com.zmsoft.ccd.module.menu.menu.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.zmsoft.ccd.data.dagger.CommonComponentManager;
import com.zmsoft.ccd.lib.base.activity.BaseActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.base.helper.AnswerEventLogger;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.KeyboardUtils;
import com.zmsoft.ccd.lib.utils.SPUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuGroupVO;
import com.zmsoft.ccd.module.menu.menu.presenter.RetailMenuListActivityContract;
import com.zmsoft.ccd.module.menu.menu.presenter.RetailMenuListActivityPresenter;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.DaggerRetailMenuListActivityComponent;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.RetailMenuListActivityPresenterModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description：零售开单界面
 * <br/>
 * Created by kumu on 2017/4/7.
 */
@Route(path = RouterPathConstant.RetailMenuList.PATH)
public class RetailMenuListActivity extends BaseActivity implements RetailMenuListActivityContract.View, IMenuListContract {

    public static final String SP_KEY_MENU_SHOW_CATEGORY = "SHOW_CATEGORY";
    public static final String SP_KEY_MENU_SHOW_IMAGE = "SHOW_IMAGE";
    public static final String ANSWER_EVENT_MENU_LIST_SHOW_CATEGORY = "MENU_LIST_SHOW_CATEGORY";

    @BindView(R2.id.drawer_right)
    DrawerLayout mDrawerLayoutRight;
    @BindView(R2.id.text_toggle_drawer)
    TextView mTextToggleDrawer;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.text_custom_food)
    TextView mTextCustomFood;
    @BindView(R2.id.image_toggle_image)
    ImageButton mImageToggleImage;
    @BindView(R2.id.image_toggle_letter_category)
    ImageButton mImageToggleLetterCategory;
    @BindView(R2.id.text_scan)
    TextView mTextScan;
    @BindView(R2.id.edit_input_key)
    EditText mEditInputKey;
    @BindView(R2.id.image_clear)
    ImageView mImageClear;

    private int mDrawerWidth;

    private boolean mIsShowCategory;
    private boolean mIsShowImage;
    private MenuFilterFragment mMenuFilterFragment;
    private RetailMenuListFragment mRetailMenuListFragment;

    private OrderParam mCreateOrderParam;
    private boolean mHasPressBack;

    private int mFrom;

    @Inject
    RetailMenuListActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_menu_activity_retail_menu_list);

        DaggerRetailMenuListActivityComponent.builder()
                .retailMenuListActivityPresenterModule(new RetailMenuListActivityPresenterModule(this))
                .commonSourceComponent(CommonComponentManager.get().getMenuSourceComponent())
                .build()
                .inject(this);

        initViews(savedInstanceState);
        mDrawerWidth = getResources().getDimensionPixelSize(R.dimen.module_menu_list_drawer_right_width);

        mCreateOrderParam = (OrderParam) getIntent()
                .getSerializableExtra(RouterPathConstant.MenuList.EXTRA_CREATE_ORDER_PARAM);

        if (savedInstanceState != null) {
            mRetailMenuListFragment = (RetailMenuListFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        }
        if (mRetailMenuListFragment == null) {
            mRetailMenuListFragment = RetailMenuListFragment.create();
            Bundle bundle = new Bundle();
            bundle.putInt(RouterPathConstant.Cart.EXTRA_FROM, mFrom);
            bundle.putSerializable(RouterPathConstant.MenuList.EXTRA_CREATE_ORDER_PARAM, mCreateOrderParam);
            mRetailMenuListFragment.setArguments(bundle);
        }
        ActivityHelper.showFragment(getSupportFragmentManager(), mRetailMenuListFragment, R.id.container, false);

        mMenuFilterFragment = (MenuFilterFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
    }

    private void initViews(Bundle savedInstanceState) {

        mFrom = getIntent().getIntExtra(RouterPathConstant.Cart.EXTRA_FROM, -1);

        mIsShowCategory = SPUtils.getInstance(this).getBoolean(SP_KEY_MENU_SHOW_CATEGORY, true);
        mIsShowImage = SPUtils.getInstance(this).getBoolean(SP_KEY_MENU_SHOW_IMAGE, true);

        mEditInputKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mImageClear.setVisibility(View.VISIBLE);
                } else {
                    mImageClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        initToolbarDoubleClick();

        mDrawerLayoutRight.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mTextToggleDrawer.setTranslationX(-slideOffset * mDrawerWidth);
            }
        });

        mImageToggleLetterCategory.setImageResource(mIsShowCategory ? R.drawable.module_menu_sort_by_category :
                R.drawable.module_menu_sort_by_letter);

        mImageToggleImage.setImageResource(mIsShowImage ? R.drawable.module_menu_list_image :
                R.drawable.module_menu_list_no_image);

        mTextToggleDrawer.setText(mIsShowCategory ? R.string.module_menu_retail_category
                : R.string.module_menu_filter_letter);
    }

    @Override
    public boolean isShowCategory() {
        return mIsShowCategory;
    }

    @Override
    public boolean isShowImage() {
        return mIsShowImage;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            KeyboardUtils.hideSoftInput(this);
        }
        return super.dispatchTouchEvent(event);
    }

    private void initToolbarDoubleClick() {
        final GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mRetailMenuListFragment != null) {
                    mRetailMenuListFragment.goListTop();
                }
                return super.onDoubleTap(e);
            }
        });
        mToolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    public void setFilterData(List<MenuGroupVO> categoryList, List<String> letterList) {
        if (mMenuFilterFragment != null) {
            mMenuFilterFragment.setFilterData(categoryList, letterList);
        }
        if (mMenuFilterFragment != null) {
            if (mIsShowCategory) {
                mMenuFilterFragment.showCategory();
            } else {
                mMenuFilterFragment.showLetter();
            }
        }
    }

    public EditText getSearchEditText() {
        return mEditInputKey;
    }

    @Override
    public void showContentByCategory(MenuGroupVO groupVO) {
        mDrawerLayoutRight.closeDrawer(GravityCompat.END);
        mRetailMenuListFragment.showMenusByCategory(groupVO);
    }

    @Override
    public void showContentByLetter(String letter) {
        mDrawerLayoutRight.closeDrawer(GravityCompat.END);
        mRetailMenuListFragment.showMenusByLetter(letter);
    }

    @OnClick({R2.id.image_back,
            R2.id.image_clear,
            R2.id.text_custom_food,
            R2.id.image_toggle_image,
            R2.id.image_toggle_letter_category,
            R2.id.text_scan, R2.id.edit_input_key,
            R2.id.text_toggle_drawer})
    void processClick(View view) {
        int i = view.getId();
        if (i == R.id.image_back) {
            if (mFrom == RouterPathConstant.RetailMenuList.FROM_COMPLETE_RECEIPT) {
                clearOtherActivity();
            }
            setResult(Activity.RESULT_OK, null);
            finish();
        } else if (i == R.id.image_clear) {
            mEditInputKey.setText("");

        } else if (i == R.id.text_custom_food) {
            showLoading(true);
            mPresenter.checkCustomFoodPermission();
        } else if (i == R.id.image_toggle_image) {
            mImageToggleImage.setImageResource(mIsShowImage ? R.drawable.module_menu_list_no_image :
                    R.drawable.module_menu_list_image);
            SPUtils.getInstance(this).putBoolean(SP_KEY_MENU_SHOW_IMAGE, mIsShowImage = !mIsShowImage);
            mRetailMenuListFragment.toggleShowType(mIsShowImage);
        } else if (i == R.id.image_toggle_letter_category) {
            if (mIsShowCategory) {
                mIsShowCategory = false;
                mImageToggleLetterCategory.setImageResource(R.drawable.module_menu_sort_by_letter);
                mTextToggleDrawer.setText(R.string.module_menu_filter_letter);
                mRetailMenuListFragment.showMenusByLetter();
                SPUtils.getInstance(this).putBoolean(SP_KEY_MENU_SHOW_CATEGORY, mIsShowCategory);
            } else {
                mIsShowCategory = true;
                mImageToggleLetterCategory.setImageResource(R.drawable.module_menu_sort_by_category);
                mTextToggleDrawer.setText(R.string.module_menu_retail_category);
                mRetailMenuListFragment.showMenusByCategory();
                SPUtils.getInstance(this).putBoolean(SP_KEY_MENU_SHOW_CATEGORY, mIsShowCategory);
            }

        } else if (i == R.id.text_scan) {
            MRouter.getInstance().build(RouterPathConstant.RetailMenuScan.PATH_MENU_SCAN)
                    .putString(RouterPathConstant.CartDetail.EXTRA_SEATCODE
                            , mCreateOrderParam == null ? null : mCreateOrderParam.getSeatCode())
                    .navigation(this);

        } else if (i == R.id.text_toggle_drawer) {
            if (mRetailMenuListFragment.getGroups().isEmpty()) {
                return;
            }
            if (mIsShowCategory) {
                mMenuFilterFragment.showCategory();
            } else {
                mMenuFilterFragment.showLetter();
            }
            if (mDrawerLayoutRight.isDrawerOpen(GravityCompat.END)) {
                mDrawerLayoutRight.closeDrawer(GravityCompat.END);
            } else {
                mDrawerLayoutRight.openDrawer(GravityCompat.END);
                AnswerEventLogger.log(ANSWER_EVENT_MENU_LIST_SHOW_CATEGORY);
            }

        }
    }

    @Override
    public void setPresenter(RetailMenuListActivityContract.Presenter presenter) {
        mPresenter = (RetailMenuListActivityPresenter) presenter;
    }

    @Override
    public void checkCustomFoodPermissionSuccess(boolean has) {
        hideLoading();
        if (has) {
            MRouter.getInstance().build(RouterPathConstant.RetailCustomFood.PATH)
                    .putSerializable(RouterPathConstant.RetailCustomFood.EXTRA_CREATE_ORDER_PARAM, mCreateOrderParam)
                    .navigation(this);
        } else {
            ToastUtils.showShortToast(this, R.string.module_menu_no_permission_custom_food);
        }
    }

    @Override
    public void checkCustomFoodPermissionFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(this, errorMsg);
    }


    public boolean getHasPressBack() {
        return mHasPressBack;
    }

    @Override
    public void onBackPressed() {
        mHasPressBack = true;
        if (mFrom == RouterPathConstant.Cart.EXTRA_FROM_HANG_UP_ORDER) {
            setResult(Activity.RESULT_OK, null);
            finish();
            return;
        } else if (mFrom == RouterPathConstant.RetailMenuList.FROM_COMPLETE_RECEIPT) {
            clearOtherActivity();
        }
        super.onBackPressed();
    }

    private void clearOtherActivity() {
        List<String> notCloseList = new ArrayList<>(2);
        notCloseList.add(RouterPathConstant.PATH_RETAIL_MAIN_ACTIVITY);
        notCloseList.add(RouterPathConstant.RetailMenuList.PATH);
        RouterActivityManager.get().finishAllActivityExcept(notCloseList);
    }
}
