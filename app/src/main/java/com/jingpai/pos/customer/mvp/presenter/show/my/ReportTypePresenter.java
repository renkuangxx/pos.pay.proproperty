package com.jingpai.pos.customer.mvp.presenter.show.my;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.bean.show.ReportBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;
import java.util.TreeMap;

/**
 * 时间: 2020/2/13
 * 功能:
 */
public class ReportTypePresenter extends BasePresenter_Old {

    public void ReportTypeDate(Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.reportType(),  callback);
    }

    public void HouseQuery(Map<String, Object> map, Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.houseQuery(map), callback);

    }

    public void ReportDate(TreeMap<String, Object> map, Callback<ReportBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.report(map), ReportBean.class, callback);
    }
}
