package com.jingpai.pos.customer.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.payment.BillDetailBean;

import java.util.List;

public class BillDetailAdapter extends BaseQuickAdapter<BillDetailBean, BaseViewHolder> {
    private BillDetailItemAdapter payItemAdapter;

    public BillDetailAdapter(int layoutResId, @Nullable List<BillDetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BillDetailBean item) {
        String date = item.getChargeDate().replace("-","年")+"月";
        helper.setText(R.id.tv_date,date);
        RecyclerView rvPayItem = helper.getView(R.id.rv_bill_item_detail);
        payItemAdapter = new BillDetailItemAdapter(R.layout.item_bill_detail_item, item.getChargeInfoList());
        rvPayItem.setLayoutManager(new LinearLayoutManager(mContext));
        rvPayItem.setAdapter(payItemAdapter);

    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
