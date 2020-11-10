package com.jingpai.pos.customer.bean.show;

import java.util.List;

/**
 * 时间: 2020/2/23
 * 功能:
 */
public class GuardListBean {


    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : [{"id":"1230335144615362561","unitId":11,"name":"单元门","unit":"第一单元","building":"1"}]
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
         * id : 1230335144615362561
         * unitId : 11
         * name : 单元门
         * unit : 第一单元
         * building : 1
         */

        private String id;
        private int unitId;
        private String name;
        private String unit;
        private String building;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getUnitId() {
            return unitId;
        }

        public void setUnitId(int unitId) {
            this.unitId = unitId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }
    }
}
