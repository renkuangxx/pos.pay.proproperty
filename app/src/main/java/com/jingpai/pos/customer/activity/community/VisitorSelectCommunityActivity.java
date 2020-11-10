package com.jingpai.pos.customer.activity.community;


import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.bean.CommunityBean;
import com.jingpai.pos.customer.adapter.CityAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.CityCommunityBean;
import com.jingpai.pos.bean.Community;
import com.jingpai.pos.customer.mvp.presenter.show.home.CommunityPresenter;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.views.SelectCommunityDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VisitorSelectCommunityActivity extends BaseActivity {

    @BindView(R.id.tv_nearest)
    TextView tvNearest;
    @BindView(R.id.lv_select_city)
    RecyclerView lvSelectCity;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    private CommunityPresenter communityPresenter;
    private CityAdapter cityAdapter;
    private List<CityCommunityBean> list = new ArrayList<>();
    private Community nearestCommunity;
    @Override
    protected int getLayout() {
        return R.layout.activity_visitor_select_community;
    }

    @Override
    protected void initData() {
        cityAdapter = new CityAdapter(R.layout.item_city, list);
        lvSelectCity.setAdapter(cityAdapter);
        lvSelectCity.setLayoutManager(new LinearLayoutManager(this));
        mToolBar.setBackClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
        cityAdapter.setOnItemClick(position -> {
            SelectCommunityDialog selectCommunityDialog = new SelectCommunityDialog(this);
            selectCommunityDialog.setData(list.get(position).getCommunityList(),list.get(position).getCityName());
            selectCommunityDialog.setOkListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CommunityBean communityBean = list.get(position).getCommunityList().get(which);
                    Community community = new Community();
                    community.setOrgId(communityBean.getOrgId());
                    community.setCommunityId(communityBean.getCommunityId());
                    community.setCommunityName(communityBean.getCommunityName());
                    community.setCity(list.get(position).getCityCode());
                    community.setAddress(communityBean.getAddress());
                    community.setAreaCode(communityBean.getAreaCode());
                    community.setCounty(communityBean.getCounty());
                    community.setProvince(communityBean.getProvince());
                    LocalCache.setCurrentCommunity(community);
                    setResult(RESULT_OK);
                    finish();
                }
            });
            selectCommunityDialog.show(llMain);
        });

        communityPresenter = new CommunityPresenter();
        communityPresenter.getCommunityGroudByCity(jsonArray -> {
            List<CityCommunityBean> cityCommunityBeanList = jsonArray.toJavaList(CityCommunityBean.class);
            list.clear();
            list.addAll(cityCommunityBeanList);
            cityAdapter.notifyDataSetChanged();
        });

        String lat = getIntent().getStringExtra("lat");
        String lon = getIntent().getStringExtra("lon");
        if (!TextUtils.isEmpty(lat)) {
            communityPresenter.getNearestCommunity(lat, lon, communityBean -> {
                if (communityBean==null||TextUtils.isEmpty(communityBean.getCommunityName())){
                    tvNearest.setText("您附近没有匹配的小区 ");
                    return;
                }
                tvNearest.setText(communityBean.getCommunityName());
                nearestCommunity = new Community();
                nearestCommunity.setOrgId(communityBean.getOrgId());
                nearestCommunity.setCommunityId(communityBean.getCommunityId());
                nearestCommunity.setCommunityName(communityBean.getCommunityName());
                nearestCommunity.setCity(communityBean.getCity());
                nearestCommunity.setAddress(communityBean.getAddress());
                nearestCommunity.setAreaCode(communityBean.getAreaCode());
                nearestCommunity.setCounty(communityBean.getCounty());
                nearestCommunity.setProvince(communityBean.getProvince());
            });
        }
    }

    @OnClick(R.id.tv_nearest)
    public void clickNearest() {
        if (nearestCommunity==null)return;
        LocalCache.setCurrentCommunity(nearestCommunity);
        setResult(RESULT_OK);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
