package com.jingpai.pos.customer.bean.house;

import java.util.ArrayList;
import java.util.List;

public class CarPositionBean {
    //"after": {},
    //	"before": "",
    //	"data": [
    //		{
    //			"parking": true,
    //			"parkingId": "",
    //			"parkingNo": "",
    //			"parkingPlace": "",
    //			"parkingType": ""
    //		}
    //	],
    //	"hasMore": true,
    //	"pageNo": 0,
    //	"pageSize": 0

    private String before;
    private boolean hasMore;
    private int pageNo;
    private int pageSize;

    private List<DataBean> data = new ArrayList<>(1);


    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

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
        //"parking": true,
        //			"parkingId": "",
        //			"parkingNo": "",
        //			"parkingPlace": "",
        //			"parkingType": ""

        private boolean parking;
        private String parkingId;
        private String parkingNo;
        private String parkingPlace;
        //车位类型（OWNER:所有权，RENTAL:租赁）
        private String parkingType;

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


}
