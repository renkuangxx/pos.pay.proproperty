package com.jingpai.pos.customer.mvp.presenter.show.my;

import com.jingpai.pos.customer.bean.show.BuildingBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/*
 * function:
 */
public class HouseQueryPresenter extends BasePresenter_Old {

    public void getHouseQuery(Map<String,Object> map, BasePresenter_Old.Callback<BuildingBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.houseQuery(map), BuildingBean.class, callback);
    }

}
