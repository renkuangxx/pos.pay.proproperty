package com.jingpai.pos.customer.mvp.presenter.show.my;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/**
 * 时间: 2020/3/8
 * 功能:
 */
public class StallDeletePresenter extends BasePresenter_Old {

    public void getDelete(Map<String,Object> map, Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.delete(map),callback);
    }

}
