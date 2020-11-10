package com.jingpai.pos.customer.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.house.PayItemBean;

import java.util.List;

public class BillItemAdapter extends BaseQuickAdapter<PayItemBean.DetailItem, BaseViewHolder> {

    public BillItemAdapter(int layoutResId, @Nullable List<PayItemBean.DetailItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayItemBean.DetailItem item) {
        helper.setText(R.id.tv_name,item.getItem()+":");
        helper.setText(R.id.tv_money,item.getMoney()+"元");
    }
}
