package com.jingpai.pos.customer.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.CarStallBean;

import java.util.List;

/**
 * 时间: 2020/3/7
 * 功能:
 */
public class CarStallShowAdapter extends BaseQuickAdapter<CarStallBean.DataBean, BaseViewHolder> {

    public boolean isShowDelete() {
        return showDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
        notifyDataSetChanged();
    }

    private boolean showDelete;
    public CarStallShowAdapter(int layoutResId, @Nullable List<CarStallBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarStallBean.DataBean item) {

        helper.setText(R.id.tv_stall_text,item.getParkingPlace()+item.getParkingNo());

        TextView delete = helper.getView(R.id.car_stall_delete);
        LinearLayout llDelete = helper.getView(R.id.ll_stall_delete);

        if (isShowDelete()){
            llDelete.setVisibility(View.VISIBLE);
        }else {
            llDelete.setVisibility(View.INVISIBLE);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carStallDeleteCallBack.carStallDeleteCallBack(item.getParkingId());
            }
        });

        TextView tvOwnerState=helper.getView(R.id.tv_stall_owner_state);
        if ("RENTAL".equals(item.getParkingType())){
            tvOwnerState.setText("租赁");
            tvOwnerState.setTextColor(mContext.getResources().getColor(R.color.text_6689C1));
            tvOwnerState.setBackground(mContext.getDrawable(R.drawable.shape_6689c1_2dp));
        }else {
            tvOwnerState.setText("产权");
            tvOwnerState.setTextColor(mContext.getResources().getColor(R.color.main));
            tvOwnerState.setBackground(mContext.getDrawable(R.drawable.shape_f8a49_2dp));
        }

    }

    //接口回调
    public interface CarStallDeleteCallBack{
        void carStallDeleteCallBack(String id);
    }

    private CarStallDeleteCallBack carStallDeleteCallBack;

    public void CarStallDeleteCallBack(CarStallDeleteCallBack carStallDeleteCallBack){
        this.carStallDeleteCallBack = carStallDeleteCallBack;
    }

}
