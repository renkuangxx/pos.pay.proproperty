package com.jingpai.pos.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ChargeOrderDetailListVo {
    private Long id;
//    @ApiModelProperty(value = "费用订单id")
    private String chargeOrderId;

//    @ApiModelProperty(value = "费用明细id")
    private Long chargeDetailId;
//    @ApiModelProperty(value = "小区id")
    private Long commId;
//    @ApiModelProperty(value = "小区名称")
    private String commName;

//    @ApiModelProperty(value = "区域类型，1-楼栋，2-车位")
    private Integer areaType;
//    @ApiModelProperty(value = "区域ID,根据区域类型，取值车位id或房屋i")
    private String areaId;
//    @ApiModelProperty(value = "区域状态")
    private String areaState;
//    @ApiModelProperty(value = "区域编号(房号、车位号)")
    private String areaNo;
//    @ApiModelProperty(value = "收费项id")
    private Long itemId;
//    @ApiModelProperty(value = "收费项编号")
    private String itemCode;
//    @ApiModelProperty(value = "收费项名称")
    private String itemName;
//    @ApiModelProperty(value = "费用科目编号")
    private String chargeSubject;
//    @ApiModelProperty(value = "费用科目名称")
    private String chargeSubjectName;
//    @ApiModelProperty(value = "费用日期")
    private String chargeDate;
//    @ApiModelProperty(value = "费用年份")
    private String chargeYear;
//    @ApiModelProperty(value = "费用月份")
    private String chargeMonth;
//    @ApiModelProperty(value = "收费金额")
    private String chargeAmount;
//    @ApiModelProperty(value = "税率(单位百分比)")
    private String taxRate;
//    @ApiModelProperty(value = "票据类型")
    private String billType;
//    @ApiModelProperty(value = "票据摘要")
    private String billAbstract;
//    @ApiModelProperty(value = "是否打印数量")
    private String isPrintCount;
//    @ApiModelProperty(value = "外部订单号")
    private String outOrderNo;
//    @ApiModelProperty(value = "区域数量，针对周期性费用有效，根据类型取值面积或者车位个数")
    private String areaNum;
//    @ApiModelProperty(value = "费用单位，针对周期性费用有效")
    private String chargeUnit;
//    @ApiModelProperty(value = " 状态，0-可用，1-不可用")
    private Integer status;

    private Date createTime;

    private String operatorId;

    private Date updateTime;


    private BigDecimal reductionAmount;
    /**
     * 应缴金额
     */
    private BigDecimal reductionReceivableAmount;
    /**
     * 零税率
     */
    private String taxPolicy;
    /**
     * 使用优惠政策
     */
    private String discountsPolicy;
    /**
     *

     陶木木:
     /**
     * 商品编码
     */
    private String productCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChargeOrderId() {
        return chargeOrderId;
    }

    public void setChargeOrderId(String chargeOrderId) {
        this.chargeOrderId = chargeOrderId;
    }

    public Long getChargeDetailId() {
        return chargeDetailId;
    }

    public void setChargeDetailId(Long chargeDetailId) {
        this.chargeDetailId = chargeDetailId;
    }

    public Long getCommId() {
        return commId;
    }

    public void setCommId(Long commId) {
        this.commId = commId;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaState() {
        return areaState;
    }

    public void setAreaState(String areaState) {
        this.areaState = areaState;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getChargeSubject() {
        return chargeSubject;
    }

    public void setChargeSubject(String chargeSubject) {
        this.chargeSubject = chargeSubject;
    }

    public String getChargeSubjectName() {
        return chargeSubjectName;
    }

    public void setChargeSubjectName(String chargeSubjectName) {
        this.chargeSubjectName = chargeSubjectName;
    }

    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
    }

    public String getChargeYear() {
        return chargeYear;
    }

    public void setChargeYear(String chargeYear) {
        this.chargeYear = chargeYear;
    }

    public String getChargeMonth() {
        return chargeMonth;
    }

    public void setChargeMonth(String chargeMonth) {
        this.chargeMonth = chargeMonth;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillAbstract() {
        return billAbstract;
    }

    public void setBillAbstract(String billAbstract) {
        this.billAbstract = billAbstract;
    }

    public String getIsPrintCount() {
        return isPrintCount;
    }

    public void setIsPrintCount(String isPrintCount) {
        this.isPrintCount = isPrintCount;
    }

    public String getOutOrderNo() {
        return outOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        this.outOrderNo = outOrderNo;
    }

    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    public String getChargeUnit() {
        return chargeUnit;
    }

    public void setChargeUnit(String chargeUnit) {
        this.chargeUnit = chargeUnit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getReductionAmount() {
        return reductionAmount;
    }

    public void setReductionAmount(BigDecimal reductionAmount) {
        this.reductionAmount = reductionAmount;
    }

    public BigDecimal getReductionReceivableAmount() {
        return reductionReceivableAmount;
    }

    public void setReductionReceivableAmount(BigDecimal reductionReceivableAmount) {
        this.reductionReceivableAmount = reductionReceivableAmount;
    }

    public String getTaxPolicy() {
        return taxPolicy;
    }

    public void setTaxPolicy(String taxPolicy) {
        this.taxPolicy = taxPolicy;
    }

    public String getDiscountsPolicy() {
        return discountsPolicy;
    }

    public void setDiscountsPolicy(String discountsPolicy) {
        this.discountsPolicy = discountsPolicy;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
