package com.jingpai.pos.customer.mvp.presenter.show.my;

import com.jingpai.pos.customer.bean.show.CarQueryBean;
import com.jingpai.pos.customer.bean.show.ReportBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/*
 * function:
 */
public class CarPresenter extends BasePresenter_Old {
    //查询车辆
    public void getCarQuery(BasePresenter_Old.Callback<CarQueryBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.carQuery(), CarQueryBean.class, callback);
    }

    public void getCarDelete(Map<String,Object> map, BasePresenter_Old.Callback<ReportBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.carDelete(map), ReportBean.class, callback);
    }

    public void getCarStallQuery(BasePresenter_Old.Callback<CarQueryBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.carQuery(), CarQueryBean.class, callback);
    }

    public void getCarStallDelete(BasePresenter_Old.Callback<CarQueryBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.carQuery(), CarQueryBean.class, callback);
    }



}