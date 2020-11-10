package com.jingpai.pos.customer.bean.house;

import java.util.ArrayList;

public class RoomBean {

    private String buildIngName;

    private String buildIngNo;

    private String orgCode;

    private String orgName;

    private String roomName;

    private String roomNo;

    private String house;//房屋
    private String houseId;//房屋
    private String name;//业主
    private ArrayList<ParkingBean> parking;//车位

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ParkingBean> getParking() {
        return parking;
    }

    public void setParking(ArrayList<ParkingBean> parking) {
        this.parking = parking;
    }

    public String getBuildIngName() {
        return buildIngName;
    }

    public void setBuildIngName(String buildIngName) {
        this.buildIngName = buildIngName;
    }

    public String getBuildIngNo() {
        return buildIngNo;
    }

    public void setBuildIngNo(String buildIngNo) {
        this.buildIngNo = buildIngNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
}
