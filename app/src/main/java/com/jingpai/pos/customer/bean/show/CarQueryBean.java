package com.jingpai.pos.customer.bean.show;

import java.util.List;

/*
 * function:
 */
public class CarQueryBean {

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
         * id : 34
         * plateNumber : 京A888888
         * createTime : 2020-01-12 11:48:56
         */

        private String id;
        private String plateNumber;
        private String createTime;
        private String enterTime;
        private String leaveTime;
        //是否是访客
        private boolean visitorCar;
        private int visitorRegistrationId;

        public String getEnterTime() {
            return enterTime;
        }

        public void setEnterTime(String enterTime) {
            this.enterTime = enterTime;
        }

        public String getLeaveTime() {
            return leaveTime;
        }

        public void setLeaveTime(String leaveTime) {
            this.leaveTime = leaveTime;
        }

        public boolean isVisitorCar() {
            return visitorCar;
        }

        public void setVisitorCar(boolean visitorCar) {
            this.visitorCar = visitorCar;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getVisitorRegistrationId() {
            return visitorRegistrationId;
        }

        public void setVisitorRegistrationId(int visitorRegistrationId) {
            this.visitorRegistrationId = visitorRegistrationId;
        }
    }
}
