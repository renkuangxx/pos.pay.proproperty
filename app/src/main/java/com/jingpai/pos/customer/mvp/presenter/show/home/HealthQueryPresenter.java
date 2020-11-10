package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.jingpai.pos.customer.bean.DailyRegistrationBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

/**
 * 时间: 2020/3/9
 * 功能:
 */
public class HealthQueryPresenter extends BasePresenter_Old {
    public void HealthData(String before, String pageSize, Callback<DailyRegistrationBean.DataBeanX> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.healthQuery(before, pageSize), DailyRegistrationBean.DataBeanX.class, callback);
    }
}