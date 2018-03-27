package com.zmsoft.ccd.module.menu.cart.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.widget.FoodNumberTextView;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.CartDetailAdapter;
import com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.BaseMenuVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.KindAndTasteVo;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.Menu;
import com.zmsoft.ccd.module.menu.cart.model.cartdetail.NormalMenuVo;
import com.zmsoft.ccd.module.menu.cart.presenter.cartdetail.CartDetailContract;
import com.zmsoft.ccd.module.menu.events.BaseEvents;
import com.zmsoft.ccd.module.menu.events.model.ModifyCartParam;
import com.zmsoft.ccd.module.menu.helper.CardDetailDataMapLayer;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.menu.bean.ParamSuitSubMenu;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitChildVO;
import com.zmsoft.ccd.module.menu.menu.bean.vo.SuitGroupVO;
import com.zmsoft.ccd.module.menu.menu.ui.EditFoodNumberDialog;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * 购物车菜品详情
 *
 * @author DangGui
 * @create 2017/4/18.
 */

public class CartDetailFragment extends BaseListFragment implements CartDetailContract.View {
    @BindView(R2.id.layout_bottom)
    LinearLayout mLayoutBottom;
    @BindView(R2.id.text_num)
    TextView mTextNum;
    @BindView(R2.id.image_delete)
    ImageView mImageDelete;
    @BindView(R2.id.edit_food_number_view)
    FoodNumberTextView mEditFoodNumberView;
    @BindView(R2.id.button_sure)
    Button mButtonSure;
    @BindView(R2.id.text_sure)
    TextView mTextSure;

    public static final String EXTRA_MENU_ID = "extra_menu_id";
    public static final String EXTRA_DETAIL_TYPE = "extra_detail_type";
    public static final String EXTRA_DETAIL_ITEMVO = "extra_detail_itemvo";
    public static final String EXTRA_SEAT_CODE = "extra_seat_code";
    public static final String EXTRA_ORDER_ID = "extra_order_id";
    public static final String EXTRA_DETAIL_BASEMENUVO = "extra_detail_basemenuvo";
    public static final String EXTRA_SPEC_ID = "extra_spec_id";
    public static final String EXTRA_SUITGROUPVO = "extra_SuitGroupVO";
    public static final String EXTRA_FOOD_NUM = "extra_foodnum";
    public static final String EXTRA_FOOD_LIMITNUM = "extra_limitnum";
    private CartDetailContract.Presenter mPresenter;
    /**
     * 菜肴ID
     */
    private String mMenuId;
    /**
     * 页面类型（商品详情、修改、套餐子菜详情等）
     */
    private int mDetailType;
    /**
     * 购物车数据
     */
    private ItemVo mItemVo;
    private BaseMenuVo mBaseMenuVo;
    /**
     * 购物车详情数据
     */
    private NormalMenuVo mNormalMenuVo;
    /**
     * 备注（目前只有普通菜详情、修改页需要该字段，套餐详情和修改不需要）
     */
    private List<KindAndTasteVo> mKindTasteList;
    /**
     * 详情页recyclerView数据源
     */
    private List<CartDetailRecyclerItem> mCartRecyclerItems;

    /**
     * 默认点菜的数量
     */
    private static final int DEFAULT_FOOD_NUM = 1;
    /**
     * 点菜数量，详情页默认是1
     */
    private double foodNum = DEFAULT_FOOD_NUM;

    /**
     * 桌位
     */
    private String mSeatCode;
    /**
     * 订单Id
     */
    private String mOrderId;

    /**
     * 套餐子菜详情 掌柜上设置的默认规格ID
     */
    private String mSpecId;

    /**
     * 套餐子菜详情，菜所在分组数量限制所需属性
     */
    private SuitGroupVO mSuitGroupVO;
    /**
     * 套餐必选子菜详情
     */
    private ParamSuitSubMenu mParamSuitSubMenu;

    /**
     * 套餐必选子菜详情 已点份数
     */
    private int mComboMustFoodNum;

    /**
     * 套餐子菜，菜本身数量限制
     */
    private int mLimitNum;

    /**
     * 菜名称
     */
    private String mMenuName;

    private int suitSubMenuPosition;

    public static CartDetailFragment newInstance(String menuId, int detailType, ItemVo itemVo
            , String seatCode, String orderId, BaseMenuVo baseMenuVo, String specId, SuitGroupVO suitGroupVO
            , int foodNum, int limitNum, ParamSuitSubMenu paramSuitSubMenu, String menuName, int suitSubMenuPosition) {
        Bundle args = new Bundle();
        args.putString(EXTRA_MENU_ID, menuId);
        args.putInt(EXTRA_DETAIL_TYPE, detailType);
        args.putSerializable(EXTRA_DETAIL_ITEMVO, itemVo);
        args.putSerializable(EXTRA_DETAIL_BASEMENUVO, baseMenuVo);
        args.putString(EXTRA_SEAT_CODE, seatCode);
        args.putString(EXTRA_ORDER_ID, orderId);
        args.putString(EXTRA_SPEC_ID, specId);
        args.putParcelable(EXTRA_SUITGROUPVO, suitGroupVO);
        args.putSerializable(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU, paramSuitSubMenu);
        args.putInt(EXTRA_FOOD_NUM, foodNum);
        args.putInt(EXTRA_FOOD_LIMITNUM, limitNum);
        args.putString(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU_NAME, menuName);
        args.putInt(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU_POSITION, suitSubMenuPosition);
        CartDetailFragment fragment = new CartDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_menu_cart_detail_fragment_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        disableAutoRefresh();
        disableRefresh();
        Bundle bundle = getArguments();
        if (null != bundle) {
            mMenuId = bundle.getString(EXTRA_MENU_ID);
            mDetailType = bundle.getInt(EXTRA_DETAIL_TYPE);
            Serializable serializable = bundle.getSerializable(EXTRA_DETAIL_ITEMVO);
            if (null != serializable) {
                mItemVo = (ItemVo) serializable;
            }
            Serializable baseMenuVoSerializable = bundle.getSerializable(EXTRA_DETAIL_BASEMENUVO);
            if (null != baseMenuVoSerializable) {
                mBaseMenuVo = (BaseMenuVo) baseMenuVoSerializable;
            }
            mSeatCode = bundle.getString(EXTRA_SEAT_CODE);
            mOrderId = bundle.getString(EXTRA_ORDER_ID);
            mSpecId = bundle.getString(EXTRA_SPEC_ID);
            mComboMustFoodNum = bundle.getInt(EXTRA_FOOD_NUM);
            mLimitNum = bundle.getInt(EXTRA_FOOD_LIMITNUM);
            mSuitGroupVO = bundle.getParcelable(EXTRA_SUITGROUPVO);
            mMenuName = bundle.getString(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU_NAME);
            Serializable paramSuitSerializable = bundle.getSerializable(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU);
            if (null != paramSuitSerializable) {
                mParamSuitSubMenu = (ParamSuitSubMenu) paramSuitSerializable;
            }
            suitSubMenuPosition = bundle.getInt(RouterPathConstant.CartDetail.PARAM_SUIT_SUB_MENU_POSITION, -1);

        }
        if (null != mItemVo) {
            if (mItemVo.getNum() > 0) {
                foodNum = mItemVo.getNum();
            }
        }
        if (mDetailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
            foodNum = mComboMustFoodNum;
        }
        initViewByDetailType();
        initInfoView();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mEditFoodNumberView.setOnEditViewClick(new FoodNumberTextView.OnEditViewClick() {
            @Override
            public void onClick(int which, double numberValue) {
                switch (which) {
                    case FoodNumberTextView.CLICK_LEFT:
                        subFoodNum(numberValue);
                        break;
                    case FoodNumberTextView.CLICK_RIGHT:
                        plusFoodNum(numberValue);
                        break;
                }
            }
        });
        mEditFoodNumberView.setOnClickEdit(new FoodNumberTextView.OnClickEdit() {
            @Override
            public void onClickEdit(double numberValue) {
                {
                    if (mNormalMenuVo != null && mNormalMenuVo.getMenu() != null) {
                        double startNum;
                        if(mDetailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL) {
                            startNum = 1;
                        }else {
                           startNum = mNormalMenuVo.getMenu().getStartNum();
                        }
                        showEditValueDialog(mNormalMenuVo,
                                startNum,
                                numberValue,
                                mNormalMenuVo.getMenu().getName(),
                                mNormalMenuVo.getMenu().getBuyAccount(),
                                CartHelper.DialogDateFrom.OTHER_VIEW);

                    } else if (mItemVo != null) {
                        showEditValueDialog(mItemVo,
                                mItemVo.getStartNum(),
                                numberValue,
                                mItemVo.getName(),
                                mItemVo.getUnit(),
                                CartHelper.DialogDateFrom.OTHER_VIEW);
                    }
                }
            }
        });
        RxView.clicks(mButtonSure).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (mDetailType == CartHelper.FoodDetailType.FOOD_DETAIL
                                || mDetailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                                || mDetailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {

                            //商品详情,如果该商品设置了起点份数，只要输入的数量小于起点数量，则弹出提示
                            if (mDetailType != CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                                    && mDetailType != CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL
                                    && getStartNum() > 1) {
                                if (mEditFoodNumberView.getNumber() < getStartNum()) {
                                    ToastUtils.showShortToast(getContext(),
                                            getString(R.string.module_menu_check_start_num, getMenuName(), getStartNum(), getUnit()));
                                    return;
                                }
                            }
                            //如果所在套餐分组设置为 数量限制，点菜数量超过了所在套餐分组的数量限制，给予提示
                            if (mDetailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                                    || mDetailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {

                                //数量必须大于0
                                if (mEditFoodNumberView.getNumber() <= CartHelper.FoodNum.MIN_VALUE) {
                                    showToast(R.string.module_menu_cart_detail_num_too_small);
                                    return;
                                }
                                //如果是套餐子菜本身有数量限制
                                if (mLimitNum > 0) {
                                    if (mEditFoodNumberView.getNumber() > mLimitNum) {
                                        ToastUtils.showShortToast(getContext(),
                                                String.format(getString(R.string.module_menu_suit_sub_num_limit)
                                                        , getActivity().getTitle(), mLimitNum + ""));
                                        return;
                                    }
                                }
                                if (null != mSuitGroupVO && !mSuitGroupVO.isNoLimit()) {
                                    if (mEditFoodNumberView.getNumber() + mSuitGroupVO.getCurrentNum() > mSuitGroupVO.getNum()) {
                                        double subNum = mEditFoodNumberView.getNumber() + mSuitGroupVO.getCurrentNum() - mSuitGroupVO.getNum();
                                        String subNumStr;
                                        if (NumberUtils.doubleIsInteger(subNum)) {
                                            subNumStr = (int) subNum + "";
                                        } else {
                                            subNumStr = FeeHelper.getDecimalFee(subNum);
                                        }
                                        ToastUtils.showShortToast(getContext(),
                                                String.format(getString(R.string.module_menu_suit_detail_group_num_limit)
                                                        , mSuitGroupVO.getGroupName(), subNumStr));
                                        return;
                                    }
                                }
                                //将套餐子菜修改后的信息组装成menu对象，返回给套餐列表
                                SuitChildVO suitChildVO = mPresenter.getComboMenu(mMenuId, mNormalMenuVo, mCartRecyclerItems, foodNum, mKindTasteList);
                                if (null != suitChildVO) {
//                                    if (null != mParamSuitSubMenu) {
//                                        ParamSuitSubMenu paramSuitSubMenu = suitChildVO.getParamSuitSubMenu();
//                                        if (null != paramSuitSubMenu) {
//                                            paramSuitSubMenu.setAccountNum(mParamSuitSubMenu.getAccountNum());
//                                            paramSuitSubMenu.setMemo(mParamSuitSubMenu.getMemo());
//                                            paramSuitSubMenu.setLabels(mParamSuitSubMenu.getLabels());
//                                            paramSuitSubMenu.setMakeId(mParamSuitSubMenu.getMakeId());
//                                        }
//                                    }
                                    if (mSuitGroupVO != null) {
                                        suitChildVO.setGroupId(mSuitGroupVO.getGroupId());
                                    }
                                    suitChildVO.setSuitSubMenuPosition(suitSubMenuPosition);
                                    RouterBaseEvent.CommonEvent event = RouterBaseEvent.CommonEvent.EVENT_SELECT_SUIT_CHILE_MENU;
                                    event.setObject(suitChildVO);
                                    EventBusHelper.post(event);
                                    getActivity().finish();
                                }
                                return;
                            }
                            if (mEditFoodNumberView.getNumber() <= CartHelper.FoodNum.MIN_VALUE) {
                                showToast(R.string.module_menu_cart_detail_num_too_small);
                            } else {
                                mPresenter.modifyCart(mSeatCode, mOrderId, mMenuId, mCartRecyclerItems
                                        , mItemVo, mNormalMenuVo, mKindTasteList, foodNum, mDetailType);
                            }
                        } else {
                            //修改的情况下，数量可以为0，如果不为0且小于起点数量，则弹出提示
                            if (getStartNum() > 1) {
                                if (mDetailType != CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                                        && mDetailType != CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL
                                        && mEditFoodNumberView.getNumber() != 0 && mEditFoodNumberView.getNumber() < getStartNum()) {
                                    ToastUtils.showShortToast(getContext(),
                                            getString(R.string.module_menu_check_start_num, getMenuName(), getStartNum(), getUnit()));
                                    return;
                                }
                            }
                            mPresenter.modifyCart(mSeatCode, mOrderId, mMenuId, mCartRecyclerItems
                                    , mItemVo, mNormalMenuVo, mKindTasteList, foodNum, mDetailType);
                        }
                    }
                });
        RxView.clicks(mImageDelete).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        handleDelete();
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        showLoadingView();
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }

    @Override
    protected void registerEventBus() {
        super.registerEventBus();
        EventBusHelper.register(this);
    }

    @Override
    protected void unRegisterEventBus() {
        super.unRegisterEventBus();
        EventBusHelper.unregister(this);
    }

    @Subscribe
    public void onReceiveEvent(BaseEvents.CommonEvent event) {
        //商品修改页，属性被修改后，需要显示确定按钮
        if (event == BaseEvents.CommonEvent.CART_DETAIL_MODIFY) {
            handleModifyEvent();
        } else if (event == BaseEvents.CommonEvent.CART_DETAIL_SWITCH_PRESENT_FOOD) {
            mPresenter.checkPermission(Permission.PresentFood.SYSTEM_TYPE, Permission.PresentFood.ACTION_CODE
                    , mCartRecyclerItems);
        }
    }

    private int getStartNum() {
        if (mNormalMenuVo != null && mNormalMenuVo.getMenu() != null) {
            return mNormalMenuVo.getMenu().getStartNum();
        } else if (mItemVo != null) {
            return mItemVo.getStartNum();
        }
        return 0;
    }

    private String getUnit() {
        if (mNormalMenuVo != null && mNormalMenuVo.getMenu() != null) {
            return mNormalMenuVo.getMenu().getBuyAccount();
        } else if (mItemVo != null) {
            return mItemVo.getUnit();
        }
        return "";
    }

    private String getMenuName() {
        if (mNormalMenuVo != null && mNormalMenuVo.getMenu() != null) {
            return mNormalMenuVo.getMenu().getName();
        } else if (mItemVo != null) {
            return mItemVo.getName();
        }
        return "";
    }

    /**
     * 数量 减
     *
     * @param numberValue
     */
    private void subFoodNum(double numberValue) {
        double num = numberValue - 1;

        if (mDetailType != CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                && mDetailType != CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL
                && getStartNum() > 1) {
            //如果有起点份数，且当前的份数小于等于起点份数相等，直接=0
            if (numberValue <= getStartNum()) {
                if (mItemVo != null) {//修改的时候，允许设置为0,
                    num = 0;
                } else {
                    ToastUtils.showShortToast(getContext(),
                            getString(R.string.module_menu_check_start_num, getMenuName(), getStartNum(), getUnit()));
                    num = getStartNum();
                }
            } else {
                //检查起点分数
                if (!CartHelper.checkStartNum(getStartNum(), num)) {
                    ToastUtils.showShortToast(getContext(),
                            getString(R.string.module_menu_check_start_num, getMenuName(), getStartNum(), getUnit()));
                    return;
                }
            }
        }

        //普通菜、套餐子菜详情加入购物车，最小数量 >0
        if (mDetailType == CartHelper.FoodDetailType.FOOD_DETAIL
                || mDetailType == CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                || mDetailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
            if (num <= CartHelper.FoodNum.MIN_VALUE) {
                num = numberValue;
            }
        } else {
            if (num < CartHelper.FoodNum.MIN_VALUE) {
                num = CartHelper.FoodNum.MIN_VALUE;
            }
        }
        mEditFoodNumberView.setNumberText(num);
        if (null != mItemVo) {
            mItemVo.setNum(num);
        }
        foodNum = num;
        handleModifyEvent();
    }

    /**
     * 数量 加
     *
     * @param numberValue
     */
    private void plusFoodNum(double numberValue) {
        double addNum = numberValue + 1;

        //如果有起点份数，当前数量为0，点击加号，则直接加到起点数量
        if (mDetailType != CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                && mDetailType != CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL
                && getStartNum() > 1) {
            if (numberValue == 0) {
                addNum = getStartNum();
            }
        }

        if (addNum > CartHelper.FoodNum.MAX_VALUE) {
            addNum = CartHelper.FoodNum.MAX_VALUE;
        }
        mEditFoodNumberView.setNumberText(addNum);
        foodNum = addNum;
        handleModifyEvent();
    }

    /**
     * 处理删除商品
     */
    private void handleDelete() {
        getDialogUtil().showDialog(R.string.material_dialog_title
                , getString(R.string.module_menu_cartdetail_delete_food), true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            foodNum = 0;
                            if (null != mItemVo) {
                                mItemVo.setNum(foodNum);
                            }
                            mPresenter.modifyCart(mSeatCode, mOrderId, mMenuId, mCartRecyclerItems
                                    , mItemVo, mNormalMenuVo, mKindTasteList, foodNum, mDetailType);
                        }
                    }
                });
    }

    private void handleModifyEvent() {
        if (null == mButtonSure || null == mTextSure) {
            return;
        }
        if (mDetailType == CartHelper.FoodDetailType.FOOD_MODIFY) {
            mButtonSure.setVisibility(View.VISIBLE);
            mTextSure.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void loadListData() {
        if (null != mBaseMenuVo) {
            showContentView();
            showCartFoodDetail(mBaseMenuVo);
        } else {
            mPresenter.queryCartFoodDetail(mMenuId);
        }
    }

    @Override
    protected int getAccentColor() {
        return R.color.accentColor;
    }

    @Override
    protected BaseListAdapter createAdapter() {
        return new CartDetailAdapter(getActivity(), null);
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(CartDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showCartFoodDetail(final BaseMenuVo baseMenuVo) {
        if (baseMenuVo != null) {
            Subscription mDataMapSubscription = RxUtils.fromCallable(new Callable<List<CartDetailRecyclerItem>>() {

                @Override
                public List<CartDetailRecyclerItem> call() {
                    mNormalMenuVo = CardDetailDataMapLayer.getNormalMenuVo(baseMenuVo);
                    mKindTasteList = baseMenuVo.getKindTasteList();
                    initFoodNumView();
                    if (mDetailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                        return CardDetailDataMapLayer.getComboDetailList(getActivity(), baseMenuVo
                                , mParamSuitSubMenu, mDetailType, mSpecId);
                    } else {
                        return CardDetailDataMapLayer.getCartDetailList(getActivity(), baseMenuVo
                                , mItemVo, mDetailType, mSpecId);
                    }
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .onTerminateDetach() //在异步调用的情况下，解除上游生产者与下游订阅者之间的引用,所以onTerminateDetach操作符要和subscription.unsubscribe() 结合使用
                    .subscribe(new Action1<List<CartDetailRecyclerItem>>() {
                        @Override
                        public void call(List<CartDetailRecyclerItem> cartRecyclerItems) {
                            mCartRecyclerItems = cartRecyclerItems;
                            renderListData(cartRecyclerItems);
                            getRecyclerView().setVisibility(View.VISIBLE);
                            mLayoutBottom.setVisibility(View.VISIBLE);
                            if (null != mNormalMenuVo && null != mNormalMenuVo.getMenu()) {
                                if (isHostActive() && !TextUtils.isEmpty(mNormalMenuVo.getMenu().getName())) {
                                    getActivity().setTitle(mNormalMenuVo.getMenu().getName());
                                }

                                //说明查看商品详情，则默认显示商品的起点分数
                                if (mDetailType != CartHelper.FoodDetailType.COMBO_CHILD_DETAIL
                                        && mDetailType != CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL
                                        && mItemVo == null && mNormalMenuVo.getMenu().getStartNum() > 1) {
                                    mEditFoodNumberView.setNumberText(mNormalMenuVo.getMenu().getStartNum());
                                    foodNum = mNormalMenuVo.getMenu().getStartNum();
                                }
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });
            addRxSubscription(mDataMapSubscription);
        }
    }

    @Override
    public void showCart(DinningTableVo dinningTableVo) {
        if (null != dinningTableVo && isHostActive()) {
            //购物车修改，通知菜单列表
            RouterBaseEvent.CommonEvent modifyCartEvent = RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_CART;
            ModifyCartParam modifyCartParam = new ModifyCartParam(dinningTableVo, null);
            modifyCartEvent.setObject(modifyCartParam);
            EventBusHelper.post(modifyCartEvent);

            Intent intent = new Intent();
            intent.putExtra(CartHelper.CartActivityRequestCode.ACTIVITY_RESULT_EXTRA_DINVO, dinningTableVo);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }

    @Override
    public void loadDataError(String errorMessage) {
        toastMsg(errorMessage);
    }

    @Override
    public void toastCartMsg(String message) {
        toastMsg(message);
    }

    @Override
    public void backPresentFoodPermission() {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadListData();
    }

    private void initViewByDetailType() {
        switch (mDetailType) {
            case CartHelper.FoodDetailType.FOOD_DETAIL:
            case CartHelper.FoodDetailType.COMBO_CHILD_DETAIL:
                mTextNum.setVisibility(View.VISIBLE);
                mImageDelete.setVisibility(View.INVISIBLE);
                mTextNum.setText(R.string.module_menu_cartdetail_num);
                mEditFoodNumberView.setVisibility(View.VISIBLE);
                mButtonSure.setVisibility(View.VISIBLE);
                mTextSure.setVisibility(View.INVISIBLE);
                if (mDetailType == CartHelper.FoodDetailType.FOOD_DETAIL) {
                    mButtonSure.setText(R.string.module_menu_cartdetail_join_cart);
                } else {
                    mButtonSure.setText(R.string.module_menu_cartdetail_join_combo);
                }
                break;
            case CartHelper.FoodDetailType.FOOD_MODIFY:
                mTextNum.setVisibility(View.INVISIBLE);
                mImageDelete.setVisibility(View.VISIBLE);
                mEditFoodNumberView.setVisibility(View.VISIBLE);
                mButtonSure.setVisibility(View.INVISIBLE);
                mTextSure.setVisibility(View.VISIBLE);
                String num = "0";
                if (null != mItemVo) {
                    if (foodNum > 0) {
                        if (NumberUtils.doubleIsInteger(foodNum)) {
                            num = (int) foodNum + "";
                        } else {
                            num = FeeHelper.getDecimalFee(foodNum);
                        }
                    }
                }
                mTextSure.setText(String.format(context.getResources().getString(R.string.module_menu_cartdetail_ordered_num)
                        , num, context.getResources().getString(R.string.module_menu_food_unit)));
                break;
            case CartHelper.FoodDetailType.MUST_SELECT_MODIFY:
            case CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL:
                mTextNum.setVisibility(View.VISIBLE);
                mImageDelete.setVisibility(View.INVISIBLE);
                String mustNum = "0";
                if (null != mItemVo) {
                    if (foodNum > 0) {
                        mustNum = FeeHelper.getDecimalFee(foodNum);
                    }
                } else {
                    if (mDetailType == CartHelper.FoodDetailType.COMBO_MUST_SELECT_CHILD_DETAIL) {
                        mustNum = mComboMustFoodNum + "";
                    }
                }
                mTextNum.setText((String.format(context.getResources().getString(R.string.module_menu_cartdetail_ordered_num)
                        , mustNum, context.getResources().getString(R.string.module_menu_food_unit))));
                mEditFoodNumberView.setVisibility(View.INVISIBLE);
                mButtonSure.setVisibility(View.VISIBLE);
                mTextSure.setVisibility(View.INVISIBLE);
                mButtonSure.setText(R.string.module_menu_confirm);
                break;
            default:
                break;
        }
    }

    private void initInfoView() {
        if (null != mItemVo) {
            if (isHostActive() && !TextUtils.isEmpty(mItemVo.getName())) {
                getActivity().setTitle(mItemVo.getName());
            }
        } else if (isHostActive() && !TextUtils.isEmpty(mMenuName)) {
            getActivity().setTitle(mMenuName);
        }
        mEditFoodNumberView.setNumberText(foodNum);
    }

    private void initFoodNumView() {
        if (null != mNormalMenuVo) {
            Menu menu = mNormalMenuVo.getMenu();
            if (null != menu) {
                String num = "0";
                if (null != mItemVo) {
                    if (foodNum > 0) {
                        if (NumberUtils.doubleIsInteger(foodNum)) {
                            num = (int) foodNum + "";
                        } else {
                            num = FeeHelper.getDecimalFee(foodNum);
                        }
                    }
                }
                if (null != mTextSure) {
                    mTextSure.setText(String.format(context.getResources().getString(R.string.module_menu_cartdetail_ordered_num)
                            , num, menu.getBuyAccount()));
                }
            }
        }
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
                if ((data instanceof NormalMenuVo) || (data instanceof ItemVo)) {
                    mEditFoodNumberView.setNumberText(number);
                    foodNum = number;
                    handleModifyEvent();
                }
            }
        });

    }
}
