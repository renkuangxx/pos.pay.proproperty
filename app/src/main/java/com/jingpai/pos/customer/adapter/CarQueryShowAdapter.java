package com.jingpai.pos.customer.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.show.CarQueryBean;
import com.stx.xhb.androidx.OnDoubleClickListener;

import java.util.List;

/*
 * function:
 */
public class CarQueryShowAdapter extends BaseQuickAdapter<CarQueryBean.DataBean, BaseViewHolder> {
    private boolean showDelete = false;

    public CarQueryShowAdapter(int layoutResId, @Nullable List<CarQueryBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarQueryBean.DataBean item) {
        helper.setText(R.id.text, item.getPlateNumber());
        LinearLayout llDelete = helper.getView(R.id.ll_delete);

        TextView delete = helper.getView(R.id.car_delete);

        delete.setOnClickListener(new  OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (carDeleteCallBack==null){
                    return;
                }
                carDeleteCallBack.carDeleteCallBack(item);
            }
        });

        TextView tvInOutHis = helper.getView(R.id.tv_in_out_his);

        tvInOutHis.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (carDeleteCallBack==null){
                    return;
                }
                carDeleteCallBack.inOutHis(item);
            }
        });

        if (isShowDelete()) {
            llDelete.setVisibility(View.VISIBLE);
            tvInOutHis.setVisibility(View.GONE);
        } else {
            llDelete.setVisibility(View.GONE);
            tvInOutHis.setVisibility(View.VISIBLE);

        }



        TextView tvCarOwnerState = helper.getView(R.id.tv_car_owner_state);
        if (item.isVisitorCar()) {
            tvCarOwnerState.setVisibility(View.VISIBLE);
        } else {
            tvCarOwnerState.setVisibility(View.GONE);
        }


        TextView tvInOutState = helper.getView(R.id.tv_car_in_or_out);


        TextView tvTime = helper.getView(R.id.tv_time);

        tvInOutState.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(item.getLeaveTime())) {
            tvInOutState.setText("已出场");
            tvInOutState.setTextColor(mContext.getResources().getColor(R.color.text_97));
            tvInOutState.setBackground(mContext.getResources().getDrawable(R.drawable.shape_979797_2dp));
            tvTime.setText("出场时间：" + item.getEnterTime());
            return;
        }
        if (!TextUtils.isEmpty(item.getEnterTime())) {
            tvInOutState.setText("已进场");
            tvInOutState.setTextColor(mContext.getResources().getColor(R.color.main));
            tvInOutState.setBackground(mContext.getResources().getDrawable(R.drawable.shape_f8a49_2dp));
            tvTime.setText("进场时间：" + item.getEnterTime());
            return;
        }

        tvInOutState.setVisibility(View.GONE);
        tvTime.setText("暂无记录");

    }

    public boolean isShowDelete() {
        return showDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
        notifyDataSetChanged();
    }

    //接口回调
    public interface CarDeleteCallBack {
        void carDeleteCallBack(CarQueryBean.DataBean item);
        void inOutHis(CarQueryBean.DataBean item);
    }

    private CarDeleteCallBack carDeleteCallBack;

    public void CarDeleteCallBack(CarDeleteCallBack carDeleteCallBack) {
        this.carDeleteCallBack = carDeleteCallBack;
    }
}