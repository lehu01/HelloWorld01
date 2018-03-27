package com.zmsoft.ccd.module.receipt.receipt.helper;

import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptDiscountItem;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptHeadReceivedItem;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptHeadRecyclerItem;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptMethodRecyclerItem;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptRecyclerItem;
import com.zmsoft.ccd.module.receipt.receiptway.coupon.model.CouponItem;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.items.UnitRecyclerItem;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.model.SignStaffItem;
import com.zmsoft.ccd.receipt.bean.CloudCashBill;
import com.zmsoft.ccd.receipt.bean.GetCloudCashBillResponse;
import com.zmsoft.ccd.receipt.bean.GetSignBillSingerResponse;
import com.zmsoft.ccd.receipt.bean.GetVoucherInfoResponse;
import com.zmsoft.ccd.receipt.bean.KindPay;
import com.zmsoft.ccd.receipt.bean.Pay;
import com.zmsoft.ccd.receipt.bean.Promotion;
import com.zmsoft.ccd.receipt.bean.SignInfoVo;
import com.zmsoft.ccd.receipt.bean.VoucherInfoVo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class DataMapLayer {
    /**
     * 默认金额
     */
    private static final String DEFAULT_FEE = "0.00";

    public static List<ReceiptRecyclerItem> getReceiptInfo(GetCloudCashBillResponse getCloudCashBillResponse
            , boolean thirdTakeout) {
        if (null == getCloudCashBillResponse) {
            return null;
        }
        List<ReceiptRecyclerItem> receiptRecyclerItems = new ArrayList<>();
        ReceiptRecyclerItem headerRecyclerItem = new ReceiptRecyclerItem();
        headerRecyclerItem.setItemType(ReceiptRecyclerItem.ItemType.TYPE_HEAD);
        ReceiptHeadRecyclerItem receiptHeadRecyclerItem = new ReceiptHeadRecyclerItem();
        String consumeFee, serviceFee, minimumFee, receivableFee, discountFee, needReceiptFee, deliveryFee;
        CloudCashBill cloudCashBill = getCloudCashBillResponse.getCloudCashBill();
        //消费金额
        if (null == cloudCashBill) {
            consumeFee = DEFAULT_FEE;
            serviceFee = DEFAULT_FEE;
            deliveryFee = DEFAULT_FEE;
            minimumFee = DEFAULT_FEE;
            receivableFee = DEFAULT_FEE;
            discountFee = DEFAULT_FEE;
            needReceiptFee = DEFAULT_FEE;
        } else {
            receiptHeadRecyclerItem.setCloudCashBill(cloudCashBill);
            consumeFee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , FeeHelper.getDecimalFeeByFen(cloudCashBill.getOriginFee()));
            serviceFee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , FeeHelper.getDecimalFeeByFen(cloudCashBill.getServiceFee()));
            deliveryFee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , FeeHelper.getDecimalFeeByFen(cloudCashBill.getOutFee()));
            minimumFee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , FeeHelper.getDecimalFeeByFen(cloudCashBill.getLeastFee()));
            receivableFee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , FeeHelper.getDecimalFeeByFen(cloudCashBill.getFee()));
            discountFee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , FeeHelper.getDecimalFeeByFen(cloudCashBill.getDiscountFee()), true);
            if (cloudCashBill.getNeedFee() >= 0) {
                needReceiptFee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                        , FeeHelper.getDecimalFeeByFen(cloudCashBill.getNeedFee()));
            } else {
                needReceiptFee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                        , FeeHelper.getDecimalFeeByFen(cloudCashBill.getNeedFee()), true);
            }
        }
        receiptHeadRecyclerItem.setConsumeFee(consumeFee);
        receiptHeadRecyclerItem.setServiceFee(serviceFee);
        receiptHeadRecyclerItem.setDeliveryFee(deliveryFee);
        receiptHeadRecyclerItem.setMinimumFee(minimumFee);
        receiptHeadRecyclerItem.setReceivableFee(receivableFee);
        receiptHeadRecyclerItem.setDiscountFee(discountFee);
        receiptHeadRecyclerItem.setNeedReceiptFee(needReceiptFee);
        receiptHeadRecyclerItem.setThirdTakeout(thirdTakeout);
        //优惠金额ITEM
        List<Promotion> promotions = getCloudCashBillResponse.getPromotions();
        if (null == promotions || promotions.isEmpty()) {
            receiptHeadRecyclerItem.setReceiptDiscountItemList(null);
        } else {
            List<ReceiptDiscountItem> discountItemList = new ArrayList<>(promotions.size());
            for (int i = 0; i < promotions.size(); i++) {
                Promotion promotion = promotions.get(i);
                ReceiptDiscountItem receiptDiscountItem = new ReceiptDiscountItem();
                receiptDiscountItem.setDiscountName(promotion.getName());
                switch (promotion.getMode()) {
                    case ReceiptHelper.DiscountMode.MODE_MEMBERPRICE:
                    case ReceiptHelper.DiscountMode.MODE_DISCOUNTPLAN:
                        receiptDiscountItem.setDiscountRate(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , FeeHelper.getDecimalFeeByFen(promotion.getFee()), true));
                        break;
                    case ReceiptHelper.DiscountMode.MODE_RATIO:
                        String ratioStr = BigDecimal.valueOf(promotion.getRatio()).divide(new BigDecimal(10)).toString();
                        double ratioD = Double.parseDouble(ratioStr);
                        String ratio;
                        if (NumberUtils.doubleIsInteger(ratioD)) {
                            ratio = (int) ratioD + "";
                        } else {
                            ratio = NumberUtils.getDecimalFee(ratioD, 1);
                        }
                        receiptDiscountItem.setDiscountRate(String.format(context.getResources().getString(R.string.module_receipt_discount_ratio)
                                , ratio));
                        break;
                }
                receiptDiscountItem.setDiscountType(promotion.getType());
                discountItemList.add(receiptDiscountItem);
            }
            receiptHeadRecyclerItem.setReceiptDiscountItemList(discountItemList);
        }
        //已收金额ITEM
        List<Pay> pays = getCloudCashBillResponse.getPays();
        if (null == pays || pays.isEmpty()) {
            receiptHeadRecyclerItem.setReceivedItemList(null);
        } else {
            List<ReceiptHeadReceivedItem> receivedItems = new ArrayList<>(pays.size());
            for (int i = 0; i < pays.size(); i++) {
                Pay pay = pays.get(i);
                ReceiptHeadReceivedItem receivedItem = new ReceiptHeadReceivedItem();
                receivedItem.setPay(pay);
                receivedItem.setPayName(pay.getName());
                String payFee;
                if (pay.getFee() >= 0) {
                    payFee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , FeeHelper.getDecimalFeeByFen(pay.getFee()));
                } else {
                    payFee = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , FeeHelper.getDecimalFeeByFen(Math.abs(pay.getFee())), true);
                }
                receivedItem.setPayFee(payFee);
                receivedItems.add(receivedItem);
            }
            receiptHeadRecyclerItem.setReceivedItemList(receivedItems);
        }
        headerRecyclerItem.setHeadRecyclerItem(receiptHeadRecyclerItem);
        receiptRecyclerItems.add(headerRecyclerItem);
        //收款方式
        List<KindPay> kindPays = getCloudCashBillResponse.getKindPays();
        if (null != kindPays && !kindPays.isEmpty()) {
            int methodCount = kindPays.size();
            int realMethodCount = methodCount;
            for (int i = 0; i < methodCount; i++) {
                KindPay kindPay = kindPays.get(i);
                switch (kindPay.getType()) {
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_CASH:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_WEIXIN:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALIPAY:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VIP:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_SIGN_BILL:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_BANK:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_FREE_BILL:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_COUPON:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_PART_PAY:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VOUCHER:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT:
                    case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK:
                        ReceiptRecyclerItem methodRecyclerItem1 = new ReceiptRecyclerItem();
                        methodRecyclerItem1.setItemType(ReceiptRecyclerItem.ItemType.TYPE_RECEIPT_METHOD);
                        ReceiptMethodRecyclerItem receiptMethodRecyclerItem = new ReceiptMethodRecyclerItem();
                        receiptMethodRecyclerItem.setName(kindPay.getName());
                        receiptMethodRecyclerItem.setMethod(kindPay.getType());
                        receiptMethodRecyclerItem.setKindPayId(kindPay.getId());
                        methodRecyclerItem1.setMethodRecyclerItem(receiptMethodRecyclerItem);
                        receiptRecyclerItems.add(methodRecyclerItem1);
                        break;
                    default:
                        realMethodCount--;
                        break;
                }
            }
            //如果收款方式个数是奇数个，则在最后补位一个空的item
            if (realMethodCount % 2 != 0) {
                ReceiptRecyclerItem emptyRecyclerItem = new ReceiptRecyclerItem();
                emptyRecyclerItem.setItemType(ReceiptRecyclerItem.ItemType.TYPE_RECEIPT_EMPTY);
                receiptRecyclerItems.add(emptyRecyclerItem);
            }
        }
        return receiptRecyclerItems;
    }

    public static List<CouponItem> getCouponItemList(GetVoucherInfoResponse getVoucherInfoResponse, String title) {
        if (null == getVoucherInfoResponse || null == getVoucherInfoResponse.getVoucherFundList()
                || getVoucherInfoResponse.getVoucherFundList().isEmpty()) {
            return null;
        }
        List<VoucherInfoVo> voucherFundList = getVoucherInfoResponse.getVoucherFundList();
        List<CouponItem> couponItemList = new ArrayList<>(voucherFundList.size());
        for (int i = 0; i < voucherFundList.size(); i++) {
            CouponItem couponItem = new CouponItem();
            VoucherInfoVo voucherInfoVo = voucherFundList.get(i);
            couponItem.setVoucherInfoVo(voucherInfoVo);
            if (i == 0) { //默认选中第一项
                couponItem.setChecked(true);
            } else {
                couponItem.setChecked(false);
            }
            //代金券实际购买金额(45.00 eg:45抵50)
            String couponCost = FeeHelper.getDecimalFee(voucherInfoVo.getCouponCost());
            //代金券面额(50.00 eg:45抵50)
            String couponFee = FeeHelper.getDecimalFee(voucherInfoVo.getCouponFee());
            couponItem.setName(String.format(context.getResources().getString(R.string.module_receipt_coupon_item)
                    , title + couponCost, couponFee));
            couponItemList.add(couponItem);
        }
        return couponItemList;
    }

    public static List<SignStaffItem> getSignStaffItemList(GetSignBillSingerResponse getSignBillSingerResponse) {
        if (null == getSignBillSingerResponse || null == getSignBillSingerResponse.getSignInfoVos()
                || getSignBillSingerResponse.getSignInfoVos().isEmpty()) {
            return null;
        }
        List<SignInfoVo> signInfoVoList = getSignBillSingerResponse.getSignInfoVos();
        List<SignStaffItem> signStaffItemList = new ArrayList<>(signInfoVoList.size());
        for (int i = 0; i < signInfoVoList.size(); i++) {
            SignStaffItem signStaffItem = new SignStaffItem();
            SignInfoVo signInfoVo = signInfoVoList.get(i);
            signStaffItem.setSignInfoVo(signInfoVo);
            signStaffItem.setChecked(false);
            signStaffItem.setName(signInfoVo.getName());
            signStaffItemList.add(signStaffItem);
        }
        return signStaffItemList;
    }

    public static List<UnitRecyclerItem> getSignUnitItemList(GetSignBillSingerResponse getSignBillSingerResponse
            , UnitRecyclerItem selectedUnitRecyclerItem) {
        if (null == getSignBillSingerResponse || null == getSignBillSingerResponse.getSignInfoVos()
                || getSignBillSingerResponse.getSignInfoVos().isEmpty()) {
            return null;
        }
        List<SignInfoVo> signInfoVoList = getSignBillSingerResponse.getSignInfoVos();
        List<UnitRecyclerItem> unitRecyclerItemList = new ArrayList<>(signInfoVoList.size());
        for (int i = 0; i < signInfoVoList.size(); i++) {
            UnitRecyclerItem unitRecyclerItem = new UnitRecyclerItem();
            SignInfoVo signInfoVo = signInfoVoList.get(i);
            unitRecyclerItem.setUnitName(signInfoVo.getName());
            if (null != selectedUnitRecyclerItem) {
                if (signInfoVo.getKindPayDetailOptionId().equals(selectedUnitRecyclerItem.getKindPayDetailOptionId())) {
                    unitRecyclerItem.setChecked(true);
                } else {
                    unitRecyclerItem.setChecked(false);
                }
            } else {
                unitRecyclerItem.setChecked(false);
            }
            unitRecyclerItem.setKindPayDetailId(signInfoVo.getKindPayDetailId());
            unitRecyclerItem.setKindPayDetailOptionId(signInfoVo.getKindPayDetailOptionId());
            unitRecyclerItemList.add(unitRecyclerItem);
        }
        return unitRecyclerItemList;
    }

}
