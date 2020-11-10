package com.jingpai.pos.customer.activity.housemember;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.adapter.HouseHolderAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.HouseHolderDataBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 86173
 */
public class HouseHistoryInfoActivity extends BaseActivity {
    @BindView(R.id.rv_info)
    RecyclerView rv_info;
    @BindView(R.id.ll_no_record)
    LinearLayout llNoRecord;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smart_refresh_layout;

    private HouseHolderAdapter houseHolderAdapter;
    HouseHolderPresenterOld houseHolderPresenter;
    private List<HouseHolderDataBean> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_history_householder;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void initView() {
        list = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_info.setLayoutManager(layoutManager);
        houseHolderAdapter = new HouseHolderAdapter(this, list);
        rv_info.setAdapter(houseHolderAdapter);
        houseHolderPresenter = new HouseHolderPresenterOld();
    }

    @Override
    public void initData() {
        initView();
        initListener();
        refreshList();
    }

    private void initListener() {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    refreshList();
                }
            });
        }
    }

    private void refreshList() {
        houseHolderPresenter.getHouseHolderInfoList(100, houseHolderListInfoBean -> {
            smartRefreshLayout.finishRefresh();
            if (houseHolderListInfoBean == null || houseHolderListInfoBean.getData() == null) {
                list.clear();
                houseHolderAdapter.setData(list);
                llNoRecord.setVisibility(View.VISIBLE);
                return;
            }
            list = houseHolderListInfoBean.getData();
            if (list!=null&&list.size()!=0){
                houseHolderAdapter.setData(list);
                llNoRecord.setVisibility(View.GONE);
            }else{
                llNoRecord.setVisibility(View.VISIBLE);
            }

        });
    }

}
