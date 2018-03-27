package com.zmsoft.ccd.module.menu.menu.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.event.JoinTableEvent;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.KeyboardUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.widget.FoodNumberTextView;
import com.zmsoft.ccd.module.menu.BuildConfig;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.dagger.ComponentManager;
import com.zmsoft.ccd.module.menu.events.model.ModifyCartParam;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.adapter.MenuListAdapter;
import com.zmsoft.ccd.module.menu.menu.adapter.RetailMenuListAdapter;
import com.zmsoft.ccd.module.menu.menu.bean.BoMenu;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;
import com.zmsoft.ccd.module.menu.menu.bean.vo.CartItemVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuGroupVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuVO;
import com.zmsoft.ccd.module.menu.menu.presenter.RetailMenuListFragmentContract;
import com.zmsoft.ccd.module.menu.menu.presenter.RetailMenuListFragmentPresenter;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.DaggerRetailMenuListFragmentComponent;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.RetailMenuListFragmentPresenterModule;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.zmsoft.ccd.app.GlobalVars.context;
import static com.zmsoft.ccd.module.menu.helper.SpecificationDataMapLayer.getSpecificationVOList;

/**
 * Description：菜单列表
 * <br/>
 * Created by kumu on 2017/4/7.
 */

public class RetailMenuListFragment extends BaseListFragment
        implements RetailMenuListFragmentContract.View, BaseListAdapter.AdapterClick {

    @BindView(R2.id.text_cart)
    TextView mTextCart;
    @BindView(R2.id.text_cart_other_order)
    TextView mTextCartOtherOrder;
    @BindView(R2.id.text_cart_my_order)
    TextView mTextCartMyOrder;
    private RetailMenuListAdapter mImageAdapter;

    @Inject
    RetailMenuListFragmentPresenter mPresenter;

    RetailWeighDialog mRetailWeighDialog;

    private int mMyOrderCount;
    private int mOtherOrderCount;

    private DinningTableVo mDinningTableVo;


    //分组后的菜单列表数据
    private LinkedHashMap<MenuGroupVO, List<MenuVO>> categoryMenusMap = new LinkedHashMap<>();
    //字母分组
    private Map<String, List<MenuVO>> mLetterMenusMap;//= new LinkedHashMap<>();
    //购物车点的数据
    private List<CartItemVO> mCartList = new ArrayList<>();
    //Adapter需要的List（分类）
    private List<Object> mAssembleCategoryList = new ArrayList<>();
    //Adapter需要的List（字母）
    private List<Object> mAssembleLetterList = new ArrayList<>();
    //菜类列表
    private List<MenuGroupVO> mGroupVOList = new ArrayList<>();
    //菜名首字母
    private List<String> mLetterList = new ArrayList<>();

    private OrderParam mCreateOrderParam;

    private boolean mMenuListSuccess;
    private boolean mCartListSuccess;
    private int mFrom;

    private HashSet<String> joinTableUser = new HashSet<>();

    public static RetailMenuListFragment create() {
        return new RetailMenuListFragment();
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.module_menu_fragment_menu_list;
    }


    private void setOrderCountText(int myOrderCount, int otherOrderCount) {
        this.mMyOrderCount = myOrderCount;
        this.mOtherOrderCount = otherOrderCount;
        mTextCartOtherOrder.setVisibility(otherOrderCount == 0 ? View.GONE : View.VISIBLE);
        mTextCartMyOrder.setVisibility(myOrderCount == 0 ? View.GONE : View.VISIBLE);

        mTextCartMyOrder.setText(String.valueOf(myOrderCount));
        mTextCartOtherOrder.setText(String.valueOf(otherOrderCount));

    }

    @Override
    protected void initParameters() {
        super.initParameters();
        DaggerRetailMenuListFragmentComponent.builder()
                .retailMenuListFragmentPresenterModule(new RetailMenuListFragmentPresenterModule(this))
                .menuSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .build()
                .inject(this);

        EventBusHelper.register(this);

        mLetterMenusMap = new TreeMap<>(new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                return obj1.compareTo(obj2);
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            mCreateOrderParam = (OrderParam) bundle
                    .getSerializable(RouterPathConstant.MenuList.EXTRA_CREATE_ORDER_PARAM);
            mFrom = bundle.getInt(RouterPathConstant.Cart.EXTRA_FROM, -1);
        }
    }

    private String getSeatCode() {
        if (mCreateOrderParam != null) {
            return mCreateOrderParam.getSeatCode();
        }
        return null;
    }

    private String getOrderId() {
        if (mCreateOrderParam != null) {
            return mCreateOrderParam.getOrderId();
        }
        return null;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        //cancelFocusEditView();
        setOrderCountText(0, 0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSearch();
    }

    private void cancelFocusEditView() {
        ViewGroup viewGroup = getRecyclerView();
        viewGroup.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        viewGroup.setFocusable(true);
        viewGroup.setFocusableInTouchMode(true);
        viewGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                KeyboardUtils.hideSoftInput(getActivity());
                return false;
            }
        });
    }

    public void initSearch() {

        if (getActivityEdit() == null) {
            return;
        }
        RxTextView.textChanges(getActivityEdit())
                .debounce(400, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return true;//charSequence.toString().trim().length() > 0;
                    }
                })
                .flatMap(new Func1<CharSequence, Observable<List<Object>>>() {

                    private boolean checkContainGroup(List<Object> list, MenuGroupVO groupVO) {
                        if (list != null && !list.isEmpty()) {
                            for (Object obj : list) {
                                if (obj instanceof MenuGroupVO) {
                                    MenuGroupVO group = (MenuGroupVO) obj;
                                    if (group.getGroupId().equals(groupVO.getGroupId())) {
                                        return true;
                                    }
                                }
                            }
                        }
                        return false;
                    }

                    private boolean checkContainLetter(List<Object> list, String letter) {
                        if (list != null && !list.isEmpty()) {
                            for (Object obj : list) {
                                if (obj instanceof String) {
                                    if (obj.toString().equalsIgnoreCase(letter)) {
                                        return true;
                                    }
                                }
                            }
                        }
                        return false;
                    }

                    private boolean compare(CharSequence charSequence, Menu menu) {
                        //匹配菜码
                        boolean isContain = menu.getName().contains(charSequence.toString());
                        //匹配简拼
                        if (!isContain && menu.getSpell() != null) {
                            isContain = menu.getSpell().toUpperCase().contains(charSequence.toString().toUpperCase());
                        }
                        //匹配菜码
                        if (!isContain && menu.getCode() != null) {
                            isContain = menu.getCode().contains(charSequence);
                        }
                        return isContain;
                    }

                    private List<Object> getCategoryMenus(CharSequence charSequence) {
                        List<Object> myAssembleList = new ArrayList<>();
                        for (Map.Entry<MenuGroupVO, List<MenuVO>> entry : categoryMenusMap.entrySet()) {
                            List<MenuVO> menuVOs = entry.getValue();
                            for (MenuVO menuVO : menuVOs) {
                                if (menuVO.getMenu() == null) {
                                    continue;
                                }
                                if (compare(charSequence, menuVO.getMenu())) {
                                    if (!checkContainGroup(myAssembleList, entry.getKey())) {
                                        myAssembleList.add(entry.getKey());
                                    }
                                    myAssembleList.add(menuVO);
                                    if (menuVO.shouldNewLine()) {
                                        myAssembleList.addAll(menuVO.getSpecificationVOs());
                                    }
                                }
                            }
                        }
                        return myAssembleList;
                    }

                    List<Object> getLetterMenus(CharSequence charSequence) {
                        List<Object> myAssembleList = new ArrayList<>();
                        for (Map.Entry<String, List<MenuVO>> entry : mLetterMenusMap.entrySet()) {
                            List<MenuVO> menuVOs = entry.getValue();
                            for (MenuVO menuVO : menuVOs) {
                                if (menuVO.getMenu() == null) {
                                    continue;
                                }
                                if (compare(charSequence, menuVO.getMenu())) {
                                    if (!checkContainLetter(myAssembleList, entry.getKey())) {
                                        myAssembleList.add(entry.getKey());
                                    }
                                    myAssembleList.add(menuVO);
                                    if (menuVO.shouldNewLine()) {
                                        myAssembleList.addAll(menuVO.getSpecificationVOs());
                                    }
                                }
                            }
                        }
                        return myAssembleList;
                    }

                    @Override
                    public Observable<List<Object>> call(CharSequence charSequence) {
                        if (TextUtils.isEmpty(charSequence.toString().trim())) {
                            if (getShowCategory()) {
                                return Observable.just(mAssembleCategoryList);
                            }
                            return Observable.just(mAssembleLetterList);
                        }
                        List<Object> myAssembleList = getShowCategory() ?
                                getCategoryMenus(charSequence) : getLetterMenus(charSequence);
                        return Observable.just(myAssembleList);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Object>>() {
                    @Override
                    public void call(List<Object> objects) {
                        mImageAdapter.setList(objects);
                        mImageAdapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });


    }


    public boolean getShowCategory() {
        if (getActivity() instanceof IMenuListContract) {
            return ((IMenuListContract) getActivity()).isShowCategory();
        }
        return false;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        disableAutoRefresh();
        showLoadingView();
    }

    @Override
    protected void loadListData() {
        //如果当前界面已经有数据了，则不从本地加载
        boolean needCheckLocal = mLetterMenusMap == null || mLetterMenusMap.isEmpty();
        //resetData();
        //加载桌位状态
        mPresenter.getSeatStatus(UserHelper.getEntityId(), getSeatCode());

        mPresenter.loadMenuList(UserHelper.getEntityId(), getSeatCode(), getOrderId(), needCheckLocal);
        //mPresenter.queryCart(getSeatCode(), getOrderId());
    }

    @Override
    protected int getAccentColor() {
        return R.color.accentColor;
    }

    private boolean isShowImage() {
        if (getActivity() instanceof IMenuListContract) {
            IMenuListContract activity = (IMenuListContract) getActivity();
            return activity.isShowImage();
        }
        return true;
    }

    @Override
    protected BaseListAdapter createAdapter() {
        mImageAdapter = RetailMenuListAdapter.createImageAdapter(getActivity(), this);
        mImageAdapter.setCreateOrderParam(mCreateOrderParam);
        mImageAdapter.setShowType(isShowImage());
        return mImageAdapter;
    }

    private int getFirstVisiblePosition() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getRecyclerView().getLayoutManager();
        return linearLayoutManager.findFirstVisibleItemPosition();
    }

    public void goListTop() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getRecyclerView().getLayoutManager();
        int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
        if (firstVisiblePosition >= 20) {
            getRecyclerView().scrollToPosition(10);
        }
        getRecyclerView().smoothScrollToPosition(0);
    }


    public void toggleShowType(boolean showImage) {
        int ps = getFirstVisiblePosition();
        mImageAdapter.setShowType(showImage);
        setAdapter(mImageAdapter);
        getRecyclerView().scrollToPosition(ps);
    }

    private void setFilterData(List<MenuGroupVO> groupVOList, List<String> letters) {
        if (getActivity() instanceof IMenuListContract) {
            IMenuListContract menuListActivity = (IMenuListContract) getActivity();
            menuListActivity.setFilterData(groupVOList, letters);
        }
    }

    private EditText getActivityEdit() {
        if (getActivity() instanceof IMenuListContract) {
            IMenuListContract menuListActivity = (IMenuListContract) getActivity();
            return menuListActivity.getSearchEditText();
        }
        return null;
    }


    @Override
    public void unBindPresenterFromView() {
        if (this.mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void setPresenter(RetailMenuListFragmentContract.Presenter presenter) {
        this.mPresenter = (RetailMenuListFragmentPresenter) presenter;
    }

    public List<MenuGroupVO> getGroups() {
        return mGroupVOList;
    }

    private void resetData() {
        mDinningTableVo = null;
        mMenuListSuccess = false;
        mCartListSuccess = false;
        categoryMenusMap.clear();
        mLetterMenusMap.clear();
        mGroupVOList.clear();
        mLetterList.clear();
        mCartList.clear();
        //刷新成功后才clear
        //mAssembleCategoryList.clear();
        //mAssembleLetterList.clear();

    }

    private void renderList(List list) {
        finishRefresh();
        mImageAdapter.setList(list);
        mImageAdapter.notifyDataSetChanged();
        showContentView();
    }

    @Override
    public void loadMenuListSuccess(List<Menu> list) {

        resetData();

//        categoryMenusMap.clear();
//        mLetterMenusMap.clear();
//        mGroupVOList.clear();
//        mLetterList.clear();

        mMenuListSuccess = true;
        groupMenu(list);
        //if (mCartListSuccess) {
        setOrderCountText(mMyOrderCount, mOtherOrderCount);
        assembleCartsToMenuMap(mCartList);
        renderList(isShowCategory() ? mAssembleCategoryList : mAssembleLetterList);
        //}
    }

    private boolean isShowCategory() {
        if (getActivity() instanceof IMenuListContract) {
            IMenuListContract menuListActivity = (IMenuListContract) getActivity();
            return menuListActivity.isShowCategory();
        }
        return true;
    }

    @Override
    public void loadMenuListFailed(String errorCode, String errorMsg) {
        loadListFailed();
        ToastUtils.showShortToast(getContext(), errorMsg);
        //如果界面已经有数据了，则不显示StateView
        if (mAssembleCategoryList == null || mAssembleCategoryList.isEmpty()) {
            showErrorView(getString(R.string.module_menu_list_load_failed));
        }
    }

    @Override
    public void queryCartSuccess(DinningTableVo dinningTableVo) {
        mCartListSuccess = true;
        if (mMenuListSuccess) {
            showContentView();
            finishRefresh();
            renderWhenCartChange(dinningTableVo);
        } else {
            boolean update = updateMemoryCart(dinningTableVo);
            if (!update) {
                return;
            }
            if (mDinningTableVo == null) {
                return;
            }
            mCartList = getSpecificationVOList(getContext(), mDinningTableVo);
            setOrderCountText(mDinningTableVo.getMyDicNum(), mDinningTableVo.getOtherDicNum());
        }
    }

    private boolean updateMemoryCart(DinningTableVo dinningTableVo) {
        //三种情况都需要更新内存购物车
        boolean update = (dinningTableVo == null || mDinningTableVo == null) ||
                (dinningTableVo.getCartTime() == 0) ||//购物车没有了数据cartTime返回0
                dinningTableVo.getCartTime() > mDinningTableVo.getCartTime();//如果返回的购物车比内存的要新
        if (update) {
            mDinningTableVo = dinningTableVo;
        }
        return update;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void joginTableTip(JoinTableEvent event) {
        if (event != null) {
            if (!joinTableUser.contains(event.getName())) {
                joinTableUser.add(event.getName());
                new JoinTableTipDialog(getActivity(), event.getName(), event.getAvatar()).show();
            }
        }
        mPresenter.queryCart(getSeatCode(), getOrderId());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshCartList(RouterBaseEvent.CommonEvent event) {

        //刷新桌位状态
        mPresenter.getSeatStatus(UserHelper.getEntityId(), getSeatCode());

        if (event == null) return;
        if (event == RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_CART) {
            if (event.getObject() instanceof ModifyCartParam) {
                ModifyCartParam modifyCartParam = (ModifyCartParam) event.getObject();
                if (modifyCartParam.getCreateOrderParam() != null) {
                    mCreateOrderParam = modifyCartParam.getCreateOrderParam();
                }
                DinningTableVo dinningTableVo = modifyCartParam.getDinningTableVo();
                if (dinningTableVo != null) {
                    renderWhenCartChange(dinningTableVo);
                } else {
                    mPresenter.queryCart(getSeatCode(), getOrderId());
                }
            } else if (event.getObject() instanceof DinningTableVo) {
                DinningTableVo dinningTableVo = (DinningTableVo) event.getObject();
                if (dinningTableVo != null) {
                    renderWhenCartChange(dinningTableVo);
                }
            } else {
                mPresenter.queryCart(getSeatCode(), getOrderId());
            }
        } else if (event == RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_FOR_WEIGHT) {
            if (event.getObject() instanceof BoMenu) {
                checkTheCommodity((BoMenu) event.getObject());
            }
        }
    }

    private void checkTheCommodity(BoMenu boMenu) {
        for (Object object : mImageAdapter.getList()) {
            if (object instanceof MenuVO) {
                final MenuVO menuVO = (MenuVO) object;
                if (menuVO.getMenu().getId().equals(boMenu.getId())) {
                    getRecyclerView().smoothScrollToPosition(mImageAdapter.getList().indexOf(object));//滑动到对应item
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showWeightDialog(menuVO); //延时200ms等fragment恢复至resumed
                        }
                    }, 200);
                    break;
                }
            }
        }
    }

    /**
     * 使用购物车对象DinningTableVo返回的数据
     *
     * @deprecated
     */
    private void computeOrderCount() {
        mMyOrderCount = 0;
        mOtherOrderCount = 0;
        for (Map.Entry<MenuGroupVO, List<MenuVO>> entry : categoryMenusMap.entrySet()) {
            for (MenuVO menuVO : entry.getValue()) {
                List<CartItemVO> list = menuVO.getSpecificationVOs();
                if (list == null) {
                    continue;
                }
                for (CartItemVO specificationVO : list) {
                    if ((specificationVO.isSelf())) {
                        mMyOrderCount++;
                    } else {
                        mOtherOrderCount++;
                    }
                }
            }
        }
        setOrderCountText(mMyOrderCount, mOtherOrderCount);
    }

    private void resetMenuSpecification() {
        for (Map.Entry<MenuGroupVO, List<MenuVO>> entry : categoryMenusMap.entrySet()) {
            for (MenuVO menuVO : entry.getValue()) {
                menuVO.setMySpecification(null);
                if (menuVO.getSpecificationVOs() != null) {
                    menuVO.getSpecificationVOs().clear();
                }
            }
        }
    }

    /**
     * 组装菜单列表数据和购物车数据
     */
    private void assembleCartsToMenuMap(List<CartItemVO> specificationVOs) {
        if (specificationVOs != null) {
            for (CartItemVO specificationVO : specificationVOs) {
                assembleCartToMenu(specificationVO);
            }
        }

        //把map中的数据，组装成adapter需要的数据（菜类分组）
        mAssembleCategoryList.clear();
        mLetterMenusMap.clear();
        for (Map.Entry<MenuGroupVO, List<MenuVO>> entry : categoryMenusMap.entrySet()) {
            MenuGroupVO groupVO = entry.getKey();
            groupVO.setOrderCount(0);
            mAssembleCategoryList.add(entry.getKey());

            //如果是自定菜的，分组右侧也需要更新数量
            if (specificationVOs != null) {
                for (CartItemVO specificationVO : specificationVOs) {
                    if (specificationVO.isCustomFood() && specificationVO.getMenuKindId().equals(groupVO.getGroupId())) {
                        groupVO.setOrderCount(groupVO.getOrderCount() + 1);
                    }
                }
            }

            for (MenuVO menuVO : entry.getValue()) {
                mAssembleCategoryList.add(menuVO);
                if ((menuVO.isOrdered())) {
                    groupVO.setOrderCount(groupVO.getOrderCount() + 1);
                }

                //零售不会有规格和做法，所以不会换行显示
                //if (menuVO.showAddToList()) {
                //    mAssembleCategoryList.addAll(menuVO.getSpecificationVOs());
                //}

                assembleMenuToLetterMap(menuVO);
            }
        }

        //把map中的数据，组装成adapter需要的数据（字母分组）
        mAssembleLetterList.clear();
        for (Map.Entry<String, List<MenuVO>> entry : mLetterMenusMap.entrySet()) {
            mAssembleLetterList.add(entry.getKey());
            for (MenuVO menuVO : entry.getValue()) {
                mAssembleLetterList.add(menuVO);
                if (menuVO.showAddToList()) {
                    mAssembleLetterList.addAll(menuVO.getSpecificationVOs());
                }
            }
        }

        //computeOrderCount();
    }

    /**
     * 把菜按字母归类
     *
     * @param menuVO
     */
    private void assembleMenuToLetterMap(MenuVO menuVO) {
        if (mLetterMenusMap.containsKey(menuVO.getFirstLetter())) {
            List<MenuVO> letterMenus = mLetterMenusMap.get(menuVO.getFirstLetter());
            letterMenus.add(menuVO);
        } else {
            List<MenuVO> letterMenus = new ArrayList<>();
            letterMenus.add(menuVO);
            mLetterMenusMap.put(menuVO.getFirstLetter(), letterMenus);
        }
    }

    private void assembleCartToMenu(CartItemVO specificationVO) {
        for (Map.Entry<MenuGroupVO, List<MenuVO>> entry : categoryMenusMap.entrySet()) {
            List<MenuVO> list = entry.getValue();
            for (MenuVO menuVO : list) {
                if (menuEqualsSpecification(menuVO, specificationVO)) {
                    specificationVO.setMenuVO(menuVO);
                    if (TextUtils.isEmpty(specificationVO.getSpecification())//如果没有规格做法
                            && specificationVO.isSelf() //且是自己点的
                            && !specificationVO.isCompulsory()) {//且该购物车记录不是必选记录
                        menuVO.setMySpecification(specificationVO);
                    }
                    if (!specificationVO.isCompulsory()) {//购物车记录是必选记录的，不需要展示出来，不可以修改
                        menuVO.addSpecification(specificationVO);
                    } else {
                        menuVO.setCompulsorySpecification(specificationVO);
                    }
                    return;
                }
            }
        }
    }

    /**
     * SpecificationVO是否属于这个MenuVO
     */
    private boolean menuEqualsSpecification(MenuVO menuVO, CartItemVO specificationVO) {
        return (specificationVO.getMenuId().equals(menuVO.getMenu().getId()));
    }


    /**
     * 把菜单列表按照类别进行分组
     *
     * @param list menu list
     */
    private void groupMenu(List<Menu> list) {
        for (Menu menu : list) {
            String spell = menu.getSpell();
            if (!TextUtils.isEmpty(spell)) {
                String letter = spell.substring(0, 1).toUpperCase();
                if (!StringUtils.isLetter(letter)) {
                    letter = "#";
                }
                if (!mLetterList.contains(letter)) {
                    mLetterList.add(letter);
                }
            }
            MenuGroupVO groupVO = new MenuGroupVO(menu.getKindMenuId(), menu.getKindMenuName());
            if (categoryMenusMap.containsKey(groupVO)) {
                List<MenuVO> menuVOs = categoryMenusMap.get(groupVO);
                menuVOs.add(new MenuVO(menu, menuVOs.get(0).getMenuGroupVO()));
            } else {
                List<MenuVO> menus = new ArrayList<>();
                menus.add(new MenuVO(menu, groupVO));
                categoryMenusMap.put(groupVO, menus);
            }
        }
        mGroupVOList.addAll(categoryMenusMap.keySet());
        if (mLetterList != null && !mLetterList.isEmpty()) {
            Collections.sort(mLetterList);
        }
        //初始化侧边栏过滤数据源
        setFilterData(mGroupVOList, mLetterList);
    }

    @Override
    public void queryCartFailed(String errorCode, String errorMsg) {
        loadListFailed();
        ToastUtils.showShortToast(getContext(), errorMsg);
        //如果界面已经有数据了，则不显示StateView
        if (mAssembleCategoryList == null || mAssembleCategoryList.isEmpty()) {
            showErrorView(getString(R.string.module_menu_list_load_failed));
        }
    }

    /**
     * 修改某个分组下点的所有项数总和
     * <br/>
     * 修改分组右侧数量的条件：
     * <ul>
     * <li>当用户点击Item直接点菜，如果是第一次需要修改分组右侧数量</li>
     * <li>当用户点击减号，变为0了，需要修改分组右侧数量</li>
     * <li>当用户直接输入数量0，也需要修改分组右侧数量</li>
     * <p>
     * <ul/>
     */
    private void updateMenuGroupOrderCount(MenuGroupVO menuGroupVO) {
        if (menuGroupVO == null) {
            return;
        }
        List<MenuVO> menuVOs = categoryMenusMap.get(menuGroupVO);
        menuGroupVO.setOrderCount(0);
        for (MenuVO menuVO : menuVOs) {
            if (menuVO.getSpecificationVOs() != null) {
                menuGroupVO.setOrderCount(menuGroupVO.getOrderCount() + menuVO.getSpecificationVOs().size());
            }
        }
    }

    /**
     * @param dinningTableVo 网络返回的购物车数据
     */
    private void renderWhenCartChange(DinningTableVo dinningTableVo) {

        boolean update = updateMemoryCart(dinningTableVo);

        if (!update) {
            return;
        }

        if (mDinningTableVo == null) {
            return;
        }
        //更新底部购物项数统计
        setOrderCountText(mDinningTableVo.getMyDicNum(), mDinningTableVo.getOtherDicNum());
        mCartList = getSpecificationVOList(getContext(), mDinningTableVo);
        resetMenuSpecification();

        assembleCartsToMenuMap(mCartList);

        mImageAdapter.notifyDataSetChanged();

    }

    private void clearKeywordIfNeed() {
        EditText editText = getActivityEdit();
        if (editText != null && !TextUtils.isEmpty(editText.getText())) {
            getActivityEdit().setText("");
        }
    }

    @Override
    public void addMenuToCartSuccess(DinningTableVo dinningTableVo) {
        hideLoading();
        renderWhenCartChange(dinningTableVo);
        //clearKeywordIfNeed();
    }

    @Override
    public void addMenuToCartFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);
    }

    @Override
    public void removeMenuFromCartSuccess(DinningTableVo dinningTableVo) {
        hideLoading();
        renderWhenCartChange(dinningTableVo);
    }

    @Override
    public void removeMenuFromCartFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);
    }

    @Override
    public void modifyInputCartSuccess(DinningTableVo dinningTableVo) {
        hideLoading();
        renderWhenCartChange(dinningTableVo);
    }

    @Override
    public void modifyInputCartFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);
    }

    @Override
    public void getSeatStatusSuccess(int status) {
        if (BuildConfig.DEBUG) {
            Logger.d("桌位状态:" + status);
        }
        mImageAdapter.setSeatCodeStatus(status);
        if (mAssembleCategoryList != null && !mAssembleCategoryList.isEmpty()) {
            mImageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getSeatStatusFailed(String errorCode, String errorMsg) {
        hideLoading();
        Logger.e("MenuListFragment", "获取桌位状态失败：" + errorMsg);
    }

    @OnClick(R2.id.layout_cart_count)
    void processClick(View view) {
        if (R.id.layout_cart_count == view.getId()) {
            //test recyclerView scroll position
            //LinearLayoutManager llm = (LinearLayoutManager) getRecyclerView().getLayoutManager();
            //int first = llm.findFirstVisibleItemPosition();
            //View itemView = llm.findViewByPosition(first);
            //int top = itemView.getTop();
            //Logger.d(first+",top:"+top);

            if (mCreateOrderParam == null) {
                ToastUtils.showShortToast(getContext(), "mCreateOrderParam is null");
                return;
            }

            if (mFrom == RouterPathConstant.RetailCart.EXTRA_FROM_HANG_UP_ORDER) {
                getActivity().setResult(Activity.RESULT_OK, null);
                getActivity().finish();
            } else {
                MRouter.getInstance().build(RouterPathConstant.RetailCart.PATH_CART)
                        .putSerializable(RouterPathConstant.RetailMenuList.EXTRA_CREATE_ORDER_PARAM, mCreateOrderParam)
                        .putSerializable(RouterPathConstant.RetailCart.EXTRA_DINNING_VO, mDinningTableVo)
                        .navigation(getActivity());
            }
        }
    }

    /**
     * 用户点击加减、输入数量修改购物车
     */
    private List<CartItem> getCartItemsBySpecificationVO(CartItemVO specificationVO, double num) {

        List<CartItem> items = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setMenuId(specificationVO.getMenuId());
        cartItem.setMenuName(specificationVO.getMenuName());
        cartItem.setNum(num);
        cartItem.setUid(UserHelper.getMemberId());
        cartItem.setIndex(specificationVO.getIndex());
        cartItem.setKindType(specificationVO.getItemVo().getKind());
        cartItem.setSource(CartHelper.CartSource.MENU_LIST);

        //如果是单单位，把accountNum设置和num一样
        if (!specificationVO.isTwoAccount()) {
            cartItem.setAccountNum(num);
        } else {
            cartItem.setAccountNum(specificationVO.getItemVo().getAccountNum());
        }

        cartItem.setSpecId(specificationVO.getSpecId());
        cartItem.setMakeId(specificationVO.getMakeId());
        //如果有套餐子菜、加料菜
        cartItem.setChildCartVos(specificationVO.getChildCartVos());

        //用户输入的备注
        cartItem.setMemo(specificationVO.getMemo());
        //用户选择的备注
        cartItem.setLabels(specificationVO.getLabels());

        items.add(cartItem);
        return items;
    }

    /**
     * 点击item直接加入购物车
     */
    private List<CartItem> getCartItemsByMenuVO(MenuVO menuVO) {
        List<CartItem> items = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setUid(UserHelper.getMemberId());
        cartItem.setMenuId(menuVO.getMenuId());
        cartItem.setMenuName(menuVO.getMenuName());
        cartItem.setNum(menuVO.getTmpNum());
        cartItem.setKindType(menuVO.getKindWhenUpdateCart());
        cartItem.setIndex(menuVO.getMySpecification() == null ?
                CartItemVO.createIndex() : menuVO.getMySpecification().getIndex());
        cartItem.setSource(CartHelper.CartSource.MENU_LIST);
        items.add(cartItem);
        //点击item直接加入购物车的话，是没有加料菜的
        //点击item直接加入购物车的话，是没有备注的
        return items;
    }

    private List<CartItem> getCartItemsByMenu(Menu menu, double num, int type, String index) {
        List<CartItem> items = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setUid(UserHelper.getMemberId());
        cartItem.setMenuId(menu.getId());
        cartItem.setMenuName(menu.getName());
        if (num <= 0) cartItem.setNum(0.d);
        else cartItem.setNum(1.d);
        cartItem.setAccountNum(num);
        cartItem.setKindType(type);
        cartItem.setIndex(index);
        cartItem.setSource(CartHelper.CartSource.MENU_LIST);
        items.add(cartItem);
        return items;
    }

    @Override
    public void onAdapterClick(int type, View view, Object data) {
        switch (type) {
            //用户直接输入
            case RetailMenuListAdapter.CLICK_TYPE_INPUT_DONE:
                if (!(data instanceof CartItemVO)) {
                    return;
                }
                CartItemVO inputSpecVO = (CartItemVO) data;
                List<CartItem> list = getCartItemsBySpecificationVO(inputSpecVO,
                        inputSpecVO.getTmpNum());
                mPresenter.modifyInputCart(getSeatCode(), getOrderId(), list);
                break;
            //点击加号
            case RetailMenuListAdapter.CLICK_TYPE_ITEM_PLUS:
                if (!(view instanceof FoodNumberTextView)) {
                    return;
                }
                if (!(data instanceof CartItemVO)) {
                    return;
                }
                CartItemVO mAddSpecificationVO = (CartItemVO) data;
                if (mAddSpecificationVO.getNum() >= CartHelper.FoodNum.MAX_VALUE) {
                    return;
                }
                double tmpNum = mAddSpecificationVO.getNum() + 1;
                mAddSpecificationVO.setTmpNum(tmpNum);
                List<CartItem> items = getCartItemsBySpecificationVO(mAddSpecificationVO, tmpNum);
                mPresenter.addMenuToCart(getSeatCode(), getOrderId(), items);
                break;
            //点击减号，从购物车中移除
            case RetailMenuListAdapter.CLICK_TYPE_ITEM_MINUS:
                if (!(data instanceof CartItemVO)) {
                    return;
                }
                CartItemVO mSpecVO = (CartItemVO) data;

                double tmpNUm = mSpecVO.getNum() - 1;
                if (mSpecVO.getStartNum() > 1 && mSpecVO.getStartNum() == mSpecVO.getNum()) {
                    tmpNUm = 0;
                }
                List<CartItem> items3 = getCartItemsBySpecificationVO(mSpecVO, tmpNUm);
                mPresenter.removeMenuFromCart(getSeatCode(), getOrderId(), items3);
                break;
            //点击Item
            case RetailMenuListAdapter.CLICK_TYPE_ADD_CART:

                if (!(view instanceof FoodNumberTextView)) {
                    return;
                }
                if (!(data instanceof MenuVO)) {
                    return;
                }
                MenuVO mTempMenuVO = (MenuVO) data;

                double startNum = 1;
                //如果该菜有起点份数
                if (mTempMenuVO.getStartNum() > 1) {
                    startNum = mTempMenuVO.getStartNum();
                }
                mTempMenuVO.setTmpNum(mTempMenuVO.getMySpecification() == null ? startNum
                        : mTempMenuVO.getMySpecification().getNum() + 1);
                List<CartItem> items2 = getCartItemsByMenuVO(mTempMenuVO);
                mPresenter.addMenuToCart(getSeatCode(), getOrderId(), items2);
                break;
            //称重商品
            case RetailMenuListAdapter.CLICK_RETAIL_WEIGH:
                if (!(data instanceof MenuVO)) {
                    return;
                }
                MenuVO menuWeight = (MenuVO) data;
                showWeightDialog(menuWeight);
                break;
            case RetailMenuListAdapter.CLICK_RETAIL_WEIGH_CLEAR:
                if (!(data instanceof MenuVO)) {
                    return;
                }
                MenuVO menu = (MenuVO) data;
                CartItemVO mSpecVO2 = menu.getSpecificationVOs().get(0);
                List<CartItem> items5 = getCartItemsBySpecificationVO(mSpecVO2, 0);
                mPresenter.removeMenuFromCart(getSeatCode(), getOrderId(), items5);
                break;
        }
    }

    @Override
    public void onAdapterClickEdit(int type, View view, Object data) {
        switch (type) {
            case RetailMenuListAdapter.CLICK_TYPE_EDIT_DIALOG:
                if (!(view instanceof FoodNumberTextView)) {
                    return;
                }
                if (!(data instanceof CartItemVO)) {
                    return;
                }
                showEditValueDialog(data,
                        ((CartItemVO) data).getStartNum(),
                        ((CartItemVO) data).getTmpNum(),
                        ((CartItemVO) data).getMenuName(),
                        ((CartItemVO) data).getUnit(),
                        CartHelper.DialogDateFrom.OTHER_VIEW);
                break;
        }
    }

    private void showWeightDialog(MenuVO menuWeight) {
        if (mRetailWeighDialog == null) {
            mRetailWeighDialog = new RetailWeighDialog();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(RetailWeighDialog.RETAIL_WEIGH_MENU, menuWeight.getMenu());
        bundle.putInt(RetailWeighDialog.RETAIL_WEIGH_TYPE, menuWeight.getKindWhenUpdateCart());
        bundle.putString(RetailWeighDialog.RETAIL_WEIGH_INDEX, menuWeight.getMySpecification() == null ?
                CartItemVO.createIndex() : menuWeight.getMySpecification().getIndex());
        if (menuWeight.getMySpecification() != null)
            bundle.putDouble(RetailWeighDialog.RETAIL_WEIGH_NUM, menuWeight.getMySpecification().getAccountNum());
        if (mRetailWeighDialog.getArguments() == null) {
            //不能使用setArguments到相同的fragment
            mRetailWeighDialog.setArguments(bundle);
        } else {
            //不为空则重新设置arguments
            mRetailWeighDialog.getArguments().putAll(bundle);
        }
        //判断fragment是否已经added
        if (isResumed() && !mRetailWeighDialog.isAdded()) {
            mRetailWeighDialog.show(getChildFragmentManager(), "RetailWeighDialog");
            showInputMethod();
        }
    }

    public void addWeightMenu(Menu menu, double num, int type, String index) {
        mRetailWeighDialog.dismissAllowingStateLoss();
        List<CartItem> items = getCartItemsByMenu(menu, num, type, index);
        mPresenter.addMenuToCart(getSeatCode(), getOrderId(), items);
    }

    private void showInputMethod() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //自动弹出键盘
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                //强制隐藏Android输入法窗口
                // inputManager.hideSoftInputFromWindow(edit.getWindowToken(),0);
            }
        }, 200);
    }

    public void showMenusByLetter(String letter) {
        int index = mAssembleLetterList.indexOf(letter);
        if (index != -1) {
            LinearLayoutManager llm = (LinearLayoutManager) getRecyclerView().getLayoutManager();
            llm.scrollToPositionWithOffset(index, 0);
        }
    }

    /**
     * 按字母的方式展示（所有）
     */
    public void showMenusByLetter() {
        mImageAdapter.setList(mAssembleLetterList);
        getRecyclerView().setAdapter(mImageAdapter);
    }

    /**
     * 按分类的方式展示（所有）
     */
    public void showMenusByCategory() {
        mImageAdapter.setList(mAssembleCategoryList);
        mImageAdapter.notifyDataSetChanged();
    }

    public void showMenusByCategory(MenuGroupVO groupVO) {
        int index = mAssembleCategoryList.indexOf(groupVO);
        if (index != -1) {
            LinearLayoutManager llm = (LinearLayoutManager) getRecyclerView().getLayoutManager();
            llm.scrollToPositionWithOffset(index, 0);
        }
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();

        if (!mCartListSuccess && !mMenuListSuccess) {
            mPresenter.loadMenuList(UserHelper.getEntityId(), getSeatCode(), getOrderId(), true);
            return;
        }

        if (!mMenuListSuccess) {
            mPresenter.loadMenuList(UserHelper.getEntityId(), getSeatCode(), getOrderId(), true);
            return;
        }

        if (!mCartListSuccess) {
            mPresenter.queryCart(getSeatCode(), getOrderId());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusHelper.unregister(this);
    }

    /**
     * 弹出数量输入框
     */
    private void showEditValueDialog(final Object data, double startNum, double number, String menuName, String unit, int from) {

        EditFoodNumberDialog dialog = new EditFoodNumberDialog(getContext());
        dialog.initDialog(data, startNum, number, menuName, unit, from);
        dialog.setNegativeListener(new EditFoodNumberDialog.DialogNegativeListener() {
            @Override
            public void onClick() {

            }
        });
        dialog.setPositiveListener(new EditFoodNumberDialog.DialogPositiveListener() {
            @Override
            public void onClick(View view, Object data, double number) {
                if(data instanceof CartItemVO) {
                    ((CartItemVO) data).setTmpNum(number);
                }
                    onAdapterClick(MenuListAdapter.CLICK_TYPE_INPUT_DONE, view, data);
            }
        });
    }

}
