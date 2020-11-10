package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.bean.FavoriteBean;
import com.jingpai.pos.customer.bean.FavoriteTabBean;
import com.jingpai.pos.customer.bean.LivingBean;
import com.jingpai.pos.customer.bean.SplashBean;
import com.jingpai.pos.customer.bean.show.CommunityServeBean;
import com.jingpai.pos.customer.bean.show.GameTimesResultBean;
import com.jingpai.pos.customer.bean.show.HomeBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

public class HomePresenter extends BasePresenter_Old {


    public void getHome(String cityCode,Callback<HomeBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.home(cityCode), HomeBean.class, callback);
    }

    public void getNewHome(String cityCode,String communityId,Callback<HomeBean> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.newHome(cityCode,communityId, "C_HOME"), HomeBean.class, callback);
    }

    public void getButtonList(String clientType,Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.buttonList(clientType), callback);
    }

    public void getFavorite(Map<String, Object> map,Callback<FavoriteBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.getFavoriteList(map),FavoriteBean.class,  callback);
    }

    public void getLiving(Map<String, Object> map,Callback<LivingBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.getLiving(map),LivingBean.class,  callback);
    }
    public void getFavoriteTab(Map<String, Object> map,Callback<FavoriteTabBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.getFavoriteListNew(map),FavoriteTabBean.class,  callback);
    }

    public void getActivityAdv(Callback<SplashBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.getActivityAdv(),SplashBean.class, callback);
    }

    public void getCommunityServe(Map<String, Object> map,Callback<CommunityServeBean> callback) {
        exeCallbackZongzhi(NetWorkUtil.getInstance().apiService.getCommunityServe(map), CommunityServeBean.class, callback);
    }

    public void getGameTimesMethod(Map<String, Object> map, Callback<GameTimesResultBean> callback) {
        exeCallback4Game(NetWorkUtil.getInstance().apiService.getGameTimes(map),GameTimesResultBean.class, callback);
    }
}
