package com.jingpai.pos.customer.activity.authentication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.utils.StatusBarUtil;

public class MyHouseDefaultActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_register;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_default);

        initView();
        initListener();
        initData();
    }


    private void initData() {

    }


    private void initListener() {
        tv_register.setOnClickListener(this);
    }

    private void initView() {
        tv_register = findViewById(R.id.tv_register);
        StatusBarUtil.INSTANCE.setStatusBarMode(this, true, R.color.black);
        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));
//        ivPic = findViewById(R.id.iv_pic);
//        picTitle = findViewById(R.id.pic_title);
//        rvList = findViewById(R.id.rv_list);
//        tv_ask = findViewById(R.id.tv_ask);
//        iv_back = findViewById(R.id.iv_back);

//        //沉浸式
//        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, true);
//        StatusBarUtil.INSTANCE.setTranslucentStatus(this);
//        StatusBarUtil.INSTANCE.setStatusBarDarkTheme(this, true);
//        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));

//        lifePresenter = new LifePresenter();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
                case R.id.tv_register:
                startActivity(new Intent(this, YezhuRegisterSearchActivity.class));
//                finish();
                break;
        }
    }
}