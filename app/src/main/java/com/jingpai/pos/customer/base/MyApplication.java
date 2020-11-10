package com.jingpai.pos.customer.base;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.webkit.WebView;

import com.jingpai.pos.activity.login.LoginActivity;
import com.jingpai.pos.customer.custom.RefreshHeader;
import com.jingpai.pos.customer.di.component.ApplicationComponent;
import com.jingpai.pos.customer.di.component.DaggerApplicationComponent;
import com.jingpai.pos.customer.di.module.ApplicationModule;
import com.jingpai.pos.customer.utils.ActivityChannelUtil;
import com.jingpai.pos.customer.utils.ActivityCollectorUtil;
import com.jingpai.pos.customer.utils.Intents;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.MyCrashHandler;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/*
 * function:
 */
public class MyApplication extends Application {

    public final static float DESIGN_WIDTH = 750; //绘制页面时参照的设计图宽度
    private static MyApplication mInstance;
    private ApplicationComponent mApplicationComponent;
    private static Context context;

    @Override
    public void onCreate() {
        mInstance = this;
        context = getApplicationContext();
//        resetDensity();
        super.onCreate();
        //为其它进程webView设置目录
        initWebview();

        //设置刷新头部
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new RefreshHeader(this));

        //初始化推送类型和打开界面的映射关系
        ActivityChannelUtil.initMap();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        MyCrashHandler handler = new MyCrashHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);//添加关闭广播
        mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);//添加关闭广播
        BootBroadcastReceiver mBootBroadcastReceiver = new BootBroadcastReceiver();
        registerReceiver(mBootBroadcastReceiver,mIntentFilter);

        //友盟
        MobclickAgent.setDebugMode(false);
        UMConfigure.init(this, "5f462c24f9d1496ef4195468", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }

    private void initWebview(){//为其它进程webView设置目录
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName();
            String packageName = this.getPackageName();
            if (!packageName.equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }
    public class BootBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(ACTION_BOOT)) { //开机启动完成后，要做的事情
//                Intent intent1 = new Intent(context, IptvSettingsService.class);
//                context.startService(intent1);
//            }
        }
    }

//    public  String getProcessName(Context mContext) {
//        if (mContext == null) return null;
//        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
//            if (processInfo.pid == android.os.Process.myPid()) {
//                return processInfo.processName;
//            }
//        }
//        return null;
//    }

////这样绘制出来的页面就跟设计图几乎完全一样但是要以pt为单位
//    private void resetDensity() {
//        Point size = new Point();
//        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
//        getResources().getDisplayMetrics().xdpi = size.x / DESIGN_WIDTH * 72f;
//    }
    /**
     * 初始化ApplicationComponent
     */
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
    public static Context getContext() {
        return context;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
//        resetDensity();//这个方法重写也是很有必要的

        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
    public static void gotoLogin(){
        LocalCache.clean();
        //关闭所有activity
        ActivityCollectorUtil.finishAllActivity();
        //跳转登录
        Intents.getInstence().intent(getContext(), LoginActivity.class);
    }

}
