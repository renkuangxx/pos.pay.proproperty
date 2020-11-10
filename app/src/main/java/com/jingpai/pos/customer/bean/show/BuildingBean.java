package com.jingpai.pos.customer.bean.show;

import java.util.List;

/**
 * 时间: 2020/2/11
 * 功能:
 */
public class BuildingBean {

    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : [{"communityName":"丰泰花园","houseId":"1227103334456541190","buildNo":"01栋","unitNo":"第一单元","roomNo":"301","type":"OWNER","typeName":"产权人","phone":"18551695587","number":"1"}]
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
         * communityName : 丰泰花园
         * houseId : 1227103334456541190
         * buildNo : 01栋
         * unitNo : 第一单元
         * roomNo : 301
         * type : OWNER
         * typeName : 产权人
         * phone : 18551695587
         * number : 1
         */

        private String communityName;
        private String houseId;
        private String buildNo;
        private String unitNo;
        private String roomNo;
        private String type;
        private String typeName;
        private String phone;
        private String number;
        private int state;
        private String expireTime;//过期时间
        private String auditId;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getAuditId() {
            return auditId;
        }

        public void setAuditId(String auditId) {
            this.auditId = auditId;
        }



        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
        }

        public String getBuildNo() {
            return buildNo;
        }

        public void setBuildNo(String buildNo) {
            this.buildNo = buildNo;
        }

        public String getUnitNo() {
            return unitNo;
        }

        public void setUnitNo(String unitNo) {
            this.unitNo = unitNo;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getHouseName() {
            return getUnitNo() + getRoomNo();
        }
    }
}
