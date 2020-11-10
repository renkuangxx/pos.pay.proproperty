package com.jingpai.pos.customer.bean;

/**
 * 时间: 2020/3/7
 * 功能:
 */
public class StallBean {

    /**
     * parking : true
     * parkingId : string
     * parkingNo : string
     * parkingPlace : string
     */

    private boolean parking;
    private String parkingId;
    private String parkingNo;
    private String parkingPlace;

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
}
