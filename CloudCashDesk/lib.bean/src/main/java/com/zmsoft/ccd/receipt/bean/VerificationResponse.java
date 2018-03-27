package com.zmsoft.ccd.receipt.bean;

/**
 * @author DangGui
 * @create 2017/8/3.
 */

public class VerificationResponse {
    private MarketPromotion marketPromotion;

    public MarketPromotion getMarketPromotion() {
        return marketPromotion;
    }

    public void setMarketPromotion(MarketPromotion marketPromotion) {
        this.marketPromotion = marketPromotion;
    }

    public static class MarketPromotion {
        /**
         * 优惠类型，对应tc的ChannelType，本店在线优惠位8，单品兑换券在线优惠位13
         */
        private short type;
        /**
         * 核销失败原因
         */
        private String verifyMessage;
        /**
         * 1表示核销正确，-1表示核销失败
         */
        private int status;
        /**
         * 优惠交易流水号
         */
        private String outTradeNo;

        public short getType() {
            return type;
        }

        public void setType(short type) {
            this.type = type;
        }

        public String getVerifyMessage() {
            return verifyMessage;
        }

        public void setVerifyMessage(String verifyMessage) {
            this.verifyMessage = verifyMessage;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }
    }
}
