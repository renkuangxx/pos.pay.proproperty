package com.jingpai.pos.customer.bean.payphone;

public class OrderBean {
    // "busiCode": "string",
    //    "orderAmt": 0,
    //    "orderNo": "string",
    //    "payInfo": "string",
    //    "payType": 0,
    //    "tradeAmt": 0
    private String busiCode;
    private int orderAmt;
    private String orderNo;
    private String payInfo;
    private int payType;
    private int tradeAmt;

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public int getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(int orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getTradeAmt() {
        return tradeAmt;
    }

    public void setTradeAmt(int tradeAmt) {
        this.tradeAmt = tradeAmt;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "busiCode='" + busiCode + '\'' +
                ", orderAmt=" + orderAmt +
                ", orderNo='" + orderNo + '\'' +
                ", payInfo='" + payInfo + '\'' +
                ", payType=" + payType +
                ", tradeAmt=" + tradeAmt +
                '}';
    }
}
