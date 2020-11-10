package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 时间: 2020/2/23
 * 功能:
 */
public class OpenDoorPresenter extends BasePresenter_Old {
    public void getOpenDoor(String id,Callback<String> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        exeCallback(NetWorkUtil.getInstance().apiService.accessOpenDoor(param), String.class, callback);
    }
}