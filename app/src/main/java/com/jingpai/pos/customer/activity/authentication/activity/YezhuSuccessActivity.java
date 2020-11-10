package com.jingpai.pos.customer.activity.authentication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.housemember.BuildingActivity;
import com.jingpai.pos.customer.activity.show.Home.ShowActivity;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.utils.LocalCache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YezhuSuccessActivity extends BaseActivity {

    @BindView(R.id.tv_to_house)
    TextView tvToHouse;
    @BindView(R.id.tv_to_main)
    TextView tvToMain;
    String shenfen;
    @Override
    protected int getLayout() {
        return R.layout.activity_yezhu_success;
    }

    @Override
    protected void initData() {
        Intent intent =getIntent();
        if (intent!=null){
            shenfen =intent.getStringExtra("shenfen");

        }
        LocalCache.saveYezhuState("isCommit",true);
//        if (TextUtils.equals("OWNER",shenfen)) {
////            type = "OWNER";
//            tvToHouse.setVisibility(View.VISIBLE);
//        } else  {
////            type = "TENANT";
//            tvToHouse.setVisibility(View.INVISIBLE);
//        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_to_house, R.id.tv_to_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_to_house:
                Intent intent = new Intent(this, BuildingActivity.class);
                intent.putExtra("yezhu",1);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_to_main:
                startActivity(new Intent(this, ShowActivity.class));
                finish();
                break;
        }
    }
}
