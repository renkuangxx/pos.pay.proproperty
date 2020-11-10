package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/**
 * 时间: 2020/2/29
 * 功能:
 */
public class FeedbackPresenter extends BasePresenter_Old {

    public void getGuardList(Map<String,Object> map, Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.feedback(map), callback);
    }
}
