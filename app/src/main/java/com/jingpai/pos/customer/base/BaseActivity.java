package com.jingpai.pos.customer.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jingpai.pos.R;
import com.jingpai.pos.activity.login.LoginActivity;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.views.CustomToolBar;
import com.jingpai.pos.customer.utils.ActivityCollectorUtil;
import com.jingpai.pos.customer.utils.Intents;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.jingpai.pos.BuildConfig.SHOP_URL;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    protected CustomToolBar mToolBar;
    protected SmartRefreshLayout smartRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //获取视图ID
        setContentView(getLayout());
        ActivityCollectorUtil.addActivity(this);

//        AndroidBug5497Workaround.assistActivity(this);
        //Butterknife的处理
        unbinder = ButterKnife.bind(this);
        //布局中要设置id为toolbar
        mToolBar=findViewById(R.id.toolbar);
        smartRefreshLayout=findViewById(R.id.smart_refresh_layout);

        if (smartRefreshLayout!=null){
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    refreshData();
                }
            });
        }
//        StatusBarUtilss.init(this);
        //沉浸式
        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.INSTANCE.setTranslucentStatus(this);
        StatusBarUtil.INSTANCE.setStatusBarDarkTheme(this, true);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));
        //初始化数据
        initData();
    }

    protected abstract int getLayout();


    protected abstract void initData();

    /**
     * 刷新数据
     */
    protected void refreshData(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);
        //解绑Butterknife的处理
        unbinder.unbind();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        AndroidBug5497Workaround.assistActivity(this);
    }

    protected void toActivity(Class cls) {
        Intents.getInstence().intent(this, cls);
    }

    protected void openShopPage(String path) {
        Intent intent = new Intent(this, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, SHOP_URL + path);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
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

    public void gotoLogin(){
        LocalCache.clean();
        //关闭所有activity
        ActivityCollectorUtil.finishAllActivity();
        //跳转登录
        Intents.getInstence().intent(this, LoginActivity.class);
    }

}