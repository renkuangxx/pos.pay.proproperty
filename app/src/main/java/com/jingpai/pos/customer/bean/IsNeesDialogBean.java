package com.jingpai.pos.customer.bean;

/**
 * @author 86173
 */
public class IsNeesDialogBean{

    /**
     * data :
     * returnCode :
     * returnMsg :
     * success : true
     */

    private String data;
    private String returnCode;
    private String returnMsg;
    private boolean success;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
