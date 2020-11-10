package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.HashMap;
import java.util.Map;

public class MemberPresenter extends BasePresenter_Old {

    public void queryAll(Callback<JSONArray> callback) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("communityId", LocalCache.getCurrentCommunityId());
        exeCallback(NetWorkUtil.getInstance().apiService.memberQueryAll(param), callback);
    }

    public void queryHouseMember(String houseId, String type, Callback<JSONArray> callback) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("communityId", LocalCache.getCurrentCommunityId());
        param.put("houseId", houseId);
        param.put("operatorType", type);
        exeCallback(NetWorkUtil.getInstance().apiService.queryHouseMember(param), callback);
    }

}
