package com.jingpai.pos.presenter.login;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.bean.User;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.LocalCache;

import java.util.HashMap;
import java.util.Map;

public class UserPresenter extends BasePresenter_Old {
    public void login(String phone, String password, Callback<User> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("phone", phone);
        param.put("password", password);
        exeCallback(NetWorkUtil.getInstance().apiService.Login(param), User.class, callback);
    }

    public void getSelectVillage(Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.communityList(),callback);
    }







    public void updateFace(String faceData,  Callback<String> callback) {

        Map<String, Object> param = new HashMap<>();
        param.put("communityId", LocalCache.getCurrentCommunityId());
        param.put("face", faceData);
        param.put("myself", true);
        param.put("name", LocalCache.getUser().getName());

        exeCallback(NetWorkUtil.getInstance().apiService.updateFace(param), String.class, callback);
    }


    public void getUserInfo(Callback<User> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("communityId", LocalCache.getCurrentCommunityId());
        exeCallback(NetWorkUtil.getInstance().apiService.getUserInfo(param), User.class, callback);
    }


}
