package com.jingpai.pos.customer.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.jingpai.pos.BuildConfig;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.bean.User;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class OpenWxUtil {
    public static void wxPay(Context context, String orderNo, int tradeAmt, String businessType) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, Constant.WEIXIN_APP_ID);//小程序appid
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(context, "您还没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        User user = LocalCache.getUser();
        StringBuilder path = new StringBuilder();
        path.append("pages/index/index?");
        path.append("orderNo=" + orderNo);
        path.append("&businessType=" + businessType);
        path.append("&tradeAmt=" + tradeAmt);
        path.append("&userMobile=" + user.getPhone());
        path.append("&userName=" + user.getName());
        req.userName = Constant.WX_USER_NAME;//小程序ID
        req.path = path.toString();
        req.miniprogramType = BuildConfig.MINIPTOGRAM_TYPE;
        iwxapi.sendReq(req);
    }

    /**
     * webview中使用
     * @param context
     * @param orderNo
     * @param tradeAmt
     * @param businessType
     */
    public static void wxPay(Context context, String orderNo, int tradeAmt, String businessType, String token) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, Constant.WEIXIN_APP_ID);//小程序appid
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(context, "您还没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        User user = LocalCache.getUser();
        StringBuilder path = new StringBuilder();
        path.append("pages/index/index?");
        path.append("orderNo=" + orderNo);
        path.append("&businessType=" + businessType);
        path.append("&tradeAmt=" + tradeAmt);
        path.append("&userMobile=" + user.getPhone());
        path.append("&userName=" + user.getName());
        path.append("&token=" +token);
        req.userName = Constant.WX_USER_NAME;//小程序ID
        req.path = path.toString();
        req.miniprogramType = BuildConfig.MINIPTOGRAM_TYPE;
        iwxapi.sendReq(req);
    }

    public static void openWX(Context context){
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage("com.tencent.mm");
        context.startActivity(intent);
    }
}
