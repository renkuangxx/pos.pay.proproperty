package com.jingpai.pos.bean;

/**
 * Created By：jinheng.liu
 * on 2019/5/31
 */

/**
 * code : 1
 * msg : success
 * result : null
 */
public class BaseBean<T> {
    private boolean success;
    private String returnCode = "0";
    private String returnMsg = "服务器繁忙，请稍后重试";
    private T data ;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
