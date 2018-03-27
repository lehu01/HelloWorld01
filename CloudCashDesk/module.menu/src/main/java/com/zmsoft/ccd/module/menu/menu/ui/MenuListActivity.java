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
import com.zmsoft.ccd.data.dagger.CommonComponentManager;
import com.zmsoft.ccd.lib.base.activity.BaseActivity;
import com.zmsoft.ccd.lib.base.constant.AnswerEventConstant;
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
import com.zmsoft.ccd.module.menu.menu.presenter.MenuListActivityContract;
import com.zmsoft.ccd.module.menu.menu.presenter.MenuListActivityPresenter;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.DaggerMenuListActivityComponent;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.MenuListActivityPresenterModule;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description：菜单列表
 * <br/>
 * Created by kumu on 2017/4/7.
 */
@Route(path = RouterPathConstant.MenuList.PATH)
public class MenuListActivity extends BaseActivity implements MenuListActivityContract.View, IMenuListContract {

    public static final String SP_KEY_MENU_SHOW_CATEGORY = "SHOW_CATEGORY";
    public static final String SP_KEY_MENU_SHOW_IMAGE = "SHOW_IMAGE";

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
    private MenuListFragment mMenuListFragment;

    private OrderParam mCreateOrderParam;
    private boolean mHasPressBack;

    private int mFrom;

    @Inject
    MenuListActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_menu_activity_menu_list);

        DaggerMenuListActivityComponent.builder()
                .menuListActivityPresenterModule(new MenuListActivityPresenterModule(this))
                .commonSourceComponent(CommonComponentManager.get().getMenuSourceComponent())
                .build()
                .inject(this);


        initViews(savedInstanceState);
        mDrawerWidth = getResources().getDimensionPixelSize(R.dimen.module_menu_list_drawer_right_width);

        mCreateOrderParam = (OrderParam) getIntent()
                .getSerializableExtra(RouterPathConstant.MenuList.EXTRA_CREATE_ORDER_PARAM);

        if (savedInstanceState != null) {
            mMenuListFragment = (MenuListFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        }
        if (mMenuListFragment == null) {
            mMenuListFragment = new MenuListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(RouterPathConstant.Cart.EXTRA_FROM, mFrom);
            bundle.putSerializable(RouterPathConstant.MenuList.EXTRA_CREATE_ORDER_PARAM, mCreateOrderParam);
            mMenuListFragment.setArguments(bundle);
        }
        ActivityHelper.showFragment(getSupportFragmentManager(), mMenuListFragment, R.id.container, false);

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


//搜索不需要点击输入法搜索，字变化就直接搜索
//        mEditInputKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    String key = mEditInputKey.getText().toString().trim();
//                    if (!TextUtils.isEmpty(key)) {
//                        //ToastUtils.showLongToast(getApplication(), key);
//                        KeyboardUtils.hideSoftInput(MenuListActivity.this);
//                    }
//                }
//                return true;
//            }
//        });
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

        mTextToggleDrawer.setText(mIsShowCategory ? R.string.module_menu_category
                : R.string.module_menu_filter_letter);
    }

    public boolean isShowCategory() {
        return mIsShowCategory;
    }

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
                if (mMenuListFragment != null) {
                    mMenuListFragment.goListTop();
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

    @Override
    public EditText getSearchEditText() {
        return mEditInputKey;
    }

    @Override
    public void showContentByCategory(MenuGroupVO groupVO) {
        mDrawerLayoutRight.closeDrawer(GravityCompat.END);
        mMenuListFragment.showMenusByCategory(groupVO);
    }

    @Override
    public void showContentByLetter(String letter) {
        mDrawerLayoutRight.closeDrawer(GravityCompat.END);
        mMenuListFragment.showMenusByLetter(letter);
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
            mMenuListFragment.toggleShowType(mIsShowImage);
        } else if (i == R.id.image_toggle_letter_category) {
            if (mIsShowCategory) {
                mIsShowCategory = false;
                mImageToggleLetterCategory.setImageResource(R.drawable.module_menu_sort_by_letter);
                mTextToggleDrawer.setText(R.string.module_menu_filter_letter);
                mMenuListFragment.showMenusByLetter();
                SPUtils.getInstance(this).putBoolean(SP_KEY_MENU_SHOW_CATEGORY, mIsShowCategory);
            } else {
                mIsShowCategory = true;
                mImageToggleLetterCategory.setImageResource(R.drawable.module_menu_sort_by_category);
                mTextToggleDrawer.setText(R.string.module_menu_category);
                mMenuListFragment.showMenusByCategory();
                SPUtils.getInstance(this).putBoolean(SP_KEY_MENU_SHOW_CATEGORY, mIsShowCategory);
            }

        } else if (i == R.id.text_scan) {
            MRouter.getInstance().build(RouterPathConstant.MenuScan.PATH_MENU_SCAN)
                    .putSerializable(RouterPathConstant.SuitDetail.PARAM_CREATE_ORDER_PARAM, mCreateOrderParam)
                    .navigation(this);

        } else if (i == R.id.text_toggle_drawer) {
            if (mMenuListFragment.getGroups().isEmpty()) {
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
                AnswerEventLogger.log(AnswerEventConstant.Menu.ANSWER_EVENT_MENU_LIST_SHOW_CATEGORY);
            }

        }
    }

    @Override
    public void setPresenter(MenuListActivityContract.Presenter presenter) {
        mPresenter = (MenuListActivityPresenter) presenter;
    }

    @Override
    public void checkCustomFoodPermissionSuccess(boolean has) {
        hideLoading();
        if (has) {
            MRouter.getInstance().build(RouterPathConstant.CustomFood.PATH)
                    .putSerializable(RouterPathConstant.CustomFood.EXTRA_CREATE_ORDER_PARAM, mCreateOrderParam)
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
        }
        super.onBackPressed();
    }
}
