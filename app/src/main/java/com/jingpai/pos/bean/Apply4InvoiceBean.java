package com.jingpai.pos.bean;

import java.math.BigDecimal;
import java.util.List;

public class Apply4InvoiceBean {
    private BigDecimal allAccount;
    List<ChargeOrderDetailListVo> chargeOrderDetailList;

    public BigDecimal getAllAccount() {
        return allAccount;
    }

    public void setAllAccount(BigDecimal allAccount) {
        this.allAccount = allAccount;
    }

    public List<ChargeOrderDetailListVo> getChargeOrderDetailList() {
        return chargeOrderDetailList;
    }

    public void setChargeOrderDetailList(List<ChargeOrderDetailListVo> chargeOrderDetailList) {
        this.chargeOrderDetailList = chargeOrderDetailList;
    }
}
