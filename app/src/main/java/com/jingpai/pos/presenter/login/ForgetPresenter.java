package com.jingpai.pos.presenter.login;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;


public class ForgetPresenter extends BasePresenter_Old {

    public ForgetPresenter() {
    }

    public void forgetData(Map<String, Object> map, Callback<String> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.GetBackPassword(map), String.class, callback);
    }

    //验证码
    public void verificationRequest(Map<String, Object> map, Callback<String> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.Verification(map),String.class,callback);
    }

    public void getUpdatePassword(Map<String,Object> map, Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.updatePassword(map),callback);
    }
}