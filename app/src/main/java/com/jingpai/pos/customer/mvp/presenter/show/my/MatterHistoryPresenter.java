package com.jingpai.pos.customer.mvp.presenter.show.my;

import com.jingpai.pos.customer.bean.MatterHistoryListBean;
import com.jingpai.pos.customer.bean.show.MatterDetailBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/*
 * function:
 */
public class MatterHistoryPresenter extends BasePresenter_Old {
    public void matterHistoryData(Map<String, Object> map, Callback<MatterHistoryListBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.reportHistory(map), MatterHistoryListBean.class,  callback);
    }

    public void reportUrging(String id, Callback<String> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.reportUrging(id), String.class,  callback);
    }

    public void reportCancel(String id, Callback<String> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.cancelMatter(id), String.class,  callback);
    }

    public void getMatterDetail(String id, Callback<MatterDetailBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.matterDetail(id), MatterDetailBean.class, callback);
    }

    public void evaluateMatter(Map<String, Object> map, Callback<String> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.evaluateMatter(map), String.class, callback);
    }



}
