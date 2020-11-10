package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.activity.census.bean.CommunityBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

public class CommunityPresenter extends BasePresenter_Old {

    public void getSelectVillage(Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.communityList(),callback);
    }

    public void getCommunityGroudByCity(Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.communityListByCity(),callback);
    }

    public void getNearestCommunity(String lat,String lon,Callback<CommunityBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.communityNearest(lat,lon), CommunityBean.class,callback);
    }

}
