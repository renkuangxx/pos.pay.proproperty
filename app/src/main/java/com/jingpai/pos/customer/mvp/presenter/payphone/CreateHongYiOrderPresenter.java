package com.jingpai.pos.customer.mvp.presenter.payphone;

import com.jingpai.pos.customer.bean.payphone.OrderBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

public class CreateHongYiOrderPresenter extends BasePresenter_Old {

    public void createHongYiOrder(Map<String, Object> map, Callback<OrderBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.createHongYiOrder(map), OrderBean.class, callback);
    }
}
