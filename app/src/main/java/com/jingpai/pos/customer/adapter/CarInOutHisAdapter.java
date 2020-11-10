package com.jingpai.pos.customer.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.show.CarQueryBean;

/**
 * 车辆出入记录适配器
 */
public class CarInOutHisAdapter extends BaseQuickAdapter<CarQueryBean.DataBean, BaseViewHolder> {
    public CarInOutHisAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarQueryBean.DataBean item) {
        String enterTime = item.getEnterTime();
        if (TextUtils.isEmpty(enterTime)){
            enterTime="--";
        }
        helper.setText(R.id.tv_in_time,"进场时间："+enterTime);
        String leaveTime = item.getLeaveTime();
        if (TextUtils.isEmpty(leaveTime)){
            leaveTime="--";
        }
        helper.setText(R.id.tv_out_time,"出场时间："+leaveTime);
    }


    @Override
    public void setEmptyView(int layoutResId) {
        super.setEmptyView(layoutResId);
        View emptyView = getEmptyView();
        ImageView ivNoData=emptyView.findViewById(R.id.iv_no_data);
        TextView tvNoData=emptyView.findViewById(R.id.tv_no_data);
        ivNoData.setImageResource(R.mipmap.carrecord_default);
        tvNoData.setText("还没有出入记录哦～");
    }
}
