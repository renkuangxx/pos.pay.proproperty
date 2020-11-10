package com.jingpai.pos.customer.adapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.show.NoticeBean;

import java.util.List;

public class NoticeAdapter extends BaseQuickAdapter<NoticeBean, BaseViewHolder> {

    public NoticeAdapter(int layoutResId, @Nullable List<NoticeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeBean item) {
        helper.setText(R.id.tv_tag, item.getTag());
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getText());
        helper.setText(R.id.tv_create_time, item.getCreateTime());

        TextView tvTitle = helper.getView(R.id.tv_tag);
        tvTitle.setTextColor(Color.parseColor("#" + item.getTagColor()));
        tvTitle.setBackgroundColor(Color.parseColor("#33" + item.getTagColor()));
    }
}
