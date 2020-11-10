package com.jingpai.pos.customer.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.house.CarPositionBean;

public class VisitCarPositionAdapter extends BaseQuickAdapter<CarPositionBean.DataBean, BaseViewHolder> {
    public VisitCarPositionAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarPositionBean.DataBean item) {

        helper.setText(R.id.tv_title,item.getParkingPlace()+item.getParkingNo()+"号车位");
    }

}
