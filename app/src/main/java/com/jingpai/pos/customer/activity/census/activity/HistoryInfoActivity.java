package com.jingpai.pos.customer.activity.census.activity;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.GatherInfoPresenterOld;
import com.jingpai.pos.customer.activity.census.adapter.HistoryInfoAdapter;
import com.jingpai.pos.customer.activity.census.bean.HistoryInfoBean;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.views.CustomToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 */
public class HistoryInfoActivity extends BaseActivity implements RecyclerView.OnClickListener {
    private CustomToolBar toolbar;
    private RecyclerView rvHistory;
    private HistoryInfoAdapter historyInfoAdapter;
    private List<HistoryInfoBean> list;
    private LinearLayout ll_no_data;
    GatherInfoPresenterOld gatherInfoPresenter;


    @Override
    protected int getLayout() {
        return R.layout.activity_history_info;
    }
    @Override
    protected void initData() {
        initView();
        initListener();
        gatherInfoPresenter = new GatherInfoPresenterOld();
        gatherInfoPresenter.userInfoQuery1(this::getHistoryListData);
    }

    //查询
    public void getHistoryListData(JSONArray jsonArray) {
        list = jsonArray.toJavaList(HistoryInfoBean.class);
        if (list == null || list.size() == 0) {
            rvHistory.setVisibility(View.GONE);
            ll_no_data.setVisibility(View.VISIBLE);
            return;
        }
        historyInfoAdapter.setData(list);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        ll_no_data = findViewById(R.id.ll_no_data);
        rvHistory = findViewById(R.id.rv_history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHistory.setLayoutManager(layoutManager);
        historyInfoAdapter = new HistoryInfoAdapter(this,list);
        list = new ArrayList<>();
        ll_no_data.setVisibility(View.GONE);
        rvHistory.setAdapter(historyInfoAdapter);
    }

    private void initListener() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
