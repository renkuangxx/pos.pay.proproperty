package com.jingpai.pos.customer.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.HouseMemberBean;
import com.jingpai.pos.customer.base.MyApplication;

import java.util.List;

/**
 * 时间: 2020/3/1
 * 功能:
 */
public class HouseMemberAdapter extends BaseQuickAdapter<HouseMemberBean, BaseViewHolder> {

    public HouseMemberAdapter(int layoutResId, @Nullable List<HouseMemberBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HouseMemberBean item) {
        ImageView imageView = helper.getView(R.id.iv_avatar);
        Glide.with(MyApplication.getContext())
                .load(item.getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
        helper.setText(R.id.tv_mem_name,item.getName());
        helper.setText(R.id.tv_mem_type,item.getTypeName());

    }
}
