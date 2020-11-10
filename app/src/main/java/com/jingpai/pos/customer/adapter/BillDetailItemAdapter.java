package com.jingpai.pos.customer.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.payment.BillDetailBean;

import java.util.List;

public class BillDetailItemAdapter extends BaseQuickAdapter<BillDetailBean.ChargeInfoBean, BaseViewHolder> {

    public BillDetailItemAdapter(int layoutResId, @Nullable List<BillDetailBean.ChargeInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillDetailBean.ChargeInfoBean item) {
        helper.setText(R.id.tv_name,item.getChargeType()+":");
        helper.setText(R.id.tv_money,item.getChargeAmount()+"元");
    }
}
