package com.jingpai.pos.customer.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.house.VisitHisBean;

public class VisitHisAdapter extends BaseQuickAdapter<VisitHisBean.DataBean, BaseViewHolder> {
    public VisitHisAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void setEmptyView(int layoutResId) {
        super.setEmptyView(layoutResId);
        View emptyView = getEmptyView();
        ImageView ivNoData = emptyView.findViewById(R.id.iv_no_data);
        TextView tvNaData = emptyView.findViewById(R.id.tv_no_data);
        tvNaData.setText("暂时没有来访记录");
        ivNoData.setImageResource(R.mipmap.visitorrecord_default);
    }

    @Override
    protected void convert(BaseViewHolder helper, VisitHisBean.DataBean item) {
        if (item.getFinalStatus() == VisitHisBean.VisitState.STATE_HAS_CANCEL && item.isAuthorizer()) {
            //如果已经取消,授权者看到需要看到访车
            //访问类型（访客：VISITOR，访车：VISITCAR）
            item.setVisitType("VISITCAR");
        }
        TextView tvCarApply = helper.getView(R.id.tv_car_apply);
        tvCarApply.setVisibility(View.GONE);
        TextView tvTitle = helper.getView(R.id.tv_title);
        tvTitle.setText(item.getVisitorName() + "  " + item.getVisitorPhone());

        TextView tvCarNum = helper.getView(R.id.tv_car_num);
        TextView tvVisitTime = helper.getView(R.id.tv_visit_time);
        TextView tvCarPosition = helper.getView(R.id.tv_car_position);
        tvVisitTime.setVisibility(View.GONE);

        if ("VISITOR".equals(item.getVisitType())) {
            //访客 只能显示来访日期
            tvCarNum.setVisibility(View.GONE);
            tvVisitTime.setVisibility(View.GONE);
            tvCarPosition.setVisibility(View.GONE);
        } else {
            //访车
            tvCarNum.setVisibility(View.VISIBLE);
            tvCarPosition.setVisibility(View.VISIBLE);
            tvCarNum.setText("车牌号码：" + item.getLicensePlateNo());
        }


        TextView tvVisitDate = helper.getView(R.id.tv_visit_date);
        String visitorDate = item.getVisitorDate();
        if (TextUtils.isEmpty(visitorDate)) {
            tvVisitDate.setVisibility(View.GONE);
        } else {
            tvVisitDate.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(item.getLicensePlateNo())) {
            tvVisitDate.setText("来访时间：" + visitorDate);
        } else {
            tvVisitDate.setText("来访日期：" + visitorDate);
        }

        if (item.getFinalStatus() == VisitHisBean.VisitState.STATE_HAS_IN) {
            tvVisitDate.setText("到访时间：" + item.getEnterTime());
        }


        String parkingPlace = item.getParkingPlace();
        if (TextUtils.isEmpty(parkingPlace)) {
            tvCarPosition.setVisibility(View.GONE);
        }
        tvCarPosition.setText("访客停车：" + parkingPlace + item.getParkingNo() + "号车位");
        TextView tvVisitState = helper.getView(R.id.tv_visit_state);

        //状态（0:未到访，1:已进场，2:已出场，3:车位待授权, 4:已拒绝, 5:已取消, 6:车位授权申请）
        int status = item.getFinalStatus();
        Drawable drawable = mContext.getDrawable(R.mipmap.cheweidaishouquan);
        int stateColor = 0xFF68C68F;
        String content = "";
        switch (status) {
            case 0:
                drawable = mContext.getDrawable(R.mipmap.weidaofang);
                stateColor = 0xFF6689C1;
                content = "未到访";
                break;
            case 1:
                drawable = mContext.getDrawable(R.mipmap.yijinchang);
                stateColor = 0xFF68C68F;
                content = "已进场";
                break;
            case 2:
                drawable = mContext.getDrawable(R.mipmap.yichuchang);
                stateColor = 0xFF979797;
                content = "已出场";
                break;

            case 3:
                drawable = mContext.getDrawable(R.mipmap.cheweidaishouquan);
                stateColor = 0xFFFF8A49;
                content = "车位待授权";
                break;
            case 4:
                drawable = mContext.getDrawable(R.mipmap.yijujue);
                stateColor = 0xFFFF3B30;
                content = "已拒绝";
                break;
            case 5:
                drawable = mContext.getDrawable(R.mipmap.yichuchang);
                stateColor = 0xFF979797;
                content = "已取消";
                break;
            case 6:
                drawable = mContext.getDrawable(R.mipmap.cheweishouquanshenqing);
                stateColor = 0xFFFF8A49;
                content = "车位授权申请";
                break;
        }
        if (item.isAuthorizer()) {
            //如果是授权方
            tvTitle.setText(item.getBuilding() + item.getUnit() + item.getRoomNo() + "室业主");
            tvCarApply.setVisibility(View.VISIBLE);

            tvVisitTime.setVisibility(View.VISIBLE);
            tvVisitTime.setText("拜访时长：" + item.getVisitUseTime() + "小时");
        }
        tvVisitState.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
        tvVisitState.setTextColor(stateColor);
        tvVisitState.setText(content);

    }
}
