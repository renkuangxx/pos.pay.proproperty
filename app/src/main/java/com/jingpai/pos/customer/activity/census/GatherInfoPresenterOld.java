package com.jingpai.pos.customer.activity.census;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.activity.census.bean.HistoryInfoBean;
import com.jingpai.pos.customer.activity.census.bean.UserInfoAllBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/**
 * @author 86173
 */
public class GatherInfoPresenterOld extends BasePresenter_Old {

    //数据采集
    public void gatherInfo(Map<String, Object> map, Callback callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.gatherInfo(map),Integer.class, callback);
    }
    public void userInfoQuery1(Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.userInfoQuery1(),callback);
    }
    public void userInfoAllQuery(Callback<UserInfoAllBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.userInfoAllQuery(),UserInfoAllBean.class,callback);
    }
    //人口数据 不含跳转
    public void userInfoQuery(Map<String, Object> map, Callback<HistoryInfoBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.userInfoQuery(map), HistoryInfoBean.class,callback);
    }
    //人口数据 不含跳转
    public void deleteInfoQuery(Map<String, Object> map, Callback callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.deleteInfoAllQuery(map),callback);
    }
}
