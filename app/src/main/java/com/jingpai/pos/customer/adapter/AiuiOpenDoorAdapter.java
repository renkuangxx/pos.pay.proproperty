package com.jingpai.pos.customer.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.AccessBean;

import java.util.List;

/**
 * 时间: 2020/2/24
 * 功能:
 */
public class AiuiOpenDoorAdapter extends BaseQuickAdapter<AccessBean, BaseViewHolder> {
    private List<AccessBean> mAccessBeans;
    public AiuiOpenDoorAdapter(int layoutResId,List<AccessBean> accessBeans) {
        super(layoutResId, accessBeans);
        mAccessBeans = accessBeans;
    }

    @Override
    public void setEmptyView(int layoutResId) {
        super.setEmptyView(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccessBean item) {
        TextView tvContent = helper.getView(R.id.tv_content);
        View line = helper.getView(R.id.view_line);
        tvContent.setText(helper.getAdapterPosition()+1+"  "+ item.getName());
        if (helper.getAdapterPosition()==mAccessBeans.size()-1)line.setVisibility(View.GONE);
        tvContent.setOnClickListener(v -> {
            openDoorBack.OpenDoorBack(item);
        });
    }

    public interface OpenDoorBack{
        void OpenDoorBack(AccessBean item);
    }

    private OpenDoorBack openDoorBack;

    public void setOpenDoorBack(OpenDoorBack openDoorBack){

        this.openDoorBack = openDoorBack;

    }

}