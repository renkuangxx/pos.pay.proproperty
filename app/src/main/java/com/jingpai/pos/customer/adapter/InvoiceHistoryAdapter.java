package com.jingpai.pos.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.activity.payment.PayDetailActivity;
import com.jingpai.pos.customer.bean.payment.BillHistoryBean;

import java.util.List;

public class InvoiceHistoryAdapter extends BaseQuickAdapter<BillHistoryBean, BaseViewHolder> {
    private Context myContext;
    public InvoiceHistoryAdapter(Context context, int layoutResId, @Nullable List<BillHistoryBean> data) {
        super(layoutResId, data);
        myContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, BillHistoryBean item) {
        helper.setText(R.id.tv_room_no_name,item.getHouseInfo());
        helper.setText(R.id.tv_park_no_name, TextUtils.isEmpty(item.getParkingInfo())?"-":item.getParkingInfo());
        helper.setText(R.id.tv_date,item.getPayDate());
        helper.setText(R.id.tv_price,"¥"+item.getOrderAmount());
        int position = helper.getLayoutPosition();
        if (position==getData().size()-1){
            helper.getView(R.id.iv_divider).setVisibility(View.INVISIBLE);
        }else{
            helper.getView(R.id.iv_divider).setVisibility(View.VISIBLE);
        }
        helper.getView(R.id.tv_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PayDetailActivity.class);
                intent.putExtra("houseName", item.getHouseInfo());
                intent.putExtra("parking", item.getParkingInfo());
                intent.putExtra("date", item.getPayDate());
                intent.putExtra("price", item.getOrderAmount());

                intent.putExtra("orderId", item.getOrderId());
                mContext.startActivity(intent);
            }
        });


    }
}
