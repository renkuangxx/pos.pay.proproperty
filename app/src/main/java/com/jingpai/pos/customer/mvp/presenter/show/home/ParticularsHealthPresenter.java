package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.jingpai.pos.customer.bean.DailyParticularsBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

/*
 * function:
 */
public class ParticularsHealthPresenter extends BasePresenter_Old {

    public void getParticulars(int id,Callback<DailyParticularsBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.healthParticulars(id), DailyParticularsBean.class,callback);
    }

}
