package com.jingpai.pos.customer.bean.payment;

import java.util.ArrayList;

/**
 * Create by liujinheng
 * date 2020/6/2
 * function
 */
public class BillDetailBean {
    private String amount ;//金额
    private String chargeDate ;//费用周期
    private ArrayList<ChargeInfoBean>chargeInfoList;//费用详情
    private String chargeMonth ;//费用月份
    private String chargeYear ;//费用年份
    private String payType ;//缴费方式

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
    }

    public ArrayList<ChargeInfoBean> getChargeInfoList() {
        return chargeInfoList;
    }

    public void setChargeInfoList(ArrayList<ChargeInfoBean> chargeInfoList) {
        this.chargeInfoList = chargeInfoList;
    }

    public String getChargeMonth() {
        return chargeMonth;
    }

    public void setChargeMonth(String chargeMonth) {
        this.chargeMonth = chargeMonth;
    }

    public String getChargeYear() {
        return chargeYear;
    }

    public void setChargeYear(String chargeYear) {
        this.chargeYear = chargeYear;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public static class ChargeInfoBean{
        private String chargeAmount ;//费用金额
        private String chargeDetailId ;//费用明细id
        private String chargeType ;//费用类型

        public String getChargeAmount() {
            return chargeAmount;
        }

        public void setChargeAmount(String chargeAmount) {
            this.chargeAmount = chargeAmount;
        }

        public String getChargeDetailId() {
            return chargeDetailId;
        }

        public void setChargeDetailId(String chargeDetailId) {
            this.chargeDetailId = chargeDetailId;
        }

        public String getChargeType() {
            return chargeType;
        }

        public void setChargeType(String chargeType) {
            this.chargeType = chargeType;
        }
    }
}
