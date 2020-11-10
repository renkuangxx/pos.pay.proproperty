package com.jingpai.pos.bean;

import com.jingpai.pos.bean.house.ParkBean;
import com.jingpai.pos.customer.bean.house.PayItemBean;

import java.io.Serializable;
import java.util.List;

public class NoPayBillBean implements Serializable {

    private String house;
    private String houseId;
    private String name;

    private List<ParkBean> parking;
    private List<PayItemBean> unpaidBillList;

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

    public List<ParkBean> getParking() {
        return parking;
    }

    public void setParking(List<ParkBean> parking) {
        this.parking = parking;
    }

    public List<PayItemBean> getUnpaidBillList() {
        return unpaidBillList;
    }

    public void setUnpaidBillList(List<PayItemBean> unpaidBillList) {
        this.unpaidBillList = unpaidBillList;
    }
}
