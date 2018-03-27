package com.zmsoft.ccd.module.takeout.order.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.activity.CcdWebViewAcitivity;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.helper.LocationHelper;
import com.zmsoft.ccd.lib.bean.view.JagVO;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.R2;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.DeliveryHolderVO;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.OrderDescHolderVO;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.OrderFoodHolderVO;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.OrderGroupHolderVO;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.OrderInfoHolderVO;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.OrderMainHolderVO;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.OrderPayInfoHolderVO;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.OrderSubFoodHolderVO;
import com.zmsoft.ccd.module.takeout.order.utils.TakeoutUtils;
import com.zmsoft.ccd.takeout.bean.Takeout;
import com.zmsoft.ccd.takeout.bean.TakeoutConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/16.
 */

public class TakeoutListAdapter extends BaseListAdapter {

    public static final int CLICK_TYPE_PRINT = 1;
    public static final int CLICK_TYPE_CANCEL_ORDER = 2;
    public static final int CLICK_TYPE_CHANGE_STATUS = 3;
    public static final int CLICK_TYPE_SHOW_PHONE_TIP = 4;
    public static final int CLICK_TYPE_SHOW_DELIVERY_INFO = 5;

    private static final int TYPE_GROUP = 0;
    private static final int TYPE_MAIN = 1;
    private static final int TYPE_DELIVERY = 2;
    private static final int TYPE_DESC = 3;
    private static final int TYPE_FOOD = 4;
    private static final int TYPE_PAY_INFO = 5;
    private static final int TYPE_ORDER_INFO = 6;
    private static final int TYPE_SUIT_SUB_MENU = 7;//套餐子菜
    private static final int TYPE_JAG = 10;

    private AdapterClick mAdapterClick;

    public TakeoutListAdapter(Context context, FooterClick footerClick, @LayoutRes int emptyView, AdapterClick adapterClick) {
        super(context, footerClick, emptyView);
        mAdapterClick = adapterClick;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_GROUP:
                return new OrderGroupHolder(getContext(), inflateLayout(R.layout.module_takeout_item_time_group, parent));
            case TYPE_MAIN:
                return new OrderMainHolder(getContext(), inflateLayout(R.layout.module_takeout_item_takeout_main, parent), this);
            case TYPE_DELIVERY:
                return new OrderDeliveryHolder(getContext(), inflateLayout(R.layout.module_takeout_item_takeout_delivery, parent), this);
            case TYPE_DESC:
                return new OrderDescHolder(getContext(), inflateLayout(R.layout.module_takeout_item_takeout_desc, parent));
            case TYPE_FOOD:
                return new OrderFoodHolder(getContext(), inflateLayout(R.layout.module_takeout_item_takeout_food, parent));
            case TYPE_PAY_INFO:
                return new OrderPayInfoHolder(getContext(), inflateLayout(R.layout.module_takeout_item_takeout_pay_info, parent));
            case TYPE_ORDER_INFO:
                return new OrderInfoHolder(getContext(), inflateLayout(R.layout.module_takeout_item_takeout_order_info, parent));
            case TYPE_SUIT_SUB_MENU:
                return new OrderSubFoodHolder(getContext(), inflateLayout(R.layout.module_takeout_item_takeout_sub_food, parent));
            case TYPE_JAG:
                return new OrderJagHolder(getContext(), inflateLayout(R.layout.item_divider_jag, parent));
        }
        return getUnKnowViewHolder(parent);
    }

    @Override
    public int getMyItemViewType(int position) {
        Object obj = getModel(position);
        if (obj instanceof OrderGroupHolderVO) {
            return TYPE_GROUP;
        } else if (obj instanceof JagVO) {
            return TYPE_JAG;
        } else if (obj instanceof OrderMainHolderVO) {
            return TYPE_MAIN;
        } else if (obj instanceof DeliveryHolderVO) {
            return TYPE_DELIVERY;
        } else if (obj instanceof OrderDescHolderVO) {
            return TYPE_DESC;
        } else if (obj instanceof OrderFoodHolderVO) {
            return TYPE_FOOD;
        } else if (obj instanceof OrderPayInfoHolderVO) {
            return TYPE_PAY_INFO;
        } else if (obj instanceof OrderInfoHolderVO) {
            return TYPE_ORDER_INFO;
        } else if (obj instanceof OrderSubFoodHolderVO) {
            return TYPE_SUIT_SUB_MENU;
        }
        return -1;
    }

    private static class OrderGroupHolder extends BaseHolder {
        private TextView mTextGroupNUm;

        OrderGroupHolder(Context context, View itemView) {
            super(context, itemView);
            mTextGroupNUm = (TextView) itemView.findViewById(R.id.text_time_group);
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (!(bean instanceof OrderGroupHolderVO)) {
                return;
            }
            OrderGroupHolderVO groupHolderVO = (OrderGroupHolderVO) bean;
            mTextGroupNUm.setText(groupHolderVO.getGroupNum());
        }
    }

    private static class OrderJagHolder extends BaseHolder {
        View viewJag;
        int distance;


        OrderJagHolder(Context context, View itemView) {
            super(context, itemView);
            viewJag = itemView.findViewById(R.id.view_jag);
            distance = context.getResources().getDimensionPixelSize(R.dimen.module_takeout_list_item_distance);
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (bean instanceof JagVO) {
                JagVO jagVO = (JagVO) bean;
                if (jagVO.isUp()) {
                    viewJag.setBackgroundResource(R.drawable.shape_item_up_jag);
                    RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) viewJag.getLayoutParams();
                    lp.topMargin = distance;
                } else {
                    viewJag.setBackgroundResource(R.drawable.shape_item_bottom_jag);
                    RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) viewJag.getLayoutParams();
                    lp.topMargin = 0;
                }
            }
        }
    }

    static class OrderMainHolder extends BaseHolder {

        @BindView(R2.id.text_takeout_order_original)
        TextView mTextTakeoutOrderOriginal;
        @BindView(R2.id.text_takeout_order_delivery_way)
        TextView mTextTakeoutOrderDeliveryWay;
        @BindView(R2.id.text_takeout_order_appointment_flag)
        TextView mTextTakeoutOrderAppointmentFlag;
        @BindView(R2.id.text_takeout_order_status)
        TextView mTextTakeoutOrderStatus;
        @BindView(R2.id.text_takeout_order_take_time)
        TextView mTextTakeoutOrderTakeTime;
        @BindView(R2.id.text_takeout_order_person_phone)
        TextView mTextTakeoutOrderPersonInfo;
        @BindView(R2.id.text_takeout_order_address)
        TextView mTextTakeoutOrderAddress;
        @BindView(R2.id.text_takeout_order_distance)
        TextView mTextTakeoutOrderDistance;
        @BindView(R2.id.text_takeout_order_detail_toggle)
        TextView mTextTakeoutOrderDetailToggle;
        @BindView(R2.id.text_takeout_order_next)
        TextView mTextTakeoutOrderNext;
        @BindView(R2.id.text_takeout_order_repeal)
        TextView mTextTakeoutOrderRepeal;
        @BindView(R2.id.text_takeout_order_print)
        TextView mTextTakeoutOrderPrint;
        @BindView(R2.id.text_takeout_order_no)
        TextView mTextTakeoutOrderNo;
        @BindView(R2.id.rl_distance)
        View mRlDistance;


        private TakeoutListAdapter mAdapter;


        OrderMainHolder(Context context, View itemView, TakeoutListAdapter adapter) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
            mAdapter = adapter;
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (!(bean instanceof OrderMainHolderVO)) {
                return;
            }
            final OrderMainHolderVO mainHolderVO = (OrderMainHolderVO) bean;
            mTextTakeoutOrderOriginal.setText(mainHolderVO.getOrderOriginal());
            mTextTakeoutOrderOriginal.setCompoundDrawablesWithIntrinsicBounds(0, mainHolderVO.getOrderOriginalImage(), 0, 0);
            mTextTakeoutOrderDeliveryWay.setText(mainHolderVO.getOrderDeliveryWay());
            mTextTakeoutOrderStatus.setText(mainHolderVO.getOrderStatus());
            mTextTakeoutOrderStatus.setTextColor(getColor(TakeoutUtils.getStatusNameColor(mainHolderVO.getTakeout())));
            mTextTakeoutOrderTakeTime.setText(mainHolderVO.getOrderTakeTime());

            if (!TextUtils.isEmpty(mainHolderVO.getOrderDistance())) {
                mTextTakeoutOrderDistance.setVisibility(View.VISIBLE);
                mTextTakeoutOrderDistance.setText(mainHolderVO.getOrderDistance());
            } else {
                mTextTakeoutOrderDistance.setVisibility(View.GONE);
            }

            if (mainHolderVO.getTakeout().getSendType() != TakeoutConstants.SendType.SELF_TAKE) {
                mRlDistance.setVisibility(View.VISIBLE);
                mTextTakeoutOrderAddress.setText(mainHolderVO.getOrderAddress());
            } else {
                mRlDistance.setVisibility(View.GONE);
            }

            mTextTakeoutOrderNo.setText(mainHolderVO.getOrderNoSpan());
            mTextTakeoutOrderPersonInfo.setText(mainHolderVO.getOrderPersonSpan());
            mTextTakeoutOrderNext.setText(mainHolderVO.getNextMenuValue());

            switch (mainHolderVO.getTakeout().getReserveStatus()) {
                case TakeoutConstants.ReserveStatus.APPOINTMENT:
                    mTextTakeoutOrderAppointmentFlag.setVisibility(View.VISIBLE);
                    mTextTakeoutOrderAppointmentFlag.setText(mainHolderVO.getAppointmentFlagText());
                    break;
                default:
                    mTextTakeoutOrderAppointmentFlag.setVisibility(View.GONE);
                    mTextTakeoutOrderAppointmentFlag.setText(mainHolderVO.getAppointmentFlagText());
                    break;

            }

            toggleShow(mainHolderVO, false);

            processMenusVisible(mainHolderVO.getTakeout());

            mTextTakeoutOrderDistance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CcdWebViewAcitivity.launchActivity(getContext(), LocationHelper.getMapUrl(String.valueOf(mainHolderVO.getTakeout().getLongitude())
                            , String.valueOf(mainHolderVO.getTakeout().getLatitude()), mainHolderVO.getTakeout().getAddress()));
                }
            });

            mTextTakeoutOrderRepeal.setText(mainHolderVO.getTakeout().getNeedCancelAgain() == 0
                    ? R.string.module_takeout_order_revoke : R.string.module_takeout_order_revoke_continue);

            mTextTakeoutOrderNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdapter.mAdapterClick != null) {
                        mAdapter.mAdapterClick.onAdapterClick(TakeoutListAdapter.CLICK_TYPE_CHANGE_STATUS, v, mainHolderVO.getTakeout());
                    }
                }
            });

            mTextTakeoutOrderDetailToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleShow(mainHolderVO, true);
                }
            });

            mTextTakeoutOrderPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdapter.mAdapterClick != null) {
                        mAdapter.mAdapterClick.onAdapterClick(TakeoutListAdapter.CLICK_TYPE_PRINT, v, mainHolderVO.getTakeout());
                    }
                }
            });

            mTextTakeoutOrderPersonInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdapter.mAdapterClick != null) {
                        Takeout takeout = mainHolderVO.getTakeout();
                        if (!TextUtils.isEmpty(takeout.getMobile())) {
                            mAdapter.mAdapterClick.onAdapterClick(TakeoutListAdapter.CLICK_TYPE_SHOW_PHONE_TIP,
                                    v, takeout.getMobile());
                        }
                    }
                }
            });

            mTextTakeoutOrderRepeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdapter.mAdapterClick != null) {
                        mAdapter.mAdapterClick.onAdapterClick(TakeoutListAdapter.CLICK_TYPE_CANCEL_ORDER, v, mainHolderVO.getTakeout());
                    }
                }
            });
        }

        private void processMenusVisible(Takeout takeout) {
            switch (takeout.getStatus()) {
                case TakeoutConstants.OrderStatus.UN_COOK:
                    //如果是未下厨，打印的金额是0，菜信息也没有。为了兼容本地收银，不显示打印按钮
                    mTextTakeoutOrderPrint.setVisibility(View.GONE);
                    mTextTakeoutOrderRepeal.setVisibility(View.VISIBLE);
                    mTextTakeoutOrderNext.setVisibility(View.VISIBLE);
                    break;
                case TakeoutConstants.OrderStatus.WAITING_DISPATCH:
                case TakeoutConstants.OrderStatus.WAITING_DELIVERY:
                    mTextTakeoutOrderPrint.setVisibility(View.VISIBLE);
                    mTextTakeoutOrderRepeal.setVisibility(View.VISIBLE);
                    mTextTakeoutOrderNext.setVisibility(View.VISIBLE);
                    break;
                case TakeoutConstants.OrderStatus.DELIVERING:
                    mTextTakeoutOrderPrint.setVisibility(View.VISIBLE);
                    mTextTakeoutOrderRepeal.setVisibility(View.VISIBLE);
                    //如果是店家配送，则显示“已送达”
//                    mTextTakeoutOrderNext.setVisibility(
//                            TakeoutConstants.DeliveryPlatformCode.SELF_DELIVERY.equals(takeout.getTakeoutOrderDetailVo().getDeliveryPlatformCode()) ?
//                                    View.VISIBLE : View.GONE);
                    mTextTakeoutOrderNext.setVisibility(View.VISIBLE);
                    break;
                case TakeoutConstants.OrderStatus.ARRIVED:
                case TakeoutConstants.OrderStatus.DELIVERY_EXCEPTION:
                    mTextTakeoutOrderPrint.setVisibility(View.VISIBLE);
                    mTextTakeoutOrderRepeal.setVisibility(View.GONE);
                    mTextTakeoutOrderNext.setVisibility(View.VISIBLE);
                    break;
                default:
                    mTextTakeoutOrderPrint.setVisibility(View.VISIBLE);
                    mTextTakeoutOrderRepeal.setVisibility(View.VISIBLE);
                    mTextTakeoutOrderNext.setVisibility(View.VISIBLE);
                    break;
            }
            //mTextTakeoutOrderNext.setVisibility(takeout.getNeedCancelAgain() == 1 ? View.GONE : View.VISIBLE);
        }

        private void toggleShow(OrderMainHolderVO mainHolderVO, boolean updateNode) {
            if (mainHolderVO.isOpen()) {
                mTextTakeoutOrderDetailToggle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.module_takeout_ic_order_detail_fold, 0);
                mTextTakeoutOrderDetailToggle.setText(R.string.module_takeout_order_item_open);
                if (updateNode && mainHolderVO.getChildNodes() != null) {
                    mainHolderVO.setOpen(false);
                    mAdapter.removeItems(mainHolderVO.getChildNodes());
                }
            } else {
                mTextTakeoutOrderDetailToggle.setText(R.string.module_takeout_order_item_close);
                mTextTakeoutOrderDetailToggle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.module_takeout_ic_order_detail_unfold, 0);
                if (updateNode && mainHolderVO.getChildNodes() != null) {
                    mainHolderVO.setOpen(true);
                    mAdapter.appendItems(getAdapterPosition() + 1, mainHolderVO.getChildNodes());
                }
            }
        }

    }

    static class OrderDeliveryHolder extends BaseHolder {

        @BindView(R2.id.text_takeout_delivery_side)
        TextView mTextTakeoutDeliverySide;
        @BindView(R2.id.text_takeout_horse_man_take_time)
        TextView mTextTakeoutHorseManTakeTime;
        @BindView(R2.id.text_takeout_horse_man_name)
        TextView mTextTakeoutHorseManName;
        @BindView(R2.id.text_takeout_delivery_time)
        TextView mTextTakeoutDeliveryTime;


        TakeoutListAdapter mAdapter;

        public OrderDeliveryHolder(Context context, View itemView, TakeoutListAdapter adapter) {
            super(context, itemView);
            mAdapter = adapter;
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView(List source, Object bean) {

            if (!(bean instanceof DeliveryHolderVO)) {
                return;
            }
            DeliveryHolderVO deliveryHolderVO = (DeliveryHolderVO) bean;
            final Takeout takeout = deliveryHolderVO.getTakeout();
            if (TextUtils.isEmpty(deliveryHolderVO.getDeliveryPlatform())) {
                mTextTakeoutDeliverySide.setVisibility(View.GONE);
            } else {
                mTextTakeoutDeliverySide.setVisibility(View.VISIBLE);
                mTextTakeoutDeliverySide.setText(deliveryHolderVO.getDeliveryPlatform());
            }
            if (!TextUtils.isEmpty(deliveryHolderVO.getExpressCode())) {
                mTextTakeoutHorseManTakeTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                mTextTakeoutHorseManTakeTime.setText(deliveryHolderVO.getExpressCode());
            } else {
                mTextTakeoutHorseManTakeTime.setText(deliveryHolderVO.getHorseManStatus());
                mTextTakeoutHorseManTakeTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.module_takeout_ic_arrow_right, 0);
            }
            if (!TextUtils.isEmpty(deliveryHolderVO.getExpressCode())) {
                mTextTakeoutHorseManName.setVisibility(View.GONE);
            } else {
                if (TextUtils.isEmpty(deliveryHolderVO.getHorseManName())) {
                    mTextTakeoutHorseManName.setVisibility(View.GONE);
                } else {
                    mTextTakeoutHorseManName.setVisibility(View.VISIBLE);
                    mTextTakeoutHorseManName.setText(deliveryHolderVO.getHorseManName());
                }
            }

            if (deliveryHolderVO.getDeliveryTime() != null) {
                mTextTakeoutDeliveryTime.setVisibility(View.VISIBLE);
                mTextTakeoutDeliveryTime.setText(deliveryHolderVO.getDeliveryTime());
            } else {
                mTextTakeoutDeliveryTime.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(deliveryHolderVO.getExpressCode())) {
                mTextTakeoutHorseManTakeTime.setOnClickListener(null);
            } else {
                mTextTakeoutHorseManTakeTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mAdapter.mAdapterClick != null) {
                            mAdapter.mAdapterClick.onAdapterClick(CLICK_TYPE_SHOW_DELIVERY_INFO, v, takeout);
                        }
                    }
                });
            }

            mTextTakeoutHorseManName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdapter.mAdapterClick != null && takeout.getTakeoutOrderDetailVo() != null
                            && !TextUtils.isEmpty(takeout.getTakeoutOrderDetailVo().getCourierPhone())) {
                        mAdapter.mAdapterClick.onAdapterClick(CLICK_TYPE_SHOW_PHONE_TIP, v,
                                takeout.getTakeoutOrderDetailVo().getCourierPhone());
                    }
                }
            });

        }
    }

    static class OrderDescHolder extends BaseHolder {
        @BindView(R2.id.text_takeout_order_person_num)
        TextView mTextTakeoutOrderPersonNum;
        @BindView(R2.id.text_takeout_order_good_num)
        TextView mTextTakeoutOrderGoodNum;
        @BindView(R2.id.text_takeout_order_desc)
        TextView mTextTakeoutOrderDesc;

        public OrderDescHolder(Context context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (!(bean instanceof OrderDescHolderVO)) {
                return;
            }
            OrderDescHolderVO descHolderVO = (OrderDescHolderVO) bean;
            mTextTakeoutOrderGoodNum.setText(descHolderVO.getOrderGoodNum());
            mTextTakeoutOrderPersonNum.setText(descHolderVO.getPersonNum());
            if (!TextUtils.isEmpty(descHolderVO.getMemo())) {
                mTextTakeoutOrderDesc.setVisibility(View.VISIBLE);
                mTextTakeoutOrderDesc.setText(descHolderVO.getMemo());
            } else {
                mTextTakeoutOrderDesc.setVisibility(View.GONE);
            }
        }
    }

    static class OrderFoodHolder extends BaseHolder {
        @BindView(R2.id.text_takeout_order_food_name)
        TextView mTextTakeoutOrderFoodName;
        @BindView(R2.id.text_takeout_order_food_spec_make)
        TextView mTextTakeoutOrderFoodSpecMake;
        @BindView(R2.id.text_takeout_order_food_num)
        TextView mTextTakeoutOrderFoodNum;
        @BindView(R2.id.view_divider_bottom)
        View mViewDividerBottom;
        @BindView(R2.id.text_suite_food_flag)
        View mTextSuitFlag;


        OrderFoodHolder(Context context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (!(bean instanceof OrderFoodHolderVO)) {
                return;
            }
            OrderFoodHolderVO foodHolderVO = (OrderFoodHolderVO) bean;
            mTextTakeoutOrderFoodName.setText(foodHolderVO.getFoodName());
            if (TextUtils.isEmpty(foodHolderVO.getFoodProperties())) {
                mTextTakeoutOrderFoodSpecMake.setVisibility(View.GONE);
            } else {
                mTextTakeoutOrderFoodSpecMake.setVisibility(View.VISIBLE);
                mTextTakeoutOrderFoodSpecMake.setText(foodHolderVO.getFoodProperties());
            }
            if (foodHolderVO.isSuit()) {
                mViewDividerBottom.setVisibility(View.INVISIBLE);
                mTextSuitFlag.setVisibility(View.VISIBLE);
            } else {
                mViewDividerBottom.setVisibility(View.VISIBLE);
                mTextSuitFlag.setVisibility(View.GONE);
            }
            mTextTakeoutOrderFoodNum.setText(foodHolderVO.getFoodNum());

            mTextTakeoutOrderFoodNum.setTextColor(getColor(foodHolderVO.getNum() > 1
                    ? R.color.module_takeout_food_num_red : R.color.module_takeout_food_num));

            setThroughTextFlag(foodHolderVO.getStatus(), mTextTakeoutOrderFoodName);
            setThroughTextFlag(foodHolderVO.getStatus(), mTextTakeoutOrderFoodSpecMake);
            setThroughTextFlag(foodHolderVO.getStatus(), mTextTakeoutOrderFoodNum);

        }
    }

    private static void setThroughTextFlag(int status, TextView textView) {
        if (status == 3) {//该菜已退
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }


    static class OrderSubFoodHolder extends BaseHolder {
        @BindView(R2.id.text_takeout_order_food_name)
        TextView mTextTakeoutOrderFoodName;
        @BindView(R2.id.text_takeout_order_food_num)
        TextView mTextTakeoutOrderFoodNum;
        @BindView(R2.id.text_suit_menu_properties)
        TextView mTextSuitMenuProperties;
        @BindView(R2.id.view_divider_bottom)
        View mViewDividerBottom;


        OrderSubFoodHolder(Context context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (!(bean instanceof OrderSubFoodHolderVO)) {
                return;
            }
            OrderSubFoodHolderVO foodHolderVO = (OrderSubFoodHolderVO) bean;
            mTextTakeoutOrderFoodName.setText(foodHolderVO.getFoodName());
            mTextTakeoutOrderFoodNum.setText(foodHolderVO.getFoodNum());
            mViewDividerBottom.setVisibility(foodHolderVO.isShowDivider() ? View.VISIBLE : View.INVISIBLE);
            mTextTakeoutOrderFoodNum.setTextColor(getColor(foodHolderVO.getNum() > 1
                    ? R.color.module_takeout_food_num_red : R.color.module_takeout_food_num));
            String properties = foodHolderVO.getFoodProperties();
            if (TextUtils.isEmpty(properties)) {
                mTextSuitMenuProperties.setVisibility(View.GONE);
            } else {
                mTextSuitMenuProperties.setVisibility(View.VISIBLE);
                mTextSuitMenuProperties.setText(properties);
            }
        }
    }

    static class OrderPayInfoHolder extends BaseHolder {

        @BindView(R2.id.text_module_takeout_pay_detail)
        TextView mTextModuleTakeoutPayDetail;

        public OrderPayInfoHolder(Context context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (!(bean instanceof OrderPayInfoHolderVO)) {
                return;
            }
            OrderPayInfoHolderVO payInfoHolderVO = (OrderPayInfoHolderVO) bean;
            mTextModuleTakeoutPayDetail.setText(payInfoHolderVO.getPayDetailSpan());
        }
    }

    static class OrderInfoHolder extends BaseHolder {

        @BindView(R2.id.text_takeout_order_info)
        TextView mTextTakeoutOrderInfo;
        @BindView(R2.id.text_takeout_order_change)
        TextView mTextTakeoutOrderPrint;

        public OrderInfoHolder(Context context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (!(bean instanceof OrderInfoHolderVO)) {
                return;
            }

            OrderInfoHolderVO vo = (OrderInfoHolderVO) bean;
            mTextTakeoutOrderInfo.setText(vo.getOrderInfo());
            mTextTakeoutOrderPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShortToast(getContext(), "order change");
                }
            });

        }
    }

}
