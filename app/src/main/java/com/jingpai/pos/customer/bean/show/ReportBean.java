package com.jingpai.pos.customer.bean.show;

/**
 * 时间: 2020/2/11
 * 功能:
 */
public class ReportBean {

    private boolean success;
    private String returnCode;
    private String returnMsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

}
