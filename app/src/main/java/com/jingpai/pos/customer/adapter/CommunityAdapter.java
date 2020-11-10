package com.jingpai.pos.customer.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.bean.CommunityBean;

import java.util.List;

public class CommunityAdapter extends BaseQuickAdapter<CommunityBean, BaseViewHolder> {
    public CommunityAdapter(int layoutResId, @Nullable List<CommunityBean>  data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, CommunityBean item) {
        TextView tvName = helper.getView(R.id.tv_name);
        helper.setText(R.id.tv_name,item.getCommunityName());
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
