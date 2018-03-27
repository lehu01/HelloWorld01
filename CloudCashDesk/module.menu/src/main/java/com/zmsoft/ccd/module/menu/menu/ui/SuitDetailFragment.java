package com.zmsoft.ccd.module.menu.menu.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.widget.FoodNumberTextView;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.Item;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.dagger.ComponentManager;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.helper.SpecificationDataMapLayer;
import com.zmsoft.ccd.module.menu.menu.adapter.MenuListAdapter;
import com.zmsoft.ccd.module.menu.menu.adapter.SuitMenuAdapter;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;
import com.zmsoft.ccd.module.menu.menu.bean.ParamSuitSubMenu;
import com.zmsoft.ccd.module.menu.menu.bean.Recipe;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenu;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenuGroup;
import com.zmsoft.ccd.module.menu.menu.bean.SuitMenuHitRule;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitChildVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitGroupVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitMenuDetailVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitMenuVO;
import com.zmsoft.ccd.module.menu.menu.converter.CartItemConverter;
import com.zmsoft.ccd.module.menu.menu.presenter.SuitDetailContract;
import com.zmsoft.ccd.module.menu.menu.presenter.SuitDetailPresenter;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.DaggerSuitDetailComponent;
import com.zmsoft.ccd.module.menu.menu.presenter.dagger.SuitDetailPresenterModule;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.zmsoft.ccd.module.menu.menu.bean.vo.MenuVO.createForSuit;


/**
 * Description：套餐详情、修改套餐、下单后修改套餐、必点套餐修改
 * <br/>
 * <p>
 * Created by kumu on 2017/4/21.
 */

public class SuitDetailFragment extends BaseListFragment
        implements SuitDetailContract.View, BaseListAdapter.AdapterClick {

    /**
     * 添加套餐
     */
    static final int TYPE_FROM_MENU_LIST = 0;
    /**
     * 加入购物车后对套餐修改
     */
    static final int TYPE_FROM_CART = 1;
    /**
     * 下单后对套餐修改
     */
    static final int TYPE_FROM_ORDER = 2;
    /**
     * 必选套餐修改
     */
    static final int TYPE_FROM_REQUIRE_SUIT = 3;

    @Inject
    SuitDetailPresenter mPresenter;

    @BindView(R2.id.text_num_flag)
    TextView mTextNumFlag;
    @BindView(R2.id.edit_suit_num)
    FoodNumberTextView mEditSuitNum;
    @BindView(R2.id.text_suit_price)
    TextView mTextSuitPrice;
    @BindView(R2.id.text_confirm)
    TextView mTextConfirm;
    @BindView(R2.id.layout_bottom)
    RelativeLayout mLayoutBottom;
    @BindView(R2.id.text_ordered_num)
    TextView mTextOrderedNum;
    private int mType = TYPE_FROM_MENU_LIST;
    private OrderParam mCreateOrderParam;

    //计价规则
    private List<SuitMenuHitRule> mSuitMenuHitRules;
    private HashMap<String, List<MenuVO>> mMemoryCart = new HashMap<>();
    private String mSuitMenuId;
    private SuitMenu mSuitMenu;
    private SuitMenuVO mSuitMenuVO;
    private List<Object> mAdapterData;
    private ItemVo mItemVo;
    private BaseMenuVo mBaseMenuVo;

    private boolean mSuitMenuSellOut;

    private CompositeSubscription compositeSub;

    @Override
    protected int getLayoutId() {
        return R.layout.module_menu_fragment_suit_detail;
    }

    private void addSubscription(Subscription s) {
        if (compositeSub == null) {
            compositeSub = new CompositeSubscription();
        }
        compositeSub.add(s);
    }

    @Override
    protected void initParameters() {
        super.initParameters();
        DaggerSuitDetailComponent.builder()
                .menuSourceComponent(ComponentManager.get().getMenuSourceComponent())
                .suitDetailPresenterModule(new SuitDetailPresenterModule(this))
                .build()
                .inject(this);


        EventBusHelper.register(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mCreateOrderParam = (OrderParam) bundle
                    .getSerializable(RouterPathConstant.SuitDetail.PARAM_CREATE_ORDER_PARAM);
            mItemVo = (ItemVo) bundle.getSerializable(RouterPathConstant.SuitDetail.PARAM_ITEMVO);
            if (mItemVo != null) {
                if (mItemVo.getIsCompulsory()) {
                    mType = TYPE_FROM_REQUIRE_SUIT;
                } else {
                    mType = TYPE_FROM_CART;
                }
            }

            mBaseMenuVo = (BaseMenuVo) bundle.getSerializable(RouterPathConstant.SuitDetail.PARAM_SUIT_BASE_MENU_VO);

            if (mSuitMenu == null) {
                mSuitMenuId = bundle.getString(RouterPathConstant.SuitDetail.PARAM_SUIT_MENU_ID);
            } else {
                mSuitMenuVO = new SuitMenuVO(mSuitMenu, mItemVo);
            }
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        disableRefresh();

        if (mItemVo != null) {
            mEditSuitNum.setNumberText(mItemVo.getNum());
        } else {
            mEditSuitNum.setNumberText(1);
        }
        mEditSuitNum.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
            @Override
            public void onClick(int which, double numberValue) {
                switch (which) {
                    case FoodNumberTextView.CLICK_LEFT:
                        if (numberValue > 1) {
                            mEditSuitNum.setNumberText(numberValue - 1);
                            calculateInBgThread();
                            initButtonWhenChange();
                        }
                        break;
                    case FoodNumberTextView.CLICK_RIGHT:
                        mEditSuitNum.setNumberText(numberValue + 1);
                        calculateInBgThread();
                        initButtonWhenChange();
                        break;
                }
            }
        });
        mEditSuitNum.setOnClickEdit(new FoodNumberTextView.OnClickEdit() {
            @Override
            public void onClickEdit(double numberValue) {
                showEditValueDialog(mSuitMenuVO, 1, numberValue, mSuitMenuVO.getMenuName(), "", CartHelper.DialogDateFrom.OTHER_VIEW);
            }
        });
      /*  mEditSuitNum.setOnInputDone(new FoodNumberTextView.OnInputDone() {
            @Override
            public void onDone(double inputNum) {
                handleInputDoneOrBack(inputNum);
            }
        });
        mEditSuitNum.setSoftBackListener(new FoodNumberTextView.SoftBackListener() {
            @Override
            public void onSoftBack(TextView textView, double inputNum) {
                handleInputDoneOrBack(inputNum);
            }
        });*/
        setPriceValue("0.00");
        initBottomMenu();
    }

    private void handleInputDoneOrBack(double inputNum) {
        if (inputNum == 0 && mType == TYPE_FROM_MENU_LIST) {
            mEditSuitNum.setNumberText(1);
        }
        if (mType == TYPE_FROM_CART && mItemVo != null && mItemVo.getNum() != mEditSuitNum.getNumber()) {
            initButtonWhenChange();
        }
    }

    private void setPriceValue(String price) {
        if (mTextSuitPrice != null) {
            mTextSuitPrice.setText(getString(R.string.module_menu_list_suit_detail_price, FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , price)));
        }
    }

    private void initBottomMenu() {
        switch (mType) {
            case TYPE_FROM_CART:
                mTextNumFlag.setText("");
                mTextNumFlag.setCompoundDrawablesWithIntrinsicBounds(R.drawable.module_menu_ic_clear, 0, 0, 0);
                if (mItemVo != null) {
                    mTextOrderedNum.setText(getString(R.string.module_menu_suit_menu_ordered_num,
                            NumberUtils.trimPointIfZero(mItemVo.getNum()), mItemVo.getUnit()));
                }
                mTextConfirm.setText(R.string.module_menu_suit_menu_update_confirm);
                mTextConfirm.setVisibility(View.GONE);
                mTextOrderedNum.setVisibility(View.VISIBLE);
                break;
            case TYPE_FROM_ORDER:
                break;
            case TYPE_FROM_REQUIRE_SUIT://该套餐是必选套餐
                mTextNumFlag.setText(getString(R.string.module_menu_suit_menu_ordered_num,
                        NumberUtils.trimPointIfZero(mItemVo.getNum()), mItemVo.getUnit()));
                mEditSuitNum.setVisibility(View.GONE);
                mTextConfirm.setText(R.string.module_menu_suit_menu_update_confirm);
                break;
        }
    }

    private String getSuitMenuId() {
        if (!TextUtils.isEmpty(mSuitMenuId)) {
            return mSuitMenuId;
        } else if (mItemVo != null) {
            return mItemVo.getMenuId();
        }
        return null;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (!TextUtils.isEmpty(getSuitMenuId())) {
            disableAutoRefresh();
            showLoadingView();
        }
    }

    @Override
    protected void loadListData() {
        if (mBaseMenuVo == null) {
            mPresenter.loadSuitDetail(getSuitMenuId());
        } else {
            mPresenter.loadSuitDetail(getSuitMenuId(), mBaseMenuVo);
        }
    }

    @Override
    protected int getAccentColor() {
        return R.color.accentColor;
    }

    @Override
    protected BaseListAdapter createAdapter() {
        SuitMenuAdapter adapter = new SuitMenuAdapter(getContext(), this);
        adapter.setCreateOrderParam(mCreateOrderParam);
        return adapter;
    }


    @Override
    public void unBindPresenterFromView() {
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }

    public void renderListData(List list) {
        finishRefresh();
        getAdapter().setList(list);
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void loadSuitDetailSuccess(SuitMenuDetailVO suitMenuVO) {
        showContentView();
        assembleShowDataWithRxJava(suitMenuVO);
    }

    private List<Item> getItemsByCondition(ItemVo itemVO, String menuId, String specId, String groupId) {
        List<Item> items = itemVO.getChildItems();
        if (items == null || items.isEmpty()) {
            return null;
        }
        List<Item> list = new ArrayList<>();
        for (Item item : items) {
            //相同的子菜，相同的规格，相同的分组
            if (item.getMenuId().equals(menuId)
                    && StringUtils.checkSameString(specId, item.getSpecDetailId())
                    && StringUtils.checkSameString(groupId, item.getSuitMenuDetailId())) {
                list.add(item);
            }
        }
        return list;
    }


    private void assembleShowDataWithRxJava(final SuitMenuDetailVO suitMenuVO) {
        Subscription s = RxUtils.fromCallable(new Callable<List<Object>>() {
            @Override
            public List<Object> call() throws Exception {
                mSuitMenuHitRules = suitMenuVO.getSuitMenuHitRules();
                mSuitMenu = suitMenuVO.getSuitMenu();
                mSuitMenuVO = new SuitMenuVO(mSuitMenu, mItemVo);
                mSuitMenuSellOut = suitMenuVO.isSuitMenuSellOut();
                return mAdapterData = assembleShowData(mSuitMenu);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .subscribe(new Action1<List<Object>>() {
                    @Override
                    public void call(List<Object> objects) {
                        if (objects == null) {
                            return;
                        }
                        if (mAdapterData != null && !mAdapterData.isEmpty()) {
                            getAdapter().removeAll();
                            renderListData(mAdapterData);
                            mLayoutBottom.setVisibility(View.VISIBLE);
                        }

                        if (mSuitMenu != null && getActivity() instanceof SuitDetailActivity) {
                            SuitDetailActivity activity = (SuitDetailActivity) getActivity();
                            activity.setTitleText(mSuitMenu.getName());
                        }

                        if (mSuitMenuSellOut) {
                            mLayoutBottom.setVisibility(View.GONE);
                            getDialogUtil().showNoticeDialog(
                                    R.string.module_menu_dialog_title_suit_sell_out,
                                    getString(R.string.module_menu_dialog_content_suit_sell_out),
                                    R.string.module_menu_dialog_button_suit_sell_out, false, new SingleButtonCallback() {
                                        @Override
                                        public void onClick(DialogUtilAction which) {
                                            getActivity().finish();
                                        }
                                    });
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
        addSubscription(s);
    }


    private List<Object> assembleShowData(SuitMenu suitMenu) {
        if (suitMenu != null) {
            List<SuitMenuGroup> groups = suitMenu.getSuitMenuGroupVos();
            if (groups == null || groups.isEmpty()) {
                return null;
            }
            List<Object> container = new ArrayList<>();
            for (SuitMenuGroup group : groups) {
                if (group.getMenus() == null || group.getMenus().isEmpty()) {
                    continue;
                }
                //添加Group
                SuitGroupVO suitGroupVO = new SuitGroupVO(group);
                container.add(suitGroupVO);
                List<Menu> menus = group.getMenus();
                //添加分组下面所有的菜
                for (Menu menu : menus) {
                    MenuVO showMenuVO = null;
                    MenuVO memoryMenuVO = null;
                    if (mItemVo != null) {
                        List<Item> items = getItemsByCondition(mItemVo, menu.getId(), menu.getSpecDetailId(), group.getSuitMenuDetailId());
                        if (items != null) {
                            //创建一个展示才信息的Item bean
                            showMenuVO = new MenuVO(menu, suitGroupVO);
                            container.add(showMenuVO);
                            for (Item item : items) {
                                Recipe recipe = new Recipe(item.getMakeId(), item.getMakeName(), item.getMakePrice(), item.getMakePriceMode());
                                memoryMenuVO = MenuVO.createForSuit(menu, item, suitGroupVO, item.getNum(), item.getAccountNum(),
                                        item.getSpecDetailId(), recipe, item.getLabels(), item.getMemo(),
                                        item.getIsWait(), item.getKind(), item.getDoubleUnitStatus(),
                                        SpecificationDataMapLayer.getMenuSpecification(item.getSpecDetailName(), item.getMakeName(), item.getLabels(), item.getMemo()),
                                        SpecificationDataMapLayer.getCombSubFoodAddPrice(menu, recipe, item.getNum(), item.getAccountNum()), item.getIndex());

                                suitGroupVO.setCurrentNum(suitGroupVO.getCurrentNum() + memoryMenuVO.getTmpNum());
                                //memoryMenuVO = MenuVO.createForSuit(showMenuVO, item.getIndex());
                                memoryMenuVO.setBelongMenu(showMenuVO);
                                memoryMenuVO.addToBelongSelectMenus();
                                if (TextUtils.isEmpty(memoryMenuVO.getSpecification())) {
                                    showMenuVO.setSelectedMenuNoSpec(memoryMenuVO);
                                }
                                addMenuToMemoryCart(memoryMenuVO);//内存购物车
                                //如果该菜点了多项或者有`描述`
                                if (items.size() > 1 || !TextUtils.isEmpty(memoryMenuVO.getSpecification())) {
                                    memoryMenuVO.setSelectedMenuNewLine(true);
                                    container.add(memoryMenuVO);
                                }
                            }
                        }
                    }

                    if (showMenuVO == null) {
                        showMenuVO = new MenuVO(menu, suitGroupVO);
                        //默认设置第一个做法
                        if (menu.getMakeDataDtos() != null && menu.getMakeDataDtos().size() > 0) {
                            showMenuVO.setSelectedMake(menu.getMakeDataDtos().get(0));
                        }
                        //Item Menu Info View Data
                        container.add(showMenuVO);
                    }


                    //如果是全部必选，默认加进购物车
                    if (group.getIsRequired() == 1) {
                        showMenuVO.setTmpNum(menu.getPerNum());
                        if (memoryMenuVO == null) {
                            memoryMenuVO = MenuVO.createForSuit(showMenuVO.getMenu(), showMenuVO.getItem(), showMenuVO.getSuitGroupVO(),
                                    showMenuVO.getTmpNum(), showMenuVO.getAccountNum(), showMenuVO.getSpecDetailId(), showMenuVO.getSelectedMake(),
                                    showMenuVO.getLabels(), showMenuVO.getMemo(), showMenuVO.getIsWait(), showMenuVO.getKindType(), showMenuVO.getDoubleUnitStatus(),
                                    getSpecificationFromMenu(showMenuVO),
                                    SpecificationDataMapLayer.getCombSubFoodAddPrice(showMenuVO.getMenu(), showMenuVO.getSelectedMake(), showMenuVO.getMenu().getPerNum(), 1), null);
                            memoryMenuVO.setBelongMenu(showMenuVO);
                            memoryMenuVO.addToBelongSelectMenus();
                            if (TextUtils.isEmpty(memoryMenuVO.getSpecification())) {
                                showMenuVO.setSelectedMenuNoSpec(memoryMenuVO);
                            }
                            addMenuToMemoryCart(memoryMenuVO);//内存购物车
                            suitGroupVO.setCurrentNum(suitGroupVO.getCurrentNum() + showMenuVO.getTmpNum());
                            if (!TextUtils.isEmpty(memoryMenuVO.getSpecification())) {
                                memoryMenuVO.setSelectedMenuNewLine(true);
                                container.add(memoryMenuVO);
                            }
                        }
                    }
//                    else {
//                        if (memoryMenuVO != null && !TextUtils.isEmpty(memoryMenuVO.getSpecification())) {
//                            memoryMenuVO.setSelectedMenuNewLine(true);
//                            container.add(memoryMenuVO);
//                        }
//                    }
                }
            }
            calculateInBgThread();
            return container;
        }
        return null;
    }

    private void addMenuToMemoryCart(MenuVO menuVO) {
        if (menuVO == null) {
            return;
        }
        List<MenuVO> list = mMemoryCart.get(menuVO.getMenuId());
        if (list != null) {
            list.add(menuVO);
        } else {
            list = new ArrayList<>();
            list.add(menuVO);
            mMemoryCart.put(menuVO.getMenuId(), list);
        }
    }

    private String getSpecificationFromMenu(MenuVO menuVO) {
        return SpecificationDataMapLayer.getMenuSpecification(menuVO.getSpecDetailName(),
                menuVO.getSelectedMake() != null ? menuVO.getSelectedMake().getName() : null,
                menuVO.getItem() != null ? menuVO.getItem().getLabels() : null,
                menuVO.getMemo());
    }


    @Override
    public void loadSuitDetailFailed(String errorCode, String errorMessage) {
        loadListFailed();
        showErrorView(getString(R.string.module_menu_suit_menu_detail_load_failed));
    }

    @Override
    public void addSuitToCartSuccess(DinningTableVo data) {
        hideLoading();
        switch (mType) {
            case TYPE_FROM_REQUIRE_SUIT:
            case TYPE_FROM_CART:
                RouterBaseEvent.CommonEvent event = RouterBaseEvent.CommonEvent.EVENT_REFRESH_SHOP_CAR;
                event.setObject(mCreateOrderParam);
                EventBusHelper.post(event);
                break;
            case TYPE_FROM_MENU_LIST:
                RouterBaseEvent.CommonEvent eventMenuList = RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_CART;
                eventMenuList.setObject(data);
                EventBusHelper.post(eventMenuList);
                break;
        }
        getActivity().finish();
    }

    @Override
    public void addSuitToCartFailed(String errorCode, String errorMessage) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMessage);
    }

    @Override
    public void loadSuitRuleSuccess(List<SuitMenuHitRule> suitMenuHitRules) {
        mSuitMenuHitRules = suitMenuHitRules;
    }

    @Override
    public void loadSuitRuleFailed(String errorCode, String errorMessage) {
        Logger.e(errorCode + "," + errorMessage);
    }

    /**
     * 数量进行校验（分组上的总数限制、单个菜的限制）
     * <br/>
     * 如果点的数量合法，则数量+1，且返回true，否则返回false
     */
    private boolean checkNum(MenuVO menuVO, double unitNum) {
        //检查单个菜的限制
        if (!menuVO.isNoLimit()) {
            if (unitNum + getMenuNumCount(menuVO, true) > menuVO.getLimitNum()) {
                ToastUtils.showShortToast(getContext(),
                        getString(R.string.module_menu_suit_sub_num_limit, menuVO.getMenuName(),
                                NumberUtils.trimPointIfZero(menuVO.getLimitNum())));
                return false;
            }
        }
        //检查分组上的总数限制
        if (!menuVO.getSuitGroupVO().isNoLimit()) {//如果分组有数量限制
            if (menuVO.getSuitGroupVO().getNum() > menuVO.getSuitGroupVO().getCurrentNum()) {
                //分组数量+1
                menuVO.getSuitGroupVO().setCurrentNum(menuVO.getSuitGroupVO().getCurrentNum() + 1);
                //子菜数量+1
                menuVO.setTmpNum(unitNum);
                return true;
            } else {
                ToastUtils.showShortToast(getContext(),
                        String.format(getString(R.string.module_menu_suit_detail_group_num_limit)
                                , menuVO.getSuitGroupName(), 1 + ""));
            }
        } else {//数量没有限制
            //分组数量+1
            menuVO.getSuitGroupVO().setCurrentNum(menuVO.getSuitGroupVO().getCurrentNum() + 1);
            //子菜数量+1
            menuVO.setTmpNum(menuVO.getTmpNum() + 1);
            return true;
        }
        return false;
    }

    private void initButtonWhenChange() {
        switch (mType) {
            case TYPE_FROM_CART:
                mTextConfirm.setVisibility(View.VISIBLE);
                mTextOrderedNum.setVisibility(View.GONE);
                break;
        }
    }


    private void processAddToCart(MenuVO menuVO, boolean clickItem) {
        boolean shouldRecalculatePrice = false;
        MenuVO targetVO;
        double unitNum;
        if (clickItem) {
            if (menuVO.getSelectedMenuNoSpec() == null) {
                targetVO = MenuVO.createForSuit(menuVO, null);
                unitNum = 1;
            } else {
                targetVO = menuVO.getSelectedMenuNoSpec();
                unitNum = targetVO.getTmpNum() + 1;
            }
            targetVO.setBelongMenu(menuVO);
        } else {
            targetVO = menuVO;
            unitNum = targetVO.getTmpNum() + 1;
        }
        List<MenuVO> memoryMenus = mMemoryCart.get(menuVO.getMenuId());
        if (memoryMenus == null) {
            if (checkNum(targetVO, unitNum)) {
                if (clickItem && menuVO.getSelectedMenuNoSpec() == null) {
                    memoryMenus = new ArrayList<>();
                    memoryMenus.add(targetVO);
                    targetVO.addToBelongSelectMenus();
                    mMemoryCart.put(menuVO.getMenuId(), memoryMenus);
                    menuVO.setSelectedMenuNoSpec(targetVO);
                    targetVO.setBelongMenu(menuVO);
                }
                getAdapter().notifyDataSetChanged();
                initButtonWhenChange();
                shouldRecalculatePrice = true;
            }
        } else {
            if (checkNum(targetVO, unitNum)) {
                if (clickItem) {
                    boolean withoutSpec = false;
                    if (menuVO.getSelectedMenuNoSpec() == null) {
                        withoutSpec = true;
                        menuVO.setSelectedMenuNoSpec(targetVO);
                        if (!memoryMenus.contains(targetVO)) {
                            memoryMenus.add(targetVO);
                        }
                    }
                    if (menuVO.getSelectedMenuNoSpec() != null) {
                        boolean hasNewLine = (menuVO.getSelectedMenuSize() == 1 && !TextUtils.isEmpty(menuVO.getSelectedMenus().get(0).getSpecification()))
                                || menuVO.getSelectedMenuSize() > 1;
                        //一开始没有NoSpe 且 有换行显示的，要把NoSpe换行显示
                        if (withoutSpec && hasNewLine) {
                            int index = mAdapterData.indexOf(menuVO);
                            if (index != -1) {
                                menuVO.getSelectedMenuNoSpec().setSelectedMenuNewLine(true);
                                addToAdapterList(index + menuVO.getSelectedMenuSize() + 1, menuVO.getSelectedMenuNoSpec());
                            }
                        }
                    }
                    //加入操作要滞后
                    if (withoutSpec) {
                        targetVO.addToBelongSelectMenus();
                    }
                }

                getAdapter().notifyDataSetChanged();
                initButtonWhenChange();
                shouldRecalculatePrice = true;
            }
        }
        if (shouldRecalculatePrice) {
            calculateInBgThread();
        }
        printDebug("add", menuVO);
    }

    private void switchNoSpecShow(MenuVO menuVO) {
        //如果该菜只剩下一个没有描述的，且还是换行显示。则不换行显示，恢复到Item中显示
        if (menuVO.getBelongMenu() != null && menuVO.getBelongMenu().getSelectedMenus() != null) {
            if (menuVO.getBelongMenu().getSelectedMenus().size() == 1) {
                MenuVO m = menuVO.getBelongMenu().getSelectedMenus().get(0);
                if (TextUtils.isEmpty(m.getSpecification()) && m.isSelectedMenuNewLine()) {
                    getAdapter().removeObject(m);
                }
            }
        }
    }

    //有规格做法的减号
    //Item里的加号
    private void removeFromCart(MenuVO menuVO) {
        List<MenuVO> menuVOs = mMemoryCart.get(menuVO.getMenuId());
        if (menuVOs == null) {
            return;
        }
        if (menuVO.getTmpNum() > 0) {
            menuVO.setTmpNum(menuVO.getTmpNum() - 1);
            if (menuVO.getSuitGroupVO().getCurrentNum() > 0) {
                menuVO.getSuitGroupVO().setCurrentNum(menuVO.getSuitGroupVO().getCurrentNum() - 1);
            }
            if (menuVO.getTmpNum() == 0) {
                menuVOs.remove(menuVO);
                menuVO.removeSelfFromBelong();
                //如果操作的是的没有描述的menu记录
                if (TextUtils.isEmpty(menuVO.getSpecification())) {
                    menuVO.resetNoSpecMenuFromBelong();
                }

                //如果该菜只剩下一个没有描述的，且还是换行显示。则不换行显示，恢复到Item中显示
                switchNoSpecShow(menuVO);

                if (menuVO.isSelectedMenuNewLine()) {
                    getAdapter().removeObject(menuVO);
                }
            }
            initButtonWhenChange();
            getAdapter().notifyDataSetChanged();
        } else {
            menuVOs.remove(menuVO);
            menuVO.removeSelfFromBelong();
            //如果操作的是的没有描述的menu记录
            if (TextUtils.isEmpty(menuVO.getSpecification())) {
                menuVO.resetNoSpecMenuFromBelong();
            }

            //如果该菜只剩下一个没有描述的，且还是换行显示。则不换行显示，恢复到Item中显示
            switchNoSpecShow(menuVO);

            if (menuVO.isSelectedMenuNewLine()) {
                getAdapter().removeObject(menuVO);
            }
        }
        calculateInBgThread();
        printDebug("remove", menuVO);
    }

    @Override
    public void onAdapterClick(int type, View view, Object data) {
        switch (type) {
            case SuitMenuAdapter.CLICK_TYPE_INPUT_DONE:
                if (!(data instanceof MenuVO)) {
                    return;
                }
                MenuVO menuVO = (MenuVO) data;
                if (menuVO.getSuitSubInputNum() <= 0) {
                    menuVO.getSuitGroupVO().setCurrentNum(menuVO.getSuitGroupVO().getCurrentNum() - menuVO.getTmpNum());
                    if (TextUtils.isEmpty(menuVO.getSpecification())) {
                        menuVO.resetNoSpecMenuFromBelong();
                    }
                    mAdapterData.remove(menuVO);
                    List<MenuVO> list = mMemoryCart.get(menuVO.getMenuId());
                    if (list != null) {
                        list.remove(menuVO);
                    }
                } else {
                    menuVO.getSuitGroupVO().setCurrentNum(menuVO.getSuitGroupVO().getCurrentNum() - menuVO.getTmpNum() + menuVO.getSuitSubInputNum());
                    menuVO.setTmpNum(menuVO.getSuitSubInputNum());
                }
                getAdapter().notifyDataSetChanged();
                calculateInBgThread();
                break;
            case SuitMenuAdapter.CLICK_TYPE_ADD_CART:
                if (!(data instanceof MenuVO)) {
                    return;
                }
                processAddToCart((MenuVO) data, true);
                break;
            case SuitMenuAdapter.CLICK_TYPE_PLUS:
                if ((data instanceof MenuVO)) {
                    processAddToCart((MenuVO) data, false);
                }
                break;
            case SuitMenuAdapter.CLICK_TYPE_MINUS:
                if ((data instanceof MenuVO)) {
                    removeFromCart((MenuVO) data);
                }
                break;
        }
    }

    @Override
    public void onAdapterClickEdit(int type, View view, Object data) {
        switch (type) {
            case SuitMenuAdapter.CLICK_TYPE_EDIT_DIALOG:
                if (!(view instanceof FoodNumberTextView)) {
                    return;
                }
                if (!(data instanceof MenuVO)) {
                    return;
                }
                MenuVO dialogMenuVo = (MenuVO) data;
                showEditValueDialog(data,
                        dialogMenuVo.getAccountNum(),
                        dialogMenuVo.getTmpNum(),
                        dialogMenuVo.getMenuName(),
                        dialogMenuVo.getUnit(), CartHelper.DialogDateFrom.SUIT_DETAIL);

                break;
        }
    }

    /**
     * 计算某个菜总共点了多少份
     */
    private int getMenuNumCount(MenuVO menuVO, boolean exceptSelf) {
        List<MenuVO> selectedMenuVO = null;
        if (menuVO.getSelectedMenus() != null) {
            selectedMenuVO = menuVO.getSelectedMenus();
        } else if (menuVO.getBelongMenu() != null && menuVO.getBelongMenu().getSelectedMenus() != null) {
            selectedMenuVO = menuVO.getBelongMenu().getSelectedMenus();
        }
        if (selectedMenuVO == null || selectedMenuVO.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (MenuVO m : selectedMenuVO) {
            if (exceptSelf && menuVO == m) {
                continue;
            }
            count += m.getTmpNum();
        }
        return count;
    }

    public void combineSuitNote(SuitMenuVO suitMenuVO) {
        mSuitMenuVO.setIsWait(suitMenuVO.getIsWait());
        mSuitMenuVO.setMemo(suitMenuVO.getMemo());
        if (suitMenuVO.getHasChanged() == 1) {
            initButtonWhenChange();
        }

    }

    public void combineSuitChild(SuitChildVO suitChildVO) {
        if (suitChildVO == null) {
            return;
        }
        //第一步，遍历集合，找出匹配的menu
        MenuVO menuVO = null;
        int position;
        if (mAdapterData == null) {
            return;
        }

        //找到对应显示的MenuVO
        position = suitChildVO.getSuitSubMenuPosition();
        if (position != -1 && position < mAdapterData.size()) {
            if (mAdapterData.get(position) instanceof MenuVO) {
                menuVO = (MenuVO) mAdapterData.get(position);
            }
        } else {
            for (int i = 0; i < mAdapterData.size(); i++) {
                Object data = mAdapterData.get(i);
                if (data instanceof MenuVO) {
                    MenuVO showedMenu = (MenuVO) data;
                    if (showedMenu.getSuitGroupId().equals(suitChildVO.getGroupId())
                            && showedMenu.getMenuId().equals(suitChildVO.getId())
                            && StringUtils.checkSameString(suitChildVO.getSpecDetailId(), showedMenu.getSpecDetailId())) {
                        menuVO = showedMenu;
                        position = i;
                        break;
                    }
                }
            }
        }
        if (menuVO == null) {
            Log.e("SuitDetailFragment", "cannot find the menu");
            return;
        }
        suitChildVO.setSpecDetailName(menuVO.getSpecDetailName());

        if (menuVO.getSuitGroupVO().isRequired()) {
            if (menuVO.getSelectedMenuNoSpec() != null) {
                MenuVO m = menuVO.getSelectedMenuNoSpec();
                boolean hasMemo = suitChildVO.getMemo() != null && !TextUtils.isEmpty(suitChildVO.getMemo().trim());
                boolean hasLabel = suitChildVO.getLabels() != null && !suitChildVO.getLabels().isEmpty();
                if (hasMemo || hasLabel) {
                    m.setAccountNum(suitChildVO.getAccountNum());
                    m.setMemo(suitChildVO.getMemo());
                    m.setLabels(suitChildVO.getLabels());
                    Recipe recipe = suitChildVO.getFirstRecipe();
                    m.setSelectedMake(recipe);
                    m.setSpecification(SpecificationDataMapLayer.getMenuSpecification(suitChildVO.getSpecDetailName(),
                            recipe != null ? recipe.getName() : null, suitChildVO.getLabels(), suitChildVO.getMemo()));
                    m.setSelectedMenuNewLine(true);
                    m.resetNoSpecMenuFromBelong();
                    addToAdapterList(position + 1, m);
                    getAdapter().notifyDataSetChanged();
                }
            } else if (menuVO.getSelectedMenus() != null) {
                MenuVO m = menuVO.getSelectedMenus().get(0);
                m.setAccountNum(suitChildVO.getAccountNum());
                Recipe recipe = suitChildVO.getFirstRecipe();
                if (recipe != null) {
                    m.setExtraPrice(SpecificationDataMapLayer.getCombSubFoodAddPrice(m.getMenu(), recipe, m.getPerNum(), m.getAccountNum()));
                    m.setSelectedMake(recipe);
                }
                m.setLabels(suitChildVO.getLabels());
                m.setMemo(suitChildVO.getMemo());

                m.setSpecification(SpecificationDataMapLayer.getMenuSpecification(suitChildVO.getSpecDetailName(),
                        m.getSelectedMake() != null ? m.getSelectedMake().getName() : null, m.getLabels(), m.getMemo()));

                if (TextUtils.isEmpty(m.getSpecification())) {
                    m.setSelectedMenuNewLine(false);
                    menuVO.setSelectedMenuNoSpec(m);
                    mAdapterData.remove(m);
                    getAdapter().notifyDataSetChanged();
                } else {
                    int index = mAdapterData.indexOf(m);
                    if (index != -1) {
                        getAdapter().notifyItemChanged(index);
                    } else {
                        getAdapter().notifyDataSetChanged();
                    }
                }
                calculateInBgThread();
            }
            initButtonWhenChange();
            return;
        }

        //第二步，进行数量检查

        // 是否超过菜本身的数量限制
        if (!menuVO.isNoLimit()) {
            if (suitChildVO.getNum() + getMenuNumCount(menuVO, false) > menuVO.getLimitNum()) {
                ToastUtils.showShortToast(getContext(),
                        getString(R.string.module_menu_suit_sub_num_limit, menuVO.getMenuName(),
                                NumberUtils.trimPointIfZero(menuVO.getLimitNum())));
                return;
            }
        }
        //如果分组有数量限制
        if (!menuVO.getSuitGroupVO().isNoLimit()) {
            double hopeNum = menuVO.getSuitGroupVO().getCurrentNum() + suitChildVO.getNum();
            if (hopeNum > menuVO.getSuitGroupVO().getNum()) {
                ToastUtils.showShortToast(getContext(),
                        String.format(getString(R.string.module_menu_suit_detail_group_num_limit)
                                , menuVO.getSuitGroupName(), String.valueOf(hopeNum - menuVO.getSuitGroupVO().getNum())));
                return;
            }
        }
        //第三步，如果是用户点过这个菜，进行比较，是否相关属性相同，如果相同则数量+n，如果不同换行显示
        Recipe recipe = suitChildVO.getFirstRecipe();
        String spec = SpecificationDataMapLayer.getMenuSpecification(suitChildVO.getSpecDetailName(),
                recipe != null ? recipe.getName() : null, suitChildVO.getLabels(), suitChildVO.getMemo());
        MenuVO addMenuVO = createForSuit(menuVO.getMenu(), menuVO.getItem(), menuVO.getSuitGroupVO(),
                suitChildVO.getNum(), suitChildVO.getAccountNum(),
                suitChildVO.getSpecDetailId(), recipe, suitChildVO.getLabels(), suitChildVO.getMemo(),
                suitChildVO.getIsWait(), menuVO.getKindWhenUpdateCart(), suitChildVO.getDoubleUnitStatus(),
                spec,
                SpecificationDataMapLayer.getCombSubFoodAddPrice(menuVO.getMenu(), recipe, suitChildVO.getNum(), suitChildVO.getAccountNum()), null);

        List<MenuVO> orderedMenus = mMemoryCart.get(suitChildVO.getId());
        //购物车是否点过这个菜
        if (orderedMenus != null && !orderedMenus.isEmpty()) {
            for (MenuVO m : orderedMenus) {
                //购物车是否已经存在和这个属性一样的菜
                if (m.getSuitGroupId().equals(suitChildVO.getGroupId()) && StringUtils.checkSameString(m.getSpecification(), spec)) {
                    m.setTmpNum(m.getTmpNum() + suitChildVO.getNum());
                    m.getSuitGroupVO().setCurrentNum(m.getSuitGroupVO().getCurrentNum() + suitChildVO.getNum());
                    getAdapter().notifyDataSetChanged();
                    initButtonWhenChange();
                    return;
                }
            }
        }

        menuVO.getSuitGroupVO().setCurrentNum(menuVO.getSuitGroupVO().getCurrentNum() + suitChildVO.getNum());
        addMenuVO.setBelongMenu(menuVO);
        //第四步，还要把该菜加入到已点的集合中
        if (orderedMenus == null) {
            orderedMenus = new ArrayList<>(1);
            mMemoryCart.put(suitChildVO.getId(), orderedMenus);
        }
        orderedMenus.add(addMenuVO);


        if (!TextUtils.isEmpty(addMenuVO.getSpecification())) {
            addMenuVO.setSelectedMenuNewLine(true);
        } else {
            if (menuVO.getSelectedMenuNoSpec() == null) {
                menuVO.setSelectedMenuNoSpec(addMenuVO);
            }
        }
        //第五步，添加到adapterData显示
        if (menuVO.getSelectedMenuSize() == 0) {
            if (TextUtils.isEmpty(spec)) {
                menuVO.setSelectedMenuNoSpec(addMenuVO);
            } else {
                addToAdapterList(position + 1, addMenuVO);
            }
        } else if (menuVO.getSelectedMenuSize() == 1) {
            if (TextUtils.isEmpty(spec)) {
                if (TextUtils.isEmpty(menuVO.getSelectedMenus().get(0).getSpecification())) {
                    menuVO.setTmpNum(addMenuVO.getTmpNum());
                } else {
                    addMenuVO.setSelectedMenuNewLine(true);
                    addToAdapterList(position + menuVO.getSelectedMenuSize() + 1, addMenuVO);
                }
            } else {
                if (TextUtils.isEmpty(menuVO.getSelectedMenus().get(0).getSpecification())) {
                    //1，把以前没有规格的也要换行显示
                    //2，把本身换显示
                    MenuVO newLineMenu = menuVO.getSelectedMenus().get(0);//MenuVO.createForSuit(menuVO);
                    newLineMenu.setSelectedMenuNewLine(true);
                    addToAdapterList(position + 1, newLineMenu);
                    addToAdapterList(position + 2, addMenuVO);
                } else {
                    addToAdapterList(position + menuVO.getBelongMenuSelectedNum() + 1, addMenuVO);
                }
            }
        } else {
            addToAdapterList(position + menuVO.getBelongMenuSelectedNum() + 1, addMenuVO);
        }
        addMenuVO.addToBelongSelectMenus();
        getAdapter().notifyDataSetChanged();
        initButtonWhenChange();
        calculateInBgThread();
        //printDebug("combine", menuVO);
    }

    private void addToAdapterList(int position, Object obj) {
        if (mAdapterData != null) {
            if (position <= mAdapterData.size()) {
                mAdapterData.add(position, obj);
            }
        }
    }

    private void printDebug(String prefix, MenuVO menuVO) {
        Log.e("SuitDetail", "[" + prefix + "]group num:" + menuVO.getSuitGroupVO().getNum() + "，current num:" +
                menuVO.getSuitGroupVO().getCurrentNum() + "，belongNum:" + menuVO.getBelongMenuSelectedNum());
    }


    /**
     * 如果数量合法，则返回null，不合法返回需要提示的信息
     */
    private String checkNumBeforeSubmit() {
        StringBuilder sb = new StringBuilder();
        for (Object data : mAdapterData) {
            if (data instanceof SuitGroupVO) {
                SuitGroupVO groupVO = (SuitGroupVO) data;
                if (!groupVO.isNoLimit() && groupVO.getNum() > groupVO.getCurrentNum()) {
                    if (sb.length() == 0) {
                        sb.append(getString(R.string.module_menu_suit_group_menu_num_no_select_prefix));
                    }
                    sb.append(getString(R.string.module_menu_suit_group_menu_num_no_select,
                            NumberUtils.trimPointIfZero(groupVO.getNum() - groupVO.getCurrentNum()), groupVO.getGroupName()));
                    sb.append(CartHelper.COMMA_SEPARATOR);
                }
            }
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        return null;
    }

    private void calculateInBgThread() {
        Subscription s = RxUtils.fromCallable(
                new Callable<Double>() {
                    @Override
                    public Double call() {
                        return calculateSuitMenuPrice();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .subscribe(new Action1<Double>() {
                    @Override
                    public void call(Double price) {
                        setPriceValue(NumberUtils.getDecimalFee(price < 0 ? 0 : price));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
        addSubscription(s);
    }

    public SuitMenuVO getSuitMenuVO() {
        if (mSuitMenuVO != null) {
            mSuitMenuVO.setSuitMenuHitRules(mSuitMenuHitRules);
        }
        return mSuitMenuVO;
    }


    private double calculateSuitMenuPrice() {
        if (mSuitMenu == null) {
            return 0;
        }
        //套餐总价
        double count = mSuitMenu.getPrice();
        List<MenuVO> all = new ArrayList<>();
        for (Map.Entry<String, List<MenuVO>> entry : mMemoryCart.entrySet()) {
            if (entry.getValue() != null) {
                all.addAll(entry.getValue());
            }
        }

        //子菜的加价
        for (MenuVO m : all) {
            count += m.getExtraPrice();
        }

        //计算撞餐
        if (mSuitMenuHitRules != null && !mSuitMenuHitRules.isEmpty()) {
            List<MenuVO> atomMenus = scatterOneByOne(all);
            count = calculateRulePriceLocal(count, atomMenus);
        }
        return count * mEditSuitNum.getNumber();
    }

    /**
     * 本地计算撞餐
     */
    private double calculateRulePriceLocal(double count, List<MenuVO> atomMenus) {
        for (SuitMenuHitRule rule : mSuitMenuHitRules) {
            List<SuitMenuHitRule.SuitMenuChange> hitMenus = rule.getItems();
            boolean contains = containsInSelects(hitMenus, atomMenus);
            if (contains) {
                count += (rule.getFloatPrice() / 100d);
                removeHitMenuFromSelect(hitMenus, atomMenus);
                count = calculateRulePriceLocal(count, atomMenus);
            }
        }
        return count;
    }

    private List<MenuVO> scatterOneByOne(List<MenuVO> selects) {
        List<MenuVO> list = new ArrayList<>();
        for (MenuVO menuVo : selects) {
            //把必选菜排除一下
            if (menuVo.getSuitGroupVO().isRequired()) {
                continue;
            }
            if (menuVo.getTmpNum() == 1) {
                list.add(menuVo);
                continue;
            }
            for (int i = 0; i < menuVo.getTmpNum(); i++) {
                MenuVO m = MenuVO.createForDispart(menuVo);
                list.add(m);
            }
        }
        return list;
    }

    private void removeHitMenuFromSelect(List<SuitMenuHitRule.SuitMenuChange> hitMenus, List<MenuVO> selects) {
        for (SuitMenuHitRule.SuitMenuChange hitMenu : hitMenus) {
            for (MenuVO menuVo : selects) {
                if (hitMenu.getMenuId().equals(menuVo.getMenuId())) {
                    selects.remove(menuVo);
                    break;
                }
            }
        }
    }

    private boolean containsInSelects(List<SuitMenuHitRule.SuitMenuChange> hitMenus, List<MenuVO> selects) {
        for (SuitMenuHitRule.SuitMenuChange hitMenu : hitMenus) {
            if (!containInSelects(hitMenu, selects)) {
                return false;
            }
        }
        return true;
    }

    private boolean containInSelects(SuitMenuHitRule.SuitMenuChange hitMenu, List<MenuVO> selects) {
        for (MenuVO menuVo : selects) {
            if (hitMenu.getMenuId().equals(menuVo.getMenuId())) {
                return true;
            }
        }
        return false;
    }


    @OnClick({R2.id.text_confirm, R2.id.text_num_flag})
    void processClick(View view) {
        if (mSuitMenu == null || mSuitMenuSellOut) {
            return;
        }

        if (view.getId() == R.id.text_num_flag) {
            if (mType == TYPE_FROM_CART) {
                getDialogUtil().showDialog(R.string.material_dialog_title
                        , getString(R.string.module_menu_cartdetail_delete_food), true, new SingleButtonCallback() {
                            @Override
                            public void onClick(DialogUtilAction which) {
                                if (which == DialogUtilAction.POSITIVE) {
                                    submitSuitMenu(0);
                                }
                            }
                        });
            }
        } else if (view.getId() == R.id.text_confirm) {
            if (mCreateOrderParam == null || mSuitMenuVO == null) {
                return;
            }
            if (mMemoryCart == null || mMemoryCart.isEmpty()) {
                ToastUtils.showShortToast(getContext(), R.string.module_menu_suit_menu_select_none_tip);
                return;
            }

            if (mEditSuitNum.getNumber() == 0 && mType == TYPE_FROM_MENU_LIST) {
                ToastUtils.showShortToast(getContext(), R.string.module_menu_suit_num_check_zero);
                return;
            }

            String tip = checkNumBeforeSubmit();
            if (tip == null) {
                submitSuitMenu(mEditSuitNum.getNumber());
            } else {
                getDialogUtil().showDialog(R.string.module_menu_dialog_title_suit_sell_out, tip,
                        R.string.module_menu_suit_menu_select_menu_positive,
                        R.string.module_menu_suit_menu_select_menu_negative,
                        false, new SingleButtonCallback() {
                            @Override
                            public void onClick(DialogUtilAction which) {
                                switch (which) {
                                    case POSITIVE:
                                        submitSuitMenu(mEditSuitNum.getNumber());
                                        break;
                                }
                            }
                        });
            }
        }
    }

    private void submitSuitMenu(double num) {
        showLoading(false);
        mSuitMenuVO.setNum(num);
        List<MenuVO> menuVOs = null;
        if (num != 0) {
            Collection<List<MenuVO>> values = mMemoryCart.values();
            menuVOs = new ArrayList<>();
            for (List<MenuVO> list : values) {
                menuVOs.addAll(list);
            }
        }
        List<CartItem> items = CartItemConverter.getCartItemsBySuitMenu(mSuitMenuVO, menuVOs);
        mPresenter.addSuitToCart(mCreateOrderParam.getSeatCode(), mCreateOrderParam.getOrderId(),
                CartHelper.CartSource.COMBO_DETAIL, items);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveSuitChild(RouterBaseEvent.CommonEvent event) {

        if (event != null && event == RouterBaseEvent.CommonEvent.EVENT_SELECT_SUIT_CHILE_MENU) {
            if (event.getObject() instanceof SuitChildVO) {
                SuitChildVO suitChildVO = (SuitChildVO) event.getObject();
                ParamSuitSubMenu paramSuitSubMenu = suitChildVO.getParamSuitSubMenu();
                if (paramSuitSubMenu != null) {
                    suitChildVO.setMakeId(paramSuitSubMenu.getMakeId());
                    suitChildVO.setMemo(paramSuitSubMenu.getMemo());
                    suitChildVO.setAccountNum(paramSuitSubMenu.getAccountNum());
                    suitChildVO.setLabels(paramSuitSubMenu.getLabels());
                }
                combineSuitChild(suitChildVO);
            }
        }
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadListData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusHelper.unregister(this);
        if (compositeSub != null && !compositeSub.isUnsubscribed()) {
            compositeSub.unsubscribe();
        }
    }

    /**
     * 弹出数量输入框
     */
    private void showEditValueDialog(final Object data, double startNum, double number, String menuName, String unit,
                                     int from) {

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
                if (data instanceof SuitMenuVO) {
                    mEditSuitNum.setNumberText(number);
                    calculateInBgThread();
                    initButtonWhenChange();
                } else if (data instanceof MenuVO) {
                    ((MenuVO) data).setSuitSubInputNum(number);
                    onAdapterClick(SuitMenuAdapter.CLICK_TYPE_INPUT_DONE, view, data);
                }
            }
        });

    }

}
