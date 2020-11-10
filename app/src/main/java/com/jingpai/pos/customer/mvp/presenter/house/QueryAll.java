package com.jingpai.pos.customer.mvp.presenter.house;

import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/**
 * 时间: 2020/3/1
 * 功能:
 */
public class QueryAll extends BasePresenter_Old {

    public void queryAll(Map<String, Object> map, Callback<String> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.queryAll(map), String.class, callback);
    }
}