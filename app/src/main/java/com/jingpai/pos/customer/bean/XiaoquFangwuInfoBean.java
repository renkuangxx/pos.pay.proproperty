package com.jingpai.pos.customer.bean;


//获取小区楼栋列表
public class XiaoquFangwuInfoBean {

    public XiaoquFangwuInfoBean(int buildingId, int communityId, int houseId, String roomNo, int unitId) {
        this.buildingId = buildingId;
        this.communityId = communityId;
        this.houseId = houseId;
        this.roomNo = roomNo;
        this.unitId = unitId;
    }
    public XiaoquFangwuInfoBean() {
    }

    /**
     * buildingId : 0
     * communityId : 0
     * houseId : 0
     * roomNo :
     * unitId : 0
     */

    private int buildingId;
    private int communityId;
    private int houseId;
    private String roomNo;
    private int unitId;

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
}
