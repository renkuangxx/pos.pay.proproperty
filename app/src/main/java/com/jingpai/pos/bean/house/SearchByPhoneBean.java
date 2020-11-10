package com.jingpai.pos.bean.house;

import java.util.ArrayList;

/**
 * Created By：jinheng.liu
 * on 2020/11/10
 */
public class SearchByPhoneBean {
    private ArrayList<RoomBean> houseSimpleList;
    private ArrayList<ParkBean> parkingEntityList;
    private ArrayList<ShopBean> shopResultVOList;

    public ArrayList<RoomBean> getHouseSimpleList() {
        return houseSimpleList;
    }

    public void setHouseSimpleList(ArrayList<RoomBean> houseSimpleList) {
        this.houseSimpleList = houseSimpleList;
    }

    public ArrayList<ParkBean> getParkingEntityList() {
        return parkingEntityList;
    }

    public void setParkingEntityList(ArrayList<ParkBean> parkingEntityList) {
        this.parkingEntityList = parkingEntityList;
    }

    public ArrayList<ShopBean> getShopResultVOList() {
        return shopResultVOList;
    }

    public void setShopResultVOList(ArrayList<ShopBean> shopResultVOList) {
        this.shopResultVOList = shopResultVOList;
    }
}
