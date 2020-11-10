package com.jingpai.pos.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.repairs.MatterDetailActivity;
import com.jingpai.pos.customer.adapter.MatterHistoryAdapter;
import com.jingpai.pos.customer.bean.MatterHistoryListBean;
import com.jingpai.pos.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FixCustomizeFirstFragment extends Fragment implements MatterHistoryAdapter.MatterCallBack{
    Unbinder unbinder;

    private View view;

    public FixCustomizeFirstFragment(int type) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getActivity(), R.layout.fragment_fix_customize_first, null);
            unbinder = ButterKnife.bind(this, view);
            loadData();
        }
        return view;
    }

    private void loadData() {
//        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
//        baseRequest.put("before", before);
//        baseRequest.put("pageSize", 30);
//        baseRequest.put("state", mType);
//        matterHistoryPresenter.matterHistoryData(baseRequest, this::matterHistoryData);
    }

    public void matterHistoryData(MatterHistoryListBean dataBeanX) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void urging(String id) {
//        matterHistoryPresenter.reportUrging(id, this::matterUrging);
    }

    @Override
    public void detail(String id) {
        Intent intent = new Intent(getActivity(), MatterDetailActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }

    @Override
    public void cancel(String id) {

    }

    public void matterUrging(String msg){
        if (msg!=null){
            ToastUtils.INSTANCE.showToast("催办成功");
        }
    }
    public void matterCancel(String msg){
    }
}
