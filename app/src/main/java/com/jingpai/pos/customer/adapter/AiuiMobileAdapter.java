package com.jingpai.pos.customer.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.ManagerBean;

import java.util.List;

/**
 * 时间: 2020/2/24
 * 功能:
 */
public class AiuiMobileAdapter extends BaseQuickAdapter<ManagerBean, BaseViewHolder> {

    private List<ManagerBean> mManagerBeanList;
    public AiuiMobileAdapter(int layoutResId, List<ManagerBean> managerBeanList) {
        super(layoutResId, managerBeanList);
        mManagerBeanList = managerBeanList;
    }

    @Override
    protected void convert(BaseViewHolder helper, ManagerBean item) {
        TextView tvContent = helper.getView(R.id.tv_content);
        View line = helper.getView(R.id.view_line);
        tvContent.setText(helper.getAdapterPosition()+1+"  "+ item.getName()+"电话： "+item.getMobile());
        if (helper.getAdapterPosition()==mManagerBeanList.size()-1)line.setVisibility(View.GONE);
        tvContent.setOnClickListener(v -> {
//            openDoorBack.OpenDoorBack(item.getId());
        });
    }

    public void setData(List<ManagerBean> managerBeanList){
        notifyDataSetChanged();
    }
    public interface OpenDoorBack{
        void OpenDoorBack(String id);
    }

    private OpenDoorBack openDoorBack;

    public void setOpenDoorBack(OpenDoorBack openDoorBack){

        this.openDoorBack = openDoorBack;

    }

}