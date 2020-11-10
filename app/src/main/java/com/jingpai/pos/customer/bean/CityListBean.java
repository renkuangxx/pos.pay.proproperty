package com.jingpai.pos.customer.bean;

import java.util.List;

public class CityListBean {

    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : [{"cityCode":"3502","cityName":"厦门市"},{"cityCode":"6101","cityName":"西安市"}]
     */

    private boolean success;
    private String returnCode;
    private String returnMsg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cityCode : 3502
         * cityName : 厦门市
         */

        private String cityCode;
        private String cityName;

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }
}
