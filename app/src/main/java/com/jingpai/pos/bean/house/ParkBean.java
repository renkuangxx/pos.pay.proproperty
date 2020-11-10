package com.jingpai.pos.bean.house;

import java.io.Serializable;

public class ParkBean implements Serializable {
//                        "createBy": "",
//                        "createTime": "",
//                        "delState": 0,
//                        "feeDeadline": "",
//                        "feeType": "",
//                        "modifyTime": "",
//                        "phone": "",
//                        "remark": "",
//                        "serviceCost": "",
//                        "sort": 0,
//                        "toolId": "",
//                        "updateBy": "",
//                        "updateTime": "",
//                        "userId": "",
//                        "version": 0


    private String id;
    private boolean parking;
    private String parkingId;
    private String communityId;
    private String communityName;
    private String name;
    private String parkingNo;
    private String parkingPlace;
    private int parkingState;
    private int parkingType;
    private String proprietorName;
    private String repairFee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getParkingState() {
        return parkingState;
    }

    public void setParkingState(int parkingState) {
        this.parkingState = parkingState;
    }

    public int getParkingType() {
        return parkingType;
    }

    public void setParkingType(int parkingType) {
        this.parkingType = parkingType;
    }

    public String getProprietorName() {
        return proprietorName;
    }

    public void setProprietorName(String proprietorName) {
        this.proprietorName = proprietorName;
    }

    public String getRepairFee() {
        return repairFee;
    }

    public void setRepairFee(String repairFee) {
        this.repairFee = repairFee;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }
}
