package com.jingpai.pos.customer.mvp.presenter.show;

import com.jingpai.pos.customer.bean.DailyParticularsBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

/*
 * function:
 */
public class LastSubmitPresenter extends BasePresenter_Old {
    public void getLastSubmit(BasePresenter_Old.Callback<DailyParticularsBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.last(), DailyParticularsBean.class,callback);
    }
}
