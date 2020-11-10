package com.jingpai.pos.activity.login;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.activity.payment.PropertySearchActivity;
import com.jingpai.pos.customer.adapter.SelectVillageAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.bean.Community;
import com.jingpai.pos.bean.User;
import com.jingpai.pos.customer.utils.LocalCache;

import java.util.List;

import butterknife.BindView;

/*
 * 查询小区
 * */
public class SelectVillageActivity extends BaseActivity {
    @BindView(R.id.rv_estate)
    RecyclerView rvEstate;

    @Override
    protected int getLayout() {
        return R.layout.activity_select_village;
    }

    @Override
    protected void initData() {
        mToolBar.setBackVisitivity(false);
        User user = LocalCache.getUser();
        List<Community> communityList = user.getCommunities();
        rvEstate.setLayoutManager(new LinearLayoutManager(SelectVillageActivity.this));
        SelectVillageAdapter adapter = new SelectVillageAdapter(R.layout.village_item, user.getCommunities());
        rvEstate.setAdapter(adapter);
        //接口回调
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            LocalCache.setCurrentCommunity(communityList.get(position));
            startActivity(new Intent(SelectVillageActivity.this, PropertySearchActivity.class));
            finish();
        });

    }

}