package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

/**
 * 时间: 2020/3/7
 * 功能:
 */
public class ParkingQueryPresenter extends BasePresenter_Old {

    public void getParkingQuery(String communityId,Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.parkingQuery(communityId), callback);
    }

}
