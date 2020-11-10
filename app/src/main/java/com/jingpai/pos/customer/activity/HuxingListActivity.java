package com.jingpai.pos.customer.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.adapter.HuxingListAdapter;
import com.jingpai.pos.customer.bean.HuxingListBean;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.views.UpdataInterface;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class HuxingListActivity extends AppCompatActivity implements View.OnClickListener {
    private SmartRefreshLayout smartRefreshLayout;
    private ImageView ivPic,iv_back;
    private TextView picTitle;
    private TextView tv_ask;
    private RecyclerView rvList;
    private LifePresenter lifePresenter;
    private HuxingListAdapter lifeListAdapter;
    List<String> houseLayout = new ArrayList<>();
    List<HuxingListBean> recommondList = new ArrayList();
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
        StatusBarUtil.setTransparentForImageView(this,ivPic);
    }

    private void initData() {

        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("token", TextUtils.isEmpty(LocalCache.getToken())?"":LocalCache.getToken());
        lifePresenter.houseQuery(baseRequest,jsonArray -> {
            if (jsonArray != null ){
                houseLayout.clear();
                houseLayout = jsonArray.toJavaList(String.class);
                if (houseLayout.size()== 0 ){
                }else {
                    TreeMap<String, Object> baseRequest1 = NetWorkUtil.getInstance().getBaseRequest();
                    baseRequest1.put("currentUserId", TextUtils.isEmpty(LocalCache.getUserId()) ? "" : LocalCache.getUserId());
                    baseRequest1.put("houseLayout", houseLayout);
                    baseRequest1.put("pageNo", 1);
                    baseRequest1.put("pageSize", 3);
                    lifePresenter.huxingRecommond(baseRequest1, huxingRecommondBean -> {
                        smartRefreshLayout.finishRefresh();
                        //户型推荐
                        LinearLayoutManager horizontal = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                        rvList.setLayoutManager(horizontal);
                        lifeListAdapter = new HuxingListAdapter(this, recommondList,lifePresenter,new UpdataInterface() {
                            @Override
                            public void updataInterface() {
                                initData();
                            }
                        });
                        rvList.setAdapter(lifeListAdapter);

                        if (huxingRecommondBean == null || huxingRecommondBean.getContentLikeCollectPageInfo() == null) return;
                        String url = TextUtils.isEmpty(huxingRecommondBean.getCateImages())?"":huxingRecommondBean.getCateImages();
                        String title = TextUtils.isEmpty(huxingRecommondBean.getCateTitle())?"":huxingRecommondBean.getCateTitle();
                        Glide.with(this)
                                .load(url)
                                .into(ivPic);
                        picTitle.setText(title);
                        recommondList = huxingRecommondBean.getContentLikeCollectPageInfo().getList();
                        if (recommondList == null || recommondList.size() == 0)return;
                        lifeListAdapter.setData(recommondList);
                    });
                }
            }
        });

    }


    private void initListener() {
        iv_back.setOnClickListener(this);
        tv_ask.setOnClickListener(this);
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    initData();
                }
            });
        }
    }

    private void initView() {
        smartRefreshLayout = findViewById(R.id.smart_refresh_layout);
        ivPic = findViewById(R.id.iv_pic);
        picTitle = findViewById(R.id.pic_title);
        rvList = findViewById(R.id.rv_list);
        tv_ask = findViewById(R.id.tv_ask);
        iv_back = findViewById(R.id.iv_back);

//        //沉浸式
//        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, true);
//        StatusBarUtil.INSTANCE.setTranslucentStatus(this);
//        StatusBarUtil.INSTANCE.setStatusBarDarkTheme(this, true);
//        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));

        lifePresenter = new LifePresenter();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //咨询
            case R.id.iv_back:
                finish();
                break;
        }
    }
}