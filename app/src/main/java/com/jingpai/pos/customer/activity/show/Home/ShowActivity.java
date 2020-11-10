package com.jingpai.pos.customer.activity.show.Home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.AppUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.EventBusMessage;
import com.jingpai.pos.customer.bean.SplashBean;
import com.jingpai.pos.customer.fragment.MyFragment;
import com.jingpai.pos.customer.fragment.ServeFragment;
import com.jingpai.pos.customer.fragment.ServerTabFragment;
import com.jingpai.pos.presenter.login.VersionPresenter;
import com.jingpai.pos.customer.utils.PermissionHelper;
import com.jingpai.pos.customer.utils.PermissionInterface;
import com.jingpai.pos.customer.utils.PermissionUtil;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.jingpai.pos.customer.utils.SystemUtils;
import com.jingpai.pos.customer.views.DownLoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ShowActivity extends BaseActivity implements PermissionInterface {

    @BindView(R.id.show_iv)
    ImageView showIv;
    @BindView(R.id.life_iv)
    ImageView lifeIv;
    @BindView(R.id.discover_iv)
    ImageView discoverIv;
    @BindView(R.id.my_iv)
    ImageView myIv;
    @BindView(R.id.FrameLayout)
    android.widget.FrameLayout FrameLayout;
    @BindView(R.id.show_tv)
    TextView showTv;
    @BindView(R.id.life_tv)
    TextView lifeTv;
    @BindView(R.id.discover_tv)
    TextView discoverTv;
    @BindView(R.id.my_tv)
    TextView myTv;
    @BindView(R.id.ll_main)
    LinearLayout llMain;

    @BindView(R.id.ll_menu)
    View menuView;

    private FragmentManager fm;
    private ServeFragment serveFragment;
    private ServerTabFragment serverTabFragment;
    private MyFragment myFragment;
    private long clickTime = 0; // 第一次点击的时间

    private VersionPresenter versionPresenter;
//    private AppUpdateDialog appUpdateDialog;
    //要申请的权限
    private PermissionHelper permissionHelper;
    @Override
    protected int getLayout() {
        return R.layout.activity_show;
    }


    @Override
    protected void initData() {
        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.translucent));
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        //初始化
        permissionHelper = new PermissionHelper(this, this);
        //申请权限
        permissionHelper.requestPermissions();
//        showFragment = new ShowFragment();
        serveFragment = new ServeFragment();
        myFragment = new MyFragment();
        serverTabFragment = new ServerTabFragment();

        fm = getSupportFragmentManager();


        versionPresenter = new VersionPresenter(this);
        versionPresenter.checkUpdate(versionBean -> {
            if (versionBean != null && versionBean.getVersionCode() > AppUtils.getAppVersionCode()) {

                llMain.post(new Runnable() {
                    @Override
                    public void run() {
                        DownLoadDialog downLoadDialog = new DownLoadDialog(ShowActivity.this,versionBean);
                        downLoadDialog.setOkListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                versionPresenter.startDownLoad( versionBean.getDownloadUrl());
                            }
                        });
                        downLoadDialog.show(llMain);
                    }
                });

            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
//        routePage(intent);
        initData();
    }

    @Override
    public void onBackPressed() {
        if (!serveFragment.isHidden() && !serveFragment.onBackPressed()) {
            //serveFragment，webView goBack
            return;
        }
        exit();
    }

    public void reloadCityLife(){
        serverTabFragment.reload();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        } else { // 如果不是back键正常响应
            return super.onKeyDown(keyCode, event);
        }
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }

    ;


    @OnClick(R.id.life)
    public void showLife() {
        FragmentTransaction transaction = fm.beginTransaction();
        lifeIv.setImageResource(R.mipmap.community_on);
        lifeTv.setTextColor(Color.parseColor("#FF8A49"));
        showIv.setImageResource(R.mipmap.home_nps);
        showTv.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams1 = showIv.getLayoutParams();
        layoutParams1.width =  SystemUtils.Dp2Px(this,24);
        layoutParams1.height =  SystemUtils.Dp2Px(this,24);
        showIv.setLayoutParams(layoutParams1);
        showTv.setTextColor(Color.parseColor("#FF000000"));
        myIv.setImageResource(R.mipmap.mine_np);
        myTv.setTextColor(Color.parseColor("#FF000000"));
        discoverIv.setImageResource(R.mipmap.community_np);
        discoverTv.setTextColor(Color.parseColor("#FF000000"));

        transaction.show(serverTabFragment).hide(serveFragment).hide(myFragment);
        transaction.commit();
    }

    @OnClick(R.id.discover)
    public void showDiscover() {
        FragmentTransaction transaction = fm.beginTransaction();
        discoverIv.setImageResource(R.mipmap.ic_community_p);
        discoverTv.setTextColor(Color.parseColor("#FF8A49"));
        showIv.setImageResource(R.mipmap.home_nps);
        showTv.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams1 = showIv.getLayoutParams();
        layoutParams1.width =  SystemUtils.Dp2Px(this,24);
        layoutParams1.height =  SystemUtils.Dp2Px(this,24);
        showIv.setLayoutParams(layoutParams1);
        showTv.setTextColor(Color.parseColor("#FF000000"));
        myIv.setImageResource(R.mipmap.mine_np);
        myTv.setTextColor(Color.parseColor("#FF000000"));
        lifeIv.setImageResource(R.mipmap.community_off);
        lifeTv.setTextColor(Color.parseColor("#FF000000"));

        transaction.show(serveFragment).hide(myFragment).hide(serverTabFragment);
        transaction.commit();
    }

    @OnClick(R.id.my)
    public void onViewClicked() {
        FragmentTransaction transaction = fm.beginTransaction();
        myIv.setImageResource(R.drawable.ic_down);
        myTv.setTextColor(Color.parseColor("#FF8A49"));
        showIv.setImageResource(R.mipmap.home_nps);
        showTv.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams1 = showIv.getLayoutParams();
        layoutParams1.width =  SystemUtils.Dp2Px(this,24);
        layoutParams1.height =  SystemUtils.Dp2Px(this,24);
        showIv.setLayoutParams(layoutParams1);
        showTv.setTextColor(Color.parseColor("#FF000000"));
        discoverIv.setImageResource(R.mipmap.community_np);
        discoverTv.setTextColor(Color.parseColor("#FF000000"));
        lifeIv.setImageResource(R.mipmap.community_off);
        lifeTv.setTextColor(Color.parseColor("#FF000000"));

        transaction.show(myFragment).hide(serveFragment).hide(serverTabFragment);
        transaction.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(EventBusMessage eventBusMessage) {
        switch (eventBusMessage.getEventType()){
            case Constant.EVENT_BUS_SKIP_TO_WEBVIEW:
                skipActivity((SplashBean)eventBusMessage.getEventObj());
                break;
            case Constant.EVENT_BUS_LIFE_SERVER:
                showLife();
                serverTabFragment.setTabIndex(1);
                break;
            case Constant.EVENT_BUS_ELECTRONIC_APPLIANCES:
                showLife();
                serverTabFragment.setTabIndex(0);
                break;
            case Constant.EVENT_BUS_CAR_LIFE:
                showLife();
                serverTabFragment.setTabIndex(2);
                break;
            case Constant.EVENT_BUS_PET_PARADISE:
                showLife();
                serverTabFragment.setTabIndex(3);
                break;
            case Constant.EVENT_BUS_COMMUNITY_GROUP_BUY:
                showDiscover();
                break;
            case Constant.EVENT_BUS_SKIP_TO_SHOPPING:
                setBottomMenuVisibility(true);
                showDiscover();
                serveFragment.reLoadUrl();
                break;
            case Constant.EVENT_BUS_SKIP_TO_ME:
                setBottomMenuVisibility(true);
                onViewClicked();
                serveFragment.reLoadUrl();
                break;
        }
    }

    private void skipActivity(SplashBean splashBean){
        if (null == splashBean)return;
        startWebViewActivity(splashBean.getLink(),splashBean.getName());
    }
    private void startWebViewActivity( String url, String title) {
        Intent intent=null;
        if(url.contains("jphl.com")){
            intent = new Intent(ShowActivity.this, CityLifeWebViewActivity.class);
            intent.putExtra(Constant.WEB_URL, url);
            startActivity(intent);
        }else{
            intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            startActivity(intent);
        }

    }
    public void goHome() {
        Observable.just("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (getSupportFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment mFragment : fragments) {
                mFragment.onActivityResult(requestCode, resultCode, intent);
            }
        }
    }


    public void setBottomMenuVisibility(boolean visibility) {
        if (menuView ==null)return;
        if (visibility) {
            menuView.setVisibility(View.VISIBLE);
        } else {
            menuView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        //移除更新弹窗
//        getLifecycle().removeObserver(appUpdateDialog);
    }


    @Override
    public int getPermissionsRequestCode() {
        return 0;
    }

    @Override
    public String[] getPermissions() {
        return PermissionUtil.mPermissions;
    }

    @Override
    public void requestPermissionsSuccess() { }

    @Override
    public void requestPermissionsFail() {

        String[] mPermissions = PermissionUtil.getDeniedPermissions(this, PermissionUtil.mPermissions);
        if (mPermissions==null)return;
//        StringBuilder sb = new StringBuilder();
//        for (String s : mPermissions) {s
//            if (s.equals(Manifest.permission.CAMERA)) {
//                sb.append("相机权限(用于拍照，视频聊天);\n");
//            } else if (s.equals(Manifest.permission.READ_EXTERNAL_STORAGE) || s.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                sb.append("存储,读取权限(用于存储必要信息，缓存数据);\n");
//            }
//        }
        //PermissionUtil.PermissionDialog(this, "程序运行需要如下权限：\n" + sb.toString() + "请在应用权限管理进行设置！");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100){
            onRequestPermissionsResult(requestCode,permissions,grantResults);
        }else{
            if (permissionHelper.requestPermissionsResult(requestCode, PermissionUtil.mPermissions, grantResults)) {
                //权限请求结果，并已经处理了该回调
                return;
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}