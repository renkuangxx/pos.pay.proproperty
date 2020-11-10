package com.jingpai.pos.customer.mvp;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.bean.CommunityByCityNameBean;
import com.jingpai.pos.customer.bean.GetCityInfoBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/*
 * function:
 */
public class YezhuPresenter extends BasePresenter_Old {

    //city name
    public void getCityInfo(Map<String, Object> map, Callback<GetCityInfoBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.getCityInfoByLocation(map), GetCityInfoBean.class, callback);
    }

    //获取小区楼栋列表
    public void getLouList(int id, Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.queryCBuilding(id),  callback);
    }

    //获取小区单元列表
    public void getDanyuanseList(int id, Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.queryCUnit(id),  callback);
    }

    //获取小区房屋列表
    public void getHouseList(int id, Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.queryCHouse(id),  callback);
    }
    //上传家属 租户信息
    public void upYezhuInfo(Map<String, Object> map, Callback<String> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.guestAuthenticate(map),String.class,  callback);
    }
    //上传业主
    public void upYezhuChanInfo(int houseId, Callback<String> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.propertyIsExists(houseId),String.class,  callback);
    }
    //城市列表
    public void upYezhuChanInfo(Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.getCityList(),  callback);
    }

    //城市列表
    public void cityList(Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.getCityList(),  callback);
    }
    //xiaoqu列表
    public void communityByCityName(Map<String, Object> map,Callback<CommunityByCityNameBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.getCommunityByCityName(map), CommunityByCityNameBean.class, callback);
    }
}
