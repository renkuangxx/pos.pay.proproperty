package com.jingpai.pos.customer.mvp.presenter.show.my;

import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.HashMap;
import java.util.Map;

public class SexChangePresenterImp extends BasePresenter_Old {

    public void modifySex(String sex, Callback callback) {
        Map<String, Object> map=new HashMap<>();
        map.put("sex",sex);
        exeCallback(NetWorkUtil.getInstance().apiService.modifySex(map), callback);
    }
}