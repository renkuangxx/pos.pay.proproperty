package com.jingpai.pos.customer.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.house.HouseBean;

public class VisitChseHouseAdapter extends BaseQuickAdapter<HouseBean.DataBean, BaseViewHolder> {
    public VisitChseHouseAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HouseBean.DataBean item) {

        helper.setText(R.id.tv_title,item.getBuilding()+item.getUnit()+item.getRoomNo()+"室");

    }

}
