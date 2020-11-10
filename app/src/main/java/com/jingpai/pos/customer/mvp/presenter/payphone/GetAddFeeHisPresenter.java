package com.jingpai.pos.customer.mvp.presenter.payphone;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

public class GetAddFeeHisPresenter extends BasePresenter_Old {

    public void getAddFeeHisList(Map<String, String> map, Callback<JSONArray> callback){
        exeCallback(NetWorkUtil.getInstance().apiService.getOrdersByMobile(map),callback);
    }

}
