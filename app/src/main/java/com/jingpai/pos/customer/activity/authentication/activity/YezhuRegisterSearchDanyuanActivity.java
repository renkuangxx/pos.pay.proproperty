package com.jingpai.pos.customer.activity.authentication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.authentication.adapter.YezhuSelectDanyuanAdapter;
import com.jingpai.pos.customer.bean.XiaoquDanyuanInfoBean;
import com.jingpai.pos.customer.fragment.SearchYezhuDanyuanFragment;
import com.jingpai.pos.customer.mvp.YezhuPresenter;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.jingpai.pos.customer.views.CustomToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 * <p>
 * dan yuan
 */
public class YezhuRegisterSearchDanyuanActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llSearch;
    private TextView etSearch;
    private View view;
    private TextView tvXiaoqu;
    private View underlineOnlyChild;
    private TextView tvTitle;
    private RecyclerView rvList;
    private FrameLayout fl_search_content1;
    private SearchYezhuDanyuanFragment searchMinzuFragment;
    private FragmentManager fm;
    private LinearLayout ll_visibility;
    private CustomToolBar toolbar;
    private String loudongInfo;
    int buildingId;
    List<XiaoquDanyuanInfoBean> list = new ArrayList<>();
    YezhuSelectDanyuanAdapter yezhuSelectDanyuanAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_yezhu_search);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        llSearch = findViewById(R.id.ll_search);
        etSearch = findViewById(R.id.et_search);
        ll_visibility = findViewById(R.id.ll_visibility);
        ll_visibility.setVisibility(View.GONE);
        etSearch.setHint("请输入单元号");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("选择单元");
        etSearch.setOnClickListener(this);
        view = findViewById(R.id.view);
        tvXiaoqu = findViewById(R.id.tv_xiaoqu);
        underlineOnlyChild = findViewById(R.id.underline_only_child);
        rvList = findViewById(R.id.rv_list);
        fl_search_content1 = findViewById(R.id.fl_search_content1);
        fl_search_content1.setVisibility(View.GONE);
        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.INSTANCE.setTranslucentStatus(this);
        StatusBarUtil.INSTANCE.setStatusBarDarkTheme(this, true);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));


        Intent intent = getIntent();
        if (intent != null) {
            buildingId = intent.getIntExtra("buildingId", 0);
            loudongInfo = intent.getStringExtra("loudongInfo");
        }
        LocalCache.saveYezhuBuildingId("buildingId", buildingId);
        tvXiaoqu.setText(loudongInfo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);
        yezhuSelectDanyuanAdapter = new YezhuSelectDanyuanAdapter(this,loudongInfo);
        rvList.setAdapter(yezhuSelectDanyuanAdapter);
    }


    private void initData(/*String content*/) {
        //lou dong
        YezhuPresenter yezhuPresenter = new YezhuPresenter();
        yezhuPresenter.getDanyuanseList(buildingId, jsonArray -> {
            list = jsonArray.toJavaList(XiaoquDanyuanInfoBean.class);
            if (list == null || list.size() == 0) {
                ll_visibility.setVisibility(View.VISIBLE);
                return;
            }
            ll_visibility.setVisibility(View.GONE);
            yezhuSelectDanyuanAdapter.setData(list);
        });
    }


    private void initListener() {
        llSearch.setOnClickListener(this);
        etSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.et_search:
            case R.id.ll_search:
                fl_search_content1.setVisibility(View.VISIBLE);
                etSearch.setVisibility(View.GONE);
                initFragment();
//                finish();
                break;
        }
    }

    private void initFragment() {
        searchMinzuFragment = new SearchYezhuDanyuanFragment();
        Bundle bundle = new Bundle();
        bundle.putString("loudongInfo", loudongInfo);
        searchMinzuFragment.setArguments(bundle);
        fm = getSupportFragmentManager();

        fm.beginTransaction().add(R.id.fl_search_content1, searchMinzuFragment)
                .show(searchMinzuFragment).commit();
    }
}
