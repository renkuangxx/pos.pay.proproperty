package com.jingpai.pos.customer.mvp.presenter.payphone;

import com.jingpai.pos.customer.bean.payphone.PayStateBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

public class GetPayFeeStatePresenter extends BasePresenter_Old {

    public void getState(Map<String,String> map,Callback<PayStateBean> callback){
        exeCallback(NetWorkUtil.getInstance().apiService.getHongYiPayStateByOrderNo(map),PayStateBean.class,callback);
    }

}
