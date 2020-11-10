package com.jingpai.pos.customer.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.show.BuildingBean;

import java.util.List;

/**
 * 时间: 2020/2/11
 * 功能:
 */
public class BuildingAdapter extends BaseQuickAdapter<BuildingBean.DataBean, BaseViewHolder> {
    public BuildingAdapter(int layoutResId, @Nullable List<BuildingBean.DataBean> data) {
        super(layoutResId, data);
    }

    public BuildingAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BuildingBean.DataBean item) {
        TextView tvType = helper.getView(R.id.tv_typeName);
        tvType.setText(item.getTypeName());
        helper.setText(R.id.tv_communityName, item.getCommunityName());
        helper.setText(R.id.tv_buildNo, item.getUnitNo() + item.getRoomNo());

        if (item.getState()==-1){
            helper.setText(R.id.tv_status,"待上传");
            helper.getView(R.id.tv_number).setVisibility(View.GONE);
            helper.getView(R.id.tv_status).setVisibility(View.VISIBLE);
        }else if (item.getState()==1){
            helper.setText(R.id.tv_number, item.getNumber() + "人");
            helper.getView(R.id.tv_number).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_status).setVisibility(View.GONE);
        }else if (item.getState()==0){
            helper.setText(R.id.tv_status,"审核中");
            helper.getView(R.id.tv_number).setVisibility(View.GONE);
            helper.getView(R.id.tv_status).setVisibility(View.VISIBLE);
        }else if (item.getState()==2){
            helper.setText(R.id.tv_status,"已拒绝");
            helper.getView(R.id.tv_number).setVisibility(View.GONE);
            helper.getView(R.id.tv_status).setVisibility(View.VISIBLE);
        }


        //人员类型(OWNER:产权人；TENANT:租户；FAMILY:家属)
        switch (item.getType()) {
            case "OWNER":
                tvType.setTextColor(0xFF6689C1);
                tvType.setBackground(mContext.getResources().getDrawable(R.drawable.shape_6689c1_2dp));
                break;
            case "TENANT":
                tvType.setTextColor(0xFFFF8A49);
                tvType.setBackground(mContext.getResources().getDrawable(R.drawable.shape_f8a49_2dp));
                break;

            case "FAMILY":
                tvType.setTextColor(0xFFFF3B30);
                tvType.setBackground(mContext.getResources().getDrawable(R.drawable.shape_ff3b30_2dp));
                break;
        }

    }
}
