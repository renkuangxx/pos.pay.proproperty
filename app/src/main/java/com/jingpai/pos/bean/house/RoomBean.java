package com.jingpai.pos.bean.house;

public class RoomBean {
    private String area;//面积
    private String id;
    private String propertyCost;//物业费
    private String repairFee;//房屋公维金
    private String roomNo;//房间号

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropertyCost() {
        return propertyCost;
    }

    public void setPropertyCost(String propertyCost) {
        this.propertyCost = propertyCost;
    }

    public String getRepairFee() {
        return repairFee;
    }

    public void setRepairFee(String repairFee) {
        this.repairFee = repairFee;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
}
