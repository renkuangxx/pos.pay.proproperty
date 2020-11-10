package com.jingpai.pos.customer.bean.house;

import com.jingpai.pos.customer.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class HouseBean {

    private int pageNo;
    private int pageSize;
    private List<DataBean> data = new ArrayList<>(1);

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        //area
        //房屋面积
        //string
        //
        //building
        //
        //string
        //
        //buildingId
        //楼栋id
        //integer(int64)
        //
        //communityId
        //小区id
        //integer(int64)
        //
        //createTime
        //创建时间
        //string(date-time)
        //
        //delState
        //删除状态
        //boolean
        //
        //id
        //id
        //integer(int64)
        //
        //modifyTime
        //修改时间
        //string(date-time)
        //
        //phone
        //业主手机号
        //string
        //
        //roomNo
        //房间号
        //string
        //
        //self
        //是否业主
        //boolean
        //
        //unit
        //
        //string
        //
        //unitId
        //单元id
        //integer(int64)

        private String houseId;
        private int id;
        private int communityId;
        private String phone;
        private int buildingId;
        private int unitId;
        private String roomNo="";
        private String area;
        private boolean delState;
        private String createTime;
        private String modifyTime;
        private String unit="";
        private String building="";
        private String buildNo="";
        private String unitNo="";
        private boolean self;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommunityId() {
            return communityId;
        }

        public void setCommunityId(int communityId) {
            this.communityId = communityId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(int buildingId) {
            this.buildingId = buildingId;
        }

        public int getUnitId() {
            return unitId;
        }

        public void setUnitId(int unitId) {
            this.unitId = unitId;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public boolean isDelState() {
            return delState;
        }

        public void setDelState(boolean delState) {
            this.delState = delState;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
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

        public boolean isSelf() {
            return self;
        }

        public void setSelf(boolean self) {
            this.self = self;
        }

        public String getHouseId() {
            return houseId;
        }

        public void setHouseId(String houseId) {
            this.houseId = houseId;
            try {
                setId(Integer.parseInt(houseId));
            }catch (Exception e){
                LogUtil.e("HouseBean",e.getMessage());
            }
        }

        public String getBuildNo() {
            return buildNo;
        }

        public void setBuildNo(String buildNo) {
            this.buildNo = buildNo;
            setBuilding(buildNo);
        }

        public String getUnitNo() {
            return unitNo;
        }

        public void setUnitNo(String unitNo) {
            this.unitNo = unitNo;
            setUnit(unitNo);
        }
    }

}
