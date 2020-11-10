package com.jingpai.pos.customer.bean;

import com.jingpai.pos.customer.activity.census.bean.CommunityBean;

import java.util.List;

public class CityCommunityBean {
    private String cityCode;
    private String cityName;
    private List<CommunityBean> communityList;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<CommunityBean> getCommunityList() {
        return communityList;
    }

    public void setCommunityList(List<CommunityBean> communityList) {
        this.communityList = communityList;
    }
}
