package com.jingpai.pos.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.adapter.LifeListAdapter;
import com.jingpai.pos.customer.bean.MoreListBean;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.views.UpdataInterface;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class LifeListActivity extends AppCompatActivity implements View.OnClickListener {
    private SmartRefreshLayout smartRefreshLayout;
    private ImageView ivPic, iv_back, iv_share;
    private TextView picTitle;
    private TextView tv_ask;
    private RecyclerView rvList;
    private LinearLayout ll_visibility;
    private LifePresenter lifePresenter;
    private LifeListAdapter lifeListAdapter;
    MoreListBean moreListBean;
    List<MoreListBean.PageInfoBean.ListBean> list;
    String userId;
    String name;
    int contentCateId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_life);
        setStatusBar();
        initView();
        initListener();
        initData();
    }

    private void setStatusBar() {
        com.jaeger.library.StatusBarUtil.setTransparentForImageView(this, ivPic);
    }

    private void initData() {
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("currentUserId", userId);
        baseRequest.put("contentCateId", contentCateId);
        baseRequest.put("industryId", 1);
        baseRequest.put("pageNo", 1);
        baseRequest.put("pageSize", 20);
        lifePresenter.moreRecommond(baseRequest, moreLIstBean -> {
            smartRefreshLayout.finishRefresh();
            if (moreLIstBean == null) return;
            String url = TextUtils.isEmpty(moreLIstBean.getCateImages()) ? "" : moreLIstBean.getCateImages();
            Glide.with(this).load(url).into((ImageView) ivPic);
            ll_visibility.setVisibility(View.GONE);
            if (moreLIstBean.getPageInfo() == null || moreLIstBean.getPageInfo().getList() == null
                    || moreLIstBean.getPageInfo().getList().size() == 0) {
                ll_visibility.setVisibility(View.VISIBLE);
                return;
            }
            list = moreLIstBean.getPageInfo().getList();
            lifeListAdapter.setData(list);
        });

    }


    private void initListener() {
        tv_ask.setOnClickListener(this);
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    initData();
                }
            });
        }
        iv_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
    }

    private void initView() {
        smartRefreshLayout = findViewById(R.id.smart_refresh_layout);
        ivPic = findViewById(R.id.iv_pic);
        picTitle = findViewById(R.id.pic_title);
        rvList = findViewById(R.id.rv_list);
        tv_ask = findViewById(R.id.tv_ask);
        ll_visibility = findViewById(R.id.ll_visibility);
        iv_back = findViewById(R.id.iv_back);
        iv_share = findViewById(R.id.iv_share);


        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra("currentUserId");
            contentCateId = intent.getIntExtra("contentCateId", 0);
            name = intent.getStringExtra("name");
        }

        picTitle.setText(TextUtils.isEmpty(name) ? "" : name);
        list = new ArrayList();

        //沉浸式
//        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, true);
//        StatusBarUtil.INSTANCE.setTranslucentStatus(this);
//        StatusBarUtil.INSTANCE.setStatusBarDarkTheme(this, true);
//        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));

        lifePresenter = new LifePresenter();

        LinearLayoutManager horizontal = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(horizontal);
        lifeListAdapter = new LifeListAdapter(this, list, lifePresenter, new UpdataInterface() {
            @Override
            public void updataInterface() {
                initData();
            }
        });
        rvList.setAdapter(lifeListAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.huxing_more:
                finish();
                break;
            //咨询
            case R.id.tv_ask:
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
//                WxShareUtils.showShareDialog(
//                        this,
//                        visitBean.password,
//                        createQRCode,
//                        tvTitle.text.toString().replaceFirst("我", LocalCache.getUserName())
//                );
                break;
        }
    }
}