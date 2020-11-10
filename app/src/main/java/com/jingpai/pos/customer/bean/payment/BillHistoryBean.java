package com.jingpai.pos.customer.bean.payment;

/**
 * Create by liujinheng
 * date 2020/5/27
 * function
 */
public class BillHistoryBean {

    private String houseInfo ;//房屋信息
    private String orderAmount ;//订单金额
    private String orderNo ;//订单编号
    private String orderDate ;//订单支付时间
    private String orderStatus ;//订单状态
    private String parkingInfo ;//车位信息
    private String payDate ;//支付时间
    private String payType ;//缴费方式
    private String picurl;
    private String invoiceBool ;//当前发票状态e

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getInvoiceBool() {
        return invoiceBool;
    }

    public void setInvoiceBool(String invoiceBool) {
        this.invoiceBool = invoiceBool;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderId() {
        return orderNo;
    }

    public void setOrderId(String orderId) {
        this.orderNo = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getParkingInfo() {
        return parkingInfo;
    }

    public void setParkingInfo(String parkingInfo) {
        this.parkingInfo = parkingInfo;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
