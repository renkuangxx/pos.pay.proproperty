package com.jingpai.pos.customer.adapter;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.DailyRegistrationBean;

import java.util.List;

/*
 * function:
 */
public class DailyRegistrationAdapter extends BaseQuickAdapter<DailyRegistrationBean.DataBeanX.ListBean.DataBean, BaseViewHolder> {

    public DailyRegistrationAdapter(int layoutResId) {
        super(layoutResId);
    }
    public DailyRegistrationAdapter(int layoutResId, @Nullable List<DailyRegistrationBean.DataBeanX.ListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyRegistrationBean.DataBeanX.ListBean.DataBean item) {

        helper.setText(R.id.tv_daily_time,item.getDate());
        helper.setText(R.id.tv_daily_type,item.getHealthState());
        helper.setText(R.id.tv_daily_head,item.getTemperature() + "℃");

        LinearLayout linearLayout = helper.getView(R.id.ll_particulars);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyParticularsCallBack.DailyParticularsCallBack(item.getId());
            }
        });


    }

    //接口回调
    public interface DailyParticularsCallBack{
        void DailyParticularsCallBack(int id);
    }

    private DailyParticularsCallBack dailyParticularsCallBack;

    public void DailyParticularsCallBack(DailyParticularsCallBack dailyParticularsCallBack){
        this.dailyParticularsCallBack = dailyParticularsCallBack;
    }

}
