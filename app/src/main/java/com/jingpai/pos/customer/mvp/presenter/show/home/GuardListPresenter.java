package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

/**
 * 时间: 2020/2/23
 * 功能:
 */
public class GuardListPresenter extends BasePresenter_Old {

    public void getGuardList(String communityId,Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.accessList(communityId), callback);
    }
}
