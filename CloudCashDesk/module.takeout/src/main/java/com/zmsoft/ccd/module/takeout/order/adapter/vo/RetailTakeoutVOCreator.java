package com.zmsoft.ccd.module.takeout.order.adapter.vo;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.menu.business.MenuConstant;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.order.utils.RetailTakeoutUtils;
import com.zmsoft.ccd.module.takeout.order.utils.TakeoutUtils;
import com.zmsoft.ccd.takeout.bean.DeliveryInfo;
import com.zmsoft.ccd.takeout.bean.Takeout;
import com.zmsoft.ccd.takeout.bean.TakeoutConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/9/14.
 */

public class RetailTakeoutVOCreator {


    public static OrderGroupHolderVO createGroupHolderVO(String groupNum) {
        OrderGroupHolderVO mainHolderVO = new OrderGroupHolderVO();
        mainHolderVO.setGroupNum(groupNum);
        return mainHolderVO;
    }

    public static OrderMainHolderVO createMainHolderVO(Context context, Takeout takeout) {
        OrderMainHolderVO mainHolderVo = new OrderMainHolderVO(takeout);

        mainHolderVo.setOrderAddress(takeout.getAddress());
        mainHolderVo.setOrderStatus(context.getString(RetailTakeoutUtils.getStatusShowName(takeout)));
        if (takeout.getOrderFrom() != TakeoutConstants.OrderFrom.XIAOER && !TextUtils.isEmpty(takeout.getTakeoutOrderDetailVo().getDaySeq())) {
            mainHolderVo.setOrderOriginal(context.getString(R.string.module_takeout_order_from_show_name,
                    context.getString(TakeoutUtils.getOrderFromShowName(takeout)), takeout.getTakeoutOrderDetailVo().getDaySeq()));
        } else {
            mainHolderVo.setOrderOriginal(context.getString(TakeoutUtils.getOrderFromShowName(takeout)));
        }
        mainHolderVo.setOrderDistance(TakeoutUtils.formatDistance(takeout.getDistance()));

        mainHolderVo.setNextMenuValue(context.getString(RetailTakeoutUtils.getOrderNextMenu(takeout)));

        mainHolderVo.setOrderOriginalImage(TakeoutUtils.getOrderFromImage(takeout));

        mainHolderVo.setAppointmentFlagText(context.getResources().getString(R.string.module_takeout_retail_reserve_flag));

        String prefix;
        if (takeout.getSendType() == TakeoutConstants.SendType.SELF_TAKE) {
            mainHolderVo.setOrderDeliveryWay(context.getResources().getString(R.string.module_takeout_retail_take_self));
            mainHolderVo.setOrderTakeTime(context.getResources().getString(R.string.module_takeout_retail_take_time_self, takeout.getSendTime()));
            prefix = (context.getResources().getString(R.string.module_takeout_retail_prefix_receiver_self));
        } else {
            mainHolderVo.setOrderDeliveryWay(context.getResources().getString(R.string.module_takeout_take_by_other));
            mainHolderVo.setOrderTakeTime(context.getResources().getString(R.string.module_takeout_take_time,
                    takeout.getReserveStatus() == TakeoutConstants.ReserveStatus.DELIVERY_IMMEDIATELY
                            ? context.getString(R.string.module_takeout_delivery_immediatelu) : takeout.getSendTime()));
            prefix = (context.getResources().getString(R.string.module_takeout_prefix_receiver));
        }
        //组装取餐人/收货人信息
        String name = takeout.getName();
        if (!TextUtils.isEmpty(name)) {
            if (name.length() > 6) {
                name = name.substring(0, 6) + "...";
            }
        }

        CharSequence man = context.getString(R.string.module_takeout_order_take_person, prefix, name, takeout.getMobile());
        SpannableString ssMan = new SpannableString(man);

        if (!TextUtils.isEmpty(name)) {
            int start = prefix.length() + 1;
            int end = start + name.length();
            ssMan.setSpan(new StyleSpan(Typeface.BOLD), start, end, 0);
            ssMan.setSpan(new RelativeSizeSpan(1.1f), start, end, 0);
        }
        mainHolderVo.setOrderPersonSpan(ssMan);

        //组装订单编号
        String number = String.valueOf(takeout.getCode());
        CharSequence s = context.getString(R.string.module_takeout_order_no, number);
        int start2 = s.length() - number.length();
        SpannableString ss1 = new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(2), start2, s.length(), 0);
        mainHolderVo.setOrderNoSpan(ss1);
        return mainHolderVo;
    }

    public static DeliveryHolderVO getDeliveryHolderVO(Context context, Takeout takeout) {
        //if (takeout.getStatus() == TakeoutConstants.OrderStatus.WAITING_DISPATCH
        //        || takeout.getStatus() == TakeoutConstants.OrderStatus.WAITING_DELIVERY || takeout.getStatus() == TakeoutConstants.OrderStatus.DELIVERING) {
        if (takeout.getStatus() == TakeoutConstants.OrderStatus.UN_COOK) {
            return null;
        }

        if (takeout.getTakeoutOrderDetailVo() != null && takeout.getSendType() != TakeoutConstants.SendType.SELF_TAKE) {//不是用户自取
            String platform = takeout.getTakeoutOrderDetailVo().getDeliveryPlatform();
            String courierName = takeout.getTakeoutOrderDetailVo().getCourierName();
            if (TextUtils.isEmpty(platform) && (TextUtils.isEmpty(courierName))) {
                return null;
            }
            DeliveryHolderVO deliveryHolderVO = new DeliveryHolderVO(takeout);
            deliveryHolderVO.setDeliveryPlatform(platform);
            if (!TextUtils.isEmpty(takeout.getTakeoutOrderDetailVo().getExpressCode())) {
                deliveryHolderVO.setExpressCode(context.getString(R.string.module_takeout_delivery_express_code,
                        takeout.getTakeoutOrderDetailVo().getExpressCode()));
            }
            if (!TextUtils.isEmpty(takeout.getTakeoutOrderDetailVo().getDeliveryTime())) {
                deliveryHolderVO.setDeliveryTime(context.getString(R.string.module_takeout_delivery_time,
                        takeout.getTakeoutOrderDetailVo().getDeliveryTime()));
            }

            if (!TextUtils.isEmpty(takeout.getTakeoutOrderDetailVo().getExpressCode())) {
                return deliveryHolderVO;
            }
            List<DeliveryInfo> deliveryInfos = takeout.getTakeoutOrderDetailVo().getTakeoutDeliveryInfoVos();
            if ((deliveryInfos == null || deliveryInfos.isEmpty())) {
                return null;
            }
            DeliveryInfo deliveryInfo = deliveryInfos.get(0);
            deliveryHolderVO.setHorseManStatus(deliveryInfo.getDate() + " " + deliveryInfo.getDesc());
            if (!TextUtils.isEmpty(courierName)) {
                deliveryHolderVO.setHorseManName(context.getString(R.string.module_takeout_horse_man_name, courierName));
            }
            return deliveryHolderVO;
        }
        //}
        return null;
    }

    private static int getInstanceNum(Takeout takeout) {
        int count = 0;
        if (takeout.getTakeoutOrderDetailVo() != null && takeout.getTakeoutOrderDetailVo().getTakeoutInstanceVoList() != null) {
            for (Takeout.TakeoutInstance instance : takeout.getTakeoutOrderDetailVo().getTakeoutInstanceVoList()) {
                if (instance.getType() != 1 &&
                        instance.getKind() != MenuConstant.CartFoodKind.KIND_FEED_FOOD) {//餐盒、加料不统计数量
                    count += instance.getNum();//点菜数量累加
                }
            }
        }
        return count;
    }

    public static OrderDescHolderVO getOrderDescHolderVO(Context context, Takeout takeout) {
        OrderDescHolderVO descHolderVO = new OrderDescHolderVO(takeout);
        //int count = getInstanceNum(takeout);
        int peopleCount = 0;
        String memo = null;
        int count = 0;
        if (takeout.getTakeoutOrderDetailVo() != null) {
            peopleCount = takeout.getTakeoutOrderDetailVo().getPeopleCount();
            memo = takeout.getTakeoutOrderDetailVo().getMemo();
            count = takeout.getTakeoutOrderDetailVo().getInstanceNum();
        }
        descHolderVO.setOrderGoodNum(context.getString(R.string.module_takeout_order_good_num, count));

        if (peopleCount != 0) {
            descHolderVO.setPersonNum(context.getString(R.string.module_takeout_retail_order_people_num, peopleCount));
        }
        if (!TextUtils.isEmpty(memo)) {
            descHolderVO.setMemo(context.getString(R.string.module_takeout_order_memo, memo));
        }
        return descHolderVO;
    }

    public static List<OrderFoodHolderVO> getOrderFoodHolderVO(Context context, Takeout takeout) {
        if (takeout.getTakeoutOrderDetailVo() != null &&
                takeout.getTakeoutOrderDetailVo().getTakeoutInstanceVoList() != null) {
            List<Takeout.TakeoutInstance> foods = takeout.getTakeoutOrderDetailVo().getTakeoutInstanceVoList();
            List<OrderFoodHolderVO> list = new ArrayList<>();
            for (Takeout.TakeoutInstance instance : foods) {
                OrderFoodHolderVO foodHolderVO = new OrderFoodHolderVO(takeout);
                foodHolderVO.setFoodName(instance.getName());
                foodHolderVO.setFoodSpecMake(instance.getMakeName());
                foodHolderVO.setNum(instance.getNum());
                foodHolderVO.setFoodNum(context.getString(R.string.module_takeout_order_food_num,
                        NumberUtils.trimPointIfZero(instance.getNum())));
                foodHolderVO.setTwoAccount(instance.getDoubleUnits() == 1);
                foodHolderVO.setCode(instance.getCode());
                if (instance.getKind() == MenuConstant.CartFoodKind.KIND_COMBO_FOOD) {
                    foodHolderVO.setSuit(true);
                    List<OrderSubFoodHolderVO> subMenus = getSuitSubMenus(context, takeout, instance);
                    if (subMenus != null) {
                        foodHolderVO.setChildren(subMenus);
                    }
                } else {
                    String foodProperties = getFoodProperties(context, instance);
                    if (!TextUtils.isEmpty(foodProperties)) {
                        foodHolderVO.setFoodProperties(foodProperties);
                    }
                }
                list.add(foodHolderVO);
            }
            return list;
        }
        return null;
    }

    /**
     * 规格、做法、加料、备注
     */
    private static String getFoodProperties(Context context, Takeout.TakeoutInstance instance) {

        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(instance.getSpecDetailName())) {
            builder.append(instance.getSpecDetailName()).append(context.getString(R.string.comma_separator));
        }
        if (!TextUtils.isEmpty(instance.getMakeName())) {
            builder.append(instance.getMakeName()).append(context.getString(R.string.comma_separator));
        }

        List<Takeout.TakeoutInstance> childTakeoutInstances = instance.getChildTakeoutInstances();
        if (childTakeoutInstances != null && !childTakeoutInstances.isEmpty()) {
            for (Takeout.TakeoutInstance child : childTakeoutInstances) {
                builder.append(child.getName());
                builder.append(NumberUtils.trimPointIfZero(child.getNum())).append(child.getUnit())
                        .append(context.getString(R.string.comma_separator));
            }
        }
        if (!TextUtils.isEmpty(instance.getMemo())) {
            builder.append(instance.getMemo()).append(context.getString(R.string.comma_separator));
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    private static List<OrderSubFoodHolderVO> getSuitSubMenus(Context context, Takeout takeout, Takeout.TakeoutInstance instance) {
        List<Takeout.TakeoutInstance> childTakeoutInstances = instance.getChildTakeoutInstances();
        if (childTakeoutInstances == null || childTakeoutInstances.isEmpty()) {
            return null;
        }
        List<OrderSubFoodHolderVO> children = new ArrayList<>(childTakeoutInstances.size());
        for (Takeout.TakeoutInstance child : childTakeoutInstances) {
            OrderSubFoodHolderVO subFoodVO = new OrderSubFoodHolderVO(takeout);
            subFoodVO.setFoodName(child.getName());
            subFoodVO.setFoodNum(context.getString(R.string.module_takeout_order_food_num,
                    NumberUtils.trimPointIfZero(child.getNum())));
            subFoodVO.setNum(child.getNum());
            String properties = getFoodProperties(context, child);
            if (!TextUtils.isEmpty(properties)) {
                subFoodVO.setFoodProperties(properties);
            }
            children.add(subFoodVO);
        }
        return children;
    }

    public static OrderPayInfoHolderVO getOrderPayInfoHolderVO(Context context, Takeout takeout) {
        if (takeout.getTakeoutOrderDetailVo() != null) {
            StringBuilder builder = new StringBuilder();
            List<SpanVO> spans = new ArrayList<>();
            if (takeout.getTakeoutOrderDetailVo().getTakeoutPayVoList() != null) {
                List<Takeout.TakeoutPay> list = takeout.getTakeoutOrderDetailVo().getTakeoutPayVoList();
                int red = context.getResources().getColor(R.color.module_takeout_red);
                for (int i = 0; i < list.size(); i++) {
                    Takeout.TakeoutPay pay = list.get(i);
                    //String payName = context.getString(BusinessHelper.getTakeoutPayShowName(pay.getType()));
                    String payPrice = NumberUtils.getDecimalFee(pay.getFee(), 2);
                    builder.append(context.getString(R.string.module_takeout_bar_member_card, pay.getName(), payPrice));
                    int end = builder.length();
                    int start = end - payPrice.length() - 1;

                    SpanVO spanVo = new SpanVO();
                    spanVo.setSpan(new ForegroundColorSpan(red));
                    spanVo.setStart(start);
                    spanVo.setEnd(end);
                    spans.add(spanVo);

                    if (i % 2 == 1) {
                        builder.append('\n');
                    } else {
                        builder.append(context.getString(R.string.module_takeout_comma));
                    }
                }


                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);

                    SpanVO spanVo = new SpanVO();
                    spanVo.setSpan(new StyleSpan(Typeface.BOLD));
                    spanVo.setStart(0);
                    spanVo.setEnd(builder.length());
                    spans.add(spanVo);
                }
            }

            if (takeout.getTakeoutOrderDetailVo().getOutFee() != 0) {
                int start = builder.length();
                builder.append("\n");
                String freight = NumberUtils.getDecimalFee(takeout.getTakeoutOrderDetailVo().getOutFee(), 2);
                CharSequence freightStr = context.getString(R.string.module_takeout_bar_pay_freight, freight);
                builder.append(freightStr);
                int end = builder.length();

                SpanVO spanVO = new SpanVO();
                spanVO.setStart(start);
                spanVO.setEnd(end);
                spanVO.setSpan(new ForegroundColorSpan(context.getResources().getColor((R.color.module_takeout_gray))));
                spans.add(spanVO);

                SpanVO spanVO2 = new SpanVO();
                spanVO2.setStart(start);
                spanVO2.setEnd(end);
                spanVO2.setSpan(new RelativeSizeSpan(.86f));
                spans.add(spanVO2);


            }

            if (builder.length() == 0) {
                return null;
            }

            SpannableString ss = new SpannableString(builder.toString());
            for (SpanVO span : spans) {
                ss.setSpan(span.getSpan(), span.getStart(), span.getEnd(), span.getFlag());
            }
            OrderPayInfoHolderVO payInfoHolderVO = new OrderPayInfoHolderVO(takeout);
            payInfoHolderVO.setPayDetailSpan(ss);
            return payInfoHolderVO;
        }

        return null;
    }

    public static OrderInfoHolderVO getOrderInfoHolderVO(Context context, Takeout takeout) {
        if (takeout.getTakeoutOrderDetailVo() != null) {
            OrderInfoHolderVO orderInfoHolderVO = new OrderInfoHolderVO(takeout);
            orderInfoHolderVO.setOrderInfo(context.getString(R.string.module_takeout_order_order_info,
                    takeout.getTakeoutOrderDetailVo().getCreateTime(),
                    takeout.getTakeoutOrderDetailVo().getOpenTime(),
                    takeout.getTakeoutOrderDetailVo().getInnerCode()));
            return orderInfoHolderVO;
        }
        return null;
    }

}
