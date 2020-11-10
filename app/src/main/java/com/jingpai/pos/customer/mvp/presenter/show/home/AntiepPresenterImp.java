package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

public class AntiepPresenterImp extends BasePresenter_Old {

    public void AntiepData(Map<String, Object> map, Callback<String> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.health(map), String.class, callback);
    }

}