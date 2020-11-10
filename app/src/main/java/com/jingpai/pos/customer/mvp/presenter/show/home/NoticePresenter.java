package com.jingpai.pos.customer.mvp.presenter.show.home;


import com.jingpai.pos.bean.common.PageBefore;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.HashMap;
import java.util.Map;

public class NoticePresenter extends BasePresenter_Old {

    public void queryNotice(String before, Callback<PageBefore> callback) {

        Map<String, Object> param = new HashMap<>();
        param.put("before", before);
        param.put("communityId", LocalCache.getCurrentCommunityId());
        param.put(PAGE_SIZE_PARAM, PAGE_SIZE_VALUE);

        exeCallback(NetWorkUtil.getInstance().apiService.queryNotice(param), PageBefore.class, callback);
    }

}
