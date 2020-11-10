package com.jingpai.pos.customer.bean.show;

import java.util.List;

/**
 * 时间: 2020/2/19
 * 功能:
 */
public class ReportTypeBean {

    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : ["卫生","绿化","电梯","乱停","设施","设备","其他"]
     */

    private boolean success;
    private String returnCode;
    private String returnMsg;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
