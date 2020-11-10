package com.jingpai.pos.customer.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.ManagerBean;

import java.util.List;

public class PhoneAdapter extends BaseQuickAdapter<ManagerBean, BaseViewHolder> {
    public PhoneAdapter(int layoutResId, @Nullable List<ManagerBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, ManagerBean item) {
        TextView tvName = helper.getView(R.id.tv_show_phone);
        tvName.setText(item.getName()+":"+item.getPhone());
        tvName.setOnClickListener(v -> onItemClick.onItemClick(item.getPhone()));
    }
    public interface OnItemClick {
        void onItemClick(String phone);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick OnItemClick) {
        this.onItemClick = OnItemClick;
    }
}
