package com.jingpai.pos.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.adapter.RecommondSpaceAdapter;
import com.jingpai.pos.customer.bean.ContentCateListBean;
import com.jingpai.pos.customer.bean.ContentLikeCollectPageInfo;
import com.jingpai.pos.customer.bean.MatterHistoryListBean;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.RecyclerViewUtil;
import com.jingpai.pos.customer.views.UpdataInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LifeChildFragment extends Fragment implements UpdataInterface{
    Unbinder unbinder;
    @BindView(R.id.matter_history_view)
    RecyclerView matterHistoryView;
    @BindView(R.id.ll_visibility)
    LinearLayout llVisibility;
    private RecommondSpaceAdapter recommondSpaceAdapter;
    //空间
    ContentCateListBean spaceBean;
    private List<ContentLikeCollectPageInfo.ListBean> dataBeanList = new ArrayList<>();
    private LifePresenter lifePresenter;
    private View view;
    int mType;

    public LifeChildFragment(int type) {
        initData(type);
    }

    private void initData(int type) {
        mType = type;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            lifePresenter = new LifePresenter();
            view = View.inflate(getActivity(), R.layout.fragment_child_vp, null);
            unbinder = ButterKnife.bind(this, view);
            setData();
//            recommondSpaceAdapter.setOnItemClickListener((adapter, itemView, position) -> {
//                String id = dataBeanList.get(position).getId();
//                Intent intent = new Intent(getActivity(), MatterDetailActivity.class);
//                intent.putExtra("id", id);
//                startActivityForResult(intent, 0);
//            });

            matterHistoryView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (RecyclerViewUtil.isSlideToBottom(recyclerView)) {
//                        loadData();
                        setData();
                    }
                }

            });
//            smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
////                refreshData();
//            });
//            refreshData();
        }
        return view;
    }
    private void setData() {
        recommondSpaceAdapter = new RecommondSpaceAdapter(getContext(), dataBeanList, lifePresenter,new UpdataInterface() {
            @Override
            public void updataInterface() {
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setAutoMeasureEnabled(true);
        matterHistoryView.setLayoutManager(layoutManager);
        matterHistoryView.setHasFixedSize(true);
        matterHistoryView.setNestedScrollingEnabled(false);

        matterHistoryView.setAdapter(recommondSpaceAdapter);
        spaceBean = LocalCache.getLifeContent("ContentCateListBean");
        if (spaceBean==null ||spaceBean.getContentCateVos().get(mType).getContentLikeCollectPageInfo() ==null
                || spaceBean.getContentCateVos().get(mType).getContentLikeCollectPageInfo().getList() == null ){
            llVisibility.setVisibility(View.VISIBLE);
            return;
        }
        dataBeanList = spaceBean.getContentCateVos().get(mType).getContentLikeCollectPageInfo().getList();
        if (dataBeanList.isEmpty()) {
            llVisibility.setVisibility(View.VISIBLE);
        } else {
            llVisibility.setVisibility(View.GONE);
            recommondSpaceAdapter.setData(dataBeanList);
        }
    }

    public void matterHistoryData(MatterHistoryListBean dataBeanX) {
//        smartRefreshLayout.finishRefresh();
        List<MatterHistoryListBean.MatterHistoryBean> data = dataBeanX.getData();
        if (data == null) {
            llVisibility.setVisibility(View.VISIBLE);
            return;
        }
//        dataBeanList.addAll(data);
        if (dataBeanList.isEmpty()) {
            llVisibility.setVisibility(View.VISIBLE);
        } else {
            llVisibility.setVisibility(View.GONE);
            recommondSpaceAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dataBeanList.clear();
    }


    @Override
    public void updataInterface() {
        setData();
    }
}
