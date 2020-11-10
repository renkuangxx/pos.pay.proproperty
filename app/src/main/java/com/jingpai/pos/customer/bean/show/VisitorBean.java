package com.jingpai.pos.customer.bean.show;

/**
 * 时间: 2020/2/13
 * 功能:
 */
public class VisitorBean {

    /**
     * password : FileBean
     * qrCode : string
     * text : string
     */

    private String password;
    private String qrCode;
    private String text;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
