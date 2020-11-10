package com.jingpai.pos.customer.activity.housemember;

import com.jingpai.pos.customer.bean.HouseHolderBean;
import com.jingpai.pos.customer.bean.HouseHolderListInfoBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/**
 * @author 86173
 */
public class HouseHolderPresenterOld extends BasePresenter_Old {
    //审核详情页面
    public void getHouseHolderInfo(String auditId, Callback<HouseHolderBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.getInfo(auditId), HouseHolderBean.class, callback);
    }

    //审核列表页面
    public void getHouseHolderInfoList(int pageSize, Callback<HouseHolderListInfoBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.getInfoList(pageSize), HouseHolderListInfoBean.class, callback);
    }
    //审核列表页面
    public void applyMember(Map<String, Object> map , Callback<HouseHolderListInfoBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.applyMember(map), HouseHolderListInfoBean.class, callback);
    }
}
