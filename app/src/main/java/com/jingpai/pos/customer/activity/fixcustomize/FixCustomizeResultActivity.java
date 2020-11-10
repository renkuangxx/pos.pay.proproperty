package com.jingpai.pos.customer.activity.fixcustomize;


import android.view.View;
import android.view.WindowManager;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;

import butterknife.OnClick;

/**
    * 时间: 2020/2/18
    * 功能:
    */
    public class FixCustomizeResultActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        getWindow().setSoftInputMode(   WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return R.layout.activity_fix_customize_result;
    }


    @Override
    protected void initData() {
    }
    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        finish();
    }

}