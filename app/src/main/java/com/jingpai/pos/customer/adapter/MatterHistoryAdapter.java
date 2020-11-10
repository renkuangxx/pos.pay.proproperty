package com.jingpai.pos.customer.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.MatterHistoryListBean;

import java.util.List;

public class MatterHistoryAdapter extends BaseQuickAdapter<MatterHistoryListBean.MatterHistoryBean, BaseViewHolder> {

    private MatterCallBack matterCallBack;
    public MatterHistoryAdapter(int layoutResId, @Nullable List<MatterHistoryListBean.MatterHistoryBean> data,MatterCallBack callBack) {
        super(layoutResId, data);
        matterCallBack = callBack;
    }
    public interface MatterCallBack{
        void urging(String id);
        void detail(String id);
        void cancel(String id);
    }
    @Override
    protected void convert(BaseViewHolder helper, MatterHistoryListBean.MatterHistoryBean item) {
        helper.setText(R.id.tv_type,item.getType());
        String desc = item.getDescription().split("\n")[0];
        helper.setText(R.id.tv_problem_description,desc);
        helper.setText(R.id.tv_matter_time,item.getCreateTime());
        int state = item.getState();
        TextView tvState = helper.getView(R.id.tv_state);
        TextView tvUrging = helper.getView(R.id.tv_urging);
        TextView tvCancel = helper.getView(R.id.tv_cancel);
        TextView tvDetail = helper.getView(R.id.tv_detail);
        RelativeLayout rl1 = helper.getView(R.id.rl_1);
        RelativeLayout rl2 = helper.getView(R.id.rl_2);
        switch (state){
            case 0:
            case 1:
//                if(state==0){
                    tvState.setText("待处理");
//                }else if(state==1){
//                    tvState.setText("已分配");
//                }
                tvState.setTextColor(Color.parseColor("#FF9C11"));
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvState.setText("进行中");
                tvState.setTextColor(Color.parseColor("#68C68F"));
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
                break;
            case 3:
                tvState.setText("待客服确认");
                tvState.setTextColor(Color.parseColor("#68C68F"));
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
                break;
            case -1:
            case 4:
            case 5:
                if(state==-1){
                    tvState.setText("已取消");
                }else if(state==4){
                    tvState.setText("待评价");
                }else if(state==5){
                    tvState.setText("已完成");
                }
                tvState.setTextColor(Color.parseColor("#979797"));
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
                break;
        }


        tvUrging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matterCallBack.urging(item.getId());
            }
        });
        tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matterCallBack.detail(item.getId());
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matterCallBack.cancel(item.getId());
            }
        });

    }

}
