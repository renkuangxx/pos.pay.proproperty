package com.jingpai.pos.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jingpai.pos.R;
import com.jingpai.pos.activity.login.LoginActivity;
import com.jingpai.pos.activity.payment.PropertySearchActivity;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.bean.Community;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.jingpai.pos.customer.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rl_logo)
    RelativeLayout rlLogo;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            skipToActovity();
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.translucent));
        StatusBarUtils.immersive(this);
        StatusBarUtils.setPaddingSmart(this, ivLogo);
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    private void skipToActovity() {
        Intent intent = null;
        Community community = LocalCache.getCurrentCommunity();
        if (community == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else{
            intent = new Intent(MainActivity.this, PropertySearchActivity.class);
            startActivity(intent);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
