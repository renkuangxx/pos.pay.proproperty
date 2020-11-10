package com.jingpai.pos.customer.mvp.presenter.payment;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.bean.common.PageBefore;
import com.jingpai.pos.bean.Apply4InvoiceBean;
import com.jingpai.pos.bean.BillOrderBean;
import com.jingpai.pos.bean.PayBean;
import com.jingpai.pos.bean.PayResultBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentPresenter extends BasePresenter_Old {

    public void queryBill(String houseId, ArrayList<String> parkArray,  Callback<JSONArray> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("houseId", houseId);
        if (!parkArray.isEmpty()){
            param.put("parkingIds", parkArray);
        }
        exeCallback(NetWorkUtil.getInstance().apiService.queryBill(param),  callback);
    }

    public void queryBillHistory(String before,int pageSize , Callback<PageBefore> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("before", before);
        param.put("pageSize", pageSize);
        exeCallback(NetWorkUtil.getInstance().apiService.queryBillHistory(param),PageBefore.class,callback);
    }

    public void queryBillDetail(String orderId, Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.queryBillDetail(orderId),callback);
    }


    public void queryRoom(Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.queryRoom(LocalCache.getCurrentCommunityId()), callback);
    }
    public void billOrder(String roomNo, Callback<BillOrderBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.billOrder(roomNo), BillOrderBean.class, callback);
    }
    public void billOrder(String houseId, ArrayList<String> parkArray,String chargeDate, Callback<BillOrderBean> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("chargeDate", chargeDate);
        param.put("houseId", houseId);
        param.put("communityId", LocalCache.getCurrentCommunityId());
        if (!parkArray.isEmpty()){
            param.put("parkingIds", parkArray);
        }
        param.put("operatingSystem", "ANDROID");
        param.put("operatorId", LocalCache.getUser().getUserId());
        exeCallback(NetWorkUtil.getInstance().apiService.billOrder(param), BillOrderBean.class, callback);
    }


    public void pay(BillOrderBean billOrderBean,int deteleFen, Callback<PayBean> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("orderNo", billOrderBean.getOrderNo());
        param.put("payType", billOrderBean.getType());
        param.put("tradeAmt", billOrderBean.getAmount());
        param.put("integral",deteleFen);
        exeCallback(NetWorkUtil.getInstance().apiService.pay(param), PayBean.class, callback);
    }
    public void pay(BillOrderBean billOrderBean,int deteleFen,String authCode, Callback<PayBean> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("orderNo", billOrderBean.getOrderNo());
        param.put("payType", billOrderBean.getType());
        param.put("tradeAmt", billOrderBean.getAmount());
        param.put("integral",deteleFen);
        param.put("authCode",authCode);
        exeCallback(NetWorkUtil.getInstance().apiService.pay(param), PayBean.class, callback);
    }

    public void queryAliPayState(String orderNo, Callback<PayResultBean> callback) {
        Map<String, Object> param = new HashMap<>();
        param.put("tradeNo", orderNo);
        exeCallback(NetWorkUtil.getInstance().apiService.queryAliPayState(param), PayResultBean.class, callback);
    }

    public void apply4Invoice(Map<String, Object> param, Callback<Apply4InvoiceBean> callback){
        exeCallback(NetWorkUtil.getInstance().apiService.queryApply4Invoice(param), Apply4InvoiceBean.class, callback);
    }

}
