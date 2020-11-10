package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.jingpai.pos.customer.bean.show.ReportBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/**
 * 时间: 2020/3/8
 * 功能:
 */
public class AntiepidemicPresenter extends BasePresenter_Old {

    public void getAntiepidemic(Map<String,Object> map, Callback<ReportBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.health(map), ReportBean.class,callback);
    }

}
