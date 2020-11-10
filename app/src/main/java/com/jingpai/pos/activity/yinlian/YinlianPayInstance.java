package com.jingpai.pos.activity.yinlian;

import android.app.Activity;

import com.ums.AppHelper;
import com.ums.anypay.service.IOnTransEndListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created By：jinheng.liu
 * on 2020/11/9
 */

//包名：com.chinaums.commoncheck；
//        appid: 880ff13a339749dda54efa40cfef3887；
//        appkey: fdb0f0d0065346979993d5e3f1f8995f。

 public class YinlianPayInstance {
    final String  APPID = "880ff13a339749dda54efa40cfef3887";
    String transAppName = "银行卡收款";
    String transAppId = "citylife";

    private static YinlianPayInstance instance;
    public static YinlianPayInstance getInstance() {
        if (instance == null) {
            instance = new YinlianPayInstance();
        }
        return instance;
    }
    IOnTransEndListener listener = new IOnTransEndListener() {
        @Override
        public void onEnd(String reslutmsg) {
            //交易结束后处理数据，此处略
        }
    };


    public void yinlianCollect(Activity activity,String orderNo){
        JSONObject transData = null;
        try {
            transData.put("appId", APPID);//appId
            transData.put("amt", 1);//金额
            transData.put("isNeedPrintReceipt", false);//交易结束后自动打单
            transData.put("extOrderNo", orderNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppHelper.callTrans(activity, "POS 通",  "扫一扫", transData, listener);
    }
}
