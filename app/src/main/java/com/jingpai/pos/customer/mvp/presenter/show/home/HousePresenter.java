package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/*
 * function:
 */
public class HousePresenter extends BasePresenter_Old {

    public void getHouse(Map<String,Object> map,Callback<JSONArray> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.houseQuery(map), callback);
    }

}
