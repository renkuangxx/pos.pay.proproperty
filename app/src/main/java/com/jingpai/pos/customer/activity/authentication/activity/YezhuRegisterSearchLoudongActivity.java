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
import com.jingpai.pos.customer.activity.authentication.adapter.YezhuSelectLoudongAdapter;
import com.jingpai.pos.customer.bean.YezhuXiaoquLoudongInfoBean;
import com.jingpai.pos.customer.fragment.SearchYezhuFragment;
import com.jingpai.pos.customer.mvp.YezhuPresenter;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.jingpai.pos.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 * <p>
 * lou dong
 */
public class YezhuRegisterSearchLoudongActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llSearch;
    private TextView etSearch;
    private View view;
    private TextView tvXiaoqu;
    private View underlineOnlyChild;
    private TextView tvTitle;
    private RecyclerView rvList;
    private LinearLayout ll_visibility;
    private FrameLayout fl_search_content1;
    private SearchYezhuFragment searchMinzuFragment;
    private FragmentManager fm;
    int communityId;
    String info;
    List<YezhuXiaoquLoudongInfoBean.data> list = new ArrayList<>();
    YezhuSelectLoudongAdapter yezhuSelectVillageAdapter;

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
            communityId = intent.getIntExtra("communityId",0);
            info = intent.getStringExtra("info");
            LocalCache.saveYezhuCommunityId("communityId",communityId);
        }
        tvXiaoqu.setText(info);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);
        yezhuSelectVillageAdapter = new YezhuSelectLoudongAdapter(this,info);
        rvList.setAdapter(yezhuSelectVillageAdapter);
    }



    private void initData(/*String content*/) {
        //lou dong
        YezhuPresenter yezhuPresenter = new YezhuPresenter();
        try {
            yezhuPresenter.getLouList(communityId, jsonArray -> {
                list = jsonArray.toJavaList(YezhuXiaoquLoudongInfoBean.data.class);
                if (list == null || list.size() == 0) {
                    ll_visibility.setVisibility(View.VISIBLE);
                    return;
                }
                ll_visibility.setVisibility(View.GONE);
                yezhuSelectVillageAdapter.setData(list);
            });
        }catch (Exception e){
            ToastUtils.INSTANCE.showToast(e+"");
        }


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
        searchMinzuFragment = new SearchYezhuFragment();
        Bundle bundle = new Bundle();
        bundle.putString("info",info);
        searchMinzuFragment.setArguments(bundle);
        fm = getSupportFragmentManager();

        fm.beginTransaction().add(R.id.fl_search_content1, searchMinzuFragment)
                .show(searchMinzuFragment).commit();
    }
}
