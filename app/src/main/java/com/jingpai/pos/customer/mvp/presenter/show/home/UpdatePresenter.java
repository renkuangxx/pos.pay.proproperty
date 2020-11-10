package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.jingpai.pos.customer.bean.show.ReportBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/*
 * function:
 */
public class UpdatePresenter extends BasePresenter_Old {
    public void getUpdate(Map<String,Object> map,Callback<ReportBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.update(map), ReportBean.class,callback);
    }
}
