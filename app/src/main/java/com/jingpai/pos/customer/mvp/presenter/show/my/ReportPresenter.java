package com.jingpai.pos.customer.mvp.presenter.show.my;

import com.jingpai.pos.customer.bean.show.ReportBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/*
 * function:
 */
public class ReportPresenter extends BasePresenter_Old {

    public void getReport(Map<String,Object> map, Callback<ReportBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.report(map), ReportBean.class, callback);
    }

}
