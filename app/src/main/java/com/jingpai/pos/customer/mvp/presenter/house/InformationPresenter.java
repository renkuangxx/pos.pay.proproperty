package com.jingpai.pos.customer.mvp.presenter.house;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

public class InformationPresenter extends BasePresenter_Old {

    public void informationPresenter(Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.queryPersonInformation(), callback);
    }

}
