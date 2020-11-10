package com.jingpai.pos.customer.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.jingpai.pos.activity.MainActivity;
import com.jingpai.pos.utils.ToastUtils;

/**
 * Created By：jinheng.liu
 * on 2020/9/22
 */
public class IconUtils {
    private static String startPath = "com.jingling.citylife.customer.StartActivity";
    private static String normalPath = "com.jingling.citylife.customer.NormalActivity";
    private static String shuangjiePath = "com.jingling.citylife.customer.ShuangJieActivity";

    public static void initIcon(MainActivity activity) {
        try {
            ComponentName startComponent = new ComponentName(activity, startPath);
            ComponentName normalComponent = new ComponentName(activity, normalPath);
//            ComponentName shuangjieComponent = new ComponentName(activity, shuangjiePath);
//            if(DateUtil.afterDay("2020-9-30")&&DateUtil.beforeDay("2020-10-16")){
//                if (!TextUtils.equals("shuangjie",LocalCache.getValue("appLogo"))){
//                    LocalCache.putValue("appLogo","shuangjie");
//                    ToastUtils.INSTANCE.showToast("因图标更换，请重新启动APP");
//                }
//                disableComponent(activity, startComponent);
//                disableComponent(activity, normalComponent);
//                enableComponent(activity, shuangjieComponent);
//                SystemUtils.reStartApp(activity);
//            }else
            if(DateUtil.afterDay("2020-11-15")){
                if (!TextUtils.equals("default",LocalCache.getValue("appLogo"))){
                    LocalCache.putValue("appLogo","default");
                    ToastUtils.INSTANCE.showToast("因图标更换，请重新启动APP");
                }
                enableComponent(activity, normalComponent);
//                disableComponent(activity, shuangjieComponent);
                disableComponent(activity, startComponent);
                SystemUtils.reStartApp(activity);
            }else{
                if (!TextUtils.equals("start",LocalCache.getValue("appLogo"))){
                    LocalCache.putValue("appLogo","start");
                    ToastUtils.INSTANCE.showToast("因图标更换，请重新启动APP");
                }
                disableComponent(activity, normalComponent);
                enableComponent(activity, startComponent);
                SystemUtils.reStartApp(activity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 启用组件 *
     * @param componentName
     * 重要方法
     */
    public static void enableComponent(Activity activity, ComponentName componentName) {
        PackageManager pm = activity.getPackageManager();
        int state = pm.getComponentEnabledSetting(componentName);
        if (PackageManager.COMPONENT_ENABLED_STATE_ENABLED == state) {
            //已经启用
            return;
        }
        pm.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件 *
     * @param componentName
     * 重要方法
     */
    public static void disableComponent(Activity activity, ComponentName componentName) {
        PackageManager pm = activity.getPackageManager();
        int state = pm.getComponentEnabledSetting(componentName);
        if (PackageManager.COMPONENT_ENABLED_STATE_DISABLED == state) {
            //已经禁用
            return;
        }
        pm.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

//    public void saveData(String savePath) {
//        SharedPreferences sp = getSharedPreferences("Icon", Context.MODE_PRIVATE);
//        //获取到edit对象
//        SharedPreferences.Editor edit = sp.edit();
//        //通过editor对象写入数据
//        edit.putString("Value", savePath);
//        //提交数据存入到xml文件中
//        edit.commit();
//    }
//
//    public String getData() {
//        SharedPreferences sp = getSharedPreferences("Icon", Context.MODE_PRIVATE);
//        return sp.getString("Value", "");
//    }
}
