package com.jingpai.pos.customer.mvp.presenter.shop;

import com.jingpai.pos.bean.User;
import com.jingpai.pos.customer.bean.shop.MemberPointBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.LocalCache;

public class ShopPresenter extends BasePresenter_Old {

    public void getMemberPoint(Callback<MemberPointBean> callback) {
        User user = LocalCache.getUser();
        if (user != null) {
            exeCallback(NetWorkUtil.getInstance().apiService.getMemberPoint(user.getPhone()), MemberPointBean.class, callback);
        }
    }

    public void getMyDistribution(Callback<String> callback) {
        User user = LocalCache.getUser();
        if (user != null) {
            exeCallback(NetWorkUtil.getInstance().apiService.getMyDistribution(user.getPhone()),String.class,  callback);
        }
    }


}
