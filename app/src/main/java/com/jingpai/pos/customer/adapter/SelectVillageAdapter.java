package com.jingpai.pos.customer.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.bean.Community;

import java.util.List;
/**
 * 时间: 2020/2/23
 * 功能:
 */
public class SelectVillageAdapter extends BaseQuickAdapter<Community, BaseViewHolder> {

    public SelectVillageAdapter(int layoutResId, @Nullable List<Community> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Community item) {
        helper.setText(R.id.tv_village_item,item.getCommunityName());
    }
}