package com.jingpai.pos.customer.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.CityCommunityBean;

import java.util.List;

public class CityAdapter extends BaseQuickAdapter<CityCommunityBean, BaseViewHolder> {
    public CityAdapter(int layoutResId, @Nullable List<CityCommunityBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, CityCommunityBean item) {
        TextView tvName = helper.getView(R.id.tv_name);
        helper.setText(R.id.tv_name,item.getCityName());
        tvName.setOnClickListener(v -> onItemClick.onItemClick(helper.getAdapterPosition()));
    }
    public interface OnItemClick {
        void onItemClick(int position);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick OnItemClick) {
        this.onItemClick = OnItemClick;
    }
}
