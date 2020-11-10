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
import com.jingpai.pos.customer.activity.authentication.adapter.YezhuSelectFangwuAdapter;
import com.jingpai.pos.customer.bean.XiaoquFangwuInfoBean;
import com.jingpai.pos.customer.fragment.SearchYezhuFangwuFragment;
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
public class YezhuRegisterSearchFangwuActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llSearch;
    private TextView etSearch;
    private CustomToolBar toolbar;
    private View view;
    private TextView tvXiaoqu;
    private View underlineOnlyChild;
    private TextView tvTitle;
    private RecyclerView rvList;
    private FrameLayout fl_search_content1;
    private LinearLayout ll_visibility;
    private SearchYezhuFangwuFragment searchMinzuFragment;
    private FragmentManager fm;
    int unitId;
    String danyunInfo;
    List<XiaoquFangwuInfoBean> list = new ArrayList<>();
    YezhuSelectFangwuAdapter yezhuSelectFangwuAdapter;

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
        etSearch.setHint("请输入房屋号");
        ll_visibility = findViewById(R.id.ll_visibility);
        ll_visibility.setVisibility(View.GONE);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("选择房屋");
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
            unitId = intent.getIntExtra("unitId", 0);
            danyunInfo = intent.getStringExtra("danyunInfo");
        }
        LocalCache.saveYezhuUnitId("unitId", unitId);
        tvXiaoqu.setText(danyunInfo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);
        yezhuSelectFangwuAdapter = new YezhuSelectFangwuAdapter(this,danyunInfo);
        rvList.setAdapter(yezhuSelectFangwuAdapter);
    }


    private void initData(/*String content*/) {
        //lou dong
        YezhuPresenter yezhuPresenter = new YezhuPresenter();
        yezhuPresenter.getHouseList(unitId, jsonArray -> {
            list = jsonArray.toJavaList(XiaoquFangwuInfoBean.class);
            if (list == null || list.size() == 0){
                ll_visibility.setVisibility(View.VISIBLE);
                return;
            }
            ll_visibility.setVisibility(View.GONE);
            yezhuSelectFangwuAdapter.setData(list);
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
        searchMinzuFragment = new SearchYezhuFangwuFragment();
        Bundle bundle = new Bundle();
        bundle.putString("danyunInfo",danyunInfo);
        searchMinzuFragment.setArguments(bundle);
        fm = getSupportFragmentManager();

        fm.beginTransaction().add(R.id.fl_search_content1, searchMinzuFragment)
                .show(searchMinzuFragment).commit();
    }
}
