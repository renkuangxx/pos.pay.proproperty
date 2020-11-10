package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/*
 * function:
 */public class CarQueryPresenterImp extends BasePresenter_Old {

    /**
     * 车辆列表
     * @param callback
     */
    public void CarQueryData(Callback<JSONArray> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.carQuery(), callback);
    }

    public void carDeleteData(Map<String, Object> map, Callback callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.carDelete(map), callback);
    }

    public void carStallDeleteData(Map<String, Object> map, Callback callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.delete(map), callback);
    }

    /**
     * 车位列表
     * @param communityId
     * @param callback
     */
    public void carStallquery(String communityId, Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.parkingQuery(communityId), callback);
    }

    /**
     * 车辆进入记录
     * @param plateNumber
     * @param callback
     */
    public void accessRecords(String plateNumber, Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.accessRecords(plateNumber), callback);
    }
}
