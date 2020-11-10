package com.jingpai.pos.customer.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.bean.ChargeOrderDetailListVo;

import java.util.ArrayList;
import java.util.List;

public class Apply4InvoiceAdapter extends RecyclerView.Adapter<Apply4InvoiceAdapter.Apply4InvoiceAdapterViewHolder> {
    private Context myContext;
    private List<ChargeOrderDetailListVo> data = new ArrayList<>();
    public Apply4InvoiceAdapter(Context context,  List<ChargeOrderDetailListVo> data) {
        myContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public Apply4InvoiceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice_detail, parent, false);
        Apply4InvoiceAdapter.Apply4InvoiceAdapterViewHolder holder = new Apply4InvoiceAdapter.Apply4InvoiceAdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Apply4InvoiceAdapterViewHolder holder, int position) {
        if (data.size() <= 0 || position > data.size() || data.get(position) == null)return;
        String a = TextUtils.isEmpty(data.get(position).getItemName())?"":data.get(position).getItemName();
        String b = TextUtils.isEmpty(data.get(position).getChargeAmount())?"":data.get(position).getChargeAmount();
        String month = TextUtils.isEmpty(data.get(position).getChargeMonth())?"":data.get(position).getChargeMonth();
        holder.tv_detail_name.setText(a);
        holder.tv_detail_price.setText(b+" 元");
        holder.tv_detail_month.setText(month+"月");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Apply4InvoiceAdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_detail_name;
        private TextView tv_detail_price;
        private TextView tv_detail_month;
        public Apply4InvoiceAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_detail_name = itemView.findViewById(R.id.tv_detail_name);
            tv_detail_price = itemView.findViewById(R.id.tv_detail_price);
            tv_detail_month = itemView.findViewById(R.id.tv_detail_month);
        }
    }
}
