package com.jingpai.pos.customer.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.repairs.MatterDetailActivity;
import com.jingpai.pos.customer.adapter.MatterHistoryAdapter;
import com.jingpai.pos.customer.bean.MatterHistoryListBean;
import com.jingpai.pos.customer.custom.RefreshHeader;
import com.jingpai.pos.customer.mvp.presenter.show.my.MatterHistoryPresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.RecyclerViewUtil;
import com.jingpai.pos.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MatterHistoryFragment extends Fragment implements MatterHistoryAdapter.MatterCallBack{
    Unbinder unbinder;
    @BindView(R.id.matter_history_view)
    RecyclerView matterHistoryView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private static final String DEFAULT_BEFORE = "0";
    @BindView(R.id.ll_visibility)
    LinearLayout llVisibility;
    private boolean hasMore = true;
    private MatterHistoryAdapter matterHistoryAdapter;
    private List<MatterHistoryListBean.MatterHistoryBean> dataBeanList = new ArrayList<>();
    private String before = DEFAULT_BEFORE;
    private MatterHistoryPresenter matterHistoryPresenter;
    private String mType;
    private View view;

    public MatterHistoryFragment(int type) {
        switch (type) {
            case 0://待处理
                mType = "HANDLING";
                break;
            case 5://已完成
                mType = "FINISHED";
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getActivity(), R.layout.fragment_matter_history, null);
            unbinder = ButterKnife.bind(this, view);
            matterHistoryPresenter = new MatterHistoryPresenter();
            matterHistoryAdapter = new MatterHistoryAdapter(R.layout.matter_history_item, dataBeanList,this);
            matterHistoryView.setAdapter(matterHistoryAdapter);
            matterHistoryView.setLayoutManager(new LinearLayoutManager(getActivity()));

            matterHistoryAdapter.setOnItemClickListener((adapter, itemView, position) -> {
                String id = dataBeanList.get(position).getId();
                Intent intent = new Intent(getActivity(), MatterDetailActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 0);
            });

            matterHistoryView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (RecyclerViewUtil.isSlideToBottom(recyclerView)) {
                        loadData();
                    }
                }
            });
            smartRefreshLayout.setRefreshHeader(new RefreshHeader(getContext()));
            smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
                refreshData();
            });
            refreshData();
        }
        return view;
    }

    protected void refreshData() {
        hasMore = true;
        before = DEFAULT_BEFORE;
        loadData();
    }

    private void loadData() {
        if (hasMore) {
            TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
            baseRequest.put("before", before);
            baseRequest.put("pageSize", 30);
            baseRequest.put("state", mType);
            matterHistoryPresenter.matterHistoryData(baseRequest, this::matterHistoryData);
        }
    }

    public void matterHistoryData(MatterHistoryListBean dataBeanX) {
        smartRefreshLayout.finishRefresh();
        List<MatterHistoryListBean.MatterHistoryBean> data = dataBeanX.getData();
        if (data == null) {
            llVisibility.setVisibility(View.VISIBLE);
            return;
        }
        if (before.equals("0")) {
            dataBeanList.clear();
        }
        dataBeanList.addAll(data);
        if (dataBeanList.isEmpty()) {
            llVisibility.setVisibility(View.VISIBLE);
        } else {
            llVisibility.setVisibility(View.GONE);
            matterHistoryAdapter.notifyDataSetChanged();
        }

        before = dataBeanX.getBefore();
        hasMore = dataBeanX.isHasMore();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        before = DEFAULT_BEFORE;
        hasMore = true;
        dataBeanList.clear();
        loadData();
    }

    @Override
    public void urging(String id) {
        matterHistoryPresenter.reportUrging(id, this::matterUrging);
    }

    @Override
    public void detail(String id) {
        Intent intent = new Intent(getActivity(), MatterDetailActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);


    }

    @Override
    public void cancel(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认取消报事?");
        builder.setTitle("取消提示");
        builder.setPositiveButton("确定", (dialog, which) -> {
            matterHistoryPresenter.reportCancel(id, this::matterCancel);
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
        });
        builder.show();
    }

    public void matterUrging(String msg){
        if (msg!=null){
            ToastUtils.INSTANCE.showToast("催办成功");
        }
    }
    public void matterCancel(String msg){
        if (msg!=null){
            ToastUtils.INSTANCE.showToast("取消报事成功");
            refreshData();
        }
    }
}
