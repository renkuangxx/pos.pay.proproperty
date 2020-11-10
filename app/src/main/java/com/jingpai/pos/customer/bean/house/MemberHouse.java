package com.jingpai.pos.customer.bean.house;

import com.jingpai.pos.customer.bean.show.MemberBean;

import java.util.List;

public class MemberHouse {

    private String buildNo;

    private String houseId;

    private String roomNo;

    private String unitNo;

    private List<MemberBean> memberList;

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public List<MemberBean> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberBean> memberList) {
        this.memberList = memberList;
    }

    public String getHouseName() {
        return unitNo + roomNo + "室";
    }
}
