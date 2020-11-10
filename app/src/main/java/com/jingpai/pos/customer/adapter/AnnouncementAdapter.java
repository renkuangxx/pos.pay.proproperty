package com.jingpai.pos.customer.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.show.AnnouncementBean;

import java.util.List;

/**
 * 时间: 2020/2/25
 * 功能:
 */
public class AnnouncementAdapter extends BaseQuickAdapter<AnnouncementBean.DataBeanX.DataBean, BaseViewHolder> {

    public AnnouncementAdapter(int layoutResId, @Nullable List<AnnouncementBean.DataBeanX.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnnouncementBean.DataBeanX.DataBean item) {
        helper.setText(R.id.tv_announcement_title,item.getTitle());
        helper.setText(R.id.tv_announcement_time,item.getCreateTime());
        helper.setText(R.id.tv_announcement_content,item.getContent());
    }
}
