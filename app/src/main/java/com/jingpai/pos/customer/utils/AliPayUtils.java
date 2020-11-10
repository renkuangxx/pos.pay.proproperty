package com.jingpai.pos.customer.utils;

import android.app.Activity;
import android.os.Bundle;

import com.alipay.sdk.app.OpenAuthTask;
import com.jingpai.pos.BuildConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By：jinheng.liu
 * on 2020/9/7
 */
public class AliPayUtils {
    /**
     * 通用跳转授权业务 Demo
     */
    public static void openAuthScheme (Activity activity, OpenAuthTask.Callback openAuthCallback)   {
        // 传递给支付宝应用的业务参数
        final Map<String, String> bizParams =  new HashMap<>();
        bizParams.put( "url" ,  "https://authweb.alipay.com/auth?auth_type=PURE_OAUTH_SDK&app_id="+ BuildConfig.APP_ID_ZFB+"&scope=auth_base&state=init" );

        // 支付宝回跳到你的应用时使用的 Intent Scheme。请设置为不和其它应用冲突的值。
        // 请不要像 Demo 这样设置为 __alipaysdkdemo__!
        // 如果不设置，将无法使用 H5 中间页的方法(OpenAuthTask.execute() 的最后一个参数)回跳至
        // 你的应用。
        //
        // 注意！参见 AndroidManifest 中 <AlipayResultActivity> 的 android:scheme，此两处
        // 必须设置为相同的值。
        final  String scheme =  "jingpaipay" ;

        // 唤起授权业务
        final OpenAuthTask task =  new  OpenAuthTask( activity );
        task.execute(
                scheme,  // Intent Scheme
                OpenAuthTask.BizType.AccountAuth,  // 业务类型
                bizParams,  // 业务参数
                openAuthCallback,  // 业务结果回调。注意：此回调必须被你的应用保持强引用
                true );  // 是否需要在用户未安装支付宝 App 时，使用 H5 中间页中转。建议设置为 true。
    }

    private   static  String  bundleToString (Bundle bundle)   {
        if  (bundle ==  null ) {
            return   "null" ;
        }
        final  StringBuilder sb =  new  StringBuilder();
        for  (String key: bundle.keySet()) {
            sb.append(key).append( "=>" ).append(bundle.get(key)).append( "\n" );
        }
        return  sb.toString();
    }
}
