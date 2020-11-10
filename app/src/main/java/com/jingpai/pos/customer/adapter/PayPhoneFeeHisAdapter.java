package com.jingpai.pos.customer.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.payphone.PayFeeHisBean;

public class PayPhoneFeeHisAdapter extends BaseQuickAdapter<PayFeeHisBean, BaseViewHolder> {

    public PayPhoneFeeHisAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayFeeHisBean item) {
        helper.setText(R.id.tv_phone_num,item.getPhone());
        helper.setText(R.id.tv_fee_date,item.getCreateTime());
        helper.setText(R.id.tv_pay_state, item.getOrderStateName());
        helper.setTextColor(R.id.tv_pay_state,item.getOrderState()==0?mContext.getResources().getColor(R.color.text_red):mContext.getResources().getColor(R.color.text_97));
        helper.setText(R.id.tv_pay_face_fee,item.getPrice()+"");

    }
}
