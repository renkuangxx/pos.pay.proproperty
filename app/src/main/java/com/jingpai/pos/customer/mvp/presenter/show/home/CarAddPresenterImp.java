package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

public class CarAddPresenterImp extends BasePresenter_Old {

    /**
     * 添加车辆
     *
     * @param map
     * @param callback
     */
    public void CarAddData(Map<String, Object> map, Callback callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.carAdd(map), callback);
    }

    /**
     * 查看车位列表
     *
     * @param callback
     */
    public void carAddChoseStall(Callback<JSONArray> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.carAddChoseStall(), callback);
    }
}
