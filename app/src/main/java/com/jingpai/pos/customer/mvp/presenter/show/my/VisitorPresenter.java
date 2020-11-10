package com.jingpai.pos.customer.mvp.presenter.show.my;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.bean.house.CarPositionBean;
import com.jingpai.pos.customer.bean.house.HouseBean;
import com.jingpai.pos.customer.bean.house.VisitHisBean;
import com.jingpai.pos.customer.bean.show.VisitorBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/**
 * 时间: 2020/2/13
 * 功能:
 */
public class VisitorPresenter extends BasePresenter_Old {

    public void getVisitor(Map<String, Object> map, Callback<VisitorBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.visitorRegister(map), VisitorBean.class, callback);
    }

    /**
     * 查询房屋列表-我的房屋
     *
     * @param map
     * @param callback
     */
    public void houseQuery(Map<String, Object> map, Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.houseQuery(map), callback);
    }

    /**
     * 查询房屋列表
     *
     * @param map
     * @param callback
     */
    public void queryHouse(Map<String, Object> map, Callback<HouseBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.queryHouseList(map),HouseBean.class, callback);
    }

    /**
     * 根据房屋查询车位列表
     *
     * @param map
     * @param callback
     */
    public void queryParking(Map<String, Object> map, Callback<CarPositionBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.queryParking(map), CarPositionBean.class,callback);
    }

    /**
     * 来访记录
     *
     * @param map
     * @param callback
     */
    public void visitorRecord(Map<String, Object> map, Callback<VisitHisBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.visitorRecord(map), VisitHisBean.class,callback);
    }

    /**
     * 访客-来访记录-记录详情
     *
     * @param map
     * @param callback
     */
    public void visitorRecordDetail(Map<String, Object> map, Callback<VisitHisBean.DataBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.visitorRecordDetail(map), VisitHisBean.DataBean.class,callback);
    }

    /**
     * 访客-访客车位申请审批-同意
     *
     * @param map
     * @param callback
     */
    public void parkingApplyPass(Map<String, Object> map, Callback callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.parkingApplyPass(map), callback);
    }
    /**
     * 访客-访客车位申请审批-拒绝
     *
     * @param map
     * @param callback
     */
    public void parkingApplyReject(Map<String, Object> map, Callback callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.parkingApplyReject(map),callback);
    }
    /**
     * 访客-访客车位-取消邀请
     *
     * @param map
     * @param callback
     */
    public void parkingApplyCancel(Map<String, Object> map, Callback callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.parkingApplyCancel(map),callback);
    }
    /**
     * 访客-访客车位-撤销停车申请
     *
     * @param map
     * @param callback
     */
    public void parkingApplyCancelParking(Map<String, Object> map, Callback callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.parkingApplyCancelParking(map),callback);
    }
    /**
     * 访客-访客车位-撤销停车申请授权
     *
     * @param map
     * @param callback
     */
    public void parkingApplyCancelParkingAuth(Map<String, Object> map, Callback callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.parkingApplyCancelParkingAuth(map),callback);
    }




}
