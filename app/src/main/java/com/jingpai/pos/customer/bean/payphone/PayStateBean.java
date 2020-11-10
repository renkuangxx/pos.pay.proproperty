package com.jingpai.pos.customer.bean.payphone;

public class PayStateBean {
    //1、待支付；2、支付中；3、支付成功；4、退款成功；5、支付或退款失败',
    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
