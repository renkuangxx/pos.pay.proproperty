package com.jingpai.pos.customer.bean;


//获取小区楼栋列表
public class XiaoquDanyuanInfoBean {
    public XiaoquDanyuanInfoBean(int buildingId, int communityId, String name, int unitId) {
        this.buildingId = buildingId;
        this.communityId = communityId;
        this.name = name;
        this.unitId = unitId;
    }


    public XiaoquDanyuanInfoBean() {
    }

    /**
     * buildingId : 0
     * communityId : 0
     * name :
     * unitId : 0
     */

    private int buildingId;
    private int communityId;
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
}
