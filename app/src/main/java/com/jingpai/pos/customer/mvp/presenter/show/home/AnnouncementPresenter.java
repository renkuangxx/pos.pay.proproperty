package com.jingpai.pos.customer.mvp.presenter.show.home;

import com.jingpai.pos.customer.bean.show.AnnouncementBean;
import com.jingpai.pos.customer.bean.show.MessageDetailBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 时间: 2020/2/24
 * 功能:
 */
public class AnnouncementPresenter extends BasePresenter_Old {

    public void message(int before, int pageSize,Callback<AnnouncementBean.DataBeanX> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.message(before,pageSize),AnnouncementBean.DataBeanX.class,callback);
    }

    public void messageDetail(int messageId,Callback<MessageDetailBean> callback) {

        Map<String,Integer> map=new HashMap<>();
        map.put("messageId",messageId);
        exeCallback(NetWorkUtil.getInstance().apiService.messageDetail(map),MessageDetailBean.class,callback);
    }
}
