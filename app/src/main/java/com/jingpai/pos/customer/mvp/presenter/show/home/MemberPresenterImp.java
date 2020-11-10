package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

public class MemberPresenterImp extends BasePresenter_Old {

    public void HouseQueryData(Map<String, Object> map, Callback<JSONArray> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.houseQuery(map),  callback);
    }

    public void MemberAdd(Map<String, Object> map, Callback callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.memberAdd(map), callback);
    }

    public void MemberUpdate(Map<String, Object> map, Callback callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.memberUpdate(map), callback);
    }

    public void MemberDelete(Map<String, Object> map, Callback callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.memberDelete(map), callback);
    }

    //验证是否需要弹窗
    public void needDialog(Map<String, Object> map, Callback<String> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.isNeedDialog(map), String.class,callback);
    }
    //弹窗信息提交
    public void updataDialogInfo(Map<String, Object> map, Callback<String> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.upDataDialogInfo(map), String.class,callback);
    }

}
