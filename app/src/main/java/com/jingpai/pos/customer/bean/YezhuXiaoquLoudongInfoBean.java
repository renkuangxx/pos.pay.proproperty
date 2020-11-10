package com.jingpai.pos.customer.bean;

import java.util.List;

public class YezhuXiaoquLoudongInfoBean {


    /**
     * success : true
     * returnCode : 0
     * returnMsg : success
     * data : [{"buildingId":59,"number":"10号楼","managerId":"735529889381220352"},{"buildingId":60,"number":"11号楼","managerId":"1207944514048622592"},{"buildingId":61,"number":"12号楼","managerId":"735529889381220352"},{"buildingId":62,"number":"13号楼","managerId":"735529889381220352"},{"buildingId":63,"number":"14号楼","managerId":"735529889381220352"},{"buildingId":75,"number":"15号楼","managerId":"735529889381220352"},{"buildingId":50,"number":"1号楼","managerId":"735529889381220352"},{"buildingId":51,"number":"2号楼","managerId":"1207846184845049856"},{"buildingId":52,"number":"3号楼","managerId":"1207944514048622592"},{"buildingId":53,"number":"4号楼"},{"buildingId":54,"number":"5号楼"},{"buildingId":55,"number":"6号楼"},{"buildingId":56,"number":"7号楼"},{"buildingId":57,"number":"8号楼"},{"buildingId":58,"number":"9号楼"},{"buildingId":101,"number":"虚拟楼栋","managerId":"735529889381220352"}]
     */

    private boolean success;
    private String returnCode;
    private String returnMsg;
    private List<data> data;

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

    public List<data> getData() {
        return data;
    }

    public void setData(List<data> data) {
        this.data = data;
    }

    public static class data {
        /**
         * buildingId : 59
         * number : 10号楼
         * managerId : 735529889381220352
         */
        public data(int buildingId, String number, String managerId) {
            this.buildingId = buildingId;
            this.number = number;
            this.managerId = managerId;
        }
        private int buildingId;
        private String number;
        private String managerId;

        public data() {

        }



        public int getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(int buildingId) {
            this.buildingId = buildingId;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getManagerId() {
            return managerId;
        }

        public void setManagerId(String managerId) {
            this.managerId = managerId;
        }
    }
}
