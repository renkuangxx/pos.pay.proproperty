package com.jingpai.pos.customer.bean.house;

/**
 * Create by liujinheng
 * date 2020/5/27
 * function
 */
public class ParkingBean {
    private boolean parking;//是否车位（true:是，false:否）
    private String parkingId;//车位主键标识
    private String parkingNo;//车位编号
    private String parkingPlace;//车位地址
    private String parkingType;//车位类型（OWNER:所有权，RENTAL:租赁）

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

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }
}
