package com.jingpai.pos.customer.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.house.PayItemBean;
import com.jingpai.pos.customer.utils.MathUtil;

import java.util.List;

public class BillAdapter extends BaseQuickAdapter<PayItemBean, BaseViewHolder> {
    private BillItemAdapter payItemAdapter;
    private List<PayItemBean> noPayBillList;
    OnChecked myOnChecked;
    //接口回调
    public interface OnChecked{
        public void myChecked(int position,boolean isChecked);
    }
    public BillAdapter(int layoutResId, @Nullable List<PayItemBean> data, OnChecked onChecked) {
        super(layoutResId, data);
        noPayBillList = data;
        myOnChecked = onChecked;
    }

    @Override
    protected void convert(BaseViewHolder helper, PayItemBean item) {
        String date = item.getTitle().replace("-","年")+"月";
        helper.setText(R.id.tv_month,date);
        helper.setText(R.id.tv_money, MathUtil.format00(item.getMoney())+"元");
        helper.setText(R.id.tv_total_price,MathUtil.format00(item.getMoney())+"");
        CheckBox checkBox = helper.getView(R.id.cb_Bill);
        checkBox.setChecked(item.isChecked());
        LinearLayout llExpand = helper.getView(R.id.ll_expand);
        ImageView ivWave = helper.getView(R.id.iv_wave);
        llExpand.setVisibility(View.GONE);
        ivWave.setVisibility(View.GONE);
        RecyclerView rvPayItem = helper.getView(R.id.rv_bill_item_detail);
        payItemAdapter = new BillItemAdapter(R.layout.item_bill_item, item.getDetail());
        rvPayItem.setLayoutManager(new LinearLayoutManager(mContext));
        rvPayItem.setAdapter(payItemAdapter);
        helper.getView(R.id.rl_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getDetail()!=null&&item.getDetail().size()>0){
                    llExpand.setVisibility(llExpand.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
                    ivWave.setVisibility(ivWave.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
                }
            }
        });
        int position = helper.getLayoutPosition();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myOnChecked.myChecked(position,isChecked);
            }
        });

    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
