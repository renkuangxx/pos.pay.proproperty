package com.jingpai.pos.customer.bean;

import java.util.List;

/**
 * 时间: 2020/3/8
 * 功能:
 */
public class CarStallBean {


    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : [{"parkingId":"1233315367686492196","parkingNo":"－1F-35","parkingPlace":"地下室-1层","parking":true}]
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
         * parkingId : 1233315367686492196
         * parkingNo : －1F-35
         * parkingPlace : 地下室-1层
         * parking : true
         */

        private String parkingId;
        private String parkingNo;
        private String parkingPlace;
        //车位类型（OWNER:所有权，RENTAL:租赁）
        private String parkingType;
        private boolean parking;


        public String getParkingId() {
            return parkingId;
        }

        public void setParkingId(String parkingId) {
            this.parkingId = parkingId;
        }

        public String getParkingNo() {
            return parkingNo;
        }

        public void setParkingNo(String parkingNo) {
            this.parkingNo = parkingNo;
        }

        public String getParkingPlace() {
            return parkingPlace;
        }

        public void setParkingPlace(String parkingPlace) {
            this.parkingPlace = parkingPlace;
        }

        public boolean isParking() {
            return parking;
        }

        public void setParking(boolean parking) {
            this.parking = parking;
        }

        public String getParkingType() {
            return parkingType;
        }

        public void setParkingType(String parkingType) {
            this.parkingType = parkingType;
        }
    }
}
