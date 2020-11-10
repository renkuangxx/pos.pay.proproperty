package com.jingpai.pos.presenter.house;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.bean.NoPayBillBean;
import com.jingpai.pos.bean.house.SearchByPhoneBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.LocalCache;

import java.util.HashMap;
import java.util.Map;


public class HousePresenter extends BasePresenter {

    public HousePresenter() {
    }

    public void getBuildingList(Callback<JSONArray> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("communityId", LocalCache.getCurrentCommunityId());
        exeCallbackPOS(NetWorkUtil.getInstance().apiService.getCommunityBuilding(param),  callback);
    }

    public void getUnitList(String buildingId, Callback<JSONArray> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("communityId", LocalCache.getCurrentCommunityId());
        param.put("buildingId", buildingId);
        exeCallbackPOS(NetWorkUtil.getInstance().apiService.getCommunityUnit(param),callback);
    }

    public void getRoomList(String buildingId,String unitId, Callback<JSONArray> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("communityId", LocalCache.getCurrentCommunityId());
        param.put("buildingId", buildingId);
        param.put("unitId", unitId);
        exeCallbackPOS(NetWorkUtil.getInstance().apiService.getCommunityRoom(param),callback);
    }
    public void getListByPhone(int areaType,String phone, Callback<SearchByPhoneBean> callback) {
        exeCallbackPOS(NetWorkUtil.getInstance().apiService.getRoomByPhone(areaType,phone),SearchByPhoneBean.class,callback);
    }

    public void getParkList( Callback<JSONArray> callback) {
        String  communityId = LocalCache.getCurrentCommunityId() ;
        exeCallbackPOS(NetWorkUtil.getInstance().apiService.getCommunityPark(communityId),callback);
    }

    public void getShopList( Callback<JSONArray> callback) {
        String  communityId = LocalCache.getCurrentCommunityId() ;
        exeCallbackPOS(NetWorkUtil.getInstance().apiService.getCommunityShop(communityId),callback);
    }

    public void getNoPayBillInfo(String areaId,int areaType, Callback<NoPayBillBean> callback) {
        exeCallbackPOS(NetWorkUtil.getInstance().apiService.getNoPayBillInfo(areaId,areaType), NoPayBillBean.class,callback);
    }
}