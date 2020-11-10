package com.jingpai.pos.customer.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.FavoriteBean;
import com.jingpai.pos.customer.utils.GlideUtils;

import java.util.List;

public class FavoriteAdapter extends BaseQuickAdapter<FavoriteBean.TabBean.ProductBean, BaseViewHolder> {

    public FavoriteAdapter(int layoutResId, @Nullable List<FavoriteBean.TabBean.ProductBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, FavoriteBean.TabBean.ProductBean item) {
        helper.setText(R.id.tv_title_icon,item.getTitle());
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        GlideUtils.LoadingImg(mContext,item.getCover(),ivIcon);
    }

}
