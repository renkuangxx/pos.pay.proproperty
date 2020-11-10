package com.jingpai.pos.presenter;

import com.jingpai.pos.bean.BillOrderBean;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.LocalCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By：jinheng.liu
 * on 2020/11/6
 */
public class PayPresenter extends BasePresenter{
    public void billOrder(String houseId, ArrayList<String> parkArray, String chargeDate, BasePresenter.Callback<BillOrderBean> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("chargeDate", chargeDate);
        param.put("houseId", houseId);
        param.put("communityId", LocalCache.getCurrentCommunityId());
        if (!parkArray.isEmpty()){
            param.put("parkingIds", parkArray);
        }
        param.put("operatingSystem", "ANDROID");
        param.put("operatorId", LocalCache.getUser().getUserId());
        exeCallbackPOS(NetWorkUtil.getInstance().apiService.billOrder(param), BillOrderBean.class, callback);
    }
}
